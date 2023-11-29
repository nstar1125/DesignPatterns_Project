package com.team15.erp.dto.product;

import com.team15.erp.model.product.ProductVisitor;


public abstract class ProductDto {

    private String productType;
    private String productName;
    private Integer price;
    private String status;

    public ProductDto(
            final String productType,
            final String productName,
            final Integer price,
            final String status
    ) {
        this.productType = productType;
        this.productName = productName;
        this.price = price;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public abstract void accept(ProductVisitor visitor);
}
