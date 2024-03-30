package com.coffee.GUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;

import com.coffee.GUI.components.*;
import com.coffee.GUI.components.Layout2;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;


public class WareHouseGUI extends Layout2 {
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JButton jButtonSearch;
    private ButtonGroup btgroup;
    private List<Function> functions;
    private DataTable dataTable;
    private boolean detail = false;
    private int indexColumnDetail = -1;
    private RoundedScrollPane scrollPane;
    private String[] columnNames;
    private Date date;
    private ShipmentBLL shipmentBLL = new ShipmentBLL();

    public WareHouseGUI(List<Function> functions) {
        super();
        this.functions = functions;
        if (functions.stream().anyMatch(f -> f.getName().equals("view"))) detail = true;
        init(functions);
    }

    private void init(List<Function> functions) {
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");

        columnNames = new String[]{"Mã Lô", "Tên Nguyên Liệu", "Nhà Cung Cấp", "SL Tồn", "Ngày Sản Xuất", "Ngày Hết Hạn"};
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

        JLabel jLabelStatus = new JLabel("Trạng thái: ");
        jLabelStatus.setFont(new Font("Lexend", Font.BOLD, 14));
        FilterDatePanel.add(jLabelStatus);

        JRadioButton radio1 = new JRadioButton("Tất cả");
        radio1.setSelected(true);
        radio1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchShipments();
            }
        });
        FilterDatePanel.add(radio1);
        JRadioButton radio2 = new JRadioButton("Sắp hết hàng");
        radio2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchShipments();
            }
        });
        FilterDatePanel.add(radio2);
        JRadioButton radio3 = new JRadioButton("Sắp hết hạn");
        radio3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchShipments();
            }
        });
        FilterDatePanel.add(radio3);

        btgroup = new ButtonGroup();
        btgroup.add(radio1);
        btgroup.add(radio2);
        btgroup.add(radio3);

        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(280, 40));
        SearchPanel.add(containerSearch);

        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Nhập tên nguyên liệu cần tìm kiếm");
        jTextFieldSearch.setPreferredSize(new Dimension(280, 30));
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchShipments();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchShipments();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchShipments();
            }
        });

        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
        jButtonSearch.addActionListener(e -> searchShipments());
        SearchPanel.add(jButtonSearch);

        loadDataTable(shipmentBLL.getData(shipmentBLL.searchShipments()));

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
        jTextFieldSearch.setText("");
        loadDataTable(shipmentBLL.getData(shipmentBLL.searchShipments()));
    }

    private void searchShipments() {
        List<Shipment> shipmentList = shipmentBLL.searchShipments();
        String status = "";
        for (Enumeration<AbstractButton> buttons = btgroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                status = button.getText();
                break;
            }
        }
        if (status.equals("Tất cả")) {
            shipmentList = shipmentBLL.searchShipments();
        }
        if (status.equals("Sắp hết hàng")) {
            shipmentList = shipmentBLL.searchShipments("remain < 30");
        }
        if (status.equals("Sắp hết hạn")) {
            shipmentList = shipmentBLL.searchShipments("DATEDIFF(exp,NOW()) > 0", "DATEDIFF(exp,NOW()) <= 15");
        }
        if (jTextFieldSearch.getText().isEmpty()) {
            loadDataTable(shipmentBLL.getData(shipmentList));
        } else {
            List<Integer> materialIDList = new ArrayList<>();
            for (Material material : new MaterialBLL().findMaterials("name", jTextFieldSearch.getText()))
                materialIDList.add(material.getId());
            shipmentList.removeIf(shipment -> !materialIDList.contains(shipment.getMaterial_id()));
            loadDataTable(shipmentBLL.getData(shipmentList));
        }
    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        if (objects.length == 0) {
            return;
        }

        Object[][] data = new Object[objects.length][objects[0].length];

        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);

            int material_id = Integer.parseInt(data[i][1].toString());
            data[i][1] = "<html>" + new MaterialBLL().findMaterialsBy(Map.of("id", material_id)).get(0).getName() + "</html>";

            int supplier_id = Integer.parseInt(data[i][2].toString());
            data[i][2] = "<html>" + new SupplierBLL().findSuppliersBy(Map.of("id", supplier_id)).get(0).getName() + "</html>";

            if (detail) {
                JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconDetail;
            }
        }

        for (Object[] object : data) {
            Object[] objects1 = object;
            System.arraycopy(objects1, 0, object, 0, 3);
            System.arraycopy(objects1, 4, object, 3, 1);
            System.arraycopy(objects1, 6, object, 4, 3);
            object = Arrays.copyOfRange(object, 0, 7);
            model.addRow(object);
        }
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail) {
//            new DetailSupplierGUI(shipmentBLL.searchShipments("").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
        }

    }


}
