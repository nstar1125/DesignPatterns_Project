package com.team15.erp.model.product;

import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductStatus;
import com.team15.erp.dto.product.ShoesDto;

public class StockVisitor implements ProductVisitor {

    @Override
    public void visit(ShoesDto shoes) {
        if (shoes.getStatus().equals(ProductStatus.SOLD.getProductStatus())) return;

        System.out.println(String.format("%4s: %3d번 %5s %3d %6s %10d ",
                shoes.getStatus(),
                shoes.getProductName(),
                shoes.getSize(),
                shoes.getBrand(),
                shoes.getPrice()));
    }

    @Override
    public void visit(BookDto book) {
        if (book.getStatus().equals(ProductStatus.SOLD.getProductStatus())) return;

        System.out.println(String.format("%4s: %3d번 %5s %8s %5d %10d ",
                book.getStatus(),
                book.getProductName(),
                book.getWriter(),
                book.getNumberOfPage(),
                book.getPrice()));
    }
}
