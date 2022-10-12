package org.example.creditodebito.adapters.events.listener;

import br.com.PoC.ComandoCredito;
import br.com.PoC.ComandoDebito;
import lombok.RequiredArgsConstructor;
import org.example.creditodebito.adapters.events.mapper.DebitoMapper;
import org.example.creditodebito.adapters.events.mapper.MessageHeaderTranslator;
import org.example.creditodebito.adapters.events.mapper.CreditoMapper;
import org.example.creditodebito.adapters.events.producer.CreditoDebitoProducer;
import org.example.creditodebito.application.service.CreditoService;
import org.example.creditodebito.application.service.DebitoService;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@KafkaListener(topics = "#{'${creditodebito.kafka.consumer.topics}'.split(',')}", containerFactory = "creditoDebitoListenerContainerFactoryBean")
public class CreditoDebitoListener {

    private final CreditoService creditoService;
    private final CreditoDebitoProducer creditoDebitoProducer;
    private final CreditoMapper creditoMapper;
    private final DebitoService debitoService;
    private final DebitoMapper debitoMapper;
    private final MessageHeaderTranslator messageHeaderTranslator;

    @KafkaHandler
    public void transferenciaSolicitada(ComandoCredito comandoCredito, @Headers MessageHeaders messageHeaders, Acknowledgment ack) throws IOException {

        System.out.println("Evento Consumido.");

        creditoService.execute(creditoMapper.map(comandoCredito));

        creditoDebitoProducer.produceCredito(messageHeaderTranslator.translate(messageHeaders));
        ack.acknowledge();
    }

    @KafkaHandler
    public void transferenciaSolicitada(ComandoDebito comandoDebito, @Headers MessageHeaders messageHeaders, Acknowledgment ack) throws IOException {

        System.out.println("Evento Consumido.");

        debitoService.execute(debitoMapper.map(comandoDebito));

        creditoDebitoProducer.produceDebito(messageHeaderTranslator.translate(messageHeaders));
        ack.acknowledge();
    }
}
