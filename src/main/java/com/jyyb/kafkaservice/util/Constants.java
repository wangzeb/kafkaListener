package com.jyyb.kafkaservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Regex {

        public static final String SERVER_RESPONSE_HTTP_STATUS = "HTTP response code: (.*?) (.*)";

    }
}
