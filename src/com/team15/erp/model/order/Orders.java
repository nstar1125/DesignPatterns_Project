package com.team15.erp.model.order;

import com.holub.database.CSVExporter;
import com.holub.database.Database;
import com.holub.database.ReadOnlyCursor;
import com.holub.database.Table;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.order.OrdersDto;
import com.team15.erp.model.Mapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Orders extends Mapper {

    public ArrayList<OrdersDto> getAllOrders() throws IOException, ParseFailure {
        ArrayList<OrdersDto> ordersDtos = new ArrayList<>();

        ReadOnlyCursor order = this.dbConnection.query("select distinct * from orders").readOnlyCursor();

        for (Object[] row: order.rows()) {
            ordersDtos.add((OrdersDto) map(row, order.columnNames()));
        }

        return ordersDtos;
    }

    @Override
    protected Object map(final Object[] row, final String[] columnNames) {
        Long id = 0L;
        Long customerId = 0L;
        ZonedDateTime orderDate = null;
        String productType = null;
        String orderStatus = null;

        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i].equals("id")) {
                id = Long.valueOf((String) row[i]);
            }
            if (columnNames[i].equals("customer_id")) {
                customerId = Long.valueOf((String) row[i]);
            }
            if (columnNames[i].equals("order_date")) {
                orderDate = toZonedDateTime((String) row[i]);
            }
            if (columnNames[i].equals("product_type")) {
                productType = (String) row[i];
            }
            if (columnNames[i].equals("orders_status")) {
                orderStatus = ((String) row[i]);
            }
        }

        return new OrdersDto(
                id,
                customerId,
                orderDate,
                productType,
                orderStatus
        );
    }

    public int getOrderTableSize() throws IOException, ParseFailure {
        return this.dbConnection.query("select * from orders").readOnlyCursor().rows().length;
    }

    public void insertOrder(final Object[] infos) throws IOException, ParseFailure {
        String query = String.format("insert into orders VALUES ('%d', '%d', '%s', '%s', '%s')", infos[0], infos[1], infos[2], infos[3], infos[4]);
//        this.dbConnection.query(query);
        dbConnection.transaction(new ArrayList<>(List.of(query)));
    }

    public void processOrders(String productType) throws IOException, ParseFailure {
        int releaseCount = updateOrders(productType); // Order를 처리하고 처리한 수를 return

        for(int i = 0; i<releaseCount; i++){ //releaseCount 만큼 출고
            Table product = this.dbConnection.query(String.format("select * from %s order by store_at",productType)); // 입고순 정렬
            ReadOnlyCursor readOnlyCursor = product.readOnlyCursor();
            Object[] firstRow = readOnlyCursor.row(0);
            String releaseId = (String) firstRow[0];
            this.dbConnection.query(String.format("update %s set status=\"SOLD\" where id=%s" ,productType ,releaseId));
        }
        System.out.println("Release complete");

        Table release = this.dbConnection.query(String.format("select * from %s", productType));
        Writer out = new FileWriter(String.format("/Users/sm/intellij-workspace/Design_Patterns_Team15/Dbase/%s.csv", productType));
        release.export(new CSVExporter(out));
        out.close();
    }
    public int updateOrders(String type) throws IOException, ParseFailure {
        String productType = (Objects.equals(type, "book"))?"책":"신발";
        Table orders = this.dbConnection.query(String.format("select * from orders where product_type=\"%s\"", productType));
        ReadOnlyCursor readOnlyCursor = orders.readOnlyCursor();
        Object[][] orderRows = readOnlyCursor.rows();
        int count = 0;
        for(int i = 0; i<orderRows.length; i++){
            LocalDateTime orderDate = LocalDateTime.parse((String)orderRows[i][2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime curDate = LocalDateTime.parse(getCurrentZonedDateTimeToString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (orderDate.isBefore(curDate)) {
                String deleteId = (String) orderRows[i][0];
                this.dbConnection.query(String.format("update orders set orders_status=\"DELIVERY\" where id=%s" ,deleteId));
                count++;
            }
        }

        Table processedOrders = this.dbConnection.query(String.format("select * from orders"));
        Writer out = new FileWriter("/Users/sm/intellij-workspace/Design_Patterns_Team15/Dbase/orders.csv");
        processedOrders.export(new CSVExporter(out));
        out.close();
        return count;
    }


    private String getCurrentZonedDateTimeToString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ZonedDateTime.now());
    }
}
