package com.system.ordercontrol.application.service;

import java.util.UUID;

import com.system.ordercontrol.infraestructure.kafka.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationCreateOrderService {

  private final @Qualifier("notificationService") NotificationService<String, byte[]>
      notificationService;

  public NotificationCreateOrderService(NotificationService<String, byte[]> notificationService) {
    this.notificationService = notificationService;
  }

  public void notify(UUID orderId, byte[] orderByteArray) {
    notificationService.sendMessage(orderId.toString(), orderByteArray);
  }
}
