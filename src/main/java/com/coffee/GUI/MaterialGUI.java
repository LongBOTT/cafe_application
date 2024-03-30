package com.coffee.GUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.DTO.Function;
import com.coffee.DTO.Material;
import com.coffee.DTO.Shipment;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailSupplierGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditSupplierGUI;
import com.coffee.GUI.components.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class MaterialGUI extends Layout2 {
    private final MaterialBLL materialBLL = new MaterialBLL();
    private List<Function> functions;
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JButton jButtonSearch;
    private ButtonGroup btgroup;
    private boolean detail = false;
    private boolean edit = false;
    private boolean remove = false;
    private DataTable dataTable;
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private int indexColumnRemove = -1;
    private String[] columnNames;

    public MaterialGUI(List<Function> functions) {
        super();
        this.functions = functions;
        if (functions.stream().anyMatch(f -> f.getName().equals("view")))
            detail = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("edit")))
            edit = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("remove")))
            remove = true;
        System.out.println(detail);
        System.out.println(edit);
        System.out.println(remove);
        initComponents(functions);
    }

    public void initComponents(List<Function> functions) {
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");

        columnNames = new String[]{"ID", "Tên nguyên liệu", "Số lượng", "Đơn vị",};
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

        dataTable = new DataTable(new Object[0][0], columnNames,
                e -> selectFunction(),
                detail, edit, remove, 4);


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
                searchMaterials();
            }
        });
        FilterDatePanel.add(radio1);
        JRadioButton radio2 = new JRadioButton("Sắp hết hàng");
        radio2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMaterials();
            }
        });
        FilterDatePanel.add(radio2);

        btgroup = new ButtonGroup();
        btgroup.add(radio1);
        btgroup.add(radio2);

        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(280, 40));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);
        SearchPanel.add(containerSearch, gbc);


        gbc.gridx++;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 0, 10);
        SearchPanel.add(jButtonSearch, gbc);


        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm");
        jTextFieldSearch.setPreferredSize(new Dimension(250, 30));


        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 40));
        jButtonSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonSearch.addActionListener(e -> searchMaterials());
        SearchPanel.add(jButtonSearch);
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchMaterials();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchMaterials();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchMaterials();
            }
        });
        loadDataTable(materialBLL.getData(materialBLL.searchMaterials("deleted = 0")));
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
//                    new AddSupplierGUI();
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
        jTextFieldSearch.setText("");
        loadDataTable(materialBLL.getData(materialBLL.searchMaterials("deleted = 0")));
    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        if (objects.length == 0) {
            return;
        }

        Object[][] data = new Object[objects.length][4];

        for (int i = 0; i < objects.length; i++) {
            data[i][0] = objects[i][0];
            data[i][1] = objects[i][1];
            data[i][2] = objects[i][2];
            data[i][3] = objects[i][3];

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
            if (remove) {
                JLabel iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconRemove;
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
//            new DetailSupplierGUI(materialBLL.searchMaterials("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

            if (edit && indexColumn == indexColumnEdit) {
//            new EditSupplierGUI(supplierBLL.searchSuppliers("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
                refresh();
            }

//        if (detail && indexColumn == indexColumnRemove)
////            deleteSupplier(supplierBLL.searchSuppliers("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
//
    }

    private void searchMaterials() {
        List<Material> materialList = materialBLL.searchMaterials();
        String status = "";
        for (Enumeration<AbstractButton> buttons = btgroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                status = button.getText();
                break;
            }
        }
        if (status.equals("Tất cả")) {
            materialList = materialBLL.searchMaterials();
        }
        if (status.equals("Sắp hết hàng")) {
            materialList = materialBLL.searchMaterials("remain < 30");
        }
        if (jTextFieldSearch.getText().isEmpty()) {
            loadDataTable(materialBLL.getData(materialList));
        } else {
            List<Integer> materialIDList = new ArrayList<>();
            for (Material material : new MaterialBLL().findMaterials("name", jTextFieldSearch.getText()))
                materialIDList.add(material.getId());
            materialList.removeIf(material -> !materialIDList.contains(material.getId()));
            loadDataTable(materialBLL.getData(materialList));
        }
    }

}