package com.coffee.DTO;

public class Payroll {
    private int id;
    private int staff_id;
    private int month;
    private int year;
    private double hours_amount;
    private double bonus_amount;
    private double deduction_amount;
    private double salary_amount;

    public Payroll() {
    }

    public Payroll(int id, int staff_id, int month, int year, double hours_amount, double bonus_amount, double deduction_amount, double salary_amount) {
        this.id = id;
        this.staff_id = staff_id;
        this.month = month;
        this.year = year;
        this.hours_amount = hours_amount;
        this.bonus_amount = bonus_amount;
        this.deduction_amount = deduction_amount;
        this.salary_amount = salary_amount;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getHours_amount() {
        return hours_amount;
    }

    public void setHours_amount(double hours_amount) {
        this.hours_amount = hours_amount;
    }

    public double getBonus_amount() {
        return bonus_amount;
    }

    public void setBonus_amount(double bonus_amount) {
        this.bonus_amount = bonus_amount;
    }

    public double getDeduction_amount() {
        return deduction_amount;
    }

    public void setDeduction_amount(double deduction_amount) {
        this.deduction_amount = deduction_amount;
    }

    public double getSalary_amount() {
        return salary_amount;
    }

    public void setSalary_amount(double salary_amount) {
        this.salary_amount = salary_amount;
    }

    @Override
    public String toString() {
        return id + " | " +
                staff_id + " | " +
                year + " | " +
                month + " | " +
                hours_amount + " | " +
                bonus_amount + " | " +
                deduction_amount + " | " +
                salary_amount;
    }
}
