package com.team15.erp.activity.calculator;

import com.team15.erp.activity.Activity;

import java.util.InputMismatchException;

public class CalculatorEntry<Option> extends Activity<Option> {
    @Override
    public void perform(Option option) throws Exception {
        switch ((Integer) option) {
            case 0:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
                break;
            case 1:
                new StockChecker<String>();
                break;
            case 2:
                new ShippingFeeCalculator<String>();
                break;
        }
    }

    @Override
    public void showOptions() {
        System.out.println("원하는 옵션을 선택하세요\n");
        System.out.println("0: 종료");
        System.out.println("1: 재고 확인");
        System.out.println("2: 배송비 계산");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.nextInt();
    }
}
