package com.coffee.BLL;

import com.coffee.DAL.PayrollDAL;
import com.coffee.DTO.*;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class PayrollBLL extends Manager<Payroll> {
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
        if (result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        List<Payroll_Detail> payrollDetails = new ArrayList<>();

        double totalSalary = 0;
        for (Staff staff : new StaffBLL().searchStaffs("deleted = 0")) {
            List<Work_Schedule> work_scheduleList = new Work_ScheduleBLL().searchWork_schedulesByStaff(staff.getId(), payroll.getYear(), payroll.getMonth());
            if (!work_scheduleList.isEmpty()) {
                List<Role_detail> role_detailList = new Role_detailBLL().searchRole_detailsByStaff(staff.getId());
                if (role_detailList.isEmpty()) {
                    return new Pair<>(false, "Vui lòng thiết lập lương nhân viên " + staff.getName());
                } else {
                    Role_detail roleDetail = role_detailList.get(0);
                    double hours_amount = 0;
                    double salary_amount = 0;
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    for (Work_Schedule work_schedule : work_scheduleList) {
                        if (Objects.equals(work_schedule.getCheck_in(), "null") || Objects.equals(work_schedule.getCheck_out(), "null"))
                            return new Pair<>(false, "Vui lòng chấm công nhân viên " + staff.getName() + " ca " + work_schedule.getShift() + " vào ngày: " + work_schedule.getDate());
                        String[] checkinArr, checkoutArr;
                        double checkin, checkout;

                        checkinArr = work_schedule.getCheck_in().split(":");
                        double v = Double.parseDouble(checkinArr[1]) / 60;
                        checkin = Double.parseDouble(checkinArr[0]) + v;

                        checkoutArr = work_schedule.getCheck_out().split(":");
                        v = Double.parseDouble(checkoutArr[1]) / 60;
                        checkout = Double.parseDouble(checkoutArr[0]) + v;

                        hours_amount += Math.abs(checkout - checkin);
                    }
                    hours_amount = Double.parseDouble(decimalFormat.format(hours_amount));
                    if (roleDetail.getType_salary() == 1)
                        salary_amount = roleDetail.getSalary();
                    if (roleDetail.getType_salary() == 2)
                        salary_amount = hours_amount * roleDetail.getSalary();

                    salary_amount = Double.parseDouble(decimalFormat.format(salary_amount));
                    Payroll_Detail payrollDetail = new Payroll_Detail(payroll.getId(), staff.getId(), hours_amount, 0, 0, salary_amount, false);
                    payrollDetails.add(payrollDetail);
                    totalSalary += salary_amount;
                }
            }
        }

//        payroll.setTotal_salary(totalSalary);
        payroll.setDebt(totalSalary);
        Payroll_DetailBLL payrollDetailBLL = new Payroll_DetailBLL();
        if (payrollDAL.addPayroll(payroll) == 0) {
            return new Pair<>(false, "Thêm bảng lương không thành công.");
        }

        for (Payroll_Detail payrollDetail : payrollDetails) {
            payrollDetailBLL.addPayroll_Detail(payrollDetail);
        }
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
        List<Payroll> payrolls = payrollDAL.searchPayrolls();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            payrolls = findObjectsBy(entry.getKey(), entry.getValue(), payrolls);
        return payrolls;
    }

    public Pair<Boolean, String> exists(Payroll payroll) {
        List<Payroll> payrolls = findPayrollsBy(Map.of(
                "month", payroll.getMonth(),
                "year", payroll.getYear()
        ));

        if (!payrolls.isEmpty()) {
            return new Pair<>(true, "Bảng lương tháng này đã tồn tại.");
        }
        return new Pair<>(false, "");
    }

    public Pair<Boolean, String> validateMonth(String month) {
        if (month.isBlank())
            return new Pair<>(false, "Tháng không được để trống");
        if (!checkMonth(month))
            return new Pair<>(false, "Tháng phải là số nguyên và nằm trong khoảng từ 1 đến 12");
        return new Pair<>(true, "Tháng hợp lệ");
    }

    public Pair<Boolean, String> validateYear(String year) {
        if (year.isBlank())
            return new Pair<>(false, "Năm không được để trống");
        if (!checkYear(year))
            return new Pair<>(false, "Năm phải là số nguyên và phải lớn hơn bằng năm hiện tại");
        return new Pair<>(true, "Tháng hợp lệ");
    }

    public boolean checkMonth(String str) {
        try {
            int number = Integer.parseInt(str);
            return number >= 0 && number <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkYear(String str) {
        try {
            int year = Integer.parseInt(str);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            return year >= currentYear && year < 9999;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Object getValueByKey(Payroll payroll, String key) {
        return switch (key) {
            case "id" -> payroll.getId();
            case "name" -> payroll.getName();
            case "entry_date" -> payroll.getEntry_date();
            case "month" -> payroll.getMonth();
            case "year" -> payroll.getYear();
            case "total_salary" -> payroll.getTotal_salary();
            case "paid" -> payroll.getPaid();
            case "debt" -> payroll.getDebt();
            default -> null;
        };
    }
}
