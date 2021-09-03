package space.forloop.airnzspider.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.forloop.airnzspider.services.ContentService;
import space.forloop.airnzspider.services.SeleniumService;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class Startup {

  private final ContentService contentService;

  private final SeleniumService seleniumService;

  @Scheduled(fixedDelay = 600_000) // 30 Minutes
  public void process() {

    // Load the Search page for AirNz
    seleniumService.crawl();

    // Fill out the details and search
    seleniumService.completeSearchForm();

    // Parse the response
    contentService.extractData();

    // Close the current session
    seleniumService.close();
  }
}
