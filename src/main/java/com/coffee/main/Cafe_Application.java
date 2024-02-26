package com.coffee.main;

import com.coffee.BLL.AccountBLL;
import com.coffee.DAL.AccountDAL;
import com.coffee.DAL.MySQL;
import com.coffee.DTO.Account;
import com.coffee.GUI.components.LoginGUI;
import com.coffee.utils.Database;
import com.formdev.flatlaf.FlatIntelliJLaf;

import java.io.IOException;
import java.sql.SQLException;

public class Cafe_Application {
    public static LoginGUI loginGUI;
    public static void main(String[] args) {
        FlatIntelliJLaf.registerCustomDefaultsSource("style");
        FlatIntelliJLaf.setup();


//        Thread thread = new Thread(() -> homeGUI = new HomeGUI());
//        thread.start();
        loginGUI = new LoginGUI();
        loginGUI.setVisible(true);
    }

    public static void exit(int status) {
        System.exit(status);
    }

}
