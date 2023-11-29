package com.team15.erp.activity.calculator;

import com.team15.erp.activity.Activity;
import com.team15.erp.entity.product.Book;
import com.team15.erp.entity.product.Shoes;
import com.team15.erp.entity.product.StockVisitor;

import java.util.InputMismatchException;

public class StockChecker<Option> extends Activity<Option> {

    @Override
    public void before() {
        System.out.println("재고 확인 모드");
    }

    @Override
    public void perform(Option option) throws Exception {
        StockVisitor stockCheck = new StockVisitor();
        String[] in;
        switch ((Integer) option) {
            case 1: //  신발: 상품명, 상품타입, 브랜드, 사이즈로 재고 조회
                System.out.println("상품명, 브랜드, 사이즈를 입력해주세요. ex) 에어포스 나이키 250");
                scanner.skip("[\\r\\n]+");
                 in = scanner.nextLine().split(" ");

                Shoes shoes = new Shoes();
                shoes.setProductName(in[0].trim());
                shoes.setBrand(in[1].trim());
                shoes.setSize(Integer.parseInt(in[2]));

                stockCheck.visitShoes(shoes);

            case 2: // 책: 상품명, 상품타입, 작가명으로 재고 조회
                System.out.println("상품명, 작가명을 입력해주세요. ex) 요리의정신 박영복");
                scanner.skip("[\\r\\n]+");
                in = scanner.nextLine().split(" ");

                Book book = new Book();
                book.setProductName(in[0].trim());
                book.setWriter(in[1].trim());

                stockCheck.visitBook(book);
        }

    }

    @Override
    public void showOptions() {
        System.out.println("재고를 확인할 상품 번호를 입력해주세요.");
        System.out.print("1 : 신발");
        System.out.print("2 : 책");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.nextInt();
    }
}
