package space.forloop.airnzspider.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {

  @Bean
  public ExecutorService executorService() {
    return Executors.newCachedThreadPool();
  }

  @Bean
  public ScheduledExecutorService scheduledExecutorService() {
    return Executors.newSingleThreadScheduledExecutor();
  }
}
