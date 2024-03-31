package com.coffee.DTO;

public class Material {
    private int id;
    private String name;
    private double remain;
    private double minRemain;
    private double maxRemain;
    private String unit;
    private double unit_price;
    private boolean deleted;

    public Material() {
    }

    public Material(int id, String name, double remain, double minRemain, double maxRemain, String unit, double unit_price, boolean deleted) {
        this.id = id;
        this.name = name;
        this.remain = remain;
        this.minRemain = minRemain;
        this.maxRemain = maxRemain;
        this.unit = unit;
        this.unit_price = unit_price;
        this.deleted = deleted;
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

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    public double getMinRemain() {
        return minRemain;
    }

    public void setMinRemain(double minRemain) {
        this.minRemain = minRemain;
    }

    public double getMaxRemain() {
        return maxRemain;
    }

    public void setMaxRemain(double maxRemain) {
        this.maxRemain = maxRemain;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return id + " | " +
                name + " | " +
                remain + " | " +
                unit + " | " +
                unit_price + " | " +
                deleted;
    }
}
