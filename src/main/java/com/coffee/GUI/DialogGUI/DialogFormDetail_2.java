package com.coffee.GUI.DialogGUI;

import com.coffee.GUI.components.RoundedPanel;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogFormDetail_2 extends  JDialog{

    public RoundedPanel titledis;
    public RoundedPanel contentdis;

    public RoundedPanel titlepro;
    public RoundedPanel contentpro;
    public RoundedPanel containerButton;

    public DialogFormDetail_2() {
        super((Frame) null, "", true);
        getContentPane().setBackground(new Color(217,217,217));
        setLayout(new MigLayout("", "50[]50", "10[]10"));
        setIconImage(new FlatSVGIcon("image/coffee_logo.svg").getImage());
//        setSize(new Dimension(1000,700));
        setSize(new Dimension(1000,700));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(Cafe_Application.homeGUI);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });
        initComponents();

    }

    private void initComponents() {
        titledis = new RoundedPanel();
        titlepro = new RoundedPanel();

        contentdis = new RoundedPanel();
        contentpro = new RoundedPanel();

        containerButton = new RoundedPanel();

        titledis.setLayout(new BorderLayout());
        titledis.setBackground(new Color(232,206,180));
        titledis.setPreferredSize(new Dimension(1000, 60));
        add(titledis, "wrap, span, center");

//        content.setLayout(new MigLayout("", "20[]20[]20", "10[]10[]10"));
        contentdis.setLayout(new MigLayout("", "[][]", "[][]"));
        contentdis.setBackground(new Color(255, 255, 255));
        contentdis.setPreferredSize(new Dimension(1000, 140));
        add(contentdis, "wrap");

        titlepro.setLayout(new BorderLayout());
        titlepro.setBackground(new Color(232,206,180));
        titlepro.setPreferredSize(new Dimension(1000, 60));
        add(titlepro, "wrap, span, center");

        contentpro.setLayout(new MigLayout("", "[][]", "[][]"));
        contentpro.setBackground(new Color(255, 255, 255));
        contentpro.setPreferredSize(new Dimension(1000, 380));
        add(contentpro, "wrap");

        containerButton.setLayout(new FlowLayout());
        containerButton.setBackground(new Color(232,206,180));
//      containerButton.setPreferredSize(new Dimension(1000, 100));
        containerButton.setPreferredSize(new Dimension(1000, 70));
        add(containerButton, "wrap");



    }

    public void cancel() {
        String[] options = new String[]{"Huỷ", "Thoát"};
        int choice = JOptionPane.showOptionDialog(null, "Bạn có muốn thoát?",
                "Thông báo", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 1)
            dispose();
    }


}
