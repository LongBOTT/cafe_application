package com.coffee.DTO;

import java.util.Date;

public class Export_Note {
    private int id;
    private int staff_id;
    private double total;
    private Date invoice_date;

    public Export_Note() {
    }

    public Export_Note(int id, int staff_id, double total, Date invoice_date) {
        this.id = id;
        this.staff_id = staff_id;
        this.total = total;
        this.invoice_date = invoice_date;
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

    @Override
    public String toString() {
        return id + " | " +
                staff_id + " | " +
                total + " | " +
                invoice_date;
    }
}
