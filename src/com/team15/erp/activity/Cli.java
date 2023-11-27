package com.team15.erp.activity;

import java.util.InputMismatchException;

public interface Cli<Option> {
    void showOptions();
    Option scanOption() throws InputMismatchException;
}
