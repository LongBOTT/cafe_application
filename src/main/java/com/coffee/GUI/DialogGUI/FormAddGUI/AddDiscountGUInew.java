package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ProductBLL;
import com.coffee.DTO.Product;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.components.MyTextFieldUnderLine;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.swing.DataSearch;
import com.coffee.GUI.components.swing.EventClick;
import com.coffee.GUI.components.swing.MyTextField;
import com.coffee.GUI.components.swing.PanelSearch;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class AddDiscountGUInew extends DialogFormDetail_1 {

    private JDateChooser[] jDateChooser = new JDateChooser[0];
    private final MaterialBLL materialBLL = new MaterialBLL();
    private ProductBLL productBLL = new ProductBLL();
    private JTextField[] dateTextField = new JTextField[0];

    private JTextField[] jTextFieldDate = new JTextFieldDateEditor[0];
    private JPanel containerForm;
    private JPanel containerProductType = new JPanel();
    private RoundedPanel containerProductTypeContent = new RoundedPanel();
    private JButton btnAddConditions;
    private JTextField txtSearch;
    private JPopupMenu menu;
    private PanelSearch search;
    private JPanel containerBillType = new JPanel();
    private JPanel containerBillTypeContent = new JPanel();
    private JLabel lblForm;
    private JLabel lblFormValue;
    private JLabel lblBuy;
    private JComboBox<String> cbDiscountType;
    private int materialID;
    JScrollPane scrollPane;

    public AddDiscountGUInew() {
        super();
        super.setTitle("Thêm chương trình giảm giá");
        super.getContentPane().setBackground(new Color(255, 255, 255));
        init();
        menu = new JPopupMenu();
        search = new PanelSearch();
        menu.setBorder(BorderFactory.createLineBorder(new Color(164, 164, 164)));
        menu.add(search);
        menu.setFocusable(false);
        search.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menu.setVisible(false);
                txtSearch.setText(data.getText()+" ("+ data.getText1()+")");
                System.out.println("Click Item : " + data.getText());
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {
                search.remove(com);
                menu.setPopupSize(menu.getWidth(), (search.getItemSize() * 35) + 2);
                if (search.getItemSize() == 0) {
                    menu.setVisible(false);
                }
                System.out.println("Remove Item : " + data.getText());
            }
        });
        setVisible(true);
    }

    public void init() {

        content.setBackground(new Color(255, 255, 255));
        top.setLayout(new MigLayout("", "30[]30[]60[]30[]", "[][]"));
        top.setPreferredSize(new Dimension(1000, 100));
        center.setBackground(new Color(255, 255, 255));
        center.setPreferredSize(new Dimension(1000, 70));
        center.setLayout(new MigLayout("", "30[]30[]30[]100[]", "[]"));
        bottom.setBackground(new Color(255, 255, 255));
        bottom.setPreferredSize(new Dimension(1000, 400));

        JLabel lblDiscountCode = createLabel("Mã giảm giá");
        JLabel lblProgramName = createLabel("Tên chương trình");
        JLabel lblWordEffect = createLabel("Hiệu lực từ");
        JLabel lblArrive = createLabel("Đến");
        JLabel lblStatus = createLabel("Trạng thái");

        JTextField txtDiscountCode = new MyTextFieldUnderLine();
        JTextField txtProgramName = new MyTextFieldUnderLine();

        JRadioButton radio1 = new JRadioButton("Kích hoạt");
        JRadioButton radio2 = new JRadioButton("Chưa áp dụng");

        ButtonGroup btgroup = new ButtonGroup();
        btgroup.add(radio1);
        btgroup.add(radio2);

        JPanel panelTimeApplication = new JPanel();
        panelTimeApplication.setPreferredSize(new Dimension(500, 40));
        panelTimeApplication.setLayout(new MigLayout("", "[][]30[][]"));
        panelTimeApplication.setBackground(new Color(255, 255, 255));

        JPanel panelStatus = new JPanel();
        panelStatus.setLayout(new MigLayout("", "[]50[]50[]"));
        panelStatus.setBackground(new Color(255, 255, 255));
        panelStatus.setPreferredSize(new Dimension(500, 40));
        panelStatus.add(lblStatus);
        panelStatus.add(radio1);
        panelStatus.add(radio2);

        jTextFieldDate = new JTextField[2];
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];

        for (int i = 0; i < 2; i++) {
            jTextFieldDate[i] =new MyTextFieldUnderLine();
            jTextFieldDate[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
            jTextFieldDate[i].setPreferredSize(new Dimension(130, 35));
            jTextFieldDate[i].setAutoscrolls(true);

            jDateChooser[i] = new JDateChooser();
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(new Dimension(130, 30));
            jDateChooser[i].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

            dateTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
            dateTextField[i].setFont(new Font("Lexend", Font.BOLD, 14));
            dateTextField[i].setBackground(new Color(245, 246, 250));

            if (i == 0) {
                panelTimeApplication.add(lblWordEffect);
            } else {
                lblArrive.setPreferredSize(new Dimension(30, 30));
                panelTimeApplication.add(lblArrive);
            }
            panelTimeApplication.add(jDateChooser[i]);
        }

        top.add(lblDiscountCode);
        top.add(txtDiscountCode);
        top.add(panelTimeApplication, "wrap");
        top.add(lblProgramName);
        top.add(txtProgramName);
        top.add(panelStatus, "wrap");


        JLabel lblDiscountType = createLabel("Giảm giá theo");
        lblDiscountType.setPreferredSize(new Dimension(100, 30));

        String[] items = {"Sản phẩm","Đơn hàng"};
        cbDiscountType = new JComboBox<>(items);
        cbDiscountType.setBackground(new Color(29, 78, 216));
        cbDiscountType.setForeground(Color.white);
        cbDiscountType.setPreferredSize(new Dimension(100, 30));
        cbDiscountType.addActionListener(e -> {
            SelectDiscountType();
        });

        containerForm = new JPanel();
        containerForm.setLayout(new MigLayout("", "[]20[]", "[]"));
        containerForm.setPreferredSize(new Dimension(300, 40));
        containerForm.setBackground(new Color(255, 255, 255));

        lblForm = createLabel("Hình thức");
        lblFormValue = createLabel("");
        lblFormValue.setFont((new Font("Public Sans", Font.PLAIN, 14)));

        containerForm.add(lblForm);
        lblFormValue.setText("Giảm giá (theo SL mua)");
        containerForm.add(lblFormValue);

        btnAddConditions = new JButton("Thêm điều kiện");
        btnAddConditions.setIcon(new FlatSVGIcon("icon/Add1.svg"));
        btnAddConditions.setPreferredSize(new Dimension(100, 40));
        btnAddConditions.setFont(new Font("Public Sans", Font.PLAIN, 15));
        btnAddConditions.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddConditions.setFocusPainted(false);
        btnAddConditions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPanelContentProduct();
            }
        });

        center.add(lblDiscountType);
        center.add(cbDiscountType);
        center.add(containerForm);
        center.add(btnAddConditions);

        JLabel totalBill = createLabel("Tổng hóa đơn");
        JLabel discount = createLabel("Giảm giá");

        containerBillType.setLayout(new MigLayout("", "30[]30[]", "[]"));
        containerBillType.setBackground(new Color(220, 244, 252));
        containerBillType.setPreferredSize(new Dimension(1000, 50));
        containerBillType.add(totalBill);
        containerBillType.add(discount);

        containerBillTypeContent.setLayout(new MigLayout("", "30[][]", "30[][]"));
