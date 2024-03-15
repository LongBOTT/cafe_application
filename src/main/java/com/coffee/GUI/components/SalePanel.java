package com.coffee.GUI.components;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class SalePanel extends RoundedPanel {
    public RoundedPanel left;
    public RoundedPanel right;
    public RoundedPanel top;
    public RoundedPanel center;
    public RoundedPanel bottom;
    public RoundedPanel ProductPanel;
    public RoundedPanel Category;
    public RoundedPanel BillPanel;
    public JPanel StaffPanel;
    public JPanel Bill_detailPanel;
    public RoundedPanel SearchPanel;
    public JPanel Title;
    public JPanel ContainerButtons;
    public RoundedPanel ContainerBill_detail;
    public RoundedPanel ContainerProduct;
    public RoundedScrollPane[] scrollPane;

    public SalePanel() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setBackground(new Color(0xFFFFFF));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1165, 733));

        left = new RoundedPanel();
        right = new RoundedPanel();
        top = new RoundedPanel();
        center = new RoundedPanel();
        bottom = new RoundedPanel();
        ProductPanel = new RoundedPanel();
        Category = new RoundedPanel();
        ContainerBill_detail = new RoundedPanel();
        ContainerProduct = new RoundedPanel();
        Bill_detailPanel = new JPanel();
        BillPanel = new RoundedPanel();
        StaffPanel = new JPanel();
        SearchPanel = new RoundedPanel();
        Title = new JPanel();
        ContainerButtons = new JPanel();
        scrollPane = new RoundedScrollPane[3];
        scrollPane[0] = new RoundedScrollPane(Category, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane[1] = new RoundedScrollPane(ContainerProduct, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane[2] = new RoundedScrollPane(Bill_detailPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        for (RoundedScrollPane roundedScrollPane : scrollPane) {
            roundedScrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        }

        left.setLayout(new BorderLayout());
        left.setPreferredSize(new Dimension(700, 760));
        left.setBackground(Color.white);
        add(left, BorderLayout.WEST);

        right.setLayout(new BorderLayout());
        right.setPreferredSize(new Dimension(450, 760));
        right.setBackground(Color.white);
        add(right, BorderLayout.EAST);

        top.setLayout(new BorderLayout());
        top.setPreferredSize(new Dimension(700, 50));
        left.add(top, BorderLayout.NORTH);

        center.setLayout(new BorderLayout());
        center.setBorder(BorderFactory.createMatteBorder(10, 0, 10, 0, Color.white));
        center.setPreferredSize(new Dimension(700, 85));
        left.add(center, BorderLayout.CENTER);

        bottom.setLayout(new GridBagLayout());
        bottom.setPreferredSize(new Dimension(700, 625));
        bottom.setBackground(new Color(217, 217, 217));
        left.add(bottom, BorderLayout.SOUTH);

        SearchPanel.setLayout(new MigLayout("", "0[]10[]0", ""));
        SearchPanel.setBackground(Color.white);
        top.add(SearchPanel, BorderLayout.CENTER);

        Category.setLayout(new FlowLayout(FlowLayout.CENTER));
        Category.setBackground(new Color(217, 217, 217));
        center.add(scrollPane[0], BorderLayout.CENTER);

        ProductPanel.setLayout(new BorderLayout());
        ProductPanel.setPreferredSize(new Dimension(690, 615));
        ProductPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        ProductPanel.setBackground(new Color(217, 217, 217));
        bottom.add(ProductPanel);

        ContainerProduct.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        ContainerProduct.setBackground(new Color(217, 217, 217));

        scrollPane[1].getViewport().setBackground(new Color(217, 217, 217));
        ProductPanel.add(scrollPane[1], BorderLayout.CENTER);

        StaffPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        StaffPanel.setPreferredSize(new Dimension(450, 50));
        StaffPanel.setBackground(Color.white);
        right.add(StaffPanel, BorderLayout.NORTH);

        BillPanel.setBackground(Color.white);
        BillPanel.setLayout(new BorderLayout());
        BillPanel.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, Color.white));
        BillPanel.setPreferredSize(new Dimension(450, 686));
        right.add(BillPanel, BorderLayout.SOUTH);

        ContainerBill_detail.setBackground(new Color(217, 217, 217));
        BillPanel.add(ContainerBill_detail, BorderLayout.CENTER);

        Title.setLayout(new FlowLayout());
        Title.setBackground(new Color(217, 217, 217));
        Title.setPreferredSize(new Dimension(440, 40));
        ContainerBill_detail.add(Title, BorderLayout.NORTH);

        Bill_detailPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        Bill_detailPanel.setBackground(new Color(217, 217, 217));

        scrollPane[2].setPreferredSize(new Dimension(450, 400));
        ContainerBill_detail.add(scrollPane[2], BorderLayout.CENTER);

        ContainerButtons.setLayout(new MigLayout("", "10[]15[]10", "10[]20[]"));
        ContainerButtons.setPreferredSize(new Dimension(450, 210));
        ContainerButtons.setBackground(new Color(217, 217, 217));
        ContainerBill_detail.add(ContainerButtons, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        new SalePanel();
    }
}
