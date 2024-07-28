package com.mini.project.order.payment.service.controller;

import com.mini.project.order.payment.service.dto.OrderDto;
import com.mini.project.order.payment.service.dto.OrderRequest;
import com.mini.project.order.payment.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Validated OrderRequest orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }
}
