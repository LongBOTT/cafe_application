package com.coffee.GUI;

import com.coffee.GUI.components.DatePicker;
import com.coffee.GUI.components.LayoutStatistic;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DateSelectionListener;

import javax.swing.*;
import java.awt.*;

public class StatisticSalesGUI extends LayoutStatistic {
    private DatePicker datePicker;
    private JFormattedTextField editor;

    public StatisticSalesGUI() {
        super();
        initUIComponents();
        setVisible(true);
    }

    private void initUIComponents() {
//        setupDisplayType();
//        setupConcerns();
//        setupTime();

        initProductPanel();
    }

    private void initProductPanel() {
        initTopBarProductPanel();

    }

    private void initTopBarProductPanel() {
        top.removeAll();


        top.repaint();
        top.revalidate();
    }

    private void setupDisplayType() {
        JLabel label = new JLabel("Kiểu hiển thị");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        displayTypePanel.add(label, "wrap");

        JRadioButton chartRadioButton = createRadioButton("Biểu đồ");
        JRadioButton reportRadioButton = createRadioButton("Báo cáo");

        ButtonGroup btnGroupDisplayType = new ButtonGroup();
        btnGroupDisplayType.add(chartRadioButton);
        btnGroupDisplayType.add(reportRadioButton);

        displayTypePanel.add(chartRadioButton);
        displayTypePanel.add(reportRadioButton);
    }

    private void setupConcerns() {
        JLabel label = new JLabel("Mối quan tâm");
        label.setFont(new Font("Inter", Font.BOLD, 14));
        concernsPanel.add(label, "wrap");

        JRadioButton timeRadioButton = createRadioButton("Thời gian");
        JRadioButton profitRadioButton = createRadioButton("Lợi nhuận");
        JRadioButton discountRadioButton = createRadioButton("Giảm giá HĐ");
        JRadioButton categoryRadioButton = createRadioButton("Thể loại");

        ButtonGroup btnGroupConcerns = new ButtonGroup();
        btnGroupConcerns.add(timeRadioButton);
        btnGroupConcerns.add(profitRadioButton);
        btnGroupConcerns.add(discountRadioButton);
        btnGroupConcerns.add(categoryRadioButton);

        concernsPanel.add(timeRadioButton);
        concernsPanel.add(profitRadioButton);
        concernsPanel.add(discountRadioButton);
        concernsPanel.add(categoryRadioButton);
    }

    private void setupTime() {
        JLabel label = new JLabel("Thời gian");
        label.setFont(new Font("Inter", Font.BOLD, 14));

        datePicker = new DatePicker();
        editor = new JFormattedTextField();


        datePicker.setDateSelectionMode(raven.datetime.component.date.DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.setEditor(editor);
        datePicker.setCloseAfterSelected(true);
        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent dateEvent) {

            }
        });

        editor.setPreferredSize(new Dimension(280, 40));
        editor.setFont(new Font("Inter", Font.BOLD, 15));

        timePanel.add(label, "wrap");
        timePanel.add(editor);
    }

    private JRadioButton createRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Inter", Font.PLAIN, 14));
        return radioButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setSize(1165, 733);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new StatisticSalesGUI());
            frame.setVisible(true);
        });
    }
}
