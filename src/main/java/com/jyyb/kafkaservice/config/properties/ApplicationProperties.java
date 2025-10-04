package com.jyyb.kafkaservice.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@AllArgsConstructor
@Getter
public class ApplicationProperties {

    @NotNull
    private final ApplicationProperties.ServiceConfig herokuApi;
    @Validated
    @ConstructorBinding
    @Getter
    @AllArgsConstructor
    public static class ServiceConfig {
        @NotBlank
        private final String baseUrl;

        @NotNull
        private final RetryStrategy retry;
    }
}
