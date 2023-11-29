package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.model.Mapper;
import com.team15.erp.entity.product.Book;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BookMapper extends Mapper<Book> {

    public List<Book> selectByNameWriter(String productName, String writer) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from book where product_name = \""+productName+"\"").readOnlyCursor();

        return Arrays.stream(cursor.rows())
                .map(row -> map(row, cursor.columnNames()))
                .toList();
    }

    @Override
    protected Book map(final Object[] row, final String[] columnNames) {
        Book book = new Book();
        for (int i = 0; i < columnNames.length; i++) {
            switch (columnNames[i]) {
                case "id":
                    book.setId(Long.valueOf((String) row[i]));
                    break;
                case "product_name":
                    book.setProductName((String) row[i]);
                    break;
                case "price":
                    book.setPrice(Integer.parseInt((String) row[i]));
                    break;
                case "writer":
                    book.setWriter((String)row[i]);
                    break;
                case "number_of_page":
                    book.setNumberOfPage(Integer.parseInt((String) row[i]));
                    break;
                case "store_at":
                    book.setStoredAt(toZonedDateTime((String) row[i]));
                    break;
                case "release_at":
                    book.setReleasedAt(toZonedDateTime((String) row[i]));
                    break;
                case "status":
                    book.setStatus((String) row[i]);
                    break;
                default:
                    break;
            }
        }
        return book;
    }
}
