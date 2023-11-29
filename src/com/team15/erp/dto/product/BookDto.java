package com.team15.erp.dto.product;

import java.time.ZonedDateTime;

public class BookDto extends ProductDto {
    private String writer;
    private Integer numberOfPage;

    public BookDto(
            final String productType,
            final String productName,
            final Integer price,
            final String writer,
            final Integer numberOfPage,
            final String status
    ) {
        super(productType, productName, price, status);
        this.writer = writer;
        this.numberOfPage = numberOfPage;
    }
}
