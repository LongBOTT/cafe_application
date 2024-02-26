package com.coffee.DTO;

import java.util.Date;

public class Role_detail {
    private int role_id;
    private int staff_id;
    private Date entry_date;
    private double salary;
    private double hourly_wage;

    public Role_detail() {
    }

    public Role_detail(int role_id, int staff_id, Date entry_date, double salary, double hourly_wage) {
        this.role_id = role_id;
        this.staff_id = staff_id;
        this.entry_date = entry_date;
        this.salary = salary;
        this.hourly_wage = hourly_wage;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getHourly_wage() {
        return hourly_wage;
    }

    public void setHourly_wage(double hourly_wage) {
        this.hourly_wage = hourly_wage;
    }

    @Override
    public String toString() {
        return role_id + " | " +
                staff_id + " | " +
                entry_date + " | " +
                salary + " | " +
                hourly_wage;
    }
}
