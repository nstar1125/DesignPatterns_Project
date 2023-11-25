package com.holub.database;

public interface ReadOnlyCursor {
    Object[][] rows();

    Object[] row(int index);

    Object[] column(String columnName) throws IndexOutOfBoundsException;

    Object[] columns(String[] columnNames) throws IndexOutOfBoundsException;

    boolean hasColumn(String columnName);

    int columnIndex(String columnName) throws IndexOutOfBoundsException;

    String[] columnNames();
}
