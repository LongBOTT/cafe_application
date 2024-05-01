package com.coffee.GUI;

import com.coffee.DAL.MySQL;
import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.RoundedPanel;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StatisticSaleGUI extends JPanel {
    private JScrollPane scrollPane;
    private DatePicker datePicker;
    private JFormattedTextField editor;
    private JPanel content;
    private RoundedPanel displayTypePanel;

    private JLabel dateLabel;
    private JLabel titleLabel;
    private JScrollPane scrollPaneDetail;
    private JPanel centerPanel;
    private static final int PANEL_WIDTH = 885;
    private static final int PANEL_HEIGHT = 40;
    private static final Dimension LABEL_SIZE = new Dimension(150, 30);
    Map<JPanel, Boolean> expandedStateMap = new HashMap<>(); // Biến để theo dõi trạng thái của nút btnDetail

    public StatisticSaleGUI() {
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
        content.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
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
        centerPanel = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        centerPanel.setBackground(new Color(255, 255, 255));
        scrollPaneDetail = new JScrollPane();
        scrollPaneDetail.setPreferredSize(new Dimension(PANEL_WIDTH, 580));
        scrollPaneDetail.setViewportView(centerPanel);
        scrollPaneDetail.setBorder(null);

        content.add(scrollPaneDetail);
        // mặc đinh sẽ hiển thị báo cáo cuối ngày về bán hàng
//        setUpSalesReports();

    }

    private JPanel createLabelPanel(Color background, LayoutManager layoutManager) {
        JPanel panel = new JPanel();
        panel.setLayout(layoutManager);
        panel.setPreferredSize(new Dimension(868, PANEL_HEIGHT));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(202, 202, 202)));
        return panel;
    }

    private void addLabelsToPanel(JPanel panel, String[] labels, int customRight, Font font) {
        int i = 0;
        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(font);
            jLabel.setPreferredSize(LABEL_SIZE);
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            if (i == 0) jLabel.setHorizontalAlignment(SwingConstants.LEFT);
            if (i == customRight) jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            panel.add(jLabel);
            i++;
        }
    }


    private void setUpProduct() {
        centerPanel.removeAll();
        JPanel salesLabelPanel = createLabelPanel(new Color(178, 232, 255), new MigLayout("", "10[]20[]20[]20[]20[]10", ""));
        addLabelsToPanel(salesLabelPanel, new String[]{"Nhóm hàng", "Số lượng bán", "", "", "Doanh thu"}, 4, new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesLabelPanel, "wrap");

        String start = "2024-01-01";
        String end = "2024-12-31";
        List<List<String>> saleCategories = MySQL.getSaleCategory(start, end);


        JPanel salesDataPanel = createLabelPanel(new Color(242, 238, 214), new MigLayout("", "10[]20[]20[]20[]20[]20[]10", ""));

        long quantity_Category = saleCategories.size();
        double quantity_Product = 0;
        BigDecimal revenue = BigDecimal.ZERO;

        for (List<String> saleCategory : saleCategories) {
            quantity_Product += Double.parseDouble(saleCategory.get(1));
            revenue = revenue.add(new BigDecimal(saleCategory.get(2)));
        }

        String formattedNumberQuantity = NumberFormat.getNumberInstance(Locale.US).format(quantity_Product);
        String formattedNumberRevenue = NumberFormat.getNumberInstance(Locale.US).format(revenue);
        addLabelsToPanel(salesDataPanel, new String[]{"Sl nhóm hàng: "+ quantity_Category,formattedNumberQuantity, "","", formattedNumberRevenue, }, 4, new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesDataPanel, "wrap");

        for(List<String> saleCategory : saleCategories) {
            JPanel salesDataPanel1 = createLabelPanel(new Color(255, 255, 255), new MigLayout("", "10[]20[]20[]20[]20[]10", ""));
            double saleProductNumber = Double.parseDouble(saleCategory.get(1));
            String formattedNumber = NumberFormat.getNumberInstance(Locale.US).format(saleProductNumber);
            double revenueDetail = Double.parseDouble(saleCategory.get(2));
            String formattedNumber1 = NumberFormat.getNumberInstance(Locale.US).format(revenueDetail);
            addLabelsToPanel(salesDataPanel1, new String[]{saleCategory.get(0), formattedNumber, "", "", formattedNumber1}, 4, new Font("Inter", Font.PLAIN, 13));
            centerPanel.add(salesDataPanel1, "wrap");
        }
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

        RoundedPanel concernsPanel = createRoundedPanel(new MigLayout("", "10[]10", "10[]10"), 515, 70);
        concernsPanel.setBackground(new Color(255, 255, 255));
        concernsPanel.setPreferredSize(new Dimension(247, 220));
        jPanel.add(concernsPanel, "wrap");

        JLabel label = new JLabel("Mối quan tâm");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        concernsPanel.add(label, "wrap");

        JRadioButton timeRadioButton = createRadioButton("Thời gian");
        JRadioButton profitRadioButton = createRadioButton("Lợi nhuận");
        JRadioButton discountRadioButton = createRadioButton("Giảm giá HĐ");
        JRadioButton categoryRadioButton = createRadioButton("Thể loại");

        ButtonGroup btnGroupConcerns = new ButtonGroup();
        btnGroupConcerns.add(timeRadioButton);
        btnGroupConcerns.add(profitRadioButton);
        btnGroupConcerns.add(discountRadioButton);
        btnGroupConcerns.add(categoryRadioButton);

        concernsPanel.add(timeRadioButton, "wrap");
        concernsPanel.add(profitRadioButton, "wrap");
        concernsPanel.add(discountRadioButton, "wrap");
        concernsPanel.add(categoryRadioButton, "wrap");


        concernsPanel.repaint();
        concernsPanel.revalidate();

        // mặc định khi chọn vào tab bán hàng sẽ chọn tiêu chí thời gian đầu tiên là mặc định
        timeRadioButton.setSelected(true);
        updateDisplayType("Báo cáo và biểu đồ");

        timeRadioButton.addActionListener(e -> {
            // Kiểm tra nếu thời gian được chọn
            if (timeRadioButton.isSelected()) {
                updateDisplayType("Báo cáo và biểu đồ");
            }
        });

        profitRadioButton.addActionListener(e -> {
            // Kiểm tra nếu lọi nhuận được chọn
            if (profitRadioButton.isSelected()) {
                updateDisplayType("Báo cáo và biểu đồ");
            }
        });

        discountRadioButton.addActionListener(e -> {
            // Kiểm tra nếu giảm HD được chọn
            if (discountRadioButton.isSelected()) {
                updateDisplayType("Báo cáo");
            }
        });

        categoryRadioButton.addActionListener(e -> {
            // Kiểm tra nếu thể loại được chọn
            if (categoryRadioButton.isSelected()) {
                updateDisplayType("Báo cáo");
                setUpProduct();
            }
        });

        RoundedPanel timePanel = new RoundedPanel();
        timePanel.setBackground(new Color(255, 255, 255));
        timePanel.setLayout(new MigLayout("", "10[]10", "10[]10"));
        timePanel.setPreferredSize(new Dimension(247, 50));
        jPanel.add(timePanel, "wrap");
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
        datePicker.setSelectedDateRange(java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(LocalDate.now())); // bao loi o day
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

    private void setupDisplayTypeReportAndChart() {
        JLabel label = new JLabel("Kiểu hiển thị");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(label, "wrap");

        JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
        JRadioButton reportRadioButton = createRadioButton("Báo cáo");

        ButtonGroup btnGroupDisplayType = new ButtonGroup();
        btnGroupDisplayType.add(chartRadioButton);
        btnGroupDisplayType.add(reportRadioButton);

        reportRadioButton.setSelected(true);
        displayTypePanel.add(chartRadioButton, "wrap");
        displayTypePanel.add(reportRadioButton, "wrap");
    }

    private void setupDisplayTypeReport() {
        JLabel label = new JLabel("Kiểu hiển thị");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(label, "wrap");

        JRadioButton reportRadioButton = createRadioButton("Báo cáo");
        reportRadioButton.setSelected(true);
        displayTypePanel.add(reportRadioButton);
    }

    private void updateDisplayType(String displayType) {
        displayTypePanel.removeAll();
        if (displayType.equals("Báo cáo và biểu đồ"))
            setupDisplayTypeReportAndChart();
        else
            setupDisplayTypeReport();

        displayTypePanel.repaint();
        displayTypePanel.revalidate();

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
