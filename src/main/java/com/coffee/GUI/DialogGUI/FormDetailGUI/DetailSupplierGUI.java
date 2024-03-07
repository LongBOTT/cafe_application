package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DetailSupplierGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeSupplier;
    private List<JTextField> jTextFieldSupplier;
    private SupplierBLL supplierBLL = new SupplierBLL();

    public DetailSupplierGUI(Supplier supplier) {
        super();
        super.setTitle("Thông tin nhà cung cấp");
        init(supplier);
        setVisible(true);
    }

    private void init(Supplier supplier) {
        titleName = new JLabel();
        attributeSupplier = new ArrayList<>();
        jTextFieldSupplier = new ArrayList<>();
        content.setLayout(new MigLayout("",
                "200[]20[]200",
                "20[]20[]20"));

        titleName.setText("Thông tin nhà cung cấp");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Tên nhà cung cấp", "Điện thoại", "Địa chỉ", "Email"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeSupplier.add(label);
            content.add(label);

            JTextField textField = new JTextField();

            if (string.equals("Tên nhà cung cấp")) {
                textField.setText(supplier.getName());
            }
            if (string.equals("Điện thoại")) {
                textField.setText(supplier.getPhone());
            }
            if (string.equals("Địa chỉ")) {
                textField.setText(supplier.getAddress());
            }
            if (string.equals("Email")) {
                textField.setText(supplier.getEmail());
            }
            
            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldSupplier.add(textField);
            content.add(textField, "wrap");

        }
    }
}

