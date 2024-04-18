package com.coffee.main;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class test {
    private List<DiscountProduct> discountProducts = new ArrayList<>();
    private JPanel containerProductType;

    public test() {
        containerProductType = new JPanel();
        containerProductType.setLayout(new BoxLayout(containerProductType, BoxLayout.Y_AXIS));

        JButton btnAddConditions = new JButton("Thêm điều kiện");
        btnAddConditions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPanelContentProduct();
            }
        });

        JFrame frame = new JFrame("Discount Panel Demo");
        frame.setLayout(new BorderLayout());
        frame.add(containerProductType, BorderLayout.CENTER);
        frame.add(btnAddConditions, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void createPanelContentProduct() {
        JPanel newContainer = new JPanel();
        newContainer.setLayout(new BoxLayout(newContainer, BoxLayout.Y_AXIS));
        newContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JTextField txtProductName = new JTextField(20);
        JTextField txtQuantity = new JTextField(10);
        JTextField txtDiscountPercentage = new JTextField(10);

        JPanel panelProductName = createPanelWithComponents("Tên sản phẩm:", txtProductName);
        JPanel panelQuantity = createPanelWithComponents("Số lượng từ:", txtQuantity);
        JPanel panelDiscountPercentage = createPanelWithComponents("Phần trăm giảm giá:", txtDiscountPercentage);

        JButton btnRemoveProduct = new JButton("Xóa sản phẩm");
        btnRemoveProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                containerProductType.remove(newContainer);
                containerProductType.revalidate();
                containerProductType.repaint();
                // Xóa sản phẩm khỏi danh sách khi loại bỏ panel
                discountProducts.removeIf(product -> product.getProductName().equals(txtProductName.getText()));
            }
        });

        JButton btnAddCondition = new JButton("+ Thêm điều kiện");
        btnAddCondition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPanelSubContentProduct(newContainer);
            }
        });

        newContainer.add(panelProductName);
        newContainer.add(panelQuantity);
        newContainer.add(panelDiscountPercentage);
        newContainer.add(btnRemoveProduct);
        newContainer.add(btnAddCondition);

        // Thêm sản phẩm vào danh sách khi tạo panel mới
        discountProducts.add(new DiscountProduct(txtProductName.getText()));

        containerProductType.add(newContainer);
        containerProductType.revalidate();
        containerProductType.repaint();
    }

    private void createPanelSubContentProduct(JPanel parentPanel) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());

        JTextField txtQuantity = new JTextField(10);
        JTextField txtDiscountPercentage = new JTextField(10);

        JLabel lblQuantity = new JLabel("Số lượng từ:");
        JLabel lblDiscountPercentage = new JLabel("Phần trăm giảm giá:");

        newPanel.add(lblQuantity);
        newPanel.add(txtQuantity);
        newPanel.add(lblDiscountPercentage);
        newPanel.add(txtDiscountPercentage);

        JButton btnRemoveRow = new JButton("Xóa dòng");
        btnRemoveRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentPanel.remove(newPanel);
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        });

        newPanel.add(btnRemoveRow);
        parentPanel.add(newPanel);
        parentPanel.revalidate();
        parentPanel.repaint();
    }

    private JPanel createPanelWithComponents(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new test();
            }
        });
    }

    // Lớp lưu trữ thông tin sản phẩm giảm giá
    private class DiscountProduct {
        private String productName;

        public DiscountProduct(String productName) {
            this.productName = productName;
        }

        public String getProductName() {
            return productName;
        }
    }
}