//        containerBillTypeContent.setBackground(new Color(255,255,255));
        containerBillTypeContent.setBackground(new Color(217, 217, 217));


        containerProductType.setLayout(new MigLayout("", "[]", ""));

        containerProductType.setBackground(new Color(255,255,255));
        scrollPane = new JScrollPane(containerProductType);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(255,255,255));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(13);
//
        bottom.add(scrollPane, BorderLayout.CENTER);
        createPanelContentProduct();

        JButton buttonCancel = new JButton("Huỷ");
        buttonCancel.setBackground(new Color(213, 50, 77));
        buttonCancel.setForeground(new Color(255, 255, 255));
        buttonCancel.setPreferredSize(new Dimension(100, 35));
        buttonCancel.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
//                buttonCancel.setBackground(new Color(0xD54218));
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                buttonCancel.setBackground(Color.white);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cancel();
            }
        });
        containerButton.add(buttonCancel);
        JButton buttonAdd = new JButton("Thêm");
        buttonAdd.setBackground(new Color(65, 149, 67));
        buttonAdd.setForeground(new Color(255, 255, 255));
        buttonAdd.setPreferredSize(new Dimension(100, 35));
        buttonAdd.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });
        containerButton.add(buttonAdd);
    }

    private JLabel createLabel(String title) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(80, 30));
        label.setText(title);
        label.setFont((new Font("Public Sans", Font.BOLD, 14)));
        return label;
    }

