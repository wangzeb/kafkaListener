package com.jyyb.kafkaservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HerokuPayload {

    @JsonProperty("CLIENT_ID")
    private String clientId;

    @JsonProperty("activity_datetime")
    private String activityDateTime;
}
