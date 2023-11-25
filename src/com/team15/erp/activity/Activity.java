package com.team15.erp.activity;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Activity<Option> implements Cli {
    protected Scanner scanner = new Scanner(System.in);
    private Option option = null;

    public Activity() {
        this.before();

        while(true) {
            this.showOptions();
            try {
                option = (Option) this.scanOption();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 옵션입니다. 다시 입력해주세요\n");
                scanner.next();
                continue;
            }

            try {
                this.perform(option);
                break;
            } catch (Exception e) {
                System.out.println("오류가 발생했습니다. " + e + "\n");
            }
        }
    }

    public void before() {}

    public abstract void perform(Option option) throws Exception;
}
