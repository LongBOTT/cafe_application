package com.coffee.GUI.DialogGUI.FromEditGUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Material;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddSupplierGUI;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class EditMaterialGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeMaterial;
    private List<JTextField> jTextFieldMaterial;
    private JButton buttonCancel;
    private JButton buttonAdd;
    private JComboBox<String> listSupplier;
    private MaterialBLL materialBLL = new MaterialBLL();
    private SupplierBLL supplierBLL = new SupplierBLL();
    private  Material material;
    public EditMaterialGUI(Material material) {
        super();
        super.setTitle("Sửa nguyên liệu");
        this.material = material;
        init(material);
        setVisible(true);
    }

    private void init(Material material) {
        titleName = new JLabel();
        attributeMaterial = new ArrayList<>();
        jTextFieldMaterial = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Cập nhật");
        content.setLayout(new MigLayout("",
                "200[]20[]20[]200",
                "20[]20[]20"));

        titleName.setText("Thêm nguyên liệu");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Tên nguyên liệu", "Số lượng", "Đơn vị", "Nhà cung cấp"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeMaterial.add(label);
            content.add(label);
            if(string.equals("Nhà cung cấp")){
                listSupplier = new JComboBox<>();
                listSupplier.setPreferredSize(new Dimension(1000, 30));
                listSupplier.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                listSupplier.setBackground(new Color(245, 246, 250));
                content.add(listSupplier);

                int Supplier_id = material.getSupplier_id();
                Object[][] objects = supplierBLL.getData(supplierBLL.searchSuppliers("deleted = 0"));
                loadDataSupplier(objects,Supplier_id);
                JButton btnThem = new JButton("Thêm NCC");
                btnThem.setPreferredSize(new Dimension(70, 30));
                btnThem.setBackground(new Color(0, 182, 62));
                btnThem.setFont(new Font("Public Sans", Font.BOLD, 16));
                btnThem.setForeground(Color.WHITE);
                btnThem.setCursor(new Cursor(Cursor.HAND_CURSOR));

                btnThem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        new AddSupplierGUI();
                        Object[][] objects = supplierBLL.getData(supplierBLL.searchSuppliers("deleted = 0"));
                        int id = supplierBLL.getAutoID(supplierBLL.searchSuppliers("deleted = 0"));
                        loadDataSupplier(objects,id-1);
                    }
                });
                content.add(btnThem,"wrap");
                continue;
            }
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldMaterial.add(textField);
            content.add(textField, "wrap");
        }

        jTextFieldMaterial.get(0).setText(material.getName());
        jTextFieldMaterial.get(1).setText(String.valueOf(material.getRemain()));
        jTextFieldMaterial.get(2).setText(material.getUnit());

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

        buttonAdd.setPreferredSize(new Dimension(100, 30));
        buttonAdd.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                updateMaterial();
            }
        });
        containerButton.add(buttonAdd);
    }

    private void updateMaterial() {
        Pair<Boolean, String> result;
        int id;
        String name, unit;
        int id_Supplier;
        Double remain;

        id = material.getId();
        name = jTextFieldMaterial.get(0).getText();
        result = materialBLL.validateQuantity(jTextFieldMaterial.get(1).getText());
        remain = Double.parseDouble(jTextFieldMaterial.get(1).getText());
        if(!result.getKey()){
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        unit = jTextFieldMaterial.get(2).getText();
        String supplier = listSupplier.getSelectedItem().toString();
        String[] parts = supplier.split(" - ");
        id_Supplier = Integer.parseInt(parts[0]);

        Material newMaterial = new Material(id, name, id_Supplier, remain, unit, false);
        result = materialBLL.updateMaterial(newMaterial,material);
        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadDataSupplier(Object[][] objects, int SupplierId) {
        listSupplier.removeAllItems();

        for (Object[] object : objects) {
            String id = object[0].toString();
            String name = object[1].toString();
            String itemString = id + " - " + name;

            listSupplier.addItem(itemString);

            if (id.equals(String.valueOf(SupplierId))) {
                listSupplier.setSelectedItem(itemString);
            }
        }
    }
}
