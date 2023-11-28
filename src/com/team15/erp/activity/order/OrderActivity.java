package com.team15.erp.activity.order;

import com.team15.erp.activity.Activity;
import java.util.InputMismatchException;

public class OrderActivity<Option> extends Activity<Option> {

    @Override
    public void before() {
        System.out.println();
    }

    @Override
    public void perform(final Option option) throws Exception {

    }

    @Override
    public void showOptions() {

    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return null;
    }
}
