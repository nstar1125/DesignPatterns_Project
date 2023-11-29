package com.team15.erp.model.product;

import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ShoesDto;

public interface ProductVisitor {
    void visit(ShoesDto shoes);
    void visit(BookDto book);
}
