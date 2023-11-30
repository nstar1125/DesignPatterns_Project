package com.team15.erp.dto.product;

import com.team15.erp.model.product.ProductVisitor;

import java.time.ZonedDateTime;

public class ShoesDto extends ProductDto {
    private Integer size;
    private String brand;

    public Integer getSize() {
        return size;
    }

    public String getBrand() {
        return brand;
    }

    public ShoesDto(
            final String productType,
            final String productName,
            final Integer price,
            final Integer size,
            final String brand,
            final ProductStatus status
    ) {
        super(productType, productName, price, status);
        this.size = size;
        this.brand = brand;
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }
}
