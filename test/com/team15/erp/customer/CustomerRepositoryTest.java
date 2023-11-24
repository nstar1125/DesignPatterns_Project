package com.team15.erp.customer;

import com.holub.database.Database;
import com.holub.database.Table;
import com.holub.text.ParseFailure;
import com.team15.erp.model.customer.Customer;
import com.team15.erp.model.customer.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerRepositoryTest {
    @Test
    @DisplayName("Customer 조회 테스트")
    public void getCustomerTest() throws IOException, ParseFailure {
        Database database = new Database("Dbase");
        database.begin();
        CustomerRepository customerRepository = new CustomerRepository();

        Table table = database.execute("select distinct * from customer");
        List<Customer> customers = customerRepository.findCustomer(table, new String[]{"id", "name", "address"});

        String[] ids = new String[customers.size()];
        String[] names = new String[customers.size()];
        String[] addresses = new String[customers.size()];
        for (int i = 0; i < customers.size(); i++) {
            ids[i] = customers.get(i).getId();
            names[i] = customers.get(i).getName();
            addresses[i] = customers.get(i).getAddress();
        }

        String[] expectedIds = new String[] {"0", "1", "2", "3"};
        String[] expectedNames = new String[] {"frodo", "mike", "kenny", "cris"};
        String[] expectedAddresses= new String[] {"seoul", "seoul", "busan", "deajeon"};

        assertEquals(customers.size(), customers.size());
        assertArrayEquals(ids, expectedIds);
        assertArrayEquals(names, expectedNames);
        assertArrayEquals(addresses, expectedAddresses);
    }
}
