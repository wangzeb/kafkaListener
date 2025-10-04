package com.jyyb.kafkaservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class HttpStatusAwareException extends KafkaEventServiceException {

    private final HttpStatus httpStatus;

    public HttpStatusAwareException(final HttpStatus httpStatus, final String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatusAwareException(final HttpStatus httpStatus, final String message, final Throwable cause){
        super(message, cause);
        this.httpStatus = httpStatus;
    }
}
