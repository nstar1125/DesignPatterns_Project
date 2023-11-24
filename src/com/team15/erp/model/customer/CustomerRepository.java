package com.team15.erp.model.customer;

import com.holub.database.ReadOnlyCursor;
import com.holub.database.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {

    public List<Customer> findCustomer(final Table table, final String[] columns) throws IOException {
        ReadOnlyCursor readOnlyCursor = table.readOnlyCursor();

        Object[][] tableRows = readOnlyCursor.rows();

        List<Customer> customers = new ArrayList<>();

        for (int i = 0; i < tableRows.length; i++) {
            customers.add(Customer.toCustomer(tableRows[i], columns));
        }

        return customers;
    }
}
