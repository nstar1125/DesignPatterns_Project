package com.team15.erp.model.product;

import com.team15.erp.dto.product.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

public class ProductFactory {

    private static final String DEFAULT_DATE_TIME = "0001-01-01 00:00:00";

    public ProductDto getProduct(String productType, String productStr, List<String> info) throws NumberFormatException {
        ProductDto productDto = null;

        switch (productType) {
            case "신발":
                try {
                    productDto = new ShoesDto(
                            ProductType.SHOES.getProductType(),
                            info.get(0),
                            Integer.parseInt(info.get(1)),
                            Integer.parseInt(info.get(2)),
                            info.get(3),
                            ProductStatus.SALE
                    );
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
                break;
            case "책":
                try {
                    productDto = new BookDto(
                            ProductType.BOOK.getProductType(),
                            info.get(0),
                            Integer.parseInt(info.get(1)),
                            info.get(2),
                            Integer.parseInt(info.get(3)),
                            ProductStatus.SALE
                    );
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
        }
        return productDto;
    }

    protected ZonedDateTime toZonedDateTime(String datetimeString) {
        String inputDateTime = datetimeString;
        if (inputDateTime.equals("null")) {
            inputDateTime = DEFAULT_DATE_TIME;
        }
        TimeZone tzSeoul = TimeZone.getTimeZone("Asia/Seoul");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(tzSeoul.toZoneId());
        return ZonedDateTime.parse(inputDateTime, dateTimeFormatter);
    }
}
