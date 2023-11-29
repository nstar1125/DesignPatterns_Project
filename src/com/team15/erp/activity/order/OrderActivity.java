package com.team15.erp.activity.order;

import com.holub.database.CSVExporter;
import com.holub.database.Table;
import com.holub.database.TableFactory;
import com.holub.text.ParseFailure;
import com.team15.erp.activity.Activity;
import com.team15.erp.dto.order.OrderStatus;
import com.team15.erp.dto.product.BookDto;
import com.team15.erp.dto.product.ProductStatus;
import com.team15.erp.dto.product.ProductType;
import com.team15.erp.dto.product.ShoesDto;
import com.team15.erp.model.order.Orders;
import com.team15.erp.model.product.Book;
import com.team15.erp.model.product.Shoes;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderActivity<Option> extends Activity<Option> {

    private int numberOfBooks = 0;
    private int numberOfShoes = 0;

    @Override
    public void before() {
        this.numberOfBooks = getAllBooks();
        this.numberOfShoes = getAllShoes();
        printBooks();
        printShoes();
    }

    @Override
    public void perform(final Option option) throws Exception {
        String inputOption = (String) option;
        String[] inputOptions = inputOption.split("-");

        Long customerId = Long.valueOf(inputOptions[0]);
        String productType = inputOptions[1];

        // order 생성 해주자
        createOrder(customerId, productType);
    }

    @Override
    public void showOptions() {
        System.out.println("고객 id와 주문할 상품 종류를 입력하세요(ex. 1-책)");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.nextLine();
    }

    private int getAllBooks() {
        Book book = new Book();

        return book.getNumberOfBooks();
    }

    private int getAllShoes() {
        Shoes shoes = new Shoes();

        return shoes.getNumberOfShoes();
    }

    private void printBooks() {
        System.out.println(String.format("현재 남아 있는 책은 %d 권 입니다.", this.numberOfBooks));
    }

    private void printShoes() {
        System.out.println(String.format("현재 남아 있는 신발은 %d 켤레 입니다.", this.numberOfShoes));
    }

    private String getCurrentZonedDateTimeToString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(ZonedDateTime.now());
    }

    private void createOrder(final Long customerId, final String productType) throws InputMismatchException {
        if (!validateOrder(productType)) {
            throw new InputMismatchException("잘못된 주문 입니다.");
        }

        try {
            Long nextOrderId = (long) getOrderTableSize();
            Object[] orderInfos = new Object[] {nextOrderId, customerId, getCurrentZonedDateTimeToString(), productType, OrderStatus.ORDER.getOrderStatus()};

            insertOrder(orderInfos);
            System.out.println("주문이 정상적으로 생성 되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateOrder(final String productType) {
        if (productType.equals(ProductType.BOOK.getProductType())) {
            return this.numberOfBooks >= 1;
        } else if (productType.equals(ProductType.SHOES.getProductType())) {
            return this.numberOfShoes >= 1;
        }

        return false;
    }

    private int getOrderTableSize() throws IOException, ParseFailure {
        Orders orders = new Orders();
        return orders.getOrderTableSize();
    }

    private void insertOrder(final Object[] infos) throws IOException, ParseFailure {
        Orders orders = new Orders();
        orders.insertOrder(infos);
    }
}
