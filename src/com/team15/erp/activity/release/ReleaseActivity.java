package com.team15.erp.activity.release;

import com.holub.text.ParseFailure;
import com.team15.erp.activity.Activity;
import com.team15.erp.model.order.Orders;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Objects;

public class ReleaseActivity <Option> extends Activity<Option> {
    @Override
    public void before() {
        System.out.println("출고 모드");
    }

    @Override
    public void showOptions() {
        System.out.println("출고할 품목을 입력하세요 (book/shoes)");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.next();
    }

    @Override
    public void perform(Option option) throws Exception {
        String productType = (String)option;
        String productName = (Objects.equals(productType, "book"))?"책":"신발";
        LocalDateTime now = LocalDateTime.now();
        System.out.println(String.format("%s 기준 이전에 주문된 %s들을 출고합니다.",now.toString(),productName));
        System.out.println();
        int releaseCount = releaseProducts(productType); //품목 출고
        System.out.println(String.format("%s %d개가 출고 되었습니다.",productName, releaseCount));
    }
    private int releaseProducts(String productType) throws IOException, ParseFailure {
        return new Orders().processOrders(productType); //품목 출고
    }

}
