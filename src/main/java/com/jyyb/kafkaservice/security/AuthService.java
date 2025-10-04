package com.jyyb.kafkaservice.security;

import com.jyyb.kafkaservice.config.WebClientConfig;
import com.jyyb.kafkaservice.config.properties.PingFederateProperties;
import com.jyyb.kafkaservice.config.properties.RetryStrategy;
import com.jyyb.kafkaservice.exception.KafkaEventServiceException;
import com.jyyb.kafkaservice.model.security.AccessTokenInfo;
import com.jyyb.kafkaservice.model.security.AuthToken;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PingFederateProperties pingFederateProperties;
    private final WebClient authTokenWebClient;

    private final ConcurrentMap<String, AuthToken> tokenCache = new ConcurrentHashMap<>();

    public AccessTokenInfo getToken(){

        final AuthToken currentToken = this.getTokenCache().getOrDefault(
                "ping_federate_token", new AuthToken()
        );
        if(currentToken.isNotExpired()){
            return currentToken.getTokenInfo();
        }
        final String url = pingFederateProperties.getUrl();;
        try{
            final AccessTokenInfo newToken = getNewToken(url);
            this.getTokenCache().put("ping_federate_token", new AuthToken(newToken));
            return newToken;
        }catch (Exception e){
            throw new KafkaEventServiceException("failed to get new token");
        }
    }

    private AccessTokenInfo getNewToken(String url){
        MultiValueMap<String, String> mapBody = constructTokenQuestBody();
        final RetryStrategy retry = pingFederateProperties.getRetry();
        return authTokenWebClient.mutate()
                .filter(WebClientConfig.errorHandlingFilter())
                .build()
                .post()
                .uri(url)
                .contentType(APPLICATION_FORM_URLENCODED)
                .bodyValue(mapBody)
                .retrieve()
                .bodyToMono(AccessTokenInfo.class)
                .timeout(retry.getTimeout())
                .block();
    }

    private MultiValueMap<String, String> constructTokenQuestBody(){
        final MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("CLIENT_ID", pingFederateProperties.getClientId());
        map.add("CLIENT_SECRET", pingFederateProperties.getClientSecret());
        map.add("GRANT_TYPE", pingFederateProperties.getGrant_type());
        map.add("SCOPE", pingFederateProperties.getScope());
        return map;
    }

}
