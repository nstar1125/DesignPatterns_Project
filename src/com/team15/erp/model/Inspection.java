package com.team15.erp.model;

import com.team15.erp.dto.product.ProductDto;
import com.team15.erp.model.product.ProductFactory;

import java.util.ArrayList;
import java.util.List;

public class Inspection {
    public ArrayList<ProductDto> inspect(List<String> products) throws NumberFormatException {
        ArrayList<ProductDto> productDtoArrayList = new ArrayList<>();
        for(String product: products) {
            productDtoArrayList.add(parseProduct(product));
        }

        return productDtoArrayList;
    }

    ProductDto parseProduct(String productStr) throws NumberFormatException {
        String[] token = productStr.split("/");

        String type = token[0].trim();
        String[] info = token[1].trim().split(" ");

        return new ProductFactory().getProduct(type, productStr, info);
    }
}
