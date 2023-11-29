package com.team15.erp.entity.product;

public interface ProductVisitor {
    void visit(Shoes shoes);
    void visit(Book book);
}
