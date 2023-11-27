package com.team15.erp.model;

import com.team15.erp.model.product.ProductFactory;
import com.team15.erp.scheme.Product;

import java.util.ArrayList;
import java.util.List;

public class Inspection {
    public ArrayList<Product> inspect(List<String> products) throws NumberFormatException {
        ArrayList<Product> productArrayList = new ArrayList<>();
        for(String product: products) {
            productArrayList.add(parseProduct(product));
        }

        return productArrayList;
    }

    Product parseProduct(String productStr) throws NumberFormatException {
        String[] token = productStr.split("/");

        String type = token[0].trim();
        String[] info = token[1].trim().split(" ");

        return new ProductFactory().getProduct(type, productStr, info);
    }
}
