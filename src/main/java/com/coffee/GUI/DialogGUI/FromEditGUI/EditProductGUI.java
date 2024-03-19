package com.coffee.GUI.DialogGUI.FromEditGUI;

import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.RecipeBLL;
import com.coffee.DTO.Material;
import com.coffee.DTO.Product;
import com.coffee.DTO.Recipe;
import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.components.CustomPanelRenderer;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.ss.formula.functions.DProduct;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditProductGUI extends DialogFormDetail_1 {
    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;

    private JButton btnImage;
    private JLabel lblListMaterial;
    private DataTable dataTable ;
    private JButton buttonCancel;
    private JButton buttonEdit;

    private RoundedPanel  containerInforMaterial;
    private RoundedScrollPane scrollPane;
    private RoundedPanel containerDataTable;
    private JComboBox<String> CbListMaterial;
    private List<String> AtributeProduct = new ArrayList<>();
    private String[] columnNames;
    private Product product = new Product();
    private final RecipeBLL recipeBLL = new RecipeBLL();
    private JLabel iconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
    private JLabel iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
    private MaterialBLL materialBLL = new MaterialBLL();
    public EditProductGUI( ArrayList<Object[]> allProducts) {
        super();
        super.setTitle("Sửa sản phẩm");
        this.product = product;
        init(allProducts);
        setVisible(true);

    }

    public void init( ArrayList<Object[]> allProducts) {
        Object[] product = allProducts.get(0);
        titleName = new JLabel("Sửa sản phẩm");
        title.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        title.add(titleName);

        top.setLayout(new FlowLayout());

        containerAtributeProduct = new RoundedPanel();
        containerAtributeProduct.setLayout(new MigLayout("", "[]60[]200", "15[]15[]15[]15"));
        containerAtributeProduct.setBackground(new Color(217, 217, 217));
        containerAtributeProduct.setPreferredSize(new Dimension(600,200));

        containerImage = new RoundedPanel();
        containerImage.setLayout(new MigLayout("", "[]", "[][]"));
        containerImage.setBackground(new Color(217, 217, 217));
        containerImage.setPreferredSize(new Dimension(200, 200));

        JPanel PanelImage = new JPanel();
        PanelImage.setPreferredSize(new Dimension(150,150));
        PanelImage.setBackground(new Color(217,217,217));

        JLabel lblImage = new JLabel();
        ImageIcon icon = new FlatSVGIcon("image/Product/" + product[5] + ".svg");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        lblImage.setIcon(icon);

        PanelImage.add(lblImage);

        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(100, 30));

        containerImage.add(PanelImage,"alignx center,wrap");
        containerImage.add(btnImage,"alignx center");

        top.add(containerAtributeProduct);
        top.add(containerImage);
        EmptyBorder emptyBorder = new EmptyBorder(0, 30, 0, 0);
        top.setBorder(emptyBorder);

        JLabel lblName = createLabel("Tên sản phẩm");
        JLabel lblSize = createLabel("Size");
        JLabel lblPrice = createLabel("Giá bán");
        JLabel lblCategory = createLabel("Thể loại");

        JTextField txtName = createTextField();
        JComboBox <String>  cbSize = createComboBox();
        JTextField txtPrice = createTextField();
        JTextField txtCategory = createTextField();


        containerAtributeProduct.add(lblName);
        containerAtributeProduct.add(txtName,"wrap");

        containerAtributeProduct.add(lblSize);
        containerAtributeProduct.add(cbSize,"wrap");

        containerAtributeProduct.add(lblPrice);
        containerAtributeProduct.add(txtPrice,"wrap");

        containerAtributeProduct.add(lblCategory);
        containerAtributeProduct.add(txtCategory,"wrap");




        txtName.setText((String) product[1]);

        ArrayList<String> sizes = (ArrayList<String>) product[4];
        for (String size : sizes) {
            cbSize.addItem(size);
        }
        ArrayList<String> prices = (ArrayList<String>) product[3];
        txtPrice.setText(String.valueOf(prices.get(0)));

        txtCategory.setText((String) product[2]);


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
        for (String string : new String[]{"Tên nguyên liệu", "Số lượng", "Đơn vị", "Thêm"}) {
            if (string.equals("Thêm")) {
                JButton btnThem = new JButton("Thêm");
                btnThem.setPreferredSize(new Dimension(100, 40));
                btnThem.setBackground(new Color(0, 182, 62));
                btnThem.setFont(new Font("Public Sans", Font.BOLD, 16));
                btnThem.setForeground(Color.WHITE);
                containerInforMaterial.add(btnThem);
                continue;
            }
            JLabel label = new JLabel();
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            label.setPreferredSize(null);
            containerInforMaterial.add(label);

            JTextField textField = new JTextField();
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            containerInforMaterial.add(textField);
            if (string.equals("Tên nguyên liệu")) {
                textField.setPreferredSize(new Dimension(240, 30));
            }
            else {
                textField.setPreferredSize(new Dimension(60, 30));
            }
        }
        columnNames = new String[]{"STT","Tên nguyên liệu", "Số lượng", "Đơn vị","Sửa","Xóa"};
        dataTable = new DataTable(new Object[0][0], columnNames,
                null,
                false, true, true, 6);
        int[] columnWidths = {50,300, 50, 50,50,50};

        for (int i = 0; i < columnWidths.length; i++) {
            dataTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        dataTable.getColumnModel().getColumn(4).setCellRenderer(new CustomPanelRenderer());
        dataTable.getColumnModel().getColumn(5).setCellRenderer(new CustomPanelRenderer());

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
        int product_id = Integer.parseInt(product[0].toString());
        String size = cbSize.getSelectedItem().toString();
        loadMaterial(product_id,size);

        cbSize.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox<String> combo = (JComboBox<String>) e.getSource();
                    int sizeIndex = (int) combo.getSelectedIndex();
                    String size = cbSize.getSelectedItem().toString();
                    txtPrice.setText(String.valueOf(prices.get(sizeIndex)));
                    loadMaterial(product_id,size);
                }
            }
        });
        buttonCancel = new JButton("Huỷ");
        buttonEdit = new JButton("Cập nhật");
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

        buttonEdit.setPreferredSize(new Dimension(100, 30));
        buttonEdit.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonEdit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });
        containerButton.add(buttonEdit);
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

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(350, 30));
        comboBox.setFont(new Font("Public Sans", Font.PLAIN, 14));
        comboBox.setBackground(new Color(245, 246, 250));
        return comboBox;
    }
    private void loadMaterial(int product_id,String size){
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        List<List<Object>> searchResults =recipeBLL.searchRecipesByProduct(product_id,size);
        Object[][] data = new Object[searchResults.size()][6];
        for (int i = 0; i < searchResults.size(); i++) {
            List<Object> recipe = searchResults.get(i);
            // Chèn STT vào cột đầu tiên
            data[i][0] = i + 1;
            // Chèn dữ liệu còn lại từ danh sách kết quả
            for (int j = 1; j < recipe.size(); j++) {
                data[i][j] = recipe.get(j - 1);
            }
            // Thêm iconEdit và iconRemove vào cột cuối cùng
            data[i][4] = iconEdit;
            data[i][5] = iconRemove;
        }
        for (Object[] object : data) {
            model.addRow(object);
        }
    }
}

