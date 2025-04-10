package com.system.ordercontrol.infraestructure.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  @Value("${spring.kafka.producer.key-serializer}")
  private String keyDeserializer;

  @Value("${spring.kafka.producer.value-serializer}")
  private String valueDeserializer;

  @Bean
  public ProducerFactory<String, byte[]> producerFactory() {

    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyDeserializer);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueDeserializer);

    return new DefaultKafkaProducerFactory<>(configProps);
  }


  @Bean
  public KafkaTemplate<String, byte[]> notificationKafkaTemplate(
      @Qualifier("producerFactory") ProducerFactory<String, byte[]> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }
}
