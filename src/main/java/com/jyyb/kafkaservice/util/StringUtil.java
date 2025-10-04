package com.jyyb.kafkaservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.defaultString;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static Optional<String> extract(String text,String regex){
        final Matcher matcher = getMatcher(text, regex);
        if(matcher.find()){
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }

    private static Matcher getMatcher(String text, String regex){
        Objects.requireNonNull(regex, "regular ex is required");
        return Pattern.compile(regex).matcher(defaultString(text));
    }
}
