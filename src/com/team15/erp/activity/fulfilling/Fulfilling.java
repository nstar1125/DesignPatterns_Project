package com.team15.erp.activity.fulfilling;

import com.team15.erp.activity.Activity;
import com.team15.erp.model.Inspection;
import com.team15.erp.dto.product.ProductDto;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Fulfilling<Option> extends Activity<Option> {
    @Override
    public void before() {
        System.out.println("입고 모드");
    }

    @Override
    public void showOptions() {
        System.out.println("파일 경로를 입력하세요");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.next();
    }

    @Override
    public void perform(Option o) throws Exception {
        List<String> fulfillingOrder = Files.readAllLines(Paths.get((String) o));

        ArrayList<ProductDto> incomingProductDtos = new Inspection().inspect(fulfillingOrder);
        System.out.println(incomingProductDtos);

        for(ProductDto p: incomingProductDtos) {
            System.out.println("상품 종류: " + p.getProductType() + "상품 이름: " + p.getProductName());
        }
    }
}
