package com.team15.erp.scheme.order;

public enum OrderStatus {

    ORDER("ORDER"),
    DELIVERY("DELIVERY"),
    CANCEL("CANCEL"),
    DONE("DONE");

    private String orderStatus;

    OrderStatus(final String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
