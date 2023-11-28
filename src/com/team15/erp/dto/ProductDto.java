package com.team15.erp.dto;

public abstract class ProductDto {
    private String productName;
    private Integer price;

    public ProductDto(String productName, Integer price) {
        this.productName = productName;
        this.price = price;
    }
}
