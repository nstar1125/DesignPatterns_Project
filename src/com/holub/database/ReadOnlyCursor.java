package com.holub.database;

import java.util.Iterator;

public interface ReadOnlyCursor extends Iterator {
    Iterator columns();

    Object column(String columnName);

    int columnCount();
}
