package com.team15.erp.dto.product;

import javax.swing.Spring;

public enum ProductType {

    BOOK("BOOK"),
    SHOES("SHOES");

    private String productType;

    ProductType(final String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }
}
