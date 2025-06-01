package com.system.ordercontrol.infraestructure.kafka;

import java.util.List;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class NotificationService<K, M> {

  private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

  private final KafkaTemplate<K, M> kafkaTemplate;
  private final String topicName;

  public NotificationService(KafkaTemplate<K, M> kafkaTemplate, String topicName) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicName = topicName;
  }

  public void sendMessage(K key, M message, List<Header> headers) {
    kafkaTemplate
        .send(new ProducerRecord<>(topicName, null, key, message, headers))
        .whenComplete(
            (result, ex) -> {
              if (ex == null) {
                log.info("Sent [key={}, message={}, headers={}]", key, message, headers);
              } else {
                log.info(
                    "Unable to sent [key={}, message={}, headers={}] due to {}",
                    key,
                    message,
                    headers,
                    ex.getMessage());
              }
            });
  }

  public void sendMessage(K key, M message) {
    kafkaTemplate
        .send(topicName, key, message)
        .whenComplete(
            (result, ex) -> {
              if (ex == null) {
                log.info("Sent [key={}, message={}]", key, message);
              } else {
                log.info(
                    "Unable to sent [key={}, message={}] due to {}", key, message, ex.getMessage());
              }
            });
  }

  public void sendMessage(K key) {
    kafkaTemplate
        .send(topicName, key, null)
        .whenComplete(
            (result, ex) -> {
              if (ex == null) {
                log.info("Sent [key={}] without message", key);
              } else {
                log.info("Unable to sent [key={}] without message due to {}", key, ex.getMessage());
              }
            });
  }
}
