package org.example.creditodebito.adapters.events.producer;

import br.com.PoC.CreditoProcessado;
import br.com.PoC.DebitoProcessado;
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
public class CreditoDebitoProducerImpl implements CreditoDebitoProducer {

    private final KafkaTemplate<String, GenericRecord> kafkaTemplate;

    @Override
    public void produceCredito(MessageHeaders headers) throws JsonProcessingException {

        ProducerRecord<String, GenericRecord> message = new ProducerRecord<>("credito-processado", UUID.randomUUID().toString(), CreditoProcessado.newBuilder()
                .setCodigo("200")
                .setMensagem("Credito Efetuado.")
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

    @Override
    public void produceDebito(MessageHeaders headers) throws JsonProcessingException {

        ProducerRecord<String, GenericRecord> message = new ProducerRecord<>("debito-processado", UUID.randomUUID().toString(), DebitoProcessado.newBuilder()
                .setCodigo("200")
                .setMensagem("Debito Efetuado.")
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
