package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.components.MyTextFieldUnderLine;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.main.Cafe_Application;
import com.coffee.utils.VNString;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailPayroll_DetailGUI extends DialogForm {
    private Payroll_Detail payrollDetail;
    private Payroll payroll;

    public DetailPayroll_DetailGUI(Payroll_Detail payrollDetail, Payroll payroll) {
        super();
        super.setTitle("BẢNG LƯƠNG CHI TIẾT THÁNG " + payroll.getMonth() + "/" + payroll.getYear());
        super.setSize(new Dimension(1000, 600));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        this.payrollDetail = payrollDetail;
        this.payroll = payroll;
        init();
        setVisible(true);
    }

    private void init() {
        JLabel titleName = new JLabel();
        content.setLayout(new BorderLayout());

        titleName.setText("BẢNG LƯƠNG CHI TIẾT THÁNG " + payroll.getMonth() + "/" + payroll.getYear());
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);
        title.setBackground(new Color(242, 245, 250));

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setBackground(Color.white);
        jTabbedPane.setPreferredSize(new Dimension(1000, 600));
        content.add(jTabbedPane);

        JPanel infoPanel = new JPanel();
        JPanel allowancePanel = new JPanel();
        JPanel latePanel = new JPanel();
        JPanel earlyPanel = new JPanel();
        JPanel absentPanel1 = new JPanel();
        JPanel absentPanel2 = new JPanel();
        JPanel bonusPanel = new JPanel();
        JPanel finePanel = new JPanel();

        jTabbedPane.add("Thông Tin", infoPanel);
        jTabbedPane.add("Trợ Cấp", allowancePanel);
        jTabbedPane.add("Đi Trễ", latePanel);
        jTabbedPane.add("Về Sớm", earlyPanel);
        jTabbedPane.add("Nghỉ Có Phép", absentPanel1);
        jTabbedPane.add("Nghỉ Không Phép", absentPanel2);
        jTabbedPane.add("Thưởng", bonusPanel);
        jTabbedPane.add("Phạt Vi Phạm", finePanel);

        initInfoPanel(infoPanel);
        initAllowancePanel(allowancePanel);
        initLatePanel(latePanel);
        initEarlyPanel(earlyPanel);
        initAbsent1Panel(absentPanel1);
        initAbsent2Panel(absentPanel2);
        initBonusPanel(bonusPanel);
        initFinePanel(finePanel);
    }

    private void initFinePanel(JPanel finePanel) {
    }

    private void initBonusPanel(JPanel bonusPanel) {
    }

    private void initAbsent2Panel(JPanel absentPanel2) {
    }

    private void initAbsent1Panel(JPanel absentPanel1) {
    }

    private void initEarlyPanel(JPanel earlyPanel) {
    }

    private void initLatePanel(JPanel latePanel) {
    }

    private void initAllowancePanel(JPanel allowancePanel) {
    }

    private void initInfoPanel(JPanel infoPanel) {
        infoPanel.setLayout(new MigLayout("", "20[]20", "20[]20"));
        infoPanel.setPreferredSize(new Dimension(1000, 600));
        infoPanel.setBackground(Color.white);
        for (String string : new String[]{"Mã nhân viên", "Họ tên", "Chức vụ", "Lương chính thức", "Giờ công thực tế", "Tổng phụ cấp", "Tổng giảm trừ", "Tổng thưởng", "Tổng phạt vi phạm", "Thực lãnh", "Trạng thái"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.BOLD, 15)));
            infoPanel.add(label);

            JTextField textField = new MyTextFieldUnderLine();
            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(280, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            Role_Detail role_detail = new Role_DetailBLL().searchRole_detailsByStaff(payrollDetail.getStaff_id()).get(0);
            if (string.equals("Mã nhân viên")) {
                textField.setText(String.valueOf(payrollDetail.getStaff_id()));
                infoPanel.add(textField);
                continue;
            }
            if (string.equals("Họ tên")) {
                Staff staff = new StaffBLL().searchStaffs("id = " + payrollDetail.getStaff_id()).get(0);
                textField.setText(staff.getName());
                infoPanel.add(textField, "wrap");
                continue;
            }
            if (string.equals("Chức vụ")) {
                Role role = new RoleBLL().searchRoles("id = " + role_detail.getRole_id()).get(0);
                textField.setText(role.getName());
                infoPanel.add(textField);
                continue;
            }
            if (string.equals("Lương chính thức")) {

                textField.setText(VNString.currency(role_detail.getSalary()) + (role_detail.getType_salary() == 1 ? " /kỳ lương" : " /giờ"));
                infoPanel.add(textField, "wrap");
                continue;
            }
            if (string.equals("Giờ công thực tế")) {
                if (role_detail.getType_salary() == 2)
                    textField.setText(String.valueOf(payrollDetail.getHours_amount()) + " giờ");
                else
                    textField.setText(String.valueOf(payrollDetail.getHours_amount()).split("\\.")[0] + " ngày");
                infoPanel.add(textField);
                continue;
            }
            if (string.equals("Tổng phụ cấp")) {
                textField.setText(VNString.currency(payrollDetail.getAllowance_amount()));
                infoPanel.add(textField, "wrap");
                continue;
            }
            if (string.equals("Tổng giảm trừ")) {
                textField.setText(VNString.currency(payrollDetail.getDeduction_amount()));
                infoPanel.add(textField);
                continue;
            }
            if (string.equals("Tổng thưởng")) {
                textField.setText(VNString.currency(payrollDetail.getBonus_amount()));
                infoPanel.add(textField, "wrap");
                continue;
            }
            if (string.equals("Tổng phạt vi phạm")) {
                textField.setText(VNString.currency(payrollDetail.getFine_amount()));
                infoPanel.add(textField);
                continue;
            }
            if (string.equals("Thực lãnh")) {
                textField.setText(VNString.currency(payrollDetail.getSalary_amount()));
                infoPanel.add(textField, "wrap");
                continue;
            }
            if (payrollDetail.isStatus())
                textField.setForeground(new Color(0x008E00));
            textField.setText(payrollDetail.isStatus() ? "Đã trả lương ✔" : "Tạm tính");
            infoPanel.add(textField, "wrap");
        }

    }
}
