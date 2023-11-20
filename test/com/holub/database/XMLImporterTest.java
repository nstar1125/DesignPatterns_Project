package com.holub.database;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class XMLImporterTest {
    private XMLImporter importer;

    @BeforeEach
    void setUp() {
        String xmlString = "<table>" +
                "<ROW><column1>Data1</column1><column2>Data2</column2></ROW>" +
                "<ROW><column1>Data3</column1><column2>Data4</column2></ROW>" +
                "</table>";

        importer = new XMLImporter(new StringReader((xmlString)));
    }

    @Test
    @DisplayName("startTable 테스트")
    void testStartTable() throws IOException {
        importer.startTable();
        assertEquals("table", importer.loadTableName());
        assertEquals(2, importer.loadWidth());
    }

    @Test
    @DisplayName("LoadRow 테스트")
    void testLoadRow() {
        Iterator<String> row = importer.loadRow();
        assertNotNull(row);
        assertEquals("Data1", row.next());
        assertEquals("Data2", row.next());
        assertFalse(row.hasNext());

        row = importer.loadRow();
        assertEquals("Data3", row.next());
        assertEquals("Data4", row.next());
        assertFalse(row.hasNext());

        row = importer.loadRow();
        assertNull(row);
    }

    @Test
    @DisplayName("endTable 테스트")
    void testEndTable() throws IOException {
        importer.endTable();
        // 아무것도 하지 않는 메소드입니다.
    }
}
