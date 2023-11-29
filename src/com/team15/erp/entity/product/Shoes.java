package com.team15.erp.entity.product;

public class Shoes extends Product {

    private Integer size;
    private String brand;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visitShoes(this);
    }
}
