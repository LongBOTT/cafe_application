package com.coffee.GUI;

import com.coffee.BLL.Payroll_DetailBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.DTO.Function;
import com.coffee.DTO.Payroll;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditSupplierGUI;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.Layout1;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PayrollDetailGUI extends Layout1 {
    private Payroll payroll;
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JButton jButtonSearch;
    private Payroll_DetailBLL payrollDetailBLL = new Payroll_DetailBLL();
    private DataTable dataTable;
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private String[] columnNames;
    private HomeGUI homeGUI;
    private List<Function> functions;


    public PayrollDetailGUI(Payroll payroll, List<Function> functions, HomeGUI homeGUI) {
        super();
        this.payroll = payroll;
        this.functions = functions;
        this.homeGUI = homeGUI;
        init();
    }

    private void init() {
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");

        columnNames = new String[]{"Mã Nhân Viên", "Tên Nhân Viên", "Tổng Lương", "Trạng Thái"};

        columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
        indexColumnDetail = columnNames.length - 1;
        columnNames[indexColumnDetail] = "Xem";

        columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
        indexColumnEdit = columnNames.length - 1;
        columnNames[indexColumnEdit] = "Sửa";

        dataTable = new DataTable(new Object[0][0], columnNames, e -> selectFunction(), true, true, false, 4); // table hiển thị các thuộc tính  nên điền 4

        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        bottom.add(scrollPane, BorderLayout.CENTER);

        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(280, 40));
        SearchPanel.add(containerSearch);

        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Nhập tên nhân viên tìm kiếm");
        jTextFieldSearch.setPreferredSize(new Dimension(280, 30));
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchPayroll_Details();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchPayroll_Details();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchPayroll_Details();
            }
        });

        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(110, 35));
        jButtonSearch.addActionListener(e -> searchPayroll_Details());
        SearchPanel.add(jButtonSearch);


        loadDataTable(payrollDetailBLL.getData(payrollDetailBLL.searchPayroll_Details("payroll_id = " + payroll.getId())));

        RoundedPanel returnPanel = new RoundedPanel();
        returnPanel.setLayout(new GridBagLayout());
        returnPanel.setPreferredSize(new Dimension(130, 40));
        returnPanel.setBackground(new Color(217, 217, 217));
        returnPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                homeGUI.openModule(new PayrollGUI(functions, homeGUI)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá
            }
        });
        FunctionPanel.add(returnPanel);

        JLabel returnLabel = new JLabel("Quay lại");
        returnLabel.setFont(new Font("Public Sans", Font.PLAIN, 13));
        returnLabel.setIcon(new FlatSVGIcon("icon/return.svg"));
        returnPanel.add(returnLabel);

        RoundedPanel roundedPanelExcel = new RoundedPanel();
        roundedPanelExcel.setLayout(new GridBagLayout());
        roundedPanelExcel.setPreferredSize(new Dimension(130, 40));
        roundedPanelExcel.setBackground(new Color(217, 217, 217));
        roundedPanelExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FunctionPanel.add(roundedPanelExcel);

        JLabel panelExcel = new JLabel("Xuất Excel");
        panelExcel.setFont(new Font("Public Sans", Font.PLAIN, 13));
        panelExcel.setIcon(new FlatSVGIcon("icon/excel.svg"));
        roundedPanelExcel.add(panelExcel);


        RoundedPanel roundedPanelPDF = new RoundedPanel();
        roundedPanelPDF.setLayout(new GridBagLayout());
        roundedPanelPDF.setPreferredSize(new Dimension(130, 40));
        roundedPanelPDF.setBackground(new Color(217, 217, 217));
        roundedPanelPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FunctionPanel.add(roundedPanelPDF);

        JLabel panelPDF = new JLabel("Xuất PDF");
        panelPDF.setFont(new Font("Public Sans", Font.PLAIN, 13));
        panelPDF.setIcon(new FlatSVGIcon("icon/pdf.svg"));
        roundedPanelPDF.add(panelPDF);

    }

    public void refresh() {
        jTextFieldSearch.setText("");
        loadDataTable(payrollDetailBLL.getData(payrollDetailBLL.searchPayroll_Details("payroll_id = " + payroll.getId())));
    }

    private void searchPayroll_Details() {

    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        Object[][] data = new Object[objects.length][objects[0].length];

        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);

            int staffId = Integer.parseInt(data[i][1].toString());
            data[i][2] = new StaffBLL().searchStaffs("id = " + staffId).get(0).getName();

            JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
            data[i] = Arrays.copyOf(data[i], data[i].length + 1);
            data[i][data[i].length - 1] = iconDetail;

            JLabel iconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
            data[i] = Arrays.copyOf(data[i], data[i].length + 1);
            data[i][data[i].length - 1] = iconEdit;
        }

        for (Object[] object : data) {
            object = Arrays.copyOfRange(object, 1, 7);
            model.addRow(object);
        }
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (indexColumn == indexColumnDetail)
//            homeGUI.openModule(new PayrollGUI(functions, homeGUI)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá

            if (indexColumn == indexColumnEdit) {
                refresh();
            }
    }
}
