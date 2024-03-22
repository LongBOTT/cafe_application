package com.coffee.GUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_DetailBLL;
import com.coffee.DTO.Role;
import com.coffee.DTO.Staff;
import com.coffee.DTO.Role_Detail;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditStaffGUI;
import com.coffee.GUI.components.EventSwitchSelected;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.SwitchButton;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class ChangeRoleGUI extends JDialog {
    private RoundedPanel role_detail_panel;
    private RoundedPanel role_detail_bonus_panel;
    private RoundedPanel role_detail_deduction_panel;
    private JLabel titleName;
    private List<JLabel> attributeRole_detail;
    private JComboBox<String> jComboBoxRole;
    private JComboBox<String> jComboBoxTypeSalary;
    private JLabel jLabelSalary;
    private JLabel jLabelTypeSalary;
    private JTextField textFieldSalary;
    private JButton buttonCancel;
    private JButton buttonSet;
    private SwitchButton switchButtonBonus;
    private SwitchButton switchButtonDeduction;
    private Role_DetailBLL role_detailBLL = new Role_DetailBLL();
    private Staff staff;
    private Role_Detail roleDetail;
    private RoundedPanel content = new RoundedPanel();
    private JScrollPane scrollPaneBonus;
    private JScrollPane scrollPaneDeduction;

    public ChangeRoleGUI(Staff staff) {
        super((Frame) null, "", true);
        getContentPane().setBackground(new Color(217, 217, 217));
        setTitle("Thiết lập lương");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setIconImage(new FlatSVGIcon("image/coffee_logo.svg").getImage());
        setSize(new Dimension(800, 500));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(Cafe_Application.homeGUI);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });
        this.staff = staff;
        List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_detailsByStaff(staff.getId());
        if (!role_detailList.isEmpty()) {
            roleDetail = role_detailList.get(0);
        } else {
            roleDetail = null;
        }
        init();
        setVisible(true);
    }

    private void init() {
        role_detail_panel = new RoundedPanel();
        role_detail_bonus_panel = new RoundedPanel();
        role_detail_deduction_panel = new RoundedPanel();
        titleName = new JLabel();
        attributeRole_detail = new ArrayList<>();
        jComboBoxRole = new JComboBox<>();
        jComboBoxTypeSalary = new JComboBox<>();
        jLabelSalary = new JLabel();
        textFieldSalary = new JTextField();
        buttonCancel = new JButton("Huỷ");
        buttonSet = new JButton("Thiết lập");
        switchButtonBonus = new SwitchButton();
        switchButtonDeduction = new SwitchButton();
        scrollPaneBonus = new JScrollPane();
        scrollPaneDeduction = new JScrollPane();

        RoundedPanel title = new RoundedPanel();
        RoundedPanel containerButton = new RoundedPanel();

        title.setLayout(new BorderLayout());
        title.setBackground(new Color(232, 206, 180));
        title.setPreferredSize(new Dimension(700, 40));
        add(title);

        content.setLayout(new FlowLayout());
        content.setBackground(new Color(217, 217, 217));
        content.setPreferredSize(new Dimension(700, 350));
        add(content);

        containerButton.setLayout(new FlowLayout());
        containerButton.setBackground(new Color(217, 217, 217));
        containerButton.setPreferredSize(new Dimension(700, 70));
        add(containerButton);

        titleName.setText("Thiết lập lương");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);


        role_detail_panel.setBackground(Color.white);
        role_detail_panel.setLayout(new MigLayout("",
                "50[]20[][]50",
                "15[]15[]15"));
        role_detail_panel.setPreferredSize(new Dimension(685, 200));
        content.add(role_detail_panel);

        role_detail_bonus_panel.setBackground(Color.white);
        role_detail_bonus_panel.setPreferredSize(new Dimension(685, 50));
        role_detail_bonus_panel.setLayout(new MigLayout("",
                "50[]20[]50",
                "15[]15[]15"));
        content.add(role_detail_bonus_panel);

        JLabel labelBonus = new JLabel();
        labelBonus.setPreferredSize(new Dimension(170, 30));
        labelBonus.setText("Phụ cấp");
        labelBonus.setFont((new Font("Public Sans", Font.PLAIN, 16)));
        role_detail_bonus_panel.add(labelBonus);

        switchButtonBonus.addEventSelected(new EventSwitchSelected() {
            @Override
            public void onSelected(boolean selected) {
                if (selected) {
                    showBonusPanel();
                } else {
                    role_detail_bonus_panel.setPreferredSize(new Dimension(685, 50));
                    content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                    content.repaint();
                    content.revalidate();
                    setSize(new Dimension(800, getHeight() - 130));
                    repaint();
                    revalidate();
                    setLocationRelativeTo(Cafe_Application.homeGUI);
                }
            }
        });
        role_detail_bonus_panel.add(switchButtonBonus, "wrap");

        JPanel contentBonus = new JPanel();
        contentBonus.setPreferredSize(new Dimension(685, 120));
        role_detail_bonus_panel.add(contentBonus, "span, wrap");
        contentBonus.add(scrollPaneBonus);

        role_detail_deduction_panel.setBackground(Color.white);
        role_detail_deduction_panel.setPreferredSize(new Dimension(685, 50));
        role_detail_deduction_panel.setLayout(new MigLayout("",
                "50[]20[]50",
                "15[]15[]15"));
        content.add(role_detail_deduction_panel);

        JLabel labelDeduction = new JLabel();
        labelDeduction.setPreferredSize(new Dimension(170, 30));
        labelDeduction.setText("Giảm trừ");
        labelDeduction.setFont((new Font("Public Sans", Font.PLAIN, 16)));
        role_detail_deduction_panel.add(labelDeduction);

        switchButtonDeduction.addEventSelected(new EventSwitchSelected() {
            @Override
            public void onSelected(boolean selected) {
                if (selected) {
                    showDeductionPanel();
                } else {
                    role_detail_deduction_panel.setPreferredSize(new Dimension(685, 50));
                    content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                    content.repaint();
                    content.revalidate();
                    setSize(new Dimension(800, getHeight() - 130));
                    repaint();
                    revalidate();
                    setLocationRelativeTo(Cafe_Application.homeGUI);
                }
            }
        });
        role_detail_deduction_panel.add(switchButtonDeduction, "wrap");

        JPanel contentDeduction = new JPanel();
        contentDeduction.setPreferredSize(new Dimension(685, 120));
        role_detail_deduction_panel.add(contentDeduction, "span, wrap");
        contentDeduction.add(scrollPaneDeduction);

        for (String string : new String[]{"Nhân viên", "Chức vụ", "Loại lương", ""}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeRole_detail.add(label);
            role_detail_panel.add(label);


            if (string.equals("Nhân viên")) {
                JLabel jLabel = new JLabel(staff.getName());
                jLabel.setFont((new Font("Public Sans", Font.BOLD, 16)));
                role_detail_panel.add(jLabel, "wrap");
            }

            if (string.equals("Chức vụ")) {
                for (Role role : new RoleBLL().searchRoles("id > 1"))
                    jComboBoxRole.addItem(role.getName());

                if (roleDetail != null) {
                    Role role = new RoleBLL().searchRoles("id = " + roleDetail.getRole_id()).get(0);
                    jComboBoxRole.setSelectedItem(role.getName());
                }

                jComboBoxRole.setPreferredSize(new Dimension(1000, 30));
                jComboBoxRole.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                role_detail_panel.add(jComboBoxRole, "wrap");
            }

            if (string.equals("Loại lương")) {
                jComboBoxTypeSalary.addItem("-- Chọn loại lương --");
                jComboBoxTypeSalary.addItem("1. Cố định");
                jComboBoxTypeSalary.addItem("2. Theo giờ làm việc");

                if (roleDetail != null) {
                    if (roleDetail.getType_salary() == 1)
                        jComboBoxTypeSalary.setSelectedIndex(1);

                    else if (roleDetail.getType_salary() == 2)
                        jComboBoxTypeSalary.setSelectedIndex(2);
                } else
                    jComboBoxTypeSalary.setSelectedIndex(0);

                jComboBoxTypeSalary.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loadTypeSalary();
                    }
                });

                jComboBoxTypeSalary.setPreferredSize(new Dimension(1000, 30));
                jComboBoxTypeSalary.setFont((new Font("Public Sans", Font.PLAIN, 14)));

                role_detail_panel.add(jComboBoxTypeSalary, "wrap");
            }

            if (string.isEmpty()) {
                textFieldSalary.setPreferredSize(new Dimension(1000, 30));
                textFieldSalary.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                if (roleDetail != null) {
                    textFieldSalary.setText(String.valueOf(roleDetail.getSalary()));
                }
                role_detail_panel.add(textFieldSalary);
            }

        }
        jLabelTypeSalary = new JLabel("          ");
        jLabelTypeSalary.setFont((new Font("Public Sans", Font.PLAIN, 13)));
        role_detail_panel.add(jLabelTypeSalary, "wrap");

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
        if (roleDetail != null) {
            if (role_id == roleDetail.getRole_id()) {
                entry_date = roleDetail.getEntry_date();
                type_salary = jComboBoxTypeSalary.getSelectedIndex();
                salary = Double.parseDouble(textFieldSalary.getText());

                Role_Detail role_detail = new Role_Detail(role_id, staff_id, entry_date, salary, type_salary); // false là tồn tại, true là đã xoá

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
            }
        }
        if (roleDetail == null || role_id != roleDetail.getRole_id()) {
            entry_date = LocalDateTime.now();
            type_salary = jComboBoxTypeSalary.getSelectedIndex();
            salary = Double.parseDouble(textFieldSalary.getText());

            Role_Detail role_detail = new Role_Detail(role_id, staff_id, entry_date, salary, type_salary); // false là tồn tại, true là đã xoá

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

    private void showBonusPanel() {
        role_detail_bonus_panel.setPreferredSize(new Dimension(685, 190));
        content.setPreferredSize(new Dimension(700, content.getHeight() + 130));
        content.repaint();
        content.revalidate();
        setSize(new Dimension(800, getHeight() + 130));
        repaint();
        revalidate();
        setLocationRelativeTo(Cafe_Application.homeGUI);

        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(1000, 500));
        jPanel.setBackground(Color.PINK);
        scrollPaneBonus.setViewportView(jPanel);
    }

    private void showDeductionPanel() {
        role_detail_deduction_panel.setPreferredSize(new Dimension(685, 190));
        content.setPreferredSize(new Dimension(700, content.getHeight() + 130));
        content.repaint();
        content.revalidate();
        setSize(new Dimension(800, getHeight() + 130));
        repaint();
        revalidate();
        setLocationRelativeTo(Cafe_Application.homeGUI);

        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(1000, 500));
        jPanel.setBackground(Color.PINK);
        scrollPaneDeduction.setViewportView(jPanel);
    }

    public void cancel() {
        String[] options = new String[]{"Huỷ", "Thoát"};
        int choice = JOptionPane.showOptionDialog(null, "Bạn có muốn thoát?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 1)
            dispose();
    }
}
