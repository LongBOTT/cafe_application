package com.coffee.GUI.DialogGUI.FromEditGUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_detailBLL;
import com.coffee.DTO.Role;
import com.coffee.DTO.Role_detail;
import com.coffee.DTO.Staff;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.ChangePasswordGUI;
import com.coffee.GUI.ChangeRoleGUI;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.BLL.StaffBLL;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditStaffGUI extends DialogForm {

    private JLabel titleName;
    private List<JLabel> attributeStaff;

    private JButton buttonCancel;
    private JButton buttonEdit;

    private StaffBLL staffBLL = new StaffBLL();

    private List<JTextField> jTextFieldsStaff;
    public static JTextField textFieldRole;
    private JDateChooser jDateChooser = new JDateChooser();

    private Staff staff;

    public EditStaffGUI(Staff staff) {
        super();
        super.setTitle("Cập Nhật Thông Tin Nhân Viên");
        this.staff = staff;
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
                "200[]20[]",
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
                    List<Role_detail> roleDetails = new Role_detailBLL().searchRole_details("staff_id = " + staff.getId());
                    for (Role_detail roleDetail : roleDetails) {
                        int roleId = roleDetail.getRole_id();
                        Role role = new RoleBLL().searchRoles("id = " + roleId).get(0);
                        textFieldRole.setText(role.getName());
                        textFieldRole.setEditable(false);
                        break; // Dừng vòng lặp sau khi tìm thấy một chức vụ
                    }
                    textFieldRole.setEditable(false);
                    content.add(textFieldRole);

                    JLabel iconChangePasswd = new JLabel(new FlatSVGIcon("icon/edit.svg"));
                    iconChangePasswd.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    iconChangePasswd.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            new ChangeRoleGUI(staff);
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
        staffNo = jTextFieldsStaff.get(1).getText().trim();
        name = jTextFieldsStaff.get(2).getText().trim();
        gender = Boolean.parseBoolean(jTextFieldsStaff.get(3).getText().trim()); // Chuyển đổi giá trị boolean từ text field
        birthdate = jDateChooser.getDate(); // Lấy ngày tháng từ JDateChooser
        phone = jTextFieldsStaff.get(4).getText().trim();
        address = jTextFieldsStaff.get(5).getText().trim();
        email = jTextFieldsStaff.get(6).getText().trim();


        Staff staff = new Staff(id, name, staffNo, gender, birthdate, phone, address, email, false);
        // false là tồn tại, true là đã xoá

        result = staffBLL.updateStaff(staff);

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
