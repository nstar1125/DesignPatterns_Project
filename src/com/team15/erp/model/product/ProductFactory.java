package com.team15.erp.model.product;

import com.team15.erp.scheme.Book;
import com.team15.erp.scheme.Product;
import com.team15.erp.scheme.Shoes;

public class ProductFactory {
    public Product getProduct(String productType, String productStr, String[] info) throws NumberFormatException {
        Product product = null;

        switch (productType) {
            case "신발":
                try {
                    product = new Shoes(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]);
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
                break;
            case "책":
                try {
                    product = new Book(info[0], Integer.parseInt(info[1]), info[2], Integer.parseInt(info[3]));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("잘못된 포맷: " + productStr);
                }
        }
        return product;
    }
}
