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
            final String productType,
            final String productName,
            final Integer price,
            final String writer,
            final Integer numberOfPage,
            final ProductStatus status
    ) {
        super(productType, productName, price, status);
        this.writer = writer;
        this.numberOfPage = numberOfPage;
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }
}
