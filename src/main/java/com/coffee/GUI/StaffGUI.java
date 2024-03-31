package com.coffee.GUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddStaffGUI;
import com.coffee.GUI.DialogGUI.FormDetailGUI.*;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditStaffGUI;
import com.coffee.GUI.components.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
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
    private final HomeGUI homeGUI;

    public StaffGUI(List<Function> functions, HomeGUI homeGUI) {
        super();
        this.functions = functions;
        this.homeGUI = homeGUI;
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
        jComboBoxSearch = new JComboBox<>(new String[]{"Bộ Lọc", "Tên", "Chức Vụ"});

        columnNames = new String[]{"Mã Nhân Viên", "CCCD", "Tên", "Số Điện Thoại", " Chức Vụ"};
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
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
        jButtonSearch.addActionListener(e -> searchStaffs());
        SearchPanel.add(jButtonSearch);

        jComboBoxSearch.setBackground(new Color(29, 78, 216));
        jComboBoxSearch.setForeground(Color.white);
        jComboBoxSearch.setPreferredSize(new Dimension(120, 30));
        jComboBoxSearch.addActionListener(e -> selectSearchFilter());
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
                    new AddStaffGUI();
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
            JOptionPane.showMessageDialog(null, "Nhập Thông Tin Cần Tìm Kiếm");
            loadDataTable(staffBLL.getData(staffBLL.searchStaffs("deleted = 0")));
        } else {
            selectSearchFilter();
        }
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(jComboBoxSearch.getSelectedItem()).toString().contains("Bộ Lọc")) {
            loadDataTable(staffBLL.getData(staffBLL.findStaffs("id", jTextFieldSearch.getText())));
        } else {
            if (Objects.requireNonNull(jComboBoxSearch.getSelectedItem()).toString().contains("Tên")) {
                searchStaffByName();
            } else {
                searchStaffByRole();
            }
        }
    }

    private void searchStaffByName() {
        String searchText = jTextFieldSearch.getText().trim();
        if (searchText.isEmpty()) {
            loadDataTable(staffBLL.getData(staffBLL.searchStaffs("deleted = 0")));
        } else {
            List<Staff> foundStaff = staffBLL.findStaffs("name", searchText);
            if (!foundStaff.isEmpty()) {
                loadDataTable(staffBLL.getData(foundStaff));
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên có tên '" + searchText + "'", "Không tìm thấy", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private void searchStaffByRole() {
        if (jTextFieldSearch.getText().isEmpty()) {

            loadDataTable(staffBLL.getData(staffBLL.searchStaffs("deleted = 0")));
        } else {
            String roleName = jTextFieldSearch.getText();
            List<Role> roles = new RoleBLL().searchRoles("name = '" + roleName + "'");

            if (!roles.isEmpty()) {
                int roleId = roles.get(0).getId();
                List<Role_Detail> roleDetails = new Role_DetailBLL().searchRole_details("role_id = " + roleId);

                if (!roleDetails.isEmpty()) {
                    List<Integer> staffIds = new ArrayList<>();
                    for (Role_Detail roleDetail : roleDetails) {
                        staffIds.add(roleDetail.getStaff_id());
                    }
                    List<Staff> staffs = new ArrayList<>();
                    for (Integer staffId : staffIds) {
                        List<Staff> tempStaffs = staffBLL.searchStaffs("id = " + staffId);
                        if (!tempStaffs.isEmpty()) {
                            staffs.add(tempStaffs.get(0));
                        }
                    }

                    loadDataTable(staffBLL.getData(staffs));
                } else {
                    JOptionPane.showMessageDialog(this, "Không có nhân viên nào có chức vụ '" + roleName + "'", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy chức vụ '" + roleName + "'", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        if (objects.length == 0) {
            return;
        }

        Object[][] data = new Object[objects.length][objects[0].length];

        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);

            int staffId = Integer.parseInt((String) data[i][0]);
            List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_detailsByStaff(staffId);
            if (!role_detailList.isEmpty()) {
                Role_Detail roleDetail = role_detailList.get(0);
                Role role = new RoleBLL().searchRoles("id = " + roleDetail.getRole_id()).get(0);
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = role.getName();
            } else {
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = "Chưa có chức vụ";
            }


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


        for (Object[] object : data) {
            model.addRow(object);
        }
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail)
            new DetailStaffGUI(staffBLL.searchStaffs("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

        if (edit && indexColumn == indexColumnEdit) {
            new EditStaffGUI(staffBLL.searchStaffs("deleted = 0").get(indexRow), homeGUI);
            System.out.println(" đã nhấn ởddaaay ");// Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá
            refresh();
        }
        if (remove && indexColumn == indexColumnRemove)
            deleteStaff(staffBLL.searchStaffs("deleted = 0").get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

    }

    private void deleteStaff(Staff staff) {
        if (dataTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xoá.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] options = new String[]{"Huỷ", "Xác nhận"};
        int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá nhà nhân viên?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 1) {
            Pair<Boolean, String> result = staffBLL.deleteStaff(staff);
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



