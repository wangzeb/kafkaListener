package com.jyyb.kafkaservice.util;

import com.jyyb.kafkaservice.exception.KafkaEventServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventMessageMapper {

    private final ApiObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    public <T> T map(Object payload, Class<T> targetType) {
        if (targetType.isInstance(payload)) {
            return (T) payload;
        }
        final String sourceJsonString = payload.toString();
        try {
            return objectMapper.readValue(sourceJsonString, targetType);
        } catch (Exception e) {
            throw new KafkaEventServiceException(String.format("failed to map %s from payload: %s", targetType.getSimpleName(), sourceJsonString));
        }
    }
}
