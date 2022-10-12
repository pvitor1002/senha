package org.example.creditodebito.adapters.events.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.messaging.MessageHeaders;

public interface CreditoDebitoProducer {
    void produceCredito(MessageHeaders headers) throws JsonProcessingException;
    void produceDebito(MessageHeaders headers) throws JsonProcessingException;
}
