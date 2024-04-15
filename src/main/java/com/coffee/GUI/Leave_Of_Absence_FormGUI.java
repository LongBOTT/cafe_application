package com.coffee.GUI;

import com.coffee.BLL.RoleBLL;
import com.coffee.BLL.Role_DetailBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.BLL.Leave_Of_Absence_FormBLL;
import com.coffee.DTO.*;

import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailLeave_Of_Absence_FormGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditLeave_Of_Absence_FormGUI;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.Layout2;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

public class Leave_Of_Absence_FormGUI extends Layout2 {
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JTextField[] jTextFieldDate;
    private JTextField[] dateTextField;
    private JDateChooser[] jDateChooser;
    private JButton jButtonSearch;
    private List<Function> functions;
    private Leave_Of_Absence_FormBLL leave_Of_Absence_FormBLL = new Leave_Of_Absence_FormBLL();
    private DataTable dataTable;
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private boolean detail = false;
    private boolean edit = false;
    private String[] columnNames;
    private StaffBLL staffBLL = new StaffBLL();
    private Date date;

    public Leave_Of_Absence_FormGUI(List<Function> functions) {
        super();
        this.functions = functions;
        if (functions.stream().anyMatch(f -> f.getName().equals("view")))
            detail = true;
        if (functions.stream().anyMatch(f -> f.getName().equals("edit")))
            edit = true;
        init(functions);
    }

    private void init(List<Function> functions) {
        date = new Date();
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];
        jTextFieldDate = new JTextField[2];

        columnNames = new String[]{"Chức vụ", "Họ tên", "Ngày tạo đơn", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"};

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

        dataTable = new DataTable(new Object[0][0], columnNames,
                e -> selectFunction(),
                detail, edit, false, 6);
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        bottom.add(scrollPane, BorderLayout.CENTER);

        for (int i = 0; i < 2; i++) {
            jTextFieldDate[i] = new JTextField();
            jTextFieldDate[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
            jTextFieldDate[i].setPreferredSize(new Dimension(200, 30));
            jTextFieldDate[i].setAutoscrolls(true);

            jDateChooser[i] = new JDateChooser();
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(new Dimension(200, 30));
            jDateChooser[i].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

            dateTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
            dateTextField[i].setFont(new Font("Lexend", Font.BOLD, 14));
            dateTextField[i].setBackground(new Color(245, 246, 250));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateTextField[i].setText(LocalDate.now().format(formatter));
            
            if (i == 0) {
                JLabel jLabel = new JLabel("Từ Ngày");
                jLabel.setFont(new Font("Lexend", Font.BOLD, 14));
                FilterDatePanel.add(jLabel);
            } else {
                JLabel jLabel = new JLabel("Đến Ngày");
                jLabel.setFont(new Font("Lexend", Font.BOLD, 14));
                FilterDatePanel.add(jLabel);
            }
            FilterDatePanel.add(jDateChooser[i]);
        }

        containerSearch.setLayout(new MigLayout("", "10[]10[]10", ""));
        containerSearch.setBackground(new Color(245, 246, 250));
        containerSearch.setPreferredSize(new Dimension(280, 40));
        SearchPanel.add(containerSearch);

        iconSearch.setIcon(new FlatSVGIcon("icon/search.svg"));
        containerSearch.add(iconSearch);

        jTextFieldSearch.setBackground(new Color(245, 246, 250));
        jTextFieldSearch.setBorder(BorderFactory.createEmptyBorder());
        jTextFieldSearch.putClientProperty("JTextField.placeholderText", "Nhập tên nhân viên cần tìm kiếm");
        jTextFieldSearch.setPreferredSize(new Dimension(250, 30));
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchLeave_Of_Absence_Forms();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchLeave_Of_Absence_Forms();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchLeave_Of_Absence_Forms();
            }
        });
        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
        jButtonSearch.addActionListener(e -> searchLeave_Of_Absence_Forms());
        SearchPanel.add(jButtonSearch);

        loadDataTable(leave_Of_Absence_FormBLL.getData(leave_Of_Absence_FormBLL.searchLeave_Of_Absence_Forms()));

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
    }

    public void refresh() {
        jTextFieldSearch.setText("");
        jDateChooser[0].getDateEditor().setDate(null);
        jDateChooser[1].getDateEditor().setDate(null);
        loadDataTable(leave_Of_Absence_FormBLL.getData(leave_Of_Absence_FormBLL.searchLeave_Of_Absence_Forms()));
    }

    private void searchLeave_Of_Absence_Forms() {
        List<Leave_Of_Absence_Form> work_scheduleList = leave_Of_Absence_FormBLL.searchLeave_Of_Absence_Forms();
        if (jTextFieldSearch.getText().isEmpty() && jDateChooser[0].getDateEditor().getDate() == null && jDateChooser[1].getDateEditor().getDate() == null) {
            loadDataTable(leave_Of_Absence_FormBLL.getData(work_scheduleList));
        } else {
            if (!jTextFieldSearch.getText().isEmpty()) {
                List<Integer> staffIDList = new ArrayList<>();
                for (Staff staff : staffBLL.findStaffs("name", jTextFieldSearch.getText()))
                    staffIDList.add(staff.getId());
                work_scheduleList.removeIf(work_schedule -> !staffIDList.contains(work_schedule.getStaff_id()));
            }
            if (jDateChooser[0].getDateEditor().getDate() != null || jDateChooser[1].getDateEditor().getDate() != null) {
                if (jDateChooser[0].getDateEditor().getDate() != null && jDateChooser[1].getDateEditor().getDate() != null) {
                    Date startDate = jDateChooser[0].getDate();
                    Date endDate = jDateChooser[1].getDate();
                    if (startDate.after(endDate)) {
                        JOptionPane.showMessageDialog(null, "Ngày bắt đầu phải trước ngày kết thúc.",
                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    work_scheduleList.removeIf(work_schedule -> (work_schedule.getDate().before(startDate) || work_schedule.getDate().after(endDate)));
                } else {
                    if (jDateChooser[0].getDateEditor().getDate() == null) {
                        Date endDate = jDateChooser[1].getDate();
                        work_scheduleList.removeIf(work_schedule -> (work_schedule.getDate().before(java.sql.Date.valueOf("1000-1-1")) || work_schedule.getDate().after(endDate)));
                    } else {
                        Date startDate = jDateChooser[0].getDate();
                        work_scheduleList.removeIf(work_schedule -> (work_schedule.getDate().before(startDate)));
                    }
                }
            }
            loadDataTable(leave_Of_Absence_FormBLL.getData(work_scheduleList));
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

            int staffId = Integer.parseInt(data[i][1].toString());

            List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_details("staff_id = " + staffId);
            Role_Detail roleDetail = role_detailList.get(role_detailList.size() - 1);
            Role role = new RoleBLL().searchRoles("id = " + roleDetail.getRole_id()).get(0);
            data[i][0] = role.getName();

            data[i][1] = staffBLL.findStaffsBy(Map.of("id", staffId)).get(0).getName();

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
        }

        for (Object[] object : data) {
            model.addRow(object);
        }
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail)
            new DetailLeave_Of_Absence_FormGUI(leave_Of_Absence_FormBLL.searchLeave_Of_Absence_Forms().get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

        if (edit && indexColumn == indexColumnEdit) {
            new EditLeave_Of_Absence_FormGUI(leave_Of_Absence_FormBLL.searchLeave_Of_Absence_Forms().get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá
            refresh();
        }

//        if (remove && indexColumn == indexColumnRemove)
//            deleteWorkSchedule(leave_Of_Absence_FormBLL.searchLeave_Of_Absence_Forms().get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá

    }
}
