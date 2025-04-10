package com.system.ordercontrol.infraestructure.config;

import com.system.ordercontrol.infraestructure.kafka.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@Configuration
public class NotificationServiceConfiguration {


  @Bean
  NotificationService<String, byte[]> notificationService(
      @Qualifier("notificationKafkaTemplate") KafkaTemplate<String, byte[]> kafkaTemplate,
      @Value("${spring.kafka.topics.stock-control}") String topicName) {
    return new NotificationService<>(kafkaTemplate, topicName);
  }
}
