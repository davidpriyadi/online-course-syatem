package com.mini.project.order.payment.service.service;

import com.mini.project.order.payment.service.client.CourseClient;
import com.mini.project.order.payment.service.client.dto.UserCourseRequestDto;
import com.mini.project.order.payment.service.dto.OrderDto;
import com.mini.project.order.payment.service.dto.OrderRequest;
import com.mini.project.order.payment.service.dto.mapper.OrderMapper;
import com.mini.project.order.payment.service.entity.Orders;
import com.mini.project.order.payment.service.entity.PaymentLogs;
import com.mini.project.order.payment.service.repository.OrdersRepository;
import com.mini.project.order.payment.service.repository.PaymentLogsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final PaymentLogsRepository paymentLogRepository;
    private final OrderMapper orderMapper;
    private final CourseClient courseClient;

    public OrderDto createOrder(OrderRequest orderDto) {


        var order = Orders.builder()
                .userId(orderDto.getUser().getId())
                .courseId(orderDto.getCourse().getId())
                .build();

        ordersRepository.save(order);

        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", order.getId() + "-" + UUID.randomUUID().toString().substring(0, 5));
        transactionDetails.put("gross_amount", orderDto.getCourse().getPrice());

        Map<String, Object> itemDetails = new HashMap<>();
        itemDetails.put("id", orderDto.getCourse().getId());
        itemDetails.put("price", orderDto.getCourse().getPrice());
        itemDetails.put("quantity", 1);
        itemDetails.put("name", orderDto.getCourse().getName());
        itemDetails.put("brand", "ONLINECOURSE-JAYA");
        itemDetails.put("category", "Online Course");

        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("first_name", orderDto.getUser().getName());
        customerDetails.put("email", orderDto.getUser().getEmail());

        Map<String, Object> midtransParams = new HashMap<>();
        midtransParams.put("transaction_details", transactionDetails);
        midtransParams.put("item_details", itemDetails);
        midtransParams.put("customer_details", customerDetails);

        String snapUrl = getSnapUrl(midtransParams);
        order.setSnapUrl(snapUrl);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("course_id", orderDto.getCourse().getId());
        metadata.put("course_price", orderDto.getCourse().getPrice());
        metadata.put("course_name", orderDto.getCourse().getName());
        order.setMetadata(metadata);

        order.setStatus("pending");

        var result = ordersRepository.save(order);

        return orderMapper.toDto(result);
    }


    private String getSnapUrl(Map<String, Object> params) {
        return "https://app.coba.coab.com/snap/v1/transactions/" + UUID.randomUUID().toString();
    }

    public void handlerWebhook(Map<String, Object> data) {

        String signatureKey = (String) data.get("signature_key");
        String orderId = (String) data.get("order_id");
        String statusCode = (String) data.get("status_code");
        String grossAmount = (String) data.get("gross_amount");
        String transactionStatus = (String) data.get("transaction_status");
        String type = (String) data.get("payment_type");
        String fraudStatus = (String) data.get("fraud_status");


        String[] realOrderId = orderId.split("-");
        var order = ordersRepository.findById(Long.parseLong(realOrderId[0]))
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));


        if ("success".equals(order.getStatus())) {
            throw new ResponseStatusException(BAD_REQUEST, "Order already success");
        }

        updateOrderStatus(order, transactionStatus, fraudStatus);

        paymentLogRepository.save(PaymentLogs
                .builder()
                .order(order)
                .status(transactionStatus)
                .rawResponse(data)
                .paymentType(type)
                .build());

        var response = ordersRepository.save(order);

        if ("success".equals(response.getStatus())) {
            courseClient.createPremium(
                    UserCourseRequestDto
                            .builder()
                            .courseId(order.getCourseId())
                            .userId(order.getUserId())
                            .build()
            );
        }
    }


//    private String generateSignatureKey(String orderId, String statusCode, String grossAmount, String serverKey) {
//        try {
//            String input = orderId + statusCode + grossAmount + serverKey;
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hash) {
//                sb.append(String.format("%02x", b));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error generating signature key", e);
//        }
//    }

    private void updateOrderStatus(Orders order, String transactionStatus, String fraudStatus) {
        switch (transactionStatus) {
            case "capture":
                if ("challenge".equals(fraudStatus)) {
                    order.setStatus("challenge");
                } else if ("accept".equals(fraudStatus)) {
                    order.setStatus("success");
                }
                break;
            case "settlement":
                order.setStatus("success");
                break;
            case "cancel":
            case "deny":
            case "expire":
                order.setStatus("failure");
                break;
            case "pending":
                order.setStatus("pending");
                break;
        }
    }
}
