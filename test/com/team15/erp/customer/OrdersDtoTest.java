package com.team15.erp.customer;

import com.holub.text.ParseFailure;
import com.team15.erp.dto.customer.CustomerDto;
import com.team15.erp.dto.order.OrderStatus;
import com.team15.erp.dto.order.OrdersDto;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.model.DBConnection;
import com.team15.erp.model.customer.Customer;
import com.team15.erp.model.order.Orders;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimeZone;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class OrdersDtoTest {

    @Test
    @DisplayName("Orders 조회 테스트")
    public void getOrdersTest() throws IOException, ParseFailure {
        DBConnection.getInstance().initialize("Dbase");
        ArrayList<OrdersDto> ordersDtos = new Orders().getAllOrders();

        OrdersDto[] expected = {
                new OrdersDto(0L, 1L, toZonedDateTime("2023-11-29 12:50:16"), "책", "SALE"),
                new OrdersDto(1L, 2L, toZonedDateTime("2023-11-29 12:50:16"), "신발", "SALE")
        };

        for(int i = 0 ; i< expected.length; i++) {
            Assertions.assertEquals(expected[i].getId(), ordersDtos.get(i).getId());
            Assertions.assertEquals(expected[i].getCustomerId(), ordersDtos.get(i).getCustomerId());
            Assertions.assertEquals(expected[i].getOrderDate(), ordersDtos.get(i).getOrderDate());
            Assertions.assertEquals(expected[i].getProductType(), ordersDtos.get(i).getProductType());
            Assertions.assertEquals(expected[i].getOrderStatus(), ordersDtos.get(i).getOrderStatus());
        }
    }

    @Test
    @DisplayName("Orders insert 테스트")
    public void insertOrdersTest() throws IOException, ParseFailure {
        DBConnection.getInstance().initialize("Dbase");
        Orders orders = new Orders();

        ZonedDateTime currentTime = ZonedDateTime.now();

        Object[] orderInfos = new Object[] {
                2L, 0L,
                getCurrentZonedDateTimeToString(currentTime),
                ProductType.BOOK.getProductType(),
                OrderStatus.ORDER.getOrderStatus()
        };

        orders.insertOrder(orderInfos);
        ArrayList<OrdersDto> ordersDtos = new Orders().getAllOrders();
        String zonedToString = getCurrentZonedDateTimeToString(currentTime);
        ZonedDateTime stringToZoned = toZonedDateTime(zonedToString);

        OrdersDto[] expected = {
                new OrdersDto(0L, 1L, toZonedDateTime("2023-11-29 12:50:16"), "책", "ORDER"),
                new OrdersDto(1L, 2L, toZonedDateTime("2023-11-29 12:50:16"), "신발", "ORDER"),
                new OrdersDto(2L, 0L, stringToZoned, "책", "ORDER")
        };

        for(int i = 0 ; i< expected.length; i++) {
            Assertions.assertEquals(expected[i].getId(), ordersDtos.get(i).getId());
            Assertions.assertEquals(expected[i].getCustomerId(), ordersDtos.get(i).getCustomerId());
            Assertions.assertEquals(expected[i].getOrderDate(), ordersDtos.get(i).getOrderDate());
            Assertions.assertEquals(expected[i].getProductType(), ordersDtos.get(i).getProductType());
            Assertions.assertEquals(expected[i].getOrderStatus(), ordersDtos.get(i).getOrderStatus());
        }
    }

    private ZonedDateTime toZonedDateTime(String datetimeString) {
        TimeZone tzSeoul = TimeZone.getTimeZone("Asia/Seoul");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(tzSeoul.toZoneId());
        return ZonedDateTime.parse(datetimeString, dateTimeFormatter);
    }

    private String getCurrentZonedDateTimeToString(ZonedDateTime currentTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(currentTime);
    }
}
