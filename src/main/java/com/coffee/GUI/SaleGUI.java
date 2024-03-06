package com.coffee.GUI;

import com.coffee.BLL.*;
import com.coffee.DTO.Account;
import com.coffee.DTO.Product;
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
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class SaleGUI extends SalePanel {
    private final ProductBLL productBLL = new ProductBLL();
    private final StaffBLL staffBLL = new StaffBLL();
    private final ReceiptBLL receiptBLL = new ReceiptBLL();
    private final Receipt_DetailBLL receipt_detailBLL = new Receipt_DetailBLL();
    private final DiscountBLL discountBLL = new DiscountBLL();
    private final Account account;
    private RoundedPanel containerSearch;
    private List<RoundedPanel> productPanelList;
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
    private List<String> categoriesName;
    private List<Integer> productIDList;
    private List<String> productNameList;
    private List<List<Object>> receiptDetailList;
    private String categoryName = "TẤT CẢ";
    public SaleGUI(Account account) {
        super();
        this.account = account;
        initComponents();
    }

    public void initComponents() {
        containerSearch = new RoundedPanel();
        productPanelList = new ArrayList<>();
        productNameList = new ArrayList<>();
        iconSearch = new JLabel();
        staffName = new JLabel("Nhân viên: " + HomeGUI.staff.getName());
        date = new JLabel("Ngày: " + LocalDate.now().toString());
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

        jButtonSearch.setBackground(new Color(29,78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
        jButtonSearch.addActionListener(e -> searchProducts());
        SearchPanel.add(jButtonSearch);

        loadCategory();

        loadProduct(productBLL.searchProducts("deleted = 0"));

        staffName.setFont((new Font("FlatLaf.style", Font.BOLD, 15)));
        StaffPanel.add(staffName);

        date.setFont(new Font("FlatLaf.style", Font.PLAIN, 13));
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
            label.setFont((new Font("FlatLaf.style", Font.PLAIN, 13)));
            ContainerButtons.add(label);

            if (string.equals("Thành tiền:") || string.equals("Khuyến mãi:") || string.equals("Tiền thừa:")) {
                JLabel jLabel = new JLabel();
                jLabel.setPreferredSize(new Dimension(230, 20));
                jLabel.setFont((new Font("FlatLaf.style", Font.PLAIN, 13)));
                jLabelBill.add(jLabel);
                ContainerButtons.add(jLabel, "wrap");
            } else {
                jTextFieldCash.setPreferredSize(new Dimension(230, 20));
                jTextFieldCash.setFont((new Font("FlatLaf.style", Font.PLAIN, 13)));
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
//                            calculateExcess();
                        }
                    }
                });
                ContainerButtons.add(jTextFieldCash, "wrap");
            }
        }

        jButtonCancel.setPreferredSize(new Dimension(40,40));
        jButtonCancel.setFont(new Font("FlatLaf.style", Font.BOLD, 15));
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
//                if (productsBuy.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm.",
//                            "Lỗi", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//                String[] options = new String[]{"Huỷ", "Xác nhận"};
//                int choice = JOptionPane.showOptionDialog(null, "Bạn muốn huỷ hoá đơn?",
//                        "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//                if (choice == 1)
//                    cancelBill();
            }
        });
        ContainerButtons.add(jButtonCancel);

        jButtonPay.setPreferredSize(new Dimension(40,40));
        jButtonPay.setFont(new Font("FlatLaf.style", Font.BOLD, 15));
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
        jLabel.setFont(new Font("FlatLaf.style", Font.BOLD, 13));
        if (Objects.equals(tittle, "Sản phẩm")) {
            jLabel.setPreferredSize(new Dimension(200   , 40));
        }
        else if (Objects.equals(tittle, "Size") || Objects.equals(tittle, "SL") || Objects.equals(tittle, "Xoá")) {
            jLabel.setPreferredSize(new Dimension(40   , 40));
        }
        else {
            jLabel.setPreferredSize(new Dimension(90   , 40));
        }
        return jLabel;
    }


    public void loadCategory () {
        Category.removeAll();
        categoriesName.removeAll(categoriesName);
        categoriesName.add("TẤT CẢ");
        categoriesName.addAll(productBLL.getCategories());
        for (String category: categoriesName) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new FlowLayout());
            roundedPanel.setBackground(new Color(253,143,143));
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
            jLabel.setFont((new Font("FlatLaf.style", Font.PLAIN, 13)));
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

    public void loadProduct (List<Product> products) {
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
            panel.setPreferredSize(new Dimension(155, 250));
            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            productPanelList.add(panel);


            ImageIcon icon = new FlatSVGIcon("image/product.svg");
            Image image = icon.getImage();
            Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newImg);

            JLabel productImage = new JLabel(icon);
            productImage.scrollRectToVisible(new Rectangle());
            panel.add(productImage);

            JLabel productName = new JLabel();
            productName.setPreferredSize(new Dimension(150, 60));
            productName.setVerticalAlignment(JLabel.CENTER);
            productName.setHorizontalAlignment(JLabel.CENTER);
            productName.setText("<html>" + product.getName() + "</html>");
            productName.setFont((new Font("FlatLaf.style", Font.PLAIN, 13)));
            panel.add(productName);

            JLabel productPrice = new JLabel();
            productPrice.setPreferredSize(new Dimension(150, 30));
            productPrice.setVerticalAlignment(JLabel.CENTER);
            productPrice.setHorizontalAlignment(JLabel.CENTER);
            productPrice.setText(String.valueOf(product.getPrice()));
            productPrice.setFont((new Font("FlatLaf.style", Font.BOLD, 10)));
            panel.add(productPrice);

            productNameList.add(product.getName());
            productIDList.add(product.getId());
        }

        for (RoundedPanel panel : productPanelList) {
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    for (int i = 0;  i < productPanelList.size(); i++) {
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

        ContainerProduct.setPreferredSize(new Dimension(690, productNameList.size()%4==0?
                260*productNameList.size()/4 :
                260*(productNameList.size()+(4-productNameList.size()%4))/4));
        ContainerProduct.repaint();
        ContainerProduct.revalidate();
    }

    private void addProductToCart(Product product) {
        List<Object> receiptDetail = new ArrayList<>();
        receiptDetail.add(product.getName());
        receiptDetail.add(product.getSize());
        receiptDetail.add(Integer.parseInt("1"));
        receiptDetail.add(product.getPrice());
        receiptDetailList.add(receiptDetail);
        nameReceiptDetail.add(new JLabel());
        sizeReceiptDetail.add(new JComboBox<>());
        quantityReceiptDetail.add(new JTextField());
        priceReceiptDetail.add(new JLabel());
        deleteReceiptDetail.add(new JLabel());

        int index = receiptDetailList.size()-1;

        nameReceiptDetail.get(index).setFont(new Font("FlatLaf.style", Font.PLAIN, 12));
        nameReceiptDetail.get(index).setText("<html>" + receiptDetailList.get(index).get(0) + "</html>");
        nameReceiptDetail.get(index).setPreferredSize(new Dimension(200, 40));
        Bill_detailPanel.add(nameReceiptDetail.get(index));

        if (!product.getSize().equals("0")) {
            for (Product product1 : productBLL.findProductsBy(Map.of("name", receiptDetailList.get(index).get(0)))) {
                sizeReceiptDetail.get(index).addItem(product1.getSize());
            }
            sizeReceiptDetail.get(index).setPreferredSize(new Dimension(40, 40));
            sizeReceiptDetail.get(index).setFont(new Font("FlatLaf.style", Font.PLAIN, 8));
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
            Bill_detailPanel.add(sizeReceiptDetail.get(index));
        } else {
            sizeReceiptDetail.get(index).addItem("0");
            JLabel panel = new JLabel();
            panel.setFont(new Font("FlatLaf.style", Font.PLAIN, 8));
            panel.setPreferredSize(new Dimension(40, 40));
            Bill_detailPanel.add(panel);
        }

        quantityReceiptDetail.get(index).setFont(new Font("FlatLaf.style", Font.PLAIN, 10));
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
                    for (int i = 0;  i < quantityReceiptDetail.size(); i++) {
                        if (quantityReceiptDetail.get(i) == e.getComponent()) {
                            saveQuantityChanged(i);
                            return;
                        }
                    }
                }
            }
        });
        Bill_detailPanel.add(quantityReceiptDetail.get(index));

        priceReceiptDetail.get(index).setText(receiptDetailList.get(index).get(3).toString());
        priceReceiptDetail.get(index).setFont(new Font("FlatLaf.style", Font.PLAIN, 12));
        priceReceiptDetail.get(index).setPreferredSize(new Dimension(90, 40));
        Bill_detailPanel.add(priceReceiptDetail.get(index));

        deleteReceiptDetail.get(index).setFont(new Font("FlatLaf.style", Font.PLAIN, 12));
        deleteReceiptDetail.get(index).setPreferredSize(new Dimension(40, 40));
        deleteReceiptDetail.get(index).setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteReceiptDetail.get(index).setText("Xoá");
        deleteReceiptDetail.get(index).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int j = 0; j < deleteReceiptDetail.size(); j++) {
                    if (deleteReceiptDetail.get(j) == e.getComponent()) {
                        deleteProductInCart(j);
                        return;
                    }
                }

            }
        });
        Bill_detailPanel.add(deleteReceiptDetail.get(index));

        Bill_detailPanel.setPreferredSize(new Dimension(450, Math.max(400, 45*receiptDetailList.size())));
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
        System.out.println(receiptDetailList);
    }

    private void saveSizeChanged(int index) {
        List<Object> receipt = receiptDetailList.get(index);
        receipt.set(1, sizeReceiptDetail.get(index).getSelectedItem());

        int quantity = Integer.parseInt(quantityReceiptDetail.get(index).getText());
        double price = productBLL.findProductsBy(Map.of("name", receipt.get(0),
                "size", Objects.requireNonNull(sizeReceiptDetail.get(index).getSelectedItem()))).get(0).getPrice();
        receipt.set(3, quantity*price);

        receiptDetailList.set(index, receipt);

        sizeReceiptDetail.get(index).setSelectedItem(receipt.get(1));
        priceReceiptDetail.get(index).setText(receipt.get(3).toString());
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
    }

    private void saveQuantityChanged(int index) {
        List<Object> receipt = receiptDetailList.get(index);
        receipt.set(2, Integer.parseInt(quantityReceiptDetail.get(index).getText()));

        int quantity = Integer.parseInt(quantityReceiptDetail.get(index).getText());
        double price = productBLL.findProductsBy(Map.of("name", receipt.get(0),
                "size", Objects.requireNonNull(sizeReceiptDetail.get(index).getSelectedItem()))).get(0).getPrice();
        receipt.set(3, quantity*price);

        receiptDetailList.set(index, receipt);
        priceReceiptDetail.get(index).setText(receipt.get(3).toString());
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
    }

    private void deleteProductInCart(int index) {
        receiptDetailList.remove(index);
        nameReceiptDetail.remove(index);
        sizeReceiptDetail.remove(index);
        quantityReceiptDetail.remove(index);
        priceReceiptDetail.remove(index);
        deleteReceiptDetail.remove(index);

        loadProductInCart();
    }

    private void loadProductInCart() {
        Bill_detailPanel.removeAll();

        for (int i=0; i<receiptDetailList.size(); i++) {
            Bill_detailPanel.add(nameReceiptDetail.get(i));
            if (receiptDetailList.get(i).get(1).equals("0")) {
                JLabel panel = new JLabel();
                panel.setFont(new Font("FlatLaf.style", Font.PLAIN, 8));
                panel.setPreferredSize(new Dimension(40, 40));
                Bill_detailPanel.add(panel);
            } else {
                Bill_detailPanel.add(sizeReceiptDetail.get(i));
            }
            Bill_detailPanel.add(quantityReceiptDetail.get(i));
            Bill_detailPanel.add(priceReceiptDetail.get(i));
            Bill_detailPanel.add(deleteReceiptDetail.get(i));
        }

        Bill_detailPanel.setPreferredSize(new Dimension(450, Math.max(400, 45*receiptDetailList.size())));
        Bill_detailPanel.repaint();
        Bill_detailPanel.revalidate();
    }

    private void checkRemainProduct() {}
}
