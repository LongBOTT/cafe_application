package com.coffee.GUI.components;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DataTable extends JTable {
    private int lastSelectedRow = -1;
    private boolean isUniqueTable;

    public DataTable(Object[][] data, Object[] columnNames) {
        super(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        for (int i = 0; i < getColumnCount(); i++) {
            setDefaultRenderer(getColumnClass(i), new CustomTableCellRenderer());
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

        for (int i = 0; i < getColumnCount(); i++) {
            setDefaultRenderer(getColumnClass(i), new CustomTableCellRenderer());
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
                if (!detail && !edit && remove && col != numberOfColumns && row != -1 && col != numberOfColumns + 1 && col != numberOfColumns + 2) {
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

    public DataTable(Object[][] data, Object[] columnNames, ActionListener actionListener, boolean detail, boolean edit, boolean remove, int numberOfColumns, int checkbox) {
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

        for (int i = numberOfColumns; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(new CustomPanelRenderer(false));
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

                if ((col == numberOfColumns && row != -1) || (col == numberOfColumns + 1 && row != -1) || (col == numberOfColumns + 2 && row != -1) || (col == checkbox && row != -1)) {
                    if (actionListener != null) {
                        actionListener.actionPerformed(null);
                    }
                }
                if (!detail && !edit && remove && col != numberOfColumns && row != -1 && col != numberOfColumns + 1 && col != numberOfColumns + 2) {
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

    public DataTable(Object[][] data, Object[] columnNames, ActionListener actionListener, ActionListener actionListenerChange,
                     boolean detail, boolean edit, boolean remove, int numberOfColumns, boolean material, int quantity) {
        super(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == quantity;
            }

        });


        getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int col = e.getColumn();
                    if (col == quantity && row != -1) {
//                        Object valueAtColumn4 = getModel().getValueAt(row, quantity);
//                        Object valueAtColumn3 = getModel().getValueAt(row, quantity - 1);
//                        if (valueAtColumn4 != null && valueAtColumn3 != null) {
//                            try {
//                                Double newValue = Double.parseDouble(valueAtColumn4.toString()) * Double.parseDouble(valueAtColumn3.toString());
//                                getModel().setValueAt(newValue, row, quantity + 1);
//                            } catch (NumberFormatException ex) {
//                                JOptionPane.showMessageDialog(null, "Số lượng không phải là số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                                SwingUtilities.invokeLater(() -> {
//                                    editCellAt(row, col);
//                                    getEditorComponent().requestFocusInWindow();
//                                });
//                            }
//                        }
                        if (actionListenerChange != null) {
                            actionListenerChange.actionPerformed(null);
                        }
                    }
                }
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

                if ((col == numberOfColumns && row != -1) || (col == numberOfColumns + 1 && row != -1) || (col == numberOfColumns + 2 && row != -1) || (col == quantity && row != -1)) {
                    if (actionListener != null) {
                        actionListener.actionPerformed(null);
                    }
                }
                if (!detail && !edit && remove && col != numberOfColumns && row != -1 && col != numberOfColumns + 1 && col != numberOfColumns + 2) {
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

