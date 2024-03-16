package com.coffee.GUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.SalePanel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class SaleGUI extends SalePanel {
    private final ProductBLL productBLL = new ProductBLL();
    private final StaffBLL staffBLL = new StaffBLL();
    private final RecipeBLL receiptBLL = new RecipeBLL();
    private final Receipt_DetailBLL receipt_detailBLL = new Receipt_DetailBLL();
    private final DiscountBLL discountBLL = new DiscountBLL();
    private final Discount_DetailBLL discountDetailBLL = new Discount_DetailBLL();
    private final Account account;
    private List<Discount_Detail> discountDetails;
    private RoundedPanel containerSearch;
    private List<RoundedPanel> productPanelList;
    private List<JPanel> productIncartPanelList;
    private List<JPanel> productIncartNotelList;
    private JLabel iconSearch;
    private JLabel staffName;
    private JLabel date;
    private List<JLabel> jLabelBill;
    private List<JLabel> nameReceiptDetail;
    private List<JLabel> deleteReceiptDetail;
    private List<JLabel> priceReceiptDetail;
    private List<JComboBox<String>> sizeReceiptDetail;
    private List<JTextField> quantityReceiptDetail;
    private JTextField jTextFieldSearch;
    private JTextField jTextFieldCash;
    private JButton jButtonSearch;
    private JButton jButtonPay;
    private JButton jButtonCancel;
    private List<ButtonGroup> buttonGroupsSugar;
    private List<ButtonGroup> buttonGroupsIce;
    private List<String> categoriesName;
    private List<Integer> productIDList;
    private List<String> productNameList;
    private List<List<Object>> receiptDetailList;
    private List<Material> materials = new MaterialBLL().searchMaterials("deleted = 0");
    private String categoryName = "TẤT CẢ";
    private int indexShowOption = -1;

    public SaleGUI(Account account) {
        super();
        this.account = account;
        initComponents();
    }

    public void initComponents() {
        containerSearch = new RoundedPanel();
        productPanelList = new ArrayList<>();
        productIncartPanelList = new ArrayList<>();
        productIncartNotelList = new ArrayList<>();
        productNameList = new ArrayList<>();
        iconSearch = new JLabel();
        staffName = new JLabel("Nhân viên: " + HomeGUI.staff.getName());
        date = new JLabel("Ngày: " + LocalDate.now());
        jTextFieldSearch = new JTextField();
        jTextFieldCash = new JTextField();
        jLabelBill = new ArrayList<>();
        nameReceiptDetail = new ArrayList<>();
        deleteReceiptDetail = new ArrayList<>();
        priceReceiptDetail = new ArrayList<>();
        quantityReceiptDetail = new ArrayList<>();
        sizeReceiptDetail = new ArrayList<>();
        jButtonSearch = new JButton("Tìm kiếm");
        jButtonPay = new JButton("Thanh toán");
        jButtonCancel = new JButton("Huỷ");
        categoriesName = new ArrayList<>();
        productIDList = new ArrayList<>();
        receiptDetailList = new ArrayList<>();
        buttonGroupsSugar = new ArrayList<>();
        buttonGroupsIce = new ArrayList<>();

        Discount discount = discountBLL.searchDiscounts("status = 0").get(0);
        discountDetails = discountDetailBLL.findDiscount_DetailsBy(Map.of("discount_id", discount.getId()));

        containerSearch.setLayout(new MigLayout("", "10[]20[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(600, 40));
        SearchPanel.add(containerSearch);

        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Nhập tên sản phẩm");
        jTextFieldSearch.setPreferredSize(new Dimension(550, 30));
        jTextFieldSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchProducts();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
        jButtonSearch.addActionListener(e -> searchProducts());
        SearchPanel.add(jButtonSearch);

        loadCategory();

        loadProduct(productBLL.searchProducts("deleted = 0"));

        staffName.setFont((new Font("Palatino", Font.BOLD, 15)));
        StaffPanel.add(staffName);

        date.setFont(new Font("Palatino", Font.PLAIN, 13));
        StaffPanel.add(date);

        List<String> titles = new ArrayList<>(List.of(new String[]{"Sản phẩm", "Size", "SL", "Thành tiền", "Xoá"}));

        for (String tittle : titles) {
            JLabel jLabel = getjLabel(tittle);
            Title.add(jLabel);
        }

        for (String string : new String[]{"Thành tiền:", "Khuyến mãi:", "Tiền nhận:", "Tiền thừa:"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 20));
            label.setText(string);
            label.setFont((new Font("Palatino", Font.PLAIN, 13)));
            ContainerButtons.add(label);

            if (string.equals("Thành tiền:") || string.equals("Khuyến mãi:") || string.equals("Tiền thừa:")) {
                JLabel jLabel = new JLabel();
                jLabel.setPreferredSize(new Dimension(230, 20));
                jLabel.setFont((new Font("Palatino", Font.PLAIN, 13)));
                jLabel.setText("0.0");
                jLabelBill.add(jLabel);
                ContainerButtons.add(jLabel, "wrap");
            } else {
                jTextFieldCash.setPreferredSize(new Dimension(230, 20));
                jTextFieldCash.setFont((new Font("Palatino", Font.PLAIN, 13)));
                jTextFieldCash.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            calculateExcess();
                        }
                    }
                });
                ContainerButtons.add(jTextFieldCash, "wrap");
            }
        }

        jButtonCancel.setPreferredSize(new Dimension(40, 40));
        jButtonCancel.setFont(new Font("Palatino", Font.BOLD, 15));
        jButtonCancel.setBackground(new Color(0xFFFFFF));
        jButtonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButtonCancel.setBackground(new Color(0xD54218));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButtonCancel.setBackground(new Color(0xFFFFFF));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (receiptDetailList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm.",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String[] options = new String[]{"Huỷ", "Xác nhận"};
                int choice = JOptionPane.showOptionDialog(null, "Bạn muốn huỷ hoá đơn?",
                        "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                if (choice == 1)
                    cancelBill();
            }
        });
        ContainerButtons.add(jButtonCancel);

        jButtonPay.setPreferredSize(new Dimension(40, 40));
        jButtonPay.setFont(new Font("Palatino", Font.BOLD, 15));
        jButtonPay.setBackground(new Color(0xB58DDEAF, true));
        jButtonPay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonPay.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String[] options = new String[]{"Huỷ", "Xác nhận"};
                int choice = JOptionPane.showOptionDialog(null, "Xác nhận lập hoá đơn?",
                        "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//                if (choice == 1)
//                    payBill();
            }
        });
        ContainerButtons.add(jButtonPay, "wrap");
    }

    private void searchProducts() {
        if (jTextFieldSearch.getText().isEmpty()) {
            loadProduct(productBLL.searchProducts("deleted = 0"));
        } else {
            loadProduct(productBLL.findProducts("name", jTextFieldSearch.getText()));
        }
        categoryName = "";
    }

    private static JLabel getjLabel(String tittle) {
        JLabel jLabel = new JLabel(tittle);
        jLabel.setFont(new Font("Palatino", Font.BOLD, 13));
        if (Objects.equals(tittle, "Sản phẩm")) {
            jLabel.setPreferredSize(new Dimension(200, 40));
        } else if (Objects.equals(tittle, "Size") || Objects.equals(tittle, "SL") || Objects.equals(tittle, "Xoá")) {
            jLabel.setPreferredSize(new Dimension(40, 40));
        } else {
            jLabel.setPreferredSize(new Dimension(90, 40));
        }
        return jLabel;
    }


    public void loadCategory() {
        Category.removeAll();
        categoriesName.removeAll(categoriesName);
        categoriesName.add("TẤT CẢ");
        categoriesName.addAll(productBLL.getCategories());
        for (String category : categoriesName) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new FlowLayout());
            roundedPanel.setBackground(new Color(253, 143, 143));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            roundedPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    RoundedPanel roundedPanel = (RoundedPanel) e.getComponent();
                    JLabel jLabel = (JLabel) roundedPanel.getComponent(0);
                    if (!categoryName.equals(jLabel.getText())) {
                        categoryName = jLabel.getText();
                        searchCategory();
//                        System.out.println(jLabel.getText());
                    }
                }
            });
            Category.add(roundedPanel);

            JLabel jLabel = new JLabel(category);
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel.setVerticalAlignment(SwingConstants.CENTER);
            jLabel.setFont((new Font("Palatino", Font.PLAIN, 13)));
            roundedPanel.add(jLabel);

            roundedPanel.setPreferredSize(new Dimension(Math.max(jLabel.getPreferredSize().width + 10, 100), 31));

        }
        Category.repaint();
        Category.revalidate();
    }

    private void searchCategory() {
        if (categoryName.equals("TẤT CẢ"))
            loadProduct(productBLL.searchProducts("deleted = 0"));
        else
            loadProduct(productBLL.findProductsBy(Map.of("category", categoryName)));
        jTextFieldSearch.setText("");
    }

    public void loadProduct(List<Product> products) {
        ContainerProduct.removeAll();
        productPanelList.removeAll(productPanelList);
        productIDList.removeAll(productIDList);
        productNameList.removeAll(productNameList);

        for (Product product : products) {
            if (productNameList.contains(product.getName())) {
                continue;
            }
            RoundedPanel panel = new RoundedPanel();
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
            panel.setBackground(Color.white);
            panel.setPreferredSize(new Dimension(155, 260));
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            productPanelList.add(panel);


            ImageIcon icon = new FlatSVGIcon("image/Product/" + product.getImage() + ".svg");
            Image image = icon.getImage();
            Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);

            JLabel productImage = new JLabel(icon);
            productImage.scrollRectToVisible(new Rectangle());
            panel.add(productImage);

            JLabel productName = new JLabel();
            productName.setPreferredSize(new Dimension(150, 30));
            productName.setVerticalAlignment(JLabel.CENTER);
            productName.setHorizontalAlignment(JLabel.CENTER);
            productName.setText(product.getName());
            productName.setFont((new Font("Palatino", Font.BOLD, 13)));
            panel.add(productName);

            JLabel productPrice = new JLabel();
            productPrice.setVerticalAlignment(JLabel.CENTER);
            productPrice.setHorizontalAlignment(JLabel.CENTER);
            productPrice.setFont((new Font("Palatino", Font.BOLD, 10)));
            productPrice.setPreferredSize(new Dimension(150, 30));

            double percent = checkDiscount(product.getId());
            if (percent == 0) {
                productPrice.setText(String.valueOf(product.getPrice()));
                panel.add(productPrice);
            } else {
                double newPrice = product.getPrice() - product.getPrice() * percent / 100;
                productPrice.setText("<html><s>" + product.getPrice() + "</s>\t <span style='color: red'>" + newPrice + "</span></html>");
                panel.add(productPrice);
            }

            JLabel productRemain = new JLabel();
            productRemain.setPreferredSize(new Dimension(150, 30));
            productRemain.setVerticalAlignment(JLabel.CENTER);
            productRemain.setHorizontalAlignment(JLabel.CENTER);
            int remain = checkRemainProduct(product.getId(), product.getSize());
            productRemain.setText("Còn Lại: " + remain);
            productRemain.setFont((new Font("Palatino", Font.BOLD, 10)));
            panel.add(productRemain);

            productNameList.add(product.getName());
            productIDList.add(product.getId());
        }

        for (RoundedPanel panel : productPanelList) {
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    for (int i = 0; i < productPanelList.size(); i++) {
                        if (productPanelList.get(i) == e.getComponent()) {
                            addProductToCart(productBLL.findProductsBy(Map.of("id", productIDList.get(i))).get(0));
                        }
                    }
                }
            });
        }

        for (RoundedPanel panel : productPanelList) {
            ContainerProduct.add(panel);
        }

        ContainerProduct.setPreferredSize(new Dimension(690, productNameList.size() % 4 == 0 ?
                270 * productNameList.size() / 4 :
                270 * (productNameList.size() + (4 - productNameList.size() % 4)) / 4));
        ContainerProduct.repaint();
        ContainerProduct.revalidate();
    }

    private void addProductToCart(Product product) {
        List<Object> receiptDetail = new ArrayList<>();
        receiptDetail.add(product.getName());
        receiptDetail.add(product.getSize());
        receiptDetail.add(Integer.parseInt("1"));
        double percent = checkDiscount(product.getId());
        if (percent == 0) {
            receiptDetail.add(product.getPrice());
        } else {
            double newPrice = product.getPrice() - product.getPrice() * percent / 100;
            receiptDetail.add(newPrice);
        }
        receiptDetailList.add(receiptDetail);

        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jPanel.setBackground(new Color(245, 246, 250));
        jPanel.setPreferredSize(new Dimension(450, 50));
        Bill_detailPanel.add(jPanel);

        productIncartPanelList.add(new JPanel());
        nameReceiptDetail.add(new JLabel());
        sizeReceiptDetail.add(new JComboBox<>());
        quantityReceiptDetail.add(new JTextField());
        priceReceiptDetail.add(new JLabel());
        deleteReceiptDetail.add(new JLabel());

        int index = receiptDetailList.size() - 1;

        nameReceiptDetail.get(index).setFont(new Font("Palatino", Font.PLAIN, 12));
        nameReceiptDetail.get(index).setText("<html>" + receiptDetailList.get(index).get(0) + "</html>");
        nameReceiptDetail.get(index).setPreferredSize(new Dimension(200, 40));
        nameReceiptDetail.get(index).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < nameReceiptDetail.size(); j++) {
                    if (nameReceiptDetail.get(j) == e.getComponent()) {
                        if (j != indexShowOption && !receiptDetailList.get(j).get(1).equals("0")) {
                            showOption(j);
                            indexShowOption = j;
                        } else {
                            if (j == indexShowOption) {
                                loadProductInCart();
                                indexShowOption = -1;
                            }
                        }

                    }
                }
            }
        });
        jPanel.add(nameReceiptDetail.get(index));

        if (!product.getSize().equals("0")) {
            for (Product product1 : productBLL.findProductsBy(Map.of("name", receiptDetailList.get(index).get(0)))) {
                sizeReceiptDetail.get(index).addItem(product1.getSize());
            }
            sizeReceiptDetail.get(index).setPreferredSize(new Dimension(40, 40));
            sizeReceiptDetail.get(index).setFont(new Font("Palatino", Font.PLAIN, 8));
            sizeReceiptDetail.get(index).setSelectedItem(receiptDetailList.get(index).get(1));
            sizeReceiptDetail.get(index).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (int j = 0; j < sizeReceiptDetail.size(); j++) {
                        if (sizeReceiptDetail.get(j) == e.getSource()) {
                            saveSizeChanged(j);
                            return;
                        }
                    }
                }
            });
            jPanel.add(sizeReceiptDetail.get(index));

            receiptDetail.add("100%");
            receiptDetail.add("100%");
        } else {
            sizeReceiptDetail.get(index).addItem("0");
            JLabel panel = new JLabel();
            panel.setFont(new Font("Palatino", Font.PLAIN, 8));
            panel.setPreferredSize(new Dimension(40, 40));
            jPanel.add(panel);
        }

        quantityReceiptDetail.get(index).setFont(new Font("Palatino", Font.PLAIN, 10));
        quantityReceiptDetail.get(index).setPreferredSize(new Dimension(40, 40));
        quantityReceiptDetail.get(index).setText(receiptDetailList.get(index).get(2).toString());

        quantityReceiptDetail.get(index).addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    for (int i = 0; i < quantityReceiptDetail.size(); i++) {
                        if (quantityReceiptDetail.get(i) == e.getComponent()) {
                            saveQuantityChanged(i);
                            return;
                        }
                    }
                }
            }
        });
        jPanel.add(quantityReceiptDetail.get(index));

        priceReceiptDetail.get(index).setText(receiptDetailList.get(index).get(3).toString());
        priceReceiptDetail.get(index).setFont(new Font("Palatino", Font.PLAIN, 12));
        priceReceiptDetail.get(index).setPreferredSize(new Dimension(90, 40));
        jPanel.add(priceReceiptDetail.get(index));

        deleteReceiptDetail.get(index).setFont(new Font("Palatino", Font.PLAIN, 12));
        deleteReceiptDetail.get(index).setPreferredSize(new Dimension(40, 40));
        deleteReceiptDetail.get(index).setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteReceiptDetail.get(index).setIcon(new FlatSVGIcon("icon/delete.svg"));
        deleteReceiptDetail.get(index).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int j = 0; j < deleteReceiptDetail.size(); j++) {
                    if (deleteReceiptDetail.get(j) == e.getComponent()) {
                        deleteProductInCart(j);
                        return;
                    }
                }

            }
        });
        jPanel.add(deleteReceiptDetail.get(index));

        JPanel productInCartNote = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productInCartNote.setPreferredSize(new Dimension(450, 150));
        productInCartNote.setBackground(new Color(245, 246, 250));

        JLabel choosingSugar = new JLabel("Chọn mức đường: ");
        choosingSugar.setFont(new Font("Palatino Sans", Font.PLAIN, 13));
        choosingSugar.setPreferredSize(new Dimension(150, 50));
        productInCartNote.add(choosingSugar);

        JRadioButton radioSugar1 = new JRadioButton("100%");
        radioSugar1.setSelected(true);
        radioSugar1.addActionListener(e -> saveNote(index));
        JRadioButton radioSugar2 = new JRadioButton("70%");
        radioSugar2.addActionListener(e -> saveNote(index));
        JRadioButton radioSugar3 = new JRadioButton("50%");
        radioSugar3.addActionListener(e -> saveNote(index));
        JRadioButton radioSugar4 = new JRadioButton("30%");
        radioSugar4.addActionListener(e -> saveNote(index));
        JRadioButton radioSugar5 = new JRadioButton("0%");
        radioSugar5.addActionListener(e -> saveNote(index));

        ButtonGroup bgSugar = new ButtonGroup();
        bgSugar.add(radioSugar1);
        bgSugar.add(radioSugar2);
        bgSugar.add(radioSugar3);
        bgSugar.add(radioSugar4);
        bgSugar.add(radioSugar5);
        buttonGroupsSugar.add(bgSugar);

        productInCartNote.add(radioSugar1);
        productInCartNote.add(radioSugar2);
        productInCartNote.add(radioSugar3);
        productInCartNote.add(radioSugar4);
        productInCartNote.add(radioSugar5);

        JLabel choosingIce = new JLabel("Chọn mức đá: ");
        choosingIce.setFont(new Font("Palatino Sans", Font.PLAIN, 13));
        choosingIce.setPreferredSize(new Dimension(150, 50));
        productInCartNote.add(choosingIce);

        JRadioButton radioIce1 = new JRadioButton("100%");
        radioIce1.setSelected(true);
        radioIce1.addActionListener(e -> saveNote(index));
        JRadioButton radioIce2 = new JRadioButton("70%");
        radioIce2.addActionListener(e -> saveNote(index));
        JRadioButton radioIce3 = new JRadioButton("50%");
        radioIce3.addActionListener(e -> saveNote(index));
        JRadioButton radioIce4 = new JRadioButton("30%");
        radioIce4.addActionListener(e -> saveNote(index));
        JRadioButton radioIce5 = new JRadioButton("0%");
        radioIce5.addActionListener(e -> saveNote(index));

        ButtonGroup bgIce = new ButtonGroup();
        bgIce.add(radioIce1);
        bgIce.add(radioIce2);
        bgIce.add(radioIce3);
        bgIce.add(radioIce4);
        bgIce.add(radioIce5);
        buttonGroupsIce.add(bgIce);

        productInCartNote.add(radioIce1);
        productInCartNote.add(radioIce2);
        productInCartNote.add(radioIce3);
        productInCartNote.add(radioIce4);
        productInCartNote.add(radioIce5);

        productIncartNotelList.add(productInCartNote);

        Bill_detailPanel.setPreferredSize(new Dimension(450, 400 < receiptDetailList.size() * 55 ? Bill_detailPanel.getHeight() + 55 : 400));
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
        calculateTotal();
    }

    private void saveNote(int index) {
        for (Enumeration<AbstractButton> buttons = buttonGroupsSugar.get(index).getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                receiptDetailList.get(index).set(4, button.getText());
            }
        }

        for (Enumeration<AbstractButton> buttons = buttonGroupsIce.get(index).getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                receiptDetailList.get(index).set(5, button.getText());
            }
        }
    }

    private void showOption(int index) {
        Bill_detailPanel.removeAll();
        productIncartPanelList = new ArrayList<>();

        for (int i = 0; i < receiptDetailList.size(); i++) {
            JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            jPanel.setBackground(new Color(245, 246, 250));
            jPanel.setPreferredSize(new Dimension(450, 50));

            jPanel.add(nameReceiptDetail.get(i));
            if (receiptDetailList.get(i).get(1).equals("0")) {
                JLabel panel = new JLabel();
                panel.setFont(new Font("Palatino", Font.PLAIN, 8));
                panel.setPreferredSize(new Dimension(40, 40));
                jPanel.add(panel);
            } else {
                jPanel.add(sizeReceiptDetail.get(i));
            }
            jPanel.add(quantityReceiptDetail.get(i));
            jPanel.add(priceReceiptDetail.get(i));
            jPanel.add(deleteReceiptDetail.get(i));

            if (i == index) {
                jPanel.add(productIncartNotelList.get(index));
                jPanel.setPreferredSize(new Dimension(450, 200));
            }

            Bill_detailPanel.add(jPanel);
            productIncartPanelList.add(jPanel);
        }
        Bill_detailPanel.setPreferredSize(new Dimension(450, Math.max(400, 55 * receiptDetailList.size() + 150)));
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
    }

    private void saveSizeChanged(int index) {
        List<Object> receipt = receiptDetailList.get(index);
        receipt.set(1, sizeReceiptDetail.get(index).getSelectedItem());

        int quantity = Integer.parseInt(quantityReceiptDetail.get(index).getText());
        Product product = productBLL.findProductsBy(Map.of("name", receipt.get(0),
                "size", Objects.requireNonNull(sizeReceiptDetail.get(index).getSelectedItem()))).get(0);
        double price;
        double percent = checkDiscount(product.getId());
        if (percent == 0) {
            price = product.getPrice();
        } else {
            price = product.getPrice() - product.getPrice() * percent / 100;
        }
        receipt.set(3, quantity * price);

        receiptDetailList.set(index, receipt);

        sizeReceiptDetail.get(index).setSelectedItem(receipt.get(1));
        priceReceiptDetail.get(index).setText(receipt.get(3).toString());
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
        calculateTotal();
    }

    private void saveQuantityChanged(int index) {
        List<Object> receipt = receiptDetailList.get(index);
        receipt.set(2, Integer.parseInt(quantityReceiptDetail.get(index).getText()));

        int quantity = Integer.parseInt(quantityReceiptDetail.get(index).getText());
        Product product = productBLL.findProductsBy(Map.of("name", receipt.get(0),
                "size", Objects.requireNonNull(sizeReceiptDetail.get(index).getSelectedItem()))).get(0);
        double price;
        double percent = checkDiscount(product.getId());
        if (percent == 0) {
            price = product.getPrice();
        } else {
            price = product.getPrice() - product.getPrice() * percent / 100;
        }
        receipt.set(3, quantity * price);

        receiptDetailList.set(index, receipt);
        priceReceiptDetail.get(index).setText(receipt.get(3).toString());
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
        calculateTotal();
    }

    private void deleteProductInCart(int index) {
        receiptDetailList.remove(index);
        nameReceiptDetail.remove(index);
        sizeReceiptDetail.remove(index);
        quantityReceiptDetail.remove(index);
        priceReceiptDetail.remove(index);
        deleteReceiptDetail.remove(index);

        productIncartNotelList.remove(index);

        buttonGroupsSugar.remove(index);
        buttonGroupsIce.remove(index);
        loadProductInCart();
        calculateTotal();
    }

    private void loadProductInCart() {
        Bill_detailPanel.removeAll();
        productIncartPanelList = new ArrayList<>();

        for (int i = 0; i < receiptDetailList.size(); i++) {
            JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            jPanel.setBackground(new Color(245, 246, 250));
            jPanel.setPreferredSize(new Dimension(450, 50));

            jPanel.add(nameReceiptDetail.get(i));
            if (receiptDetailList.get(i).get(1).equals("0")) {
                JLabel panel = new JLabel();
                panel.setFont(new Font("Palatino", Font.PLAIN, 8));
                panel.setPreferredSize(new Dimension(40, 40));
                jPanel.add(panel);
            } else {
                jPanel.add(sizeReceiptDetail.get(i));
            }
            jPanel.add(quantityReceiptDetail.get(i));
            jPanel.add(priceReceiptDetail.get(i));
            jPanel.add(deleteReceiptDetail.get(i));

            Bill_detailPanel.add(jPanel);
            productIncartPanelList.add(jPanel);
        }
        Bill_detailPanel.setPreferredSize(new Dimension(450, Math.max(400, 55 * receiptDetailList.size())));
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
    }

    private void cancelBill() {
        receiptDetailList.removeAll(receiptDetailList);
        nameReceiptDetail.removeAll(nameReceiptDetail);
        sizeReceiptDetail.removeAll(sizeReceiptDetail);
        quantityReceiptDetail.removeAll(quantityReceiptDetail);
        deleteReceiptDetail.removeAll(deleteReceiptDetail);

        productIncartNotelList.removeAll(productIncartNotelList);
        buttonGroupsSugar.removeAll(buttonGroupsSugar);
        buttonGroupsIce.removeAll(buttonGroupsIce);

        productIncartPanelList.removeAll(productIncartPanelList);

        Bill_detailPanel.removeAll();
        Bill_detailPanel.setPreferredSize(new Dimension(450, 400));
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();

        jLabelBill.get(0).setText("0.0");
        jLabelBill.get(1).setText("0.0");
        jLabelBill.get(2).setText("0.0");

        jTextFieldCash.setText("");
    }

    private void calculateTotal() {
        double totalPrice = 0;
        for (List<Object> receiptDetail : receiptDetailList) {
            totalPrice += (Double) receiptDetail.get(3);
        }
        jLabelBill.get(0).setText(String.valueOf(totalPrice));
        jLabelBill.get(2).setText("0");
        jTextFieldCash.setText("");
    }

    private void calculateExcess() {
        double total = Double.parseDouble(jLabelBill.get(0).getText());
        double cash = Double.parseDouble(jTextFieldCash.getText());
        double excess = cash - total;
        jLabelBill.get(2).setText(String.valueOf(excess));
    }

    private double checkDiscount(int product_id) {
        for (Discount_Detail discountDetail : discountDetails) {
            if (product_id == discountDetail.getProduct_id()) {
                return discountDetail.getPercent();
            }
        }
        return 0;
    }

    private int checkRemainProduct(int product_id, String size) {
        List<Recipe> recipes = new RecipeBLL().findRecipesBy(Map.of("product_id", product_id, "size", size));
        List<Pair<Integer, Double>> quantityMaterials = new ArrayList<>();

        for (Recipe recipe : recipes) {
            for (Material material : materials)
                if (recipe.getMaterial_id() == material.getId())
                    quantityMaterials.add(new Pair<>(material.getId(), material.getRemain()));
        }

        quantityMaterials.sort(Comparator.comparing(Pair::getValue));

        double minRemain = quantityMaterials.get(0).getValue();
        double quantityRecipe = 0;

        for (Recipe recipe : recipes) {
            if (recipe.getMaterial_id() == quantityMaterials.get(0).getKey())
                quantityRecipe = recipe.getQuantity();
        }

        return (int) (minRemain / quantityRecipe);
    }

}
