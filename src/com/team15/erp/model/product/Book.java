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

public class Book extends Mapper {

    public ArrayList<BookDto> getAllBooks() throws IOException, ParseFailure {
        ArrayList<BookDto> bookDtos = new ArrayList<>();

        this.dbConnection.initialize("/Users/sonmingyu/학교자료/설계패턴/HolubSQL/src/com/holub/database/Dbase");
        ReadOnlyCursor book = this.dbConnection.query("select distinct * from book").readOnlyCursor();

        for (Object[] row: book.rows()) {
            bookDtos.add((BookDto) map(row, book.columnNames()));
        }

        return bookDtos;
    }

    @Override
    protected Object map(final Object[] row, final String[] columnNames) {
        Long id = 0L;
        String productType = ProductType.BOOK.getProductType();
        String productName = NULL;
        Integer price = 0;
        String writer = NULL;
        Integer numberOfPage = 0;
        ZonedDateTime storeAt = null;
        ZonedDateTime releaseAt = null;
        String status = ProductStatus.SALE.getProductStatus();

        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals("id")) {
                id = (Long) row[i];
            }
            if (columnNames[i].equals("product_name")) {
                productName = (String) row[i];
            }
            if (columnNames[i].equals("price")) {
                price = (Integer) row[i];
            }
            if (columnNames[i].equals("writer")) {
                writer = (String) row[i];
            }
            if (columnNames[i].equals("number_of_page")) {
                numberOfPage = (Integer) row[i];
            }
            if (columnNames[i].equals("store_at")) {
                storeAt = (ZonedDateTime) row[i];
            }
            if (columnNames[i].equals("release_at")) {
                releaseAt = (ZonedDateTime) row[i];
            }
            if (columnNames[i].equals("status")) {
                status = (String) row[i];
            }
        }

        return new BookDto(
                id,
                productType,
                productName,
                price,
                writer,
                numberOfPage,
                storeAt,
                releaseAt,
                status
        );
    }
}
