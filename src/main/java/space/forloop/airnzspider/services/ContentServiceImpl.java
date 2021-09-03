package space.forloop.airnzspider.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

  /**
   * AirNZ has the 25th of Sept on their site as the next known flight. See if anything comes up
   * before that.
   */
  public static final LocalDate CUT_OFF = LocalDate.of(2021, 9, 25);

  private final SeleniumService seleniumService;

  private final NotificationService notificationService;

  public void extractData() {
    final var document = seleniumService.getPageSource();

    document.getElementsByClass("vui-sc-daywrapper").stream()
        .filter(getElementPredicate())
        .findAny()
        .map(element -> parseDate("%s %s".formatted(getDay(element), getMonth(document))))
        .ifPresentOrElse(this::date, this::noDate);
  }

  @NotNull
  private Predicate<Element> getElementPredicate() {
    return element -> !element.hasClass("unavailable");
  }

  @NotNull
  private String getMonth(final Document document) {
    final String month = document.select("div.vui-sc-monthname").text();

    final String[] arr = month.split(" ", 2);

    return "%s %d".formatted(arr[0].trim(), LocalDate.now().getYear());
  }

  @NotNull
  private String getDay(final Element element) {
    return element.select("div.vui-sc-date").text();
  }

  /**
   * If we find a date and its before the last known manually entered date, announce it via Slack.
   */
  private void date(final LocalDate localDate) {
    if (localDate.isBefore(CUT_OFF)) {
      notificationService.send("Next flight is: %s".formatted(localDate));
    } else {
      log.info("Skipping date...");
    }
  }

  /** Ran in the circumstance that no date could be found */
  private void noDate() {
    seleniumService.clickArrow();

    // Recursive call to extract date
    extractData();
  }

  /** Convert a string of "Sat 25 September 2021" into a LocalDate format. */
  private LocalDate parseDate(final String text) {
    final var date = text.split(" ", 2)[1].trim();
    final var formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

    return LocalDate.parse(date, formatter);
  }
}
