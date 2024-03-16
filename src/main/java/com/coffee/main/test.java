package com.coffee.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class test {
    public static void main(String[] args) {
        // Dữ liệu mẫu cho bảng
        Object[][] data = {
                {"Product 1", ""},
                {"Product 2", ""},
                {"Product 3", ""}
        };

        // Tiêu đề cột
        String[] columns = {"Product", "Size"};

        // Tạo model cho bảng
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa ô ở cột "Size"
                return column == 1;
            }
        };

        // Tạo bảng
        JTable table = new JTable(model);

        // Tạo combobox chứa các giá trị "L", "M", "S"
        JComboBox<String> sizeComboBox = new JComboBox<>(new String[]{"L", "M", "S"});

        // Thiết lập cell editor cho cột "Size" là combobox
        table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(sizeComboBox));

        // Hiển thị bảng
        JFrame frame = new JFrame();
        frame.add(new JScrollPane(table));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

