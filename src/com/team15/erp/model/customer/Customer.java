package com.team15.erp.model.customer;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.customer.CustomerDto;
import com.team15.erp.model.Mapper;

import java.io.IOException;
import java.util.ArrayList;

public class Customer extends Mapper<CustomerDto> {

    public ArrayList<CustomerDto> getAllCustomers() throws IOException, ParseFailure {
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();

        ReadOnlyCursor customer = this.dbConnection.query("select distinct * from customer").readOnlyCursor();

        for (Object[] row: customer.rows()) {
            customerDtos.add((CustomerDto) map(row, customer.columnNames()));
        }

        return customerDtos;
    }

    @Override
    protected CustomerDto map(Object[] row, String[] columnNames) {
        String id = "0";
        String name = NULL;
        String address = NULL;

        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals("id")) {
                id = (String) row[i];
            }
            if (columnNames[i].equals("name")) {
                name = (String) row[i];
            }
            if (columnNames[i].equals("address")) {
                address = (String) row[i];
            }
        }

        return new CustomerDto(id, name, address);
    }
}
