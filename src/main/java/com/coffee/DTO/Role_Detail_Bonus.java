package com.coffee.DTO;

import java.time.LocalDateTime;
import java.util.Date;

public class Role_Detail_Bonus {
    private int role_id;
    private int staff_id;
    private LocalDateTime entry_date;
    private int bonus_id;

    public Role_Detail_Bonus() {
    }

    public Role_Detail_Bonus(int role_id, int staff_id, LocalDateTime entry_date, int bonus_id) {
        this.role_id = role_id;
        this.staff_id = staff_id;
        this.entry_date = entry_date;
        this.bonus_id = bonus_id;
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

    public int getBonus_id() {
        return bonus_id;
    }

    public void setBonus_id(int bonus_id) {
        this.bonus_id = bonus_id;
    }

    @Override
    public String toString() {
        return getRole_id() + " | " +
                getStaff_id() + " | " +
                getEntry_date() + " | " +
                getBonus_id();
    }
}
