package com.coffee.GUI;

import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.RoundedPanel;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StatisticSaleGUI extends JPanel {
    private JScrollPane scrollPane;
    private DatePicker datePicker;
    private JFormattedTextField editor;
    private JPanel content;
    private RoundedPanel displayTypePanel;

    private JLabel dateLabel;
    private JLabel titleLabel;

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
        content.setLayout(new FlowLayout(FlowLayout.LEFT));
        content.setPreferredSize(new Dimension(730, 700));
        content.setBackground(new Color(238, 238, 238));
        add(content, BorderLayout.CENTER);
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
