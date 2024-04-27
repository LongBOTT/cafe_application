package com.coffee.GUI;

import com.coffee.GUI.components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class StatisticGUI extends RoundedPanel {
    private final StatisticEndOfTheDayGUI statisticEndOfTheDayGUI = new StatisticEndOfTheDayGUI();
    private final StatisticSaleGUI statisticSaleGUI = new StatisticSaleGUI();
    private final StatisticStaffGUI statisticStaffGUI = new StatisticStaffGUI();
    private final StatisticProductGUI statisticProductGUI = new StatisticProductGUI();
    private final StatisticFinanceGUI statisticFinanceGUI = new StatisticFinanceGUI();

    public StatisticGUI() {
        setBackground(new Color(191, 198, 208));
        setPreferredSize(new Dimension(1165, 733));
        setLayout(new BorderLayout());
        init();
        setVisible(true);
    }

    private void init() {
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.setBackground(Color.white);
        jTabbedPane.setPreferredSize(new Dimension(1165, 733));
        jTabbedPane.addTab("Cuối Ngày", statisticEndOfTheDayGUI);
        jTabbedPane.addTab("Bán Hàng", statisticSaleGUI);
        jTabbedPane.addTab("Nhân Viên", statisticStaffGUI);
        jTabbedPane.addTab("Sản Phẩm", statisticProductGUI);
        jTabbedPane.addTab("Tài Chính", statisticFinanceGUI);
        add(jTabbedPane, BorderLayout.CENTER);


    }


}
