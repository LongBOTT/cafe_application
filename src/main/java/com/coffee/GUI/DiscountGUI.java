package com.coffee.GUI;

import com.coffee.BLL.DiscountBLL;
import com.coffee.DTO.Discount;
import com.coffee.DTO.Discount_Detail;
import com.coffee.DTO.Function;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddDiscountGUInew;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailDiscountGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditDiscountGUI;
import com.coffee.GUI.components.*;
import com.coffee.ImportExcel.AddDiscountFromExcel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.coffee.utils.Resource.chooseExcelFile;


public class DiscountGUI extends Layout2 {
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JButton jButtonSearch;
    private JComboBox<String> jComboBoxSearch;
    private List<Function> functions;

    private DiscountBLL discountBLL = new DiscountBLL();
    private DataTable dataTable;
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private int indexColumnRemove = -1;
    private boolean detail = false;
    private boolean edit = false;
    private boolean remove = false;
    private String[] columnNames;

    private JDateChooser[] jDateChooser = new JDateChooser[0];

    private JTextField[] dateTextField = new JTextField[0];

    private JTextField[] jTextFieldDate = new JTextFieldDateEditor[0];
    private boolean processDateChangeEvent = true;
    private Object[][] data = new Object[0][0];

    public DiscountGUI(List<Function> functions) {
        super();
        this.functions = functions;
        if (functions.stream().anyMatch(f -> f.getName().equals("view")))
            detail = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("edit")))
            edit = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("remove")))
            remove = true;
        init(functions);
    }

    private void init(List<Function> functions) {
        containerSearch = new RoundedPanel();
        SearchPanel.setLayout(new MigLayout("", "[]10[]40[]20[]"));
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");
        jComboBoxSearch = new JComboBox<>(new String[]{"Tất cả", "Kích hoạt", "Chưa áp dụng"});
        JLabel lbFilter = new JLabel("Trạng thái");
        lbFilter.setFont((new Font("Public Sans", Font.BOLD, 14)));
        columnNames = new String[]{"Mã Giảm Giá", "Tên chương trình", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Hình thức", "Trạng Thái"};
        if (detail) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnDetail = columnNames.length - 1;
            columnNames[indexColumnDetail] = "Xem";
        }

        if (edit) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnEdit = columnNames.length - 1;
            columnNames[indexColumnEdit] = "Sửa";
        }

        if (remove) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnRemove = columnNames.length - 1;
            columnNames[indexColumnRemove] = "Xoá";
        }

        dataTable = new DataTable(new Object[0][0], columnNames, e -> selectFunction(),
                detail, edit, remove, 6);
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        bottom.add(scrollPane, BorderLayout.CENTER);

        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(320, 40));
        SearchPanel.add(containerSearch);

        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Tìm kiếm theo tên chương trình");
        jTextFieldSearch.setPreferredSize(new Dimension(220, 30));
        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(1, 120, 220));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 40));
        jButtonSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                searchDiscountByName();
            }
        });
        SearchPanel.add(jButtonSearch);

        jComboBoxSearch.setBackground(new Color(1, 120, 220));
        jComboBoxSearch.setForeground(Color.white);
        jComboBoxSearch.setPreferredSize(new Dimension(150, 40));
        jComboBoxSearch.addActionListener(e -> {
            SelectDiscountStatus();
        });
        SearchPanel.add(lbFilter);
        SearchPanel.add(jComboBoxSearch);

        loadDataTable(discountBLL.getData(discountBLL.searchDiscounts()));

        RoundedPanel refreshPanel = new RoundedPanel();
        refreshPanel.setLayout(new GridBagLayout());
        refreshPanel.setPreferredSize(new Dimension(120, 40));
        refreshPanel.setBackground(new Color(1, 120, 220));
        refreshPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                refresh();
            }
        });
        FunctionPanel.add(refreshPanel);

        jTextFieldDate = new JTextField[2];
        dateTextField = new JTextField[2];
        jDateChooser = new JDateChooser[2];


        for (int i = 0; i < 2; i++) {
            jTextFieldDate[i] = new JTextField();
            jTextFieldDate[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
            jTextFieldDate[i].setPreferredSize(new Dimension(200, 30));
            jTextFieldDate[i].setAutoscrolls(true);

            jDateChooser[i] = new JDateChooser();
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(new Dimension(150, 30));
            jDateChooser[i].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

            if (i == 0) {
                JLabel jLabel = new JLabel("Từ Ngày");
                jLabel.setFont(new Font("Lexend", Font.BOLD, 14));
                FilterDatePanel.add(jLabel);
                JLabel jLabel1 = new JLabel("         ");
                FilterDatePanel.add(jLabel1);
            } else {
                JLabel jLabel = new JLabel("Đến Ngày");
                jLabel.setFont(new Font("Lexend", Font.BOLD, 14));
                FilterDatePanel.add(jLabel);
                JLabel jLabel1 = new JLabel("         ");
                FilterDatePanel.add(jLabel1);
            }
            FilterDatePanel.add(jDateChooser[i]);
            JLabel jLabel1 = new JLabel("      ");
            FilterDatePanel.add(jLabel1);
        }
        jDateChooser[0].addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    searchDiscountByStartDate_EndDate();
                }
            }
        });

        jDateChooser[1].addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    searchDiscountByStartDate_EndDate();
                }
            }
        });

        JLabel refreshLabel = new JLabel("Làm mới");
        refreshLabel.setFont(new Font("Public Sans", Font.PLAIN, 13));
        refreshLabel.setForeground(Color.white);
        refreshLabel.setIcon(new FlatSVGIcon("icon/refresh.svg"));
        refreshPanel.add(refreshLabel);
        refreshPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                refresh();
            }
        });
        if (functions.stream().anyMatch(f -> f.getName().equals("add"))) {
            RoundedPanel roundedPanel = getRoundedPanel();
            roundedPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    new AddDiscountGUInew();
                    refresh();
                }
            });
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Thêm mới");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setForeground(Color.white);
            panel.setIcon(new FlatSVGIcon("icon/add.svg"));
            roundedPanel.add(panel);
        }
        if (functions.stream().anyMatch(f -> f.getName().equals("excel"))) {
            RoundedPanel roundedPanel = getRoundedPanel();
            roundedPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    File file = chooseExcelFile(null);
                    if (file != null) {
                        Pair<Boolean, String> result;
                        try {
                            result = new AddDiscountFromExcel().addDiscountsFromExcel(file);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (!result.getKey()) {
                            JOptionPane.showMessageDialog(null, result.getValue(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Thêm chương trình giảm giá thành công",
                                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            refresh();
                        }
                    }
                }
            });

            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Nhập Excel");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setForeground(Color.white);
            panel.setIcon(new FlatSVGIcon("icon/import.svg"));
            roundedPanel.add(panel);
        }
        if (functions.stream().anyMatch(f -> f.getName().equals("pdf"))) {
            RoundedPanel roundedPanel = getRoundedPanel();
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Xuất PDF");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setForeground(Color.white);
            panel.setIcon(new FlatSVGIcon("icon/export.svg"));
            roundedPanel.add(panel);
        }
    }

    private RoundedPanel getRoundedPanel() {
        RoundedPanel roundedPanel = new RoundedPanel();
        roundedPanel.setLayout(new GridBagLayout());
        roundedPanel.setPreferredSize(new Dimension(120, 40));
        roundedPanel.setBackground(new Color(1, 120, 220));
        roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return roundedPanel;
    }

    public void refresh() {
        jTextFieldSearch.setText("");
        jComboBoxSearch.setSelectedIndex(0);

        processDateChangeEvent = false;
        jDateChooser[0].setDate(null);
        jDateChooser[1].setDate(null);
        processDateChangeEvent = true;


        loadDataTable(discountBLL.getData(discountBLL.searchDiscounts()));
    }

    private void searchDiscountByName() {
        String value = jTextFieldSearch.getText();
        if (value.isEmpty()) {
            loadDataTable(discountBLL.getData(discountBLL.searchDiscounts()));
        } else {
            loadDataTable(discountBLL.getData(discountBLL.findDiscounts("name", value)));
        }
    }

    private void searchDiscountByStartDate_EndDate() {
        if (!processDateChangeEvent) {
            return;
        }
        Date startDate = jDateChooser[0].getDate();
        Date endDate = jDateChooser[1].getDate();

        if (startDate != null && endDate != null && startDate.before(endDate)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateStr = dateFormat.format(startDate);
            String endDateStr = dateFormat.format(endDate);

            loadDataTable(discountBLL.getData(discountBLL.searchDiscounts("start_date >= '" + startDateStr + "' AND end_date <= '" + endDateStr + "'")));
        } else if (startDate != null && endDate != null && startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if (startDate != null && endDate == null) {
            // Người dùng chọn ngày bắt đầu trước khi chọn ngày kết thúc, không làm gì cả
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void SelectDiscountStatus() {
        String selectedItem = Objects.requireNonNull(jComboBoxSearch.getSelectedItem()).toString();
        if (selectedItem.equals("Kích hoạt")) {
            loadDataTable(discountBLL.getData(discountBLL.searchDiscounts("status = 0")));
        } else if (selectedItem.equals("Chưa áp dụng")) {
            loadDataTable(discountBLL.getData(discountBLL.searchDiscounts("status = 1")));
        } else
            loadDataTable(discountBLL.getData(discountBLL.searchDiscounts()));
    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        if (objects.length == 0) {
            return;
        }

        data = new Object[objects.length][objects[0].length];

        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);

            data[i][2] = convertDateString(objects[i][2].toString());
            data[i][3] = convertDateString(objects[i][3].toString());

            if (detail) {
                JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconDetail;
            }
            if (edit) {
                JLabel iconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconEdit;
            }
        }

        for (Object[] object : data) {
            model.addRow(object);
        }
    }

    private String convertDateString(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object selectedValue = model.getValueAt(indexRow, 0);
        if (indexColumn == indexColumnDetail) {
            new DetailDiscountGUI(discountBLL.searchDiscounts("id = " + data[indexRow][0]).get(0));
        }
        if (indexColumn == indexColumnEdit) {
            new EditDiscountGUI(discountBLL.searchDiscounts("id = " + data[indexRow][0]).get(0));
            refresh();
        }
    }

}
