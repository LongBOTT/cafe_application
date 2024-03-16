package com.coffee.GUI.DialogGUI.FromEditGUI;

import com.coffee.BLL.StaffBLL;
import com.coffee.BLL.Work_ScheduleBLL;

import com.coffee.DTO.Work_Schedule;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.components.AutocompleteJComboBox;
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

public class EditWorkScheduleGUI extends DialogForm {
    private StaffBLL staffBLL = new StaffBLL();
    private JLabel titleName;
    private List<JLabel> attributeWork_Schedule;
    private List<JTextField> jTextFieldWork_Schedule;
    private JTextField jTextFieldDate;
    private JTextField dateTextField;
    private JDateChooser jDateChooser;
    private JButton buttonCancel;
    private JButton buttonEdit;
    private ButtonGroup bgShift;
    private AutocompleteJComboBox combo;
    private Work_ScheduleBLL workScheduleBLL = new Work_ScheduleBLL();
    private Work_Schedule workSchedule;
    private List<String> staffList = new ArrayList<>();

    public EditWorkScheduleGUI(Work_Schedule workSchedule) {
        super();
        super.setTitle("Cập nhật lịch làm việc");
        this.workSchedule = workSchedule;
        init(workSchedule);
        setVisible(true);
    }

    private void init(Work_Schedule workSchedule) {
        titleName = new JLabel();
        jTextFieldDate = new JTextField();
        jDateChooser = new JDateChooser();
        dateTextField = new JTextField();
        attributeWork_Schedule = new ArrayList<>();
        jTextFieldWork_Schedule = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonEdit = new JButton("Cập nhật");
        bgShift = new ButtonGroup();
        content.setLayout(new MigLayout("",
                "200[]20[]200",
                "20[]20[]20"));

        titleName.setText("Cập nhật lịch làm việc");
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

            JTextField textField = new JTextField();

            if (string.equals("Nhân viên")) {
//                for (Staff staff : staffBLL.searchStaffs("deleted = 0")) {
//                    staffList.add(String.valueOf(staff.getId()));
//                }
//
//                staffList.replaceAll(s -> staffBLL.findStaffsBy(Map.of("id", Integer.valueOf(s))).get(0).getName() + " - " + s);
//                StringSearchable searchable = new StringSearchable(staffList);
//
//                combo = new AutocompleteJComboBox(searchable);
//                combo.setPreferredSize(new Dimension(1000, 30));
//                combo.setFont((new Font("Public Sans", Font.PLAIN, 14)));
//                combo.setBackground(new Color(245, 246, 250));
//                content.add(combo, "wrap");
//                continue;
                textField.setText(staffBLL.findStaffsBy(Map.of("id", workSchedule.getStaff_id())).get(0).getName());
                textField.setEditable(false);
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

                jDateChooser.getDateEditor().setDate(workSchedule.getDate());

                content.add(jDateChooser, "wrap");
                continue;
            }

            if (string.equals("Ca")) {
                JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                jPanel.setPreferredSize(new Dimension(1000, 30));
                jPanel.setBackground(Color.white);

                JRadioButton radioShift1 = new JRadioButton("1: 6h - 12h");
                JRadioButton radioShift2 = new JRadioButton("2: 12h - 18h");
                JRadioButton radioShift3 = new JRadioButton("3: 18h - 23h");

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
                textField.setText(workSchedule.getCheck_in());
            }

            if (string.equals("Giờ ra")) {
                textField.setText(workSchedule.getCheck_out());
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
                editWork_Schedule();
            }
        });
        containerButton.add(buttonEdit);
    }

    private void editWork_Schedule() {
        Pair<Boolean, String> result;
        int id, staff_id, shift = 0;
        Date date;
        String checkin, checkout;

        id = workSchedule.getId();
        staff_id = workSchedule.getStaff_id();
        date = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser.getDate()));
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
        checkin = jTextFieldWork_Schedule.get(1).getText().isEmpty() ? "null" : jTextFieldWork_Schedule.get(1).getText();
        checkout = jTextFieldWork_Schedule.get(2).getText().isEmpty() ? "null" : jTextFieldWork_Schedule.get(2).getText();


        Work_Schedule workSchedule = new Work_Schedule(id, staff_id, date, checkin, checkout, shift);

        result = workScheduleBLL.updateWork_schedule(workSchedule);

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
