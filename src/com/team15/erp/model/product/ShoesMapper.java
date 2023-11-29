package com.team15.erp.model.product;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.model.Mapper;
import com.team15.erp.entity.product.Shoes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShoesMapper extends Mapper<Shoes> {

    public List<Shoes> selectByNameTypeBrandSize(String productName, String brand, Integer size) throws IOException, ParseFailure {
        ReadOnlyCursor cursor = dbConnection.query("select * from shoes where product_name = \""+productName+"\"").readOnlyCursor();

        return Arrays.stream(cursor.rows())
                .map(row -> map(row, cursor.columnNames()))
                .collect(Collectors.toList());
    }

    @Override
    protected Shoes map(final Object[] row, final String[] columnNames) {
        Shoes shoes = new Shoes();
        for (int i = 0; i < columnNames.length; i++) {
            switch (columnNames[i]) {
                case "id":
                    shoes.setId(Long.valueOf((String)row[i]));
                    break;
                case "product_name":
                    shoes.setProductName((String) row[i]);
                    break;
                case "price":
                    shoes.setPrice(Integer.parseInt((String) row[i]));
                    break;
                case "brand":
                    shoes.setBrand((String)row[i]);
                    break;
                case "size":
                    shoes.setSize(Integer.parseInt((String) row[i]));
                    break;
                case "store_at":
                    shoes.setStoredAt(toZonedDateTime((String) row[i]));
                    break;
                case "release_at":
                    shoes.setReleasedAt(toZonedDateTime((String) row[i]));
                    break;
                case "status":
                    shoes.setStatus((String) row[i]);
                    break;
                default:
                    break;
            }
        }
        return shoes;
    }
}
