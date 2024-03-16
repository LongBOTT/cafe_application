package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.components.RoundedPanel;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.compress.harmony.pack200.NewAttribute;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class DetailProductGUI extends DialogFormDetail_1 {

    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;
    private JLabel lblNameProduct;
    private JLabel lblPrice;
    private  JLabel lblCategory;
    private JLabel lblSize;
    private JLabel lblImage;
    private JTextField txtNameProduct;
    private JTextField txtPrice;
    private JTextField txtSize;
    private JTextField txtCategory;
    private JButton btnImage;
    private List<String> AtributeProduct=  new ArrayList<>();
    public DetailProductGUI() {
        super();
        init();
    }

    public void init() {
        titleName = new JLabel("Chi tiết sản phẩm");
        title.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        title.add(titleName);

        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(150, 50));
        lblImage = new JLabel("Imange");
        lblImage.setPreferredSize(new Dimension(200, 200));

        top.setLayout(new GridBagLayout());

        containerAtributeProduct = new RoundedPanel();
        containerAtributeProduct.setLayout(new MigLayout("", "[]10[]", "[][][]"));
        containerAtributeProduct.setBackground(new Color(217,217,217));
        containerAtributeProduct.setPreferredSize(new Dimension(600, 200));

        top.add(containerAtributeProduct);
        containerImage = new RoundedPanel();
        containerImage.setLayout(new MigLayout("", "[]", "[]10[]"));

        containerImage.setBackground(new Color(217,217,217));
        containerImage.setPreferredSize(new Dimension(300, 200));
        containerImage.add(btnImage);
        containerImage.add(lblImage);
        lblImage.setBackground(new Color(0,0,0));
        top.add(containerImage);
        GridBagConstraints gbcAtributeProduct = new GridBagConstraints();
        gbcAtributeProduct.fill = GridBagConstraints.BOTH;
        gbcAtributeProduct.gridx = 0;
        gbcAtributeProduct.gridy = 0;
        gbcAtributeProduct.weightx = 1.0;
        gbcAtributeProduct.weighty = 1.0;
        gbcAtributeProduct.insets = new Insets(0, 0, 0, 10); // Khoảng cách bên phải

        // Thêm containerAtributeProduct vào top với constraints
        top.add(containerAtributeProduct, gbcAtributeProduct);

        // Thiết lập constraints cho containerImage
        GridBagConstraints gbcImage = new GridBagConstraints();
        gbcImage.fill = GridBagConstraints.BOTH;
        gbcImage.gridx = 1;
        gbcImage.gridy = 0;
        gbcImage.weightx = 1.0;
        gbcImage.weighty = 1.0;
        // Đặt weightx giống containerAtributeProduct
        gbcImage.insets = new Insets(0, 0, 0, 0); // Không có khoảng cách

        // Thêm containerImage vào top với constraints
        top.add(containerImage, gbcImage);


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
//                textField.setText(AtributeProduct.get(1));
            }
            if (string.equals("Giá bán")) {
//                textField.setText(AtributeProduct.get(2));
            }
            if (string.equals("Loại")) {
//                textField.setText(AtributeProduct.get(3));
            }

            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(250, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
//            jTextFieldSupplier.add(textField);
            containerAtributeProduct.add(textField,"wrap");

        }

    }
}
