package com.team15.erp.dto.product;

import javax.swing.Spring;

public enum ProductType {

    BOOK("책"),
    SHOES("신발");

    private String productType;

    ProductType(final String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
