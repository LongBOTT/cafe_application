package com.coffee.DTO;

import java.time.LocalDateTime;

public class Role_Detail_Deduction {
    private int role_id;
    private int staff_id;
    private LocalDateTime entry_date;
    private int deduction_id;

    public Role_Detail_Deduction() {
    }

    public Role_Detail_Deduction(int role_id, int staff_id, LocalDateTime entry_date, int deduction_id) {
        this.role_id = role_id;
        this.staff_id = staff_id;
        this.entry_date = entry_date;
        this.deduction_id = deduction_id;
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

    public LocalDateTime getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(LocalDateTime entry_date) {
        this.entry_date = entry_date;
    }

    public int getDeduction_id() {
        return deduction_id;
    }

    public void setDeduction_id(int deduction_id) {
        this.deduction_id = deduction_id;
    }

    @Override
    public String toString() {
        return getRole_id() + " | " +
                getStaff_id() + " | " +
                getEntry_date() + " | " +
                getDeduction_id();
    }
}
