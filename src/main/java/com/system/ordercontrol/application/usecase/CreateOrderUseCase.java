package com.system.ordercontrol.application.usecase;

import com.system.ordercontrol.application.dto.CreateOrderDTO;
import com.system.ordercontrol.application.service.NotificationCreateOrderService;
import com.system.ordercontrol.application.service.OrderService;
import com.system.ordercontrol.domain.entity.Order;
import org.springframework.stereotype.Component;
import project.commerce.proto.OrderOuterClass;

import java.util.stream.Collectors;

@Component
public class CreateOrderUseCase {

    OrderService orderService;
    NotificationCreateOrderService notificationCreateOrderService;

    CreateOrderUseCase(OrderService orderService, NotificationCreateOrderService notificationCreateOrderService) {
        this.orderService = orderService;
        this.notificationCreateOrderService = notificationCreateOrderService;
    }

    public void execute(CreateOrderDTO dto) {
        try {

            Order order = dto.toOrder();

            OrderOuterClass.Order orderProto = OrderOuterClass.Order.newBuilder()
                    .setId(order.getId().toString())
                    .setCustomerName(order.getCustomerName())
                    .setCustomerEmail(order.getCustomerEmail())
                    .setStatus(OrderOuterClass.OrderStatusEnum.valueOf(order.getStatus().name()))
                    .addAllOrderItems(order.getOrderItems().stream()
                            .map(item -> OrderOuterClass.OrderItem.newBuilder()
                                    .setProductId(item.getProductId())
                                    .setQuantity(item.getQuantity())
                                    .setPrice(item.getPrice().toString())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();

            byte[] orderByteArray = orderProto.toByteArray();
            notificationCreateOrderService.notify(dto.toOrder().getId(), orderByteArray);

        } catch (Exception e) {
            throw new RuntimeException("nao foi possivel salvar nada" + e);
        }
    }

}
