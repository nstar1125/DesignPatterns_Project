package com.team15.erp.model.product;

public interface ProductVisitor {
    void visitShoes(Shoes shoes);
    void visitBook(Book book);
}
