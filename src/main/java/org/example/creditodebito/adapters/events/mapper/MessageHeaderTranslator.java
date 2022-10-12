package org.example.creditodebito.adapters.events.mapper;

import org.springframework.messaging.MessageHeaders;

public interface MessageHeaderTranslator {

    MessageHeaders translate(MessageHeaders headers);
}
