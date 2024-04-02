package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.RecipeBLL;
import com.coffee.DTO.Material;
import com.coffee.DTO.Product;
import com.coffee.DTO.Recipe;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailProductGUI extends DialogFormDetail_1 {

    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;

    private JLabel lblListMaterial;
    private JLabel material;
    private DataTable dataTable;

    private RoundedPanel containerInforMaterial;
    private RoundedScrollPane scrollPane;
    private RoundedPanel containerDataTable;
    private JComboBox<String> CbListMaterial;
    private RecipeBLL recipeBLL = new RecipeBLL();
    private List<String> AtributeProduct = new ArrayList<>();
    private String[] columnNames;
    private JPanel panelSize;
    private JTextField txtNameProduct;
    private JTextField txtPrice ;
    private JTextField txtCategory;
    private JLabel lblName;
    private JLabel lblCategory ;
    private JLabel lblSize ;
    private JLabel lblPrice ;
    private JLabel selectedLabel;
    private List<Product> Products;
    private int product_id;
    private String imageProduct;
    public DetailProductGUI(List<Product> Products) {
        super();
        super.setTitle("Chi tiết sản phẩm");
        this.Products = Products;
        this.product_id = Products.get(0).getId();
        this.imageProduct = Products.get(0).getImage();
        init(Products);
        setVisible(true);

    }

    public void init( List<Product> Products) {

        titleName = new JLabel("Chi tiết sản phẩm");
        title.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        title.add(titleName);

        top.setLayout(new FlowLayout());

        containerAtributeProduct = new RoundedPanel();
        containerAtributeProduct.setLayout(new MigLayout("", "[]60[]200", "15[]15[]15[]15"));
        containerAtributeProduct.setBackground(new Color(217, 217, 217));
        containerAtributeProduct.setPreferredSize(new Dimension(600, 200));

        containerImage = new RoundedPanel();
        containerImage.setLayout(new MigLayout("", "[]", "[][]"));
        containerImage.setBackground(new Color(217, 217, 217));
        containerImage.setPreferredSize(new Dimension(200, 200));

        JPanel PanelImage = new JPanel();
        PanelImage.setPreferredSize(new Dimension(150, 150));
        PanelImage.setBackground(new Color(217, 217, 217));

        JLabel lblImage = new JLabel();
        ImageIcon icon = new FlatSVGIcon("image/Product/" +imageProduct + ".svg");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        lblImage.setIcon(icon);

        PanelImage.add(lblImage);



        containerImage.add(PanelImage, "alignx center,wrap");
        top.add(containerAtributeProduct);
        top.add(containerImage);
        EmptyBorder emptyBorder = new EmptyBorder(0, 30, 0, 0);
        top.setBorder(emptyBorder);

        columnNames = new String[]{"ID nguyên liệu", "Tên nguyên liệu", "Số lượng", "Đơn vị"};
        dataTable = new DataTable(new Object[0][0], columnNames,
                null,
                false, false, false, 4);
        int[] columnWidths = {50, 300, 50, 50};

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



        lblName = createLabel("Tên sản phẩm");
        lblCategory = createLabel("Thể loại");
        lblSize = createLabel("Size");
        lblPrice = createLabel("Giá bán");

        txtNameProduct = createTextField();
        txtCategory = createTextField();
        txtPrice = createTextField();

        panelSize = new JPanel();
        panelSize = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0));
        panelSize.setBackground(new Color(217, 217, 217));
        panelSize.setPreferredSize(new Dimension(350, 40));

        txtNameProduct.setText(Products.get(0).getName());
        txtCategory.setText(Products.get(0).getCategory());

        String defaultSize = "";
        for (Product product : Products) {
            String size = product.getSize();
            JLabel label = new JLabel(size);
            label.setPreferredSize(new Dimension(30, 30));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFocusable(true);
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);

            if (defaultSize.isEmpty()) {
                defaultSize = size;
            }

            if (size.equals(defaultSize)) {
                selectedLabel = label;
                label.setBackground(new Color(59, 130, 198));
                label.setForeground(Color.WHITE);
                loadPriceBySize(size);
                loadRecipeBySize(size);
            }

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
                    loadPriceBySize(size);
                    loadRecipeBySize(size);
                }
            });

            panelSize.add(label);
            panelSize.revalidate();
            panelSize.repaint();
        }

        containerAtributeProduct.add(lblName);
        containerAtributeProduct.add(txtNameProduct,"wrap");

        containerAtributeProduct.add(lblCategory);
        containerAtributeProduct.add(txtCategory,"wrap");

        containerAtributeProduct.add(lblSize);
        containerAtributeProduct.add(panelSize,"wrap");

        containerAtributeProduct.add(lblPrice);
        containerAtributeProduct.add(txtPrice,"wrap");

        lblListMaterial = new JLabel("Danh sách nguyên liệu");

        lblListMaterial.setFont(new Font("Public Sans", Font.BOLD, 16));

        center.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        center.add(lblListMaterial);

        containerInforMaterial = new RoundedPanel();
        containerInforMaterial.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        containerInforMaterial.setBackground(new Color(217, 217, 217));

        EmptyBorder emptyBorder1 = new EmptyBorder(0, 30, 0, 0);
        containerInforMaterial.setBorder(emptyBorder1);
        bottom.add(containerInforMaterial, BorderLayout.NORTH);



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
//        textField.setEditable(false);
        textField.setFocusable(false);
        return textField;
    }
    private void loadPriceBySize(String size){
        for(Product product: Products){
            if(product.getSize().equals(size)){
                String price = product.getPrice().toString();
                txtPrice.setText(price);
                return;
            }
        }
        txtPrice.setText("0");
    }
    private void loadRecipeBySize(String size) {
        System.out.println(size);
        System.out.println(product_id);
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
//        List<List<Object>> searchResults = recipeBLL.searchRecipesByProduct(product_id, size);
//        for (List<Object> row : searchResults) {
//            model.addRow(row.toArray());
//        }
    }
}
