package com.coffee.GUI;

import com.coffee.BLL.Import_NoteBLL;
import com.coffee.BLL.ReceiptBLL;
import com.coffee.DAL.MySQL;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailImportGUI;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailReceiptGUI;
import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.RoundedPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StatisticEndOfTheDayGUI extends JPanel {
    private JScrollPane scrollPane;
    private JScrollPane scrollPaneDetail;
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
        centerPanel = new JPanel(new MigLayout("", "0[]0", "0[]0"));
        centerPanel.setBackground(new Color(255, 255, 255));
        scrollPaneDetail = new JScrollPane();
        scrollPaneDetail.setPreferredSize(new Dimension(PANEL_WIDTH, 580));
        scrollPaneDetail.setViewportView(centerPanel);
        scrollPaneDetail.setBorder(null);

        content.add(scrollPaneDetail);
        // mặc đinh sẽ hiển thị báo cáo cuối ngày về bán hàng
        setUpSalesReports();
    }

    private void setUpSalesReports() {
        centerPanel.removeAll();

        JPanel salesLabelPanel = createLabelPanel(new Color(178, 232, 255), new MigLayout("", "10[]20[]20[]30[]20[]10", ""));
        addLabelsToPanel(salesLabelPanel, new String[]{"Mã hóa đơn", "Thời gian", "Nhân viên tạo", "SLSP", "Doanh thu",}, 4,new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesLabelPanel, "wrap");

        Date currentDate = new Date(System.currentTimeMillis());
        List<Map.Entry<List<String>, List<List<String>>>> salesStatistics = MySQL.getSalesStatistics(currentDate);

        JPanel salesDataPanel = createLabelPanel(new Color(242, 238, 214), new MigLayout("", "10[]20[]20[]30[]20[]10", ""));
        // Tính toán số lượng hóa đơn, tổng số lượng sản phẩm và tổng doanh thu từ salesStatistics
        long totalInvoices = 0;
        long totalQuantity = 0;
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (Map.Entry<List<String>, List<List<String>>> entry : salesStatistics) {
            List<String> hour = entry.getKey();
            List<List<String>> invoices = entry.getValue();
            totalInvoices += invoices.size(); // Số lượng hóa đơn trong 1 giờ
            long quantity = Long.parseLong(hour.get(2)); // sô lượng sản phẩm trong 1 giờ
            BigDecimal revenue = new BigDecimal(hour.get(3).replace(",", "")); // doanh thu trong 1 giờ
            totalQuantity += quantity;
            totalRevenue = totalRevenue.add(revenue);
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedRevenue = numberFormat.format(totalRevenue); // format kiểu tiền tệ theo hàng nghìn

        addLabelsToPanel(salesDataPanel, new String[]{"Hóa đơn: " + totalInvoices, "", "", totalQuantity + "", formattedRevenue}, 4,new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesDataPanel, "wrap");


        // Tạo panel báo cáo cho mỗi dòng trong mảng dữ liệu
        for (Map.Entry<List<String>, List<List<String>>> entry : salesStatistics) {
            List<String> keyList = entry.getKey();
            List<List<String>> invoices = entry.getValue();
            if (!keyList.isEmpty() && !invoices.isEmpty()) {
                // Hiển thị thông tin tổng quan
                JPanel summaryPanel = createPanelReport(keyList, invoices);
                expandedStateMap.put(summaryPanel, false); // Ban đầu, tất cả các panel đều không mở rộng
                centerPanel.add(summaryPanel, "wrap");
            }
        }

        centerPanel.repaint();
        centerPanel.revalidate();

    }

    private void setUpMonthlyReport() {
        centerPanel.removeAll();

        JPanel salesLabelPanel = createLabelPanel(new Color(178, 232, 255), new MigLayout("", "10[]20[]20[]20[]20[]10", ""));
        addLabelsToPanel(salesLabelPanel, new String[]{"Mã chứng từ", "Loại chi", "Người tạo", "Thời gian", "Thu/Chi"}, 4,new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesLabelPanel, "wrap");
        Date currentDate = new Date(System.currentTimeMillis());

        List<List<String>> monthlyStatistics = MySQL.getExpenseIncomeData(currentDate);
        int totalInvoices = 0;
        BigDecimal totalRevenue = BigDecimal.ZERO;

        for (List<String> row : monthlyStatistics) {
            String id = row.get(0);
            BigDecimal amount = new BigDecimal(row.get(4).replaceAll(",", ""));

            if (id.startsWith("HD")) {
                totalRevenue = totalRevenue.add(amount);
            } else {
                totalRevenue = totalRevenue.add(amount);// lý do cộng là vì nó đang là âm sẳn rồi nên cộng tức là trừ
            }
            totalInvoices++;
        }
        JPanel salesDataPanel = createLabelPanel(new Color(242, 238, 214), new MigLayout("", "10[]20[]20[]20[]20[]10", ""));
        addLabelsToPanel(salesDataPanel, new String[]{"Số lượng phiếu: " + totalInvoices, "", "", "", NumberFormat.getNumberInstance(Locale.US).format(totalRevenue)}, 4,new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesDataPanel, "wrap");


        for (List<String> row : monthlyStatistics) {
            JPanel panel = createLabelPanelMon(Color.WHITE, new MigLayout("", "10[]20[]20[]40[]0[]10", ""));
            addLabelsToPanelDetailMonthly(panel, row.toArray(new String[0]));
            centerPanel.add(panel, "wrap");
        }

        centerPanel.repaint();
        centerPanel.revalidate();
    }

    private void setUpProduct() {
        centerPanel.removeAll();
        JPanel salesLabelPanel = createLabelPanel(new Color(178, 232, 255), new MigLayout("", "10[]20[]20[]20[]20[]20[]10", ""));
        addLabelsToPanel(salesLabelPanel, new String[]{"Mã hàng", "Tên hàng", "SLSP", "Giá trị niêm yết", "Doanh thu", "Chênh lệch"}, 5,new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesLabelPanel, "wrap");

        Date currentDate = new Date(System.currentTimeMillis());
        List<Map.Entry<List<String>, List<List<String>>>> productsStatistics = MySQL.getproductsStatistics(currentDate);

        JPanel salesDataPanel = createLabelPanel(new Color(242, 238, 214), new MigLayout("", "10[]20[]20[]20[]20[]20[]10", ""));
        addLabelsToPanel(salesDataPanel, new String[]{"Sl mặt hàng: 3", "", "4", "180,000", "172,000", "8000"}, 5,new Font("Inter", Font.BOLD, 13));
        centerPanel.add(salesDataPanel, "wrap");
        // Tính toán số lượng hóa đơn, tổng số lượng sản phẩm và tổng doanh thu từ salesStatistics
//        long totalInvoices = 0;
//        long totalQuantity = 0;
//        BigDecimal totalRevenue = BigDecimal.ZERO;
//
//        for (Map.Entry<List<String>, List<List<String>>> entry : productsStatistics) {
//            List<String> hour = entry.getKey();
//            List<List<String>> invoices = entry.getValue();
//            totalInvoices += invoices.size(); // Số lượng hóa đơn trong 1 giờ
//            long quantity = Long.parseLong(hour.get(2)); // sô lượng sản phẩm trong 1 giờ
//            BigDecimal revenue = new BigDecimal(hour.get(3).replace(",", "")); // doanh thu trong 1 giờ
//            totalQuantity += quantity;
//            totalRevenue = totalRevenue.add(revenue);
//        }
//        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
//        String formattedRevenue = numberFormat.format(totalRevenue); // format kiểu tiền tệ theo hàng nghìn
//
//        addLabelsToPanel(salesDataPanel, new String[]{"Hóa đơn: " + totalInvoices, "", "", totalQuantity + "", formattedRevenue}, 4);
//        centerPanel.add(salesDataPanel, "wrap");
//
//
//        // Tạo panel báo cáo cho mỗi dòng trong mảng dữ liệu
//        for (Map.Entry<List<String>, List<List<String>>> entry : productsStatistics) {
//            List<String> keyList = entry.getKey();
//            List<List<String>> invoices = entry.getValue();
//            if (!keyList.isEmpty() && !invoices.isEmpty()) {
////                // Hiển thị thông tin tổng quan
//        JPanel summaryPanel = createPanelReport(keyList, invoices);
//                expandedStateMap.put(summaryPanel, false); // Ban đầu, tất cả các panel đều không mở rộng
//                centerPanel.add(summaryPanel, "wrap");
//            }
//        }

        centerPanel.repaint();
        centerPanel.revalidate();
    }


    private JPanel createLabelPanel(Color background, LayoutManager layoutManager) {
        JPanel panel = new JPanel();
        panel.setLayout(layoutManager);
        panel.setPreferredSize(new Dimension(868, PANEL_HEIGHT));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(202, 202, 202)));
        return panel;
    }

    private JPanel createLabelPanelMon(Color background, LayoutManager layoutManager) {
        JPanel panel = new JPanel();
        panel.setLayout(layoutManager);
        panel.setPreferredSize(new Dimension(868, PANEL_HEIGHT));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(202, 202, 202)));
        return panel;
    }

    private void addLabelsToPanel(JPanel panel, String[] labels, int customRight,Font font) {
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

    private void addLabelsToPanelDetailMonthly(JPanel panel, String[] labels) {
        int i = 0;
        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(new Font("Inter", Font.PLAIN, 13));
            jLabel.setPreferredSize(LABEL_SIZE);
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);

            if (i == 0) {
                jLabel.setHorizontalAlignment(SwingConstants.LEFT);
                jLabel.setForeground(new Color(33, 113, 188));
                jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                jLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String labelText = jLabel.getText();
                        if (labelText.startsWith("HD")) {
                            String numberText = labelText.substring(2); // Cắt bỏ "HD" từ chuỗi
                            try {
                                int invoiceNumber = Integer.parseInt(numberText);
                                new DetailReceiptGUI(new ReceiptBLL().searchReceipts("id = " + invoiceNumber).get(0));
                            } catch (NumberFormatException ex) {
                                System.out.println("Lỗi: không thể chuyển đổi thành số nguyên");
                            }
                        }
                        if (labelText.startsWith("PN")) {
                            String numberText = labelText.substring(2); // Cắt bỏ "HD" từ chuỗi
                            try {
                                int importNumber = Integer.parseInt(numberText);
                                new DetailImportGUI(new Import_NoteBLL().searchImport("id = " + importNumber).get(0));
                            } catch (NumberFormatException ex) {
                                System.out.println("Lỗi: không thể chuyển đổi thành số nguyên");
                            }
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        jLabel.setForeground(new Color(100, 157, 204));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        jLabel.setForeground(new Color(33, 113, 188));
                    }
                });
            }

            if (i == 4) {
//                String formattedRevenue = NumberFormat.getNumberInstance(Locale.US).format(new BigDecimal(label));
//                jLabel.setText(formattedRevenue);
                jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            }

            panel.add(jLabel);
            i++;
        }
    }

    private void addLabelsToPanelDetail(JPanel panel, String[] labels) {
        int i = 0;
        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            jLabel.setFont(new Font("Inter", Font.PLAIN, 13));
            jLabel.setPreferredSize(LABEL_SIZE);
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);

            if (i == 0) {
                jLabel.setHorizontalAlignment(SwingConstants.LEFT);
                jLabel.setForeground(new Color(33, 113, 188));
                jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                jLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String labelText = jLabel.getText();
                        if (labelText.startsWith("HD")) {
                            String numberText = labelText.substring(2); // Cắt bỏ "HD" từ chuỗi
                            try {
                                int invoiceNumber = Integer.parseInt(numberText);
                                new DetailReceiptGUI(new ReceiptBLL().searchReceipts("id = " + invoiceNumber).get(0));
                            } catch (NumberFormatException ex) {
                                System.out.println("Lỗi: không thể chuyển đổi thành số nguyên");
                            }
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        jLabel.setForeground(new Color(100, 157, 204));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        jLabel.setForeground(new Color(33, 113, 188));
                    }
                });
            }

            if (i == 4) {
                String formattedRevenue = NumberFormat.getNumberInstance(Locale.US).format(new BigDecimal(label));
                jLabel.setText(formattedRevenue);
                jLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            }

            panel.add(jLabel);
            i++;
        }
    }


    private JPanel createPanelReport(List<String> keyList, List<List<String>> invoices) {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("", "10[]20[]20[]30[]20[]10", ""));
        panel.setPreferredSize(new Dimension(868, 50));
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
            togglePanel(btnDetail, selectedPanel, invoices);// Kích hoạt chức năng mở rộng/ thu gọn khi nhấn nút
        });

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel1.setLayout(new BorderLayout());

        JLabel lblDate = new JLabel(keyList.get(0));
        lblDate.setFont(new Font("Inter", Font.PLAIN, 13));
        lblDate.setForeground(new Color(33, 160, 223));

        JLabel lblHour = new JLabel(keyList.get(1));
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

        JLabel lblQuantity = new JLabel(keyList.get(2));
        lblQuantity.setFont(new Font("Inter", Font.PLAIN, 13));
        lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuantity.setPreferredSize(new Dimension(150, 50));

        String formattedRevenue = NumberFormat.getNumberInstance(Locale.US).format(new BigDecimal(keyList.get(3)));
        JLabel lblRevenue = new JLabel(formattedRevenue);
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

    private JPanel createPanelProduct(List<String> keyList, List<List<String>> invoices) {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("", "10[]20[]20[]20[]20[]20[]10", ""));
        panel.setPreferredSize(new Dimension(868, 50));
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
            togglePanel(btnDetail, selectedPanel, invoices);// Kích hoạt chức năng mở rộng/ thu gọn khi nhấn nút
        });


        JLabel lblProductID = new JLabel(keyList.get(0));
        lblProductID.setFont(new Font("Inter", Font.PLAIN, 13));
        lblProductID.setForeground(new Color(33, 160, 223));

        JLabel lblName = new JLabel(keyList.get(1));
        lblName.setFont(new Font("Inter", Font.PLAIN, 13));
        lblName.setForeground(new Color(33, 160, 223));


        containerBtn.add(btnDetail);
        containerBtn.add(lblProductID);

        JLabel lblQuantity = new JLabel(keyList.get(2));
        lblQuantity.setFont(new Font("Inter", Font.PLAIN, 13));
        lblQuantity.setHorizontalAlignment(SwingConstants.CENTER);
        lblQuantity.setPreferredSize(new Dimension(150, 50));

        JLabel lbl_listed_value = new JLabel(keyList.get(3));
        lbl_listed_value.setFont(new Font("Inter", Font.PLAIN, 13));
        lbl_listed_value.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_listed_value.setPreferredSize(new Dimension(150, 50));

        JLabel lblRevenue = new JLabel(keyList.get(4));
        lblRevenue.setFont(new Font("Inter", Font.PLAIN, 13));
        lblRevenue.setHorizontalAlignment(SwingConstants.CENTER);
        lblRevenue.setPreferredSize(new Dimension(150, 50));

        JLabel lblDifference = new JLabel(keyList.get(5));
        lblDifference.setFont(new Font("Inter", Font.PLAIN, 13));
        lblDifference.setPreferredSize(new Dimension(150, 50));
        lblDifference.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(containerBtn);
        panel.add(lblName);
        panel.add(lblQuantity);
        panel.add(lbl_listed_value);
        panel.add(lblRevenue);
        panel.add(lblDifference);

        return panel;
    }

    private void togglePanel(JButton btnDetail, JPanel selectedPanel, List<List<String>> invoices) {
        boolean isExpanded = expandedStateMap.get(selectedPanel); // Lấy trạng thái mở rộng của panel được chọn
        if (isExpanded) {
            removeDetailPanels(selectedPanel);
            btnDetail.setIcon(new FlatSVGIcon("icon/add-square-svgrepo-com.svg"));
        } else {
            addDetailPanel(selectedPanel, invoices); // Thêm danh sách hóa đơn khi mở rộng
            btnDetail.setIcon(new FlatSVGIcon("icon/minus-square-svgrepo-com.svg"));
        }
        expandedStateMap.put(selectedPanel, !isExpanded); // Cập nhật trạng thái mở rộng
    }

    private void removeDetailPanels(JPanel selectedPanel) {
        int selectedIndex = centerPanel.getComponentZOrder(selectedPanel);

        for (int i = selectedIndex + 1; i < centerPanel.getComponentCount(); i++) {
            Component component = centerPanel.getComponent(i);
            Boolean isDetailPanel = (Boolean) ((JPanel) component).getClientProperty("isDetailPanel");
            System.out.println(i);
            System.out.println(isDetailPanel);
            if (isDetailPanel != null && isDetailPanel) {
                centerPanel.remove(component);
                i--;
            } else break;
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void addDetailPanel(JPanel selectedPanel, List<List<String>> invoices) {
        for (List<String> invoiceDetail : invoices) {
            JPanel panel = createPanelDetail(invoiceDetail.toArray(new String[0]));
            int selectedIndex = centerPanel.getComponentZOrder(selectedPanel);
            centerPanel.add(panel, "growx, wrap", selectedIndex + 1);
        }
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JPanel createPanelDetail(String[] labels) {
        JPanel panel = createLabelPanel(Color.WHITE, new MigLayout("", "10[]20[]20[]30[]20[]10", ""));
        panel.putClientProperty("isDetailPanel", true); // Đặt thuộc tính cho panel chi tiết
        addLabelsToPanelDetail(panel, labels);
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


        ButtonGroup btnGroupConcerns = new ButtonGroup();
        btnGroupConcerns.add(sellRadioButton);
        btnGroupConcerns.add(revenueAndExpenditureRadioButton);
        btnGroupConcerns.add(productRadioButton);


        concernsPanel.add(sellRadioButton, "wrap");
        concernsPanel.add(revenueAndExpenditureRadioButton, "wrap");
        concernsPanel.add(productRadioButton, "wrap");


        // mặc định khi chọn vào tab bán hàng sẽ chọn tiêu chí bán hàng đầu tiên là mặc định
        sellRadioButton.setSelected(true);


        sellRadioButton.addActionListener(e -> {
            // Kiểm tra nếu bán hàng  được chọn
            if (sellRadioButton.isSelected()) {
                titleLabel.setText("Báo cáo cuối ngày về bán hàng");
                setUpSalesReports();
            }
        });
        revenueAndExpenditureRadioButton.addActionListener(e -> {
            // Kiểm tra nếu thu chi được chọn
            if (revenueAndExpenditureRadioButton.isSelected()) {
                titleLabel.setText("Báo cáo cuối ngày về thu chi");
                setUpMonthlyReport(); // Hiển thị báo cáo cuối ngày về thu chi
            }
        });
        productRadioButton.addActionListener(e -> {
            // Kiểm tra nếu hàng hóa được chọn
            if (productRadioButton.isSelected()) {
                titleLabel.setText("Báo cáo cuối ngày về hàng hóa");
                setUpProduct();
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
