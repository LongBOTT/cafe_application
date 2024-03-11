package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.DiscountBLL;
import com.coffee.DTO.Discount;
import com.coffee.DTO.Discount_Detail;
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

public class AddDiscountGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeSupplier;
    private List<JTextField> jTextFieldSupplier;
    private JButton buttonCancel;
    private JButton buttonAdd;

    private DiscountBLL discountBLL = new DiscountBLL();


    public AddDiscountGUI() {
        super();
        super.setTitle("Thêm Khuyến Mãi");
        init();
        setVisible(true);
    }
    private void init() {
        titleName = new JLabel();
        attributeSupplier = new ArrayList<>();
        jTextFieldSupplier = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        content.setLayout(new MigLayout("",
                "200[]20[]200",
                "20[]20[]20"));

        titleName.setText("Thêm Khyến Mãi");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);
        content.setLayout(flowLayout);

        for (String string : new String[]{"Mã Giảm Giá", "Trạng Thái"}) {
            JLabel label = new JLabel(string);
            label.setPreferredSize(new Dimension(170, 40));
            label.setFont(new Font("Public Sans", Font.PLAIN, 16));
            attributeSupplier.add(label);
            content.add(label);

            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(200, 40));
            textField.setFont(new Font("Public Sans", Font.PLAIN, 14));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldSupplier.add(textField);
            content.add(textField);
        }
        jTextFieldSupplier.get(1).setEnabled(false);

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

            }
        });
        containerButton.add(buttonAdd);
    }
//    private void addSupplier() {
//        Pair<Boolean, String> result;
//        int id;
//        String name, phone, address, email;
//
//        id = discountBLL.getAutoID(discountBLL.searchDiscounts());
//        // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
//        name = jTextFieldSupplier.get(0).getText();
//        phone = jTextFieldSupplier.get(1).getText();
//        address = jTextFieldSupplier.get(2).getText();
//        email = jTextFieldSupplier.get(3).getText();
//
//        Discount_Detail discount_detail = new Discount_Detail();
//
//        Supplier supplier = new Supplier(id, name, phone, address, email, false); // false là tồn tại, true là đã xoá
//
//        result = discountBLL.addDiscount(supplier);
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
