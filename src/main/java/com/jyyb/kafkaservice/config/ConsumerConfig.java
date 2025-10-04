package com.jyyb.kafkaservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
@Configuration
@RequiredArgsConstructor
@Slf4j
public class ConsumerConfig {
    @Value("${spring.cloud.stream.kafka.binder.configuration.sasl.kerberos.realm}")
    private String realm;
    @Value("${spring.cloud.stream.kafka.binder.configuration.sasl.kerberos.kdc")
    private String kdc;

    @PostConstruct
    protected void postConstruct(){
        System.setProperty("java.security.krb5.realm", realm);
        System.setProperty("java.security.krb5.kdc", kdc);
    }
}
