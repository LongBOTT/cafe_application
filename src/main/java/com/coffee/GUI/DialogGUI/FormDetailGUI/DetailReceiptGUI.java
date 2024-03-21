package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.ReceiptBLL;
import com.coffee.BLL.Receipt_DetailBLL;
import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Receipt;
import com.coffee.DTO.Receipt_Detail;
import com.coffee.DTO.Supplier;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.DialogGUI.DialogFormReceipt;
import com.coffee.GUI.components.DataTable;
import com.coffee.GUI.components.RoundedScrollPane;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DetailReceiptGUI extends DialogFormReceipt {
    private JLabel titleName;
    private List<JLabel> attributeReceipt;
    private List<JTextField> jTextFieldReceipt;
    private ReceiptBLL receiptBLL = new ReceiptBLL();
    private Receipt receipt = new Receipt();
    private Receipt_DetailBLL receipt_detailBLL = new Receipt_DetailBLL();

    private DataTable dataTable;
    private String[] columnNames;
    private RoundedScrollPane scrollPane;
    private JDateChooser jDateChooser = new JDateChooser();

    private Receipt_Detail receiptDetail = new Receipt_Detail();

    public DetailReceiptGUI(Receipt receipt) {
        super();
        super.setTitle("Thông tin hóa đơn");
        init(receipt);
        setVisible(true);
    }

    private void init(Receipt receipt) {
        titleName = new JLabel();
        attributeReceipt = new ArrayList<>();
        jTextFieldReceipt = new ArrayList<>();
        contenttop.setLayout(new MigLayout("",
                "100[]20[]100",
                "10[]10[]10"));

        titleName.setText("Thông tin Hóa Đơn");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Mã Hóa Đơn", "Mã Nhân Viên", "Ngày Tạo"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeReceipt.add(label);
            contenttop.add(label);
            JTextField textField = new JTextField();
            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            if (string.trim().equals("Ngày Tạo")) {
                Date invoice_date = receipt.getInvoice_date() ;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                jDateChooser = new JDateChooser();

                jDateChooser.setDateFormatString("dd/MM/yyyy");
                jDateChooser.setDate(invoice_date);
                jDateChooser.setPreferredSize(new Dimension(180, 35));
                jDateChooser.setMinSelectableDate(java.sql.Date.valueOf("1000-01-01"));
                jDateChooser.setEnabled(false);
                textField = (JTextField) jDateChooser.getDateEditor().getUiComponent();

                textField.setText(dateFormat.format(invoice_date));
                contenttop.add(jDateChooser, "wrap");
            } else  {
                if (string.trim().equals("Mã Hóa Đơn")) {
                    String receiptId = Integer.toString(receipt.getId());
                    textField.setText(receiptId);
                }
                if (string.equals("Mã Nhân Viên")) {
                    String staffId = Integer.toString(receipt.getStaff_id());
                    textField.setText(staffId);
                }

                jTextFieldReceipt.add(textField);
                contenttop.add(textField, "wrap");
            }

        }


//        ////
        columnNames = new String[]{"Mã hóa đơn", "Mã Sản Phẩm ", "Số Lượng", "Giá", };
        dataTable = new DataTable(new Object[0][0], columnNames);
        scrollPane = new RoundedScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1165, 680));
        contentmid.add(scrollPane, BorderLayout.CENTER);

        loadDataTable(receipt_detailBLL.getData(receipt_detailBLL.searchReceipt_Details()));

        ///
        for (String string : new String[]{"Tổng Tiền", "Tiền Nhận", "Tiền Thối"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeReceipt.add(label);
            contentbot.add(label);

            JTextField textField = new JTextField();

            if (string.equals("Tổng Tiền")) {
                String total = Double.toString(receipt.getTotal());
                textField.setText(total);
            }
            if (string.equals("Tiền Nhận")) {
                String total = Double.toString(receipt.getReceived());
                textField.setText(total);
            }
            if (string.equals("Tiền Thối")) {
                String total = Double.toString(receipt.getExcess());
                textField.setText(total);
            }


            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldReceipt.add(textField);
            contentbot.add(textField, "wrap");

            contentbot.add(textField, "wrap");

        }
    }

    public void loadDataTable(Object[][] objects) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        for (Object[] object : objects) {
            model.addRow(object);
        }
    }
}
