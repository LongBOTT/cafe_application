package com.coffee.GUI;

import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.RoundedPanel;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatisticEndOfTheDayGUI extends JPanel {
    private JScrollPane scrollPane;
    private DatePicker datePicker;
    private JFormattedTextField editor;
    private JPanel content;
    private RoundedPanel displayTypePanel;
    private JLabel dateLabel;
    private JLabel titleLabel;

    private JPanel centerPanel;

    public StatisticEndOfTheDayGUI() {
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
        initRightBartEndOfDayPanel();

        content = new JPanel();
        content.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        content.setPreferredSize(new Dimension(730, 700));
        content.setBackground(new Color(255, 255, 255));
        add(content, BorderLayout.CENTER);
        initTopContent();
        initCenterContent();

    }

    private void initTopContent() {
        JPanel titletTopPanel = new JPanel(new BorderLayout());
        titletTopPanel.setBackground(Color.WHITE);

        titletTopPanel.setPreferredSize(new Dimension(885, 100));
        content.add(titletTopPanel);
        // Cập nhật ngày lập
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        dateLabel = new JLabel("Ngày lập: " + formattedDateTime);
        dateLabel.setFont(new Font("Inter", Font.PLAIN, 14));

        // Cập nhật ngày bán
        LocalDateTime currentDate = LocalDateTime.now().toLocalDate().atStartOfDay();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        JLabel dateReportLabel = new JLabel("Ngày bán: " + formattedDate);
        dateReportLabel.setFont(new Font("Inter", Font.PLAIN, 14));

        // mặc đinh tiêu đề là báo cáo cuối ngày về bán hàng
        titleLabel = new JLabel("Báo cáo cuối ngày về bán hàng");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 18));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setBackground(Color.WHITE);
        datePanel.add(dateLabel);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(titleLabel);

        JPanel dateReportPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dateReportPanel.setBackground(Color.WHITE);
        dateReportPanel.add(dateReportLabel);

        titletTopPanel.add(datePanel, BorderLayout.NORTH);
        titletTopPanel.add(titlePanel, BorderLayout.CENTER);
        titletTopPanel.add(dateReportPanel, BorderLayout.SOUTH);

    }

    private void initCenterContent() {
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setPreferredSize(new Dimension(885, 580));
        content.add(centerPanel);
        centerPanel.setBackground(Color.WHITE);

        setUpLabelSalesReports();

    }

    private void setUpLabelSalesReports() {
        centerPanel.removeAll();

        JPanel salesLabelPanel = new JPanel(); // panel chứa các nhãn
        salesLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 10));
        salesLabelPanel.setPreferredSize(new Dimension(885, 40));
        salesLabelPanel.setBackground(new Color(178, 232, 255));
        salesLabelPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(202, 202, 202))); // tạo viền cho panel

        String[] labels = {"Mã hóa đơn", "Nhân viên tạo", "Thời gian", "SLSP", "Doanh thu"};

        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(new Font("Inter", Font.BOLD, 13));
            salesLabelPanel.add(jLabel);
        }

        centerPanel.add(salesLabelPanel);

        //
        JPanel salesLabelPanel1 = new JPanel(); // panel chứa kết quả
        salesLabelPanel1.setLayout(new MigLayout("", "90[]580[]90", "10[]10"));
        salesLabelPanel1.setPreferredSize(new Dimension(885, 40));
        salesLabelPanel1.setBackground(new Color(242, 238, 214));
        salesLabelPanel1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(202, 202, 202))); // tạo viền cho panel

        JLabel quantityBill = new JLabel();
        quantityBill.setText("Hóa đơn: 10");
        quantityBill.setFont(new Font("Inter", Font.PLAIN, 13));
        salesLabelPanel1.add(quantityBill);

        JLabel revenue = new JLabel();
        revenue.setText("1000000");
        revenue.setFont(new Font("Inter", Font.PLAIN, 13));
        salesLabelPanel1.add(revenue);

        centerPanel.add(salesLabelPanel1);


        centerPanel.repaint();
        centerPanel.revalidate();
    }


    private void initRightBartEndOfDayPanel() {
        JPanel jPanel = new JPanel(new MigLayout("", "5[]5", "10[]10"));
        jPanel.setBorder(null);
        jPanel.setBackground(new Color(238, 238, 238));
        scrollPane.setViewportView(jPanel);

        displayTypePanel = createRoundedPanel(new MigLayout("", "10[]10", "10[]10"), 300, 70);
        displayTypePanel.setBackground(new Color(255, 255, 255));
        displayTypePanel.setPreferredSize(new Dimension(247, 120));
        jPanel.add(displayTypePanel, "wrap");

        JLabel labelDisplay = new JLabel("Kiểu hiển thị");
        labelDisplay.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(labelDisplay, "wrap");

        JRadioButton reportRadioButton = createRadioButton("Báo cáo");
        reportRadioButton.setSelected(true);
        displayTypePanel.add(reportRadioButton);

        RoundedPanel concernsPanel = createRoundedPanel(new MigLayout("", "10[]10", "10[]10"), 515, 70);
        concernsPanel.setBackground(new Color(255, 255, 255));
        concernsPanel.setPreferredSize(new Dimension(247, 220));
        jPanel.add(concernsPanel, "wrap");

        JLabel label = new JLabel("Mối quan tâm");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        concernsPanel.add(label, "wrap");

        JRadioButton sellRadioButton = createRadioButton("Bán hàng");
        JRadioButton revenueAndExpenditureRadioButton = createRadioButton("Thu chi");
        JRadioButton productRadioButton = createRadioButton("Hàng hóa");
        JRadioButton syntheticRadioButton = createRadioButton("Tổng hợp");

        ButtonGroup btnGroupConcerns = new ButtonGroup();
        btnGroupConcerns.add(sellRadioButton);
        btnGroupConcerns.add(revenueAndExpenditureRadioButton);
        btnGroupConcerns.add(productRadioButton);
        btnGroupConcerns.add(syntheticRadioButton);

        concernsPanel.add(sellRadioButton, "wrap");
        concernsPanel.add(revenueAndExpenditureRadioButton, "wrap");
        concernsPanel.add(productRadioButton, "wrap");
        concernsPanel.add(syntheticRadioButton, "wrap");

        // mặc định khi chọn vào tab bán hàng sẽ chọn tiêu chí bán hàng đầu tiên là mặc định
        sellRadioButton.setSelected(true);


        sellRadioButton.addActionListener(e -> {
            // Kiểm tra nếu bán hàng  được chọn
            if (sellRadioButton.isSelected()) {
                titleLabel.setText("Báo cáo cuối ngày về bán hàng");
            }
        });
        revenueAndExpenditureRadioButton.addActionListener(e -> {
            // Kiểm tra nếu thu chi được chọn
            if (revenueAndExpenditureRadioButton.isSelected()) {
                titleLabel.setText("Báo cáo cuối ngày về thu chi");
            }
        });
        productRadioButton.addActionListener(e -> {
            // Kiểm tra nếu hàng hóa được chọn
            if (productRadioButton.isSelected()) {
                titleLabel.setText("Báo cáo cuối ngày về hàng hóa");
            }
        });

        syntheticRadioButton.addActionListener(e -> {
            // Kiểm tra nếu tổng hợp được chọn
            if (syntheticRadioButton.isSelected()) {
                titleLabel.setText("Báo cáo cuối ngày tổng hợp");
            }
        });

        RoundedPanel timePanel = new RoundedPanel();

        timePanel.setBackground(new Color(255, 255, 255));
        timePanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        timePanel.setPreferredSize(new Dimension(247, 50));
        jPanel.add(timePanel, "wrap");

        JLabel labelTime = new JLabel("Thời gian");
        labelTime.setFont(new Font("Inter", Font.BOLD, 14));

        datePicker = new DatePicker();
        editor = new JFormattedTextField();

        datePicker.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.setEditor(editor);
        datePicker.setCloseAfterSelected(true);
        datePicker.setSelectedDateRange(LocalDate.now(), LocalDate.now()); // bao loi o day
        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {

            }
        });

        editor.setPreferredSize(new Dimension(240, 30));
        editor.setFont(new Font("Inter", Font.BOLD, 13));

        timePanel.add(labelTime, "wrap");
        timePanel.add(editor);

    }


    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Inter", Font.PLAIN, 14));
        return radioButton;
    }

    private RoundedPanel createRoundedPanel(LayoutManager layoutManager, int width, int height) {
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(layoutManager);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
}
