package com.coffee.GUI;

import com.coffee.BLL.ProductBLL;
import com.coffee.DTO.Function;
import com.coffee.DTO.Product;
import com.coffee.GUI.components.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductGUI extends Layout3 {
    private final ProductBLL productBLL = new ProductBLL();
    private List<Function> functions;
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JButton jButtonSearch;

    private boolean detail = false;
    private boolean edit = false;
    private boolean remove = false;
    private DataTable dataTable;
    private List<String> categoriesName;
    private String categoryName = "TẤT CẢ";
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private int indexColumnRemove = -1;

    private String[] columnNames;
    public ProductGUI() {

    }

    public ProductGUI(List<Function> functions) {
        super();
        this.functions = functions;
        if (functions.stream().anyMatch(f -> f.getName().equals("view")))
            detail = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("edit")))
            edit = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("remove")))
            remove = true;
        initComponents(functions);
    }

    public void initComponents(List<Function> functions) {
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");
        categoriesName = new ArrayList<>();

        columnNames = new String[]{"Ảnh", "Tên sản phẩm", "giá bán"};
        if (detail) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnDetail = columnNames.length - 1;
            columnNames[indexColumnDetail] = "Xem";
        }

        if (edit) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnEdit = columnNames.length - 1;
            columnNames[indexColumnEdit] = "Sửa";
        }

        if (remove) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnRemove = columnNames.length - 1;
            columnNames[indexColumnRemove] = "Xoá";
        }

        dataTable = new DataTable(new Object[0][0], columnNames,
                e -> selectFunction(),
                detail, edit, remove, 3);
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        bottom.add(scrollPane, BorderLayout.CENTER);
        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(280, 40));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);
        SearchPanel.add(containerSearch, gbc);


        gbc.gridx++;
        gbc.weightx = 0.0;
        gbc.insets = new Insets(0, 0, 0, 10);
        SearchPanel.add(jButtonSearch, gbc);


        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm");
        jTextFieldSearch.setPreferredSize(new Dimension(250, 30));


        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 40));
        jButtonSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        jButtonSearch.addActionListener(e -> searchSuppliers());
//        SearchPanel.add(jButtonSearch);
//        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                searchSuppliers();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                searchSuppliers();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                searchSuppliers();
//            }
//        });
        RoundedPanel refreshPanel = new RoundedPanel();
        refreshPanel.setLayout(new GridBagLayout());
        refreshPanel.setPreferredSize(new Dimension(130, 40));
        refreshPanel.setBackground(new Color(217, 217, 217));
        refreshPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                refresh();
            }
        });
        FunctionPanel.add(refreshPanel);
        JLabel refreshLabel = new JLabel("Làm mới");
        refreshLabel.setFont(new Font("Public Sans", Font.PLAIN, 13));
        refreshLabel.setIcon(new FlatSVGIcon("icon/refresh.svg"));
        refreshPanel.add(refreshLabel);

        if (functions.stream().anyMatch(f -> f.getName().equals("add"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(217, 217, 217));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            roundedPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
//                    new AddSupplierGUI();
                    refresh();
                }
            });
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Thêm mới");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setIcon(new FlatSVGIcon("icon/add.svg"));
            roundedPanel.add(panel);
        }
        if (functions.stream().anyMatch(f -> f.getName().equals("excel"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(217, 217, 217));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Xuất Excel");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setIcon(new FlatSVGIcon("icon/excel.svg"));
            roundedPanel.add(panel);
        }
        if (functions.stream().anyMatch(f -> f.getName().equals("pdf"))) {
            RoundedPanel roundedPanel = new RoundedPanel();
            roundedPanel.setLayout(new GridBagLayout());
            roundedPanel.setPreferredSize(new Dimension(130, 40));
            roundedPanel.setBackground(new Color(217, 217, 217));
            roundedPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            FunctionPanel.add(roundedPanel);

            JLabel panel = new JLabel("Xuất PDF");
            panel.setFont(new Font("Public Sans", Font.PLAIN, 13));
            panel.setIcon(new FlatSVGIcon("icon/pdf.svg"));
            roundedPanel.add(panel);
        }

        loadCategory();
        loadDataTable(productBLL.getData(productBLL.searchProducts("deleted = 0")));
    }

    public void refresh() {
        jTextFieldSearch.setText("");
        loadDataTable(productBLL.getData(productBLL.searchProducts("deleted = 0")));
    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        Object[][] data = new Object[objects.length][objects[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " "); // In ra giá trị của phần tử ở hàng i, cột j
            }
            System.out.println(); // Xuống dòng sau khi hoàn thành mỗi hàng
        }

