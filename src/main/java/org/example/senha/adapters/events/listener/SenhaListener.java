package org.example.senha.adapters.events.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.senha.adapters.events.entity.Request;
import org.example.senha.adapters.events.entity.Response;
import org.example.senha.adapters.events.mapper.MessageHeaderTranslator;
import org.example.senha.adapters.events.producer.SenhaProducer;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${senha.kafka.consumer.topic-processado}", containerFactory = "senhaListenerContainerFactoryBean")
public class SenhaListener {

    private final SenhaProducer senhaProducer;
    private final MessageHeaderTranslator messageHeaderTranslator;

    @KafkaHandler
    public void transferenciaSolicitada(@Payload String payload, @Headers MessageHeaders messageHeaders, Acknowledgment ack) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Iniciando transferencia.");
        String payload1 = payload.replaceAll("\\\\","").replaceFirst("\"","");
        Request request = mapper.readValue(payload1.substring(0, payload1.length()-1), Request.class);

        Response response = new Response();
        response.setId(request.getId());
        response.setMessage("Message " + request.getMessageIndex());
        response.setOrigin(request.getOrigin());

        System.out.println("Objeto convertido: " + response);

        senhaProducer.produce(response, messageHeaderTranslator.translate(messageHeaders));
        ack.acknowledge();
    }
}
