package com.coffee.GUI;

import com.coffee.BLL.AccountBLL;
import com.coffee.DTO.Account;
import com.coffee.main.Cafe_Application;
import com.coffee.utils.Password;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatProgressBarUI;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class LoginGUI extends JFrame {
    private JProgressBar progressBar;
    private JPanel contentPane;
    private JPanel jPanelLogo;
    private JPanel jPanelTitle;
    private JPanel jPanelTitleLogin;
    private JPanel formLogin;
    private JPanel formInput;
    private JLabel labelLogo;
    private JLabel labelLogin;
    private JLabel labelUsername;
    private JLabel labelPassword;
    private JLabel labelForgetPasswd;
    private JTextField jTextFieldUserName;
    private JPasswordField jTextFieldPassword;
    private JButton jButtonLogin;

    public LoginGUI() {
        initComponents();
    }

    private void initComponents() {
        setIconImage(new FlatSVGIcon("image/coffee_logo.svg").getImage());

        setSize(700, 500);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cancel();
            }
        });

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(232, 206, 180));
        setContentPane(contentPane);

        formLogin = new JPanel(new FlowLayout());
        formLogin.setBackground(new Color(217, 217, 217));
        formLogin.setPreferredSize(new Dimension(400, 500));
        contentPane.add(formLogin, BorderLayout.WEST);

        jPanelLogo = new JPanel(new BorderLayout());
        jPanelLogo.setBackground(new Color(232, 206, 180));
        jPanelLogo.setPreferredSize(new Dimension(300, 500));
        contentPane.add(jPanelLogo, BorderLayout.EAST);

        labelLogo = new JLabel();
        labelLogo.setHorizontalAlignment(SwingConstants.CENTER);

        labelLogo.setIcon(new FlatSVGIcon("image/coffee_logo.svg"));
        jPanelLogo.add(labelLogo);

        jPanelTitle = new JPanel();
        jPanelTitle.setBackground(new Color(217, 217, 217));
        formLogin.add(jPanelTitle, BorderLayout.NORTH);

        jPanelTitleLogin = new JPanel(new GridBagLayout());
        jPanelTitleLogin.setBackground(new Color(217, 217, 217));
        jPanelTitleLogin.setPreferredSize(new Dimension(300, 100));
        jPanelTitle.add(jPanelTitleLogin);

        labelLogin = new JLabel("Đăng Nhập", SwingConstants.CENTER);
        labelLogin.setFont(new Font("FlatLaf.style", Font.BOLD, 30));
        labelLogin.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, new Color(0x000000)));
        jPanelTitleLogin.add(labelLogin);

        formInput = new JPanel(new MigLayout("", "[]", "[]0[]"));
        formInput.setBackground(new Color(217, 217, 217));
        formInput.setPreferredSize(new Dimension(250, 400));
        formLogin.add(formInput, BorderLayout.CENTER);

        labelUsername = new JLabel("Tài khoản:", JLabel.LEFT);
        labelUsername.setForeground(new Color(0x000000));
        labelUsername.setPreferredSize(new Dimension(100, 50));
        labelUsername.setFont(new Font("FlatLaf.style", Font.PLAIN, 15));
        formInput.add(labelUsername, "span, wrap");

        jTextFieldUserName = new JTextField();
        jTextFieldUserName.setBackground(Color.white);
        jTextFieldUserName.setPreferredSize(new Dimension(250, 40));
        jTextFieldUserName.setFont(new Font("FlatLaf.style", Font.PLAIN, 15));
        jTextFieldUserName.putClientProperty("JTextField.placeholderText", "Nhập tài khoản");
        jTextFieldUserName.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                    login();
            }
        });
        formInput.add(jTextFieldUserName, "span, center, wrap");

        labelPassword = new JLabel("Mật khẩu:", JLabel.LEFT);
        labelPassword.setForeground(new Color(0x000000));
        labelPassword.setPreferredSize(new Dimension(100, 50));
        labelPassword.setFont(new Font("FlatLaf.style", Font.PLAIN, 15));
        formInput.add(labelPassword, "span, wrap");

        jTextFieldPassword = new JPasswordField();
        jTextFieldPassword.setBackground(Color.white);
        jTextFieldPassword.setPreferredSize(new Dimension(250, 40));
        jTextFieldPassword.setFont(new Font("FlatLaf.style", Font.PLAIN, 15));
        jTextFieldPassword.putClientProperty("JTextField.placeholderText", "Nhập mật khẩu");
        jTextFieldPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                    login();
            }
        });
        formInput.add(jTextFieldPassword, "span, center, wrap");

        labelForgetPasswd = new JLabel("Quên mật khẩu?");
        labelForgetPasswd.setFont(new Font("FlatLaf.style", Font.PLAIN, 12));
        labelForgetPasswd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        labelForgetPasswd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                labelForgetPasswd.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(232, 206, 180)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelForgetPasswd.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }

            public void mousePressed(MouseEvent e) {
                forgotPassword();
            }
        });
        formInput.add(labelForgetPasswd, "span, right, wrap");

        jButtonLogin = new JButton("Đăng nhập");
        jButtonLogin.setBackground(new Color(232, 206, 180));
        ;
        jButtonLogin.setForeground(Color.BLACK);
        jButtonLogin.setFont(new Font("FlatLaf.style", Font.BOLD, 15));
        jButtonLogin.setPreferredSize(new Dimension(80, 50));
        jButtonLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                login();
            }
        });
        jButtonLogin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    login();
            }
        });
        formInput.add(jButtonLogin, "span, wrap");

    }

    private void progress() {
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("FlatLaf.style", Font.BOLD, 15));
        progressBar.setForeground(new Color(0x97B4EA));
        progressBar.setUI(new FlatProgressBarUI());
        contentPane.add(progressBar, BorderLayout.SOUTH);
        contentPane.repaint();
        contentPane.revalidate();
        int i = 0;
        while (i <= 100) {
            i++;
            progressBar.setValue(i);
            try {
                sleep(30);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        System.gc();
        Cafe_Application.homeGUI.setVisible(true);
        contentPane.remove(progressBar);
        contentPane.repaint();
        contentPane.revalidate();
    }

    public void login() {
        String userName, passWord;
        userName = jTextFieldUserName.getText();
        passWord = new String(jTextFieldPassword.getPassword());
        AccountBLL accountBLL = new AccountBLL();
        List<Account> accountList = accountBLL.findAccountsBy(Map.of("username", userName));
        if (userName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (passWord.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (accountList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String hashedPassword = accountList.get(0).getPassword();
        if (hashedPassword.startsWith("first"))
            hashedPassword = hashedPassword.substring("first".length());
        if (!Password.verifyPassword(passWord, hashedPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không chính xác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Account account = accountList.get(0);
        try {
            Thread thread = new Thread(() -> Cafe_Application.homeGUI.setAccount(account));
            thread.start();
//            thread.join();
            Thread threadProgress = new Thread(this::progress);
            threadProgress.start();
        } catch (Exception ignored) {

        }
    }

    private void forgotPassword() {
        new ChangePasswordGUI().setVisible(true);
    }

    private void cancel() {
        String[] options = new String[]{"Huỷ", "Thoát ứng dụng"};
        int choice = JOptionPane.showOptionDialog(null, "Bạn có muốn thoát ứng dụng?",
                "Lỗi", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        if (choice == 1)
            Cafe_Application.exit(1);
    }

}
