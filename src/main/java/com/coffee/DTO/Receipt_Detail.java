package com.coffee.DTO;

import com.coffee.utils.VNString;

public class Receipt_Detail {
    private int receipt_id;
    private int product_id;
    private String size;
    private double quantity;
    private double price;
    private double reduced_price;
    private String notice;

    public Receipt_Detail() {
    }

    public Receipt_Detail(int receipt_id, int product_id, String size, double quantity, double price, double reduced_price,String notice) {
        this.receipt_id = receipt_id;
        this.product_id = product_id;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.reduced_price = reduced_price;
        this.notice = notice;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public double getReduced_price() {
        return reduced_price;
    }

    public void setReduced_price(double reduced_price) {
        this.reduced_price = reduced_price;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return receipt_id + " | " +
                product_id + " | " +
                size + " | " +
                quantity + " | " +
                VNString.currency(price) + " | " +
                VNString.currency(reduced_price) + " | " +
                notice;
    }
}
