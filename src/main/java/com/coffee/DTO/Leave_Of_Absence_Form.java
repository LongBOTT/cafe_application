package com.coffee.DTO;

import java.util.Date;

public class Leave_Of_Absence_Form {
    private int id;
    private int staff_id;
    private Date date;
    private Date start_date;
    private Date end_date;
    private String reason;
    private int status;

    public Leave_Of_Absence_Form() {
    }

    public Leave_Of_Absence_Form(int id, int staff_id, Date date, Date start_date, Date end_date, String reason, int status) {
        this.id = id;
        this.staff_id = staff_id;
        this.date = date;
        this.start_date = start_date;
        this.end_date = end_date;
        this.reason = reason;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String status1 = status == 0 ? "Chưa duyệt" : status == 1 ? "Duyệt" : "Không duyệt";
        return id + " | " +
                staff_id + " | " +
                date + " | " +
                start_date + " | " +
                end_date + " | " +
                status1;
    }
}
