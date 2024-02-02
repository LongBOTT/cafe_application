package com.coffee.DTO;

public class Account {
    private int id;
    private String username;
    private String password;
    private int staff_id;
    private int role_id;

    public Account() {
    }

    public Account(int id, String username, String password, int staff_id, int role_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.staff_id = staff_id;
        this.role_id = role_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return id + " | " +
                username + " | " +
                staff_id + " | " +
                role_id;
    }
}
