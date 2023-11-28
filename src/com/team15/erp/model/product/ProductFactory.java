package com.team15.erp.model.product;

import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductDto;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.dto.product.ShoesDto;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ProductFactory {
    public ProductDto getProduct(String productType, String productStr, String[] info) throws NumberFormatException {
        ProductDto productDto = null;

        switch (productType) {
            case "신발":
                try {
                    productDto = new ShoesDto(
                            Long.parseLong(info[0]), ProductType.SHOES.getProductType(),
                            info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]),
                            info[4], toZonedDateTime(info[5]), null,
                            info[7]
                    );
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
                break;
            case "책":
                try {
                    productDto = new BookDto(
                            Long.parseLong(info[0]), ProductType.BOOK.getProductType(),
                            info[1], Integer.parseInt(info[2]), info[3],
                            Integer.parseInt(info[4]), toZonedDateTime(info[5]), null,
                            info[7]
                    );
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
        }
        return productDto;
    }

    public static ZonedDateTime toZonedDateTime(String datetimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH-mm-ss");
        return ZonedDateTime.parse(datetimeString, formatter);
    }
}
