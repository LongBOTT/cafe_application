package com.coffee.GUI;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.DTO.Module;
import com.coffee.GUI.components.RoundedPanel;
import com.coffee.GUI.components.RoundedScrollPane;
import com.coffee.main.Cafe_Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HomeGUI extends JFrame {
    private final StaffBLL staffBLL = new StaffBLL();
    private final RoleBLL roleBLL = new RoleBLL();
    public static Account account;
    public static Staff staff;
    public static Role role;
    private JPanel contentPanel;
    private JPanel left;
    private JPanel right;
    private JPanel jPanelLogo;
    private JPanel staffInfo;
    private RoundedPanel infor;
    private RoundedPanel menu;
    private RoundedPanel center;
    private RoundedPanel content;
    private RoundedPanel[] modules;
    private RoundedPanel currentModule;
    private RoundedPanel logout;
    private RoundedScrollPane scrollPane;
    private JLabel name = new JLabel();
    private JLabel roleName = new JLabel();
    private JLabel iconLogo;
    private JLabel iconInfo;
    private JLabel iconLogout;
    private JLabel[] moduleNames;
    private JPanel[] allPanelModules;
    private Color color;
    private Color colorOver;
    private int currentPanel = 0;
    private boolean pressover;
    private boolean over = false;

    public HomeGUI() {
        initComponents();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        getUser();
        initMenu();
    }

    public void getUser() {
        staff = staffBLL.findStaffsBy(Map.of("id", account.getStaff_id())).get(0);

        Role_detailBLL roleDetailBLL = new Role_detailBLL();
        List<Role_detail> role_detailList = new Role_detailBLL().searchRole_detailsByStaff(staff.getId());
        Role_detail roleDetail = role_detailList.get(0);
        role = roleBLL.findRolesBy(Map.of("id", roleDetail.getRole_id())).get(0);

        name.setText("<html>" + staff.getName() + "</html>");
        roleName.setText("<html>Chức vụ: " + role.getName() + "</html>");
    }

    public void initComponents() {
        setIconImage(new FlatSVGIcon("image/coffee_logo.svg").getImage());
        setTitle("Hệ thống quản lý cửa hàng coffee");
        setResizable(false);
        setPreferredSize(new Dimension(1440, 800));
        setMinimumSize(new Dimension());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        contentPanel = new JPanel();
        left = new JPanel();
        right = new JPanel();
        jPanelLogo = new JPanel();
        staffInfo = new JPanel();
        infor = new RoundedPanel();
        menu = new RoundedPanel();
        center = new RoundedPanel();
        content = new RoundedPanel();
        logout = new RoundedPanel();
        scrollPane = new RoundedScrollPane(menu, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        iconInfo = new JLabel();
        iconLogo = new JLabel();
        iconLogout = new JLabel();
        color = new Color(0xFFFFFF);
        colorOver = new Color(185, 184, 184);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.white);
        setContentPane(contentPanel);

        left.setLayout(new MigLayout("", "10[]10", "10[]10[]"));
        left.setBackground(Color.white);
        left.setPreferredSize(new Dimension(250, 800));
        contentPanel.add(left, BorderLayout.WEST);

        right.setLayout(new BorderLayout());
        contentPanel.add(right, BorderLayout.CENTER);

        infor.setLayout(new MigLayout());
        infor.setBackground(new Color(232, 206, 180));
        infor.setPreferredSize(new Dimension(250, 150));
        left.add(infor, "span, wrap");

        jPanelLogo.setBackground(new Color(232, 206, 180));
        jPanelLogo.setPreferredSize(new Dimension(40, 120));
        infor.add(jPanelLogo);

        roleName.setFont(new Font("FlatLaf.style", Font.PLAIN, 15));
        infor.add(roleName, "wrap");

        staffInfo.setBackground(new Color(232, 206, 180));
        staffInfo.setPreferredSize(new Dimension(40, 80));
        infor.add(staffInfo);

        iconLogo.setIcon(new FlatSVGIcon("icon/avatar.svg"));
        jPanelLogo.add(iconLogo);

        iconInfo.setIcon(new FlatSVGIcon("icon/profile.svg"));
        staffInfo.add(iconInfo);

        name.setFont(new Font("FlatLaf.style", Font.BOLD, 15));
        infor.add(name, "wrap");

        menu.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        menu.setBackground(new Color(217, 217, 217));
        menu.setPreferredSize(new Dimension(200, 500));
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);

        scrollPane.setPreferredSize(new Dimension(250, 550));
//        scrollPane.setViewportBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(217,217,217)));
        left.add(scrollPane, "span, wrap");

        logout.setLayout(new FlowLayout(FlowLayout.CENTER));
        logout.setBackground(new Color(232, 206, 180));
        logout.setPreferredSize(new Dimension(160, 40));
        logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logout.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                exit();
            }
        });
        left.add(logout, "span, wrap, center");

        iconLogout = new JLabel("Đăng xuất");
        iconLogout.setIcon(new FlatSVGIcon("icon/logout.svg"));
        iconLogout.setPreferredSize(new Dimension(140, 30));
        iconLogout.setHorizontalAlignment(SwingConstants.LEFT);
        iconLogout.setVerticalAlignment(SwingConstants.CENTER);
        iconLogout.setFont((new Font("FlatLaf.style", Font.PLAIN, 15)));
        iconLogout.setIconTextGap(20);
        iconLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iconLogout.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                exit();
            }
        });
        logout.add(iconLogout);

        center.setLayout(new BorderLayout());
        center.setBackground(Color.white);
        center.setBorder(BorderFactory.createMatteBorder(10, 0, 15, 10, Color.white));
        right.add(center, BorderLayout.CENTER);

        content.setLayout(new BorderLayout());
        content.setBackground(new Color(232, 206, 180));
        center.add(content, BorderLayout.CENTER);

