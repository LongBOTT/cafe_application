package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Material;
import com.coffee.GUI.DialogGUI.DialogForm;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DetailMaterialGUI extends DialogForm {
    private JLabel titleName;
    private java.util.List<JLabel> attributeMaterial;
    private List<JTextField> jTextFieldMaterial;
    private SupplierBLL supplierBLL = new SupplierBLL();
    public DetailMaterialGUI(Material material) {
        super();
        super.setTitle("Chi tiết nguyên liệu");
        init(material);
        setVisible(true);
    }

    private void init(Material material) {
        titleName = new JLabel();
        attributeMaterial = new ArrayList<>();
        jTextFieldMaterial = new ArrayList<>();

        content.setLayout(new MigLayout("",
                "200[]20[]20[]200",
                "20[]20[]20"));

        titleName.setText("Chi tiết nguyên liệu");
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
            JTextField textField = new JTextField();
            textField.setFocusable(false);
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldMaterial.add(textField);
            content.add(textField, "wrap");
        }

        jTextFieldMaterial.get(0).setText(material.getName());
        jTextFieldMaterial.get(1).setText(String.valueOf(material.getRemain()));
        jTextFieldMaterial.get(2).setText(material.getUnit());
        jTextFieldMaterial.get(3).setText(supplierBLL.findSuppliers("id",String.valueOf(material.getSupplier_id())).get(0).getName());

    }

}
