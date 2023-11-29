package com.team15.erp.model.product;

import com.team15.erp.model.Mapper;
import com.team15.erp.entity.product.Shoes;

public class ShoesMapper extends Mapper<Shoes> {
    @Override
    protected Shoes map(final Object[] row, final String[] columnNames) {
        Shoes shoes = new Shoes();
        for (int i = 0; i < columnNames.length; i++) {
            switch (columnNames[i]) {
                case "id":
                    shoes.setId((Long) row[i]);
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
