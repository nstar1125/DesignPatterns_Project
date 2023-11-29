package com.team15.erp.model.product;

public abstract class Product {

    private String storedAt;
    private String productName;
    private String price;
    private String releasedAt;
    public String getStoredAt() {
        return storedAt;
    }

    public void setStoredAt(final String storedAt) {
        this.storedAt = storedAt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(final String releasedAt) {
        this.releasedAt = releasedAt;
    }
}
