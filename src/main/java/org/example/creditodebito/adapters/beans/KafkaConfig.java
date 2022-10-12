package org.example.creditodebito.adapters.beans;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.listener.concurrency}")
    private String concurrency;

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapAddress;

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, GenericRecord>> creditoDebitoListenerContainerFactoryBean(
            ConsumerFactory<String, GenericRecord> consumerFactory){
        final Map<String, Object> configurations = ((DefaultKafkaConsumerFactory<String, GenericRecord>) consumerFactory)
                .getConfigurationProperties();
        final Map<String, Object> mapaConfiguration = new HashMap<>(configurations);

        DefaultKafkaConsumerFactory<String, GenericRecord> consumerFactoryEdit = new DefaultKafkaConsumerFactory<>(mapaConfiguration);

        ConcurrentKafkaListenerContainerFactory<String, GenericRecord> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactoryEdit);
        //containerFactory.setErrorHandler(errorHandler);
        containerFactory.setConcurrency(Integer.parseInt(concurrency));
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return containerFactory;
    }

    @Bean
    ConsumerFactory<String, GenericRecord> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "transferencia");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081/");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    KafkaTemplate<String, GenericRecord> getKafkaTemplate(ProducerFactory<String, GenericRecord> producerFactory){
        KafkaTemplate<String, GenericRecord> template = new KafkaTemplate<>(producerFactory);
        template.setDefaultTopic("transferencia-concluida");
        return template;
    }

    @Bean
    ProducerFactory<String, GenericRecord> producerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081/");
        return new DefaultKafkaProducerFactory<>(props);
    }
}
