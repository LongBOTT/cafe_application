package com.coffee.GUI;

import com.coffee.BLL.PayrollBLL;
import com.coffee.DTO.Function;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddPayrollGUI;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.Layout1;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PayrollGUI extends Layout1 {
    private RoundedPanel containerSearch;
    private JMonthChooser jMonthChooserStart;
    private JYearChooser jYearChooserStart;
    private JMonthChooser jMonthChooserEnd;
    private JYearChooser jYearChooserEnd;
    private List<Function> functions;
    private PayrollBLL payrollBLL = new PayrollBLL();
    private DataTable dataTable;
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private boolean detail = false;
    private String[] columnNames;
    private HomeGUI homeGUI;

    public PayrollGUI(List<Function> functions, HomeGUI homeGUI) {
        super();
        this.functions = functions;
        this.homeGUI = homeGUI;
        if (functions.stream().anyMatch(f -> f.getName().equals("view"))) detail = true;
        init(functions);
    }

    private void init(List<Function> functions) {
        containerSearch = new RoundedPanel();

        columnNames = new String[]{"Mã", "Tên", "Kỳ Làm Việc", "Tổng Lương", "Đã Trả", "Còn Lại"};
        if (detail) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnDetail = columnNames.length - 1;
            columnNames[indexColumnDetail] = "Xem";
        }

        dataTable = new DataTable(new Object[0][0], columnNames, e -> selectFunction(), detail, false, false, 6); // table hiển thị các thuộc tính  nên điền 4
        dataTable.setRowHeight(60);

        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        bottom.add(scrollPane, BorderLayout.CENTER);

        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(500, 40));
        SearchPanel.add(containerSearch);

        jMonthChooserStart = new JMonthChooser();
        jMonthChooserStart.setPreferredSize(new Dimension(100, 50));
        jMonthChooserStart.setMonth(0);
        containerSearch.add(jMonthChooserStart);

        jYearChooserStart = new JYearChooser();
        jYearChooserStart.setPreferredSize(new Dimension(100, 50));
        jYearChooserStart.setYear(2000);
        containerSearch.add(jYearChooserStart);

        containerSearch.add(new JLabel(" Đến "));

        jMonthChooserEnd = new JMonthChooser();
        jMonthChooserEnd.setPreferredSize(new Dimension(100, 50));
        jMonthChooserEnd.setMonth(LocalDate.now().getMonth().getValue() - 1);
        containerSearch.add(jMonthChooserEnd);

        jYearChooserEnd = new JYearChooser();
        jYearChooserEnd.setPreferredSize(new Dimension(100, 50));
        jYearChooserEnd.setYear(LocalDate.now().getYear());
        containerSearch.add(jYearChooserEnd);

        loadDataTable(payrollBLL.getData(payrollBLL.searchPayrolls()));

        RoundedPanel refreshPanel = new RoundedPanel();
        refreshPanel.setLayout(new GridBagLayout());
        refreshPanel.setPreferredSize(new Dimension(130, 40));
        refreshPanel.setBackground(new Color(217, 217, 217));
        refreshPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                refresh();
            }
        });
        FunctionPanel.add(refreshPanel);

        JLabel refreshLabel = new JLabel("Làm mới");
        refreshLabel.setFont(new Font("Public Sans", Font.PLAIN, 13));
        refreshLabel.setIcon(new FlatSVGIcon("icon/refresh.svg"));
        refreshPanel.add(refreshLabel);

        if (functions.stream().anyMatch(f -> f.getName().equals("add"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(217, 217, 217));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            roundedPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    new AddPayrollGUI();
                    refresh();
                }
            });

            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Thêm mới");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setIcon(new FlatSVGIcon("icon/add.svg"));
            roundedPanel.add(panel);
        }
        if (functions.stream().anyMatch(f -> f.getName().equals("excel"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(217, 217, 217));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Xuất Excel");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setIcon(new FlatSVGIcon("icon/excel.svg"));
            roundedPanel.add(panel);
        }
        if (functions.stream().anyMatch(f -> f.getName().equals("pdf"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(217, 217, 217));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Xuất PDF");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setIcon(new FlatSVGIcon("icon/pdf.svg"));
            roundedPanel.add(panel);
        }
    }

    public void refresh() {
        jMonthChooserStart.setMonth(0);
        jYearChooserStart.setYear(2000);

        jMonthChooserEnd.setMonth(LocalDate.now().getMonth().getValue() - 1);
        jYearChooserEnd.setYear(LocalDate.now().getYear());

        loadDataTable(payrollBLL.getData(payrollBLL.searchPayrolls()));
    }

    private void searchPayrolls() {

    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        Object[][] data = new Object[objects.length][objects[0].length];

        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);

            if (detail) {
                JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconDetail;
            }
        }

        for (Object[] object : data) {
            model.addRow(object);
        }
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail)
            homeGUI.openModule(new PayrollDetailGUI(payrollBLL.searchPayrolls().get(indexRow), functions, homeGUI)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá
    }
}
