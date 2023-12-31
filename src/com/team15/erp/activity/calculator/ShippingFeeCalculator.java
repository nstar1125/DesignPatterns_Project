package com.team15.erp.activity.calculator;

import com.team15.erp.activity.Activity;

import com.team15.erp.dto.product.BookDto;
import com.team15.erp.model.product.ShippingFeeVisitor;
import com.team15.erp.dto.product.ShoesDto;
import com.team15.erp.model.product.Book;
import com.team15.erp.model.product.Shoes;

import java.util.InputMismatchException;

public class ShippingFeeCalculator<Option> extends Activity<Option> {

    @Override
    public void before() {
        System.out.println("배송비 계산 모드");
    }

    @Override
    public void perform(Option option) throws Exception {
        Shoes shoesMapper = new Shoes();
        Book bookMapper = new Book();
        ShippingFeeVisitor shipCal = new ShippingFeeVisitor();
        String[] in;

        switch ((Integer) option) {
            case 1:
                System.out.println("상품명, 브랜드를 입력해주세요. ex) 에어포스 나이키");
                scanner.skip("[\\r\\n]+");
                in = scanner.nextLine().split(" ");

                Object shoes = shoesMapper.selectByNameBrand(in[0].trim(), in[1].trim());
                System.out.println("\n=================================================");
                System.out.println("신발 배송 요금 정보");
                System.out.println("- 가격 200,000원 초과 시, 5% 보험금 추가");
                System.out.println("=================================================");

                shipCal.visit((ShoesDto) shoes);

                break;
            case 2:
                System.out.println("상품명, 작가명을 입력해주세요. ex) 요리의정신 박영복");
                scanner.skip("[\\r\\n]+");
                in = scanner.nextLine().split(" ");


                Object book = bookMapper.selectAllByNameWriter(in[0].trim(), in[1].trim()).get(0);

                System.out.println("\n=================================================");
                System.out.println("책 배송 요금 정보");
                System.out.println("- 페이지 200p 초과 시, 무게추가금 500원 추가");
                System.out.println("=================================================");

                shipCal.visit((BookDto) book);

                break;
        }
    }

    @Override
    public void showOptions() {
        System.out.println("배송비를 확인할 상품 종류 번호를 입력해주세요.");
        System.out.println("1 : 신발");
        System.out.println("2 : 책");
        System.out.print("> ");
    }

    @Override
    public Object scanOption() throws InputMismatchException {
        return scanner.nextInt();
    }
}
