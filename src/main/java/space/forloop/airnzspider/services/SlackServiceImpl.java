package space.forloop.airnzspider.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import space.forloop.airnzspider.domain.SlackRequest;
import space.forloop.airnzspider.properties.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

  private final Properties properties;

  private final OkHttpClient okHttpClient;

  private final Callback callback;

  private final ObjectMapper objectMapper;

  @Override
  public void sendMessage(final String message) {
    okHttpClient.newBuilder().build().newCall(getRequest(message)).enqueue(callback);
  }

  @NotNull
  private Request getRequest(final String message) {

    return new Request.Builder()
        .url(properties.getNotification().getSlackWebhook())
        .post(getRequestBody(message))
        .build();
  }

  @NotNull
  @SneakyThrows
  private RequestBody getRequestBody(final String message) {
    final var json = MediaType.get("application/json; charset=utf-8");

    final var string =
        objectMapper.writeValueAsString(SlackRequest.builder().text(message).build());

    return RequestBody.create(string, json);
  }
}
