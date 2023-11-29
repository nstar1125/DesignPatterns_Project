package com.team15.erp.dto.order;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

public class OrdersDto {

    private Long customerId;
    private ZonedDateTime orderDate;
    private String productType;
    private String orderStatus;

    public OrdersDto(
            final Long customerId,
            final ZonedDateTime orderDate,
            final String productType,
            final String orderStatus
    ) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.productType = productType;
        this.orderStatus = orderStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public String getProductType() {
        return productType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        return String.format("CustomerId : %d, OrderDate : %s, ProductType : %s, OrderStatus : %s",
                customerId, orderDate, productType, orderStatus
        );
    }
}
