package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.GUI.DialogGUI.DialogFormDetail_1;
import com.coffee.GUI.components.CustomPanelRenderer;
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
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class AddProductGUI extends DialogFormDetail_1 {
    private JLabel titleName;
    private RoundedPanel containerAtributeProduct;
    private RoundedPanel containerImage;

    private JButton btnImage;
    private JLabel lblListMaterial;
    private JLabel material;
    private DataTable dataTable ;
    private JButton buttonCancel;
    private JButton buttonEdit;

    private RoundedPanel  containerInforMaterial;
    private RoundedScrollPane scrollPane;
    private RoundedPanel containerDataTable;
    private JComboBox<String> CbListMaterial;
    private List<String> AtributeProduct = new ArrayList<>();
    private String[] columnNames;
    public AddProductGUI() {
        super();
        super.setTitle("Thêm sản phẩm");
        init();
        setVisible(true);

    }

    public void init() {
        titleName = new JLabel("Thêm sản phẩm");
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
//        PanelImage.setBackground(new Color(217,217,217));

        JLabel lblImage = new JLabel();
        PanelImage.add(lblImage);


        btnImage = new JButton("Thêm ảnh");
        btnImage.setPreferredSize(new Dimension(100, 30));

        containerImage.add(PanelImage,"alignx center,wrap");
        containerImage.add(btnImage,"alignx center");

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

            textField.setPreferredSize(new Dimension(350, 30));
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
        // Tạo dữ liệu mẫu
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/edit.svg"));
        JLabel iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
        Object[][] data = {
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
                {1, "Nguyên liệu 1", 10, "Đơn vị 1",iconDetail,iconRemove},
        };
        for (Object[] object : data) {
            model.addRow(object);
        }

        JTableHeader jTableHeader = dataTable.getTableHeader();
        jTableHeader.setBackground(new Color(232, 206, 180));
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

}

