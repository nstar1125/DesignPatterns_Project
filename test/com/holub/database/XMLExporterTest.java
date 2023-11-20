package com.holub.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class XMLExporterTest {
    private XMLExporter exporter;
    private Writer out;
    private String tableName;
    private int width;
    private int height;
    private Iterator columnNames;

    @BeforeEach
    void setUp() {
        out = new StringWriter();
        exporter = new XMLExporter(out);

        tableName = "table";
        width = 2;
        height = 2;
        columnNames = Arrays.asList("column1", "column2").iterator();
    }

    @Test
    @DisplayName("XML Export 테스트")
    void testXMLExporter() throws IOException {
        exporter.startTable();
        exporter.storeMetadata(tableName, width, height, columnNames);
        exporter.storeRow(Arrays.asList("Data1", "Data2").iterator());
        exporter.storeRow(Arrays.asList("Data3", "Data4").iterator());
        exporter.endTable();

        String expected =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                        "<table>" +
                        "<ROW><column1>Data1</column1><column2>Data2</column2></ROW>" +
                        "<ROW><column1>Data3</column1><column2>Data4</column2></ROW>" +
                        "</table>";

        assertTrue(out.toString().contains(expected));
    }


}