//    private JTextField createTextField() {
//        JTextField textField = new JTextField();
//        textField.setPreferredSize(new Dimension(300, 30));
//        textField.setFont(new Font("Public Sans", Font.PLAIN, 14));
//        textField.setBackground(new Color(255, 255, 255));
//        textField.setOpaque(false);
//
//        Border bottomBorderBlack = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(163, 162, 149));
//        Border bottomBorderGreen = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(75, 172, 77));
//
//        textField.setBorder(bottomBorderBlack);
//
//        textField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                textField.setBorder(bottomBorderGreen); // Khi focus vào, thay đổi màu border thành xanh lá cây
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                textField.setBorder(bottomBorderBlack); // Khi mất focus, thay đổi màu border về màu đen
//            }
//        });
//
//        return textField;
//    }


    private void SelectDiscountType() {
        containerForm.removeAll();
        bottom.removeAll();
        String selectedValue = (String) cbDiscountType.getSelectedItem();
        if (selectedValue.equals("Đơn hàng")) {
            containerForm.add(lblForm);
            lblFormValue.setText("Giảm giá đơn hàng");
            containerForm.add(lblFormValue);
            bottom.add(containerBillType, BorderLayout.NORTH);
            bottom.add(containerBillTypeContent, BorderLayout.CENTER);
        } else {

            containerForm.add(lblForm);
            lblFormValue.setText("Giảm giá (theo SL mua)");
            containerForm.add(lblFormValue);
            bottom.add(scrollPane, BorderLayout.CENTER);
        }
        containerForm.revalidate();
        containerForm.repaint();
        bottom.revalidate();
        bottom.repaint();
    }

    private void createPanelContentProduct() {
        RoundedPanel newContainer = new RoundedPanel();
        newContainer.setLayout(new MigLayout("", "[]"));

//        newContainer.setBackground(Color.CYAN);
//        newContainer.setBackground(new Color(217,255,255));

        newContainer.setPreferredSize(new Dimension(915, 100));
        JLabel lblBuy = createLabel("Khi mua");

        RoundedPanel contaierSearch = new RoundedPanel();
        contaierSearch.setLayout(new MigLayout("","[][]"));
        JButton category = new JButton();
        category.setIcon(new FlatSVGIcon("icon/icons8-category-20.svg"));
       category.setBorderPainted(false);
       category.setFocusPainted(false);
       category.setContentAreaFilled(false);
       category.setCursor(new Cursor(Cursor.HAND_CURSOR));


        JTextField txtSearch = new MyTextFieldUnderLine();
        txtSearch.setPreferredSize(new Dimension(500, 30));
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        contaierSearch.add(txtSearch);
        contaierSearch.add(category);

        JButton btnRemove = new JButton();
        btnRemove.setIcon(new FlatSVGIcon("icon/icons8-remove-26.svg"));
        btnRemove.setBorderPainted(false);
        btnRemove.setFocusPainted(false);
        btnRemove.setContentAreaFilled(false);
        btnRemove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                containerProductType.remove(newContainer);
                containerProductType.revalidate();
                containerProductType.repaint();
            }
        });

        RoundedPanel newContainer1 = new RoundedPanel();
        newContainer1.setLayout(new MigLayout("", "10[]30[]320[]"));
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(225, 225, 225)); // Viền dưới
        newContainer1.setBorder(bottomBorder);
        newContainer1.add(lblBuy);
        newContainer1.add(contaierSearch);
        newContainer1.add(btnRemove);
        newContainer.add(newContainer1, BorderLayout.NORTH);
        createPanelSubContentProduct(newContainer);
        JButton btnAddRow = new JButton(" + Thêm dòng");
        btnAddRow.setForeground(new Color(99, 165, 210));
        btnAddRow.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnAddRow.setBorderPainted(false);
        btnAddRow.setFocusPainted(false);
        btnAddRow.setContentAreaFilled(false);
        btnAddRow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPanelSubContentProduct(newContainer);
            }
        });

        RoundedPanel containerbtnAddRow = new RoundedPanel();
        containerbtnAddRow.setLayout(new FlowLayout(FlowLayout.LEFT,80,0));
        containerbtnAddRow.add(btnAddRow);
        newContainer.add(containerbtnAddRow, BorderLayout.SOUTH);

        containerProductType.add(newContainer, "wrap");
        containerProductType.revalidate();
        containerProductType.repaint();
    }

    private void createPanelSubContentProduct(RoundedPanel newContainer) {
        RoundedPanel newPanel = new RoundedPanel();
        newPanel.setLayout(new MigLayout("", "100[]20[]20[]20[]20[]20[]320[]10"));
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(225, 225, 225));
        newPanel.setBorder(bottomBorder);

        JLabel quantity = createLabel("Số lượng từ");
        JTextField txtQuantity = new MyTextFieldUnderLine();
        JLabel discount = createLabel("Giảm giá");
        JTextField txtValue =new MyTextFieldUnderLine();

        JButton VND = new JButton("VND");
        VND.setPreferredSize(new Dimension(30, 40));
        VND.setBackground(new Color(133, 137, 138));
        VND.setForeground(Color.WHITE);
        VND.setFont(new Font("Times New Roman", Font.BOLD, 14));
        VND.setBorderPainted(false);
        VND.setFocusPainted(false);
        VND.setMargin(new Insets(0, 0, 0, 0));
        VND.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton percent = new JButton(" % ");
        percent.setBackground(new Color(52, 147, 54));
        percent.setForeground(Color.WHITE);
        percent.setFont(new Font("Times New Roman", Font.BOLD, 14));
        percent.setPreferredSize(new Dimension(30, 40));
        percent.setBorderPainted(false);
        percent.setFocusPainted(false);
        percent.setMargin(new Insets(0, 5, 0, 5));
        percent.setCursor(new Cursor(Cursor.HAND_CURSOR));

        VND.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VND.setBackground(new Color(52, 147, 54));
                percent.setBackground(new Color(133, 137, 138));
            }
        });

        percent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                percent.setBackground(new Color(52, 147, 54));
                VND.setBackground(new Color(133, 137, 138));
            }
        });
        newPanel.add(quantity);
        newPanel.add(txtQuantity);
        newPanel.add(discount);
        newPanel.add(txtValue);
        newPanel.add(VND);
        newPanel.add(percent);

        JButton btnRemoveRow = new JButton();
        btnRemoveRow.setIcon(new FlatSVGIcon("icon/icons8-minus-26.svg"));
        btnRemoveRow.setBorderPainted(false);
        btnRemoveRow.setFocusPainted(false);
        btnRemoveRow.setContentAreaFilled(false);
        btnRemoveRow.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRemoveRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newContainer.remove(newPanel);
                newContainer.revalidate();
                newContainer.repaint();
            }
        });

        newPanel.add(btnRemoveRow);

        newContainer.add(newPanel, "wrap ");
        newContainer.revalidate();
        newContainer.repaint();
    }
   private String[] convertTxtSearchToArray(String txtSearchValue) {
        List<String> resultList = new ArrayList<>();
        String[] values = txtSearchValue.split("\\(");

        if (values.length == 2) {
            String name = values[0].trim();
            String size = values[1].replaceAll("\\)", "").trim();
            resultList.add(name);
            resultList.add(size);
        }

        return resultList.toArray(new String[0]);
    }

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {
        if (search.getItemSize() > 0 && !txtSearch.getText().isEmpty()) {
            menu.show(txtSearch, 0, txtSearch.getHeight());
            search.clearSelected();
        }
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_ENTER) {
            String text = txtSearch.getText().trim().toLowerCase();
            search.setData1(search(text));
            if (search.getItemSize() > 0 && !txtSearch.getText().isEmpty()) {
                menu.show(txtSearch, 0, txtSearch.getHeight());
                menu.setPopupSize(450, (search.getItemSize() * 35) + 2);

            } else {
                menu.setVisible(false);
            }
        }

    }

    private java.util.List<DataSearch> search(String text) {
        java.util.List<DataSearch> list = new ArrayList<>();
        List<Product>products = productBLL.findProducts("name", text);

        for (Product p : products) {
            if (list.size() == 7) {
                break;
            }
            list.add(new DataSearch(p.getName(),p.getSize(),p.getPrice().toString()));
        }
        return list;
    }

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            search.keyUp();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            search.keyDown();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = search.getSelectedText();
            txtSearch.setText(text);

        }
        menu.setVisible(false);

    }
}
