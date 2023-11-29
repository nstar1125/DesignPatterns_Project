package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.ProductStatus;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.dto.product.ShoesDto;
import com.team15.erp.model.Mapper;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shoes extends Mapper {

    public int getNumberOfShoes() {
        try {
            ReadOnlyCursor shoes = this.dbConnection.query("select distinct * from shoes").readOnlyCursor();

            return shoes.rows().length;
        } catch (IOException ioException) {
            System.out.println(ioException);
        } catch (ParseFailure parseFailure) {
            System.out.println(parseFailure);
        }

        return 0;
    }

    public List<Object> selectAllByNameBrandSize(String productName, String brand, Integer size) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from shoes " +
                "where product_name = \""+productName+"\" "+
                "and brand = \""+brand+"\" "+
                "and size = "+size).readOnlyCursor();

        return Arrays.stream(cursor.rows())
                .map(row -> map(row, cursor.columnNames()))
                .collect(Collectors.toList());
    }

    public Object selectByNameBrand(String productName, String brand) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from shoes " +
                "where product_name = \""+productName+"\""+
                "and brand = \""+brand+"\"").readOnlyCursor();

        return map(cursor.row(0), cursor.columnNames());
    }

    @Override
    protected Object map(final Object[] row, final String[] columnNames) {
        Long id = 0L;
        String productType = ProductType.SHOES.getProductType();
        String productName = NULL;
        Integer price = 0;
        Integer size = 0;
        String brand = NULL;
        ZonedDateTime storeAt = null;
        ZonedDateTime releaseAt = null;
        String status = ProductStatus.SALE.getProductStatus();

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
            if (columnNames[i].equals("size")) {
                size = Integer.parseInt((String) row[i]);
            }
            if (columnNames[i].equals("brand")) {
                brand = (String) row[i];
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

        return new ShoesDto(
                productType,
                productName,
                price,
                size,
                brand,
                status
        );
    }
}
