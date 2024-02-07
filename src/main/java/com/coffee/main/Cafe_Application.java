package com.coffee.main;

import com.coffee.BLL.AccountBLL;
import com.coffee.DAL.AccountDAL;
import com.coffee.DAL.MySQL;
import com.coffee.DTO.Account;
import com.coffee.utils.Database;

import java.io.IOException;
import java.sql.SQLException;

public class Cafe_Application {
    public static void main(String[] args) {
        AccountBLL accountBLL = new AccountBLL();
        System.out.println(accountBLL.deleteAccount(new Account(1, "abc", 4, 5)));
    }
}
