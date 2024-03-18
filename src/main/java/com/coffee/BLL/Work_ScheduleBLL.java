package com.coffee.BLL;

import com.coffee.DAL.Work_ScheduleDAL;
import com.coffee.DTO.Account;
import com.coffee.DTO.Work_Schedule;
import javafx.util.Pair;

import java.util.*;

public class Work_ScheduleBLL extends Manager<Work_Schedule> {
    private Work_ScheduleDAL work_scheduleDAL;

    public Work_ScheduleBLL() {
        work_scheduleDAL = new Work_ScheduleDAL();
    }

    public Work_ScheduleDAL getWork_scheduleDAL() {
        return work_scheduleDAL;
    }

    public void setWork_scheduleDAL(Work_ScheduleDAL work_scheduleDAL) {
        this.work_scheduleDAL = work_scheduleDAL;
    }

    public Object[][] getData() {
        return getData(work_scheduleDAL.searchWork_schedules());
    }

    public Pair<Boolean, String> addWork_schedule(List<Work_Schedule> work_schedules) {
        Pair<Boolean, String> result;
        for (Work_Schedule work_schedule : work_schedules) {
            result = exists(work_schedule);
            if (result.getKey()) {
                return new Pair<>(false, result.getValue());
            }
            if (work_scheduleDAL.addWork_schedule(work_schedule) == 0)
                return new Pair<>(false, "Thêm lịch làm việc không thành công.");
        }
        return new Pair<>(true, "Thêm lịch làm việc thành công.");
    }

    public Pair<Boolean, String> updateWork_schedule(Work_Schedule work_schedule) {
        if (work_scheduleDAL.updateWork_schedule(work_schedule) == 0)
            return new Pair<>(false, "Cập nhật lịch làm việc không thành công.");

        return new Pair<>(true, "Cập nhật lịch làm việc thành công.");
    }

    public Pair<Boolean, String> deleteWork_schedule(Work_Schedule work_schedule) {
        if (work_scheduleDAL.deleteWork_schedule("id = " + work_schedule.getId()) == 0)
            return new Pair<>(false, "Xoá lịch không thành công.");

        return new Pair<>(true, "Xoá lịch thành công.");
    }

    public List<Work_Schedule> searchWork_schedules(String... conditions) {
        return work_scheduleDAL.searchWork_schedules(conditions);
    }

    public List<Work_Schedule> findWork_schedules(String key, String value) {
        List<Work_Schedule> list = new ArrayList<>();
        List<Work_Schedule> work_scheduleList = work_scheduleDAL.searchWork_schedules();
        for (Work_Schedule work_schedule : work_scheduleList) {
            if (getValueByKey(work_schedule, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(work_schedule);
            }
        }
        return list;
    }

    public List<Work_Schedule> findWork_schedulesBy(Map<String, Object> conditions) {
        List<Work_Schedule> work_schedules = work_scheduleDAL.searchWork_schedules();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            work_schedules = findObjectsBy(entry.getKey(), entry.getValue(), work_schedules);
        return work_schedules;
    }

    public Pair<Boolean, String> exists(Work_Schedule work_schedule) {
        List<Work_Schedule> work_schedules = findWork_schedulesBy(Map.of(
                "staff_id", work_schedule.getStaff_id(),
                "date", work_schedule.getDate(),
                "shift", work_schedule.getShift()
        ));

        if (!work_schedules.isEmpty()) {
            return new Pair<>(true, "Nhân viên đã có lịch làm việc trong ngày.");
        }
        return new Pair<>(false, "");
    }

    @Override
    public Object getValueByKey(Work_Schedule work_schedule, String key) {
        return switch (key) {
            case "id" -> work_schedule.getId();
            case "staff_id" -> work_schedule.getStaff_id();
            case "date" -> work_schedule.getDate();
            case "check_in" -> work_schedule.getCheck_in();
            case "check_out" -> work_schedule.getCheck_out();
            case "shift" -> work_schedule.getShift();
            default -> null;
        };
    }
}
