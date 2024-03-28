package com.coffee.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payroll_Detail {
    private int payroll_id;
    private int staff_id;
    private int role_id;
    private LocalDateTime entry_date;
    private double hours_amount;
    private BigDecimal bonus_amount;
    private BigDecimal deduction_amount;
    private BigDecimal salary_amount;
    private boolean status;

    public Payroll_Detail() {
    }

    public Payroll_Detail(int payroll_id, int staff_id, double hours_amount, BigDecimal bonus_amount, BigDecimal deduction_amount, BigDecimal salary_amount, boolean status, int role_id, LocalDateTime entry_date) {
        this.payroll_id = payroll_id;
        this.staff_id = staff_id;
        this.role_id = role_id;
        this.entry_date = entry_date;
        this.hours_amount = hours_amount;
        this.bonus_amount = bonus_amount;
        this.deduction_amount = deduction_amount;
        this.salary_amount = salary_amount;
        this.status = status;
    }

    public int getPayroll_id() {
        return payroll_id;
    }

    public void setPayroll_id(int payroll_id) {
        this.payroll_id = payroll_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public LocalDateTime getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(LocalDateTime entry_date) {
        this.entry_date = entry_date;
    }

    public double getHours_amount() {
        return hours_amount;
    }

    public void setHours_amount(double hours_amount) {
        this.hours_amount = hours_amount;
    }

    public BigDecimal getBonus_amount() {
        return bonus_amount;
    }

    public void setBonus_amount(BigDecimal bonus_amount) {
        this.bonus_amount = bonus_amount;
    }

    public BigDecimal getDeduction_amount() {
        return deduction_amount;
    }

    public void setDeduction_amount(BigDecimal deduction_amount) {
        this.deduction_amount = deduction_amount;
    }

    public BigDecimal getSalary_amount() {
        return salary_amount;
    }

    public void setSalary_amount(BigDecimal salary_amount) {
        this.salary_amount = salary_amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
//        String status1 = status ? "Đã trả" : "Tạm tính";
        return payroll_id + " | " +
                staff_id + " | " +
                "staff_name" + " | " +
                salary_amount + " | " +
                status;
    }
}
