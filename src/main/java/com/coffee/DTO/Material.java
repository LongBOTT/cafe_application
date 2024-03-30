package com.coffee.DTO;

public class Material {
    private int id;
    private String name;
    private double remain;
    private String unit;
    private boolean deleted;

    public Material() {
    }

    public Material(int id, String name, double remain, String unit, boolean deleted) {
        this.id = id;
        this.name = name;
        this.remain = remain;
        this.unit = unit;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
                deleted;
    }
}
