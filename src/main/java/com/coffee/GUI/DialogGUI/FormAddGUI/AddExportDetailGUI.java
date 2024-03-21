package com.coffee.GUI.DialogGUI.FormAddGUI;

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

public class AddExportDetailGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeSupplier;
    private List<JTextField> jTextFieldExDetail;

    private JComboBox<String> jComboBoxSearch;
    private JButton buttonCancel;
    private JButton buttonAdd;

    private JButton buttonAddSupplier ;
    private SupplierBLL supplierBLL = new SupplierBLL();

    public AddExportDetailGUI() {
        super();
        super.setTitle("Thêm Phiếu Nhập");
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        attributeSupplier = new ArrayList<>();
        jTextFieldExDetail = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        content.setLayout(new MigLayout("",
                "150[]20[]10",
                "10[]10[]"));

        titleName.setText("Thêm Phiếu Nhập");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);



        for (String string : new String[]{"Mã Phiếu Nhập ", "Nhà Cung Cấp ","Số Lượng ", "Ghi Chú"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(180, 35));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeSupplier.add(label);
            content.add(label);

            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(250, 35));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 15)));
            textField.setBackground(new Color(245, 246, 250));

            if(string.trim().equals("Nhà Cung Cấp")) {
                List<Supplier> suppliers = new SupplierBLL().searchSuppliers();
                List<String> supplierNames = new ArrayList<>();

                for (Supplier supplier : suppliers) {
                    supplierNames.add(supplier.getName());
                }

                jComboBoxSearch = new JComboBox<>(supplierNames.toArray(new String[0]));
                jComboBoxSearch.setBackground(new Color(255, 255, 255));
                jComboBoxSearch.setForeground(Color.black);
                jComboBoxSearch.setPreferredSize(new Dimension(250, 35));

                content.add(jComboBoxSearch);


                buttonAddSupplier = new JButton("Thêm Mới");
                buttonAddSupplier.setPreferredSize(new Dimension(100, 30));
                buttonAddSupplier.setFont(new Font("Lexend", Font.BOLD, 15));
                buttonAddSupplier.setCursor(new Cursor(Cursor.HAND_CURSOR));
                buttonAddSupplier.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        new AddSupplierGUI();
                    }
                });
                content.add(buttonAddSupplier, "wrap");
                continue;

            } else {
                if (string.trim().equals("Mã Phiếu Nhập"))
                {

                }
                if (string.trim().equals("Số Lượng"))
                {

                }
                if (string.trim().equals("Ghi Chú"))
                {

                }

            }
            jTextFieldExDetail.add(textField);
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
//        buttonAdd.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                addSupplier();
//            }
//        });
        containerButton.add(buttonAdd);
    }

//    private void addSupplier() {
//        Pair<Boolean, String> result;
//        int id;
//        String name, phone, address, email;
//
//        id = supplierBLL.getAutoID(supplierBLL.searchSuppliers("deleted = 0")); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
//        name = jTextFieldSupplier.get(0).getText();
//        phone = jTextFieldSupplier.get(1).getText();
//        address = jTextFieldSupplier.get(2).getText();
//        email = jTextFieldSupplier.get(3).getText();
//
//        Supplier supplier = new Supplier(id, name, phone, address, email, false); // false là tồn tại, true là đã xoá
//
//        result = supplierBLL.addSupplier(supplier);
//
//        if (result.getKey()) {
//            JOptionPane.showMessageDialog(null, result.getValue(),
//                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//            dispose();
//        } else {
//            JOptionPane.showMessageDialog(null, result.getValue(),
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
}
