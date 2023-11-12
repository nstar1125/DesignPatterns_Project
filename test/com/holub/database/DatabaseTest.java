package com.holub.database;

import com.holub.text.ParseFailure;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    @DisplayName("ReadOnlyCursor 테스트")
    public void testReadOnlyCursor() throws IOException, ParseFailure, IndexOutOfBoundsException {
        Database database = new Database("Dbase");
        database.begin();

        Table lastNames = database.execute("select * from name2");
        ReadOnlyCursor readOnlyCursor = lastNames.readOnlyCursor();

        // rows test
        Object[][] rows = readOnlyCursor.rows();
        Object[][] expectedRows = {
                {"Fred", "Flintstone", "7"},
                {"Wilma", "Alintstone", "1"},
                {"Allen", "Holub", "0"},
        };
        assertArrayEquals(rows, expectedRows);

        // row test
        Object[] firstRow = readOnlyCursor.row(0);
        Object[] expectedFirstRow = {"Fred", "Flintstone", "7"};
        assertArrayEquals(firstRow, expectedFirstRow);

        // last column test
        Object[] lastCol = new Object[0];
        lastCol = readOnlyCursor.column("last");
        Object[] expectedLastCol = {"Flintstone", "Alintstone", "Holub"};
        assertArrayEquals(lastCol, expectedLastCol);
    }

    @Test
    @DisplayName("order by parsing 테스트")
    public void testOrderByParse() throws IOException, ParseFailure {
        Database database = new Database("Dbase");
        database.begin();

        // parse fail
        assertThrows(ParseFailure.class, () -> database.execute("select * from name2 order by ASC"));
        assertThrows(NoSuchElementException.class, () -> database.execute("select last from name2 order by addrId, first"));
        assertThrows(ParseFailure.class, () -> database.execute("select * from name2 order by addrId, DESC"));
        // fail: missing comma between columns
        assertThrows(ParseFailure.class, () -> database.execute("select * from name2 order by addrId DESC first ASC"));

        // parse success
        database.execute("select * from name order by addrId");
        database.execute("select * from name order by addrId ASC");
        database.execute("select * from name order by addrId DESC");
        database.execute("select * from name order by first ASC addrId DESC");
        database.execute("select * from name order by first addrId");
        database.execute("select * from name order by first addrId ASC");
    }
}
