package com.coffee.GUI;

import com.coffee.BLL.Role_DetailBLL;
import com.coffee.BLL.ShipmentBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.DTO.Role_Detail;
import com.coffee.DTO.Shipment;
import com.coffee.DTO.Staff;
import com.coffee.GUI.DialogGUI.DialogForm;
import com.coffee.GUI.components.swing.DataSearch;
import com.coffee.GUI.components.swing.EventClick;
import com.coffee.GUI.components.swing.MyTextField;
import com.coffee.GUI.components.swing.PanelSearch;
import com.coffee.main.Cafe_Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckRemainWearHouse extends DialogForm {
    private MyTextField txtSearch;
    private PanelSearch search;
    private JPopupMenu menu;
    private Shipment selectedShipment = null;

    public CheckRemainWearHouse() {
        super();
        super.setTitle("Kiểm kho");
        super.setSize(new Dimension(1000, 600));
        super.setLocationRelativeTo(Cafe_Application.homeGUI);
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
                selectedShipment = new ShipmentBLL().searchShipments("id = " + data.getText()).get(0);
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
        JLabel titleName = new JLabel();
        titleName.setText("Kiểm kho");
        titleName.setFont(new Font("Public Sans", Font.BOLD, 18));
        titleName.setHorizontalAlignment(JLabel.CENTER);
        titleName.setVerticalAlignment(JLabel.CENTER);
        title.add(titleName, BorderLayout.CENTER);


    }

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {
        if (search.getItemSize() > 0 && !txtSearch.getText().isEmpty()) {
            menu.show(txtSearch, 0, txtSearch.getHeight());
            search.clearSelected();
        }
    }

    private java.util.List<DataSearch> search(String text) {
        selectedShipment = null;
        java.util.List<DataSearch> list = new ArrayList<>();
        List<Shipment> shipmentList = new ShipmentBLL().findShipments("id", text);
        for (Shipment m : shipmentList) {
            if (list.size() == 7)
                break;
            list.add(new DataSearch(m.getId() + ""));
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
