package com.team15.erp.model.product;

import com.team15.erp.dto.BookDto;
import com.team15.erp.dto.ProductDto;
import com.team15.erp.dto.ShoesDto;

public class ProductFactory {
    public ProductDto getProduct(String productType, String productStr, String[] info) throws NumberFormatException {
        ProductDto productDto = null;

        switch (productType) {
            case "신발":
                try {
                    productDto = new ShoesDto(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
                break;
            case "책":
                try {
                    productDto = new BookDto(info[0], Integer.parseInt(info[1]), info[2], Integer.parseInt(info[3]));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
        }
        return productDto;
    }
}
