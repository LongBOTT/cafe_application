package com.coffee.GUI.DialogGUI.FormAddGUI;

import com.coffee.BLL.*;
import com.coffee.DTO.Import_Note;
import com.coffee.DTO.Material;
import com.coffee.DTO.Shipment;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogFormDetail;
import com.coffee.GUI.HomeGUI;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.coffee.GUI.components.swing.DataSearch;
import com.coffee.GUI.components.swing.EventClick;
import com.coffee.GUI.components.swing.MyTextField;
import com.coffee.GUI.components.swing.PanelSearch;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
    private List<Shipment> shipmentList = new ArrayList<>();
    private JTextField jTextFieldQuantity = new JTextField();
    private JComboBox<String> jComboBoxSupplier = new JComboBox<>();
    private MyTextField txtSearch;
    private PanelSearch search;
    private JPopupMenu menu;
    private JTextField[] jTextFieldDate;
    private JTextField[] dateTextField;
    private JDateChooser[] jDateChooser;
    private int materialID;
    private int import_id;
    private int shipment_id;

    public AddImportGUI() {
        super();
        super.setTitle("Tạo phiếu nhập");
        super.setSize(new Dimension(1200, 700));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        import_id = import_NoteBLL.getAutoID(import_NoteBLL.searchImport());
        shipment_id = shipmentBLL.getAutoID(shipmentBLL.searchShipments()) - 1;
        init();
        menu = new JPopupMenu();
        search = new PanelSearch();
        menu.setBorder(BorderFactory.createLineBorder(new Color(164, 164, 164)));
        menu.add(search);
        menu.setFocusable(false);
        search.addEventClick(new EventClick() {
            @Override
            public void itemClick(DataSearch data) {
                menu.setVisible(false);
                txtSearch.setText(data.getText());
                Material material = new MaterialBLL().findMaterialsBy(Map.of("name", data.getText())).get(0);
                materialID = material.getId();
            }

            @Override
            public void itemRemove(Component com, DataSearch data) {
                search.remove(com);
                menu.setPopupSize(menu.getWidth(), (search.getItemSize() * 35) + 2);
                if (search.getItemSize() == 0) {
                    menu.setVisible(false);
                }
            }
        });
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        buttonAdd = new JButton("Tạo phiếu nhập");
        attributeImport_Note = new ArrayList<>();
        contenttop.setLayout(new MigLayout("",
                "50[]20[]20[]20[]20[]20[]20[]20[]20",
                "10[]10[]10"));
        contentbot.setLayout(new MigLayout("",
                "50[]20[]50",
                "10[]10[]10"));
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];
        jTextFieldDate = new JTextField[2];

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

        JButton btnAddMaterial = new JButton();
        ImageIcon icon = new FlatSVGIcon("icon/add.svg");
        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);

        super.remove(contentmid);
        super.remove(contentbot);
        super.remove(containerButton);

        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.setPreferredSize(new Dimension(1200, 100));
        jPanel.setBackground(new Color(217, 217, 217));

        btnAddMaterial.setIcon(icon);
        btnAddMaterial.setPreferredSize(new Dimension(30, 30));
        btnAddMaterial.setBackground(new Color(0, 182, 62));
        btnAddMaterial.setFont(new Font("Public Sans", Font.BOLD, 16));
        btnAddMaterial.setForeground(Color.WHITE);
        btnAddMaterial.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddMaterial.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new AddMaterialGUI();
            }
        });
        jPanel.add(btnAddMaterial);

        int i = 0;

        for (String string : new String[]{"Tên Nguyên Liệu", "Nhà Cung Cấp", "SL Nhập", "MFG", "EXP"}) {
            JLabel jLabelTitle = new JLabel(string);
            jLabelTitle.setFont((new Font("Public Sans", Font.BOLD, 12)));
            jPanel.add(jLabelTitle);

            if (string.equals("Tên Nguyên Liệu")) {
                txtSearch = new MyTextField();
                txtSearch.setPreferredSize(new Dimension(100, 30));

                txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        txtSearchMouseClicked(evt);
                    }
                });
                txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyPressed(java.awt.event.KeyEvent evt) {
                        txtSearchKeyPressed(evt);
                    }

                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        txtSearchKeyReleased(evt);
                    }
                });
                jPanel.add(txtSearch);
                continue;
            }
            if (string.equals("Nhà Cung Cấp")) {
                for (Supplier supplier : new SupplierBLL().searchSuppliers("deleted = 0"))
                    jComboBoxSupplier.addItem(supplier.getName());
                jComboBoxSupplier.setPreferredSize(new Dimension(150, 30));
                jPanel.add(jComboBoxSupplier);
                continue;
            }
            if (string.equals("SL Nhập")) {
                jTextFieldQuantity.setPreferredSize(new Dimension(50, 30));
                jTextFieldQuantity.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                        if (!Character.isDigit(e.getKeyChar())) {
                            e.consume();
                        }
                    }
                });
                jPanel.add(jTextFieldQuantity);
            } else {
                jTextFieldDate[i] = new JTextField();
                jTextFieldDate[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
                jTextFieldDate[i].setPreferredSize(new Dimension(130, 30));
                jTextFieldDate[i].setAutoscrolls(true);

                jDateChooser[i] = new JDateChooser();
                jDateChooser[i].setDateFormatString("dd/MM/yyyy");
                jDateChooser[i].setPreferredSize(new Dimension(130, 30));
                jDateChooser[i].setMinSelectableDate(java.sql.Date.valueOf("1000-1-1"));

                dateTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
                dateTextField[i].setFont(new Font("Lexend", Font.BOLD, 14));
                dateTextField[i].setBackground(new Color(245, 246, 250));
                jPanel.add(jDateChooser[i]);
                i++;
            }
        }

        JButton btnThem = new JButton("Thêm lô hàng");
        btnThem.setPreferredSize(new Dimension(150, 30));
        btnThem.setBackground(new Color(0, 182, 62));
        btnThem.setFont(new Font("Public Sans", Font.BOLD, 12));
        btnThem.setForeground(Color.WHITE);
        btnThem.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addShipment();
                refresh();
            }

        });
        jPanel.add(btnThem);

        super.add(jPanel, "wrap");
        contentmid.setPreferredSize(new Dimension(1200, 350));
        super.add(contentmid, "wrap");
        contentbot.setPreferredSize(new Dimension(1200, 100));
        super.add(contentbot, "wrap");
        super.add(containerButton, "wrap");


        columnNames = new String[]{"Mã Lô", "Nguyên Liệu", "Tên NCC", "SL Nhập", "SL Tồn", "MFG", "EXP",};
        dataTable = new DataTable(new Object[0][0], columnNames);
        dataTable.getColumnModel().getColumn(0).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(1).setMaxWidth(500);
        dataTable.getColumnModel().getColumn(2).setMaxWidth(500);
        dataTable.getColumnModel().getColumn(3).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(4).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(5).setMaxWidth(250);
        dataTable.getColumnModel().getColumn(6).setMaxWidth(250);
        dataTable.setRowHeight(60);
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        contentmid.add(scrollPane, BorderLayout.CENTER);

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

    private void addShipment() {
        // kiem tra du lieu nhap trong hay sai kieu du lieu

        int supplier_id;
        double quantity;
        java.util.Date mfg, exp;

        shipment_id += 1;
        supplier_id = jComboBoxSupplier.getSelectedIndex() + 1;
        quantity = Double.parseDouble(jTextFieldQuantity.getText());
        mfg = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser[0].getDate()));
        exp = java.sql.Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser[1].getDate()));

        Shipment shipment = new Shipment(shipment_id, materialID, supplier_id, import_id, quantity, quantity, mfg, exp);
        for (Shipment shipment1 : shipmentList) {
            if (shipment1.getMaterial_id() == materialID && shipment1.getSupplier_id() == supplier_id) {
                JOptionPane.showMessageDialog(null, "Lô hàng đã được thêm!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        shipmentList.add(shipment);
    }

    private void refresh() {
        txtSearch.setText("");
        jComboBoxSupplier.setSelectedIndex(0);
        jTextFieldQuantity.setText("");
        jDateChooser[0].setDate(null);
        jDateChooser[1].setDate(null);

        loadDataTable(shipmentBLL.getData(shipmentList));

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

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {
        if (search.getItemSize() > 0 && !txtSearch.getText().isEmpty()) {
            menu.show(txtSearch, 0, txtSearch.getHeight());
            search.clearSelected();
        }
    }

    private List<DataSearch> search(String text) {
        List<DataSearch> list = new ArrayList<>();
        List<Material> materials = new MaterialBLL().findMaterials("name", text);
        for (Material m : materials) {
            if (list.size() == 7)
                break;
            list.add(new DataSearch(m.getName()));
        }
        return list;
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_ENTER) {
            String text = txtSearch.getText().trim().toLowerCase();
            search.setData(search(text));
            if (search.getItemSize() > 0 && !txtSearch.getText().isEmpty()) {
                menu.show(txtSearch, 0, txtSearch.getHeight());
                menu.setPopupSize(menu.getWidth(), (search.getItemSize() * 35) + 2);
            } else {
                menu.setVisible(false);
            }
        }
    }

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            search.keyUp();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            search.keyDown();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = search.getSelectedText();
            txtSearch.setText(text);

        }
        menu.setVisible(false);

    }
}
