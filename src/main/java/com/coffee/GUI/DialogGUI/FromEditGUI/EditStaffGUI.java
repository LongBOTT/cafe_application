package com.coffee.GUI.DialogGUI.FromEditGUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_DetailBLL;
import com.coffee.DTO.Role;
import com.coffee.DTO.Role_Detail;
import com.coffee.DTO.Staff;
import com.coffee.GUI.ChangeRoleGUI;
import com.coffee.GUI.CreateWorkScheduleGUI;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.BLL.StaffBLL;
import com.coffee.GUI.HomeGUI;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class EditStaffGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeStaff;
    private JButton buttonCancel;
    private JButton buttonEdit;
    private StaffBLL staffBLL = new StaffBLL();
    private List<JTextField> jTextFieldsStaff;
    public static JTextField textFieldRole;
    public static boolean changeRole = false;
    private JDateChooser jDateChooser = new JDateChooser();
    private Staff staff;
    private HomeGUI homeGUI;

    public EditStaffGUI(Staff staff, HomeGUI homeGUI) {
        super();
        super.setTitle("Cập Nhật Thông Tin Nhân Viên");
        this.staff = staff;
        this.homeGUI = homeGUI;
        super.setSize(new Dimension(600, 700));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        init(staff);
        setVisible(true);
    }

    private void init(Staff staff) {
        titleName = new JLabel();
        attributeStaff = new ArrayList<>();
        jTextFieldsStaff = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonEdit = new JButton("Cập nhật");
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "20[]20[]20"));

        titleName.setText("Cập Nhật Thông Tin Nhân Viên");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Mã Nhân Viên", "Tên Nhân Viên", "CCCD", "Giới Tính",
                "Ngày Sinh", "Chức Vụ", "Số Điện Thoại", "Địa Chỉ", "Email"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(150, 35));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 15)));
            attributeStaff.add(label);
            content.add(label);
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(280, 35));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));

            if (string.trim().equals("Ngày Sinh")) {
                Date birthDate = staff.getBirthdate();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                jDateChooser = new JDateChooser();
                jDateChooser.setDateFormatString("dd/MM/yyyy");
                jDateChooser.setDate(birthDate);
                jDateChooser.setPreferredSize(new Dimension(180, 35));
                jDateChooser.setMinSelectableDate(java.sql.Date.valueOf("1000-01-01"));
                textField = (JTextField) jDateChooser.getDateEditor().getUiComponent();
                textField.setText(dateFormat.format(birthDate));
                jDateChooser.setEnabled(false);
                content.add(jDateChooser, "wrap");

                continue;
            } else {
                if (string.trim().equals("Mã Nhân Viên")) {
                    String staffId = Integer.toString(staff.getId());
                    textField.setText(staffId);
                    textField.setEditable(false);
                }
                if (string.trim().equals("Tên Nhân Viên")) {
                    textField.setText(staff.getName());
                    textField.setEditable(false);
                }
                if (string.trim().equals("CCCD")) {
                    textField.setText(staff.getStaffNo());
                    textField.setEditable(false);
                }
                if (string.trim().equals("Giới Tính")) {
                    boolean gender = staff.isGender();
                    String gender1 = gender ? "Nữ" : "Nam";
                    textField.setText(gender1);
                    textField.setEditable(false);
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
                    textFieldRole = new JTextField();
                    textFieldRole.setPreferredSize(new Dimension(280, 35));
                    textFieldRole.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                    textFieldRole.setBackground(new Color(245, 246, 250));
                    List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_detailsByStaff(staff.getId());

                    if (!role_detailList.isEmpty()) {
                        Role_Detail roleDetail = role_detailList.get(0);
                        Role role = new RoleBLL().searchRoles("id = " + roleDetail.getRole_id()).get(0);
                        textFieldRole.setText(role.getName());
                    } else {
                        textFieldRole.setText("Chưa có");
                    }
                    textFieldRole.setEditable(false);
                    content.add(textFieldRole);

                    JLabel iconChangePasswd = new JLabel(new FlatSVGIcon("icon/edit.svg"));
                    iconChangePasswd.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    iconChangePasswd.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            changeRole = false;
                            dispose();
                            new ChangeRoleGUI(staff);
                            if (changeRole && staff.getId() == HomeGUI.staff.getId()) {
                                JOptionPane.showMessageDialog(null, "Vui lòng đăng nhập lại.",
                                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                homeGUI.dispose();
                                System.gc();
                                Cafe_Application.loginGUI.setVisible(true);
                                return;
                            }
                            if (changeRole) {
                                CreateWorkScheduleGUI.refresh();
                            }
                            setVisible(true);
                        }
                    });
                    content.add(iconChangePasswd, "wrap");
                    continue;
                }

                content.add(textField, "wrap");
            }
            jTextFieldsStaff.add(textField);
        }

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

        buttonEdit.setPreferredSize(new Dimension(100, 30));
        buttonEdit.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editStaff();
            }
        });
        containerButton.add(buttonEdit);
    }


    private void editStaff() {
        Pair<Boolean, String> result;
        int id;
        String staffNo, name, phone, address, email;
        boolean gender;
        Date birthdate;
        id = staff.getId();
        staffNo = staff.getStaffNo();
        name = staff.getName();
        gender = staff.isGender(); // Chuyển đổi giá trị boolean từ text field
        birthdate = staff.getBirthdate(); // Lấy ngày tháng từ JDateChooser


        phone = jTextFieldsStaff.get(4).getText().trim();
        address = jTextFieldsStaff.get(5).getText().trim();
        email = jTextFieldsStaff.get(6).getText().trim();


        Staff newStaff = new Staff(id, staffNo, name, gender, birthdate, phone, address, email, false);
        // false là tồn tại, true là đã xoá

        result = staffBLL.updateStaff(staff, newStaff);

        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
}
