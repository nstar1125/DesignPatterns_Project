package com.team15.erp.dto.product;

public class ShoesDto extends ProductDto {
    private String size;
    private String brand;

    public ShoesDto(
            final String id,
            final String productType,
            final String productName,
            final String price,
            final String size,
            final String brand,
            final String storeAt,
            final String releaseAt,
            final String isSale
    ) {
        super(id, productType, productName, price, storeAt, releaseAt, isSale);
        this.size = size;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return String.format("[%s] id : %s, name : %s, price : %s, size : %s, brand : %s",
                getProductType(),
                getId(),
                getProductName(),
                getPrice(),
                size,
                brand
        );
    }
}
