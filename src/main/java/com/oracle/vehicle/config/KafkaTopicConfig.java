package com.oracle.vehicle.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    private static final String CLEANUP_POLICY_CONFIG = "cleanup.policy";

    @Value("${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value("${kafka.vehicles.topic}")
    private String topicName;

    @Value("${kafka.vehicles.topic.cleanup.policy}")
    private String cleanupPolicy;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic vehiclesTopic() {
        Map<String, String> configs = new HashMap<>();
        configs.put(CLEANUP_POLICY_CONFIG, cleanupPolicy);

        return new NewTopic(topicName, 3, (short) 1).configs(configs);
    }

}
