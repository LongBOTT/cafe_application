package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.DiscountBLL;
import com.coffee.DTO.Discount;
import com.coffee.DTO.Discount_Detail;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
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
    private List<JLabel> attributeDiscount;

    private List<JTextField> jTextFieldDiscount;
    private JButton buttonCancel;
    private JButton buttonAdd;


    private JDateChooser[] jDateChooser = new JDateChooser[0];

    private  JTextField[] dateTextField = new JTextField[0];

    private  JTextField[] jTextFieldDate = new JTextFieldDateEditor[0];


    public AddDiscountGUI() {
        super();
        super.setTitle("Thêm Khuyến Mãi");
        init();
        setVisible(true);
    }
    private void init() {

        titleName = new JLabel();
        attributeDiscount = new ArrayList<>();
        jTextFieldDiscount = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        content.setLayout(new MigLayout("",
                "200[]20[]200",
                "20[]20[]20"));

        titleName.setText("Thêm Giảm Giá");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 25, 25);
        content.setLayout(flowLayout);

        for (String string : new String[]{"Mã Giảm Giá", "Trạng Thái"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(180, 35));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeDiscount.add(label);
            content.add(label);

            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(180, 35));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldDiscount.add(textField);
            content.add(textField, "wrap");
        }

        jTextFieldDate = new JTextField[2];
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];

        for (int i = 0; i < 2; i++) {
            jTextFieldDate[i] = new JTextField();
            jTextFieldDate[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
            jTextFieldDate[i].setPreferredSize(new Dimension(180, 35));
            jTextFieldDate[i].setAutoscrolls(true);

            jDateChooser[i] = new JDateChooser();
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(new Dimension(180, 35));
            jDateChooser[i].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

            dateTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
            dateTextField[i].setFont(new Font("Lexend", Font.BOLD, 14));

            if (i == 0) {
                JLabel jLabel = new JLabel("Từ Ngày");
                jLabel.setPreferredSize(new Dimension(180, 35));
                jLabel.setFont(new Font("Lexend", Font.BOLD, 14));
                content.add(jLabel);
            } else {
                JLabel jLabel = new JLabel("Đến Ngày");
                jLabel.setPreferredSize(new Dimension(180, 35));
                jLabel.setFont(new Font("Lexend", Font.BOLD, 14));
                content.add(jLabel);
            }
            content.add(jDateChooser[i], "wrap");
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
                addDiscount();
            }
        });
        containerButton.add(buttonAdd);



//
//        buttonCancel.setPreferredSize(new Dimension(100, 30));
//        buttonCancel.setFont(new Font("Public Sans", Font.BOLD, 15));
//        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        buttonCancel.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//                buttonCancel.setBackground(new Color(0xD54218));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                buttonCancel.setBackground(Color.white);
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                cancel();
//            }
//        });
//
//        containerButton.add(buttonCancel);
//
//        buttonAdd.setPreferredSize(new Dimension(100, 30));
//        buttonAdd.setFont(new Font("Public Sans", Font.BOLD, 15));
//        buttonAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        buttonAdd.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//        });
//        containerButton.add(buttonAdd);
    }
    private void addDiscount() {
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
    }
}
