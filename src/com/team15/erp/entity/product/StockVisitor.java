package com.team15.erp.entity.product;

import com.team15.erp.dto.product.ProductStatus;

import java.time.ZonedDateTime;

public class StockVisitor implements ProductVisitor {

    private boolean hasReleased(ZonedDateTime dateTime) {
        // FIXME: 출고되지 않은 상품도 mapper 에서 아주 과거로 설정됨
        if (dateTime.getYear() > 2000) return true;
        return false;
    }

    @Override
    public void visit(Shoes shoes) {
        if (hasReleased(shoes.getReleasedAt())) return;

        System.out.println(String.format("%4s: %3d번 %5s %3d %6s %10d ",
                shoes.getStatus(),
                shoes.getId(),
                shoes.getProductName(),
                shoes.getSize(),
                shoes.getBrand(),
                shoes.getPrice()));
    }

    @Override
    public void visit(Book book) {
        if (hasReleased(book.getReleasedAt())) return;

        System.out.println(String.format("%4s: %3d번 %5s %8s %5d %10d ",
                book.getStatus(),
                book.getId(),
                book.getProductName(),
                book.getWriter(),
                book.getNumberOfPage(),
                book.getPrice()));
    }
}
