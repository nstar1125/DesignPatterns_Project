package com.holub.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class TableDistinctVisitor implements TableVisitor {

    @Override
    public void visit(ConcreteTable table) {
        LinkedList mutRows = table.rows().getRowSet();
        LinkedList distinctMutRows = new LinkedList();

        List<String> stringDistinctMutRows = new ArrayList<>();

        Object[][] rows = table.readOnlyCursor().rows();
        for (int i = 0; i < rows.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < rows[i].length; j++) {
                stringBuilder.append(rows[i][j]);
            }

            if (!stringDistinctMutRows.contains(stringBuilder.toString())) {
                stringDistinctMutRows.add(stringBuilder.toString());
                distinctMutRows.add(rows[i]);
            }
        }

        mutRows.clear();
        mutRows.addAll(distinctMutRows);
    }

    @Override
    public void visit(UnmodifiableTable table) {
        visit((ConcreteTable) table.extract());
    }
}
