package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.Work_ScheduleBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.DTO.Work_Schedule;
import com.coffee.DTO.Staff;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.components.AutocompleteJComboBox;
import com.coffee.GUI.components.StringSearchable;
import com.coffee.main.Cafe_Application;
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

public class AddWorkScheduleGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeWork_Schedule;
    private List<JTextField> jTextFieldWork_Schedule;
    private AutocompleteJComboBox combo;
    private JTextField jTextFieldDate;
    private JTextField dateTextField;
    private JDateChooser jDateChooser;
    private JButton buttonCancel;
    private JButton buttonAdd;
    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JCheckBox jCheckBox3;

    private StaffBLL staffBLL = new StaffBLL();
    private Work_ScheduleBLL work_ScheduleBLL = new Work_ScheduleBLL();
    private List<String> staffList;

    public AddWorkScheduleGUI() {
        super();
        super.setTitle("Thêm lịch làm việc");
        super.setSize(new Dimension(600, 400));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        jTextFieldDate = new JTextField();
        jDateChooser = new JDateChooser();
        dateTextField = new JTextField();
        attributeWork_Schedule = new ArrayList<>();
        jTextFieldWork_Schedule = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        jCheckBox1 = new JCheckBox();
        jCheckBox2 = new JCheckBox();
        jCheckBox3 = new JCheckBox();
        staffList = new ArrayList<String>();
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "20[]20[]20"));

        titleName.setText("Thêm lịch làm việc");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Nhân viên", "Ngày", "Ca"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeWork_Schedule.add(label);
            content.add(label);


            if (string.equals("Nhân viên")) {
                for (Staff staff : staffBLL.searchStaffs("deleted = 0")) {
                    staffList.add(String.valueOf(staff.getId()));
                }

                staffList.replaceAll(s -> staffBLL.findStaffsBy(Map.of("id", Integer.valueOf(s))).get(0).getName() + " - " + s);
                StringSearchable searchable = new StringSearchable(staffList);

                combo = new AutocompleteJComboBox(searchable);
                combo.setPreferredSize(new Dimension(1000, 30));
                combo.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                combo.setBackground(new Color(245, 246, 250));
                content.add(combo, "wrap");
                continue;
            }
            if (string.equals("Ngày")) {
                jTextFieldDate = new JTextField();
                jTextFieldDate.setFont(new Font("Times New Roman", Font.BOLD, 15));
                jTextFieldDate.setPreferredSize(new Dimension(1000, 30));
                jTextFieldDate.setAutoscrolls(true);

                jDateChooser = new JDateChooser();
                jDateChooser.setDateFormatString("dd/MM/yyyy");
                jDateChooser.setPreferredSize(new Dimension(1000, 30));
                jDateChooser.setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

                dateTextField = (JTextField) jDateChooser.getDateEditor().getUiComponent();
                dateTextField.setFont(new Font("Lexend", Font.BOLD, 14));

                content.add(jDateChooser, "wrap");
                continue;
            }
            if (string.equals("Ca")) {
                JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                jPanel.setPreferredSize(new Dimension(1000, 30));
                jPanel.setBackground(Color.white);

                jCheckBox1.setText("1: 6h - 12h");
                jCheckBox2.setText("2: 12h - 18h");
                jCheckBox3.setText("3: 18h - 23h");

                jPanel.add(jCheckBox1);
                jPanel.add(jCheckBox2);
                jPanel.add(jCheckBox3);
                content.add(jPanel, "wrap");
            }
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

        buttonAdd.setPreferredSize(new Dimension(100, 30));
        buttonAdd.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addWork_Schedule();
            }
        });
        containerButton.add(buttonAdd);
    }

    private void addWork_Schedule() {
        Pair<Boolean, String> result;

        int id, staff_id;
        List<Integer> shifts = new ArrayList<>();
        Date date;
        String checkin, checkout;

        if (combo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!staffList.contains(combo.getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Nhân viên không hợp lệ!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Work_Schedule> work_schedules = work_ScheduleBLL.searchWork_schedules();
        work_schedules.sort(Comparator.comparing(Work_Schedule::getId));
        id = work_ScheduleBLL.getAutoID(work_schedules);

        staff_id = Integer.parseInt(Objects.requireNonNull(combo.getSelectedItem()).toString().split(" - ")[1]);
        date = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser.getDate()));

        if (jCheckBox1.isSelected())
            shifts.add(1);

        if (jCheckBox2.isSelected())
            shifts.add(2);

        if (jCheckBox3.isSelected())
            shifts.add(3);

        if (shifts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ca làm!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }

        checkin = "null";
        checkout = "null";

        List<Work_Schedule> newWork_scheduleList = new ArrayList<>();
        for (Integer shift : shifts) {
            Work_Schedule workSchedule = new Work_Schedule(id, staff_id, date, checkin, checkout, shift);
            newWork_scheduleList.add(workSchedule);
            id += 1;
        }
        result = work_ScheduleBLL.addWork_schedule(newWork_scheduleList);

        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
