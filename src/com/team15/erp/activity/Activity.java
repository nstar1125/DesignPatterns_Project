package com.team15.erp.activity;

import java.util.Scanner;

public abstract class Activity<Option> implements Cli {
    protected Scanner scanner = new Scanner(System.in);

    public Activity() {
        this.before();
        this.showOptions();
        Option option = (Option) this.scanOption();
        this.perform(option);
    }

    public abstract void before();

    public abstract void perform(Option option);
}
