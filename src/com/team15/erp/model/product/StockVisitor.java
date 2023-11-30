package com.team15.erp.model.product;

import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductStatus;
import com.team15.erp.dto.product.ShoesDto;

public class StockVisitor implements ProductVisitor {

    @Override
    public void visit(ShoesDto shoes) {
        if (shoes.getStatus().equals(ProductStatus.SOLD)) return;

        System.out.printf("%4s: %5s %3d %6s %10d %n",
                shoes.getStatus(),
                shoes.getProductName(),
                shoes.getSize(),
                shoes.getBrand(),
                shoes.getPrice());
    }

    @Override
    public void visit(BookDto book) {
        if (book.getStatus().equals(ProductStatus.SOLD)) return;

        System.out.printf("%4s: %5s %8s %5d %10d %n",
                book.getStatus(),
                book.getProductName(),
                book.getWriter(),
                book.getNumberOfPage(),
                book.getPrice());
    }
}
