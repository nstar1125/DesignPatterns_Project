package com.team15.erp.dto.product;

public class BookDto extends ProductDto {
    private String writer;
    private String numberOfPage;

    public BookDto(
            final String id,
            final String productType,
            final String productName,
            final String price,
            final String writer,
            final String numberOfPage,
            final String storeAt,
            final String releaseAt,
            final String isSale
    ) {
        super(id, productType, productName, price, storeAt, releaseAt, isSale);
        this.writer = writer;
        this.numberOfPage = numberOfPage;
    }

    @Override
    public String toString() {
        return String.format("[%s] id : %s, title : %s, price : %s, writer : %s, totalPage : %s",
                getProductType(),
                getId(),
                getProductName(),
                getPrice(),
                writer,
                numberOfPage
        );
    }
}
