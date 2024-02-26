package com.coffee.BLL;

import com.coffee.DAL.PayrollDAL;
import com.coffee.DTO.Payroll;
import javafx.util.Pair;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayrollBLL extends Manager<Payroll>{
    private PayrollDAL payrollDAL;

    public PayrollBLL() {
        payrollDAL = new PayrollDAL();
    }

    public PayrollDAL getPayrollDAL() {
        return payrollDAL;
    }

    public void setPayrollDAL(PayrollDAL payrollDAL) {
        this.payrollDAL = payrollDAL;
    }

    public Object[][] getData() {
        return getData(payrollDAL.searchPayrolls());
    }

    public Pair<Boolean, String> addPayroll(Payroll payroll) {
        Pair<Boolean, String> result;

        result = exists(payroll);
        if(result.getKey()){
            return new Pair<>(false,result.getValue());
        }

        if (payrollDAL.addPayroll(payroll) == 0)
            return new Pair<>(false, "Thêm bảng lương không thành công.");

        return new Pair<>(true, "Thêm bảng lương thành công.");
    }

    public Pair<Boolean, String> updatePayroll(Payroll payroll) {
        if (payrollDAL.updatePayroll(payroll) == 0)
            return new Pair<>(false, "Cập nhật bảng lương không thành công.");

        return new Pair<>(true, "Cập nhật bảng lương thành công.");
    }

    public List<Payroll> searchPayrolls(String... conditions) {
        return payrollDAL.searchPayrolls(conditions);
    }

    public List<Payroll> findPayrolls(String key, String value) {
        List<Payroll> list = new ArrayList<>();
        List<Payroll> payrollList = payrollDAL.searchPayrolls();
        for (Payroll payroll : payrollList) {
            if (getValueByKey(payroll, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(payroll);
            }
        }
        return list;
    }

    public List<Payroll> findPayrollsBy(Map<String, Object> conditions) {
        List<Payroll> payrolls =payrollDAL.searchPayrolls();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            payrolls = findObjectsBy(entry.getKey(), entry.getValue(), payrolls);
        return payrolls;
    }

    public Pair<Boolean, String> exists(Payroll payroll) {
        List<Payroll> payrolls = findPayrollsBy(Map.of(
                "staff_id", payroll.getStaff_id(),
                "month", payroll.getMonth(),
                "year", payroll.getYear()
        ));

        if(!payrolls.isEmpty()){
            return new Pair<>(true, "Bảng lương nhân viên trong tháng này đã tồn tại.");
        }
        return new Pair<>(false, "");
    }
    public Pair<Boolean, String> validateMonth(String month) {
    if(month.isBlank())
        return new Pair<>(false,"Tháng không được để trống");
    if(!checkMonth(month))
        return new Pair<>(false,"Tháng phải là số nguyên và nằm trong khoảng từ 1 đến 12");
    return new Pair<>(true,"Tháng hợp lệ");
    }

    public Pair<Boolean, String> validateYear(String year) {
        if(year.isBlank())
            return new Pair<>(false,"Năm không được để trống");
        if(!checkYear(year))
            return new Pair<>(false,"Năm phải là số nguyên và phải lớn hơn bằng năm hiện tại");
        return new Pair<>(true,"Tháng hợp lệ");
    }
    public boolean checkMonth(String str) {
        try {
            int number = Integer.parseInt(str);
            return number >= 0 && number <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public  boolean checkYear(String str) {
        try {
            int year = Integer.parseInt(str);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return year >= currentYear && year < 9999;
        }catch(NumberFormatException e){
            return false;
        }
    }
    @Override
    public Object getValueByKey(Payroll payroll, String key) {
        return switch (key) {
            case "id" -> payroll.getId();
            case "staff_id" -> payroll.getStaff_id();
            case "month" -> payroll.getMonth();
            case "year" -> payroll.getYear();
            case "hours_amount" -> payroll.getHours_amount();
            case "bonus_amount" -> payroll.getBonus_amount();
            case "deduction_amount" -> payroll.getDeduction_amount();
            case "salary_amount" -> payroll.getSalary_amount();
            default -> null;
        };
    }
}
