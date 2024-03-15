package com.coffee.GUI;

import com.coffee.BLL.ProductBLL;
import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Function;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditSupplierGUI;
import com.coffee.GUI.components.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class ProductGUI extends Layout3 {
    private final ProductBLL productBLL = new ProductBLL();
    private List<Function> functions;
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JButton jButtonSearch;
    private SupplierBLL supplierBLL = new SupplierBLL();
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
//    private JComboBox<String> sizeComboBox;

    private  ArrayList<Object[]> allProducts;
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

        columnNames = new String[]{"Ảnh", "Tên sản phẩm", "Size", "giá bán"};
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
                detail, edit, remove, 4);
        int[] columnWidths = {150, 200, 20, 100};

        for (int i = 0; i < columnWidths.length; i++) {
            dataTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        dataTable.setRowHeight(150);

        dataTable.getColumnModel().getColumn(0).setCellRenderer(new CustomPanelRenderer());
        dataTable.getColumnModel().getColumn(2).setCellRenderer(new CustomPanelRenderer());
        dataTable.getColumnModel().getColumn(3).setCellRenderer(new CustomPanelRenderer());
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
        loadDataTable(productBLL.getData(productBLL.searchProducts("deleted = 0")));
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

    }

    public void refresh() {
        jTextFieldSearch.setText("");
        loadDataTable(productBLL.getData(productBLL.searchProducts("deleted = 0")));
    }


public void loadDataTable(Object[][] objects) {
    DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
    model.setRowCount(0);
    int columnWidth = dataTable.getColumnModel().getColumn(0).getPreferredWidth();
    int rowHeight = dataTable.getRowHeight(0);
    // Mảng chứa tất cả các sản phẩm
    allProducts = new ArrayList<>();

    for (Object[] product : objects) {
        String productName = (String) product[1];
        String size = (String) product[4];
        double price = Double.parseDouble((String) product[3]);
        String productImage = (String) product[5];

        boolean productExists = false;
        for (Object[] productArray : allProducts) {
            String existingProductName = (String) productArray[1];
            if (existingProductName.equals(productName)) {
                productExists = true;

                ((ArrayList<String>) productArray[2]).add(size);
                ((ArrayList<Double>) productArray[3]).add(price);
                break;
            }
        }

        if (!productExists) {
            ArrayList<String> sizes = new ArrayList<>();
            sizes.add(size);

            ArrayList<Double> prices = new ArrayList<>();
            prices.add(price);

            Object[] productArray = {productImage, productName, sizes, prices};
            allProducts.add(productArray);
        }
    }

    // Duyệt qua mảng allProducts để thêm dữ liệu vào bảng
    for (Object[] productArray : allProducts) {
        String productName = (String) productArray[1];
        ArrayList<String> sizes = (ArrayList<String>) productArray[2];
        ArrayList<Double> prices = (ArrayList<Double>) productArray[3];

        ImageIcon icon = new FlatSVGIcon("image/Product/" + productArray[0] + ".svg");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(columnWidth, rowHeight, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel productImage = new JLabel(icon);
        productImage.scrollRectToVisible(new Rectangle());

        CustomPopupMenu popupMenuSize = new CustomPopupMenu();
        CustomPopupMenu popupMenuPrice = new CustomPopupMenu();
        for (String size : sizes) {
            popupMenuSize.addMenuItem(size);
        }
        for (Double price : prices){
            popupMenuPrice.addMenuItem(String.valueOf(price));
        }
        if (detail) {
            JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
            productArray = Arrays.copyOf(productArray, productArray.length + 1);
            productArray[productArray.length - 1] = iconDetail;
        }
        if (edit) {
            JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/edit.svg"));
            productArray = Arrays.copyOf(productArray, productArray.length + 1);
            productArray[productArray.length - 1] = iconDetail;
        }
        if (remove) {
            JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/remove.svg"));
            productArray = Arrays.copyOf(productArray, productArray.length + 1);
            productArray[productArray.length - 1] = iconDetail;
        }

        Object[] rowData;
        if (productArray.length == 7) {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice, productArray[4], productArray[5], productArray[6]};
        } else if (productArray.length == 6) {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice, productArray[4], productArray[5]};
        } else if (productArray.length == 5) {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice, productArray[4]};
        } else {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice};
        }

        model.addRow(rowData);


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
//        if (categoryName.equals("TẤT CẢ"))
////            loadProduct(productBLL.searchProducts("deleted = 0"));
//        else
////            loadProduct(productBLL.findProductsBy(Map.of("category", categoryName)));
//        jTextFieldSearch.setText("");
    }



    private void selectFunction() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail)
            new DialogFormDetail_1(); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

        if (detail && indexColumn == indexColumnEdit) {
            new DialogFormDetail_1(); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
            refresh();
        }

        if (detail && indexColumn == indexColumnRemove)
            new DialogFormDetail_1(); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
        refresh(); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá


    }

}
