package com.team15.erp.dto.product;

public abstract class ProductDto {

    private String id;
    private String productType;
    private String productName;
    private String price;
    private String storeAt;
    private String releaseAt;
    private String isSale;

    public ProductDto(
            final String id,
            final String productType,
            final String productName,
            final String price,
            final String storeAt,
            final String releaseAt,
            final String isSale
    ) {
        this.id = id;
        this.productType = productType;
        this.productName = productName;
        this.price = price;
        this.storeAt = storeAt;
        this.releaseAt = releaseAt;
        this.isSale = isSale;
    }

    public String getId() {
        return id;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getStoreAt() {
        return storeAt;
    }

    public String getReleaseAt() {
        return releaseAt;
    }

    public String getIsSale() {
        return isSale;
    }
}
