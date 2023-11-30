package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.BookDto;
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

public class Shoes extends Mapper<ShoesDto> {

    public int getNumberOfShoes() {
        try {
            ArrayList<ShoesDto> shoesDtos = new ArrayList<>();
            ReadOnlyCursor shoes = this.dbConnection.query("select distinct * from shoes").readOnlyCursor();

            for (Object[] row : shoes.rows()) {
                shoesDtos.add((ShoesDto) map(row, shoes.columnNames()));
            }

            return (int) shoesDtos
                    .stream()
                    .filter(bookDto -> bookDto.getStatus().equals(ProductStatus.SALE))
                    .count();
        } catch (IOException | ParseFailure e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public List<Object> selectAllByNameBrandSize(String productName, String brand, Integer size) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from shoes " +
                "where product_name = \"" + productName + "\" " +
                "and brand = \"" + brand + "\" " +
                "and size = " + size).readOnlyCursor();

        return Arrays.stream(cursor.rows())
                .map(row -> map(row, cursor.columnNames()))
                .collect(Collectors.toList());
    }

    public Object selectByNameBrand(String productName, String brand) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from shoes " +
                "where product_name = \"" + productName + "\"" +
                "and brand = \"" + brand + "\"").readOnlyCursor();

        return map(cursor.row(0), cursor.columnNames());
    }

    public void insert(ShoesDto shoes) throws IOException, ParseFailure {
        dbConnection.transaction(new ArrayList<>(List.of(String.format("" +
                        "insert into Shoes(product_name, price, size, brand, store_at, release_at, status) VALUES ('%s', '%d', '%d', '%s', '%s', '%s', '%s')",
                shoes.getProductName(), shoes.getPrice(), shoes.getSize(), shoes.getBrand(), getCurrentZonedDateTimeToString(), null, shoes.getStatus().name()
        ))));
    }

    @Override
    protected ShoesDto map(final Object[] row, final String[] columnNames) {
        Long id = 0L;
        String productType = ProductType.SHOES.getProductType();
        String productName = NULL;
        Integer price = 0;
        Integer size = 0;
        String brand = NULL;
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
                status = ProductStatus.valueOf((String) row[i]);
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
