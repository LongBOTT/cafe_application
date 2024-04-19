package com.coffee.GUI.DialogGUI.FormDetailGUI;


import com.coffee.BLL.StaffBLL;
import com.coffee.DTO.Leave_Of_Absence_Form;
import com.coffee.GUI.components.MyTextFieldUnderLine;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.swing.MyTextField;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailLeave_Of_Absence_FormGUI extends JDialog {
    private List<JLabel> attributeLeaveOfAbsenceForm;
    private JTextField[] jTextFieldDate;
    private JTextField[] dateTextField;
    private JDateChooser[] jDateChooser;
    private JTextArea jTextArea;
    private Leave_Of_Absence_Form leaveOfAbsenceForm;

    public DetailLeave_Of_Absence_FormGUI(Leave_Of_Absence_Form leaveOfAbsenceForm) {
        super((Frame) null, "", true);
        this.leaveOfAbsenceForm = leaveOfAbsenceForm;
        getContentPane().setBackground(new Color(242, 245, 250));
        setTitle("Thông tin đơn nghỉ phép");
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
        jDateChooser = new JDateChooser[3];
        jTextFieldDate = new JTextField[3];
        dateTextField = new JTextField[3];
        jTextArea = new JTextArea();

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

        JLabel jLabelTitle = new JLabel("Thông Tin Đơn Nghỉ Phép");
        jLabelTitle.setHorizontalAlignment(JLabel.CENTER);
        jLabelTitle.setFont(new Font("Lexend", Font.BOLD, 18));
        roundedPanelTitle.add(jLabelTitle);

        for (String string : new String[]{"Họ tên", "Ngày tạo", "Từ ngày", "Đến ngày", "Lý do"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeLeaveOfAbsenceForm.add(label);
            center.add(label);

            JTextField textField = new MyTextFieldUnderLine();
            if (string.equals("Họ tên")) {
                textField.setText(new StaffBLL().findStaffsBy(Map.of("id", leaveOfAbsenceForm.getStaff_id())).get(0).getName());
                textField.setPreferredSize(new Dimension(1000, 50));
                textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                textField.setBackground(Color.white);

            }
            if (string.equals("Ngày tạo")) {
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

                jDateChooser[0].setDate(leaveOfAbsenceForm.getDate());
                jDateChooser[0].setEnabled(false);

                center.add(jDateChooser[0], "wrap");
                continue;
            }
            if (string.equals("Từ ngày")) {
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

                jDateChooser[1].setDate(leaveOfAbsenceForm.getStart_date());
                jDateChooser[1].setEnabled(false);

                center.add(jDateChooser[1], "wrap");
                continue;
            }
            if (string.equals("Đến ngày")) {
                jTextFieldDate[2] = new JTextField();
                jTextFieldDate[2].setFont(new Font("Times New Roman", Font.BOLD, 15));
                jTextFieldDate[2].setPreferredSize(new Dimension(1000, 50));
                jTextFieldDate[2].setAutoscrolls(true);

                jDateChooser[2] = new JDateChooser();
                jDateChooser[2].setDateFormatString("dd/MM/yyyy");
                jDateChooser[2].setPreferredSize(new Dimension(1000, 50));
                jDateChooser[2].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

                dateTextField[2] = (JTextField) jDateChooser[2].getDateEditor().getUiComponent();
                dateTextField[2].setFont(new Font("Lexend", Font.BOLD, 14));

                jDateChooser[2].setDate(leaveOfAbsenceForm.getEnd_date());
                jDateChooser[2].setEnabled(false);

                center.add(jDateChooser[2], "wrap");
                continue;
            }
            if (string.equals("Lý do")) {
                jTextArea.setBackground(new Color(255, 255, 255));
                jTextArea.setPreferredSize(new Dimension(1000, 200));
                jTextArea.setText(leaveOfAbsenceForm.getReason());
                jTextArea.setEditable(false);
                center.add(jTextArea, "wrap");
                continue;
            }
            textField.setEditable(false);
            center.add(textField, "wrap");
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
