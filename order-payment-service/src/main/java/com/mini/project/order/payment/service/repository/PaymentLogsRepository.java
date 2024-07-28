package com.mini.project.order.payment.service.repository;

import com.mini.project.order.payment.service.entity.PaymentLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLogsRepository extends JpaRepository<PaymentLogs, Long> {
}