package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.DTO.Staff;
import com.coffee.GUI.DialogGUI.DialogExportForm;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditSupplierGUI;
import com.coffee.GUI.components.DataTable;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailExportGUI extends DialogExportForm {
    private java.util.List<JLabel> attributeStaff;

    private DataTable dataTable;

    private boolean detail = false;
    private boolean edit = false;
    private boolean remove = false;
    private List<JTextField> jTextFieldsStaff;

    private int indexColumnDetail = -1;
    private int indexColumnEdit = -1;
    private int indexColumnRemove = -1;
    private JLabel titleName;
    private java.util.List<JLabel> attributeDiscount;

    private JButton buttonCancel;
    private JButton buttonAdd;

    private JDateChooser jDateChooser = new JDateChooser();

    private JTextField textField = new JTextField();

    private Staff staff = new Staff();

    private JRadioButton radioMale = new JRadioButton();

    private JRadioButton radioFemale = new JRadioButton();

    public DetailExportGUI() {
        super();
        super.setTitle("Thêm Nhân Viên");
        init();
        setVisible(true);
    }

    private void init() {
        titleName = new JLabel();
        attributeStaff = new ArrayList<>();
        jTextFieldsStaff = new ArrayList<>();
        buttonCancel = new JButton("Huỷ");
        buttonAdd = new JButton("Thêm");
        content.setLayout(new MigLayout("", "200[]20[]200", "20[]20[]20"));

        titleName.setText("Thêm Nhân Viên");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);


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


    }


}
