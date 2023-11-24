package com.holub.application.order;

import com.holub.application.product.Product;
import java.util.List;

public class Order {

    private String id;
    private String orderNumber;
    private List<String> orderProductIds;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(final String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<String> getOrderProductIds() {
        return orderProductIds;
    }

    public void setOrderProductIds(final List<String> orderProductIds) {
        this.orderProductIds = orderProductIds;
    }
}
