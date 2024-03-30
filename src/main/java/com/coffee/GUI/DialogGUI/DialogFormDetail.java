package com.coffee.GUI.DialogGUI;

import com.coffee.GUI.components.RoundedPanel;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogFormDetail extends JDialog {
    public RoundedPanel title;
    public RoundedPanel contenttop;
    public RoundedPanel contentmid;
    public RoundedPanel contentbot;
    public RoundedPanel containerButton;

    public DialogFormDetail() {
        super((Frame) null, "", true);
        getContentPane().setBackground(new Color(217, 217, 217));

        setLayout(new MigLayout("", "50[]50", "10[]10"));
        setIconImage(new FlatSVGIcon("image/coffee_logo.svg").getImage());
        setSize(new Dimension(700, 800));
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
        contenttop = new RoundedPanel();
        contentmid = new RoundedPanel();
        contentbot = new RoundedPanel();
        containerButton = new RoundedPanel();

        title.setLayout(new BorderLayout());
        title.setBackground(new Color(217, 217, 217));
        title.setPreferredSize(new Dimension(1000, 50));
        add(title, "wrap, span, center");

        contenttop.setLayout(new MigLayout("", "20[]20[]20", "20[]20[]20"));
        contenttop.setBackground(new Color(217, 217, 217));
        contenttop.setPreferredSize(new Dimension(1000, 100));
        add(contenttop, "wrap");

        contentmid.setLayout(new MigLayout());
        contentmid.setBackground(new Color(255, 255, 255));
        contentmid.setPreferredSize(new Dimension(1000, 350));
        add(contentmid, "wrap");

        contentbot.setLayout(new MigLayout("", "20[]20[]20", "20[]20[]20"));
        contentbot.setBackground(new Color(217, 217, 217));
        contentbot.setPreferredSize(new Dimension(1000, 200));
        add(contentbot, "wrap");

        containerButton.setLayout(new FlowLayout());
        containerButton.setBackground(new Color(217, 217, 217));
        containerButton.setPreferredSize(new Dimension(1000, 50));
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
