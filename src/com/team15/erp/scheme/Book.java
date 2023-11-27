package com.team15.erp.scheme;

public class Book extends Product {
    private String writer;
    private Integer numberOfPage;
    public Book(String productName, Integer price, String writer, Integer numberOfPage) {
        super(productName, price);
        this.writer = writer;
        this.numberOfPage = numberOfPage;
    }
}
