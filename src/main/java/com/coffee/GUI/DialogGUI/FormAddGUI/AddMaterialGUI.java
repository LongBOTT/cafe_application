package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Material;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.SupplierGUI;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMaterialGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeMaterial;
    private List<JTextField> jTextFieldMaterial;
    private JButton buttonCancel;
    private JButton buttonAdd;
    private JComboBox<String> listSupplier;
    private JComboBox<String> listUnit;

    private MaterialBLL materialBLL = new MaterialBLL();
    private SupplierBLL supplierBLL = new SupplierBLL();

    public AddMaterialGUI() {
        super();
        super.setTitle("Thêm nguyên liệu");
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        attributeMaterial = new ArrayList<>();
        jTextFieldMaterial = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        content.setLayout(new MigLayout("",
                "200[]20[]20[]200",
                "20[]20[]20"));

        titleName.setText("Thêm nguyên liệu");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Tên nguyên liệu", "Đơn vị"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeMaterial.add(label);
            content.add(label);
//            if(string.equals("Nhà cung cấp")){
//                listSupplier = new JComboBox<>();
//                listSupplier.setPreferredSize(new Dimension(1000, 30));
//                listSupplier.setFont((new Font("Public Sans", Font.PLAIN, 14)));
//                listSupplier.setBackground(new Color(245, 246, 250));
//                content.add(listSupplier);
//
//                Object[][] objects = supplierBLL.getData(supplierBLL.searchSuppliers("deleted = 0"));
//                loadDataSupplier(objects);
//
//                JButton btnThem = new JButton();
//                ImageIcon icon = new FlatSVGIcon("icon/add.svg");
//                Image image = icon.getImage();
//                Image newImg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
//                icon = new ImageIcon(newImg);
//                btnThem.setIcon(icon);
//                btnThem.setPreferredSize(new Dimension(30, 30));
//                btnThem.setBackground(new Color(0, 182, 62));
//                btnThem.setFont(new Font("Public Sans", Font.BOLD, 16));
//                btnThem.setForeground(Color.WHITE);
//                btnThem.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//                btnThem.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mousePressed(MouseEvent e) {
//                        new AddSupplierGUI();
//                        Object[][] objects = supplierBLL.getData(supplierBLL.searchSuppliers("deleted = 0"));
//                        loadDataSupplier(objects);
//                    }
//                });
//                content.add(btnThem,"wrap");
//                continue;
//            }
            if (string.equals("Đơn vị")) {
                listUnit = new JComboBox<>();
                listUnit.setPreferredSize(new Dimension(1000, 30));
                listUnit.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                listUnit.setBackground(new Color(245, 246, 250));

                String[] units = {"Chọn đơn vị", "kg", "g", "ml", "túi", "cái", "trái", "hạt"};

                for (String unit : units) {
                    listUnit.addItem(unit);
                }
                content.add(listUnit, "wrap");
                continue;
            }
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldMaterial.add(textField);
            content.add(textField, "wrap");

        }


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
                addMaterial();
            }
        });
        containerButton.add(buttonAdd);
    }

    private void addMaterial() {
        Pair<Boolean, String> result;
        int id;
        String name, unit;
//        int id_Supplier;

        id = materialBLL.getAutoID(materialBLL.searchMaterials());
        name = jTextFieldMaterial.get(0).getText();

        unit = listUnit.getSelectedItem().toString();
        if (unit.equals("Chọn đơn vị")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn đơn vị",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String supplier = listSupplier.getSelectedItem().toString();
        if (supplier.equals("Chọn nhà cung cấp")) {
            JOptionPane.showMessageDialog(null, "Chưa chọn nhà cung cấp",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] parts = supplier.split(" - ");
        result = materialBLL.validateSupplier(parts[0]);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
//        id_Supplier = Integer.parseInt(parts[0]);
        Material material = new Material(id, name, 0, unit, false);
        result = materialBLL.addMaterial(material);
        if (result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadDataSupplier(Object[][] objects) {
        Object[][] data = new Object[objects.length][objects[0].length];

        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);
        }
        listSupplier.removeAllItems();
        listSupplier.addItem("Chọn nhà cung cấp");
        listSupplier.setSelectedItem("Chọn nhà cung cấp");
        for (Object[] object : data) {
            String id = object[0].toString();
            String name = object[1].toString();
            listSupplier.addItem(id + " - " + name);
        }
    }

}