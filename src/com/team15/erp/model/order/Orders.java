package com.team15.erp.model.order;

import com.holub.database.CSVExporter;
import com.holub.database.Database;
import com.holub.database.ReadOnlyCursor;
import com.holub.database.Table;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.order.OrderStatus;
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
import java.util.stream.Collectors;

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
        Long customerId = 0L;
        ZonedDateTime orderDate = null;
        String productType = null;
        String orderStatus = null;

        for (int i = 0; i < columnNames.length; i++) {
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
        String query = String.format("insert into orders VALUES ('%d', '%s', '%s', '%s')", infos[0], infos[1], infos[2], infos[3]);
        dbConnection.transaction(new ArrayList<>(List.of(query)));
    }

    public int processOrders(String productType) throws IOException, ParseFailure { //입고 순 정렬 후 가장 오래된 항목들 출고
        int releaseCount = updateOrders(productType); //Order를 DELIVERY로 변경 및 처리할 항목 수 받아옴
        for(int i = 0; i<releaseCount; i++){ //입고 순 정렬 후 가장 오래된 항목들 순서대로 출고
            Table product = this.dbConnection.query(String.format("select * from %s where status=\"SALE\" order by store_at",productType)); // 입고순 정렬
            ReadOnlyCursor readOnlyCursor = product.readOnlyCursor();
            Object[] firstRow = readOnlyCursor.row(0);
            String releaseId = (String) firstRow[0];
            this.dbConnection.query(String.format("update %s set status=\"SOLD\" where id=%s" ,productType ,releaseId));
        }
        exportCSV(productType);
        return releaseCount;
    }
    public int updateOrders(String type) throws IOException, ParseFailure { //Order를 DELIVERY로 변경 및 처리할 항목 수 return
        String productType = (Objects.equals(type, "book"))?"책":"신발";
        Table orders = this.dbConnection.query(String.format("select * from orders where product_type=\"%s\" AND orders_status=\"ORDER\"", productType));
        ReadOnlyCursor readOnlyCursor = orders.readOnlyCursor();
        Object[][] orderRows = readOnlyCursor.rows();
        int count = 0;
        for(int i = 0; i<orderRows.length; i++){ //현재 보다 이전에 주문된 품목들 count
            LocalDateTime orderDate = LocalDateTime.parse((String)orderRows[i][2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime curDate = LocalDateTime.parse(getCurrentZonedDateTimeToString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (orderDate.isBefore(curDate)) {
                String deleteId = (String) orderRows[i][0];
                this.dbConnection.query(String.format("update orders set orders_status=\"DELIVERY\" where id=%s" ,deleteId));
                count++;
            }
        }
        exportCSV("orders");
        return count;
    }

    private String getCurrentZonedDateTimeToString() { //현재 시간 계산
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ZonedDateTime.now());
    }

    private void exportCSV(String csv) throws IOException, ParseFailure { //CSV로 export
        Table table = this.dbConnection.query(String.format("select * from %s",csv));
        Writer out = new FileWriter(String.format("Dbase/%s.csv", csv));
        table.export(new CSVExporter(out));
        out.close();
    }
}
