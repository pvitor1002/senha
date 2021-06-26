package org.example.senha.adapters.events.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.senha.adapters.events.entity.Request;
import org.example.senha.adapters.events.entity.Response;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SenhaProducerImpl implements SenhaProducer{

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void produce(Response response, MessageHeaders headers) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        String event = mapper.writeValueAsString(response);
        ProducerRecord<String, String> message = new ProducerRecord<>("comando-atualizar", UUID.randomUUID().toString(), event);

        message.headers().add("type", "senha_validada".getBytes());
        message.headers().add("replyChannel", headers.get("replyChannel").toString().getBytes());
        message.headers().add("errorChannel", headers.get("errorChannel").toString().getBytes());
        message.headers().add("instanceId", headers.get("instanceId").toString().getBytes());
        kafkaTemplate.send(message).addCallback(
                successCallback -> {
                    System.out.println("Mensagem enviada com sucesso!");
                },
                failureCallback -> {
                    System.err.println("Erro ao enviar mensagem!");
                });
    }
}
