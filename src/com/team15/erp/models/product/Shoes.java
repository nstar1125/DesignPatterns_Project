package com.team15.erp.models.product;

public class Shoes extends Product {

    private String size;
    private String brand;

    public String getSize() {
        return size;
    }

    public void setSize(final String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }
}
