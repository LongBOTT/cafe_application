package com.coffee.DTO;

import java.util.Date;

public class Staff {
    private int id;
    private String staffNo;
    private String name;
    private boolean gender;
    private Date birthdate;
    private String phone;
    private String address;
    private String email;
    private double hourlyWage;
    private boolean deleted;

    public Staff() {
    }

    public Staff(int id, String staffNo, String name, boolean gender, Date birthdate, String phone,
                 String address, String email, double hourlyWage, boolean deleted) {
        this.id = id;
        this.staffNo = staffNo;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.hourlyWage = hourlyWage;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        String gender1 = gender? "Nam" : "Nữ";
        return id + " | " +
                staffNo + " | " +
                name + " | " +
                gender1 + " | " +
                birthdate + " | " +
                phone + " | " +
                address + " | " +
                email + " | " +
                hourlyWage + " | " +
                deleted;
    }
}
