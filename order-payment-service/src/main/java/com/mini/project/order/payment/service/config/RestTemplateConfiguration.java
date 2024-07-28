package com.mini.project.order.payment.service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    @Value("${http.outgoing.readTimeout}")
    private int readTimeout;

    @Value("${http.outgoing.connectionTimeout}")
    private int connectionTimeout;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .build();
    }
}