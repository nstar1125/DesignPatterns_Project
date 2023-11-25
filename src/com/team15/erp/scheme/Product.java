package com.team15.erp.scheme;

public abstract class Product {
    private String productName;
    private Integer price;

    public Product(String productName, Integer price) {
        this.productName = productName;
        this.price = price;
    }
}
