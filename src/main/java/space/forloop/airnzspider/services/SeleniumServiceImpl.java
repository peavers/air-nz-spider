package space.forloop.airnzspider.services;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeleniumServiceImpl implements SeleniumService {

  private final WebDriver driver;

  @Override
  public void crawl() {
    driver.get("https://flightbookings.airnewzealand.co.nz/vbook/actions/search");
    driver.manage().window().maximize();
  }

  @Override
  public void clickArrow() {
    try {
      driver.findElement(By.className("vui-icon-arrowright")).click();
    } catch (final org.openqa.selenium.NoSuchElementException exception) {
      log.error("Unable to find element...");
    }
  }

  @Override
  public void close() {
    driver.close();
  }

  @Override
  public void completeSearchForm() {

    log.info("Filling out Search Form...");

    fixedPause();
    driver.findElement(By.id("field-search-journey-type-oneway")).click();

    keypress("depart-from", "Wellington");
    keypress("depart-to", "Melbourne");
    keypress("leaveDate", LocalDate.now().toString());

    driver.findElement(By.id("leaveDate")).sendKeys(Keys.ENTER);
    fixedPause();

    driver.findElement(By.id("leaveDate")).sendKeys(Keys.ENTER);
    fixedPause();

    log.info("Search Form complete");
  }

  @Override
  public Document getPageSource() {
    return Jsoup.parse(driver.getPageSource());
  }

  /** Pause for a fixed amount of seconds for the page to finish loading. */
  private void fixedPause() {
    final var duration = 5;
    try {
      log.info("Waiting {} seconds...", duration);

      TimeUnit.SECONDS.sleep(duration);
    } catch (final InterruptedException e) {
      log.error("Sleep interrupted: {}", e.getMessage());
    }
  }

  /** Sleep the current thread for a random time between the two values. */
  private void randomPause() {
    final var random = new Random();

    try {
      TimeUnit.MILLISECONDS.sleep(random.nextInt(100 - 50) + 50);
    } catch (final InterruptedException e) {
      log.error("Pause interrupting: {}", e.getMessage());
    }
  }

  /** Enter into a field the passed input with random delays between each character. */
  private void keypress(final String field, final String input) {
    driver.findElement(By.id(field)).click();

    for (final char c : input.toCharArray()) {
      driver.findElement(By.id(field)).sendKeys(String.valueOf(c));

      randomPause();
    }
  }
}
