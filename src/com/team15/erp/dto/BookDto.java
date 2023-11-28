package com.team15.erp.dto;

public class BookDto extends ProductDto {
    private String writer;
    private Integer numberOfPage;
    public BookDto(String productName, Integer price, String writer, Integer numberOfPage) {
        super(productName, price);
        this.writer = writer;
        this.numberOfPage = numberOfPage;
    }
}
