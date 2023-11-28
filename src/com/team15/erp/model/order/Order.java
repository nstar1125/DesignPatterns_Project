package com.team15.erp.model.order;

import com.holub.database.ReadOnlyCursor;
import com.holub.text.ParseFailure;
import com.team15.erp.dto.order.OrderDto;
import com.team15.erp.model.Mapper;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order extends Mapper {

    public ArrayList<OrderDto> getAllOrders() throws IOException, ParseFailure {
        ArrayList<OrderDto> orderDtos = new ArrayList<>();

        this.dbConnection.initialize(DEFAULT_FILE_ROUTE);
        ReadOnlyCursor order = this.dbConnection.query("select distinct * from order").readOnlyCursor();

        for (Object[] row: order.rows()) {
            orderDtos.add((OrderDto) map(row, order.columnNames()));
        }

        return orderDtos;
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

        return new OrderDto(
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
}
