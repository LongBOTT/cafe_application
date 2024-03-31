package com.coffee.GUI.DialogGUI.FormDetailGUI;

import com.coffee.BLL.Import_NoteBLL;
import com.coffee.BLL.MaterialBLL;
import com.coffee.BLL.ShipmentBLL;
import com.coffee.BLL.SupplierBLL;
import com.coffee.DTO.Shipment;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.main.Cafe_Application;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailShipmentGUI extends DialogForm {
    private JLabel titleName;
    private List<JLabel> attributeShipment;
    private List<JTextField> jTextFieldShipment;
    private ShipmentBLL shipmentBLL = new ShipmentBLL();

    public DetailShipmentGUI(Shipment shipment) {
        super();
        super.setTitle("Thông tin lô hàng");
        super.setSize(new Dimension(600, 550));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
        init(shipment);
        setVisible(true);
    }

    private void init(Shipment shipment) {
        titleName = new JLabel();
        attributeShipment = new ArrayList<>();
        jTextFieldShipment = new ArrayList<>();
        content.setLayout(new MigLayout("",
                "50[]20[]50",
                "20[]20[]20"));

        titleName.setText("Thông tin lô hàng");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);

        for (String string : new String[]{"Tên nguyên liệu", "Tên nhà cung cấp", "Ngày nhập", "Số lượng nhập", "Số lượng tồn kho", "Đơn giá", "Ngày sản xuất", "Ngày hết hạn"}) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(170, 30));
            label.setText(string);
            label.setFont((new Font("Public Sans", Font.PLAIN, 16)));
            attributeShipment.add(label);
            content.add(label);

            JTextField textField = new JTextField();

            textField.setEditable(false);
            textField.setPreferredSize(new Dimension(1000, 30));
            textField.setFont((new Font("Public Sans", Font.PLAIN, 14)));
            textField.setBackground(new Color(245, 246, 250));
            jTextFieldShipment.add(textField);
            content.add(textField, "wrap");
        }

        jTextFieldShipment.get(0).setText(new MaterialBLL().findMaterialsBy(Map.of("id", shipment.getMaterial_id())).get(0).getName());
        jTextFieldShipment.get(1).setText(new SupplierBLL().findSuppliersBy(Map.of("id", shipment.getSupplier_id())).get(0).getName());
        jTextFieldShipment.get(2).setText(new Import_NoteBLL().findImportBy(Map.of("id", shipment.getImport_id())).get(0).getReceived_date().toString());
        jTextFieldShipment.get(3).setText(String.valueOf(shipment.getQuantity()));
        jTextFieldShipment.get(4).setText(String.valueOf(shipment.getRemain()));
        jTextFieldShipment.get(5).setText(String.valueOf(shipment.getUnit_price()));
        jTextFieldShipment.get(6).setText(String.valueOf(shipment.getMfg()));
        jTextFieldShipment.get(7).setText(String.valueOf(shipment.getExp()));


    }
}

