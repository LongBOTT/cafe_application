package com.coffee.main;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JComboBox with Icon");


        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});

        JButton button = new JButton(new FlatSVGIcon("icon/icons8-category-20.svg"));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox.isVisible()) {
                    comboBox.setVisible(false);
                } else {
                    Point location = button.getLocationOnScreen();
                    comboBox.setLocation(location.x, location.y + button.getHeight());
                    comboBox.setVisible(true);
                    comboBox.requestFocus();
                }
            }
        });

        frame.add(button, BorderLayout.WEST);
        frame.add(comboBox, BorderLayout.CENTER);

        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


