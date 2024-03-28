package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.main.Cafe_Application;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailPayroll_DetailGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributePayroll_Detail;
    private Payroll_DetailBLL payrollDetailBLL = new Payroll_DetailBLL();

    public DetailPayroll_DetailGUI(Payroll_Detail payrollDetail, Payroll payroll) {
        super();
        super.setTitle("BẢNG LƯƠNG CHI TIẾT THÁNG " + payroll.getMonth() + "/" + payroll.getYear());
        super.setSize(new Dimension(600, 600));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        init(payrollDetail, payroll);
        setVisible(true);
    }

    private void init(Payroll_Detail payrollDetail, Payroll payroll) {
        titleName = new JLabel();
        attributePayroll_Detail = new ArrayList<>();
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "5[]5[]5"));

        titleName.setText("BẢNG LƯƠNG CHI TIẾT THÁNG " + payroll.getMonth() + "/" + payroll.getYear());
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);
        title.setBackground(new Color(217, 217, 217));

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_details(
                "role_id = " + payrollDetail.getRole_id(),
                "staff_id = " + payrollDetail.getStaff_id(),
                "entry_date = '" + payrollDetail.getEntry_date().format(myFormatObj) + "'");
        Role_Detail roleDetail = role_detailList.get(0);

        List<Role_Detail_Bonus> roleDetailBonusList = new Role_Detail_BonusBLL().searchRole_Detail_Bonuss(
                "role_id = " + roleDetail.getRole_id(),
                "staff_id = " + roleDetail.getStaff_id(),
                "entry_date = '" + roleDetail.getEntry_date().format(myFormatObj) + "'");

        List<Role_Detail_Deduction> roleDetailDeductionList = new Role_Detail_DeductionBLL().searchRole_Detail_Deductions(
                "role_id = " + roleDetail.getRole_id(),
                "staff_id = " + roleDetail.getStaff_id(),
                "entry_date = '" + roleDetail.getEntry_date().format(myFormatObj) + "'");
        List<Work_Schedule> work_scheduleList = new Work_ScheduleBLL().searchWork_schedulesByStaff(payrollDetail.getStaff_id(), payroll.getYear(), payroll.getMonth());

        for (String string : new String[]{"Mã nhân viên", "Họ tên", "Chức vụ", "Lương chính thức", "Giờ công thực tế", "Các khoản phụ cấp", "Các khoản giảm trừ", "Thực lãnh", "Trạng thái"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.BOLD, 15)));
            attributePayroll_Detail.add(label);


            JLabel textField = new JLabel();
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));

            Staff staff = new StaffBLL().searchStaffs("id = " + payrollDetail.getStaff_id()).get(0);
            if (string.equals("Mã nhân viên")) {
                textField.setText(String.valueOf(staff.getId()));
            }
            if (string.equals("Họ tên")) {
                textField.setText(staff.getName());
            }
            if (string.equals("Chức vụ")) {
                Role role = new RoleBLL().searchRoles("id = " + roleDetail.getRole_id()).get(0);
                textField.setText(role.getName());
            }
            if (string.equals("Lương chính thức")) {
                if (roleDetail.getType_salary() == 1)
                    textField.setText(roleDetail.getSalary() + " /kỳ lương");
                if (roleDetail.getType_salary() == 2)
                    textField.setText(roleDetail.getSalary() + "/giờ");
            }
            if (string.equals("Giờ công thực tế")) {
                textField.setText(String.valueOf(payrollDetail.getHours_amount()));
            }
            if (string.equals("Các khoản phụ cấp")) {
                if (roleDetailBonusList.isEmpty()) {
                    content.add(label);
                    textField.setText("Không có");
                    content.add(textField, "wrap");
                    continue;
                }

                content.add(label, "span, wrap");

                for (Role_Detail_Bonus roleDetailBonus : roleDetailBonusList) {
                    Bonus bonus = new BonusBLL().searchBonuss("id = " + roleDetailBonus.getBonus_id()).get(0);
                    BigDecimal bonus_amount = BigDecimal.valueOf(0);

                    JLabel labelBonusName = new JLabel(bonus.getName());
                    labelBonusName.setPreferredSize(new Dimension(1000, 30));
                    labelBonusName.setFont((new Font("Public Sans", Font.PLAIN, 14)));

                    if (bonus.getBonus_type() == 0) {
                        List<Date> dates = new ArrayList<>();
                        for (Work_Schedule work_schedule : work_scheduleList) {
                            if (!dates.contains(work_schedule.getDate()))
                                dates.add(work_schedule.getDate());
                        }
                        bonus_amount = BigDecimal.valueOf(dates.size() * bonus.getBonus_amount());

                    } else if (bonus.getBonus_type() == 1) {
                        bonus_amount = BigDecimal.valueOf(bonus.getBonus_amount());
                    }

                    JLabel labelBonusAmount = new JLabel(bonus_amount.toString());
                    labelBonusAmount.setPreferredSize(new Dimension(1000, 30));
                    labelBonusAmount.setFont((new Font("Public Sans", Font.PLAIN, 14)));

                    content.add(labelBonusName);
                    content.add(labelBonusAmount, "wrap");
                }
                JLabel labelBonusTotal = new JLabel("Tổng phụ cấp");
                labelBonusTotal.setPreferredSize(new Dimension(1000, 30));
                labelBonusTotal.setFont((new Font("Public Sans", Font.PLAIN, 14)));

                textField.setText(String.valueOf(payrollDetail.getBonus_amount()));

                content.add(labelBonusTotal);
                content.add(textField, "wrap");
                continue;
            }
            if (string.equals("Các khoản giảm trừ")) {
                if (roleDetailBonusList.isEmpty()) {
                    content.add(label);
                    textField.setText("Không có");
                    content.add(textField, "wrap");
                    continue;
                }

                content.add(label, "span, wrap");

                for (Role_Detail_Deduction roleDetailDeduction : roleDetailDeductionList) {
                    Deduction deduction = new DeductionBLL().searchDeductions("id = " + roleDetailDeduction.getDeduction_id()).get(0);
                    BigDecimal deduction_amount = BigDecimal.valueOf(0);

                    JLabel labelBonusName = new JLabel(deduction.getName());
                    labelBonusName.setPreferredSize(new Dimension(1000, 30));
                    labelBonusName.setFont((new Font("Public Sans", Font.PLAIN, 14)));

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

                            if (minutesLate >= 15) {
                                shifts += 1;
                            }
                        }
                        deduction_amount = BigDecimal.valueOf(shifts * deduction.getDeduction_amount());
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

                            if (minutesEarly >= 15) {
                                shifts += 1;
                            }
                        }
                        deduction_amount = BigDecimal.valueOf(shifts * deduction.getDeduction_amount());
                    }

                    if (deduction.getDeduction_type() == 2) {
                        deduction_amount = BigDecimal.valueOf(deduction.getDeduction_amount());
                    }

                    JLabel labelBonusAmount = new JLabel(deduction_amount.toString());
                    labelBonusAmount.setPreferredSize(new Dimension(1000, 30));
                    labelBonusAmount.setFont((new Font("Public Sans", Font.PLAIN, 14)));

                    content.add(labelBonusName);
                    content.add(labelBonusAmount, "wrap");
                }

                JLabel labelBonusTotal = new JLabel("Tổng giảm trừ");
                labelBonusTotal.setPreferredSize(new Dimension(1000, 30));
                labelBonusTotal.setFont((new Font("Public Sans", Font.PLAIN, 14)));

                textField.setText(String.valueOf(payrollDetail.getDeduction_amount()));

                content.add(labelBonusTotal);
                content.add(textField, "wrap");
                continue;
            }
            if (string.equals("Thực lãnh")) {
                content.add(label, "span, wrap");
                textField.setText(String.valueOf(payrollDetail.getSalary_amount()));
            }
            if (string.equals("Trạng thái")) {
                if (payrollDetail.isStatus())
                    textField.setForeground(new Color(0x008E00));
                textField.setText(payrollDetail.isStatus() ? "Đã trả lương ✔" : "Tạm tính");
            }

            content.add(label);
            content.add(textField, "wrap");

        }
    }
}
