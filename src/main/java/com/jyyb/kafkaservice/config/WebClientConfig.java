package com.jyyb.kafkaservice.config;

import com.jyyb.kafkaservice.config.properties.ApplicationProperties;
import com.jyyb.kafkaservice.exception.HttpStatusAwareException;
import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;




@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final ApplicationProperties applicationProperties;

    @Bean("loggableHttClient")
    HttpClient loggableHttpClient(){
        return HttpClient.create()
                .wiretap("httpClientLogger", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
    }

    @Bean
    WebClient authTokenWebClient(){
        final WebClient.Builder builder = WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return builder.build();

    }

    @Bean
    @DependsOn("loggableHttpClient")
    WebClient herokuWebClient(final HttpClient loggableHttpClient) {
        final ApplicationProperties.ServiceConfig herokuApi = applicationProperties.getHerokuApi();
        return baseWebClient(errorHandlingFilter())
                .baseUrl(herokuApi.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(loggableHttpClient))
                .build();

    }

    private WebClient.Builder baseWebClient(ExchangeFilterFunction filterFunction){
        return WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(filterFunction);

    }

    public static ExchangeFilterFunction errorHandlingFilter(){
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            final HttpStatus httpStatus = response.statusCode();
            if(httpStatus.isError()){
                return response.bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new HttpStatusAwareException(httpStatus,errorBody)));
            }
            return Mono.just(response);
        });

    }
}
