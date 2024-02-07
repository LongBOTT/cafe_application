package com.coffee.DTO;

public class Recipe {
    private int product_id;
    private int material_id;
    private double quantity;

    public Recipe() {
    }

    public Recipe(int product_id, int material_id, double quantity) {
        this.product_id = product_id;
        this.material_id = material_id;
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product_id + " | " +
                material_id + " | " +
                quantity;
    }
}
