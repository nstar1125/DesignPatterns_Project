package com.team15.erp.entity.product;

import com.team15.erp.dto.product.ProductType;

import java.time.ZonedDateTime;

public abstract class Product {

    private Long id;
    private ProductType productType;
    private String productName;
    private Integer price;
    private ZonedDateTime storedAt;
    private ZonedDateTime releasedAt;
    private String status;

    public abstract void accept(ProductVisitor visitor);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ZonedDateTime getStoredAt() {
        return storedAt;
    }

    public ZonedDateTime getReleasedAt() {
        return releasedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
