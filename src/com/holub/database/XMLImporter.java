package com.holub.database;

import java.io.IOException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
 * @include /etc/license.txt
 *
 * @see Table
 * @see Table.Importer
// * @see XMLExporter
 */

public class XMLImporter implements Table.Importer
{
    private final Document doc;
    private final ArrayList<String> columnNames = new ArrayList<>();
    private String tableName;
    int rowIdx = 0;

    public XMLImporter(String in) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        this.doc = builder.parse(in);
        doc.getDocumentElement().normalize();
    }

    @Override
    public void startTable() throws IOException {
        Element root = doc.getDocumentElement();
        tableName = root.getNodeName();
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
        return tableName;
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
        Node node = doc.getElementsByTagName("ROW").item(rowIdx);
        if (node != null)
        {
            ArrayList<String> rowList = new ArrayList<>();
            NodeList data = node.getChildNodes();
            for (int j = 0; j < data.getLength(); j++) {
                Node rowNode = data.item(j);
                if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                    rowList.add(rowNode.getTextContent());
                }
            }
            rowIdx++;
            return rowList.iterator();
        }
        return null;
    }

    @Override
    public void endTable() throws IOException {}
}
