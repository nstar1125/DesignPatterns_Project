package com.team15.erp.activity.release;

import com.holub.text.ParseFailure;
import com.team15.erp.activity.Activity;
import com.team15.erp.model.order.Orders;

import java.io.IOException;
import java.util.InputMismatchException;

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
        releaseProducts(productType);
    }
    private void releaseProducts(String productType) throws IOException, ParseFailure {
        Orders orders = new Orders();
        orders.processOrders(productType);
    }

}
