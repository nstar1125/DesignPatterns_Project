package com.team15.erp.dto.product;

import java.time.ZonedDateTime;

public class ShoesDto extends ProductDto {
    private Integer size;
    private String brand;

    public ShoesDto(
            final String productType,
            final String productName,
            final Integer price,
            final Integer size,
            final String brand,
            final String status
    ) {
        super(productType, productName, price, status);
        this.size = size;
        this.brand = brand;
    }
}
