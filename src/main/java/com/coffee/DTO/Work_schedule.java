package com.coffee.DTO;

import java.util.Date;

public class Work_schedule {
    private int id;
    private int staff_id;
    private Date date;
    private String start_time;
    private String end_time;

    public Work_schedule() {
    }

    public Work_schedule(int id, int staff_id, Date date, String start_time, String end_time) {
        this.id = id;
        this.staff_id = staff_id;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return id + " | " +
                staff_id + " | " +
                date + " | " +
                start_time + " | " +
                end_time;
    }
}
