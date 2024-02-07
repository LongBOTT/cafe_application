package com.coffee.DTO;

public class Receipt_Detail {
    private int receipt_id;
    private int product_id;
    private double quantity;
    private double price;
    private int discount_id;

    public Receipt_Detail() {
    }

    public Receipt_Detail(int receipt_id, int product_id, double quantity, double price, int discount_id) {
        this.receipt_id = receipt_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
        this.discount_id = discount_id;
    }

    public int getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(int receipt_id) {
        this.receipt_id = receipt_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(int discount_id) {
        this.discount_id = discount_id;
    }

    @Override
    public String toString() {
        return receipt_id + " | " +
                product_id + " | " +
                quantity + " | " +
                price + " | " +
                discount_id;
    }
}
