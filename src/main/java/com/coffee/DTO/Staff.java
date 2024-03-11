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
    private boolean deleted;
    private String role ;
    public Staff() {
    }

    public Staff(int id, String staffNo, String name, boolean gender, Date birthdate, String phone,
                 String address, String email, boolean deleted) {
        this.id = id;
        this.staffNo = staffNo;
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.deleted = deleted;
    }
//
//    public Staff(int id, String staffNo, String name, boolean gender, Date birthdate, String phone,
//                String role, boolean deleted) {
//        this.id = id;
//        this.staffNo = staffNo;
//        this.name = name;
//        this.gender = gender;
//        this.birthdate = birthdate;
//        this.phone = phone;
//        this.role= role;
//        this.deleted = deleted;
//
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        String gender1 = gender? "Nam" : "Nữ";
        return id + " | " + // stt nv
                staffNo + " | " +  // mã nv
                name + " | " + // tên
                gender1 + " | " + // giới tính
                birthdate + " | " + // ngày sinh
//                role + " | " +
                phone + " | " + // số điện thoại
                address + " | " + // địa chỉ
                email + " | " ; // email
//                deleted; // trạng thái
    }


}
