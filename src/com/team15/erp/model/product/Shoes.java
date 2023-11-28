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

public class Shoes extends Mapper {

    public ArrayList<ShoesDto> getAllShoes() throws IOException, ParseFailure {
        ArrayList<ShoesDto> shoesDtos = new ArrayList<>();

        ReadOnlyCursor shoes = this.dbConnection.query("select distinct * from shoes").readOnlyCursor();

        for (Object[] row: shoes.rows()) {
            shoesDtos.add((ShoesDto) map(row, shoes.columnNames()));
        }

        return shoesDtos;
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
                id,
                productType,
                productName,
                price,
                size,
                brand,
                storeAt,
                releaseAt,
                status
        );
    }
}
