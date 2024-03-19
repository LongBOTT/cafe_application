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
    private DataTable dataTable;

    private RoundedPanel containerInforMaterial;
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
        ImageIcon icon = new FlatSVGIcon("image/Product/" + "SP01" + ".svg");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        lblImage.setIcon(icon);

        PanelImage.add(lblImage);

        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(100, 30));

        containerImage.add(PanelImage, "alignx center,wrap");
        containerImage.add(btnImage, "alignx center");

        top.add(containerAtributeProduct);
        top.add(containerImage);
        EmptyBorder emptyBorder = new EmptyBorder(0, 30, 0, 0);
        top.setBorder(emptyBorder);

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

        EmptyBorder emptyBorder1 = new EmptyBorder(0, 30, 0, 0);
        containerInforMaterial.setBorder(emptyBorder1);
        bottom.add(containerInforMaterial, BorderLayout.NORTH);
        columnNames = new String[]{"STT", "Tên nguyên liệu", "Số lượng", "Đơn vị"};
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

        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[][] data = {
                {1, "Nguyên liệu 1", 10, "Đơn vị 1"},
                {2, "Nguyên liệu 2", 20, "Đơn vị 2"},
                {3, "Nguyên liệu 3", 30, "Đơn vị 3"},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1"},
                {2, "Nguyên liệu 2", 20, "Đơn vị 2"},
                {3, "Nguyên liệu 3", 30, "Đơn vị 3"},
        };
        for (Object[] object : data) {
            model.addRow(object);
        }

        JTableHeader jTableHeader = dataTable.getTableHeader();
        jTableHeader.setBackground(new Color(232, 206, 180));
    }
}
