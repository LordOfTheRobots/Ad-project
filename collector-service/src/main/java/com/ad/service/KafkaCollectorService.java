package com.ad.service;

import com.ad.config.JsonComponentLoader;
import com.ad.config.KafkaProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class KafkaCollectorService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final KafkaProperties kafkaProperties;

    private final JsonComponentLoader loader;

    public void send(Object data) {
        String topic = kafkaProperties.getTopicForCollector(loader.getActiveType());
        kafkaTemplate.send(topic, UUID.randomUUID().toString(), data);
    }

    public void send(Object data, String key) {
        String topic = kafkaProperties.getTopicForCollector(loader.getActiveType());
        kafkaTemplate.send(topic, key, data);
    }

    public String getCurrentTopic() {
        return kafkaProperties.getTopicForCollector(loader.getActiveType());
    }
}
