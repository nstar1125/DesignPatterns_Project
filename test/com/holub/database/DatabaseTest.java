package com.holub.database;

import com.holub.text.ParseFailure;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
    @DisplayName("order by 테스트")
    public void testOrderBy() throws IOException, ParseFailure {
        Database database = new Database("Dbase");
        database.begin();

        // parse fail
        assertThrows(ParseFailure.class, () -> database.execute("select * from name2 order by ASC"));
        assertThrows(NoSuchElementException.class, () -> database.execute("select last from name2 order by addrId, first"));
        assertThrows(ParseFailure.class, () -> database.execute("select * from name2 order by addrId, DESC"));
        // fail: missing comma between columns
        assertThrows(ParseFailure.class, () -> database.execute("select * from name2 order by addrId DESC first ASC"));

        // parse success
        {
            Object[] expected = new Object[]{"Alintstone", "Flintstone", "Holub"};
            assertArrayEquals(expected, database.execute("select last from name2 order by last").readOnlyCursor().column("last"));
        }
        {
            Object[] expected = new Object[]{"Allen", "Wilma", "Fred"};
            assertArrayEquals(expected, database.execute("select * from name2 order by addrId ASC").readOnlyCursor().column("first"));
        }
        {
            Object[] expected = new Object[]{"Fred", "Wilma", "Allen"};
            assertArrayEquals(expected, database.execute("select * from name2 order by addrId DESC").readOnlyCursor().column("first"));
        }
        {
            Object[] expected = new Object[]{"Allen", "Fred", "Wilma"};
            assertArrayEquals(expected, database.execute("select first, last from name2 order by last DESC").readOnlyCursor().column("first"));
        }
        {
            Object[][] expected = new Object[][]{
                    {"Fred", "ab", "7"},
                    {"Wilma", "aa", "7"},
                    {"Allen", "Holub", "0"},
            };
            assertArrayEquals(expected, database.execute("select * from name3 order by addrId DESC, last DESC").readOnlyCursor().rows());
        }
        {
            Object[][] expected = new Object[][]{
                    {"Wilma", "aa", "7"},
                    {"Fred", "ab", "7"},
                    {"Allen", "Holub", "0"},
            };
            assertArrayEquals(expected, database.execute("select * from name3 order by addrId DESC, last ASC").readOnlyCursor().rows());
        }
    }

    @Test
    @DisplayName("distinct 테스트")
    public void testDistinctParse() throws IOException, ParseFailure {
        Database database = new Database("Dbase");
        database.begin();

        // parse fail
        assertThrows(ParseFailure.class, () -> database.execute("select distnct * from student"));
        assertThrows(IndexOutOfBoundsException.class, () -> database.execute("select distinct agge from student"));


        // parse success
        database.execute("select distinct name from student");
        database.execute("select distinct name, age from student");
        database.execute("select distinct * from class");

        {
            Object[] expected = new Object[]{"frodo", "mike", "kenny"};
            System.out.println(database.execute("select distinct name from student"));
            assertArrayEquals(expected, database.execute("select distinct name from student").readOnlyCursor().column("name"));
        }

        {
            Object[] expected = new Object[]{"frodo 20", "mike 21", "kenny 23", "mike 25", "frodo 22"};
            System.out.println(database.execute("select distinct name, age from student"));
            assertArrayEquals(expected, database.execute("select distinct name, age from student")
                    .readOnlyCursor().columns(new String[]{"name", "age"})
            );
        }

        {
            Object[][] expected = new Object[][]{
                    {"0", "frodo", "20"},
                    {"1", "mike", "21"},
                    {"2", "kenny", "23"},
                    {"3", "mike", "25"},
                    {"4", "frodo", "22"},
            };
            System.out.println(database.execute("select distinct name, age from student"));
            assertArrayEquals(expected, database.execute("select distinct * from student")
                    .readOnlyCursor().rows()
            );
        }
    }

    @Test
    @DisplayName("XML customer import 테스트")
    public void testXmlImportCustomer() throws IOException, ParseFailure {
        Database database = new Database("Dbase");

        Object[][] rows = database.execute("SELECT * FROM customer2").readOnlyCursor().rows();
        Object[][] expected = {
                {"Data1", "Data2"},
                {"Data3", "Data4"},
        };

        assertArrayEquals(expected, rows);
    }

    @Test
    @DisplayName("XML customer export 테스트")
    public void testXmlExportCustomer() throws IOException, ParseFailure {
        Database database = new Database("Dbase");

        database.begin();
        database.execute("INSERT INTO customer2 VALUES ('Data5', 'Data6')");
        database.commit();
    }
}
