package com.coffee.GUI;

import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.RoundedPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StatisticEndOfTheDayGUI extends JPanel {
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
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 580));
        content.add(centerPanel);
        centerPanel.setBackground(Color.WHITE);

        setUpSalesReports();
    }

    private void setUpSalesReports() {
        centerPanel.removeAll();

        JPanel salesLabelPanel = createLabelPanel(new Color(178, 232, 255), new Color(202, 202, 202));
        addLabelsToPanel(salesLabelPanel, new String[]{"Mã hóa đơn", "Thời gian", "Nhân viên tạo", "SLSP", "Doanh thu"});
        centerPanel.add(salesLabelPanel);

        JPanel salesDataPanel = createLabelPanel(new Color(242, 238, 214), new Color(202, 202, 202));
        addLabelsToPanel(salesDataPanel, new String[]{"Hóa đơn: 10", "", "", "10", "10,000"});
        centerPanel.add(salesDataPanel);

        String[][] reportsData = {
                {"12-12-2021", "12:00", "10", "20,000"},
                {"13-12-2021", "13:00", "20", "20,000"},
                {"14-12-2021", "14:00", "30", "20,000"},
                {"15-12-2021", "15:00", "40", "20,000"},
                {"16-12-2021", "16:00", "50", "20,000"},
                // Thêm các dữ liệu khác nếu cần
        };

        // Tạo panel báo cáo cho mỗi dòng trong mảng dữ liệu
        for (String[] data : reportsData) {
            JPanel reportPanel = createPanelReport(data[0], data[1], data[2], data[3]);
            expandedStateMap.put(reportPanel, false); // Ban đầu, tất cả các panel đều không mở rộng
            centerPanel.add(reportPanel);
        }

        centerPanel.repaint();
        centerPanel.revalidate();

    }

    private JPanel createLabelPanel(Color background, Color border) {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("", "10[]30[]30[]30[]30[]10", ""));
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, border));
        return panel;
    }

    private void addLabelsToPanel(JPanel panel, String[] labels) {
        int i = 0;
        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(new Font("Inter", Font.BOLD, 13));
            jLabel.setPreferredSize(LABEL_SIZE);
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            if (i==0)
                jLabel.setHorizontalAlignment(SwingConstants.LEFT);
            if (i==4)
                jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            panel.add(jLabel);
            i++;
        }
    }

    private JPanel createPanelReport(String date, String hour, String quantity, String revenue) {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("", "10[]30[]30[]30[]30[]10", ""));
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, 50));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(202, 202, 202)));

        JPanel containerBtn = new JPanel();
        containerBtn.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        containerBtn.setPreferredSize(new Dimension(150, 30));
        containerBtn.setBackground(Color.WHITE);

        JButton btnDetail = new JButton();
        btnDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDetail.setIcon(new FlatSVGIcon("icon/add-square-svgrepo-com.svg")); // Đặt biểu tượng ban đầu
        btnDetail.setBorderPainted(false);
        btnDetail.setFocusPainted(false);
        btnDetail.setContentAreaFilled(false);
        btnDetail.addActionListener(e -> {
            JPanel selectedPanel = (JPanel) btnDetail.getParent().getParent(); // Lấy panel cha của nút
            togglePanel(btnDetail, selectedPanel);// Kích hoạt chức năng mở rộng/ thu gọn khi nhấn nút
        });

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setLayout(new BorderLayout());

        JLabel lblDate = new JLabel(date);
        lblDate.setFont(new Font("Inter", Font.PLAIN, 13));
        lblDate.setForeground(new Color(33, 160, 223));

        JLabel lblHour = new JLabel(hour);
        lblHour.setFont(new Font("Inter", Font.PLAIN, 13));
        lblHour.setForeground(new Color(33, 160, 223));

        panel1.add(lblHour, BorderLayout.SOUTH);
        panel1.add(lblDate, BorderLayout.NORTH);

        containerBtn.add(btnDetail);
        containerBtn.add(panel1);

        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(150, 50));

        JLabel lbl1 = new JLabel();
        lbl1.setPreferredSize(new Dimension(150, 50));

        JLabel lblQuantity = new JLabel(quantity);
        lblQuantity.setFont(new Font("Inter", Font.PLAIN, 13));
        lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuantity.setPreferredSize(new Dimension(150, 50));

        JLabel lblRevenue = new JLabel(revenue);
        lblRevenue.setFont(new Font("Inter", Font.PLAIN, 13));
        lblRevenue.setPreferredSize(new Dimension(150, 50));
        lblRevenue.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(containerBtn);
        panel.add(lbl);
        panel.add(lbl1);
        panel.add(lblQuantity);
        panel.add(lblRevenue);

       return panel;
    }


    private void togglePanel(JButton btnDetail, JPanel selectedPanel) {
        boolean isExpanded = expandedStateMap.get(selectedPanel); // Lấy trạng thái mở rộng của panel được chọn
        if (isExpanded) {
            removeDetailPanels(selectedPanel);
            btnDetail.setIcon(new FlatSVGIcon("icon/add-square-svgrepo-com.svg"));
        } else {
            addDetailPanel(selectedPanel);
            btnDetail.setIcon(new FlatSVGIcon("icon/minus-square-svgrepo-com.svg"));
        }
        expandedStateMap.put(selectedPanel, !isExpanded); // Cập nhật trạng thái mở rộng
    }

    private void removeDetailPanels(JPanel selectedPanel) {
        int selectedIndex = centerPanel.getComponentZOrder(selectedPanel);
        int nextPanelIndex = centerPanel.getComponentCount();

        // Tìm vị trí của panel cha tiếp theo trong centerPanel
        for (int i = selectedIndex + 1; i < centerPanel.getComponentCount(); i++) {
            Component component = centerPanel.getComponent(i);
           if (((JPanel) component).getClientProperty("isDetailPanel") == null) {
                nextPanelIndex = i;
                break;
            }
        }

        Component[] components = centerPanel.getComponents();
        for (int i = selectedIndex + 1; i < nextPanelIndex; i++) {
            Component component = centerPanel.getComponent(i);
            if (component instanceof JPanel && ((JPanel) component).getClientProperty("isDetailPanel") != null) {
                centerPanel.remove(component);
            } else {
                return; // Kết thúc vòng lặp nếu gặp panel không phải là chi tiết
            }
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }
    private void addDetailPanel(JPanel selectedPanel) {
        String[] labels = {"HD000001", "12:00", "Nguyen Vam A", "10", "10,000"};
        JPanel panel = createPanelDetail(labels);

        // Thêm panel chi tiết vào dưới panel được chọn
        int selectedIndex = centerPanel.getComponentZOrder(selectedPanel);
        centerPanel.add(panel, selectedIndex + 1);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JPanel createPanelDetail(String[] labels) {
        JPanel panel = createLabelPanel(Color.WHITE, new Color(202, 202, 202));
        panel.putClientProperty("isDetailPanel", true); // Đặt thuộc tính cho panel chi tiết
        addLabelsToPanel(panel, labels);
        return panel;
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
