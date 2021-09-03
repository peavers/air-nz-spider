package space.forloop.airnzspider.services;

import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final ExecutorService executorService;

  private final SlackService slackService;

  @Override
  public void send(final String message) {
    executorService.execute(() -> slackService.sendMessage(message));
  }
}
