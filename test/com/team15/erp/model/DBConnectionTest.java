package com.team15.erp.model;

import com.holub.text.ParseFailure;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;

public class DBConnectionTest {
    @Test
    @DisplayName("DBConnection singleton 생성 테스트")
    public void TestNewDBConnection() throws IOException {
        DBConnection dbConnection1 = DBConnection.getInstance();
        DBConnection dbConnection2 = DBConnection.getInstance();

        Assertions.assertTrue(dbConnection1 == dbConnection2);
    }

    @Test
    @DisplayName("DBase query 테스트")
    public void TestQueryDBase() {
        DBConnection dbConnection = DBConnection.getInstance();

        Assertions.assertDoesNotThrow(() -> {
            dbConnection.initialize("DBase");
            dbConnection.query("select distinct * from customer");
        });
    }

    @Test
    @DisplayName("DBase INSERT auto-increment id 테스트")
    public void TestInsertDBaseAutoIncrement() throws IOException, ParseFailure {
        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.initialize("DBase");

        ArrayList<String> queries = new ArrayList<>();
        queries.add("INSERT INTO customer VALUES ('alice', 'wonju')");
        dbConnection.transaction(queries);
    }

    @Test
    @DisplayName("DBase INSERT auto-increment id 테스트")
    public void TestInsertDBaseManual() throws IOException, ParseFailure {
        DBConnection dbConnection = DBConnection.getInstance();
        dbConnection.initialize("DBase");

        ArrayList<String> queries = new ArrayList<>();
        queries.add("INSERT INTO name VALUES ('aa', 'bb', 1)");
        dbConnection.transaction(queries);
    }
}
