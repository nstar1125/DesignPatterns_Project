package com.team15.erp.dto.product;

import java.time.ZonedDateTime;

public class ShoesDto extends ProductDto {
    private Integer size;
    private String brand;

    public ShoesDto(
            final Long id,
            final String productType,
            final String productName,
            final Integer price,
            final Integer size,
            final String brand,
            final ZonedDateTime storeAt,
            final ZonedDateTime releaseAt,
            final String status
    ) {
        super(id, productType, productName, price, storeAt, releaseAt, status);
        this.size = size;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return String.format("[%s] id : %d, name : %s, price : %d, size : %d, brand : %s",
                getProductType(),
                getId(),
                getProductName(),
                getPrice(),
                size,
                brand
        );
    }
}
