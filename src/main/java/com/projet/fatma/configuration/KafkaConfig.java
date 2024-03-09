package com.projet.fatma.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {


    @Bean
    public NewTopic taskTopic() {
        return TopicBuilder.name("project-description-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic LandUseTopic() {
        return TopicBuilder.name("land-use-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic ForestManagemnetTopic() {
        return TopicBuilder.name("forest-management-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic GrassLandTopic() {
        return TopicBuilder.name("grass-land-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}

