package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.components.RoundedPanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DetailProductGUI extends DialogFormDetail_1 {

    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;

    private JButton btnImage;
    private JLabel lblListMaterial;
    private JLabel material;

    private JComboBox<String> CbListMaterial;
    private List<String> AtributeProduct = new ArrayList<>();

    public DetailProductGUI() {
        super();
        super.setTitle("Thông tin nhà cung cấp");
        init();
        setVisible(true);

    }

    public void init() {

//        super.init();

        titleName = new JLabel("Chi tiết sản phẩm");
        title.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        title.add(titleName);

        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(100, 30));

        ImageIcon icon = new FlatSVGIcon("image/Product/" + "SP01" + ".svg");
        JLabel lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(200, 150));
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        lblImage.setIcon(icon);
//

        top.setLayout(new GridBagLayout());

        containerAtributeProduct = new RoundedPanel();
        containerAtributeProduct.setLayout(new MigLayout("", "[]30[]30", "[]20[]20[]"));
//        containerAtributeProduct.setBackground(new Color(217,217,217));



        containerImage = new RoundedPanel();
        containerImage.setLayout(new MigLayout("", "[]", "[][]"));

        containerImage.add(lblImage, "alignx center, wrap");
        containerImage.add(btnImage, "alignx center");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 50, 0, 200); // Khoảng cách bên phải

        top.add(containerAtributeProduct, gbc);

        gbc.gridx++;
        gbc.weightx = 0.0;

        gbc.insets = new Insets(0, 0, 0, 0); // Không có khoảng cách

        top.add(containerImage, gbc);

        for (String string : new String[]{"Tên sản phẩm", "Size", "Giá bán", "Loại"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            containerAtributeProduct.add(label);

            JTextField textField = new JTextField();

            if (string.equals("Tên sản phẩm")) {
//                textField.setText(AtributeProduct.get(0));
            }
            if (string.equals("Size")) {
                JComboBox<String> size = new JComboBox();
//                textField.setText(AtributeProduct.get(1));
                size.setPreferredSize(new Dimension(350, 30));
                size.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                size.setBackground(new Color(245, 246, 250));
                containerAtributeProduct.add(size, "wrap");
                continue;
            }
            if (string.equals("Giá bán")) {
//                textField.setText(AtributeProduct.get(2));
            }
            if (string.equals("Loại")) {
//                textField.setText(AtributeProduct.get(3));
            }

            textField.setEditable(false);
            textField.setEnabled(false);
            textField.setPreferredSize(new Dimension(350, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            containerAtributeProduct.add(textField, "wrap");

        }
        lblListMaterial = new JLabel("Danh sách nguyên liệu");

        lblListMaterial.setFont(new Font("Public Sans", Font.BOLD, 16));

        center.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        center.add(lblListMaterial);
//        for (String string : new String[]{"Tên nguyên liệu", "Số lượng", "Đơn vị", "Thêm"}) {
//            JLabel label = new JLabel();
//            label.setPreferredSize(new Dimension(170, 30));
//            label.setText(string);
//            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
//            containerAtributeProduct.add(label);
//
//            JTextField textField = new JTextField();
//
//            if (string.equals("Tên sản phẩm")) {
////                textField.setText(AtributeProduct.get(0));
//            }
//            if (string.equals("Size")) {
//                JComboBox<String> size = new JComboBox();
////                textField.setText(AtributeProduct.get(1));
//                size.setPreferredSize(new Dimension(350, 30));
//                size.setFont((new Font("Public Sans", Font.PLAIN, 14)));
//                size.setBackground(new Color(245, 246, 250));
//                containerAtributeProduct.add(size, "wrap");
//                continue;
//            }
//            if (string.equals("Giá bán")) {
////                textField.setText(AtributeProduct.get(2));
//            }
//            if (string.equals("Loại")) {
////                textField.setText(AtributeProduct.get(3));
//            }
//
////            textField.setEditable(false);
////            textField.setEnabled(false);
////            textField.setPreferredSize(new Dimension(350, 30));
////            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
////            textField.setBackground(new Color(245, 246, 250));
////            containerAtributeProduct.add(textField,"wrap");
//
//        }

    }
}
