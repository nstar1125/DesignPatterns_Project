package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.dto.product.ShoesDto;
import com.team15.erp.model.Mapper;
import java.io.IOException;
import java.util.ArrayList;

public class Shoes extends Mapper {

    public ArrayList<ShoesDto> getAllCustomers() throws IOException, ParseFailure {
        ArrayList<ShoesDto> shoesDtos = new ArrayList<>();

        ReadOnlyCursor shoes = this.dbConnection.query("select distinct * from shoes").readOnlyCursor();

        for (Object[] row: shoes.rows()) {
            shoesDtos.add((ShoesDto) map(row, shoes.columnNames()));
        }

        return shoesDtos;
    }

    @Override
    protected Object map(final Object[] row, final String[] columnNames) {
        String id = "0";
        String productType = ProductType.SHOES.getProductType();
        String productName = NULL;
        String price = NULL;
        String size = NULL;
        String brand = NULL;

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
            if (columnNames[i].equals("size")) {
                size = (String) row[i];
            }
            if (columnNames[i].equals("brand")) {
                brand = (String) row[i];
            }
        }

        return new ShoesDto(
                id,
                productType,
                productName,
                price,
                size,
                brand
        );
    }
}
