package com.coffee.GUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ReceiptBLL;
import com.coffee.DTO.Function;
import com.coffee.DTO.Material;
import com.coffee.DTO.Receipt;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddMaterialGUI;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailMaterialGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditMaterialGUI;
import com.coffee.GUI.components.*;
import com.coffee.main.PDF;
import com.coffee.ImportExcel.AddMaterialFromExcel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static com.coffee.utils.Resource.chooseExcelFile;

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
        initComponents(functions);
    }

    public void initComponents(List<Function> functions) {
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");

        columnNames = new String[]{"ID", "Tên nguyên liệu", "Tồn kho", "Đơn vị", "Giá vốn"};
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
                detail, edit, remove, 5);


        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        bottom.add(scrollPane, BorderLayout.CENTER);

        JLabel jLabelStatus = new JLabel("Tồn kho: ");
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

        JRadioButton radio2 = new JRadioButton("Dưới định mức tồn");
        radio2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMaterials();
            }
        });
        FilterDatePanel.add(radio2);

        JRadioButton radio3 = new JRadioButton("Vượt định mức tồn");
        radio3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMaterials();
            }
        });
        FilterDatePanel.add(radio3);

        JRadioButton radio4 = new JRadioButton("Còn hàng");
        radio4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMaterials();
            }
        });
        FilterDatePanel.add(radio4);

        JRadioButton radio5 = new JRadioButton("Hết hàng");
        radio5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMaterials();
            }
        });
        FilterDatePanel.add(radio5);

        btgroup = new ButtonGroup();
        btgroup.add(radio1);
        btgroup.add(radio2);
        btgroup.add(radio3);
        btgroup.add(radio4);
        btgroup.add(radio5);

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

        jButtonSearch.setBackground(new Color(1, 120, 220));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
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
        refreshPanel.setBackground(new Color(1, 120, 220));
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
        refreshLabel.setForeground(Color.white);
        refreshLabel.setIcon(new FlatSVGIcon("icon/refresh.svg"));
        refreshPanel.add(refreshLabel);

        if (functions.stream().anyMatch(f -> f.getName().equals("add"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(1, 120, 220));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            roundedPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    new AddMaterialGUI();
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
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(1, 120, 220));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Nhập Excel");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setForeground(Color.white);
            panel.setIcon(new FlatSVGIcon("icon/import.svg"));
            roundedPanel.add(panel);
            roundedPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    File file = chooseExcelFile(null);
                    if (file != null) {
                        Pair<Boolean, String> result;
                        try {
                            result = new AddMaterialFromExcel().addMaterialFromExcel(file);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (!result.getKey()) {
                            JOptionPane.showMessageDialog(null, result.getValue(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, result.getValue(),
                                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            refresh();
                        }
                    }
                    refresh();
                }
            });
        }
        if (functions.stream().anyMatch(f -> f.getName().equals("pdf"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(1, 120, 220));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Xuất PDF");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setForeground(Color.white);
            panel.setIcon(new FlatSVGIcon("icon/export.svg"));
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    String exportFolderPath = "Export\\PDF";
                    PDF.exportMaterialPDF(materialBLL.getData(materialBLL.searchMaterials("deleted = 0")), exportFolderPath);

                }
            });
            roundedPanel.add(panel);
        }


    }

    public void refresh() {
        for (Enumeration<AbstractButton> buttons = btgroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.getText().equals("Tất cả")) {
                button.setSelected(true);
                break;
            }
        }
        jTextFieldSearch.setText("");
        loadDataTable(materialBLL.getData(materialBLL.searchMaterials("deleted = 0")));
    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        if (objects.length == 0) {
            return;
        }

        Object[][] data = new Object[objects.length][5];

        for (int i = 0; i < objects.length; i++) {
            data[i][0] = objects[i][0];
            data[i][1] = objects[i][1];
            data[i][2] = objects[i][2];
            data[i][3] = objects[i][3];
            data[i][4] = objects[i][4];

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

        if (indexColumn == indexColumnDetail)
            new DetailMaterialGUI(materialBLL.searchMaterials("deleted = 0").get(indexRow));

        if (edit && indexColumn == indexColumnEdit) {
            new EditMaterialGUI(materialBLL.searchMaterials("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
            refresh();
        }

        if (indexColumn == indexColumnRemove) {
            deleteMaterial(materialBLL.searchMaterials("deleted = 0").get(indexRow));
            refresh();
        }
    }

    private void deleteMaterial(Material material) {
        if (dataTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nguyên liệu cần xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] options = new String[]{"Huỷ", "Xác nhận"};
        int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá nguyên liệu?", "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
        if (choice == 1) {
            Pair<Boolean, String> result = materialBLL.deleteMaterial(material);
            if (result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, result.getValue(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
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
        if (status.equals("Dưới định mức tồn")) {
            materialList = materialBLL.searchMaterials("remain < min_remain");
        }
        if (status.equals("Vượt định mức tồn")) {
            materialList = materialBLL.searchMaterials("remain > max_remain ");
        }
        if (status.equals("Còn hàng")) {
            materialList = materialBLL.searchMaterials("remain > 0");
        }
        if (status.equals("Hết hàng")) {
            materialList = materialBLL.searchMaterials("remain = 0");
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