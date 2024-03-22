package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.Payroll_DetailBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.DTO.Payroll_Detail;
import com.coffee.DTO.Staff;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.main.Cafe_Application;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DetailPayroll_DetailGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributePayroll_Detail;
    private List<JTextField> jTextFieldPayroll_Detail;
    private Payroll_DetailBLL payrollDetailBLL = new Payroll_DetailBLL();

    public DetailPayroll_DetailGUI(Payroll_Detail payrollDetail) {
        super();
        super.setTitle("Thông tin phiếu lương");
        super.setSize(new Dimension(600, 450));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        init(payrollDetail);
        setVisible(true);
    }

    private void init(Payroll_Detail payrollDetail) {
        titleName = new JLabel();
        attributePayroll_Detail = new ArrayList<>();
        jTextFieldPayroll_Detail = new ArrayList<>();
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "20[]20[]20"));

        titleName.setText("Thông tin phiếu lương");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Nhân viên", "Tổng giờ làm", "Tiền thưởng", "Tiền phạt", "Tổng lương", "Trạng thái"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributePayroll_Detail.add(label);
            content.add(label);

            JTextField textField = new JTextField();

            if (string.equals("Nhân viên")) {
                Staff staff = new StaffBLL().searchStaffs("id = " + payrollDetail.getStaff_id()).get(0);
                textField.setText(staff.getName());
            }
            if (string.equals("Tổng giờ làm")) {
                textField.setText(String.valueOf(payrollDetail.getHours_amount()));
            }
            if (string.equals("Tiền thưởng")) {
                textField.setText(String.valueOf(payrollDetail.getBonus_amount()));
            }
            if (string.equals("Tiền phạt")) {
                textField.setText(String.valueOf(payrollDetail.getDeduction_amount()));
            }
            if (string.equals("Tổng lương")) {
                textField.setText(String.valueOf(payrollDetail.getSalary_amount()));
            }
            if (string.equals("Trạng thái")) {
                textField.setText(payrollDetail.isStatus() ? "Đã trả" : "Tạm tính");
            }
            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldPayroll_Detail.add(textField);
            content.add(textField, "wrap");

        }
    }
}
