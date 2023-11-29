package com.team15.erp.dto.product;

import com.team15.erp.model.product.ProductVisitor;

import java.time.ZonedDateTime;

public class BookDto extends ProductDto {
    private String writer;
    private Integer numberOfPage;

    public String getWriter() {
        return writer;
    }

    public Integer getNumberOfPage() {
        return numberOfPage;
    }

    public BookDto(
            final Long id,
            final String productType,
            final String productName,
            final Integer price,
            final String writer,
            final Integer numberOfPage,
            final ZonedDateTime storeAt,
            final ZonedDateTime releaseAt,
            final String status
    ) {
        super(id, productType, productName, price, storeAt, releaseAt, status);
        this.writer = writer;
        this.numberOfPage = numberOfPage;
    }

    @Override
    public String toString() {
        return String.format("[%s] id : %d, title : %s, price : %d, writer : %s, totalPage : %d",
                getProductType(),
                getId(),
                getProductName(),
                getPrice(),
                writer,
                numberOfPage
        );
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }
}
