package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ProductBLL;
import com.coffee.BLL.RecipeBLL;
import com.coffee.DTO.Material;
import com.coffee.DTO.Product;
import com.coffee.DTO.Recipe;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.ProductGUI;
import com.coffee.GUI.components.swing.DataSearch;
import com.coffee.GUI.components.swing.EventClick;
import com.coffee.GUI.components.swing.MyTextField;
import com.coffee.GUI.components.swing.PanelSearch;
import com.coffee.GUI.components.CustomPanelRenderer;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

import com.coffee.BLL.MaterialBLL;
import com.coffee.DTO.Material;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class AddProductGUI extends DialogFormDetail_1 {
    private MaterialBLL materialBLL = new MaterialBLL();
    private ProductBLL productBLL = new ProductBLL();
    private RecipeBLL recipeBLL = new RecipeBLL();
    //    private List<Material> listMaterials = new ArrayList<>();
    private List<Recipe> materialList = new ArrayList<>();
    ;
    private List<Product> productList = new ArrayList<>();
    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;
    private JLabel lblImage;
    private JButton btnImage;
    private JLabel lblListMaterial;
    private DataTable dataTable;
    private JButton buttonCancel;
    private JButton buttonAdd;

    private RoundedPanel containerInforMaterial;
    private RoundedScrollPane scrollPane;
    private RoundedPanel containerDataTable;
    private List<String> AtributeProduct = new ArrayList<>();
    private String[] columnNames;
    private JTextField txtNameProduct;
    private JComboBox<String> cbSize;
    private JPanel panelSize;
    private JTextField txtPrice;
    private JTextField txtCapitalPrice;
    private JTextField txtCategory;
    private JTextField txtQuantity;
    private JTextField txtUnit;
    private JButton btnAddMaterial;
    private JPopupMenu menu;
    private PanelSearch search;
    private MyTextField txtSearch;
    private JLabel selectedLabel;
    private int productID;
    private int materialID;
    private String imageProduct;
    private JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/edit.svg"));
    private JLabel iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));

    public AddProductGUI() {
        super();
        super.setTitle("Thêm sản phẩm");
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
                txtSearch.setText(data.getText());
                Material material = materialBLL.findMaterialsBy(Map.of("name", data.getText())).get(0);
                materialID = material.getId();
                txtUnit.setText(material.getUnit());
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
        titleName = new JLabel("Thêm sản phẩm");
        title.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        title.add(titleName);

        top.setLayout(new FlowLayout());

        containerAtributeProduct = new RoundedPanel();
        containerAtributeProduct.setLayout(new MigLayout("", "[]40[][][]100", "10[]10[]10[]10"));
        containerAtributeProduct.setBackground(new Color(217, 217, 217));
        containerAtributeProduct.setPreferredSize(new Dimension(600, 200));

        containerImage = new RoundedPanel();
        containerImage.setLayout(new MigLayout("", "[]", "[][]"));
        containerImage.setBackground(new Color(217, 217, 217));
        containerImage.setPreferredSize(new Dimension(200, 200));

        JPanel PanelImage = new JPanel();
        PanelImage.setPreferredSize(new Dimension(150, 150));
//        PanelImage.setBackground(new Color(217,217,217));

        lblImage = new JLabel();
        PanelImage.add(lblImage);


        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(100, 30));
        btnImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!productList.isEmpty()) {
                    imageProduct = uploadImage();
                    PanelImage.setBackground(new Color(217, 217, 217));
                    for (Product product : productList) {
                        product.setImage(imageProduct);
                    }
                } else
                    JOptionPane.showMessageDialog(null, "Chưa điền thông tin sản phẩm",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        containerImage.add(PanelImage, "alignx center,wrap");
        containerImage.add(btnImage, "alignx center");

        top.add(containerAtributeProduct);
        top.add(containerImage);
        EmptyBorder emptyBorder = new EmptyBorder(0, 30, 0, 0);
        top.setBorder(emptyBorder);

        JLabel lblName = createLabel("Tên sản phẩm");
        JLabel lblCategory = createLabel("Thể loại");
        JLabel lblSize = createLabel("Size");
        JLabel lblPrice = createLabel("Giá bán");
        JLabel lblCapitalPrice = createLabel("Giá Vốn");

        txtNameProduct = createTextField();
        txtCategory = createTextField();

        panelSize = new JPanel();
        panelSize = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panelSize.setBackground(new Color(217, 217, 217));
        panelSize.setPreferredSize(new Dimension(350, 30));

        cbSize = createComboBox();
        Set<String> addedLabels = new HashSet<>();
        String[] sizeOptions = {"0", "S", "M", "L"};
        for (String size : sizeOptions)
            cbSize.addItem(size);
        cbSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSize = (String) cbSize.getSelectedItem();
                if (selectedLabel != null) {
                    selectedLabel.setBackground(Color.WHITE);
                    selectedLabel.setForeground(Color.BLACK);
                }
                if ("0".equals(selectedSize)) {
                    panelSize.removeAll();
                    addedLabels.clear();
                    addedLabels.add("0");
                    if (!addProductBySize("0")) {
                        return;
                    }
                    JLabel label = new JLabel("0");
                    label.setPreferredSize(new Dimension(30, 30));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFocusable(true);
                    label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    label.setOpaque(true);
                    label.setBackground(new Color(59, 130, 198));
                    label.setForeground(Color.WHITE);
                    selectedLabel = label;
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (selectedLabel != null) {
                                selectedLabel.setBackground(Color.WHITE);
                                selectedLabel.setForeground(Color.BLACK);
                            }
                            label.setBackground(new Color(59, 130, 198));
                            label.setForeground(Color.WHITE);
                            selectedLabel = label;
                            loadPriceBySize("0");
                            loadRecipeBySize("0");
                        }
                    });
                    panelSize.add(label);
                } else {
                    if (addedLabels.contains("0")) {
                        panelSize.removeAll();
                        addedLabels.remove("0");
                    }
                    if (!addProductBySize(selectedSize)) {
                        return;
                    }
                    JLabel label = new JLabel(selectedSize);
                    label.setPreferredSize(new Dimension(30, 30));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFocusable(true);
                    label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    label.setOpaque(true);
                    label.setBackground(new Color(59, 130, 198));
                    label.setForeground(Color.WHITE);
                    selectedLabel = label;
                    addedLabels.add(selectedSize);
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (selectedLabel != null) {
                                selectedLabel.setBackground(Color.WHITE);
                                selectedLabel.setForeground(Color.BLACK);
                            }
                            label.setBackground(new Color(59, 130, 198));
                            label.setForeground(Color.WHITE);
                            selectedLabel = label;
                            loadPriceBySize(selectedSize);
                            loadRecipeBySize(selectedSize);
                        }
                    });
                    panelSize.add(label);
                }
                panelSize.revalidate();
                panelSize.repaint();
                resetTxt();
            }
        });


        JButton deleteSize = new JButton();
        deleteSize.setPreferredSize(new Dimension(30, 30));
        deleteSize.setBackground(new Color(217, 217, 217));
        deleteSize.setBorder(null);
        deleteSize.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ImageIcon iconRemove = new FlatSVGIcon("icon/remove.svg");
        Image imageRemove = iconRemove.getImage();
        Image newImgRemove = imageRemove.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        iconRemove = new ImageIcon(newImgRemove);
        deleteSize.setIcon(iconRemove);
        deleteSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedLabel != null) {
                    panelSize.remove(selectedLabel);
                    String labelText = selectedLabel.getText();
                    addedLabels.remove(labelText);
                    panelSize.revalidate();
                    panelSize.repaint();
                    resetTxt();
                    removeSizeProduct();
                    selectedLabel = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Chọn size muốn xóa");
                }
            }
        });

        txtPrice = createTextField();
        txtCapitalPrice = createTextField();
        txtCapitalPrice.setFocusable(false);

        txtNameProduct.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!productList.isEmpty())
                    updateProductByName();
            }
        });

        txtCategory.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (!productList.isEmpty())
                    updateProductByCategory();
            }
        });
        txtPrice.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                updatePriceProductBySize();
            }
        });

        containerAtributeProduct.add(lblName);
        containerAtributeProduct.add(txtNameProduct, "wrap");

        containerAtributeProduct.add(lblCategory);
        containerAtributeProduct.add(txtCategory, "wrap");

        containerAtributeProduct.add(lblSize);
        containerAtributeProduct.add(panelSize);
        containerAtributeProduct.add(cbSize);
        containerAtributeProduct.add(deleteSize, "wrap");

        containerAtributeProduct.add(lblPrice);
        containerAtributeProduct.add(txtPrice, "wrap");

        containerAtributeProduct.add(lblCapitalPrice);
        containerAtributeProduct.add(txtCapitalPrice, "wrap");


        lblListMaterial = new JLabel("Danh sách nguyên liệu");
        lblListMaterial.setFont(new Font("Public Sans", Font.BOLD, 16));

        center.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        center.add(lblListMaterial);

        containerInforMaterial = new RoundedPanel();
        containerInforMaterial.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        containerInforMaterial.setBackground(new Color(217, 217, 217));


        btnAddMaterial = new JButton();
        ImageIcon icon = new FlatSVGIcon("icon/add.svg");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        btnAddMaterial.setIcon(icon);
        btnAddMaterial.setPreferredSize(new Dimension(30, 30));
        btnAddMaterial.setBackground(new Color(0, 182, 62));
        btnAddMaterial.setFont(new Font("Public Sans", Font.BOLD, 16));
        btnAddMaterial.setForeground(Color.WHITE);
        btnAddMaterial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddMaterial.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new AddMaterialGUI();
            }
        });
        containerInforMaterial.add(btnAddMaterial);
        bottom.add(containerInforMaterial, BorderLayout.NORTH);

        JLabel lblNameMaterial = createLabelMaterial("Tên nguyên liệu");


        txtSearch = new MyTextField();
        txtSearch.setPreferredSize(new Dimension(230, 30));

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

        JLabel lblQuantity = createLabelMaterial("Số lượng");
        txtQuantity = createTextFieldMaterial();
        txtQuantity.setPreferredSize(new Dimension(60, 30));

        JLabel lblUnit = createLabelMaterial("Đơn vị");
        txtUnit = createTextFieldMaterial();
        txtUnit.setPreferredSize(new Dimension(60, 30));
        txtUnit.setEnabled(false);

        JButton btnThem = new JButton("Thêm");
        btnThem.setPreferredSize(new Dimension(80, 30));
        btnThem.setBackground(new Color(0, 182, 62));
        btnThem.setFont(new Font("Public Sans", Font.BOLD, 16));
        btnThem.setForeground(Color.WHITE);
        btnThem.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    addMaterialTemp();
            }
        });

        containerInforMaterial.add(lblNameMaterial);
        containerInforMaterial.add(txtSearch);
        containerInforMaterial.add(lblQuantity);
        containerInforMaterial.add(txtQuantity);
        containerInforMaterial.add(lblUnit);
        containerInforMaterial.add(txtUnit);
        containerInforMaterial.add(btnThem);

        columnNames = new String[]{"ID", "Tên nguyên liệu", "Đơn vị", "Giá vốn", "SL", "T.Tiền"};
        columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
        columnNames[columnNames.length - 1] = "Xóa";
        dataTable = new DataTable(new Object[0][0], columnNames,
                e -> selectFunction(), e -> {
        }, false, true, true, 6, true, 4);
        int[] columnWidths = {50, 300, 50, 50, 50, 50};

        for (int i = 0; i < columnWidths.length; i++) {
            dataTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }


        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        containerDataTable = new RoundedPanel();
        containerDataTable.setLayout(new BorderLayout());
        containerDataTable.setBackground(new Color(217, 217, 217));
        EmptyBorder emptyBorderTop = new EmptyBorder(20, 0, 0, 0);
        containerDataTable.setBorder(emptyBorderTop);
        containerDataTable.add(scrollPane, BorderLayout.CENTER);
        bottom.add(containerDataTable, BorderLayout.CENTER);

        JTableHeader jTableHeader = dataTable.getTableHeader();
        jTableHeader.setBackground(new Color(232, 206, 180));

        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        buttonCancel.setPreferredSize(new Dimension(100, 30));
        buttonCancel.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonCancel.setBackground(new Color(0xD54218));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonCancel.setBackground(Color.white);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                cancel();
            }
        });
        containerButton.add(buttonCancel);

        buttonAdd.setPreferredSize(new Dimension(100, 30));
        buttonAdd.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addAllProducts();
                addAllRecipe();
            }
        });
        containerButton.add(buttonAdd);

    }

    private Pair<Boolean, String> validateMaterial(String name, String quantity) {
        Pair<Boolean, String> result;

        if (name.isBlank()) {
            return new Pair<>(false, "Vui lòng chọn nguyên liệu");
        }
        result = materialBLL.validateQuantity(quantity);
        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        return new Pair<>(true, "hợp lệ");
    }

    private void updatePriceProductBySize() {
        if (selectedLabel == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn size",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String size = selectedLabel.getText();
        Pair<Boolean, String> result;
        String price = txtPrice.getText();
        result = productBLL.validatePrice(price);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtPrice.requestFocus();
            return;
        }
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                if (product.getSize().equals(size)) {
                    product.setPrice(Double.parseDouble(price));
                    return;
                }
            }
        }
    }

    private void updateProductByName() {
        Pair<Boolean, String> result;
        String name = txtNameProduct.getText();
        result = productBLL.validateName(name);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNameProduct.requestFocus();
            return;
        }
        for (Product product : productList) {
            product.setName(name);
        }
    }

    private void updateProductByCategory() {
        Pair<Boolean, String> result;
        String category = txtCategory.getText();
        result = productBLL.validateCategory(category);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtCategory.requestFocus();
            return;
        }
        for (Product product : productList) {
            product.setCategory(category);
        }
    }

    private boolean addProductBySize(String size) {

        String name, category;
        if (productList.isEmpty()) {
            productID = productBLL.getAutoID(productBLL.searchProducts());
            name = txtNameProduct.getText();
        } else {
            productID = productList.get(0).getId();
            name = productList.get(0).getName();
        }
        Pair<Boolean, String> result;
        result = productBLL.validateName(name);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        category = txtCategory.getText();
        result = productBLL.validateCategory(category);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Product product = new Product(productID, name, size, category, 0, imageProduct, false);
        result = productBLL.validateProductAll(product);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        productList.add(product);
        return true;
    }

    private String uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            String imageFile = "SP" + productID + ".svg";
            String imageName = "SP" + productID;
            java.nio.file.Path destinationPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "image", "Product", imageFile);
            java.nio.file.Path destinationPathTarget = Paths.get(System.getProperty("user.dir"), "target", "classes", "image", "Product", imageFile);
            try {
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(selectedFile.toPath(), destinationPathTarget, StandardCopyOption.REPLACE_EXISTING);
                InputStream inputStream = Files.newInputStream(destinationPath);
                ImageIcon icon = new FlatSVGIcon(inputStream);
                Image image = icon.getImage();
                Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                lblImage.setIcon(icon);

                return imageName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private void addMaterialTemp() {
        String name = txtSearch.getText();
        String quantity = txtQuantity.getText();
        String unit = txtUnit.getText();
        String size = selectedLabel.getText();
        Pair<Boolean, String> result = validateMaterial(name, quantity);
        if (!result.getKey()) {
            JOptionPane.showMessageDialog(null, result.getValue(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (unit.isBlank()) {
            JOptionPane.showMessageDialog(null, "Nguyên liệu không tồn tại",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Recipe recipe = new Recipe(productID, materialID, Double.parseDouble(quantity), size, unit);
        if (!checkRecipeExist(recipe)) {
            materialList.add(recipe);
            addDataToTable(materialID, name, quantity, unit);
        } else {
            JOptionPane.showMessageDialog(null, "Nguyên liệu đã có trong công thức",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean checkRecipeExist(Recipe recipe) {
        for (Recipe r : materialList) {
            if (r.getProduct_id() == recipe.getProduct_id()
                    && r.getMaterial_id() == recipe.getMaterial_id()
                    && r.getSize().equals(recipe.getSize())) {
                return true;
            }
        }
        return false;
    }

    private void addDataToTable(int materialID, String name, String quantity, String unit) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();

        Material material = materialBLL.findMaterialsBy(Map.of("id",materialID)).get(0);
        String materialName = material.getName();
        String materialPrice = String.valueOf(material.getUnit_price());
        double materialPriceD = material.getUnit_price();
        double totalAmount = materialPriceD * Double.parseDouble(quantity);
        Object[] rowData = {materialID, materialName, unit,materialPrice,Double.parseDouble(quantity),totalAmount, iconRemove};

        model.addRow(rowData);

        txtSearch.setText("");
        txtQuantity.setText("");
        txtUnit.setText("");
        if(txtCapitalPrice.getText().isEmpty()){
            txtCapitalPrice.setText(materialPrice);
        }
        else{
           double capitalPrice = Double.parseDouble(txtCapitalPrice.getText()) ;
           txtCapitalPrice.setText(String.valueOf(capitalPrice+totalAmount));
        }
    }

    private void selectFunction() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();
        if (indexColumn == 6) {
            String[] options = new String[]{"Huỷ", "Xác nhận"};
            int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá nguyên liệu?",
                    "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == 1) {
                Iterator<Recipe> iterator = materialList.iterator();
                String labelText = selectedLabel.getText();
                int id = (int) model.getValueAt(indexRow, 0);
                double totalAmount =  (double) model.getValueAt(indexRow,5);
                double capitalPrice = Double.parseDouble(txtCapitalPrice.getText());
                txtCapitalPrice.setText(String.valueOf(capitalPrice-totalAmount));
                while (iterator.hasNext()) {
                    Recipe recipe = iterator.next();
                    if (recipe.getProduct_id() == productID && recipe.getMaterial_id() == id && recipe.getSize().equals(labelText)) {
                        iterator.remove();
                    }
                }
                model.removeRow(indexRow);
            }
        }
    }

    private void removeSizeProduct() {
        Iterator<Product> iterator = productList.iterator();
        String labelText = selectedLabel.getText();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getSize().equals(labelText)) {
                iterator.remove();
                removeRecipeByProduct(product);
            }
        }

    }

    private void removeRecipeByProduct(Product product) {
        materialList.removeIf(recipe -> recipe.getProduct_id() == product.getId() && recipe.getSize().equals(product.getSize()));
    }

    private void resetTxt() {
        txtPrice.setText("0");
        txtSearch.setText("");
        txtQuantity.setText("");
        txtUnit.setText("");
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
    }

    private void loadPriceBySize(String size) {
        for (Product product : productList) {
            if (product.getSize().equals(size)) {
                String price = product.getPrice().toString();
                txtPrice.setText(price);
                return;
            }
        }
        txtPrice.setText("0");
    }

    private void loadRecipeBySize(String size) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        double capitalPrice = 0.0;
        for (Recipe recipe : materialList) {
            if (recipe.getSize().equals(size)) {
                Material material = materialBLL.findMaterialsBy(Map.of("id", recipe.getMaterial_id())).get(0);
                String materialName = material.getName();
                String materialPrice = String.valueOf(material.getUnit_price());

                double materialPriceD = material.getUnit_price();
                double totalAmount = materialPriceD * recipe.getQuantity();
                Object[] rowData = {recipe.getMaterial_id(), materialName, recipe.getUnit(),materialPrice,recipe.getQuantity(),totalAmount, iconRemove};

                model.addRow(rowData);
                capitalPrice  += totalAmount;
            }
        }
        txtSearch.setText("");
        txtQuantity.setText("");
        txtUnit.setText("");
        txtCapitalPrice.setText(Double.toString(capitalPrice));
    }

    private JLabel createLabelMaterial(String text) {
        JLabel label = new JLabel(text);
        label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
        label.setPreferredSize(null);
        return label;
    }

    private JTextField createTextFieldMaterial() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Public Sans", Font.PLAIN, 14));
        textField.setBackground(new Color(245, 246, 250));
        return textField;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(30, 30));
        comboBox.setFont(new Font("Public Sans", Font.PLAIN, 14));
        comboBox.setBackground(new Color(245, 246, 250));
        return comboBox;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(170, 30));
        label.setFont(new Font("Public Sans", Font.PLAIN, 16));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(350, 30));
        textField.setFont(new Font("Public Sans", Font.PLAIN, 14));
        textField.setBackground(new Color(245, 246, 250));
        return textField;
    }

    private void addAllProducts() {
        boolean allProductsAdded = true;
        for (Product product : productList) {
            Pair<Boolean, String> result = productBLL.addProduct(product);
            if (!result.getKey()) {
                allProductsAdded = false;
                JOptionPane.showMessageDialog(null, result.getValue(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
        if (allProductsAdded) {
            JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    private void addAllRecipe() {
        for (Recipe recipe : materialList) {
            Pair<Boolean, String> result = recipeBLL.addRecipe(recipe);
            if (!result.getKey()) {
                JOptionPane.showMessageDialog(null, result.getValue(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                break;
            }
        }
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
            search.setData(search(text));
            if (search.getItemSize() > 0 && !txtSearch.getText().isEmpty()) {
                menu.show(txtSearch, 0, txtSearch.getHeight());
                menu.setPopupSize(menu.getWidth(), (search.getItemSize() * 35) + 2);
            } else {
                menu.setVisible(false);
            }
        }
    }

    private List<DataSearch> search(String text) {
        List<DataSearch> list = new ArrayList<>();
        List<Material> materials = materialBLL.findMaterials("name", text);
        for (Material m : materials) {
            if (list.size() == 7) {
                break;
            }
            list.add(new DataSearch(m.getName()));
        }
        return list;
    }

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            search.keyUp();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            search.keyDown();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = search.getSelectedText();
            txtSearch.setText(text);

        }
        menu.setVisible(false);

    }//GEN-LAST:event_txtSearchKeyPressed

}

