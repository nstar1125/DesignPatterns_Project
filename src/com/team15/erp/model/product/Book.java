package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.model.Mapper;
import java.io.IOException;
import java.util.ArrayList;

public class Book extends Mapper {

    public ArrayList<BookDto> getAllCustomers() throws IOException, ParseFailure {
        ArrayList<BookDto> bookDtos = new ArrayList<>();

        ReadOnlyCursor book = this.dbConnection.query("select distinct * from book").readOnlyCursor();

        for (Object[] row: book.rows()) {
            bookDtos.add((BookDto) map(row, book.columnNames()));
        }

        return bookDtos;
    }

    @Override
    protected Object map(final Object[] row, final String[] columnNames) {
        String id = "0";
        String productType = ProductType.BOOK.getProductType();
        String productName = NULL;
        String price = NULL;
        String writer = NULL;
        String numberOfPage = NULL;
        String storeAt = NULL;
        String releaseAt = NULL;
        String isSale = NULL;

        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals("id")) {
                id = (String) row[i];
            }
            if (columnNames[i].equals("product_name")) {
                productName = (String) row[i];
            }
            if (columnNames[i].equals("price")) {
                price = (String) row[i];
            }
            if (columnNames[i].equals("writer")) {
                writer = (String) row[i];
            }
            if (columnNames[i].equals("number_of_page")) {
                numberOfPage = (String) row[i];
            }
            if (columnNames[i].equals("store_at")) {
                storeAt = (String) row[i];
            }
            if (columnNames[i].equals("release_at")) {
                releaseAt = (String) row[i];
            }
            if (columnNames[i].equals("is_sale")) {
                isSale = (String) row[i];
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
                isSale
        );
    }
}
