package com.mini.project.order.payment.service.controller;


import com.mini.project.order.payment.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/webhook")
@RequiredArgsConstructor
public class WebHookController {

    private final OrderService orderService;

    @PostMapping
    private ResponseEntity<Void> webHook(@RequestBody Map<String, Object> response) {
        orderService.handlerWebhook(response);
        return ResponseEntity.ok().build();
    }
}
