package com.coffee.DTO;

public class Material {
    private int id;
    private String name;
    private int supplier_id;
    private double remain;
    private String unit;
    private boolean deleted;

    public Material() {
    }

    public Material(int id, String name, int supplier_id, double remain, String unit, boolean deleted) {
        this.id = id;
        this.name = name;
        this.supplier_id = supplier_id;
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

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
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
                supplier_id + " | " +
                remain + " | " +
                unit + " | " +
                deleted;
    }
}
