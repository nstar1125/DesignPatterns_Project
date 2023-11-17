package com.holub.database;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class HTMLExporterTest {
    Table people = TableFactory.create("people", new String[] { "first", "last", "addrId" }); //임의의 테이블 설정
    private void setData(){ //테이블 임의의 데이터 삽입
        try{
            people.insert(new Object[]{"Fred", "Flintstone", "7"});
            people.insert(new Object[]{"Wilma", "Alintstone", "1"});
            people.insert(new Object[]{"Allen", "Holub", "0"});
        } catch (Exception e){}
    }

    @Test
    @DisplayName("HTMLExporter 테스트")
    public void htmlExporterTest() throws IOException{
        setData();
        // 기대 값
        String expected = "<html>\n" +
                "<body>\n" +
                "\t<h1>people</h1>\n" +
                "\t<table>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th>first</th>\n" +
                "\t\t\t<th>last</th>\n" +
                "\t\t\t<th>addrId</th>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>Fred</td>\n" +
                "\t\t\t<td>Flintstone</td>\n" +
                "\t\t\t<td>7</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>Wilma</td>\n" +
                "\t\t\t<td>Alintstone</td>\n" +
                "\t\t\t<td>1</td>\n" +
                "\t\t</tr>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td>Allen</td>\n" +
                "\t\t\t<td>Holub</td>\n" +
                "\t\t\t<td>0</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>\n" +
                "</body>\n" +
                "</html>";
        StringWriter out = new StringWriter();
        people.export(new HTMLExporter(out)); // 출력 값
        assertEquals(out.toString(), expected); //기대값과 출력값 비교
    }
}