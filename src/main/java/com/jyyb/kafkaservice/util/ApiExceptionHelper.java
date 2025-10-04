package com.jyyb.kafkaservice.util;

import com.jyyb.kafkaservice.exception.HttpStatusAwareException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

import static com.jyyb.kafkaservice.util.Constants.Regex.SERVER_RESPONSE_HTTP_STATUS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiExceptionHelper {

    public static Optional<HttpStatus> getHttpStatus (Throwable e){
        if(e instanceof WebClientResponseException){
            return Optional.of(((WebClientResponseException) e).getStatusCode());
        }
        if(e instanceof HttpStatusAwareException){
            return Optional.of(((HttpStatusAwareException) e).getHttpStatus());
        }
        final String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);
        return StringUtil.extract(rootCauseMessage, SERVER_RESPONSE_HTTP_STATUS)
                .map(httStatusCode-> HttpStatus.valueOf(Integer.parseInt(httStatusCode)));
    }

    public static boolean isRetryable(Throwable e){
        return getHttpStatus(e)
                .map(ApiExceptionHelper::isRetryable)
                .orElse(true);
    }
    public static boolean isRetryable(HttpStatus statusCode){
        return statusCode.is5xxServerError()
                || statusCode == HttpStatus.PROXY_AUTHENTICATION_REQUIRED;
    }
}
