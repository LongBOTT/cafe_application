package com.coffee.GUI.DialogGUI;

import com.coffee.GUI.components.RoundedPanel;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogExportForm extends JDialog {

    public RoundedPanel title;
    public RoundedPanel titlepro;
    public RoundedPanel content;
    public RoundedPanel contentfunc;
    public RoundedPanel contentproleft;
    public RoundedPanel contentproright;
    public RoundedPanel containerButton;

    public DialogExportForm() {
        super((Frame) null, "", true);
        getContentPane().setBackground(new Color(217, 217, 217));

        setLayout(new MigLayout("", "20[]20", "10[]10"));
        setIconImage(new FlatSVGIcon("image/coffee_logo.svg").getImage());
        setSize(new Dimension(1000, 800));
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
        title = new RoundedPanel();
        titlepro = new RoundedPanel();
        contentfunc = new RoundedPanel();
        content = new RoundedPanel();
        containerButton = new RoundedPanel();
        contentproleft = new RoundedPanel();
        contentproright = new RoundedPanel();


        title.setLayout(new BorderLayout());
        title.setBackground(new Color(232, 206, 180));
        title.setPreferredSize(new Dimension(1000, 60));
        add(title, "wrap, span, center");


        contentfunc.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        contentfunc.setBackground(new Color(255, 255, 255));
        contentfunc.setPreferredSize(new Dimension(1000, 200));
        add(contentfunc, "wrap");


        content.setLayout(new BorderLayout());
        content.setBackground(new Color(139, 118, 118));
        content.setPreferredSize(new Dimension(1000, 550));
        add(content, "wrap");

        contentproleft.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contentproleft.setBackground(new Color(227, 138, 138));
        contentproleft.setPreferredSize(new Dimension(600, 500));
        content.add(contentproleft, BorderLayout.LINE_START);


        contentproright.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        contentproright.setBackground(new Color(60, 86, 148));
        contentproright.setPreferredSize(new Dimension(400, 500));
        content.add(contentproright, BorderLayout.LINE_END);
//        content.setLayout(new BorderLayout());
//        content.setBackground(new Color(139, 118, 118));
//        content.setPreferredSize(new Dimension(1000, 550));
//        add(content, "wrap");

//        contentproleft.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
//        contentproleft.setBackground(new Color(227, 138, 138));
//        contentproleft.setPreferredSize(new Dimension(600, 500));
//       add(contentproleft, "wrap");

//        contentproright.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
//        contentproright.setBackground(new Color(60, 86, 148));
//        contentproright.setPreferredSize(new Dimension(400, 500));
//        add(contentproright, "wrap");


        containerButton.setLayout(new FlowLayout());
        containerButton.setBackground(new Color(217, 217, 217));
        containerButton.setPreferredSize(new Dimension(1000, 80));
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
