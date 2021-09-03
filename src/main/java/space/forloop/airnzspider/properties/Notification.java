package space.forloop.airnzspider.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spider.notification")
public class Notification {

  private String slackWebhook;
}
