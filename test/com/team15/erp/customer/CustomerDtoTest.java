package com.team15.erp.customer;

import com.holub.text.ParseFailure;
import com.team15.erp.model.DBConnection;
import com.team15.erp.dto.customer.CustomerDto;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerDtoTest {
    @Test
    @DisplayName("Customer 조회 테스트")
    public void getCustomerTest() throws IOException, ParseFailure {
        DBConnection.getInstance().initialize("Dbase");
        ArrayList<CustomerDto> customerDtos = new com.team15.erp.model.customer.Customer().getAllCustomers();

        CustomerDto[] expected = {
                new CustomerDto("0", "frodo", "seoul"),
                new CustomerDto("1", "mike", "seoul"),
                new CustomerDto("2", "kenny", "busan"),
                new CustomerDto("3", "cris", "deajeon")
        };

        for(int i = 0 ; i< expected.length; i++) {
            Assertions.assertEquals(expected[i].getId(), customerDtos.get(i).getId());
            Assertions.assertEquals(expected[i].getName(), customerDtos.get(i).getName());
            Assertions.assertEquals(expected[i].getAddress(), customerDtos.get(i).getAddress());
        }
    }
}
