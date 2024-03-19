package com.coffee.GUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_detailBLL;
import com.coffee.DTO.Role;
import com.coffee.DTO.Staff;
import com.coffee.DTO.Role_detail;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditStaffGUI;
import com.coffee.main.Cafe_Application;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class ChangeRoleGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeRole_detail;
    private JComboBox<String> jComboBoxRole;
    private JComboBox<String> jComboBoxTypeSalary;
    private JLabel jLabelSalary;
    private JLabel jLabelTypeSalary;
    private JTextField textFieldSalary;
    private JButton buttonCancel;
    private JButton buttonSet;
    private Role_detailBLL role_detailBLL = new Role_detailBLL();
    private Staff staff;
    private Role_detail roleDetail;

    public ChangeRoleGUI(Staff staff) {
        super();
        super.setTitle("Thiết lập lương");
        super.setSize(new Dimension(600, 400));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        this.staff = staff;
        List<Role_detail> role_detailList = new Role_detailBLL().searchRole_detailsByStaff(staff.getId());
        roleDetail = role_detailList.get(0);
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        attributeRole_detail = new ArrayList<>();
        jComboBoxRole = new JComboBox<>();
        jComboBoxTypeSalary = new JComboBox<>();
        jLabelSalary = new JLabel();
        textFieldSalary = new JTextField();
        buttonCancel = new JButton("Huỷ");
        buttonSet = new JButton("Thiết lập");
        content.setLayout(new MigLayout("",
                "50[]20[][]50",
                "20[]20[]20"));

        titleName.setText("Thiết lập lương");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);


        for (String string : new String[]{"Nhân viên", "Chức vụ", "Loại lương", ""}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeRole_detail.add(label);
            content.add(label);


            if (string.equals("Nhân viên")) {
                JLabel jLabel = new JLabel(staff.getName());
                jLabel.setFont((new Font("Public Sans", Font.BOLD, 16)));
                content.add(jLabel, "wrap");
            }

            if (string.equals("Chức vụ")) {
                for (Role role : new RoleBLL().searchRoles("id > 1"))
                    jComboBoxRole.addItem(role.getName());


                Role role = new RoleBLL().searchRoles("id = " + roleDetail.getRole_id()).get(0);
                jComboBoxRole.setSelectedItem(role.getName());

                jComboBoxRole.setPreferredSize(new Dimension(1000, 30));
                jComboBoxRole.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                content.add(jComboBoxRole, "wrap");
            }

            if (string.equals("Loại lương")) {
                jComboBoxTypeSalary.addItem("-- Chọn loại lương --");
                jComboBoxTypeSalary.addItem("1. Cố định");
                jComboBoxTypeSalary.addItem("2. Theo giờ làm việc");

                if (roleDetail.getType_salary() == 1)
                    jComboBoxTypeSalary.setSelectedIndex(1);

                else if (roleDetail.getType_salary() == 2)
                    jComboBoxTypeSalary.setSelectedIndex(2);
                else
                    jComboBoxTypeSalary.setSelectedIndex(0);

                jComboBoxTypeSalary.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loadTypeSalary();
                    }
                });

                jComboBoxTypeSalary.setPreferredSize(new Dimension(1000, 30));
                jComboBoxTypeSalary.setFont((new Font("Public Sans", Font.PLAIN, 14)));

                content.add(jComboBoxTypeSalary, "wrap");
            }

            if (string.isEmpty()) {
                textFieldSalary.setPreferredSize(new Dimension(1000, 30));
                textFieldSalary.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                textFieldSalary.setText(String.valueOf(roleDetail.getSalary()));
                content.add(textFieldSalary);
            }

        }
        jLabelTypeSalary = new JLabel("          ");
        jLabelTypeSalary.setFont((new Font("Public Sans", Font.PLAIN, 13)));
        content.add(jLabelTypeSalary, "wrap");

        loadTypeSalary();

        buttonCancel.setPreferredSize(new Dimension(100, 30));
        buttonCancel.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonCancel.setBackground(new Color(0xD54218));
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

        buttonSet.setPreferredSize(new Dimension(100, 30));
        buttonSet.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonSet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonSet.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateRole_detail();
            }
        });
        containerButton.add(buttonSet);
    }

    private void loadTypeSalary() {
        if (jComboBoxTypeSalary.getSelectedIndex() == 0) {
            attributeRole_detail.get(attributeRole_detail.size() - 1).setText("");
            textFieldSalary.setVisible(false);
            jLabelTypeSalary.setText("         ");
        } else {
            attributeRole_detail.get(attributeRole_detail.size() - 1).setText("Mức lương");
            textFieldSalary.setVisible(true);

            if (jComboBoxTypeSalary.getSelectedIndex() == 1)
                jLabelTypeSalary.setText("/kỳ lương");

            if (jComboBoxTypeSalary.getSelectedIndex() == 2)
                jLabelTypeSalary.setText("/giờ");
        }
    }

    private void updateRole_detail() {
        Pair<Boolean, String> result;
        int role_id, staff_id, type_salary;
        LocalDateTime entry_date;
        double salary;

        role_id = jComboBoxRole.getSelectedIndex() + 2;
        staff_id = staff.getId();
        if (role_id == roleDetail.getRole_id()) {
            entry_date = roleDetail.getEntry_date();
            type_salary = jComboBoxTypeSalary.getSelectedIndex();
            salary = Double.parseDouble(textFieldSalary.getText());

            Role_detail role_detail = new Role_detail(role_id, staff_id, entry_date, salary, type_salary); // false là tồn tại, true là đã xoá

            result = role_detailBLL.updateRole_detail(role_detail);

            if (result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                EditStaffGUI.textFieldRole.setText(Objects.requireNonNull(jComboBoxRole.getSelectedItem()).toString());
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            entry_date = LocalDateTime.now();
            type_salary = jComboBoxTypeSalary.getSelectedIndex();
            salary = Double.parseDouble(textFieldSalary.getText());

            Role_detail role_detail = new Role_detail(role_id, staff_id, entry_date, salary, type_salary); // false là tồn tại, true là đã xoá

            result = role_detailBLL.addRole_detail(role_detail);

            if (result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                EditStaffGUI.textFieldRole.setText(Objects.requireNonNull(jComboBoxRole.getSelectedItem()).toString());
                EditStaffGUI.changeRole = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
