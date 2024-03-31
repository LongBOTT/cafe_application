package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.*;
import com.coffee.DTO.Import_Note;
import com.coffee.GUI.DialogGUI.DialogFormDetail;
import com.coffee.GUI.HomeGUI;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AddImportGUI extends DialogFormDetail {
    private List<JLabel> attributeImport_Note;
    private JLabel titleName;
    private JLabel jLabelTotal;
    private JButton buttonAdd;
    private Import_NoteBLL import_NoteBLL = new Import_NoteBLL();
    private ShipmentBLL shipmentBLL = new ShipmentBLL();
    private DataTable dataTable;
    private String[] columnNames;
    private RoundedScrollPane scrollPane;
    private Import_Note import_note = new Import_Note();

    public AddImportGUI() {
        super();
        super.setTitle("Tạo phiếu nhập");
        super.setSize(new Dimension(1000, 700));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        buttonAdd = new JButton("Tạo phiếu nhập");
        attributeImport_Note = new ArrayList<>();
        contenttop.setLayout(new MigLayout("",
                "50[]20[]50",
                "10[]10[]10"));
        contentbot.setLayout(new MigLayout("",
                "50[]20[]50",
                "10[]10[]10"));

        titleName.setText("Tạo Phiếu Nhập");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Mã Phiếu Nhập", "Nhân Viên", "Ngày Nhập"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.BOLD, 16)));
            attributeImport_Note.add(label);
            contenttop.add(label);
            JLabel textField = new JLabel();
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            if (string.trim().equals("Ngày Nhập")) {
                import_note.setReceived_date(Date.valueOf(LocalDate.now()));
                textField.setText(import_note.getReceived_date().toString());
            }
            if (string.trim().equals("Mã Phiếu Nhập")) {
                import_note.setId(import_NoteBLL.getAutoID(import_NoteBLL.searchImport()));
                String import_noteId = Integer.toString(import_note.getId());
                textField.setText(import_noteId);
            }
            if (string.equals("Nhân Viên")) {
                import_note.setStaff_id(HomeGUI.staff.getId());
                String name = new StaffBLL().findStaffsBy(Map.of("id", import_note.getStaff_id())).get(0).getName();
                textField.setText(name);
            }
            contenttop.add(textField, "wrap");
        }

        JLabel jLabel = new JLabel(new FlatSVGIcon("icon/add.svg"));
        jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });
        contenttop.add(jLabel);

        columnNames = new String[]{"Mã Lô", "Nguyên Liệu", "Tên NCC", "SL", "Đơn Giá", "MFG", "EXP",};
        dataTable = new DataTable(new Object[0][0], columnNames);
        dataTable.getColumnModel().getColumn(0).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(1).setMaxWidth(500);
        dataTable.getColumnModel().getColumn(2).setMaxWidth(400);
        dataTable.getColumnModel().getColumn(3).setMaxWidth(50);
        dataTable.getColumnModel().getColumn(4).setMaxWidth(250);
        dataTable.getColumnModel().getColumn(5).setMaxWidth(250);
        dataTable.getColumnModel().getColumn(6).setMaxWidth(250);
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        contentmid.add(scrollPane, BorderLayout.CENTER);

//        loadDataTable(shipmentBLL.getData(shipmentBLL.findShipmentsBy(Map.of("import_id", import_note.getId()))));

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(170, 30));
        label.setText("Tổng Tiền");
        label.setFont((new Font("Public Sans", Font.BOLD, 16)));
        contentbot.add(label);

        jLabelTotal = new JLabel();
//        String total = Double.toString(import_note.getTotal());
        jLabelTotal.setText("0.0");
        jLabelTotal.setPreferredSize(new Dimension(1000, 30));
        jLabelTotal.setFont((new Font("Public Sans", Font.PLAIN, 14)));
        jLabelTotal.setBackground(new Color(245, 246, 250));
        contentbot.add(jLabelTotal, "wrap");

        buttonAdd.setPreferredSize(new Dimension(200, 30));
        buttonAdd.setFont(new Font("Public Sans", Font.BOLD, 15));
        buttonAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
        });
        containerButton.add(buttonAdd);
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

            int material_id = Integer.parseInt(data[i][1].toString());
            data[i][1] = "<html>" + new MaterialBLL().findMaterialsBy(Map.of("id", material_id)).get(0).getName() + "</html>";

            int supplier_id = Integer.parseInt(data[i][2].toString());
            data[i][2] = "<html>" + new SupplierBLL().findSuppliersBy(Map.of("id", supplier_id)).get(0).getName() + "</html>";


        }

        for (Object[] object : data) {
            Object[] objects1 = object;
            System.arraycopy(objects1, 0, object, 0, 3);
            System.arraycopy(objects1, 4, object, 3, 4);
            object = Arrays.copyOfRange(object, 0, 7);
            model.addRow(object);
        }
    }
}
