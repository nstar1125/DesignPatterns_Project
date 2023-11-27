package com.team15.erp.model.product;

public class Book extends Product {

    private String writer;
    private String numberOfPage;

    public String getWriter() {
        return writer;
    }

    public void setWriter(final String writer) {
        this.writer = writer;
    }

    public String getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(final String numberOfPage) {
        this.numberOfPage = numberOfPage;
    }
}
