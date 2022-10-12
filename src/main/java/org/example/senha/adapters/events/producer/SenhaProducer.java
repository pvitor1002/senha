package org.example.senha.adapters.events.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.messaging.MessageHeaders;

public interface SenhaProducer {
    void produce(MessageHeaders headers) throws JsonProcessingException;
}
