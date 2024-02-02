package com.coffee.DTO;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private String unit;
    private String image;
    private boolean deleted;

    public Product() {
    }

    public Product(int id, String name, String category, double price, String unit, String image, boolean deleted) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.unit = unit;
        this.image = image;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    // Getters and Setters

    @Override
    public String toString() {
        return id + " | " +
                name + " | " +
                category + " | " +
                price + " | " +
                unit + " | " +
                image + " | " +
                deleted;
    }
}
