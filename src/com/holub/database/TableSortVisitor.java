package com.holub.database;

import javax.management.AttributeNotFoundException;
import java.util.*;

public class TableSortVisitor implements TableVisitor {
    private LinkedHashMap<String, Integer> orderings;

    public TableSortVisitor(LinkedHashMap<String, Integer> orderings) {
        this.orderings = orderings;
    }

    @Override
    public void visit(ConcreteTable table) {
        LinkedList mutRows = table.rows().getRowSet();

        for (String keyCol : this.orderings.keySet()) {
            if (!table.readOnlyCursor().hasColumn(keyCol)) {
                throw new NoSuchElementException();
            }
        }

        mutRows.sort((r1, r2) -> {
            for (String keyCol : this.orderings.keySet()) {
                int diff;
                int col_idx = table.readOnlyCursor().columnIndex(keyCol);

                String r1_col = ((Object[]) r1)[col_idx].toString();
                String r2_col = ((Object[]) r2)[col_idx].toString();

                try {
                    // number columns
                    diff = Integer.parseInt(r1_col) - Integer.parseInt(r2_col);
                } catch (Exception e) {
                    // alphabet columns
                    diff = r1_col.compareTo(r2_col);
                }
                if (diff != 0) {
                    return diff * orderings.get(keyCol);
                }
            }
            return 0;
        });
    }

    @Override
    public void visit(UnmodifiableTable table) {
        visit((ConcreteTable) table.extract());
    }
}
