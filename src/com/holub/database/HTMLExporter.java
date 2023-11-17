package com.holub.database;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/********************************
 *
 * HTML 형식:
 *
 * <html>
 * <body>
 *  <h1> tableName </h1>
 *  <table>
 *      <tr>
 *          <th> 이름 </th>
 *          <th> 나이 </th>
 *          <th> 성별 </th>
 *      </tr>
 *      <tr>
 *          <td> 홍길동 </td>
 *          <td> 20 </td>
 *          <td> 남 </td>
 *      </tr>
 *      ...
 *  </table>
 * </body>
 * </html>
 *
 ********************************/

public class HTMLExporter implements Table.Exporter{
    private final Writer out; // Export 하는 대상

    public HTMLExporter(Writer out){
        this.out = out;
    }

    @Override
    public void startTable() throws IOException { // HTML 형식 시작
        out.write("<html>\n");
        out.write("<body>\n");

    }

    @Override
    public void storeMetadata(String tableName,
                              int width,
                              int height,
                              Iterator columnNames) throws IOException // 1행 (예. 이름, 나이, 성별 등) 정보 저장
    {
        out.write(String.format("\t<h1>%s</h1>\n", tableName==null?"anonymous":tableName)); // 테이블 이름
        out.write("\t<table>\n"); // 테이블 시작

        out.write("\t\t<tr>\n"); // 행 시작
        while (columnNames.hasNext()){ // 1행 정보 나열
            String columnName = columnNames.next().toString();
            out.write(String.format("\t\t\t<th>%s</th>\n", columnName));
        }
        out.write("\t\t</tr>\n"); // 행 끝
    }

    @Override
    public void storeRow(Iterator data) throws IOException { // 2~ 행에 데이터 저장
        out.write("\t\t<tr>\n"); // 행 시작
        while (data.hasNext()){ // 행 데이터 나열
            String dataVal = data.next().toString();
            out.write(String.format("\t\t\t<td>%s</td>\n", dataVal));
        }
        out.write("\t\t</tr>\n"); // 행 끝
    }

    @Override
    public void endTable() throws IOException { // HTML 형식 끝
        out.write("\t</table>\n"); //테이블 끝
        out.write("</body>\n");
        out.write("</html>");
    }
}