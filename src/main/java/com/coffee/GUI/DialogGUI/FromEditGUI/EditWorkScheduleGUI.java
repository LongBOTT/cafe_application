package com.coffee.GUI.DialogGUI.FromEditGUI;

import com.coffee.BLL.StaffBLL;
import com.coffee.BLL.Work_ScheduleBLL;

import com.coffee.DTO.Work_Schedule;
import com.coffee.GUI.CreateWorkScheduleGUI;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.components.AutocompleteJComboBox;
import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.MyTextFieldUnderLine;

import com.coffee.main.Cafe_Application;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class EditWorkScheduleGUI extends DialogForm {
    private StaffBLL staffBLL = new StaffBLL();
    private JLabel titleName;
    private List<JLabel> attributeWork_Schedule;
    private List<JTextField> jTextFieldWork_Schedule;
    private JButton buttonCancel;
    private JButton buttonEdit;
    private ButtonGroup bgShift;
    private AutocompleteJComboBox combo;
    private Work_ScheduleBLL workScheduleBLL = new Work_ScheduleBLL();
    private Work_Schedule workSchedule;
    private List<String> staffList = new ArrayList<>();
    private DatePicker datePicker;
    private JFormattedTextField editor;

    public EditWorkScheduleGUI(Work_Schedule workSchedule) {
        super();
        super.setTitle("Chấm công");
        super.setSize(new Dimension(600, 450));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        this.workSchedule = workSchedule;
        init(workSchedule);
        setVisible(true);
    }

    private void init(Work_Schedule workSchedule) {
        titleName = new JLabel();
        attributeWork_Schedule = new ArrayList<>();
        jTextFieldWork_Schedule = new ArrayList<>();
        buttonCancel = new JButton("Huỷ lịch");
        buttonEdit = new JButton("Cập nhật");
        bgShift = new ButtonGroup();
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "20[]20[]20"));
        datePicker = new DatePicker();
        editor = new JFormattedTextField();


        titleName.setText("Chấm công");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Nhân viên", "Ngày", "Ca", "Giờ vào", "Giờ ra"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeWork_Schedule.add(label);
            content.add(label);

            JTextField textField = new MyTextFieldUnderLine();

            if (string.equals("Nhân viên")) {
                textField.setText(staffBLL.findStaffsBy(Map.of("id", workSchedule.getStaff_id())).get(0).getName());
                textField.setEditable(false);
            }

            if (string.equals("Ngày")) {
                datePicker.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.SINGLE_DATE_SELECTED);
                datePicker.setEditor(editor);
                datePicker.setCloseAfterSelected(true);
                datePicker.setSelectedDate(workSchedule.getDate());
                datePicker.addDateSelectionListener(new DateSelectionListener() {
                    @Override
                    public void dateSelected(DateEvent dateEvent) {
                        if (datePicker.getDateSQL_Single() != null && datePicker.getDateSQL_Single().compareTo(workSchedule.getDate()) != 0) {
                            for (Enumeration<AbstractButton> buttons = bgShift.getElements(); buttons.hasMoreElements(); ) {
                                AbstractButton button = buttons.nextElement();

                                if (button.isSelected()) {
                                    if (button.getText().contains("1:"))
                                        checkExistByDate(datePicker.getDateSQL_Single(), 1);

                                    else if (button.getText().contains("2:"))
                                        checkExistByDate(datePicker.getDateSQL_Single(), 2);

                                    else if (button.getText().contains("3:"))
                                        checkExistByDate(datePicker.getDateSQL_Single(), 3);
                                }
                            }
                        }
                    }
                });
                editor.setPreferredSize(new Dimension(1000, 30));
                content.add(editor, "wrap");
                continue;
            }

            if (string.equals("Ca")) {
                JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                jPanel.setPreferredSize(new Dimension(1000, 30));
                jPanel.setBackground(Color.white);

                JRadioButton radioShift1 = new JRadioButton("1: 6h - 12h");
                radioShift1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (workSchedule.getShift() != 1)
                            checkExistByShift(datePicker.getDateSQL_Single(), 1);
                    }
                });

                JRadioButton radioShift2 = new JRadioButton("2: 12h - 18h");
                radioShift2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (workSchedule.getShift() != 2)
                            checkExistByShift(datePicker.getDateSQL_Single(), 2);
                    }
                });

                JRadioButton radioShift3 = new JRadioButton("3: 18h - 23h");
                radioShift3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (workSchedule.getShift() != 3)
                            checkExistByShift(datePicker.getDateSQL_Single(), 3);
                    }
                });

                if (workSchedule.getShift() == 1)
                    radioShift1.setSelected(true);

                if (workSchedule.getShift() == 2)
                    radioShift2.setSelected(true);

                if (workSchedule.getShift() == 3)
                    radioShift3.setSelected(true);

                jPanel.add(radioShift1);
                jPanel.add(radioShift2);
                jPanel.add(radioShift3);

                bgShift.add(radioShift1);
                bgShift.add(radioShift2);
                bgShift.add(radioShift3);
                content.add(jPanel, "wrap");
                continue;
            }

            if (string.equals("Giờ vào")) {
                JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                jPanel.setPreferredSize(new Dimension(1000, 30));
                jPanel.setBackground(Color.white);

                JTextField textField1 = new JTextField();
                JTextField textField2 = new JTextField();

                textField1.setPreferredSize(new Dimension(40, 30));
                textField2.setPreferredSize(new Dimension(40, 30));

                jPanel.add(textField1);
                jPanel.add(new JLabel(":"));
                jPanel.add(textField2);

                if (!workSchedule.getCheck_in().equals("null")) {
                    if (!workSchedule.getCheck_in().equals("Phép")) {
                        textField1.setText(workSchedule.getCheck_in().split(":")[0]);
                        textField2.setText(workSchedule.getCheck_in().split(":")[1]);
                    } else {
                        textField1.setEnabled(false);
                        textField2.setEnabled(false);
                    }

                }

                textField1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                });

                textField2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                });

                jTextFieldWork_Schedule.add(textField1);
                jTextFieldWork_Schedule.add(textField2);

                content.add(jPanel, "wrap");
                continue;
            }

            if (string.equals("Giờ ra")) {
                JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                jPanel.setPreferredSize(new Dimension(1000, 30));
                jPanel.setBackground(Color.white);

                JTextField textField1 = new JTextField();
                JTextField textField2 = new JTextField();

                textField1.setPreferredSize(new Dimension(40, 30));
                textField2.setPreferredSize(new Dimension(40, 30));

                jPanel.add(textField1);
                jPanel.add(new JLabel(":"));
                jPanel.add(textField2);

                if (!workSchedule.getCheck_out().equals("null")) {
                    if (!workSchedule.getCheck_out().equals("Phép")) {
                        textField1.setText(workSchedule.getCheck_out().split(":")[0]);
                        textField2.setText(workSchedule.getCheck_out().split(":")[1]);
                    } else {
                        textField1.setEnabled(false);
                        textField2.setEnabled(false);
                    }
                }

                textField1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                });

                textField2.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                });

                jTextFieldWork_Schedule.add(textField1);
                jTextFieldWork_Schedule.add(textField2);

                content.add(jPanel, "wrap");
                continue;
            }

            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldWork_Schedule.add(textField);
            content.add(textField, "wrap");

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
                deleteWorkSchedule();
            }
        });
        containerButton.add(buttonCancel);

        buttonEdit.setPreferredSize(new Dimension(100, 30));
        buttonEdit.setBackground(new Color(1, 120, 220));
        buttonEdit.setForeground(Color.white);
        buttonEdit.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editWork_Schedule();
            }
        });
        containerButton.add(buttonEdit);
    }

    private void checkExistByDate(Date date, int shift) {
        List<Work_Schedule> work_schedules = workScheduleBLL.findWork_schedulesBy(Map.of(
                "staff_id", workSchedule.getStaff_id(),
                "date", date,
                "shift", shift
        ));

        if (!work_schedules.isEmpty()) {
            datePicker.setSelectedDate(workSchedule.getDate());
            JOptionPane.showMessageDialog(null, "Lịch làm việc đã tồn tại!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void checkExistByShift(Date date, int shift) {
        System.out.println(date);
        List<Work_Schedule> work_schedules = workScheduleBLL.findWork_schedulesBy(Map.of(
                "staff_id", workSchedule.getStaff_id(),
                "date", date,
                "shift", shift
        ));

        if (!work_schedules.isEmpty()) {
            for (Enumeration<AbstractButton> buttons = bgShift.getElements(); buttons.hasMoreElements(); ) {
                AbstractButton button = buttons.nextElement();

                if (button.getText().contains("1:") && workSchedule.getShift() == 1) {
                    button.setSelected(true);
                } else if (button.getText().contains("2:") && workSchedule.getShift() == 2) {
                    button.setSelected(true);
                } else if (button.getText().contains("3:") && workSchedule.getShift() == 3) {
                    button.setSelected(true);
                }
            }
            JOptionPane.showMessageDialog(null, "Lịch làm việc đã tồn tại!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void editWork_Schedule() {
        Pair<Boolean, String> result;
        int id, staff_id, shift = 0;
        Date date;
        String checkin, checkout;

        id = workSchedule.getId();
        staff_id = workSchedule.getStaff_id();
        date = datePicker.getDateSQL_Single();

        if (date == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày làm việc!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Enumeration<AbstractButton> buttons = bgShift.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                if (button.getText().contains("1:"))
                    shift = 1;

                if (button.getText().contains("2:"))
                    shift = 2;

                if (button.getText().contains("3:"))
                    shift = 3;
            }
        }
        if (workSchedule.getCheck_in().equals("Phép") && workSchedule.getCheck_out().equals("Phép")) {
            checkin = workSchedule.getCheck_in();
            checkout = workSchedule.getCheck_out();
        } else {
            checkin = jTextFieldWork_Schedule.get(1).getText().isEmpty() || jTextFieldWork_Schedule.get(2).getText().isEmpty() ?
                    "null" : jTextFieldWork_Schedule.get(1).getText() + ":" + jTextFieldWork_Schedule.get(2).getText();
            checkout = jTextFieldWork_Schedule.get(3).getText().isEmpty() || jTextFieldWork_Schedule.get(4).getText().isEmpty() ?
                    "null" : jTextFieldWork_Schedule.get(3).getText() + ":" + jTextFieldWork_Schedule.get(4).getText();
        }


        Work_Schedule workSchedule = new Work_Schedule(id, staff_id, date, checkin, checkout, shift);

        result = workScheduleBLL.updateWork_schedule(workSchedule);

        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            CreateWorkScheduleGUI createWorkScheduleGUI = (CreateWorkScheduleGUI) Cafe_Application.homeGUI.allPanelModules[Cafe_Application.homeGUI.indexModuleCreateWorkScheduleGUI];
            createWorkScheduleGUI.refresh();
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteWorkSchedule() {
        String[] options = new String[]{"Huỷ", "Xác nhận"};
        int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá lịch làm việc?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
        if (choice == 1) {
            Pair<Boolean, String> result = workScheduleBLL.deleteWork_schedule(workSchedule);
            if (result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                CreateWorkScheduleGUI createWorkScheduleGUI = (CreateWorkScheduleGUI) Cafe_Application.homeGUI.allPanelModules[Cafe_Application.homeGUI.indexModuleCreateWorkScheduleGUI];
                createWorkScheduleGUI.refresh();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
