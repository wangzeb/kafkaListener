package com.jyyb.kafkaservice.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix="oauth.pingfederate", ignoreUnknownFields = false)
@ConstructorBinding
@Getter
@AllArgsConstructor
public class PingFederateProperties {

    @NotBlank
    private final String url;

    private final String clientId; //from vault

    private final String clientSecret; //from vault

    private final String scope = "read";

    private final String grant_type="client_credential";

    private final RetryStrategy retry;
}
