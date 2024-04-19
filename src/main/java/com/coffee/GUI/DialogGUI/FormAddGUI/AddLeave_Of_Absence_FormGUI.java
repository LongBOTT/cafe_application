package com.coffee.GUI.DialogGUI.FormAddGUI;


import com.coffee.BLL.Leave_Of_Absence_FormBLL;
import com.coffee.DTO.Leave_Of_Absence_Form;
import com.coffee.DTO.Staff;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class AddLeave_Of_Absence_FormGUI extends JDialog {
    private Staff staff;
    private List<JLabel> attributeLeaveOfAbsenceForm;
    private JTextField textField;
    private JTextField[] jTextFieldDate;
    private JTextField[] dateTextField;
    private JDateChooser[] jDateChooser;
    private JTextArea jTextArea;
    private JButton buttonCreate;
    private Leave_Of_Absence_FormBLL leaveOfAbsenceFormBLL = new Leave_Of_Absence_FormBLL();

    public AddLeave_Of_Absence_FormGUI(Staff staff) {
        super((Frame) null, "", true);
        this.staff = staff;
        getContentPane().setBackground(new Color(242, 245, 250));
        setTitle("Tạo đơn nghỉ phép");
        setLayout(new BorderLayout());
        setIconImage(new FlatSVGIcon("image/coffee_logo.svg").getImage());
        setSize(new Dimension(600, 500));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(Cafe_Application.homeGUI);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        attributeLeaveOfAbsenceForm = new ArrayList<>();
        textField = new JTextField();
        jDateChooser = new JDateChooser[2];
        jTextFieldDate = new JTextField[2];
        dateTextField = new JTextField[2];
        jTextArea = new JTextArea();
        buttonCreate = new JButton("Tạo");

        RoundedPanel top = new RoundedPanel();
        top.setLayout(new GridBagLayout());
        top.setBackground(new Color(242, 245, 250));
        top.setPreferredSize(new Dimension(600, 50));
        add(top, BorderLayout.NORTH);

        RoundedPanel center = new RoundedPanel();
        center.setBackground(new Color(242, 245, 250));
        center.setPreferredSize(new Dimension(600, 400));
        center.setLayout(new MigLayout("",
                "20[]20[]20",
                "20[]20[]20"));
        add(center, BorderLayout.CENTER);

        RoundedPanel bot = new RoundedPanel();
        bot.setLayout(new GridBagLayout());
        bot.setBackground(new Color(242, 245, 250));
        bot.setPreferredSize(new Dimension(600, 50));
        add(bot, BorderLayout.SOUTH);

        RoundedPanel roundedPanelTitle = new RoundedPanel();
        roundedPanelTitle.setBackground(new Color(242, 245, 250));
        roundedPanelTitle.setLayout(new BorderLayout());
        roundedPanelTitle.setPreferredSize(new Dimension(250, 40));
        top.add(roundedPanelTitle);

        JLabel jLabelTitle = new JLabel("Tạo Đơn Nghỉ Phép");
        jLabelTitle.setHorizontalAlignment(JLabel.CENTER);
        jLabelTitle.setFont(new Font("Lexend", Font.BOLD, 18));
        roundedPanelTitle.add(jLabelTitle);

        for (String string : new String[]{"Họ tên", "Từ ngày", "Đến ngày", "Lý do"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeLeaveOfAbsenceForm.add(label);
            center.add(label);

            if (string.equals("Họ tên")) {
                textField.setText(staff.getName());
                textField.setPreferredSize(new Dimension(1000, 50));
                textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                textField.setBackground(Color.white);
                textField.setEditable(false);
                center.add(textField, "wrap");
            }
            if (string.equals("Từ ngày")) {
                jTextFieldDate[0] = new JTextField();
                jTextFieldDate[0].setFont(new Font("Times New Roman", Font.BOLD, 15));
                jTextFieldDate[0].setPreferredSize(new Dimension(1000, 50));
                jTextFieldDate[0].setAutoscrolls(true);

                jDateChooser[0] = new JDateChooser();
                jDateChooser[0].setDateFormatString("dd/MM/yyyy");
                jDateChooser[0].setPreferredSize(new Dimension(1000, 50));
                jDateChooser[0].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

                dateTextField[0] = (JTextField) jDateChooser[0].getDateEditor().getUiComponent();
                dateTextField[0].setFont(new Font("Lexend", Font.BOLD, 14));

                center.add(jDateChooser[0], "wrap");
                continue;
            }
            if (string.equals("Đến ngày")) {
                jTextFieldDate[1] = new JTextField();
                jTextFieldDate[1].setFont(new Font("Times New Roman", Font.BOLD, 15));
                jTextFieldDate[1].setPreferredSize(new Dimension(1000, 50));
                jTextFieldDate[1].setAutoscrolls(true);

                jDateChooser[1] = new JDateChooser();
                jDateChooser[1].setDateFormatString("dd/MM/yyyy");
                jDateChooser[1].setPreferredSize(new Dimension(1000, 50));
                jDateChooser[1].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

                dateTextField[1] = (JTextField) jDateChooser[1].getDateEditor().getUiComponent();
                dateTextField[1].setFont(new Font("Lexend", Font.BOLD, 14));

                center.add(jDateChooser[1], "wrap");
                continue;
            }
            if (string.equals("Lý do")) {
                jTextArea.setBackground(Color.white);
                jTextArea.setPreferredSize(new Dimension(1000, 200));
                center.add(jTextArea, "wrap");
            }
        }

        buttonCreate.setPreferredSize(new Dimension(100, 30));
        buttonCreate.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCreate.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addLeave_Of_Absence_form();
            }
        });
        bot.add(buttonCreate);
    }

    private void addLeave_Of_Absence_form() {
        Pair<Boolean, String> result;

        int id, staff_id;
        Date date, start_date, end_date;
        String reason;

        id = leaveOfAbsenceFormBLL.getAutoID(leaveOfAbsenceFormBLL.searchLeave_Of_Absence_Forms());
        staff_id = staff.getId();

        if (jDateChooser[0].getDateEditor().getDate() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (jDateChooser[1].getDateEditor().getDate() == null) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày kết thúc.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        date = java.sql.Date.valueOf(LocalDate.now());
        start_date = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser[0].getDate()));
        end_date = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser[1].getDate()));
        reason = jTextArea.getText();

        Leave_Of_Absence_Form leaveOfAbsenceForm = new Leave_Of_Absence_Form(id, staff_id, date, start_date, end_date, reason, 0);

        result = leaveOfAbsenceFormBLL.addLeave_Of_Absence_Form(leaveOfAbsenceForm);

        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() {
        String[] options = new String[]{"Huỷ", "Thoát"};
        int choice = JOptionPane.showOptionDialog(null, "Bạn có muốn thoát?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
        if (choice == 1)
            dispose();
    }
}
