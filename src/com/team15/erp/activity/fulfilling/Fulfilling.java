package com.team15.erp.activity.fulfilling;

import com.team15.erp.activity.Activity;
import com.team15.erp.model.Inspection;
import com.team15.erp.scheme.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Fulfilling<Option> extends Activity<Option> {
    @Override
    public void before() {

    }

    @Override
    public void showOptions() {
        System.out.println("파일 경로를 입력하세요");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() {
        return scanner.next();
    }

    @Override
    public void perform(Option o) {
        List<String> fulfillingOrder = null;

        try {
            fulfillingOrder = Files.readAllLines(Paths.get((String) o));
        } catch (IOException e) {
            System.out.println("실패: " + e);
        }

        ArrayList<Product> incomingProducts = new Inspection().inspect(fulfillingOrder);

        //TODO: pipeline another activity here
    }
}
