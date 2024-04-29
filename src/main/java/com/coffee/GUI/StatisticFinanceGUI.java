package com.coffee.GUI;

import com.coffee.DAL.MySQL;
import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.line_chart.ModelData;
import com.coffee.GUI.components.line_chart.chart.CurveLineChart;
import com.coffee.GUI.components.line_chart.chart.ModelLineChart;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticFinanceGUI extends JPanel {
    private JPanel content;
    private JScrollPane scrollPane;
    private int concern = 0;
    private int displayType = 0;

    public StatisticFinanceGUI() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1000, 700));
        setLayout(new BorderLayout());
        init();
        setVisible(true);
    }

    private void init() {
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(270, 700));
        add(scrollPane, BorderLayout.EAST);
        initRightBar();

        content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.LEFT));
        content.setPreferredSize(new Dimension(730, 700));
        content.setBackground(new Color(238, 238, 238));
        add(content, BorderLayout.CENTER);

        byMonthChart();
    }

    private void initRightBar() {
        JPanel jPanel = new JPanel(new MigLayout("", "5[]5", "10[]10"));
        jPanel.setBackground(new Color(238, 238, 238));
        scrollPane.setViewportView(jPanel);

        RoundedPanel displayTypePanel = new RoundedPanel();
        displayTypePanel.setBackground(new Color(255, 255, 255));
        displayTypePanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        displayTypePanel.setPreferredSize(new Dimension(247, 100));
        jPanel.add(displayTypePanel, "wrap");

        ButtonGroup btnGroupDisplayType = new ButtonGroup();
        JLabel labelDisplayType = new JLabel("Chọn hiển thị");
        labelDisplayType.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(labelDisplayType, "wrap");

        JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
        chartRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayType = 0;
                loadData();
            }
        });
        chartRadioButton.setSelected(true);
        btnGroupDisplayType.add(chartRadioButton);
        displayTypePanel.add(chartRadioButton, "wrap");

        JRadioButton reportRadioButton = createRadioButton("Báo cáo");
        reportRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayType = 1;
                loadData();
            }
        });
        btnGroupDisplayType.add(reportRadioButton);
        displayTypePanel.add(reportRadioButton, "wrap");

        RoundedPanel timePanel = new RoundedPanel();
        timePanel.setBackground(new Color(255, 255, 255));
        timePanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        timePanel.setPreferredSize(new Dimension(247, 120));
        jPanel.add(timePanel, "wrap");

        JLabel labelTime = new JLabel("Thời gian");
        labelTime.setFont(new Font("Inter", Font.BOLD, 14));
        timePanel.add(labelTime, "wrap");

        JRadioButton radio1 = createRadioButton("Theo Tháng");
        if (concern == 0)
            radio1.setSelected(true);
        radio1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 0;
                initRightBar();
                loadData();
            }
        });

        JRadioButton radio2 = createRadioButton("Theo Quý");
        if (concern == 1)
            radio2.setSelected(true);
        radio2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 1;
                initRightBar();
                loadData();
            }
        });

        JRadioButton radio3 = createRadioButton("Theo Năm");
        if (concern == 2)
            radio3.setSelected(true);
        radio3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                concern = 2;
                initRightBar();
                loadData();
            }
        });

        ButtonGroup btnGroupTime = new ButtonGroup();
        btnGroupTime.add(radio1);
        btnGroupTime.add(radio2);
        btnGroupTime.add(radio3);

        timePanel.add(radio1, "wrap");
        timePanel.add(radio2, "wrap");
        timePanel.add(radio3, "wrap");
    }

    private void loadData() {
        if (concern == 0) {
            if (displayType == 0)
                byMonthChart();
            if (displayType == 1)
                byMonthReport();
        }
        if (concern == 1) {
            if (displayType == 0)
                byQuarterChart();
            if (displayType == 1)
                byQuarterReport();
        }
        if (concern == 2) {
            if (displayType == 0)
                byYearChart();
            if (displayType == 1)
                byYearReport();
        }
    }

    private void byYearReport() {
    }

    private void byYearChart() {
    }

    private void byQuarterReport() {
    }

    private void byQuarterChart() {
    }

    private void byMonthReport() {
    }

    private void byMonthChart() {
        List<List<String>> dataSale_Discount = MySQL.getSale_DiscountByMonth();
        List<List<String>> dataCapitalPrice = MySQL.getCapitalPriceByMonth();
        List<List<String>> dataSalary_Allowance_Bonus_Deduction_Fine = MySQL.getSalary_Allowance_Bonus_Deduction_FineByMonth();

        List<Integer> monthList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Duyệt qua các tháng từ tháng 1 đến tháng hiện tại
        for (Month month : Month.values()) {
            // Kiểm tra xem tháng đó đã qua hay chưa
            if (month.compareTo(today.getMonth()) <= 0) {
                monthList.add(month.getValue());
            }
        }

        List<List<String>> data = new ArrayList<>();

        for (Integer month : monthList) {
            List<String> list = new ArrayList<>();
            list.add(month.toString());

            boolean check = false;
            for (List<String> strings : dataSale_Discount) {
                if (strings.get(0).equals(month.toString())) {
                    list.add(strings.get(1));
                    list.add(strings.get(2));
                    check = true;
                    break;
                }
            }
            if (!check) {
                list.add("0");
                list.add("0");
            }

            check = false;
            for (List<String> strings : dataCapitalPrice) {
                if (strings.get(0).equals(month.toString())) {
                    list.add(strings.get(1));
                    check = true;
                    break;
                }
            }
            if (!check) {
                list.add("0");
            }

            check = false;
            for (List<String> strings : dataSalary_Allowance_Bonus_Deduction_Fine) {
                if (strings.get(1).equals(month.toString())) {
                    list.add(strings.get(2));
                    list.add(strings.get(3));
                    list.add(strings.get(4));
                    list.add(strings.get(5));
                    list.add(strings.get(6));
                    check = true;
                    break;
                }
            }
            if (!check) {
                list.add("0");
                list.add("0");
                list.add("0");
                list.add("0");
                list.add("0");
            }

            data.add(list);
        }

        content.removeAll();

        JLabel jLabelTile1 = new JLabel("Lợi nhuận theo tháng");
        jLabelTile1.setPreferredSize(new Dimension(890, 30));
        jLabelTile1.setFont(new Font("Inter", Font.BOLD, 15));
        jLabelTile1.setVerticalAlignment(JLabel.CENTER);
        jLabelTile1.setHorizontalAlignment(JLabel.CENTER);
        content.add(jLabelTile1, "wrap");

        CurveLineChart chart = new CurveLineChart();
//        chart.setTitle("Lợi nhuận theo tháng");
        chart.addLegend("Lợi nhuận thuần", Color.decode("#7b4397"), Color.decode("#dc2430"));
        chart.addLegend("Lợi nhuận từ hoạt động kinh doanh (Lợi nhuận bán hàng - Chi phí nhân viên)", Color.decode("#e65c00"), Color.decode("#F9D423"));
        chart.addLegend("Thu nhập khác", Color.decode("#0099F7"), Color.decode("#F11712"));
//        chart.addLegend("Thu nhập khác", Color.decode("#0099F5"), Color.decode("#F11710"));
        chart.setForeground(new Color(0x919191));
        chart.setFillColor(true);

        JPanel panelShadow = new JPanel();
        panelShadow.setPreferredSize(new Dimension(890, 650));
        panelShadow.setBackground(new Color(255, 255, 255));
        panelShadow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelShadow.setLayout(new BorderLayout());
        panelShadow.add(chart, BorderLayout.CENTER);
        content.add(panelShadow, "wrap");

        List<ModelData> lists = new ArrayList<>();
        for (List<String> list : data) {
            double sale = Double.parseDouble(list.get(1)) - Double.parseDouble(list.get(2)) - Double.parseDouble(list.get(3));
            double excess = Double.parseDouble(list.get(4)) + Double.parseDouble(list.get(5)) + Double.parseDouble(list.get(6));
            double other = Double.parseDouble(list.get(7)) + Double.parseDouble(list.get(8));
            double profit = sale - excess + other;
            lists.add(new ModelData(list.get(0) + "/" + LocalDate.now().getYear(), profit, sale - excess, other));

        }

        for (ModelData d : lists) {
            chart.addData(new ModelLineChart(d.getMonth(), new double[]{d.getDeposit(), d.getWithdrawal(), d.getTransfer()}));
        }
        chart.start();

        content.repaint();
        content.revalidate();
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Inter", Font.PLAIN, 14));
        return radioButton;
    }
}
