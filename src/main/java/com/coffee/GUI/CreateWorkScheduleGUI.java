package com.coffee.GUI;

import com.coffee.BLL.StaffBLL;
import com.coffee.BLL.Work_ScheduleBLL;
import com.coffee.DTO.Account;
import com.coffee.DTO.Function;
import com.coffee.DTO.Staff;
import com.coffee.DTO.Work_Schedule;
import com.coffee.GUI.components.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class CreateWorkScheduleGUI extends Layout2 {
    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JTextField[] jTextFieldDate;
    private JTextField[] dateTextField;
    private JDateChooser[] jDateChooser;
    private JButton jButtonSearch;
    private List<Function> functions;
    private Work_ScheduleBLL workScheduleBLL = new Work_ScheduleBLL();
    private DataTable dataTable;
    private RoundedScrollPane scrollPane;
    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private int indexColumnRemove = -1;
    private boolean detail = false;
    private boolean edit = false;
    private boolean remove = false;
    private String[] columnNames;
    private StaffBLL staffBLL = new StaffBLL();
    private Date date;

    public CreateWorkScheduleGUI(List<Function> functions) {
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
        date = new Date();
        containerSearch = new RoundedPanel();
        iconSearch = new JLabel();
        jTextFieldSearch = new JTextField();
        jButtonSearch = new JButton("Tìm kiếm");
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];
        jTextFieldDate = new JTextField[2];

        columnNames = new String[]{"Chức vụ", "Họ tên", "Ngày làm", "Ca", "Giờ vào", "Giờ ra"};
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
                detail, edit, remove, 6);
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
                searchWork_schedules();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchWork_schedules();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchWork_schedules();
            }
        });
        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
        jButtonSearch.addActionListener(e -> searchWork_schedules());
        SearchPanel.add(jButtonSearch);

        loadDataTable(workScheduleBLL.getData(workScheduleBLL.searchWork_schedules()));

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
//                    new AddWorkScheduleGUI();
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
        jDateChooser[0].getDateEditor().setDate(null);
        jDateChooser[1].getDateEditor().setDate(null);
        loadDataTable(workScheduleBLL.getData(workScheduleBLL.searchWork_schedules()));
    }

    private void searchWork_schedules() {
        List<Work_Schedule> work_scheduleList = workScheduleBLL.searchWork_schedules();
        if (jTextFieldSearch.getText().isEmpty() && jDateChooser[0].getDateEditor().getDate() == null && jDateChooser[1].getDateEditor().getDate() == null) {
            loadDataTable(workScheduleBLL.getData(work_scheduleList));
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
            loadDataTable(workScheduleBLL.getData(work_scheduleList));
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
            data[i][1] = staffBLL.findStaffsBy(Map.of("id", staffId)).get(0).getName();

            if (data[i][3].toString().equals("1"))
                data[i][3] = "6h - 12h";

            if (data[i][3].toString().equals("2"))
                data[i][3] = "12h - 18h";

            if (data[i][3].toString().equals("3"))
                data[i][3] = "18h - 23h";

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
//            new DetailWorkScheduleGUI(workScheduleBLL.searchWork_schedules().get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá

            if (edit && indexColumn == indexColumnEdit) {
//            new EditWorkScheduleGUI(workScheduleBLL.searchWork_schedules().get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá
                refresh();
            }

        if (remove && indexColumn == indexColumnRemove)
            deleteWorkSchedule(workScheduleBLL.searchWork_schedules().get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm  để lấy các đối tượng còn tồn tại, chưa xoá

    }

    private void deleteWorkSchedule(Work_Schedule workSchedule) {
        if (dataTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn nhà cung cấp cần xoá.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] options = new String[]{"Huỷ", "Xác nhận"};
        int choice = JOptionPane.showOptionDialog(null, "Xác nhận xoá nhà cung cấp?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 1) {
            Pair<Boolean, String> result = workScheduleBLL.deleteWork_schedule(workSchedule);
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
