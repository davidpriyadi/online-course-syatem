package com.mini.project.order.payment.service.dto.mapper;

import com.mini.project.order.payment.service.dto.OrderDto;
import com.mini.project.order.payment.service.dto.OrderRequest;
import com.mini.project.order.payment.service.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Orders orders);
    Orders toCreateEntity(OrderRequest orderRequest);
}
