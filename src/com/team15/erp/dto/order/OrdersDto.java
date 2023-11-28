package com.team15.erp.dto.order;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

public class OrdersDto {

    private Long id;
    private Long customerId;
    private ZonedDateTime orderDate;
    private List<HashMap<String, Long>> orderProductIds;
    private String orderStatus;

    public OrdersDto(
            final Long id,
            final Long customerId,
            final ZonedDateTime orderDate,
            final List<HashMap<String, Long>> orderProductIds,
            final String orderStatus
    ) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderProductIds = orderProductIds;
        this.orderStatus = orderStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public List<HashMap<String, Long>> getOrderProductIds() {
        return orderProductIds;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
