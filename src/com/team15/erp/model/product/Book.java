package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductStatus;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.model.Mapper;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Book extends Mapper<BookDto> {

    public int getNumberOfBooks() {
        try {
            ArrayList<BookDto> bookDtos = new ArrayList<>();
            ReadOnlyCursor book = this.dbConnection.query("select distinct * from book").readOnlyCursor();

            for (Object[] row : book.rows()) {
                bookDtos.add(map(row, book.columnNames()));
            }

            return (int) bookDtos
                    .stream()
                    .filter(bookDto -> bookDto.getStatus().equals(ProductStatus.SALE))
                    .count();
        } catch (IOException | ParseFailure e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public List<Object> selectAllByNameWriter(String productName, String writer) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from book " +
                "where product_name = \"" + productName + "\"" +
                "and writer = \"" + writer + "\"").readOnlyCursor();

        return Arrays.stream(cursor.rows())
                .map(row -> map(row, cursor.columnNames()))
                .collect(Collectors.toList());
    }

    public void insert(BookDto book) throws IOException, ParseFailure {
        dbConnection.transaction(new ArrayList<>(List.of(String.format("" +
                "insert into Book(product_name, price, writer, number_of_page, store_at, release_at, status) VALUES ('%s', '%d', '%s', '%d', '%s', '%s', '%s')",
                book.getProductName(), book.getPrice(), book.getWriter(), book.getNumberOfPage(), getCurrentZonedDateTimeToString(), null, book.getStatus().name()
            )))
        );
    }

    @Override
    protected BookDto map(final Object[] row, final String[] columnNames) {
        Long id = 0L;
        String productType = ProductType.BOOK.getProductType();
        String productName = NULL;
        Integer price = 0;
        String writer = NULL;
        Integer numberOfPage = 0;
        ZonedDateTime storeAt = null;
        ZonedDateTime releaseAt = null;
        ProductStatus status = ProductStatus.SALE;

        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals("id")) {
                id = Long.valueOf((String) row[i]);
            }
            if (columnNames[i].equals("product_name")) {
                productName = (String) row[i];
            }
            if (columnNames[i].equals("price")) {
                price = Integer.parseInt((String) row[i]);
            }
            if (columnNames[i].equals("writer")) {
                writer = (String) row[i];
            }
            if (columnNames[i].equals("number_of_page")) {
                numberOfPage = Integer.parseInt((String) row[i]);
            }
            if (columnNames[i].equals("store_at")) {
                storeAt = toZonedDateTime((String) row[i]);
            }
            if (columnNames[i].equals("release_at")) {
                releaseAt = toZonedDateTime((String) row[i]);
            }
            if (columnNames[i].equals("status")) {
                status = ProductStatus.valueOf((String) row[i]);
            }
        }

        return new BookDto(
                productType,
                productName,
                price,
                writer,
                numberOfPage,
                status
        );
    }
}
