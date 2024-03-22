package com.coffee.GUI.components;


import com.coffee.BLL.Role_DetailBLL;
import com.coffee.BLL.StaffBLL;
import com.coffee.BLL.Work_ScheduleBLL;

import com.coffee.DTO.Role_Detail;
import com.coffee.DTO.Staff;
import com.coffee.DTO.Work_Schedule;
import com.coffee.GUI.DialogGUI.FromEditGUI.EditWorkScheduleGUI;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class WorkSchedulePanel extends JScrollPane {
    private final List<String> shifts;
    private RoundedPanel panel;
    private List<JLabel> jLabelsDay;
    private final JPanel[][] roundedPanels;
    private int role_id;

    public WorkSchedulePanel(int role_id) {
        this.role_id = role_id;

        shifts = new ArrayList<>();
        shifts.add("Shift 1: 6h - 12h");
        shifts.add("Shift 2: 12h - 18h");
        shifts.add("Shift 3: 18h - 23h");
        roundedPanels = new JPanel[shifts.size()][7];
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setBackground(Color.white);


        panel = new RoundedPanel();
        jLabelsDay = new ArrayList<>();

        panel.setLayout(new GridLayout(shifts.size() + 1, 7 + 1));
        panel.setBackground(Color.white);

        int i, j;
        panel.add(new JLabel());
        for (i = 0; i < 7; i++) {
            JLabel jLabel = new JLabel();
            jLabel.setBackground(Color.white);
            jLabel.setVerticalAlignment(JLabel.CENTER);
            jLabel.setHorizontalAlignment(JLabel.CENTER);
            jLabel.setFont(new Font("Inter", Font.BOLD, 14));
            jLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            panel.add(jLabel);
            jLabelsDay.add(jLabel);
        }
        for (i = 0; i < shifts.size(); i++) {
            String shift = shifts.get(i);
            JLabel jLabel = new JLabel(shift);
            jLabel.setBackground(Color.white);
            jLabel.setVerticalAlignment(JLabel.CENTER);
            jLabel.setHorizontalAlignment(JLabel.CENTER);
            jLabel.setFont(new Font("Inter", Font.BOLD, 14));
            jLabel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            panel.add(jLabel);
            for (j = 0; j < 7; j++) {
                roundedPanels[i][j] = new JPanel(new MigLayout("", "5[]5", ""));
                roundedPanels[i][j].setBackground(Color.white);
                roundedPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 1));
                panel.add(roundedPanels[i][j]);
            }
        }

        List<Date> weekDates = getCurrentWeek();
        showWorkSchedule(weekDates.get(0), weekDates.get(6));
        this.setViewportView(panel);
    }

    public void showWorkSchedule(Date date1, Date date2) {
        List<Integer> staffIDList = new ArrayList<>();
        List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_detailsByRole(role_id);
        for (Role_Detail roleDetail : role_detailList)
            if (!staffIDList.contains(roleDetail.getStaff_id()))
                staffIDList.add(roleDetail.getStaff_id());

        List<Work_Schedule> work_schedules = new ArrayList<>();
        for (Integer id : staffIDList) {
            work_schedules.addAll(new Work_ScheduleBLL().searchWork_schedules("staff_id = " + id,
                    "date >= '" + new SimpleDateFormat("yyyy-MM-dd").format(date1) + "'",
                    "date <= '" + new SimpleDateFormat("yyyy-MM-dd").format(date2) + "'"));
        }

        List<Date> dates = getDaysBetween(date1, date2);
        for (int i = 0; i < dates.size(); i++) {
            DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("u-M-d", Locale.ENGLISH);
            DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);
            String date = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(i)), dtfInput).format(dtfOutput) +
                    " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(i)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")";
            jLabelsDay.get(i).setText(date);
        }

        for (JPanel[] jPanel : roundedPanels)
            for (JPanel panel1 : jPanel) {
                panel1.removeAll();
                panel1.repaint();
                panel1.revalidate();
            }

        int i, j;
        for (Work_Schedule work_schedule : work_schedules) {
            i = work_schedule.getShift() - 1;
            j = dates.indexOf(work_schedule.getDate());

            Staff staff = new StaffBLL().searchStaffs("id = " + work_schedule.getStaff_id()).get(0);
            JPanel jPanel = new JPanel(new BorderLayout());
            jPanel.setBackground(new Color(0x4CFD8F8F, true));
            jPanel.setPreferredSize(new Dimension(130, 10));
            jPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            jPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    new EditWorkScheduleGUI(work_schedule);
                }
            });

            JLabel jLabelName = new JLabel(staff.getName());
            jLabelName.setFont(new Font("Inter", Font.BOLD, 12));
            jPanel.add(jLabelName, BorderLayout.CENTER);

            String checkin = work_schedule.getCheck_in().equals("null") ? "--" : work_schedule.getCheck_in();
            String checkout = work_schedule.getCheck_out().equals("null") ? "--" : work_schedule.getCheck_out();

            JLabel jLabelCheckInOut = new JLabel(checkin + " " + checkout);
            jLabelCheckInOut.setFont(new Font("Inter", Font.PLAIN, 10));
            jPanel.add(jLabelCheckInOut, BorderLayout.SOUTH);

            roundedPanels[i][j].add(jPanel, "wrap");
        }
    }

    public static List<Date> getDaysBetween(Date start, Date end) {
        List<Date> daysBetween = new ArrayList<>();

        long startTime = start.getTime();
        long endTime = end.getTime();

        long dayInMillis = 1000 * 60 * 60 * 24;

        for (long time = startTime; time <= endTime; time += dayInMillis) {
            daysBetween.add(new Date(time));
        }

        return daysBetween;
    }

    private List<Date> getCurrentWeek() {
        List<Date> weekDates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.sql.Date.valueOf(LocalDate.now()));

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
}
