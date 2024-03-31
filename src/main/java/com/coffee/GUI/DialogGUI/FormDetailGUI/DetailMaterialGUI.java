package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.*;
import com.coffee.DTO.Material;
import com.coffee.DTO.Shipment;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogFormDetail;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DetailMaterialGUI extends DialogFormDetail {
    private JLabel titleName;
    private JComboBox<String> jComboBoxRemain;
    private JComboBox<String> jComboBoxSupplier;
    private List<JLabel> attributeMaterial;
    private ShipmentBLL shipmentBLL = new ShipmentBLL();
    private DataTable dataTable;
    private String[] columnNames;
    private RoundedScrollPane scrollPane;
    private Material material;

    public DetailMaterialGUI(Material material) {
        super();
        super.setTitle("Thông tin nguyên liệu");
        super.setSize(new Dimension(1100, 700));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        this.material = material;
        init(material);
        setVisible(true);
    }

    private void init(Material material) {
        titleName = new JLabel();
        jComboBoxRemain = new JComboBox<>(new String[]{"Tất cả", "Còn hàng", "Hết hàng"});
        jComboBoxSupplier = new JComboBox<>();
        attributeMaterial = new ArrayList<>();
        contenttop.setLayout(new MigLayout("",
                "50[]20[]50",
                "10[]10[]10"));

        super.remove(contentbot);
        super.remove(containerButton);

        titleName.setText("Thông tin Nguyên Liệu");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Mã Nguyên Liệu", "Tên Nguyên Liệu", "Tồn Kho", "Tồn Kho Tối Thiểu", "Tồn Kho Tối Đa", "Đơn Vị", "Giá Vốn"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.BOLD, 16)));
            attributeMaterial.add(label);
            contenttop.add(label);
            JLabel textField = new JLabel();
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));

            if (string.trim().equals("Mã Nguyên Liệu")) {
                textField.setText(String.valueOf(material.getId()));
            }
            if (string.trim().equals("Tên Nguyên Liệu")) {
                textField.setText(material.getName());
            }
            if (string.trim().equals("Tồn Kho")) {
                textField.setText(String.valueOf(material.getRemain()));
            }
            if (string.trim().equals("Tồn Kho Tối Thiểu")) {
                textField.setText(String.valueOf(material.getMinRemain()));
            }
            if (string.trim().equals("Tồn Kho Tối Đa")) {
                textField.setText(String.valueOf(material.getMaxRemain()));
            }
            if (string.trim().equals("Đơn Vị")) {
                textField.setText(material.getUnit());
            }
            if (string.trim().equals("Giá Vốn")) {
                textField.setText(String.valueOf(material.getUnit_price()));
            }
            contenttop.add(textField, "wrap");

        }

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(170, 30));
        label.setText("Trạng thái lô hàng");
        label.setFont((new Font("Public Sans", Font.PLAIN, 15)));
        contenttop.add(label);

        jComboBoxRemain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchShipments();
            }
        });
        contenttop.add(jComboBoxRemain);

        JLabel jlabel = new JLabel();
        jlabel.setPreferredSize(new Dimension(170, 30));
        jlabel.setText("Nhà cung cấp");
        jlabel.setFont((new Font("Public Sans", Font.PLAIN, 15)));
        contenttop.add(jlabel);

        jComboBoxSupplier.addItem("Tất cả");
        for (Supplier supplier : new SupplierBLL().searchSuppliers("deleted = 0"))
            jComboBoxSupplier.addItem(supplier.getName());
        jComboBoxSupplier.setPreferredSize(new Dimension(200, 30));
        jComboBoxSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchShipments();
            }
        });
        contenttop.add(jComboBoxSupplier);

        columnNames = new String[]{"Mã Lô", "Nguyên Liệu", "Tên NCC", "SL Nhập", "SL Tồn", "MFG", "EXP",};
        dataTable = new DataTable(new Object[0][0], columnNames);
        dataTable.getColumnModel().getColumn(0).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(1).setMaxWidth(500);
        dataTable.getColumnModel().getColumn(2).setMaxWidth(500);
        dataTable.getColumnModel().getColumn(3).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(4).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(5).setMaxWidth(250);
        dataTable.getColumnModel().getColumn(6).setMaxWidth(250);
        dataTable.setRowHeight(60);
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        contentmid.add(scrollPane, BorderLayout.CENTER);

        loadDataTable(shipmentBLL.getData(shipmentBLL.findShipmentsBy(Map.of("material_id", material.getId()))));
    }

    private void searchShipments() {
        List<Shipment> shipmentList = shipmentBLL.findShipmentsBy(Map.of("material_id", material.getId()));
        if (jComboBoxRemain.getSelectedIndex() == 1) {
            shipmentList.removeIf(shipment -> shipment.getRemain() == 0);
        }
        if (jComboBoxRemain.getSelectedIndex() == 2) {
            shipmentList.removeIf(shipment -> shipment.getRemain() > 0);
        }
        if (jComboBoxSupplier.getSelectedIndex() != 0) {
            shipmentList.removeIf(shipment -> shipment.getSupplier_id() != jComboBoxSupplier.getSelectedIndex());
        }
        loadDataTable(shipmentBLL.getData(shipmentList));
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


        }

        for (Object[] object : data) {
            Object[] objects1 = object;
            System.arraycopy(objects1, 0, object, 0, 3);
            System.arraycopy(objects1, 4, object, 3, 4);
            object = Arrays.copyOfRange(object, 0, 7);
            model.addRow(object);
        }
    }
}
