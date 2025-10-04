package com.jyyb.kafkaservice.funtions;

import com.jyyb.kafkaservice.model.KafkaEvent;
import com.jyyb.kafkaservice.service.KafkaEventService;
import com.jyyb.kafkaservice.util.EventMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component("eventConsumer")
@RequiredArgsConstructor
public class KafkaEventConsumer implements Consumer<Message<KafkaEvent>> {

    private final KafkaEventService kafkaEventService;
    private final EventMessageMapper mapper;

    @Override
    public void accept(Message<KafkaEvent> message){
        final MessageHeaders headers = message.getHeaders();
        log.info("event id: {}", headers.getId());

        try{
            final KafkaEvent kafkaEvent = mapper.map(message.getPayload(), KafkaEvent.class);
            final String clientId=kafkaEvent.getEventAttributes().getClientId();
            kafkaEventService.perform(clientId);

        }catch (Exception e){
            log.info("failed to consumer event", e);
        }
    }
}
