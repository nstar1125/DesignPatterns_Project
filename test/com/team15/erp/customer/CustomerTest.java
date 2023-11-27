package com.team15.erp.customer;

import com.holub.text.ParseFailure;
import com.team15.erp.model.DBConnection;
import com.team15.erp.model.customer.Customer;
import com.team15.erp.model.customer.CustomerScheme;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerTest {
    @Test
    @DisplayName("Customer 조회 테스트")
    public void getCustomerTest() throws IOException, ParseFailure {
        DBConnection.getInstance().initialize("Dbase");
        ArrayList<CustomerScheme> customers = new Customer().getAllCustomers();

        CustomerScheme[] expected = {
                new CustomerScheme("0", "frodo", "seoul"),
                new CustomerScheme("1", "mike", "seoul"),
                new CustomerScheme("2", "kenny", "busan"),
                new CustomerScheme("3", "cris", "deajeon")
        };

        for(int i = 0 ; i< expected.length; i++) {
            Assertions.assertEquals(expected[i].getId(), customers.get(i).getId());
            Assertions.assertEquals(expected[i].getName(), customers.get(i).getName());
            Assertions.assertEquals(expected[i].getAddress(), customers.get(i).getAddress());
        }
    }
}
