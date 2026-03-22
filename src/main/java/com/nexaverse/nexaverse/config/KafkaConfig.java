package com.nexaverse.nexaverse.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic worldEventsTopic() {
        return TopicBuilder.name("world-events")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic avatarEventsTopic() {
        return TopicBuilder.name("avatar-events")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic chatEventsTopic() {
        return TopicBuilder.name("chat-events")
                .partitions(1)
                .replicas(1)
                .build();
    }
}