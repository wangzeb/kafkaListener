package com.jyyb.kafkaservice.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class AuthToken {

    private AccessTokenInfo tokenInfo;
    private Instant expiresAt;

    public AuthToken(AccessTokenInfo accessTokenInfo){
        this.tokenInfo = accessTokenInfo;
        this.expiresAt= Optional.ofNullable(tokenInfo)
                .map(AccessTokenInfo::getExpiresIn)
                .map(exp -> Instant.now().plusSeconds(exp))
                .orElse(null);
    }

    public boolean isExpired(){
        return Optional.ofNullable(this.expiresAt)
                .map(exp->Instant.now().isAfter(exp))
                .orElse(true);
    }

    public boolean isNotExpired(){
        return !isExpired();
    }
}
