package com.coffee.BLL;

import com.coffee.DAL.PayrollDAL;
import com.coffee.DTO.*;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PayrollBLL extends Manager<Payroll> {
    private final int maxMinutesCheckInLate = 15;
    private final int maxMinutesCheckOutEarly = 15;
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
                // nếu đang làm thì thay đổi chức vụ
                //
                //
                List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_detailsByStaff(staff.getId());
                if (role_detailList.isEmpty()) {
                    return new Pair<>(false, "Vui lòng thiết lập lương nhân viên " + staff.getName());
                } else {
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    Role_Detail roleDetail = role_detailList.get(0);
                    List<Role_Detail_Bonus> roleDetailBonusList = new Role_Detail_BonusBLL().searchRole_Detail_Bonuss(
                            "role_id = " + roleDetail.getRole_id(),
                            "staff_id = " + roleDetail.getStaff_id(),
                            "entry_date = '" + roleDetail.getEntry_date().format(myFormatObj) + "'");

                    List<Role_Detail_Deduction> roleDetailDeductionList = new Role_Detail_DeductionBLL().searchRole_Detail_Deductions(
                            "role_id = " + roleDetail.getRole_id(),
                            "staff_id = " + roleDetail.getStaff_id(),
                            "entry_date = '" + roleDetail.getEntry_date().format(myFormatObj) + "'");
                    double hours_amount = 0;
                    double bonus_amount = 0;
                    double deduction_amount = 0;
                    double salary_amount = 0;

                    // tinh luương theo giờ làm
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");

                    if (roleDetail.getType_salary() == 1) {
                        for (Work_Schedule work_schedule : work_scheduleList) {

                            System.out.println(work_schedule.getDate());/////////////////////////////

                            if (Objects.equals(work_schedule.getCheck_in(), "null") || Objects.equals(work_schedule.getCheck_out(), "null"))
                                return new Pair<>(false, "Vui lòng chấm công nhân viên " + staff.getName() + " ca " + work_schedule.getShift() + " vào ngày: " + work_schedule.getDate());

                        }
                        salary_amount = roleDetail.getSalary();
                    }

                    System.out.println(staff.getName());/////////////////////////////

                    if (roleDetail.getType_salary() == 2) {
                        for (Work_Schedule work_schedule : work_scheduleList) {

                            System.out.println(work_schedule.getDate());/////////////////////////////

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

                            System.out.println("Gio lam " + Math.abs(checkout - checkin));/////////////////////////////

                        }
                        hours_amount = Double.parseDouble(decimalFormat.format(hours_amount));
                        salary_amount = hours_amount * roleDetail.getSalary();
                    }
                    salary_amount = Double.parseDouble(decimalFormat.format(salary_amount));

                    // tính tiền phụ cấp

                    for (Role_Detail_Bonus roleDetailBonus : roleDetailBonusList) {
                        Bonus bonus = new BonusBLL().searchBonuss("id = " + roleDetailBonus.getBonus_id()).get(0);

                        if (bonus.getBonus_type() == 0) {
                            List<Date> dates = new ArrayList<>();
                            for (Work_Schedule work_schedule : work_scheduleList) {
                                if (!dates.contains(work_schedule.getDate()))
                                    dates.add(work_schedule.getDate());
                            }
                            bonus_amount += dates.size() * bonus.getBonus_amount();

                            System.out.println("So ngay lam " + dates.size());/////////////////////////////
                            System.out.println("Tien phu cap " + dates.size() * bonus.getBonus_amount());/////////////////////////////
                        }

                        if (bonus.getBonus_type() == 1) {
                            bonus_amount += bonus.getBonus_amount();
                            System.out.println("Tien phu cap thang " + bonus.getBonus_amount());/////////////////////////////
                        }
                    }
                    System.out.println("Tien phu cap tong cong " + bonus_amount);/////////////////////////////

                    // tính tiền giảm trừ

                    for (Role_Detail_Deduction roleDetailDeduction : roleDetailDeductionList) {
                        Deduction deduction = new DeductionBLL().searchDeductions("id = " + roleDetailDeduction.getDeduction_id()).get(0);

                        if (deduction.getDeduction_type() == 0) {
                            int shifts = 0;
                            for (Work_Schedule work_schedule : work_scheduleList) {
                                int hour = Integer.parseInt(work_schedule.getCheck_in().split(":")[0]);
                                int minute = Integer.parseInt(work_schedule.getCheck_in().split(":")[1]);
                                LocalTime checkin = LocalTime.of(hour, minute);
                                LocalTime timeShiftStart = null;

                                if (work_schedule.getShift() == 1)
                                    timeShiftStart = LocalTime.of(6, 0);

                                if (work_schedule.getShift() == 2)
                                    timeShiftStart = LocalTime.of(12, 0);

                                if (work_schedule.getShift() == 3)
                                    timeShiftStart = LocalTime.of(18, 0);

                                assert timeShiftStart != null;
                                long minutesLate = ChronoUnit.MINUTES.between(timeShiftStart, checkin);

                                if (minutesLate >= maxMinutesCheckInLate) {
                                    System.out.println("Ngay di muon " + work_schedule.getDate());/////////////////////////////
                                    System.out.println("Thoi gian di muon " + minutesLate);/////////////////////////////
                                    shifts += 1;
                                }
                            }
                            deduction_amount += shifts * deduction.getDeduction_amount();
                            System.out.println("So ca di muon " + shifts);/////////////////////////////
                            System.out.println("Tien giam tru di muon " + shifts * deduction.getDeduction_amount());/////////////////////////////

                        }

                        if (deduction.getDeduction_type() == 1) {
                            int shifts = 0;
                            for (Work_Schedule work_schedule : work_scheduleList) {
                                int hour = Integer.parseInt(work_schedule.getCheck_out().split(":")[0]);
                                int minute = Integer.parseInt(work_schedule.getCheck_out().split(":")[1]);
                                LocalTime checkout = LocalTime.of(hour, minute);
                                LocalTime timeShiftEnd = null;

                                if (work_schedule.getShift() == 1)
                                    timeShiftEnd = LocalTime.of(12, 0);

                                if (work_schedule.getShift() == 2)
                                    timeShiftEnd = LocalTime.of(18, 0);

                                if (work_schedule.getShift() == 3)
                                    timeShiftEnd = LocalTime.of(23, 0);

                                assert timeShiftEnd != null;
                                long minutesEarly = ChronoUnit.MINUTES.between(checkout, timeShiftEnd);

                                if (minutesEarly >= maxMinutesCheckOutEarly) {
                                    System.out.println("Ngay ve som " + work_schedule.getDate());/////////////////////////////
                                    System.out.println("Thoi gian ve som " + minutesEarly);
                                    shifts += 1;
                                }
                            }
                            deduction_amount += shifts * deduction.getDeduction_amount();
                            System.out.println("So ca ve som " + shifts);/////////////////////////////
                            System.out.println("Tien giam tru ve som " + shifts * deduction.getDeduction_amount());/////////////////////////////
                        }

                        if (deduction.getDeduction_type() == 2) {
                            deduction_amount += deduction.getDeduction_amount();
                            System.out.println("Tien giam tru co dinh " + deduction.getDeduction_amount());/////////////////////////////
                        }
                    }

                    System.out.println("Tien giam tru tong cong " + deduction_amount);

                    salary_amount += bonus_amount;
                    salary_amount -= deduction_amount;

                    if (roleDetail.getType_salary() == 1) {
                        List<Date> dates = new ArrayList<>();
                        for (Work_Schedule work_schedule : work_scheduleList) {
                            if (!dates.contains(work_schedule.getDate()))
                                dates.add(work_schedule.getDate());
                        }
                        salary_amount = salary_amount / 26 * dates.size(); // lương tháng cố định = (lương thoả thuận + phụ cấp - giảm trừ)/26 * số ngày làm thực tế
                    }
                    salary_amount = Double.parseDouble(decimalFormat.format(salary_amount));

                    Payroll_Detail payrollDetail = new Payroll_Detail(payroll.getId(), staff.getId(), hours_amount, bonus_amount, deduction_amount, salary_amount, false);
                    payrollDetails.add(payrollDetail);
                    totalSalary += salary_amount;
                }
            }
        }

        payroll.setTotal_salary(totalSalary);
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
