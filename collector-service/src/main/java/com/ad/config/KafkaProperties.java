package com.ad.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "spring.kafka")
@Component
public class KafkaProperties {

    private boolean enabled = true;
    private String bootstrapServers = "localhost:9092";

    private Consumer consumer = new Consumer();
    private Producer producer = new Producer();
    private Map<String, String> topics = new HashMap<>();

    @Data
    public static class Consumer {
        private String groupId = "collector-group";
        private String autoOffsetReset = "earliest";
        private Integer maxPollRecords = 500;
        private Boolean enableAutoCommit = false;
    }

    @Data
    public static class Producer {
        private String acks = "all";
        private Integer retries = 3;
        private Integer batchSize = 16384;
        private Long bufferMemory = 33554432L;
    }

    public String getTopicForCollector(String collectorType) {
        return topics.getOrDefault(collectorType, topics.getOrDefault("default", "collector-topic"));
    }
}
