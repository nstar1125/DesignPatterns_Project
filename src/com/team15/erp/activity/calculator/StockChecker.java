package com.team15.erp.activity.calculator;

import com.team15.erp.activity.Activity;
import com.team15.erp.entity.product.Book;
import com.team15.erp.entity.product.Shoes;
import com.team15.erp.entity.product.StockVisitor;
import com.team15.erp.model.product.BookMapper;
import com.team15.erp.model.product.ShoesMapper;

import java.util.InputMismatchException;
import java.util.List;

public class StockChecker<Option> extends Activity<Option> {

    @Override
    public void before() {
        System.out.println("재고 확인 모드");
    }

    @Override
    public void perform(Option option) throws Exception {
        ShoesMapper shoesMapper = new ShoesMapper();
        BookMapper bookMapper = new BookMapper();
        StockVisitor stockCheck = new StockVisitor();
        String[] in;

        switch ((Integer) option) {
            case 1: //  신발: 상품명, 브랜드, 사이즈로 재고 조회
                System.out.println("상품명, 브랜드, 사이즈를 입력해주세요. ex) 에어포스 나이키 250");
                scanner.skip("[\\r\\n]+");
                 in = scanner.nextLine().split(" ");

                List<Shoes> shoeStock = shoesMapper.selectAllByNameBrandSize(
                        in[0].trim(),
                        in[1].trim(),
                        Integer.parseInt(in[2]));

                System.out.println("\n=================================================");
                System.out.println("창고에 남아 있는 신발");
                System.out.println("=================================================");
                for (Shoes shoes : shoeStock) {
                    stockCheck.visit(shoes);
                }
                break;

            case 2: // 책: 상품명, 작가명으로 재고 조회
                System.out.println("상품명, 작가명을 입력해주세요. ex) 요리의정신 박영복");
                scanner.skip("[\\r\\n]+");
                in = scanner.nextLine().split(" ");

                List<Book> bookStock = bookMapper.selectAllByNameWriter(
                        in[0].trim(),
                        in[1].trim());

                System.out.println("\n=================================================");
                System.out.println("창고에 남아 있는 책");
                System.out.println("=================================================");
                for (Book book : bookStock) {
                    stockCheck.visit(book);
                }
                break;
        }

    }

    @Override
    public void showOptions() {
        System.out.println("재고를 확인할 상품 종류 번호를 입력해주세요.");
        System.out.println("1 : 신발");
        System.out.println("2 : 책");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.nextInt();
    }
}
