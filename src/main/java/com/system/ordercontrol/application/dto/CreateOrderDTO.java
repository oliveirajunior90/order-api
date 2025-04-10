package com.system.ordercontrol.application.dto;

import com.system.ordercontrol.domain.entity.Order;
import com.system.ordercontrol.domain.entity.OrderItem;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record CreateOrderDTO(
        @NotNull(message="Campo Obrigatório") String customerName,
        @Email String customerEmail,
        @NotEmpty Set<OrderItem> items
) {
  public Order toOrder() {
    return new Order(UUID.randomUUID(), customerName, customerEmail, items);
  }
}
