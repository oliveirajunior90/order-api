package com.system.ordercontrol.domain.entity;

import com.system.ordercontrol.domain.enums.OrderStatusEnum;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "customer_name", nullable = false)
  private String customerName;

  @Column(name = "customer_email", nullable = false)
  private String customerEmail;

  @Column(name = "total_price")
  private BigDecimal totalPrice;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatusEnum status = OrderStatusEnum.PENDING;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @CreationTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private Set<OrderItem> orderItems;

  public Order(UUID id, String customerName, String customerEmail, Set<OrderItem> items) {
    this.id = id;
    this.customerName = customerName;
    this.customerEmail = customerEmail;
    this.orderItems = items;
  }

  public Order() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(customerName, order.customerName)
        && Objects.equals(customerEmail, order.customerEmail)
        && Objects.equals(orderItems, order.orderItems);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public OrderStatusEnum getStatus() {
    return status;
  }

  public void setStatus(OrderStatusEnum status) {
    this.status = status;
  }

  public Set<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(Set<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}
