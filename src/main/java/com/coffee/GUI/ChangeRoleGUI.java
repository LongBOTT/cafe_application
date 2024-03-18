package com.coffee.GUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_detailBLL;
import com.coffee.DTO.Role;
import com.coffee.DTO.Staff;
import com.coffee.DTO.Role_detail;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.main.Cafe_Application;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    private Role_detailBLL supplierBLL = new Role_detailBLL();
    private Staff staff;

    public ChangeRoleGUI(Staff staff) {
        super();
        super.setTitle("Thiết lập lương");
        super.setSize(new Dimension(600, 400));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        this.staff = staff;
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
                "50[]20[]",
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
                jLabel.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                content.add(jLabel, "wrap");
            }

            if (string.equals("Chức vụ")) {
                for (Role role : new RoleBLL().searchRoles("id > 1"))
                    jComboBoxRole.addItem(role.getName());

                jComboBoxRole.setPreferredSize(new Dimension(1000, 30));
                jComboBoxRole.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                content.add(jComboBoxRole, "wrap");
            }

            if (string.equals("Loại lương")) {
                jComboBoxTypeSalary.addItem("-- Chọn loại lương --");
                jComboBoxTypeSalary.addItem("1. Cố định");
                jComboBoxTypeSalary.addItem("2. Theo giờ làm việc");

                jComboBoxTypeSalary.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loadTypeSalary();
                    }
                });

                jComboBoxTypeSalary.setPreferredSize(new Dimension(1000, 30));
                jComboBoxTypeSalary.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                content.add(jComboBoxTypeSalary, "wrap");
            } else {
                textFieldSalary.setPreferredSize(new Dimension(1000, 30));
                textFieldSalary.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                textFieldSalary.setVisible(false);
                content.add(jComboBoxTypeSalary);
            }

        }
        jLabelTypeSalary = new JLabel();
        content.add(jLabelTypeSalary, "wrap");

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
                addRole_detail();
            }
        });
        containerButton.add(buttonSet);
    }

    private void loadTypeSalary() {
        if (jComboBoxTypeSalary.getSelectedIndex() == 0) {
            attributeRole_detail.get(attributeRole_detail.size() - 1).setText("");
            textFieldSalary.setVisible(false);
            jLabelTypeSalary.setText("");
        }
        attributeRole_detail.get(attributeRole_detail.size() - 1).setText("Mức lương");
        textFieldSalary.setText("");
        textFieldSalary.setVisible(true);

        if (jComboBoxTypeSalary.getSelectedIndex() == 1)
            jLabelTypeSalary.setText("/kỳ lương");

        if (jComboBoxTypeSalary.getSelectedIndex() == 2)
            jLabelTypeSalary.setText("/giờ");
    }

    private void addRole_detail() {
//        Pair<Boolean, String> result;
//        int id;
//        String name, phone, address, email;
//
//        id = supplierBLL.getAutoID(supplierBLL.searchRole_details("deleted = 0")); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
//        name = jTextFieldRole_detail.get(0).getText();
//        phone = jTextFieldRole_detail.get(1).getText();
//        address = jTextFieldRole_detail.get(2).getText();
//        email = jTextFieldRole_detail.get(3).getText();

//        Role_detail supplier = new Role_detail(id, name, phone, address, email, false); // false là tồn tại, true là đã xoá
//
//        result = supplierBLL.addRole_detail(supplier);
//
//        if (result.getKey()) {
//            JOptionPane.showMessageDialog(null, result.getValue(),
//                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            dispose();
//        } else {
//            JOptionPane.showMessageDialog(null, result.getValue(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
    }
}
