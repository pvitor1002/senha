package org.example.senha.adapters.events.mapper;

import lombok.RequiredArgsConstructor;
import org.example.senha.adapters.datastore.ContextDatastore;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageHeaderTranslatorImpl implements MessageHeaderTranslator {

    private final ContextDatastore contextDatastore;

    @Override
    public MessageHeaders translate(MessageHeaders headers){

        final Map<String, Object> headersConverted = new HashMap<>();

        headers.forEach((k, v) -> {
            try {
                headersConverted.put(k, new String((byte[]) v, StandardCharsets.UTF_8));
            }catch(Exception e){
                System.err.println("Objeto inválido");
            }
        });

        contextDatastore.setHeaders(headers);
        return new MessageHeaders(headersConverted);
    }
}
