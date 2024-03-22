package com.coffee.DTO;

public class Bonus {
    private int id;
    private String name;
    private double bonus_amount;
    private int bonus_type;

    public Bonus() {
    }

    public Bonus(int id, String name, double bonus_amount, int bonus_type) {
        this.id = id;
        this.name = name;
        this.bonus_amount = bonus_amount;
        this.bonus_type = bonus_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBonus_amount() {
        return bonus_amount;
    }

    public void setBonus_amount(double bonus_amount) {
        this.bonus_amount = bonus_amount;
    }

    public int getBonus_type() {
        return bonus_type;
    }

    public void setBonus_type(int bonus_type) {
        this.bonus_type = bonus_type;
    }

    @Override
    public String toString() {
        return id + " | " +
                name + " | " +
                bonus_amount + " | " +
                bonus_type;
    }
}
