package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductStatus;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.model.Mapper;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Book extends Mapper {

    public int getNumberOfBooks() {
        try {
            ReadOnlyCursor book = this.dbConnection.query("select distinct * from book").readOnlyCursor();

            return book.rows().length;
        } catch (IOException ioException) {
            System.out.println(ioException);
        } catch (ParseFailure parseFailure) {
            System.out.println(parseFailure);
        }

        return 0;
    }

    public List<Object> selectAllByNameWriter(String productName, String writer) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from book " +
                "where product_name = \""+productName+"\"" +
                "and writer = \""+writer+"\"").readOnlyCursor();

        return Arrays.stream(cursor.rows())
                .map(row -> map(row, cursor.columnNames()))
                .collect(Collectors.toList());
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
                id = Long.valueOf((String) row[i]) ;
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
