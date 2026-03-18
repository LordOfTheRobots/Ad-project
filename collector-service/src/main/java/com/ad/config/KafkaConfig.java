package com.ad.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    private final JsonComponentLoader loader;

    @Bean
    @ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
    public ProducerFactory<String, Object> kafkaProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getProducer().getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getProducer().getBatchSize());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getProducer().getBufferMemory());
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(kafkaProducerFactory());
    }

    @Bean
    @ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Object> kafkaConsumerFactory() {
        JacksonJsonDeserializer<Object> deserializer = new JacksonJsonDeserializer<>();
        deserializer.addTrustedPackages("com.example.dto", "com.example.model");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }
}

