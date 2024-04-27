package com.coffee.GUI.components;

import net.miginfocom.swing.MigLayout;


import javax.swing.*;
import java.awt.*;

public class LayoutStatistic extends RoundedPanel {
    public RoundedPanel top;
    public RoundedPanel center;
    public RoundedPanel displayTypePanel;
    public RoundedPanel concernsPanel;
    public RoundedPanel timePanel;



    public LayoutStatistic() {
        initializeComponents();
    }

    private void initializeComponents() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1165, 733));
        setLayout(new BorderLayout());

        top = createRoundedPanel(Color.WHITE, new FlowLayout(), 1165, 100);
        center = createRoundedPanel(Color.CYAN, new BorderLayout(), 1165, 680);
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        displayTypePanel = createRoundedPanel(new MigLayout("", "[]", "[][][]"), 300, 70);
        concernsPanel = createRoundedPanel(new MigLayout("", "[]", "[][][]"), 515, 70);
        timePanel = createRoundedPanel(new MigLayout("", "[]", "[][][]"), 300, 70);

        top.add(displayTypePanel);
        top.add(concernsPanel);
        top.add(timePanel);


    }

    private RoundedPanel createRoundedPanel(Color color, LayoutManager layoutManager, int width, int height) {
        RoundedPanel panel = new RoundedPanel();
        panel.setBackground(color);
        panel.setLayout(layoutManager);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    private RoundedPanel createRoundedPanel(LayoutManager layoutManager, int width, int height) {
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(layoutManager);
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }
}
