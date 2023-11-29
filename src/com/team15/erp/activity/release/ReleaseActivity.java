package com.team15.erp.activity.release;

import com.holub.database.CSVExporter;
import com.holub.database.Database;
import com.holub.database.ReadOnlyCursor;
import com.holub.database.Table;
import com.holub.text.ParseFailure;
import com.team15.erp.activity.Activity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Objects;

public class ReleaseActivity <Option> extends Activity<Option> {
    @Override
    public void before() {
        System.out.println("출고 모드");
    }

    @Override
    public void showOptions() {
        System.out.println("출고할 품목을 입력하세요 (book/shoes)");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.next();
    }

    @Override
    public void perform(Option table) throws Exception {
        Database db = new Database("Dbase"); // 수정
        db.begin();

        int releaseCount = processOrder((String)table); // Order를 처리하고 처리한 수를 return

        for(int i = 0; i<releaseCount; i++){
            Table product = db.execute(String.format("select * from %s order by store_at",table)); // 입고순 정렬
            ReadOnlyCursor readOnlyCursor = product.readOnlyCursor();
            Object[] firstRow = readOnlyCursor.row(0);
            String deleteId = (String) firstRow[0];
            db.execute(String.format("update %s set status=\"SOLD\" where id=%s" ,table ,deleteId));
        }
        System.out.println("Release complete");
        db.commit();

        Table release = db.execute(String.format("select * from %s", table));
        Writer out = new FileWriter(String.format("/Users/sm/intellij-workspace/Design_Patterns_Team15/Dbase/%s.csv", table));
        release.export(new CSVExporter(out));
        out.close();
    }

    private int processOrder(String type) throws IOException, ParseFailure {
        Database db = new Database("Dbase"); // 수정
        db.begin();
        String productType = (Objects.equals(type, "book"))?"책":"신발";
        Table orders = db.execute(String.format("select * from orders where product_type=\"%s\"", productType));
        ReadOnlyCursor readOnlyCursor = orders.readOnlyCursor();
        Object[][] orderRows = readOnlyCursor.rows();
        int count = 0;
        for(int i = 0; i<orderRows.length; i++){
            LocalDateTime orderDate = LocalDateTime.parse((String)orderRows[i][2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime curDate = LocalDateTime.parse(getCurrentZonedDateTimeToString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (orderDate.isBefore(curDate)) {
                String deleteId = (String) orderRows[i][0];
                db.execute(String.format("update orders set orders_status=\"DELIVERY\" where id=%s" ,deleteId));
                count++;
            }
        }
        db.commit();
        Table processedOrders = db.execute(String.format("select * from orders"));
        Writer out = new FileWriter("/Users/sm/intellij-workspace/Design_Patterns_Team15/Dbase/orders.csv");
        processedOrders.export(new CSVExporter(out));
        out.close();
        return count;
    }
    private String getCurrentZonedDateTimeToString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ZonedDateTime.now());
    }

}
