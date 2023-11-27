package com.team15.erp.main;

import com.team15.erp.activity.Entry;
import com.team15.erp.model.DBConnection;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            DBConnection.getInstance().initialize("Dbase");
        } catch (IOException e) {
            System.out.println("DB 연결 실패: " + e);
        }

        while (true) {
            new Entry<Integer>();
        }
    }
}
