package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.DiscountBLL;
import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ProductBLL;
import com.coffee.DTO.Product;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.components.MyTextFieldUnderLine;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.swing.DataSearch;
import com.coffee.GUI.components.swing.EventClick;
import com.coffee.GUI.components.swing.MyTextField;
import com.coffee.GUI.components.swing.PanelSearch;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javafx.util.Pair;
import jdk.jfr.Category;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class AddDiscountGUInew extends DialogFormDetail_1 {
    private DiscountBLL discountBLL = new DiscountBLL();
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
    private JScrollPane scrollPane;
    private JTextField txtDiscountCode ;
    private JTextField txtProgramName ;
    private JRadioButton radio1;
    private JRadioButton radio2;
    private  ButtonGroup btgroup;

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
                txtSearch.setText(data.getText() + " (" + data.getText1() + ")");
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

         txtDiscountCode = new MyTextFieldUnderLine();
         txtProgramName = new MyTextFieldUnderLine();

         radio1 = new JRadioButton("Kích hoạt");
         radio2 = new JRadioButton("Chưa áp dụng");
         btgroup = new ButtonGroup();
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


        jDateChooser = new JDateChooser[2];

        for (int i = 0; i < 2; i++) {
            jDateChooser[i] = new JDateChooser();
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(new Dimension(130, 30));
            jDateChooser[i].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

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

        String[] items = {"Sản phẩm", "Đơn hàng"};
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

        containerProductType.setBackground(new Color(255, 255, 255));
        scrollPane = new JScrollPane(containerProductType);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(255, 255, 255));
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
private void addDiscount(){
        Pair<Boolean, String> result;
        int id;
        String name;
        Date startDate, endDate;
        boolean type;
        String status;

        id = discountBLL.getAutoID(discountBLL.searchDiscounts()); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
        name = txtProgramName.getText();

        startDate = jDateChooser[0].getDate();
        endDate= jDateChooser[1].getDate();

        String typeText = Objects.requireNonNull(cbDiscountType.getSelectedItem()).toString();

       if(typeText.equals("Sản phẩm")){

       }

//        address = jTextFieldSupplier.get(2).getText();
//        email = jTextFieldSupplier.get(3).getText();

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

}
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
        contaierSearch.setLayout(new MigLayout("", "[][]"));
        JButton category = createButton();
        category.setIcon(new FlatSVGIcon("icon/icons8-category-20.svg"));



        String[] categories = productBLL.getCategories().toArray(new String[0]);

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"-- Chọn thể loại --","Tất cả"});
        comboBox.setVisible(false);
        comboBox.setBorder(null);
        comboBox.setBackground(new Color(242, 242, 242));

        for (String c : categories) {
            comboBox.addItem(c);
        }
        category.addActionListener(new ActionListener() {
            boolean isComboBoxVisible = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                isComboBoxVisible = !isComboBoxVisible;

                if (isComboBoxVisible) {
                    Point location = category.getLocationOnScreen();
                    comboBox.setLocation(location.x, location.y +category.getHeight());
                    comboBox.setVisible(true);
                    comboBox.requestFocus();
                } else {
                    comboBox.setVisible(false);
                }
            }
        });


         JTextField txtS = new MyTextFieldUnderLine();

        txtS.setPreferredSize(new Dimension(500, 30));
        txtS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearch = txtS;
                txtSearchMouseClicked(evt);
            }
        });
        txtS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearch = txtS;
                txtSearchKeyPressed(evt);
            }

            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch = txtS;
                txtSearchKeyReleased(evt);
            }
        });
        contaierSearch.add(txtS);
        contaierSearch.add(category);
        contaierSearch.add(comboBox);

        JButton btnRemove = createButton();
        btnRemove.setIcon(new FlatSVGIcon("icon/icons8-remove-26.svg"));

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                containerProductType.remove(newContainer);
                containerProductType.revalidate();
                containerProductType.repaint();
            }
        });

        RoundedPanel newContainer1 = new RoundedPanel();
        newContainer1.setLayout(new MigLayout("", "10[]30[]240[]"));
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(225, 225, 225)); // Viền dưới
        newContainer1.setBorder(bottomBorder);
        newContainer1.add(lblBuy);
        newContainer1.add(contaierSearch);
        newContainer1.add(btnRemove);
        newContainer.add(newContainer1, BorderLayout.NORTH);

        createPanelSubContentProduct(newContainer);
        JButton btnAddRow = createButton();
        btnAddRow.setText(" + Thêm dòng");
        btnAddRow.setForeground(new Color(99, 165, 210));
        btnAddRow.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        btnAddRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPanelSubContentProduct(newContainer);
            }
        });

        RoundedPanel containerbtnAddRow = new RoundedPanel();
        containerbtnAddRow.setLayout(new FlowLayout(FlowLayout.LEFT, 80, 0));
        containerbtnAddRow.add(btnAddRow);
        newContainer.add(containerbtnAddRow, BorderLayout.SOUTH);

        containerProductType.add(newContainer, "wrap");
        containerProductType.revalidate();
        containerProductType.repaint();
    }

    private void createPanelSubContentProduct(RoundedPanel newContainer) {
        RoundedPanel newPanel = new RoundedPanel();
        newPanel.setLayout(new MigLayout("", "100[]20[]20[]20[]20[]390[]10"));
        Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(225, 225, 225));
        newPanel.setBorder(bottomBorder);

        JLabel quantity = createLabel("Số lượng từ");
        JTextField txtQuantity = new MyTextFieldUnderLine();
        JLabel discount = createLabel("Giảm giá");
        JTextField txtValue = new MyTextFieldUnderLine();


        JButton percent = createButton();
        percent.setContentAreaFilled(true);
        percent.setText(" % ");
        percent.setBackground(new Color(52, 147, 54));
        percent.setForeground(Color.WHITE);
        percent.setFont(new Font("Times New Roman", Font.BOLD, 14));
        percent.setPreferredSize(new Dimension(30, 40));
        percent.setMargin(new Insets(0, 5, 0, 5));


        newPanel.add(quantity);
        newPanel.add(txtQuantity);
        newPanel.add(discount);
        newPanel.add(txtValue);
        newPanel.add(percent);

        JButton btnRemoveRow = createButton();
        btnRemoveRow.setIcon(new FlatSVGIcon("icon/icons8-minus-26.svg"));

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
        List<Product> products = productBLL.findProducts("name", text);

        for (Product p : products) {
            if (list.size() == 7) {
                break;
            }
            list.add(new DataSearch(p.getName(), p.getSize(), p.getPrice().toString()));
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
    private JLabel createLabel(String title) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(80, 30));
        label.setText(title);
        label.setFont((new Font("Public Sans", Font.BOLD, 14)));
        return label;
    }
    private JButton createButton(){
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
