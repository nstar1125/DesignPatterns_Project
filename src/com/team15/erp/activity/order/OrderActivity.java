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

    private static final String fileOutputName = "/Users/sonmingyu/학교자료/설계패턴/HolubSQL/src/com/holub/database/Dbase";
    private static final String[] orderColumns = new String[] {"id", "customer_id", "order_date", "order_status", "order_product_ids"};
    private List<BookDto> bookDtos;
    private List<ShoesDto> shoesDtos;

    @Override
    public void before() {
        this.bookDtos = getAllBooks();
        this.shoesDtos = getAllShoes();

        printBooks();
        printShoes();
    }

    @Override
    public void perform(final Option option) throws Exception {
        String inputOption = (String) option;
        String[] inputOptions = inputOption.split("-");

        Long customerId = Long.valueOf(inputOptions[0]);
        String productType = inputOptions[1];
        Long productId = Long.valueOf(inputOptions[2]);

        // order 생성 해주자
        createOrder(customerId, productType, productId);

    }

    @Override
    public void showOptions() {
        System.out.println("고객 id와 주문할 상품 종류, id를 입력하세요(ex. 1-책-1)");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.nextLine();
    }

    private List<BookDto> getAllBooks() {
        Book book = new Book();
        List<BookDto> bookDtos = new ArrayList<>();

        try {
            bookDtos = book.getAllBooks()
                    .stream()
                    .filter(bookDto -> bookDto.getIsSale().equals(ProductStatus.SALE.getProductStatus()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            System.out.println("Data를 불러오는 과정에서 오류가 발생했습니다.");
        }

        return bookDtos;
    }

    private List<ShoesDto> getAllShoes() {
        Shoes shoes = new Shoes();
        List<ShoesDto> shoesDtos = new ArrayList<>();

        try {
            shoesDtos = shoes.getAllShoes()
                    .stream()
                    .filter(shoesDto -> shoesDto.getIsSale().equals(ProductStatus.SALE.getProductStatus()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Data를 불러오는 과정에서 오류가 발생했습니다.");
        }

        return shoesDtos;
    }

    private void printBooks() {
        for (BookDto bookDto : this.bookDtos) {
            System.out.println(bookDto.toString());
        }
    }

    private void printShoes() {
        for (ShoesDto shoesDto : this.shoesDtos) {
            System.out.println(shoesDto.toString());
        }
    }

    private String getCurrentZonedDateTimeToString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(ZonedDateTime.now());
    }

    private void createOrder(final Long customerId, final String productType, final Long id) throws InputMismatchException {
        if (!validateOrder(productType, id)) {
            throw new InputMismatchException("잘못된 주문 입니다.");
        }

        try {
            Long nextOrderId = 0L;
            String orderProduct = productType + "-" + id;
            Object[] orderInfos = new Object[] {nextOrderId, customerId, getCurrentZonedDateTimeToString(), OrderStatus.ORDER.getOrderStatus(), orderProduct};
            Table order = null;

            if (fileExists(fileOutputName + "/orders.csv")) {
                order = selectOrderTable();

                Object[][] rows = order.readOnlyCursor().rows();

                for ()

                orderInfos[0] = (long) order.readOnlyCursor().rows().length;
                order.insert(orderInfos);
//                insertOrder(orderInfos);
            }
            else {
                order = createOrderTable();
                order.insert(orderColumns, orderInfos);
            }
            // 단계 2: Writer 생성
            Writer out = new FileWriter(fileOutputName + "/orders.csv", true);

            // 단계 3: CSVExporter 생성
            CSVExporter csvExporter = new CSVExporter(out);

            // 단계 4: 테이블 내보내기
            order.export(csvExporter);

            // 단계 5: Writer 닫기
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateOrder(final String productType, final Long id) {
        if (productType.equals(ProductType.BOOK.getProductType())) {
            int count = (int) bookDtos.stream()
                    .filter(bookDto -> Objects.equals(bookDto.getId(), id)).count();
            return count >= 1;
        } else if (productType.equals(ProductType.SHOES.getProductType())) {
            int count = (int) shoesDtos.stream()
                    .filter(shoesDto -> Objects.equals(shoesDto.getId(), id)).count();
            return count >= 1;
        }

        return false;
    }

    private Table createOrderTable() {
        return createNewTable();
    }

    private boolean fileExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    private int getOrderTableSize() throws IOException, ParseFailure {
        Orders orders = new Orders();
        return orders.getOrderTableSize();
    }

    private Table selectOrderTable() throws IOException, ParseFailure {
        Orders orders = new Orders();
        return orders.selectOrderTable();
    }

    private void insertOrder(final Object[] infos) throws IOException, ParseFailure {
        Orders orders = new Orders();
        orders.insertOrder(infos);
    }

    private Table createNewTable() {
        return TableFactory.create("orders", orderColumns);
    }
}
