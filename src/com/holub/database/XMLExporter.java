package com.holub.database;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLExporter implements Table.Exporter {
    private final Writer out;
    private final Document doc;
    private Element root;
    private final ArrayList<String> columnNames = new ArrayList<>();

    public XMLExporter(Writer out) {
        this.out = out;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        doc = builder.newDocument();
    }

    @Override
    public void storeMetadata( String tableName, int width, int height, Iterator itColumnNames) {
        root = doc.createElement(tableName == null ? "anonymous" : tableName);
        doc.appendChild(root);
        while (itColumnNames.hasNext()){
            this.columnNames.add(itColumnNames.next().toString());
        }
    }

    @Override
    public void storeRow(Iterator data) {
        Element row = doc.createElement("ROW");
        root.appendChild(row);

        int i = 0;
        while (data.hasNext()) {
            Object datum = data.next();

            if (datum != null) {
                Element column = doc.createElement(columnNames.get(i));
                column.appendChild(doc.createTextNode(datum.toString()));
                row.appendChild(column);
            }

            i++;
        }
    }

    @Override
    public void startTable() {/*nothing to do*/}

    @Override
    public void endTable() throws IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(out);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }
}
