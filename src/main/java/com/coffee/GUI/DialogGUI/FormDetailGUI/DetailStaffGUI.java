package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_detailBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.DTO.Role;
import com.coffee.DTO.Role_detail;
import com.coffee.DTO.Staff;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailStaffGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeSupplier;


    private List<JTextField> jTextFieldsStaff;

    private StaffBLL staffBLL = new StaffBLL();
    private Staff staff = new Staff();
    private JDateChooser jDateChooser = new JDateChooser();
    public DetailStaffGUI (Staff staff)
    {
        super();
        super.setTitle("Thông Tin Nhân Viên ");
        init(staff);
        setVisible(true);
    }
    private void init(Staff staff) {
        titleName = new JLabel();
        attributeSupplier = new ArrayList<>();
        jTextFieldsStaff = new ArrayList<>();
        content.setLayout(new MigLayout("",
                "200[]20[]200",
                "20[]20[]20"));

        titleName.setText("Thông Tin Nhân Viên");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Mã Nhân Viên", "Tên Nhân Viên", "CCCD", "Giới Tính", "Ngày Sinh", "Chức Vụ", "Số Điện Thoại", "Địa Chỉ", "Email"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(150, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            attributeSupplier.add(label);
            content.add(label);
            JTextField textField = new JTextField();

            if (string.trim().equals("Ngày Sinh")) {
                Date birthDate = staff.getBirthdate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                textField.setText(dateFormat.format(birthDate));
            }

            if (string.trim().equals("Mã Nhân Viên")) {
                String staffId = Integer.toString(staff.getId());
                textField.setText(staffId);
            }
            if (string.trim().equals("Tên Nhân Viên")) {
                textField.setText(staff.getName());
            }
            if (string.trim().equals("CCCD")) {
                textField.setText(staff.getStaffNo());
            }
            if (string.trim().equals("Giới Tính")) {
                boolean gender = staff.isGender();
                String gender1 = gender? "Nữ" : "Nam";
                textField.setText(gender1);
            }
            if (string.trim().equals("Số Điện Thoại")) {
                textField.setText(staff.getPhone());
            }
            if (string.trim().equals("Địa Chỉ")) {
                textField.setText(staff.getAddress());
            }
            if (string.trim().equals("Email")) {
                textField.setText(staff.getEmail());
            }
            if (string.trim().equals("Chức Vụ")) {
                List<Role_detail> roleDetails = new Role_detailBLL().searchRole_details("staff_id = " + staff.getId());
                for (Role_detail roleDetail : roleDetails) {
                    int roleId = roleDetail.getRole_id();
                    Role role = new RoleBLL().searchRoles("id = " + roleId).get(0);
                    textField.setText(role.getName());
                    break; // Dừng vòng lặp sau khi tìm thấy một chức vụ
                }


            }
            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(280, 35));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 13)));
            textField.setBackground(new Color(245, 246, 250));

            jTextFieldsStaff.add(textField);
            content.add(textField, "wrap");
        }

    }
}
