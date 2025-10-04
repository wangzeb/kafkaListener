package com.jyyb.kafkaservice.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConstructorBinding
@AllArgsConstructor
@Getter
public class RetryStrategy {
    private final int maxAttemps;
    private final double jitterFactor;
    private final Duration minInterval;
    private final Duration timeout;

}
