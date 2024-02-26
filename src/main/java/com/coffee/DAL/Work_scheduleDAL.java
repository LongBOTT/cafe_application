package com.coffee.DAL;

import com.coffee.DTO.Work_schedule;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Work_scheduleDAL extends Manager{
    public Work_scheduleDAL() {
        super("work_schedule",
                List.of("id",
                        "staff_id",
                        "date",
                        "start_time",
                        "end_time"));
    }

    public List<Work_schedule> convertToWork_schedules(List<List<String>> data ){
        return convert(data, row -> {
            try {
                return new Work_schedule(
                        Integer.parseInt(row.get(0)), // id
                        Integer.parseInt(row.get(1)), // staff_id
                        Date.valueOf(row.get(2)), // date
                        row.get(3), // start_time
                        row.get(4) // end_time
                );
            } catch (Exception e) {
                System.out.println("Error occurred in RoleDAL.convertToWork_schedule(): " + e.getMessage());
            }
            return new Work_schedule();
        });
    }
    public int addWork_schedule(Work_schedule work_schedule) {
        try {
            return create(work_schedule.getId(),
                    work_schedule.getStaff_id(),
                    work_schedule.getDate(),
                    work_schedule.getStart_time(),
                    work_schedule.getEnd_time()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Work_scheduleDAL.addWork_schedule(): " + e.getMessage());
        }
        return 0;
    }
    public int updateWork_schedule(Work_schedule work_schedule) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(work_schedule.getId());
            updateValues.add(work_schedule.getStaff_id());
            updateValues.add(work_schedule.getDate());
            updateValues.add(work_schedule.getStart_time());
            updateValues.add(work_schedule.getEnd_time());
            return update(updateValues, "id = " + work_schedule.getId());
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Work_scheduleDAL.updateWork_schedule(): " + e.getMessage());
        }
        return 0;
    }

    public List<Work_schedule> searchWork_schedules(String... conditions) {
        try {
            return convertToWork_schedules(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Work_scheduleDAL.searchWork_schedules(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
