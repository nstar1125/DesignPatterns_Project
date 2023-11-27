package com.team15.erp.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

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
}
