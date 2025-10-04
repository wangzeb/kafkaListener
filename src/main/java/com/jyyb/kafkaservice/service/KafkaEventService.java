package com.jyyb.kafkaservice.service;

import com.jyyb.kafkaservice.model.HerokuPayload;
import com.jyyb.kafkaservice.service.client.HerokuClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.ZonedDateTime;

import static com.jyyb.kafkaservice.util.DateTimeUtil.HEROKU_DATE_TIME;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventService {

    private final HerokuClient herokuClient;

    public void perform(String clientId) {
        final HerokuPayload herokuPayload = buildPayload(clientId);
        final String reponse = herokuClient.herokuCall(herokuPayload,"status_change");
        log.info("response: {}", reponse);
    }

    private HerokuPayload buildPayload(String clientId) {
        final String hashedClientId = DigestUtils.sha1Hex(StringUtils.rightPad(clientId, 20, "")).toUpperCase();
        return HerokuPayload.builder()
                .clientId(hashedClientId)
                .activityDateTime(ZonedDateTime.now().format(HEROKU_DATE_TIME)).
                build();
    }
}
