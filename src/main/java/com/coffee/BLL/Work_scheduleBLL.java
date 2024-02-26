package com.coffee.BLL;

import com.coffee.DAL.Work_scheduleDAL;
import com.coffee.DTO.Work_schedule;
import com.coffee.DTO.Work_schedule;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Work_scheduleBLL extends Manager<Work_schedule>{
    private Work_scheduleDAL work_scheduleDAL;

    public Work_scheduleBLL() {
        work_scheduleDAL = new Work_scheduleDAL();
    }

    public Work_scheduleDAL getWork_scheduleDAL() {
        return work_scheduleDAL;
    }

    public void setWork_scheduleDAL(Work_scheduleDAL work_scheduleDAL) {
        this.work_scheduleDAL = work_scheduleDAL;
    }

    public Object[][] getData() {
        return getData(work_scheduleDAL.searchWork_schedules());
    }

    public Pair<Boolean, String> addWork_schedule(Work_schedule work_schedule) {
        Pair<Boolean, String> result;

        result = exists(work_schedule);
        if(result.getKey()){
            return new Pair<>(false,result.getValue());
        }

        if (work_scheduleDAL.addWork_schedule(work_schedule) == 0)
            return new Pair<>(false, "Thêm lịch làm việc không thành công.");

        return new Pair<>(true, "Thêm lịch làm việc thành công.");
    }

    public Pair<Boolean, String> updateWork_schedule(Work_schedule work_schedule) {
        if (work_scheduleDAL.updateWork_schedule(work_schedule) == 0)
            return new Pair<>(false, "Cập nhật lịch làm việc không thành công.");

        return new Pair<>(true, "Cập nhật lịch làm việc thành công.");
    }

    public List<Work_schedule> searchWork_schedules(String... conditions) {
        return work_scheduleDAL.searchWork_schedules(conditions);
    }

    public List<Work_schedule> findWork_schedules(String key, String value) {
        List<Work_schedule> list = new ArrayList<>();
        List<Work_schedule> work_scheduleList = work_scheduleDAL.searchWork_schedules();
        for (Work_schedule work_schedule : work_scheduleList) {
            if (getValueByKey(work_schedule, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(work_schedule);
            }
        }
        return list;
    }

    public List<Work_schedule> findWork_schedulesBy(Map<String, Object> conditions) {
        List<Work_schedule> work_schedules =work_scheduleDAL.searchWork_schedules();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            work_schedules = findObjectsBy(entry.getKey(), entry.getValue(), work_schedules);
        return work_schedules;
    }

    public Pair<Boolean, String> exists(Work_schedule work_schedule) {
        List<Work_schedule> work_schedules = findWork_schedulesBy(Map.of(
                "staff_id", work_schedule.getStaff_id(),
                "date", work_schedule.getDate()
        ));

        if(!work_schedules.isEmpty()){
            return new Pair<>(true, "Nhân viên đã có lịch làm việc trong ngày.");
        }
        return new Pair<>(false, "");
    }

    @Override
    public Object getValueByKey(Work_schedule work_schedule, String key) {
        return switch (key) {
            case "id" -> work_schedule.getId();
            case "staff_id" -> work_schedule.getStaff_id();
            case "date" -> work_schedule.getDate();
            case "start_time" -> work_schedule.getStart_time();
            case "end_time" -> work_schedule.getEnd_time();
            default -> null;
        };
    }
}
