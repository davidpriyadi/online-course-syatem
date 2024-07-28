package com.mini.project.order.payment.service.repository;

import com.mini.project.order.payment.service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}