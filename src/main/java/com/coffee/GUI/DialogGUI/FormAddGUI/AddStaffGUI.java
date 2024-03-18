package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_detailBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.DAL.RoleDAL;
import com.coffee.DTO.Role;
import com.coffee.DTO.Role_detail;
import com.coffee.DTO.Staff;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;

import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.DialogGUI.DialogFormDetail_2;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class AddStaffGUI extends DialogForm {

    private List<JLabel> attributeStaff;
    private List<JTextField> jTextFieldsStaff;
    private JLabel titleName;
    private java.util.List<JLabel> attributeDiscount;

    private JButton buttonCancel;
    private JComboBox<String> jComboBoxSearch;
    private JButton buttonAdd;

    private JDateChooser jDateChooser = new JDateChooser();

    private JTextField textField = new JTextField();

    private Staff staff = new Staff();
    private StaffBLL staffBLL = new StaffBLL();

    private RoleBLL roleBLL = new RoleBLL();

    private JRadioButton radioMale = new JRadioButton();

    private JRadioButton radioFemale = new JRadioButton();

    private ButtonGroup Gender;

    public AddStaffGUI() {
        super();
        super.setTitle("Thêm Nhân Viên");
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        attributeStaff = new ArrayList<>();
        jTextFieldsStaff = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        content.setLayout(new MigLayout("", "200[]50[]50", "10[]10[]"));

        titleName.setText("Thêm Nhân Viên");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        Gender = new ButtonGroup();

        for (String string : new String[]{"Mã Nhân Viên", "Tên Nhân Viên", "CCCD", "Ngày Sinh", "Giới Tính", "Chức Vụ", "Số Điện Thoại", "Địa Chỉ", "Email", "Tên Tài Khoản", " Mật Khẩu"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(180, 35));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 15)));
            attributeStaff.add(label);
            content.add(label);

            textField = new JTextField();
            textField.setPreferredSize(new Dimension(250, 35));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));

            if (string.trim().equals("Ngày Sinh")) {
                jDateChooser = new JDateChooser();
                jDateChooser.setDateFormatString("dd/MM/yyyy");
                jDateChooser.setPreferredSize(new Dimension(180, 35));
                jDateChooser.setMinSelectableDate(java.sql.Date.valueOf("1000-01-01"));
                textField = (JTextField) jDateChooser.getDateEditor().getUiComponent();
                content.add(jDateChooser, "wrap");
            } else if (string.trim().equals("Giới Tính")) {
                JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                jPanel.setPreferredSize(new Dimension(1000, 35));
                jPanel.setBackground(Color.white);

                radioMale = new JRadioButton("Nam");
                radioFemale = new JRadioButton("Nữ");

                boolean isMale = staff.isGender();
                if (isMale) {
                    radioMale.setSelected(true);
                } else {
                    radioFemale.setSelected(true);
                }

                jPanel.add(radioMale);
                jPanel.add(radioFemale);

                Gender.add(radioMale);
                Gender.add(radioFemale);

                content.add(jPanel, "wrap");
            }
//            else if (string.trim().equals("Chức Vụ")) {
//                List<Role> roles = new RoleBLL().searchRoles();
//                List<String> roleNames = new ArrayList<>();
//
//                for (Role r : roles) {
//                    roleNames.add(r.getName());
//                }
//
//                jComboBoxSearch = new JComboBox<>(roleNames.toArray(new String[0]));
//
//                jComboBoxSearch.setBackground(new Color(255, 255, 255));
//                jComboBoxSearch.setForeground(Color.black);
//                jComboBoxSearch.setPreferredSize(new Dimension(150, 35));
//
//                content.add(jComboBoxSearch, "wrap");
//
//            }
            else {
                if (string.trim().equals("Chức Vụ")) {

                }

                if (string.trim().equals("Mã Nhân Viên")) {

                }
                if (string.trim().equals("Tên Nhân Viên")) {

                }
                if (string.trim().equals("CCCD")) {

                }
                if (string.trim().equals("Số Điện Thoại")) {

                }
                if (string.trim().equals("Địa Chỉ")) {

                }
                if (string.trim().equals("Email")) {

                }

                if (string.trim().equals("Tên Tài Khoản")) {

                }
                if (string.trim().equals("Mật Khẩu ")) {

                }
                jTextFieldsStaff.add(textField);
                content.add(textField, "wrap");
            }


        }

        buttonCancel.setPreferredSize(new Dimension(100, 30));
        buttonCancel.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonCancel.setBackground(new Color(0xD54218));

//                            int i = 0;
//            for (JTextField textFieldtest : jTextFieldsStaff) {
//                System.out.println("get( " + i + ") " + textFieldtest.getText());
//                i++;
//                Date birthdate;
//                birthdate = jDateChooser.getDate();
//                System.out.println(" Ngày Sinh " + birthdate);
//
//                String selectedGender = "";
//                if (radioMale.isSelected()) {
//                    selectedGender = "Nam";
//                } else if (radioFemale.isSelected()) {
//                    selectedGender = "Nữ";
//                } else {
//                    JOptionPane.showMessageDialog(null, "Vui lòng chọn giới tính.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    return; // Dừng việc thêm mới nhân viên nếu không có giới tính nào được chọn
//                }
//                boolean gender = selectedGender.equals("Nam");
//                System.out.println(gender);
//                 int id = staffBLL.getAutoID(staffBLL.searchStaffs("deleted = 0"));
//
//
//            }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonCancel.setBackground(Color.white);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cancel();
            }
        });
        containerButton.add(buttonCancel);

        buttonAdd.setPreferredSize(new Dimension(100, 30));
        buttonAdd.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addStaff();

            }
        });
        containerButton.add(buttonAdd);

    }

    private void addStaff() {

        Pair<Boolean, String> result;
        int id;
        String staffNo, name, phone, address, email;
        boolean gender;
        Date birthdate;

// add get đúng trong list
        id = staffBLL.getAutoID(staffBLL.searchStaffs("deleted = 0"));
        name = jTextFieldsStaff.get(1).getText().trim();
        staffNo = jTextFieldsStaff.get(2).getText().trim();

        String selectedGender = "";
        if (radioMale.isSelected()) {
            selectedGender = "Nam";
        } else if (radioFemale.isSelected()) {
            selectedGender = "Nữ";
        }
        gender = selectedGender.equals("Nam");
        birthdate = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser.getDate()));
        phone = jTextFieldsStaff.get(4).getText().trim();
        address = jTextFieldsStaff.get(5).getText().trim();
        email = jTextFieldsStaff.get(6).getText().trim();

        Staff staff = new Staff(id, staffNo, name, gender, birthdate, phone, address, email, false);

        result = staffBLL.addStaff(staff);


        if (result.getKey()) {

//            String selectedRole = (String) jComboBoxSearch.getSelectedItem();
//            List<Role> roles = new RoleBLL().findRoles("name", selectedRole);
//            int roleId = roles.get(0).getId();
//            Role role = new Role(roleId,id,);
//            result = roleBLL.addRole(role);

            JOptionPane.showMessageDialog(null, result.getValue(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {

            JOptionPane.showMessageDialog(null, result.getValue(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }


    }
}
