package com.holub.database;

import java.util.Iterator;

public interface ReadOnlyCursor {
    Object[][] rows();

    Object[] row(int index);

    Object[] column(String columnName) throws IndexOutOfBoundsException;

    int columnCount();
}
