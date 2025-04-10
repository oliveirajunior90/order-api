package com.system.ordercontrol.order;

import static org.mockito.Mockito.*;

import com.system.ordercontrol.application.dto.CreateOrderDTO;
import com.system.ordercontrol.application.service.OrderService;
import com.system.ordercontrol.domain.entity.OrderItem;
import com.system.ordercontrol.infraestructure.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

  @Mock private OrderRepository orderRepository;

  @InjectMocks private OrderService orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private CreateOrderDTO createOrderDtoWithIngredients() {

    Set<OrderItem> items = new HashSet<>();
    items.add(new OrderItem(1L, new BigDecimal("2.0"), 3));
    items.add(new OrderItem(2L, new BigDecimal("3.0"), 2));

    var orderDto = new CreateOrderDTO("John Doe", "teste@teste.com", items);

    when(orderRepository.save(orderDto.toOrder())).thenReturn(orderDto.toOrder());

    return orderDto;
  }

  @Test
  void createOrderWhenHasIngredients() throws Exception {
    var orderDto = createOrderDtoWithIngredients();
    orderService.create(orderDto);
    verify(orderRepository).save(orderDto.toOrder());
  }
}
