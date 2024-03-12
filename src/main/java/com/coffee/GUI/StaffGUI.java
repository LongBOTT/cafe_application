package com.coffee.GUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.GUI.*;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddSupplierGUI;
import com.coffee.GUI.DialogGUI.FormDetailGUI.*;
import com.coffee.GUI.DialogGUI.*;
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

import java.util.List;

public class StaffGUI extends Layout1 {
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JButton jButtonSearch;
    private JComboBox<String> jComboBoxSearch;
    private List<Function> functions;
    private StaffBLL staffBLL = new StaffBLL();
    private DataTable dataTable;
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private int indexColumnRemove = -1;
    private boolean detail = false;
    private boolean edit = false;
    private boolean remove = false;
    private String[] columnNames;

    private Role_detail roleDetail = new Role_detail();

    private Role role = new Role();

    public StaffGUI(List<Function> functions) {


        super();
        this.functions = functions;
        if (functions.stream().anyMatch(f -> f.getName().equals("view")))
            detail = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("edit")))
            edit = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("remove")))
            remove = true;
        init(functions);
    }

    private void init(List<Function> functions) {
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");
        jComboBoxSearch = new JComboBox<>(new String[]{"Bộ Lọc","Tên", "Mã Nhân Viên", "Chức Vụ", "Số Điện Thoại",});

        columnNames = new String[]{"STT", "Mã Nhân Viên", "Tên" , "Số Điện Thoại" , " Chức Vụ"};
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
                detail, edit, remove, 5); // table hiển thị các thuộc tính "Mã NCC", "Tên NCC", "SĐT", "Email" nên điền 4
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        bottom.add(scrollPane, BorderLayout.CENTER);

        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(280, 40));
        SearchPanel.add(containerSearch);

        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Nhập nội dung tìm kiếm");
        jTextFieldSearch.setPreferredSize(new Dimension(300, 30));
        containerSearch.add(jTextFieldSearch);
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
        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 35));
//        jButtonSearch.addActionListener(e -> searchSuppliers());
        SearchPanel.add(jButtonSearch);

        jComboBoxSearch.setBackground(new Color(29, 78, 216));
        jComboBoxSearch.setForeground(Color.white);
        jComboBoxSearch.setPreferredSize(new Dimension(120, 35));
//        jComboBoxSearch.addActionListener(e -> selectSearchFilter());
        SearchPanel.add(jComboBoxSearch);

        loadDataTable(staffBLL.getData(staffBLL.searchStaffs("deleted = 0")));

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
    }

    public void refresh() {
        jTextFieldSearch.setText("");
        jComboBoxSearch.setSelectedIndex(0);
        loadDataTable(staffBLL.getData(staffBLL.searchStaffs("deleted = 0")));
    }
    private void searchStaffs() {
        if (jTextFieldSearch.getText().isEmpty()) {
            loadDataTable(staffBLL.getData(staffBLL.searchStaffs("deleted = 0")));
        } else {
            selectSearchFilter();
        }
    }
    private void selectSearchFilter() {
        if (Objects.requireNonNull(jComboBoxSearch.getSelectedItem()).toString().contains("SĐT")) {
           // searchSuppliersByPhone();
        } else {
            if (Objects.requireNonNull(jComboBoxSearch.getSelectedItem()).toString().contains("Email")) {
                searchSuppliersByEmail();
            } else {
                loadDataTable(staffBLL.getData(staffBLL.findStaffs("staffNo", jTextFieldSearch.getText())));
            }
        }
    }
    private void searchSuppliersByName() {
        if (jTextFieldSearch.getText().isEmpty()) {
            loadDataTable(staffBLL.getData(staffBLL.searchStaffs("deleted = 0")));
        } else {
            loadDataTable(staffBLL.getData(staffBLL.findStaffs("name", jTextFieldSearch.getText())));
        }
    }

    private void searchSuppliersByEmail() {
        if (jTextFieldSearch.getText().isEmpty()) {
           // loadDataTable(supplierBLL.getData(supplierBLL.searchSuppliers("deleted = 0")));
        } else {
           // loadDataTable(supplierBLL.getData(supplierBLL.findSuppliers("email", jTextFieldSearch.getText())));
        }
    }
    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        Object[][] data = new Object[objects.length][objects[0].length];


        for (Object[] object : objects) {
            try {
                int staffId = Integer.parseInt(object[1].toString());
                List<Role_detail> role_detailList = new Role_detailBLL().searchRole_details("staff_id = " + staffId);
                Role_detail roleDetail = role_detailList.get(role_detailList.size()-1);
                Role role = new RoleBLL().searchRoles("id = " + roleDetail.getRole_id()).get(0);
                object = Arrays.copyOf(object, object.length+1);
                object[object.length-1] = role.getName();
            } catch (NumberFormatException e) {
                System.out.println("Không thể chuyển đổi chuỗi thành số nguyên: " + object[1].toString());
            }
        }


        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);

            if (detail) {
                JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconDetail;
            }
            if (edit) {
                JLabel iconEdit = new JLabel(new FlatSVGIcon("icon/edit.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconEdit;
            }
            if (remove) {
                JLabel iconRemove = new JLabel(new FlatSVGIcon("icon/remove.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconRemove;
            }
        }


        for (Object[] object : objects) {
            model.addRow(object);
        }
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail)
            //new DetailSupplierGUI(staffBLL.searchStaffs("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

            if (detail && indexColumn == indexColumnEdit) {
                //new EditSupplierGUI(staffBLL.searchStaffs("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
                refresh();
            }

//       if (detail && indexColumn == indexColumnRemove)
//          deleteSupplier(staffBLL.searchStaffs("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

    }

//    private void deleteSupplier(Staff staff) {
//        if (dataTable.getSelectedRow() == -1) {
//            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà cung cấp cần xoá.",
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        String[] options = new String[]{"Huỷ", "Xác nhận"};
//        int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá nhà cung cấp?",
//                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//        if (choice == 1) {
//            Pair<Boolean, String> result = staffBLL.deleteStaff(staff);
//            if (result.getKey()) {
//                JOptionPane.showMessageDialog(null, result.getValue(),
//                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//                refresh();
//            } else {
//                JOptionPane.showMessageDialog(null, result.getValue(),
//                        "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
}



