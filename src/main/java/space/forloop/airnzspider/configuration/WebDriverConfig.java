package space.forloop.airnzspider.configuration;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.forloop.airnzspider.properties.Properties;

@Configuration
@RequiredArgsConstructor
public class WebDriverConfig {

  private final Properties properties;

  @Bean
  public WebDriver get() {

    final var options = new ChromeOptions();

    System.setProperty("webdriver.chrome.whitelistedIps", "");
    System.setProperty("webdriver.chrome.driver", properties.getDriver());

    options.addArguments("--log-level=3");
    options.addArguments("--lang=en_US");
    options.addArguments("--window-size=1840,2160");
    options.addArguments("--disable-extensions");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-gpu");
    //    options.addArguments("--headless");

    options.addArguments(
        "--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");

    return new ChromeDriver(options);
  }
}
