package org.example.senha.adapters.events.producer;

import br.com.PoC.SenhaProcessada;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SenhaProducerImpl implements SenhaProducer{

    private final KafkaTemplate<String, GenericRecord> kafkaTemplate;

    @Override
    public void produce(MessageHeaders headers) throws JsonProcessingException {

        ProducerRecord<String, GenericRecord> message = new ProducerRecord<>("senha-processada", UUID.randomUUID().toString(), SenhaProcessada.newBuilder()
                .setCodigo("200")
                .setMensagem("Senha Validada.")
                .build()
        );

        message.headers().add("transaction_id", headers.get("transaction_id").toString().getBytes());
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
