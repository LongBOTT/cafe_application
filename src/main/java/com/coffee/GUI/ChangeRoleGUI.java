package com.coffee.GUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddBonusGUI;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddDeductionGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditBonusGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditDeductionGUI;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class ChangeRoleGUI extends JDialog {
    private JPanel jPanelBonus;
    private JPanel jPanelDeduction;
    private RoundedPanel role_detail_panel;
    private RoundedPanel role_detail_bonus_panel;
    private RoundedPanel role_detail_deduction_panel;
    private JLabel titleName;
    private List<JLabel> attributeRole_detail;
    private JComboBox<String> jComboBoxRole;
    private JComboBox<String> jComboBoxTypeSalary;
    private JComboBox<String> jComboBoxBonus;
    private JComboBox<String> jComboBoxDeduction;
    private JLabel jLabelTypeSalary;
    private JTextField textFieldSalary;
    private JButton buttonCancel;
    private JButton buttonSet;
    private JButton buttonAddBonus;
    private JButton buttonAddDeduction;
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

        role_detail_bonus_panel.setBackground(new Color(217, 217, 217));
        role_detail_bonus_panel.setPreferredSize(new Dimension(685, 40));
        role_detail_bonus_panel.setLayout(new MigLayout("",
                "50[]445[]50",
                "15[]5[]5[]"));
        content.add(role_detail_bonus_panel);

        JLabel labelBonus = new JLabel();
        labelBonus.setText("Phụ cấp");
        labelBonus.setFont((new Font("Public Sans", Font.PLAIN, 16)));
        role_detail_bonus_panel.add(labelBonus);

        switchButtonBonus.addEventSelected(new EventSwitchSelected() {
            @Override
            public void onSelected(boolean selected) {
                if (selected && switchButtonBonus.isEnabled()) {
                    showBonus_DeductionPanel(true);
                }
                if (!selected && switchButtonBonus.isEnabled()) {
                    role_detail_bonus_panel.setPreferredSize(new Dimension(685, 40));
                    content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                    content.repaint();
                    content.revalidate();
                    setSize(new Dimension(800, getHeight() - 130));
                    repaint();
                    revalidate();
                    setLocationRelativeTo(Cafe_Application.homeGUI);
                }
                if (!switchButtonBonus.isEnabled()) {
                    JOptionPane.showMessageDialog(null, "Nhân viên chưa có chức vụ!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        role_detail_bonus_panel.add(switchButtonBonus, "wrap");

        scrollPaneBonus.setPreferredSize(new Dimension(685, 150));
        role_detail_bonus_panel.add(scrollPaneBonus, "span, wrap");

        buttonAddBonus = new JButton("+ Thêm phụ cấp");
        buttonAddBonus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddBonusGUI();
                List<Bonus> bonusList = new BonusBLL().searchBonuss();
                Bonus bonus = bonusList.get(bonusList.size() - 1);
                jComboBoxBonus.addItem(bonus.getName());
                setVisible(true);
            }
        });
        role_detail_bonus_panel.add(buttonAddBonus, "span, wrap");

        role_detail_deduction_panel.setBackground(new Color(217, 217, 217));
        role_detail_deduction_panel.setPreferredSize(new Dimension(685, 40));
        role_detail_deduction_panel.setLayout(new MigLayout("",
                "50[]440[]50",
                "15[]5[]5[]"));
        content.add(role_detail_deduction_panel);

        JLabel labelDeduction = new JLabel();
        labelDeduction.setText("Giảm trừ");
        labelDeduction.setFont((new Font("Public Sans", Font.PLAIN, 16)));
        role_detail_deduction_panel.add(labelDeduction);

        switchButtonDeduction.addEventSelected(new EventSwitchSelected() {
            @Override
            public void onSelected(boolean selected) {
                if (selected && switchButtonDeduction.isEnabled()) {
                    showBonus_DeductionPanel(false);
                }
                if (!selected && switchButtonDeduction.isEnabled()) {
                    role_detail_deduction_panel.setPreferredSize(new Dimension(685, 40));
                    content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                    content.repaint();
                    content.revalidate();
                    setSize(new Dimension(800, getHeight() - 130));
                    repaint();
                    revalidate();
                    setLocationRelativeTo(Cafe_Application.homeGUI);
                }
                if (!switchButtonDeduction.isEnabled()) {
                    JOptionPane.showMessageDialog(null, "Nhân viên chưa có chức vụ!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        role_detail_deduction_panel.add(switchButtonDeduction, "wrap");

        scrollPaneDeduction.setPreferredSize(new Dimension(685, 150));
        role_detail_deduction_panel.add(scrollPaneDeduction, "span, wrap");

        buttonAddDeduction = new JButton("+ Thêm giảm trừ");
        buttonAddDeduction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddDeductionGUI();
                List<Deduction> deductionList = new DeductionBLL().searchDeductions();
                Deduction deduction = deductionList.get(deductionList.size() - 1);
                jComboBoxDeduction.addItem(deduction.getName());
                setVisible(true);
            }
        });
        role_detail_deduction_panel.add(buttonAddDeduction, "span, wrap");


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
                textFieldSalary.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                });
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

        if (roleDetail == null) {
            switchButtonBonus.setEnabled(false);
            switchButtonDeduction.setEnabled(false);
        }
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

    private void showBonus_DeductionPanel(boolean flag) {

        if (flag) {
            role_detail_bonus_panel.setPreferredSize(new Dimension(685, 190));
        } else {
            role_detail_deduction_panel.setPreferredSize(new Dimension(685, 190));
        }

        content.setPreferredSize(new Dimension(700, content.getHeight() + 130));
        content.repaint();
        content.revalidate();
        setSize(new Dimension(800, getHeight() + 130));
        repaint();
        revalidate();
        setLocationRelativeTo(Cafe_Application.homeGUI);

        JLabel titleName = new JLabel();
        JLabel titleAmount = new JLabel();
        JLabel titleType = new JLabel();

        if (flag) {
            jPanelBonus = new JPanel();
            jPanelBonus.setBackground(new Color(245, 246, 250));
            jPanelBonus.setLayout(new MigLayout("", "10[]10[]10[]70[]0[]0"));

            titleName.setText("Tên phụ cấp");
            titleAmount.setText("Số tiền phụ cấp");
            titleType.setText("Loại phụ cấp");

            jPanelBonus.add(titleName);
            jPanelBonus.add(titleAmount);
            jPanelBonus.add(titleType, "span, wrap");
        } else {
            jPanelDeduction = new JPanel();
            jPanelDeduction.setBackground(new Color(245, 246, 250));
            jPanelDeduction.setLayout(new MigLayout("", "10[]10[]10[]100[]0[]0"));

            titleName.setText("Tên giảm trừ");
            titleAmount.setText("Số tiền giảm trừ");
            titleType.setText("Loại giảm trừ");

            jPanelDeduction.add(titleName);
            jPanelDeduction.add(titleAmount);
            jPanelDeduction.add(titleType, "span, wrap");
        }

        titleName.setPreferredSize(new Dimension(120, 30));
        titleName.setFont((new Font("Public Sans", Font.BOLD, 13)));

        titleAmount.setPreferredSize(new Dimension(120, 30));
        titleAmount.setFont((new Font("Public Sans", Font.BOLD, 13)));


        titleType.setPreferredSize(new Dimension(120, 30));
        titleType.setFont((new Font("Public Sans", Font.BOLD, 13)));


        if (flag) {
            jComboBoxBonus = new JComboBox<>();
            jComboBoxBonus.addItem("--Chon loại phụ cấp--");
            for (Bonus bonus : new BonusBLL().searchBonuss())
                jComboBoxBonus.addItem(bonus.getName());

            jComboBoxBonus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addRole_Detail_Bonus();
                }
            });
        } else {
            jComboBoxDeduction = new JComboBox<>();
            jComboBoxDeduction.addItem("--Chon loại giảm trừ--");
            for (Deduction deduction : new DeductionBLL().searchDeductions())
                jComboBoxDeduction.addItem(deduction.getName());

            jComboBoxDeduction.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addRole_Detail_Deduction();
                }
            });
        }

        if (roleDetail != null) {
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if (flag) {
                List<Role_Detail_Bonus> roleDetailBonusList = new Role_Detail_BonusBLL().searchRole_Detail_Bonuss(
                        "role_id = " + roleDetail.getRole_id(),
                        "staff_id = " + roleDetail.getStaff_id(),
                        "entry_date = '" + roleDetail.getEntry_date().format(myFormatObj) + "'");
                if (!roleDetailBonusList.isEmpty()) {
                    for (Role_Detail_Bonus roleDetailBonus : roleDetailBonusList) {
                        Bonus bonus = new BonusBLL().searchBonuss("id = " + roleDetailBonus.getBonus_id()).get(0);

                        jComboBoxBonus.removeItem(bonus.getName());

                        JLabel jLabelBonusName = new JLabel(bonus.getName());
                        jLabelBonusName.setPreferredSize(new Dimension(120, 30));
                        jLabelBonusName.setFont((new Font("Public Sans", Font.PLAIN, 13)));
                        jPanelBonus.add(jLabelBonusName);

                        JLabel jLabelBonusAmount = new JLabel(String.valueOf(bonus.getBonus_amount()));
                        jLabelBonusAmount.setPreferredSize(new Dimension(120, 30));
                        jLabelBonusAmount.setFont((new Font("Public Sans", Font.PLAIN, 13)));
                        jPanelBonus.add(jLabelBonusAmount);

                        JLabel jLabelBonusType = new JLabel();
                        if (bonus.getBonus_type() == 0)
                            jLabelBonusType.setText("Phụ cấp theo ngày làm");
                        if (bonus.getBonus_type() == 1)
                            jLabelBonusType.setText("Phụ cấp theo tháng làm");
                        jLabelBonusType.setPreferredSize(new Dimension(120, 30));
                        jLabelBonusType.setFont((new Font("Public Sans", Font.PLAIN, 13)));
                        jPanelBonus.add(jLabelBonusType);

                        JLabel jLabelIconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
                        jLabelIconEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        jLabelIconEdit.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                setVisible(false);
                                role_detail_bonus_panel.setPreferredSize(new Dimension(685, 40));
                                content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                                content.repaint();
                                content.revalidate();
                                setSize(new Dimension(800, getHeight() - 130));
                                repaint();
                                revalidate();
                                setLocationRelativeTo(Cafe_Application.homeGUI);
                                new EditBonusGUI(bonus);
                                showBonus_DeductionPanel(true);
                                setVisible(true);
                                pack();
                            }
                        });
                        jPanelBonus.add(jLabelIconEdit);

                        JLabel jLabelIconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
                        jLabelIconRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        jLabelIconRemove.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                deleteRole_Detail_Bonus(roleDetailBonus);
                                showBonus_DeductionPanel(true);
                            }
                        });
                        jPanelBonus.add(jLabelIconRemove, "wrap");
                    }
                }
            } else {
                List<Role_Detail_Deduction> roleDetailDeductionList = new Role_Detail_DeductionBLL().searchRole_Detail_Deductions(
                        "role_id = " + roleDetail.getRole_id(),
                        "staff_id = " + roleDetail.getStaff_id(),
                        "entry_date = '" + roleDetail.getEntry_date().format(myFormatObj) + "'");
                if (!roleDetailDeductionList.isEmpty()) {
                    for (Role_Detail_Deduction roleDetailDeduction : roleDetailDeductionList) {
                        Deduction deduction = new DeductionBLL().searchDeductions("id = " + roleDetailDeduction.getDeduction_id()).get(0);

                        jComboBoxDeduction.removeItem(deduction.getName());

                        JLabel jLabelDeductionName = new JLabel(deduction.getName());
                        jLabelDeductionName.setFont((new Font("Public Sans", Font.PLAIN, 13)));
                        jPanelDeduction.add(jLabelDeductionName);

                        JLabel jLabelDeductionAmount = new JLabel(String.valueOf(deduction.getDeduction_amount()));
                        jLabelDeductionAmount.setFont((new Font("Public Sans", Font.PLAIN, 13)));
                        jPanelDeduction.add(jLabelDeductionAmount);

                        JLabel jLabelDeductionType = new JLabel();
                        if (deduction.getDeduction_type() == 0)
                            jLabelDeductionType.setText("Giảm trừ đi muộn");
                        if (deduction.getDeduction_type() == 1)
                            jLabelDeductionType.setText("Giảm trừ về sớm");
                        if (deduction.getDeduction_type() == 2)
                            jLabelDeductionType.setText("Giảm trừ cố định");
                        jLabelDeductionType.setFont((new Font("Public Sans", Font.PLAIN, 13)));
                        jPanelDeduction.add(jLabelDeductionType);

                        RoundedPanel roundedPanel = new RoundedPanel();
                        roundedPanel.setBackground(Color.white);
                        roundedPanel.setLayout(new GridBagLayout());

                        JLabel jLabelIconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
                        jLabelIconEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        jLabelIconEdit.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                setVisible(false);
                                role_detail_deduction_panel.setPreferredSize(new Dimension(685, 40));
                                content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                                content.repaint();
                                content.revalidate();
                                setSize(new Dimension(800, getHeight() - 130));
                                repaint();
                                revalidate();
                                setLocationRelativeTo(Cafe_Application.homeGUI);
                                new EditDeductionGUI(deduction);
                                showBonus_DeductionPanel(false);
                                setVisible(true);
                            }
                        });
                        jPanelDeduction.add(jLabelIconEdit);

                        JLabel jLabelIconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
                        jLabelIconRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        jLabelIconRemove.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                deleteRole_Detail_Deduction(roleDetailDeduction);
                                showBonus_DeductionPanel(false);
                            }
                        });
                        jPanelDeduction.add(jLabelIconRemove, "wrap");
                    }
                }
            }
        }

        if (flag) {
            jComboBoxBonus.setSelectedIndex(0);
            jPanelBonus.add(jComboBoxBonus);
            scrollPaneBonus.setViewportView(jPanelBonus);
        } else {
            jComboBoxDeduction.setSelectedIndex(0);
            jPanelDeduction.add(jComboBoxDeduction);
            scrollPaneDeduction.setViewportView(jPanelDeduction);
        }

    }

    private void deleteRole_Detail_Bonus(Role_Detail_Bonus roleDetailBonus) {
        Pair<Boolean, String> result = new Role_Detail_BonusBLL().deleteRole_Detail_Bonus(roleDetailBonus);

        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            role_detail_bonus_panel.setPreferredSize(new Dimension(685, 40));
            content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
            content.repaint();
            content.revalidate();
            setSize(new Dimension(800, getHeight() - 130));
            repaint();
            revalidate();
            setLocationRelativeTo(Cafe_Application.homeGUI);
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRole_Detail_Deduction(Role_Detail_Deduction roleDetailDeduction) {
        Pair<Boolean, String> result = new Role_Detail_DeductionBLL().deleteRole_Detail_Deduction(roleDetailDeduction);

        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            role_detail_deduction_panel.setPreferredSize(new Dimension(685, 40));
            content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
            content.repaint();
            content.revalidate();
            setSize(new Dimension(800, getHeight() - 130));
            repaint();
            revalidate();
            setLocationRelativeTo(Cafe_Application.homeGUI);
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancel() {
        String[] options = new String[]{"Huỷ", "Thoát"};
        int choice = JOptionPane.showOptionDialog(null, "Bạn có muốn thoát?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 1)
            dispose();
    }

    private void addRole_Detail_Bonus() {
        int selectedIndex = jComboBoxBonus.getSelectedIndex();
        if (selectedIndex != 0) {
            Bonus bonus = new BonusBLL().searchBonuss("name = '" + jComboBoxBonus.getSelectedItem() + "'").get(0);
            Role_Detail_Bonus roleDetailBonus = new Role_Detail_Bonus(roleDetail.getRole_id(), roleDetail.getStaff_id(), roleDetail.getEntry_date(), bonus.getId());

            Pair<Boolean, String> result = new Role_Detail_BonusBLL().addRole_Detail_Bonus(roleDetailBonus);

            if (result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                role_detail_bonus_panel.setPreferredSize(new Dimension(685, 40));
                content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                content.repaint();
                content.revalidate();
                setSize(new Dimension(800, getHeight() - 130));
                repaint();
                revalidate();
                setLocationRelativeTo(Cafe_Application.homeGUI);
                showBonus_DeductionPanel(true);
            } else {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addRole_Detail_Deduction() {
        int selectedIndex = jComboBoxDeduction.getSelectedIndex();
        if (selectedIndex != 0) {
            Deduction deduction = new DeductionBLL().searchDeductions("name = '" + jComboBoxDeduction.getSelectedItem() + "'").get(0);
            Role_Detail_Deduction roleDetailDeduction = new Role_Detail_Deduction(roleDetail.getRole_id(), roleDetail.getStaff_id(), roleDetail.getEntry_date(), deduction.getId());

            Pair<Boolean, String> result = new Role_Detail_DeductionBLL().addRole_Detail_Deduction(roleDetailDeduction);

            if (result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                role_detail_deduction_panel.setPreferredSize(new Dimension(685, 40));
                content.setPreferredSize(new Dimension(700, content.getHeight() - 130));
                content.repaint();
                content.revalidate();
                setSize(new Dimension(800, getHeight() - 130));
                repaint();
                revalidate();
                setLocationRelativeTo(Cafe_Application.homeGUI);
                showBonus_DeductionPanel(false);
            } else {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
