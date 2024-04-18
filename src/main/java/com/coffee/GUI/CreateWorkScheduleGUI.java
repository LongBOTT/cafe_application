package com.coffee.GUI;

import com.coffee.BLL.RoleBLL;

import com.coffee.DTO.*;
import com.coffee.GUI.DialogGUI.FormAddGUI.AddWorkScheduleGUI;
import com.coffee.GUI.components.*;
import com.coffee.ImportExcel.AddWorkScheduleFromExcel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javafx.util.Pair;


import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import static com.coffee.utils.Resource.chooseExcelFile;

public class CreateWorkScheduleGUI extends Layout1 {
    private static JLabel jLabelDateWork;
    private JLabel iconNext;
    private JLabel iconPrev;
    private List<Function> functions;
    private static WorkSchedulePanel workSchedulePanelManager;
    private static WorkSchedulePanel workSchedulePanelStaffSale;
    private static WorkSchedulePanel workSchedulePanelStaffWarehouse;
    private boolean edit = false;
    private boolean remove = false;
    private static Date today;
    private static List<Date> weekDates;

    public CreateWorkScheduleGUI() {
        super();
        today = java.sql.Date.valueOf(LocalDate.now());
        init();
    }

    private void init() {
        jLabelDateWork = new JLabel();
        iconNext = new JLabel(new FlatSVGIcon("icon/next.svg"));
        iconPrev = new JLabel(new FlatSVGIcon("icon/previous.svg"));
        weekDates = new ArrayList<>();
        workSchedulePanelManager = new WorkSchedulePanel(new RoleBLL().searchRoles("name = 'Quản lý'").get(0).getId());
        workSchedulePanelStaffSale = new WorkSchedulePanel(new RoleBLL().searchRoles("name = 'Nhân viên bán hàng'").get(0).getId());
        workSchedulePanelStaffWarehouse = new WorkSchedulePanel(new RoleBLL().searchRoles("name = 'Nhân viên kho'").get(0).getId());
        RoundedPanel TitlePanelManager = new RoundedPanel();
        RoundedPanel TitlePanelStaffSale = new RoundedPanel();
        RoundedPanel TitlePanelStaffWarehouse = new RoundedPanel();
        RoundedPanel ContentPanelManager = new RoundedPanel();
        RoundedPanel ContentPanelStaffSale = new RoundedPanel();
        RoundedPanel ContentPanelStaffWarehouse = new RoundedPanel();
        JPanel jPanelDate = new JPanel();
        JLabel jLabelTitleManager = new JLabel("    Nhân Viên Quản Lý");
        JLabel jLabelTitleSale = new JLabel("    Nhân Viên Bán Hàng");
        JLabel jLabelTitleWarehouse = new JLabel("    Nhân Viên Kho");

        iconPrev.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iconPrev.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prev();
            }
        });
        SearchPanel.add(iconPrev);
        jPanelDate.setLayout(new BorderLayout());
        jPanelDate.setBackground(Color.white);
        jPanelDate.setPreferredSize(new Dimension(350, 40));
        SearchPanel.add(jPanelDate);

        jLabelDateWork.setFont(new Font("Lexend", Font.BOLD, 15));
        jLabelDateWork.setHorizontalAlignment(JLabel.CENTER);
        jPanelDate.add(jLabelDateWork, BorderLayout.CENTER);
        loadDate();

        iconNext.setCursor(new Cursor(Cursor.HAND_CURSOR));
        iconNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                next();
            }
        });
        SearchPanel.add(iconNext);

        RoundedPanel refreshPanel = new RoundedPanel();
        refreshPanel.setLayout(new GridBagLayout());
        refreshPanel.setPreferredSize(new Dimension(130, 40));
        refreshPanel.setBackground(new Color(217, 217, 217));
        refreshPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                refresh();
            }
        });
        FunctionPanel.add(refreshPanel);

        JLabel refreshLabel = new JLabel("Làm mới");
        refreshLabel.setFont(new Font("Public Sans", Font.PLAIN, 13));
        refreshLabel.setIcon(new FlatSVGIcon("icon/refresh.svg"));
        refreshPanel.add(refreshLabel);

        RoundedPanel roundedPanelAdd = new RoundedPanel();
        roundedPanelAdd.setLayout(new GridBagLayout());
        roundedPanelAdd.setPreferredSize(new Dimension(130, 40));
        roundedPanelAdd.setBackground(new Color(217, 217, 217));
        roundedPanelAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        roundedPanelAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new AddWorkScheduleGUI();
                refresh();
            }
        });
        FunctionPanel.add(roundedPanelAdd);
        JLabel panelAdd = new JLabel("Thêm mới");
        panelAdd.setFont(new Font("Public Sans", Font.PLAIN, 13));
        panelAdd.setIcon(new FlatSVGIcon("icon/add.svg"));
        roundedPanelAdd.add(panelAdd);

        RoundedPanel roundedPanelExcel = new RoundedPanel();
        roundedPanelExcel.setLayout(new GridBagLayout());
        roundedPanelExcel.setPreferredSize(new Dimension(130, 40));
        roundedPanelExcel.setBackground(new Color(217, 217, 217));
        roundedPanelExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FunctionPanel.add(roundedPanelExcel);

        JLabel panelExcel = new JLabel("Nhập Excel");
        panelExcel.setFont(new Font("Public Sans", Font.PLAIN, 13));
        panelExcel.setIcon(new FlatSVGIcon("icon/excel.svg"));
        roundedPanelExcel.add(panelExcel);
        roundedPanelExcel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                File file = chooseExcelFile(null);
                if (file != null) {
                    Pair<Boolean, String> result;
                    try {
                        result = new AddWorkScheduleFromExcel().addWorkScheduleFromExcel(file);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (!result.getKey()) {
                        JOptionPane.showMessageDialog(null, result.getValue(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Thêm lịch làm việc thành công",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        refresh();
                    }
                }
            }
        });

        RoundedPanel roundedPanelPDF = new RoundedPanel();
        roundedPanelPDF.setLayout(new GridBagLayout());
        roundedPanelPDF.setPreferredSize(new Dimension(130, 40));
        roundedPanelPDF.setBackground(new Color(217, 217, 217));
        roundedPanelPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FunctionPanel.add(roundedPanelPDF);

        JLabel panelPDF = new JLabel("Xuất PDF");
        panelPDF.setFont(new Font("Public Sans", Font.PLAIN, 13));
        panelPDF.setIcon(new FlatSVGIcon("icon/pdf.svg"));
        roundedPanelPDF.add(panelPDF);

        bottom.setBackground(Color.white);
        bottom.setLayout(new FlowLayout(FlowLayout.LEFT));

        TitlePanelManager.setLayout(new FlowLayout(FlowLayout.LEFT));
        TitlePanelManager.setBackground(Color.white);
        bottom.add(TitlePanelManager);

        jLabelTitleManager.setFont(new Font("Lexend", Font.BOLD, 15));
        TitlePanelManager.add(jLabelTitleManager);

        ContentPanelManager.setLayout(new BorderLayout());
        ContentPanelManager.setPreferredSize(new Dimension(1160, 185));
        ContentPanelManager.setBackground(Color.white);
        bottom.add(ContentPanelManager);

        ContentPanelManager.add(workSchedulePanelManager);

        TitlePanelStaffSale.setLayout(new FlowLayout(FlowLayout.LEFT));
        TitlePanelStaffSale.setBackground(Color.white);
        bottom.add(TitlePanelStaffSale);

        jLabelTitleSale.setFont(new Font("Lexend", Font.BOLD, 15));
        TitlePanelStaffSale.add(jLabelTitleSale);

        ContentPanelStaffSale.setLayout(new BorderLayout());
        ContentPanelStaffSale.setPreferredSize(new Dimension(1160, 185));
        ContentPanelStaffSale.setBackground(Color.white);
        bottom.add(ContentPanelStaffSale);

        ContentPanelStaffSale.add(workSchedulePanelStaffSale);

        TitlePanelStaffWarehouse.setLayout(new FlowLayout(FlowLayout.LEFT));
        TitlePanelStaffWarehouse.setBackground(Color.white);
        bottom.add(TitlePanelStaffWarehouse);

        jLabelTitleWarehouse.setFont(new Font("Lexend", Font.BOLD, 15));
        TitlePanelStaffWarehouse.add(jLabelTitleWarehouse);

        ContentPanelStaffWarehouse.setLayout(new BorderLayout());
        ContentPanelStaffWarehouse.setPreferredSize(new Dimension(1160, 185));
        ContentPanelStaffWarehouse.setBackground(Color.white);
        bottom.add(ContentPanelStaffWarehouse);

        ContentPanelStaffWarehouse.add(workSchedulePanelStaffWarehouse);

    }

    public static List<Date> getWeekDatesContaining(Date date) {
        List<Date> weekDates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Tìm ngày đầu tiên của tuần (Thứ Hai)
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_WEEK, -1);
        }

        // Lặp qua các ngày từ Thứ Hai đến Chủ nhật và thêm vào danh sách
        for (int i = 0; i < 7; i++) {
            weekDates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }

        return weekDates;
    }

    private static void loadDate() {
        weekDates = getWeekDatesContaining(today);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(weekDates.get(0)) +
                " - " +
                new SimpleDateFormat("dd/MM/yyyy").format(weekDates.get(weekDates.size() - 1));
        jLabelDateWork.setText(date);
    }

    public static void refresh() {
        today = java.sql.Date.valueOf(LocalDate.now());
        loadDate();
        workSchedulePanelManager.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
        workSchedulePanelStaffSale.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
        workSchedulePanelStaffWarehouse.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));

    }

    private void prev() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        today = calendar.getTime();

        loadDate();
        workSchedulePanelManager.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
        workSchedulePanelStaffSale.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
        workSchedulePanelStaffWarehouse.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
    }

    private void next() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        today = calendar.getTime();

        loadDate();
        workSchedulePanelManager.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
        workSchedulePanelStaffSale.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
        workSchedulePanelStaffWarehouse.showWorkSchedule(weekDates.get(0), weekDates.get(weekDates.size() - 1));
    }

}
