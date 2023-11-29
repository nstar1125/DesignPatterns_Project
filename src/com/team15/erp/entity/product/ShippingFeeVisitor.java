package com.team15.erp.entity.product;

import java.util.List;

public class ShippingFeeVisitor implements ProductVisitor {

    final Integer shoesShippingFee = 3000;
    final Integer bookShippingFee = 2000;

    public void calPriceWithShippingFee(List<Product> products) {
        for (Product product: products) {
            product.accept((this));
        }
    }

    @Override
    public void visit(Shoes shoes) {
        System.out.printf("상품명 : %6s\n", shoes.getProductName());
        System.out.printf("가격 : %10d\n", shoes.getPrice());

        int extraFee = 0;
        if (shoes.getPrice() > 200000) extraFee = (int)(shoesShippingFee * 0.05);
        System.out.printf("배송료 : %10d\n", shoesShippingFee + extraFee);
        System.out.printf("%8s: %6d\n", "- 기본금", shoesShippingFee);
        System.out.printf("%8s: %6d\n", "- 추가금", extraFee);
    }

    @Override
    public void visit(Book book) {
        System.out.printf("상품명 : %6s\n", book.getProductName());
        System.out.printf("가격  : %10d\n", book.getPrice());
        System.out.printf("페이지 수 : %4d\n", book.getNumberOfPage());

        int extraFee = 0;
        if (book.getNumberOfPage() > 200) extraFee = 500;
        System.out.printf("배송료 : %10d\n", bookShippingFee + extraFee);
        System.out.printf("%8s: %6d\n", "- 기본금", bookShippingFee);
        System.out.printf("%8s: %6d\n", "- 추가금", extraFee);
    }
}
