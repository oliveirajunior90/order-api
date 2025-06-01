package com.system.ordercontrol.application.service;

import com.system.ordercontrol.application.dto.CreateOrderDTO;
import com.system.ordercontrol.domain.entity.Order;
import com.system.ordercontrol.domain.entity.OrderItem;
import com.system.ordercontrol.infraestructure.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

  OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public BigDecimal calculateTotalPrice(Set<OrderItem> orderItems) {
    return orderItems
            .stream()
            .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void create(CreateOrderDTO orderDto) {
    Order order = orderDto.toOrder();
    order.setTotalPrice(calculateTotalPrice(orderDto.items()));
    orderRepository.save(order);
  }

  public Optional<Order> getOrder(UUID id) {
    return Optional.empty();
  }

  public Iterable<Order> getOrders() {
    return orderRepository.findAll();
  }

}
