package com.team15.erp.scheme;

public class Shoes extends Product {
    Integer size;
    String brand;

    public Shoes(String productName, Integer price, Integer size, String brand) {
        super(productName, price);
        this.size = size;
        this.brand = brand;
    }
}
