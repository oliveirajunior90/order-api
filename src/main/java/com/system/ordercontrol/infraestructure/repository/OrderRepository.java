package com.system.ordercontrol.infraestructure.repository;

import com.system.ordercontrol.domain.entity.Order;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, UUID> {

  @NotNull
  @Override
  Order save(@NotNull Order order);

  @Override
  Optional<Order> findById(UUID id);

  @Override
  void deleteById(UUID id);

  @Override
  Iterable<Order> findAll();
}
