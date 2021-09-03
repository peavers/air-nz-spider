package space.forloop.airnzspider.configuration;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OkHttpConfig {

  @Bean
  public OkHttpClient okHttpClient() {

    return new OkHttpClient.Builder().retryOnConnectionFailure(false).build();
  }

  @Bean
  public Callback callback() {

    return new Callback() {

      @SneakyThrows
      @Override
      public void onResponse(@NotNull final Call call, @NotNull final Response response) {
        if (response.code() != 200) {
          log.warn("Response: {}", response.body().string());
        } else {
          log.info("Response: {}", response.code());
        }

        response.close();
      }

      @Override
      public void onFailure(@NotNull final Call call, @NotNull final IOException e) {
        log.error("Failure: {}", e.getMessage());
      }
    };
  }
}
