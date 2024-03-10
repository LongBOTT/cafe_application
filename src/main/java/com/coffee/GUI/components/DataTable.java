package com.coffee.GUI.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataTable extends JTable {
    private int lastSelectedRow = -1;

    public DataTable(Object[][] data, Object[] columnNames) {
        super(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        getTableHeader().setFont(new Font("Public Sans", Font.BOLD | Font.ITALIC, 15));
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);

        setFont(new Font("Public Sans", Font.PLAIN, 15));
        setAutoCreateRowSorter(false);
        setRowHeight(40);
        setSelectionBackground(new Color(220, 221, 225, 221));
        setSelectionForeground(Color.BLACK);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (getSelectedRow() == -1) {
                    lastSelectedRow = -1;
                }
            }
        });
//        addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                int row = rowAtPoint(e.getPoint());
//                if (row != -1 && row != lastSelectedRow) {
//                    setRowSelectionInterval(lastSelectedRow, lastSelectedRow);
//                }
//            }
//        });

        JTableHeader jTableHeader = getTableHeader();
        jTableHeader.setBackground(new Color(217, 217, 217));
    }

    public DataTable(Object[][] data, Object[] columnNames, int checkbox) {
        super(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == checkbox) {
                    return Boolean.class;
                }
                return String.class;
            }
        });

        getTableHeader().setFont(new Font("Public Sans", Font.BOLD | Font.ITALIC, 15));
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);

        setFont(new Font("Public Sans", Font.PLAIN, 15));
        setAutoCreateRowSorter(false);
        setRowHeight(40);
        setSelectionBackground(new Color(220, 221, 225, 221));
        setSelectionForeground(Color.BLACK);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastSelectedRow = rowAtPoint(e.getPoint());
//                if (actionListener != null) {
//                    actionListener.actionPerformed(null);
//                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (getSelectedRow() == -1) {
                    lastSelectedRow = -1;
                }
            }
        });
//        addMouseMotionListener(new MouseAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                int row = rowAtPoint(e.getPoint());
//                if (row != -1 && row != lastSelectedRow) {
//                    setRowSelectionInterval(lastSelectedRow, lastSelectedRow);
//                }
//            }
//        });
        JTableHeader jTableHeader = getTableHeader();
        jTableHeader.setBackground(new Color(217, 217, 217));
    }

    public DataTable(Object[][] data, Object[] columnNames, ActionListener actionListener, boolean detail, boolean edit, boolean remove, int numberOfColumns) {
        super(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        });
        for (int i = 0; i < getColumnCount(); i++) {
            setDefaultRenderer(getColumnClass(i), new CustomTableCellRenderer());
        }
        for (int i = numberOfColumns; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(new CustomPanelRenderer());
            getColumnModel().getColumn(i).setMaxWidth(50);
        }

        getTableHeader().setFont(new Font("Public Sans", Font.BOLD | Font.ITALIC, 15));
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);

        setFont(new Font("Public Sans", Font.PLAIN, 15));
        setAutoCreateRowSorter(false);
        setRowHeight(40);
        setSelectionBackground(new Color(220, 221, 225, 221));
        setSelectionForeground(Color.BLACK);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int col = columnAtPoint(e.getPoint());

                if ((col == numberOfColumns && row != -1) || (col == numberOfColumns + 1 && row != -1) || (col == numberOfColumns + 2 && row != -1)) {
                    if (actionListener != null) {
                        actionListener.actionPerformed(null);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (getSelectedRow() == -1) {
                    lastSelectedRow = -1;
                }
            }
        });

        JTableHeader jTableHeader = getTableHeader();
        jTableHeader.setBackground(new Color(217, 217, 217));
    }

}

