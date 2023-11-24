package com.holub.application.customer;

public class Customer {

    private static final String DEFAULT_NAME = "default name";
    private static final String DEFAULT_ADDRESS = "default address";

    private String id;
    private String name;
    private String address;

    public Customer(final String id, final String name, final String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public static Customer toCustomer(final Object[] row, final String[] columnNames) {
        String id = "0";
        String name = DEFAULT_NAME;
        String address = DEFAULT_ADDRESS;
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

        return new Customer(id, name, address);
    }
}
