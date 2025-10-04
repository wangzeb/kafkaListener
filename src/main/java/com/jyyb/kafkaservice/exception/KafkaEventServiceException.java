package com.jyyb.kafkaservice.exception;

import lombok.Getter;

@Getter
public class KafkaEventServiceException extends RuntimeException {

    public KafkaEventServiceException(String message){
        super(message);
    }

    public KafkaEventServiceException(String message, Throwable cause){
        super(message, cause);
    }
}
