package com.jyyb.kafkaservice.funtions;

import com.jyyb.kafkaservice.model.KafkaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class kafkaEventConsumerBatch implements Consumer<List<Message<KafkaEvent>>> {
    private final ExecutorService executorService;

    @Override
    public void accept(List<Message<KafkaEvent>> messages) {
//        messages.stream().forEach(
//                message -> CompletableFuture.runAsync(
//                        () -> externalRestCall.call(message), executorService
//                )
//
//        );
    }

    //externalRestCall.call could use DoOnSuccesss to get call response then process response.
}
