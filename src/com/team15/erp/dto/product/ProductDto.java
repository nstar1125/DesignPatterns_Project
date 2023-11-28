package com.team15.erp.dto.product;

import java.time.ZonedDateTime;

public abstract class ProductDto {

    private Long id;
    private String productType;
    private String productName;
    private Integer price;
    private ZonedDateTime storeAt;
    private ZonedDateTime releaseAt;
    private String status;

    public ProductDto(
            final Long id,
            final String productType,
            final String productName,
            final Integer price,
            final ZonedDateTime storeAt,
            final ZonedDateTime releaseAt,
            final String status
    ) {
        this.id = id;
        this.productType = productType;
        this.productName = productName;
        this.price = price;
        this.storeAt = storeAt;
        this.releaseAt = releaseAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getPrice() {
        return price;
    }

    public ZonedDateTime getStoreAt() {
        return storeAt;
    }

    public ZonedDateTime getReleaseAt() {
        return releaseAt;
    }

    public String getIsSale() {
        return status;
    }
}
