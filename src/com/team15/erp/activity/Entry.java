package com.team15.erp.activity;

import com.team15.erp.activity.fulfilling.Fulfilling;

import com.team15.erp.activity.order.OrderActivity;
import java.util.InputMismatchException;

public class Entry<Option> extends Activity<Option> {
    @Override
    public void before() {
        System.out.println("" +
                "  ______ _____  _____  \n" +
                " |  ____|  __ \\|  __ \\ \n" +
                " | |__  | |__) | |__) |\n" +
                " |  __| |  _  /|  ___/ \n" +
                " | |____| | \\ \\| |     \n" +
                " |______|_|  \\_\\_|     \n" +
                "                       \n" +
                "                       ");
    }

    @Override
    public void showOptions() {
        System.out.println("원하는 옵션을 선택하세요\n");
        System.out.println("0: 종료");
        System.out.println("1: 입고");
        System.out.println("2: 출고");
        System.out.println("3: 주문");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.nextInt();
    }

    @Override
    public void perform(Option option) throws Exception {
        switch ((Integer) option) {
            case 0:
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
                break;
            case 1:
                new Fulfilling<String>();
                break;
            case 2:
                break;
            case 3:
                new OrderActivity<String>();
                break;
        }
    }
}