//        content.add(new SaleGUI(account), BorderLayout.CENTER);

//        checkDiscount();
    }

    private void checkDiscount() {
//        DiscountBLL discountBLL = new DiscountBLL();
//        Discount_detailBLL discountDetailBLL = new Discount_detailBLL();
//        for (Discount discount : discountBLL.getDiscountList()){
//            try {
//                if (discount.getEnd_date().isBefore(Date.parseDate(Date.dateNow()))) {
//                    discount.setStatus(true);
//                    discountBLL.updateDiscount(discount);
//                    for (Discount_detail discountDetail : discountDetailBLL.findDiscount_detailsBy(Map.of("discount_id", discount.getId()))) {
//                        discountDetail.setStatus(true);
//                        discountDetailBLL.updateDiscount_detail(discountDetail);
//                    }
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    private void initMenu() {
        menu.removeAll();
        Pair<List<Module>, List<List<Function>>> result = getModulesAndFunctionsFromRole(role.getId());
        List<Module> moduleList = result.getKey();
        List<List<Function>> function2D = result.getValue();

        allPanelModules = new RoundedPanel[moduleList.size()];
        modules = new RoundedPanel[moduleList.size()];
        moduleNames = new JLabel[moduleList.size()];
        for (int i = 0; i < modules.length; i++) {
            modules[i] = new RoundedPanel();
            modules[i].setLayout(new FlowLayout());
            modules[i].setPreferredSize(new Dimension(200, 41));
            modules[i].setBackground(new Color(0xFFFFFF));
            modules[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            Module module = moduleList.get(i);
            List<Function> functions = function2D.get(i);
            allPanelModules[i] = getPanelModule(module.getId(), functions);
            int index = i;
            modules[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!pressover && index != currentPanel) {
                        e.getComponent().setBackground(colorOver);
                        over = true;
                    }
                }

                public void mouseExited(MouseEvent e) {
                    if (!pressover && index != currentPanel) {
                        e.getComponent().setBackground(color);
                        over = false;
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if (!pressover && index != currentPanel) {
                        if (over) {
                            e.getComponent().setBackground(colorOver);
                        } else {
                            e.getComponent().setBackground(color);
                        }
                    }
                }

                public void mousePressed(MouseEvent e) {
                    openModule(allPanelModules[index]);
                    Active(modules[index]);
                    currentPanel = index;
                }
            });
            menu.add(modules[i]);

            moduleNames[i] = new JLabel(module.getName());
            moduleNames[i].setIcon(new FlatSVGIcon("icon/icon_module.svg"));
            moduleNames[i].setPreferredSize(new Dimension(190, 35));
            moduleNames[i].setHorizontalAlignment(SwingConstants.LEFT);
            moduleNames[i].setVerticalAlignment(SwingConstants.CENTER);
            moduleNames[i].setFont((new Font("FlatLaf.style", Font.PLAIN, 14)));
            moduleNames[i].setIconTextGap(30);
            moduleNames[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            modules[i].add(moduleNames[i]);
        }
        menu.setPreferredSize(new Dimension(200, Math.max(500, modules.length * 52)));
        openModule(allPanelModules[0]);
        Active(modules[0]);
    }

    public Pair<List<Module>, List<List<Function>>> getModulesAndFunctionsFromRole(int roleID) {
        DecentralizationBLL decentralizationBLL = new DecentralizationBLL();
        List<Decentralization> decentralizations = decentralizationBLL.searchDecentralizations("role_id = " + roleID);
        List<Module> modules = new ArrayList<>();
        List<List<Function>> function2D = new ArrayList<>();
        ModuleBLL moduleBLL = new ModuleBLL();
        FunctionBLL functionBLL = new FunctionBLL();
        for (int i = 0; i < decentralizations.size(); i++) {
            int moduleID = decentralizations.get(i).getModule_id();
            List<Function> functions = new ArrayList<>();
            boolean canView = false;
            do {
                int functionID = decentralizations.get(i).getFunction_id();
                Function function = functionBLL.findFunctionsBy(Map.of("id", functionID)).get(0);
                if (function.getId() == 1) // view
                    canView = true;
                functions.add(function);
            } while (++i < decentralizations.size() && decentralizations.get(i).getModule_id() == moduleID);
            if (canView) {
                modules.add(moduleBLL.findModulesBy(Map.of("id", moduleID)).get(0));
                function2D.add(functions);
            }
            i--;
        }
        return new Pair<>(modules, function2D);
    }

    public JPanel[] getAllPanelModules() {
        return allPanelModules;
    }

    public JPanel getPanelModule(int id, List<Function> functions) {
        return switch (id) {
            case 1 -> new SaleGUI(account);
            case 2 -> new WareHouseGUI();
            case 3 -> new StatisticSalesGUI();
            case 4 -> new StatisticSalaryGUI();
            case 5 -> new StatisticStaffGUI();
            case 6 -> new DiscountGUI(functions);
            case 7 -> new ReceiptGUI();
            case 8 -> new ExportGUI(functions);
            case 9 -> new ImportGUI(functions);
            case 10 -> new ProductGUI(functions);
            case 11 -> new SupplierGUI(functions);
            case 12 -> new StaffGUI(functions, this);
            case 13 -> new Leave_Of_Absence_FormGUI(functions);
            case 14 -> new AccountGUI(functions);
            case 15 -> new DecentralizationGUI(functions);
            case 16 -> new MaterialGUI(functions);
            case 17 -> new InfoGUI(account, staff);
            case 18 -> new MyWorkScheduleGUI(staff);
            case 19 -> new CreateWorkScheduleGUI();
            default -> new RoundedPanel();
        };
    }

    public void openModule(JPanel module) {
        content.removeAll();
        content.add(module, BorderLayout.CENTER);
        content.repaint();
        content.revalidate();
        System.gc();
    }

    private void Disable() {
        if (currentModule != null) {
            currentModule.setBackground(color);
        }
    }

    private void Active(RoundedPanel module) {
        Disable();
        currentModule = module;
        module.setBackground(colorOver);
    }

    public void exit() {
        int message = JOptionPane.showOptionDialog(null,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Đăng xuất",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Đăng xuất", "Huỷ"},
                "Đăng xuất");
        if (message == JOptionPane.YES_OPTION) {
            dispose();
            System.gc();
            Cafe_Application.loginGUI.setVisible(true);
        }
    }

    public static void main(String[] args) {
        AccountBLL accountBLL = new AccountBLL();
        HomeGUI homeGUI = new HomeGUI();
        homeGUI.setVisible(true);
        homeGUI.setAccount(accountBLL.searchAccounts().get(0));
    }
}
