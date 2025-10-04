package com.jyyb.kafkaservice.service.client;


import com.jyyb.kafkaservice.config.properties.ApplicationProperties;
import com.jyyb.kafkaservice.config.properties.RetryStrategy;
import com.jyyb.kafkaservice.model.HerokuPayload;
import com.jyyb.kafkaservice.security.AuthService;
import com.jyyb.kafkaservice.util.ApiExceptionHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class HerokuClient {

    private final WebClient herokuWebClient;
    private final AuthService authService;
    private final ApplicationProperties applicationProperties;

    public String herokuCall(HerokuPayload requestBody, String activityType){
        final RetryStrategy retry = applicationProperties.getHerokuApi().getRetry();;
        return herokuWebClient.mutate()
                .build()
                .post()
                .headers(buildHeaders(activityType))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(retry.getMaxAttemps(), retry.getMinInterval())
                        .jitter(retry.getJitterFactor())
                        .doAfterRetry(signal -> log.debug("retry count: {}/{}", signal.totalRetries()+1, retry.getMaxAttemps()))
                        .filter(ApiExceptionHelper::isRetryable))
                .timeout(retry.getTimeout())
                .block();


    }
    private Consumer<HttpHeaders> buildHeaders(String activityType){
        return headers ->{
            headers.setBearerAuth(authService.getToken().getAccessToken());
            headers.set("EVENT_ID", UUID.randomUUID().toString());
        };
    }
}
