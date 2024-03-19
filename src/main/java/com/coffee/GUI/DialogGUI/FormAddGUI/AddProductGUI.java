package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailSupplierGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditSupplierGUI;
import com.coffee.GUI.components.CustomPanelRenderer;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.coffee.utils.VNString;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddProductGUI extends DialogFormDetail_1 {
    private MaterialBLL materialBLL = new MaterialBLL();
    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;

    private JButton btnImage;
    private JLabel lblListMaterial;
    private DataTable dataTable ;
    private JButton buttonCancel;
    private JButton buttonEdit;

    private RoundedPanel  containerInforMaterial;
    private RoundedScrollPane scrollPane;
    private RoundedPanel containerDataTable;
    private JComboBox<String> CbListMaterial;
    private List<String> AtributeProduct = new ArrayList<>();
    private String[] columnNames;
    private JTextField txtNameMaterial;
    private JTextField txtQuantity;
    private JTextField txtUnit;
    private JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/edit.svg"));
    private JLabel iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
    public AddProductGUI() {
        super();
        super.setTitle("Thêm sản phẩm");
        init();
        setVisible(true);

    }

    public void init() {
        titleName = new JLabel("Thêm sản phẩm");
        title.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        title.add(titleName);

        top.setLayout(new FlowLayout());

        containerAtributeProduct = new RoundedPanel();
        containerAtributeProduct.setLayout(new MigLayout("", "[]60[]200", "15[]15[]15[]15"));
        containerAtributeProduct.setBackground(new Color(217, 217, 217));
        containerAtributeProduct.setPreferredSize(new Dimension(600,200));

        containerImage = new RoundedPanel();
        containerImage.setLayout(new MigLayout("", "[]", "[][]"));
        containerImage.setBackground(new Color(217, 217, 217));
        containerImage.setPreferredSize(new Dimension(200, 200));

        JPanel PanelImage = new JPanel();
        PanelImage.setPreferredSize(new Dimension(150,150));
//        PanelImage.setBackground(new Color(217,217,217));

        JLabel lblImage = new JLabel();
        PanelImage.add(lblImage);


        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(100, 30));

        containerImage.add(PanelImage,"alignx center,wrap");
        containerImage.add(btnImage,"alignx center");

        top.add(containerAtributeProduct);
        top.add(containerImage);
        EmptyBorder emptyBorder = new EmptyBorder(0, 30, 0, 0);
        top.setBorder(emptyBorder);

        for (String string : new String[]{"Tên sản phẩm", "Size", "Giá bán", "Loại"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            containerAtributeProduct.add(label);

            JTextField textField = new JTextField();

            if (string.equals("Tên sản phẩm")) {

            }
            if (string.equals("Size")) {

            }
            if (string.equals("Giá bán")) {

            }
            if (string.equals("Loại")) {

            }

            textField.setPreferredSize(new Dimension(350, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            containerAtributeProduct.add(textField, "wrap");

        }
        lblListMaterial = new JLabel("Danh sách nguyên liệu");

        lblListMaterial.setFont(new Font("Public Sans", Font.BOLD, 16));

        center.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        center.add(lblListMaterial);

        containerInforMaterial = new RoundedPanel();
        containerInforMaterial.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        containerInforMaterial.setBackground(new Color(217, 217, 217));

        EmptyBorder emptyBorder1 = new EmptyBorder(0, 30, 0, 0);
        containerInforMaterial.setBorder(emptyBorder1);
        bottom.add(containerInforMaterial, BorderLayout.NORTH);

        JLabel lblNameMaterial = createLabel("Tên nguyên liệu");
        txtNameMaterial = createTextField();
        txtNameMaterial.setPreferredSize(new Dimension(240, 30));

        JLabel lblQuantity = createLabel("Số lượng");
        txtQuantity = createTextField();
        txtQuantity.setPreferredSize(new Dimension(60, 30));

        JLabel lblUnit = createLabel("Đơn vị");
        txtUnit = createTextField();
        txtUnit.setPreferredSize(new Dimension(60, 30));

        JButton btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(100, 40));
        btnThem.setBackground(new Color(0, 182, 62));
        btnThem.setFont(new Font("Public Sans", Font.BOLD, 16));
        btnThem.setForeground(Color.WHITE);
        btnThem.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = dataTable.getSelectedRow();
                if (selectedRow == -1) {
                    addDataToTable();
                } else {
                    updateDataTable(selectedRow);
                }
            }

        });


        containerInforMaterial.add(lblNameMaterial);
        containerInforMaterial.add(txtNameMaterial);
        containerInforMaterial.add(lblQuantity);
        containerInforMaterial.add(txtQuantity);
        containerInforMaterial.add(lblUnit);
        containerInforMaterial.add(txtUnit);
        containerInforMaterial.add(btnThem);

        columnNames = new String[]{"STT","Tên nguyên liệu", "Số lượng", "Đơn vị"};
        columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
        columnNames[ columnNames.length - 1] = "Sửa";
        columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
        columnNames[ columnNames.length - 1] = "Xóa";
        dataTable = new DataTable(new Object[0][0], columnNames,
                e -> selectFunction(),
                false, true, true, 4);
        int[] columnWidths = {50,300, 50, 50};

        for (int i = 0; i < columnWidths.length; i++) {
            dataTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        dataTable.getColumnModel().getColumn(4).setCellRenderer(new CustomPanelRenderer());
        dataTable.getColumnModel().getColumn(5).setCellRenderer(new CustomPanelRenderer());


        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        containerDataTable = new RoundedPanel();
        containerDataTable.setLayout(new BorderLayout());
        containerDataTable.setBackground(new Color(217, 217, 217));
        EmptyBorder emptyBorderTop = new EmptyBorder(20, 0, 0, 0);
        containerDataTable.setBorder(emptyBorderTop);
        containerDataTable.add(scrollPane, BorderLayout.CENTER);
        bottom.add(containerDataTable, BorderLayout.CENTER);

        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();

        JTableHeader jTableHeader = dataTable.getTableHeader();
        jTableHeader.setBackground(new Color(232, 206, 180));

        buttonCancel = new JButton("Huỷ");
        buttonEdit = new JButton("Cập nhật");
        buttonCancel.setPreferredSize(new Dimension(100, 30));
        buttonCancel.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonCancel.setBackground(new Color(0xD54218));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonCancel.setBackground(Color.white);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cancel();
            }
        });
        containerButton.add(buttonCancel);

        buttonEdit.setPreferredSize(new Dimension(100, 30));
        buttonEdit.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });
        containerButton.add(buttonEdit);
    }
    private Pair<Boolean, String> validateMaterial(String name,String quantity,String unit){
       Pair<Boolean, String> result;

        result = materialBLL.validateName(name);
        if(!result.getKey()){
            return new Pair<>(false,result.getValue());
        }
        result = materialBLL.validateQuantity(quantity);
        if(!result.getKey()){
            return new Pair<>(false,result.getValue());
        }
        result = materialBLL.validateUnit(unit);
        if(!result.getKey()){
            return new Pair<>(false,result.getValue());
        }
        return new Pair<>(true,"hợp lệ");
    }
    private void addDataToTable() {

        String name = txtNameMaterial.getText();
        String quantity = txtQuantity.getText();
        String unit = txtUnit.getText();

        Pair<Boolean, String> result = validateMaterial(name,quantity,unit);
        if(!result.getKey()){
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        int nextRowNumber = model.getRowCount() + 1;
        Object[] rowData = {nextRowNumber, name, quantity, unit, iconDetail, iconRemove};
        model.addRow(rowData);

        txtNameMaterial.setText("");
        txtQuantity.setText("");
        txtUnit.setText("");
    }
    private void updateDataTable(int selectedRow) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();

        String quantity = txtQuantity.getText();
        Pair <Boolean,String> result = materialBLL.validateQuantity(quantity);
        if(result.getKey()) {
            model.setValueAt(quantity, selectedRow, 2);
            txtNameMaterial.setText("");
            txtQuantity.setText("");
            txtUnit.setText("");
            txtNameMaterial.setEnabled(true);
            txtUnit.setEnabled(true);
            dataTable.clearSelection();
        }
        else
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    private void selectFunction() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (indexColumn == 4){
            txtNameMaterial.setText(dataTable.getValueAt(indexRow, 1).toString());
            txtQuantity.setText(dataTable.getValueAt(indexRow, 2).toString());
            txtUnit.setText(dataTable.getValueAt(indexRow, 3).toString());
            txtNameMaterial.setEnabled(false);
            txtUnit.setEnabled(false);
        }

        if ( indexColumn == 5) {
            String[] options = new String[]{"Huỷ", "Xác nhận"};
            int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá nguyên liệu?",
                    "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if(choice == 1 )
                model.removeRow(indexRow);
        }
    }
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
        label.setPreferredSize(null);
        return label;
    }
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Public Sans", Font.PLAIN, 14));
        textField.setBackground(new Color(245, 246, 250));
        return textField;
    }
}

