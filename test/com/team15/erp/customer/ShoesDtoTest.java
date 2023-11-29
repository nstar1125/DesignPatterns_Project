package com.team15.erp.customer;

import com.team15.erp.model.DBConnection;
import com.team15.erp.model.product.Book;
import java.io.IOException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class ShoesDtoTest {

    @Test
    @DisplayName("Shoes 개수 조회 테스트")
    public void getNumberOfShoesTest() throws IOException {
        DBConnection.getInstance().initialize("Dbase");

        int numberOfBook = new Book().getNumberOfBooks();

        int expect = 6;

        Assertions.assertEquals(numberOfBook, expect);
    }

}
