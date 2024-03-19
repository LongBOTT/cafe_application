package com.coffee.GUI;

import com.coffee.BLL.ProductBLL;
import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Function;
import com.coffee.DTO.Product;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddProductGUI;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailProductGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditProductGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditSupplierGUI;
import com.coffee.GUI.components.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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

        containerSearch.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(380, 40));

        SearchPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        SearchPanel.add(containerSearch);
        SearchPanel.add(jButtonSearch);

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
        jButtonSearch.addActionListener(e -> searchProducts());
        SearchPanel.add(jButtonSearch);
//        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                searchProducts();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                searchProducts();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                searchProducts();
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
                    new AddProductGUI();
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
    int columnCount = model.getColumnCount();
    model.setRowCount(0);
    int columnWidth = dataTable.getColumnModel().getColumn(0).getPreferredWidth();
    int rowHeight = dataTable.getRowHeight(0);
    // Mảng chứa tất cả các sản phẩm
     ArrayList<Object[]> allProducts = ConvertProductUnique(objects);

    // Duyệt qua mảng allProducts để thêm dữ liệu vào bảng
    for (Object[] productArray : allProducts) {
        String productName = (String) productArray[1];
        ArrayList<String> sizes = (ArrayList<String>) productArray[4];
        ArrayList<Double> prices = (ArrayList<Double>) productArray[3];

        ImageIcon icon = new FlatSVGIcon("image/Product/" + productArray[5] + ".svg");
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
        JLabel iconDetail = null;
        JLabel iconEdit = null;
        JLabel iconRemove = null;
        if (detail) {
            iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
        }
        if (edit) {
             iconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
        }
        if (remove) {
             iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));

        }

        Object[] rowData;
        if (columnCount == 7) {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice, iconDetail,iconEdit,iconRemove};
        } else if (columnCount == 6) {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice,  iconDetail, iconEdit};
        } else if (columnCount == 5) {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice, iconDetail};
        } else {
            rowData = new Object[]{productImage, productName, popupMenuSize, popupMenuPrice};
        }

        model.addRow(rowData);

    }

}
public ArrayList<Object[]> ConvertProductUnique(Object[][] objects){
     ArrayList<Object[]> allProducts = new ArrayList<>();
    for (Object[] product : objects) {
        String productID = (String) product[0];
        String productName = (String) product[1];
        String category = (String) product[2];
        double price = Double.parseDouble((String) product[3]);
        String size = (String) product[4];
        String productImage = (String) product[5];

        boolean productExists = false;
        for (Object[] productArray : allProducts) {
            String existingProductName = (String) productArray[1];
            if (existingProductName.equals(productName)) {
                productExists = true;

                ((ArrayList<String>) productArray[4]).add(size);
                ((ArrayList<Double>) productArray[3]).add(price);
                break;
            }
        }

        if (!productExists) {
            ArrayList<String> sizes = new ArrayList<>();
            sizes.add(size);

            ArrayList<Double> prices = new ArrayList<>();
            prices.add(price);

            Object[] productArray = {productID,productName,category,prices,sizes,productImage};
            allProducts.add(productArray);
        }
    }
    return allProducts;
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
            loadDataTable(productBLL.getData(productBLL.searchProducts("deleted = 0")));
        else
            loadDataTable(productBLL.getData(productBLL.findProductsBy(Map.of("category", categoryName))));
        jTextFieldSearch.setText("");
    }

    private void searchProducts() {
        if (jTextFieldSearch.getText().isEmpty()) {
            loadDataTable(productBLL.getData(productBLL.searchProducts("deleted = 0")));
        } else {
            loadDataTable(productBLL.getData(productBLL.findProducts("name", jTextFieldSearch.getText())));
        }
        categoryName = "";
    }

    private void selectFunction() {
        int indexColumn = dataTable.getSelectedColumn();
        int indexRow = dataTable.getSelectedRow();
        if (indexColumn == indexColumnDetail)
            new DetailProductGUI();

        if ( indexColumn == indexColumnEdit) {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();

            Object selectedValue = model.getValueAt(indexRow,1);

            new EditProductGUI(ConvertProductUnique(productBLL.getData(productBLL.findProducts("name", selectedValue.toString()))));


//            refresh();
        }
        if (indexColumn == indexColumnRemove)
           deleteProduct(productBLL.searchProducts("deleted = 0").get(indexRow));
        refresh();
    }
    private void deleteProduct(Product product) {
        String[] options = new String[]{"Huỷ", "Xác nhận"};
        int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá sản phẩm?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 1) {
            Pair<Boolean, String> result = productBLL.deleteProduct(product);
            if (result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                refresh();
            } else {
                JOptionPane.showMessageDialog(null, result.getValue(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
