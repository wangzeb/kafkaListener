package com.jyyb.kafkaservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@Slf4j
@Component
public class KeytabTruststoreGenerator {

    @Value("${spring.cloud.stream.kafka.binder.jaas.options.keyTabEncoded}")
    private String keyTabEncoded;

    @Value("${spring.cloud.stream.kafka.binder.jaas.options.keyTab}")
    private String keytabLocation;

    @Value("${spring.cloud.stream.kafka.binder.configuration.ssl.truststore.encoded}")
    private String truststoreEncoded;

    @Value("${spring.cloud.stream.kafka.binder.configuration.ssl.truststore.location}")
    private String jksLocation;

    @PostConstruct
    public void run() throws IOException {
        generate(keyTabEncoded, keytabLocation);
        if(!truststoreEncoded.isBlank()){
            generate(truststoreEncoded, jksLocation);
        }
    }

    private void generate(String encoded,String fileLocation) throws IOException{
        final Path file = Paths.get(fileLocation);
        Files.write(file, Base64.getMimeDecoder().decode(encoded), WRITE, CREATE);
    }
}