//        for (int i = 0; i < objects.length; i++) {
//            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);
//

//            if (detail) {
//                JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
//                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
//                data[i][data[i].length - 1] = iconDetail;
//            }
//            if (edit) {
//                JLabel iconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
//                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
//                data[i][data[i].length - 1] = iconEdit;
//            }
//            if (remove) {
//                JLabel iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
//                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
//                data[i][data[i].length - 1] = iconRemove;
//            }
//        }

        for (Object[] object : data) {
            model.addRow(object);
        }
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

    public void loadProduct(List<Product> products) {
//        ContainerProduct.removeAll();
//        productPanelList.removeAll(productPanelList);
//        productIDList.removeAll(productIDList);
//        productNameList.removeAll(productNameList);
//
//        for (Product product : products) {
//            if (productNameList.contains(product.getName())) {
//                continue;
//            }
//            RoundedPanel panel = new RoundedPanel();
//            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
//            panel.setBackground(Color.white);
//            panel.setPreferredSize(new Dimension(155, 250));
//            panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//            productPanelList.add(panel);
//
//
//            ImageIcon icon = new FlatSVGIcon("image/Product/" + product.getImage() + ".svg");
//            Image image = icon.getImage();
//            Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
//            icon = new ImageIcon(newImg);
//
//            JLabel productImage = new JLabel(icon);
//            productImage.scrollRectToVisible(new Rectangle());
//            panel.add(productImage);
//
//            JLabel productName = new JLabel();
//            productName.setPreferredSize(new Dimension(150, 60));
//            productName.setVerticalAlignment(JLabel.CENTER);
//            productName.setHorizontalAlignment(JLabel.CENTER);
//            productName.setText("<html>" + product.getName() + "</html>");
//            productName.setFont((new Font("FlatLaf.style", Font.PLAIN, 13)));
//            panel.add(productName);
//
//            JLabel productPrice = new JLabel();
//            productPrice.setPreferredSize(new Dimension(150, 30));
//            productPrice.setVerticalAlignment(JLabel.CENTER);
//            productPrice.setHorizontalAlignment(JLabel.CENTER);
//            productPrice.setText(String.valueOf(product.getPrice()));
//            productPrice.setFont((new Font("FlatLaf.style", Font.BOLD, 10)));
//            panel.add(productPrice);
//
//            productNameList.add(product.getName());
//            productIDList.add(product.getId());
//        }
//
//        for (RoundedPanel panel : productPanelList) {
//            panel.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mousePressed(MouseEvent e) {
//                    for (int i = 0; i < productPanelList.size(); i++) {
//                        if (productPanelList.get(i) == e.getComponent()) {
//                            addProductToCart(productBLL.findProductsBy(Map.of("id", productIDList.get(i))).get(0));
//                        }
//                    }
//                }
//            });
//        }
//
//        for (RoundedPanel panel : productPanelList) {
//            ContainerProduct.add(panel);
//        }
//
//        ContainerProduct.setPreferredSize(new Dimension(690, productNameList.size() % 4 == 0 ?
//                260 * productNameList.size() / 4 :
//                260 * (productNameList.size() + (4 - productNameList.size() % 4)) / 4));
//        ContainerProduct.repaint();
//        ContainerProduct.revalidate();
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail)
//            new DetailSupplierGUI(productBLL.searchProducts("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

        if (detail && indexColumn == indexColumnEdit) {
//            new EditSupplierGUI(productBLL.searchSuppliers("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
            refresh();
        }

//        if (detail && indexColumn == indexColumnRemove)
//          deleteSupplier(supplierBLL.searchSuppliers("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
//
  }
}
