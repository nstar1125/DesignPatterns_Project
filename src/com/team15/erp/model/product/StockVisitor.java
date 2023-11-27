package com.team15.erp.model.product;

import java.util.List;

public class StockVisitor implements ProductVisitor{

    public void getStockInfos(List<Product> products) {
        for (Product product: products) {
            product.accept((this));
        }
    }

    @Override
    public void visitShoes(Shoes shoes) {
        //TODO
    }

    @Override
    public void visitBook(Book book) {
        //TODO
    }
}
