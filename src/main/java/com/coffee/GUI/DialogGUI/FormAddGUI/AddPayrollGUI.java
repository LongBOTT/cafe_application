package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.PayrollBLL;
import com.coffee.DTO.Payroll;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.main.Cafe_Application;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddPayrollGUI extends DialogForm {
    private JLabel titleName;
    private JMonthChooser jMonthChooser;
    private JYearChooser jYearChooser;
    private List<JLabel> attributePayroll;
    private JButton buttonCancel;
    private JButton buttonAdd;
    private PayrollBLL payrollBLL = new PayrollBLL();

    public AddPayrollGUI() {
        super();
        super.setTitle("Thêm bảng lương");
        super.setSize(new Dimension(600, 250));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        attributePayroll = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "20[]20[]20"));

        titleName.setText("Thêm bảng lương");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        content.setBackground(new Color(217, 217, 217));

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(170, 30));
        label.setText("Kỳ làm việc");
        label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
        attributePayroll.add(label);
        content.add(label);

        jMonthChooser = new JMonthChooser();
        jMonthChooser.setPreferredSize(new Dimension(1000, 30));
        jMonthChooser.setBackground(new Color(245, 246, 250));
        jMonthChooser.setMonth(LocalDate.now().getMonth().getValue() - 1);
        content.add(jMonthChooser);

        jYearChooser = new JYearChooser();
        jYearChooser.setPreferredSize(new Dimension(1000, 30));
        jYearChooser.setBackground(new Color(245, 246, 250));
        jYearChooser.setYear(LocalDate.now().getYear());
        content.add(jYearChooser);

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
                addPayroll();
            }
        });
        containerButton.add(buttonAdd);
    }

    private void addPayroll() {
        Pair<Boolean, String> result;
        int id, month, year;
        String name;
        Date entry_date;
        double total_salary, paid, debt;

        month = jMonthChooser.getMonth() + 1;
        year = jYearChooser.getYear();
        LocalDate start = YearMonth.of(year, month).atDay(1);
        LocalDate end = YearMonth.of(year, month).atEndOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

        id = payrollBLL.getAutoID(payrollBLL.searchPayrolls()); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
        name = "Bảng lương " + formatter.format(start) + " - " + formatter.format(end);
        entry_date = java.sql.Date.valueOf(LocalDate.now());
        total_salary = 0;
        paid = 0;
        debt = 0;
        Payroll payroll = new Payroll(id, name, entry_date, month, year, total_salary, paid, debt); // false là tồn tại, true là đã xoá

        result = payrollBLL.addPayroll(payroll);

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
