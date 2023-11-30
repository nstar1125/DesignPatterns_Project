package com.team15.erp.model.fulfilling;

import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductDto;
import com.team15.erp.dto.product.ShoesDto;
import com.team15.erp.model.Mapper;
import com.team15.erp.model.product.Book;
import com.team15.erp.model.product.Shoes;


import java.io.IOException;
import java.util.ArrayList;

public class Fulfillings extends Mapper {

    public void saveProduct(ArrayList<ProductDto> productDtoList) throws IOException, ParseFailure {
        for(ProductDto p: productDtoList) {
            switch (p.getProductType()) {

            }
            if("책".equals(p.getProductType())) {
                new Book().insert((BookDto) p);
            } else {
                new Shoes().insert((ShoesDto) p);
            }
        }
    }

    public int count() throws IOException, ParseFailure {
        String query1 = "SELECT DISTINCT product_name FROM book";
        int num1 = dbConnection.query(query1).readOnlyCursor().rows().length;
        String query2 = "SELECT DISTINCT product_name FROM shoes";
        int num2 = dbConnection.query(query2).readOnlyCursor().rows().length;
        return num1 + num2;
    }

    @Override
    protected Object map(Object[] row, String[] columnNames) {
        return null;
    }
}
