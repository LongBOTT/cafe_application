package com.coffee.GUI.DialogGUI;

import com.coffee.GUI.components.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class DialogFormDetail_1 extends DialogForm{
    protected RoundedPanel top;
    protected  RoundedPanel center;
    protected RoundedPanel bottom;

    private RoundedPanel contentMateral;
    public DialogFormDetail_1() {
        super();
        init();
        setVisible(true);
    }
    public void init(){
        top = new RoundedPanel();
        center = new RoundedPanel();
        bottom = new RoundedPanel();

        top.setLayout(new BorderLayout());
        top.setBackground(new Color(255, 0, 0));
        top.setPreferredSize(new Dimension(1000, 200));
        content.add(top, "wrap");

        center.setLayout(new BorderLayout());
        center.setBackground(new Color(232,206,180));
        center.setPreferredSize(new Dimension(1000, 70));
        content.add(center, "wrap");

        bottom.setLayout(new BorderLayout());
        bottom.setBackground(new Color(0, 0, 255));
        bottom.setPreferredSize(new Dimension(1000, 290));
        content.add(bottom, "wrap");
    }

}

