package com.team15.erp.entity.product;

public interface ProductVisitor {
    void visitShoes(Shoes shoes);
    void visitBook(Book book);
}
