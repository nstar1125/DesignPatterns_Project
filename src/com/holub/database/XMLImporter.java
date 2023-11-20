package com.holub.database;

import java.io.IOException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

/***
 *	Pass this importer to a {@link Table} constructor (such
 *	as
 *	{link com.holub.database.ConcreteTable#ConcreteTable(Table.Importer)}
 *	to initialize
 *	a <code>Table</code> from
 *	a comma-sparated-value repressentation. For example:
 *	<PRE>
 *	// xml parse builder
 *  name4 = new ConcreteTable( new XMLImporter(string_path) );
 *  doc.close();
 *	</PRE>
 *	The input file for a table called "name4" with
 *	columns "first," "last," and "admission" would look
 *	like this:
 *	<PRE>
 *	<users>
 *	    <ROW>
 *	        <first>이름</first>
 *	        <last>성</last>
 *	        <gender>Male</gender>
 *	    </ROW>
 *	    ...
 *	</users>
 *	</PRE>
 *	The root element is the table name, the sibling of root's first child
 *	identifies the columns, and the sibling value define
 *	the rows.
 *
 * @see Table
 * @see Table.Importer
// * @see XMLExporter
 */

public class XMLImporter implements Table.Importer
{
    private final ArrayList<String> columnNames = new ArrayList<>();
    private final Document doc;
    private final Element root;
    private final NodeList rows;
    private int curRowIdx;

    public XMLImporter(Reader in) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            this.doc = builder.parse(new InputSource(in));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        root = doc.getDocumentElement();
        rows = root.getElementsByTagName("ROW");
        curRowIdx = 0;
    }

    @Override
    public void startTable() throws IOException {
        Node node = root.getFirstChild().getNextSibling();
        NodeList childNodes = node.getChildNodes();
        for(int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                columnNames.add(item.getNodeName());
            }
        }
    }

    @Override
    public String loadTableName() {
        return root.getNodeName();
    }

    @Override
    public int loadWidth() {
        return columnNames.size();
    }

    @Override
    public Iterator loadColumnNames() {
        return columnNames.iterator();
    }

    @Override
    public Iterator loadRow() {
        if (curRowIdx < rows.getLength()) {
            NodeList data = rows.item(curRowIdx).getChildNodes();
            curRowIdx++;
            ArrayList<String> rowList = new ArrayList<>();
            for (int j = 0; j < data.getLength(); j++) {
                Node rowNode = data.item(j);
                if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                    rowList.add(rowNode.getTextContent());
                }
            }
            return rowList.iterator();
        }
        return null;
    }

    @Override
    public void endTable() throws IOException {}
}
