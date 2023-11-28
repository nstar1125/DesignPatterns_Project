package com.team15.erp.model.order;

import com.holub.database.ReadOnlyCursor;
import com.holub.database.Table;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.order.OrderStatus;
import com.team15.erp.dto.order.OrdersDto;
import com.team15.erp.model.Mapper;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Orders extends Mapper {

    public ArrayList<OrdersDto> getAllOrders() throws IOException, ParseFailure {
        ArrayList<OrdersDto> ordersDtos = new ArrayList<>();

        this.dbConnection.initialize(DEFAULT_FILE_ROUTE);
        ReadOnlyCursor order = this.dbConnection.query("select distinct * from order").readOnlyCursor();

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
        List<HashMap<String, Long>> orderProductIds = new ArrayList<>();
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
            if (columnNames[i].equals("order_status")) {
                orderStatus = (String) row[i];
            }
            if (columnNames[i].equals("order_product_ids")) {
                orderProductIds = toOrderProducts((String) row[i]);
            }
        }

        return new OrdersDto(
                id,
                customerId,
                orderDate,
                orderProductIds,
                orderStatus
        );
    }

    private List<HashMap<String, Long>> toOrderProducts(final String inputOrderProductIds) {
        List<String> parseOrderProducts = new ArrayList<>(List.of(inputOrderProductIds.split("/")));
        List<HashMap<String, Long>> orderProductIds = new ArrayList<>();

        for (String parseOrderProduct : parseOrderProducts) {
            String[] infos = parseOrderProduct.split("-");
            String productType = infos[0];
            Long productId = Long.valueOf(infos[1]);
            HashMap<String, Long> orderProduct = new HashMap<>();
            orderProduct.put(productType, productId);
            orderProductIds.add(orderProduct);
        }

        return orderProductIds;
    }

    public int getOrderTableSize() throws IOException, ParseFailure {
        this.dbConnection.initialize(DEFAULT_FILE_ROUTE);
        return this.dbConnection.query("select * from orders").readOnlyCursor().rows().length;
    }

    public Table selectOrderTable() throws IOException, ParseFailure {
        this.dbConnection.initialize(DEFAULT_FILE_ROUTE);
        return this.dbConnection.query("select * from orders");
    }

    public void insertOrder(final Object[] infos) throws IOException, ParseFailure {
        String query = String.format("insert into orders VALUES ('%d', '%d', '%s', '%s', '%s')", infos[0], infos[1], infos[2], infos[3], infos[4]);
        this.dbConnection.query(query);
    }
}
