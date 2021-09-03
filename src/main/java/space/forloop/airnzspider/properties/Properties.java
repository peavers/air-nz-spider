package space.forloop.airnzspider.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spider")
public class Properties {

  private Notification notification;

  private String driver;
}
