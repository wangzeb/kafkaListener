package com.jyyb.kafkaservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ApiObjectMapper {

    private final ObjectMapper objectMapper;

    @SneakyThrows(JsonProcessingException.class)
    @SuppressWarnings("unchecked")
    public Map<String,Object> readValue(final String value){
        return objectMapper.readValue(value, Map.class);
    }

    @SneakyThrows(JsonProcessingException.class)
    @SuppressWarnings("unchecked")
    public <T> T readValue(final String value, Class<T> type){
        return objectMapper.readValue(value, type);
    }

    @SneakyThrows(JsonProcessingException.class)
    public String writeValueAsString (final Object value){
        return objectMapper.writeValueAsString(value);
    }
}
