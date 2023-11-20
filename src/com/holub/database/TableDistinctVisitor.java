package com.holub.database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class TableDistinctVisitor implements TableVisitor {

    @Override
    public void visit(ConcreteTable table) {
        LinkedList mutRows = table.rows().getRowSet();

        List<Object> duplicateMutRows = new ArrayList<>();

        for (int i = 0; i < mutRows.size(); i++) {
            if (!duplicateMutRows.contains(mutRows.get(i))) {
                duplicateMutRows.add(mutRows.get(i));
            }
        }
        mutRows.clear();
        mutRows.addAll(duplicateMutRows);
    }

    @Override
    public void visit(UnmodifiableTable table) {
        visit((ConcreteTable) table.extract());
    }
}
