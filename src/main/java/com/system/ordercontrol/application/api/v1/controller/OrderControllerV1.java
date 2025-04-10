package com.system.ordercontrol.application.api.v1.controller;

import com.system.ordercontrol.application.dto.CreateOrderDTO;
import com.system.ordercontrol.application.service.OrderService;
import com.system.ordercontrol.application.usecase.CreateOrderUseCase;
import com.system.ordercontrol.domain.entity.Order;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderControllerV1 {
  OrderService orderService;
  CreateOrderUseCase createOrderUseCase;

  public OrderControllerV1(OrderService orderService, CreateOrderUseCase createOrderUseCase) {
    this.orderService = orderService;
    this.createOrderUseCase = createOrderUseCase;
  }

  @PostMapping
  public ResponseEntity<HttpStatus> create(@RequestBody @Validated CreateOrderDTO dto) {
    createOrderUseCase.execute(dto);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<Order>> getOrder(@PathVariable UUID id) {
    return ResponseEntity.ok(orderService.getOrder(id));
  }

  @GetMapping
  public ResponseEntity<Iterable<Order>> getOrders() {
    return ResponseEntity.ok(orderService.getOrders());
  }
}
