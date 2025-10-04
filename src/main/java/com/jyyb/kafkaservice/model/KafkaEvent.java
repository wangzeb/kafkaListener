package com.jyyb.kafkaservice.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class KafkaEvent {

    @JsonProperty("eventAttributes")
    private EventAttributes eventAttributes;

    @JsonProperty("eventHeader")
    private EventHeader eventHeader;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EventAttributes {
        @NotNull
        @JsonProperty ("client_id")
        private String clientId;

    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EventHeader {
        @NotNull
        @JsonProperty ("event_id")
        private String eventId;

    }

}
