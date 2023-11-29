package com.team15.erp.customer;

import com.team15.erp.model.DBConnection;
import com.team15.erp.model.product.Shoes;
import java.io.IOException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class BookDtoTest {

    @Test
    @DisplayName("Book 개수 조회 테스트")
    public void getNumberOfBookTest() throws IOException {
        DBConnection.getInstance().initialize("Dbase");

        int numberOfShoes = new Shoes().getNumberOfShoes();

        int expect = 3;

        Assertions.assertEquals(numberOfShoes, expect);
    }
}
