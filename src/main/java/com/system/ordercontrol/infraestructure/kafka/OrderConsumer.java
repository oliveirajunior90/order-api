package com.system.ordercontrol.infraestructure.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.system.ordercontrol.application.dto.CreateOrderDTO;
import com.system.ordercontrol.application.service.OrderService;
import com.system.ordercontrol.domain.entity.OrderItem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import project.commerce.proto.OrderOuterClass;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderConsumer {

    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    OrderService orderService;

    OrderConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.stock-control}")
    public void orderConsumer(ConsumerRecord<String, byte[]> record){

        if (record.value() == null || record.value().length == 0) {
            log.warn("Received empty message with key: {}", record.key());
            return;
        }

        try {
            UUID key = UUID.fromString(String.valueOf(record.key()));
            OrderOuterClass.Order orderProto = OrderOuterClass.Order.parseFrom(record.value());
            Set<OrderItem> orderItems = orderProto.getOrderItemsList().stream().map(orderIt -> {
                var item = orderIt.toBuilder();
                return new OrderItem(
                        item.getProductId(),
                        new BigDecimal(item.getPrice()),
                        item.getQuantity());
            }).collect(Collectors.toSet());

            CreateOrderDTO orderDto = new CreateOrderDTO(
                    orderProto.getCustomerName(),
                    orderProto.getCustomerEmail(),
                    orderItems
            );
            log.info("Created with key {} order: {}", key, orderDto);
            orderService.create(orderDto);
        } catch (Exception e) {
            log.info("Error to create message with key: {}", e.getMessage());
        }
    }
}
