package com.team15.erp.model.fulfilling;

import com.holub.text.ParseFailure;
import com.team15.erp.dto.product.ProductDto;
import com.team15.erp.model.Mapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fulfilling extends Mapper {

    public void saveProduct(ArrayList<ProductDto> productDtoList) throws IOException, ParseFailure {
        for(ProductDto p: productDtoList) {
            String query = "";
            System.out.println("상품 종류: " + p.getProductType() + "상품 이름: " + p.getProductName());
            if("책".equals(p.getProductType())) {
                query = String.format("insert into book(product_name, price, status) VALUES ('%s', '%d', '%s')", p.getProductName(), p.getPrice(), p.getStatus());
            } else {
                query = String.format("insert into Shoes(product_name, price, status) VALUES ('%s', '%d', '%s')", p.getProductName(), p.getPrice(), p.getStatus());
            }
            dbConnection.transaction(new ArrayList<>(List.of(query)));
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
