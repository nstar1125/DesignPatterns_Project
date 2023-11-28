package com.team15.erp.scheme.order;

import java.util.HashMap;
import java.util.List;

public class Order {

    private String id;
    private String customerId;
    private String orderDate;
    private String orderStatus;
    private List<HashMap<String, String>> orderProductIds;

    public Order(
            final String id,
            final String customerId,
            final String orderDate,
            final List<HashMap<String, String>> orderProductIds
    ) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderStatus = OrderStatus.ORDER.getOrderStatus();
        this.orderProductIds = orderProductIds;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<HashMap<String, String>> getOrderProductIds() {
        return orderProductIds;
    }

    public void setOrderProductIds(final List<HashMap<String, String>> orderProductIds) {
        this.orderProductIds = orderProductIds;
    }
}
