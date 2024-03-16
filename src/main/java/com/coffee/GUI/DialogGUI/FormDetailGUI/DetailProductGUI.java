package com.coffee.GUI.DialogGUI.FormDetailGUI;

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
import java.util.ArrayList;
import java.util.List;

public class DetailProductGUI extends DialogFormDetail_1 {

    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;

    private JButton btnImage;
    private JLabel lblListMaterial;
    private JLabel material;
    private DataTable dataTable ;

    private RoundedPanel  containerInforMaterial;
    private RoundedScrollPane scrollPane;
    private RoundedPanel containerDataTable;
    private JComboBox<String> CbListMaterial;
    private List<String> AtributeProduct = new ArrayList<>();
    private String[] columnNames;
    public DetailProductGUI() {
        super();
        super.setTitle("Chi tiết sản phẩm");
        init();
        setVisible(true);

    }

    public void init() {
        titleName = new JLabel("Chi tiết sản phẩm");
        title.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        title.add(titleName);

        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(100, 30));

        ImageIcon icon = new FlatSVGIcon("image/Product/" + "SP01" + ".svg");
        JLabel lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(200, 150));
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        lblImage.setIcon(icon);

        top.setLayout(new GridBagLayout());

        containerAtributeProduct = new RoundedPanel();
        containerAtributeProduct.setLayout(new MigLayout("", "[]30[]30", "[]20[]20[]"));
        containerAtributeProduct.setBackground(new Color(217, 217, 217));
        top.add(containerAtributeProduct);
        containerImage = new RoundedPanel();
        containerImage.setLayout(new MigLayout("", "[]", "[][]"));

        containerImage.setBackground(new Color(217, 217, 217));
        containerImage.setPreferredSize(new Dimension(300, 200));
        containerImage.add(lblImage,"alignx center, wrap");
        containerImage.add(btnImage,"alignx center");

        lblImage.setBackground(new Color(0, 0, 0));
        top.add(containerImage);
        GridBagConstraints gbcAtributeProduct = new GridBagConstraints();
        gbcAtributeProduct.fill = GridBagConstraints.BOTH;
        gbcAtributeProduct.gridx = 0;
        gbcAtributeProduct.gridy = 0;
        gbcAtributeProduct.weightx = 1.0;
        gbcAtributeProduct.weighty = 1.0;
        gbcAtributeProduct.insets = new Insets(0, 0, 0, 10); // Khoảng cách bên phải

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 50, 0, 200); // Khoảng cách bên phải

        top.add(containerAtributeProduct, gbc);

        gbc.gridx++;
        gbc.weightx = 0.0;

        gbc.insets = new Insets(0, 0, 0, 0); // Không có khoảng cách

        top.add(containerImage, gbc);

        for (String string : new String[]{"Tên sản phẩm", "Size", "Giá bán", "Loại"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            containerAtributeProduct.add(label);

            JTextField textField = new JTextField();

            if (string.equals("Tên sản phẩm")) {
//                textField.setText(AtributeProduct.get(0));
            }
            if (string.equals("Size")) {
                JComboBox<String> size = new JComboBox();
//                textField.setText(AtributeProduct.get(1));
                size.setPreferredSize(new Dimension(350, 30));
                size.setFont((new Font("Public Sans", Font.PLAIN, 14)));
                size.setBackground(new Color(245, 246, 250));
                containerAtributeProduct.add(size, "wrap");
                continue;
            }
            if (string.equals("Giá bán")) {
//                textField.setText(AtributeProduct.get(2));
            }
            if (string.equals("Loại")) {
//                textField.setText(AtributeProduct.get(3));
            }

            textField.setEditable(false);
            textField.setEnabled(false);
            textField.setPreferredSize(new Dimension(350, 40));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            containerAtributeProduct.add(textField, "wrap");

        }
        lblListMaterial = new JLabel("Danh sách nguyên liệu");

        lblListMaterial.setFont(new Font("Public Sans", Font.BOLD, 16));

        center.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        center.add(lblListMaterial);

        containerInforMaterial = new RoundedPanel();
        containerInforMaterial.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        containerInforMaterial.setBackground(new Color(217, 217, 217));

        EmptyBorder emptyBorder = new EmptyBorder(0, 30, 0, 0);
        containerInforMaterial.setBorder(emptyBorder);
        bottom.add(containerInforMaterial, BorderLayout.NORTH);
        for (String string : new String[]{"Tên nguyên liệu", "Số lượng", "Đơn vị", "Thêm"}) {
            if (string.equals("Thêm")) {
                JButton btnThem = new JButton("Thêm");
                btnThem.setPreferredSize(new Dimension(100, 40));
                btnThem.setBackground(new Color(0, 182, 62));
                btnThem.setFont(new Font("Public Sans", Font.BOLD, 16)); // Đặt font chữ đậm
                btnThem.setForeground(Color.WHITE); // Đặt màu chữ trắng
                containerInforMaterial.add(btnThem);
                continue;
            }
            JLabel label = new JLabel();
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            label.setPreferredSize(null);
            containerInforMaterial.add(label);

            JTextField textField = new JTextField();
            textField.setEditable(false);
            textField.setEnabled(false);
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
        columnNames = new String[]{"STT","Tên nguyên liệu", "Số lượng", "Đơn vị"};
        dataTable = new DataTable(new Object[0][0], columnNames,
               null,
                false, false, false, 4);
        int[] columnWidths = {50,300, 50, 50};

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
        // Tạo dữ liệu mẫu
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[][] data = {
                {1, "Nguyên liệu 1", 10, "Đơn vị 1"},
                {2, "Nguyên liệu 2", 20, "Đơn vị 2"},
                {3, "Nguyên liệu 3", 30, "Đơn vị 3"},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1"},
                {2, "Nguyên liệu 2", 20, "Đơn vị 2"},
                {3, "Nguyên liệu 3", 30, "Đơn vị 3"},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1"},
                {2, "Nguyên liệu 2", 20, "Đơn vị 2"},
                {3, "Nguyên liệu 3", 30, "Đơn vị 3"},
                // Thêm dữ liệu khác nếu cần
        };
        for (Object[] object : data) {
            model.addRow(object);
        }

        JTableHeader jTableHeader = dataTable.getTableHeader();
        jTableHeader.setBackground(new Color(232, 206, 180));
    }
}
