package com.team15.erp.dto.product;

public enum ProductStatus {

    SALE("SALE"),
    SOLD("SOLD");

    private String productStatus;

    ProductStatus(final String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductStatus() {
        return productStatus;
    }

}
