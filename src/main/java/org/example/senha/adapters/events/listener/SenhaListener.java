package org.example.senha.adapters.events.listener;

import br.com.PoC.ComandoSenha;
import lombok.RequiredArgsConstructor;
import org.example.senha.adapters.events.mapper.MessageHeaderTranslator;
import org.example.senha.adapters.events.mapper.SenhaMapper;
import org.example.senha.adapters.events.producer.SenhaProducer;
import org.example.senha.application.service.SenhaService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "${senha.kafka.consumer.topic-processado}", containerFactory = "senhaListenerContainerFactoryBean")
public class  SenhaListener {

    private final SenhaService senhaService;
    private final SenhaProducer senhaProducer;
    private final SenhaMapper senhaMapper;
    private final MessageHeaderTranslator messageHeaderTranslator;

    @KafkaHandler
    public void transferenciaSolicitada(ComandoSenha comandoSenha, @Headers MessageHeaders messageHeaders, Acknowledgment ack) throws IOException {

        System.out.println("Evento Consumido.");

        senhaService.execute(senhaMapper.map(comandoSenha));

        senhaProducer.produce(messageHeaderTranslator.translate(messageHeaders));
        ack.acknowledge();
    }
}
