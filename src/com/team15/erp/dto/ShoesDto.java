package com.team15.erp.dto;

public class ShoesDto extends ProductDto {
    Integer size;
    String brand;

    public ShoesDto(String productName, Integer price, Integer size, String brand) {
        super(productName, price);
        this.size = size;
        this.brand = brand;
    }
}
