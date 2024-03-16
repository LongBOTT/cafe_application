package com.coffee.main;

import com.coffee.GUI.HomeGUI;
import com.coffee.GUI.LoginGUI;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class Cafe_Application {
    public static LoginGUI loginGUI;
    public static HomeGUI homeGUI;

    public static void main(String[] args) {
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();

        UIManager.put("ProgressBar.selectionForeground", Color.black);
        UIManager.put("ProgressBar.selectionBackground", Color.black);
        UIManager.put("ScrollBar.trackArc", 999);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", new Color(220, 221, 225, 255));
        UIManager.put("PasswordField.showRevealButton", true);
        UIManager.put("PasswordField.capsLockIcon", new FlatSVGIcon("icon/capslock.svg"));
        UIManager.put("TitlePane.iconSize", new Dimension(25, 25));
        UIManager.put("TitlePane.iconMargins", new Insets(3, 5, 0, 20));

        Thread thread = new Thread(() -> homeGUI = new HomeGUI());
        thread.start();
        loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    }

    public static void exit(int status) {
        System.exit(status);
    }

}
