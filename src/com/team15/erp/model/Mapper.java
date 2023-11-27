package com.team15.erp.model;

public abstract class Mapper<T> {
    public final String NULL = "NULL";
    protected DBConnection dbConnection = DBConnection.getInstance();

    protected abstract T map(final Object[] row, final String[] columnNames);
}
