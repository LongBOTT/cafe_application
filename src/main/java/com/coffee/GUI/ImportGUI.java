package com.coffee.GUI;

import com.coffee.BLL.Import_NoteBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.BLL.Work_ScheduleBLL;
import com.coffee.DTO.Function;
import com.coffee.DTO.Import_Note;
import com.coffee.DTO.Staff;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddExportDetailGUI;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddWorkScheduleGUI;
import com.coffee.GUI.DialogGUI.FormDetailGUI.DetailImportGUI;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditSupplierGUI;
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
import java.util.*;
import java.util.List;

public class ImportGUI extends Layout2 {

    private RoundedPanel containerSearch;
    private JLabel iconSearch;
    private JTextField jTextFieldSearch;
    private JTextField[] jTextFieldDate;
    private JTextField[] dateTextField;
    private JDateChooser[] jDateChooser;
    private JButton jButtonSearch;
    private List<Function> functions;
    private DataTable dataTable;
    private StaffBLL staffBLL = new StaffBLL();
    private Import_NoteBLL importNoteBLL = new Import_NoteBLL();
    private boolean detail = false;
    private boolean edit = false;
    private boolean remove = false;
    private int indexColumnDetail = -1;
    private RoundedScrollPane scrollPane;
    private String[] columnNames;
    private Date date;

    public ImportGUI(List<Function> functions) {
        super();
        this.functions = functions;
        if (functions.stream().anyMatch(f -> f.getName().equals("view"))) detail = true;
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

        columnNames = new String[]{"Mã Phiếu Nhập", "Nhân Viên", "Tổng Tiền", "Ngày Nhập"};
        if (detail) {
            columnNames = Arrays.copyOf(columnNames, columnNames.length + 1);
            indexColumnDetail = columnNames.length - 1;
            columnNames[indexColumnDetail] = "Xem";
        }

        dataTable = new DataTable(new Object[0][0], columnNames, e -> selectFunction(), detail, edit, remove, 4);
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
                searchImports();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchImports();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchImports();
            }
        });
        containerSearch.add(jTextFieldSearch);

        jButtonSearch.setBackground(new Color(29, 78, 216));
        jButtonSearch.setForeground(Color.white);
        jButtonSearch.setPreferredSize(new Dimension(100, 30));
        jButtonSearch.addActionListener(e -> searchImports());
        SearchPanel.add(jButtonSearch);

        loadDataTable(importNoteBLL.getData(importNoteBLL.searchImport()));

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
//                    new AddExportDetailGUI();
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

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        if (objects.length == 0) {
            return;
        }

        Object[][] data = new Object[objects.length][objects[0].length];

        for (int i = 0; i < objects.length; i++) {
            System.arraycopy(objects[i], 0, data[i], 0, objects[i].length);

            int staff_id = Integer.parseInt(data[i][1].toString());
            data[i][1] = staffBLL.findStaffsBy(Map.of("id", staff_id)).get(0).getName();

            if (detail) {
                JLabel iconDetail = new JLabel(new FlatSVGIcon("icon/detail.svg"));
                data[i] = Arrays.copyOf(data[i], data[i].length + 1);
                data[i][data[i].length - 1] = iconDetail;
            }
        }

        for (Object[] object : data) {
            model.addRow(object);
        }
    }

    public void refresh() {
        jTextFieldSearch.setText("");
        jDateChooser[0].getDateEditor().setDate(null);
        jDateChooser[1].getDateEditor().setDate(null);
        loadDataTable(importNoteBLL.getData(importNoteBLL.searchImport()));
    }

    private void selectFunction() {
        int indexRow = dataTable.getSelectedRow();
        int indexColumn = dataTable.getSelectedColumn();

        if (detail && indexColumn == indexColumnDetail)
            new DetailImportGUI(importNoteBLL.searchImport().get(indexRow)); // Đối tượng nào có thuộc tính deleted thì thêm "deleted = 0" để lấy các đối tượng còn tồn tại, chưa xoá

    }

    private void searchImports() {
        List<Import_Note> import_noteList = importNoteBLL.searchImport();
        if (jTextFieldSearch.getText().isEmpty() && jDateChooser[0].getDateEditor().getDate() == null && jDateChooser[1].getDateEditor().getDate() == null) {
            loadDataTable(importNoteBLL.getData(import_noteList));
        } else {
            if (!jTextFieldSearch.getText().isEmpty()) {
                List<Integer> staffIDList = new ArrayList<>();
                for (Staff staff : staffBLL.findStaffs("name", jTextFieldSearch.getText()))
                    staffIDList.add(staff.getId());
                import_noteList.removeIf(import_note -> !staffIDList.contains(import_note.getStaff_id()));
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
                    import_noteList.removeIf(import_note -> (import_note.getReceived_date().before(startDate) || import_note.getReceived_date().after(endDate)));
                } else {
                    if (jDateChooser[0].getDateEditor().getDate() == null) {
                        Date endDate = jDateChooser[1].getDate();
                        import_noteList.removeIf(import_note -> (import_note.getReceived_date().before(java.sql.Date.valueOf("1000-1-1")) || import_note.getReceived_date().after(endDate)));
                    } else {
                        Date startDate = jDateChooser[0].getDate();
                        import_noteList.removeIf(import_note -> (import_note.getReceived_date().before(startDate)));
                    }
                }
            }
            loadDataTable(importNoteBLL.getData(import_noteList));
        }
    }
}
