package com.coffee.DTO;

import java.util.Date;

public class Receipt {
    private int id;
    private int staff_id;
    private double total;
    private Date invoice_date;
    private double received;
    private double excess;

    public Receipt() {
    }

    public Receipt(int id, int staff_id, double total, Date invoice_date, double received, double excess) {
        this.id = id;
        this.staff_id = staff_id;
        this.total = total;
        this.invoice_date = invoice_date;
        this.received = received;
        this.excess = excess;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(Date invoice_date) {
        this.invoice_date = invoice_date;
    }

    public double getReceived() {
        return received;
    }

    public void setReceived(double received) {
        this.received = received;
    }

    public double getExcess() {
        return excess;
    }

    public void setExcess(double excess) {
        this.excess = excess;
    }

    @Override
    public String toString() {
        return id + " | " +
                staff_id + " | " +
                total + " | " +
                invoice_date;
    }
}
