package com.coffee.DTO;

public class Discount_Detail {
    private int discount_id;
    private int product_id;
    private double percent;

    public Discount_Detail() {
    }

    public Discount_Detail(int discount_id, int product_id, double percent) {
        this.discount_id = discount_id;
        this.product_id = product_id;
        this.percent = percent;
    }

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return discount_id + " | " +
                product_id + " | " +
                percent;
    }
}
