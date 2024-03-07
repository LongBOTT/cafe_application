package com.coffee.main;

import com.coffee.utils.Pdf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;



    public class PdfTest {
        public static void main(String[] args) {
            // Tạo giao diện Swing
            JFrame frame = new JFrame("PDF Export Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Tạo bảng để hiển thị dữ liệu
            JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel();

            // Thêm cột vào bảng
            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Price");

            // Thêm dữ liệu vào bảng
            model.addRow(new Object[]{1, "Coffee", 3.5});
            model.addRow(new Object[]{2, "Tea", 2.0});
            model.addRow(new Object[]{3, "Cake", 5.0});

            // Gán model cho bảng
            table.setModel(model);


            JButton exportButton = new JButton("Export to PDF");
            exportButton.addActionListener(e -> {
                // Chọn đường dẫn lưu file
                String filePath = Pdf.chooseFilePath("pdf");

                // Xuất file PDF
                if (filePath != null) {
                    Pdf.exportToPdfRecipe(
                            "2024-03-07", // Ngày tạo
                            "John Doe",   // Thu ngân
                            table,        // Bảng dữ liệu
                            "20.00",      // Tổng tiền
                            "50.00",      // Tiền khách đưa
                            "30.00",      // Tiền thối lại
                            filePath      // Đường dẫn lưu file
                    );
                }
            });

            // Thêm các thành phần vào giao diện
            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.add(exportButton, BorderLayout.SOUTH);

            // Hiển thị giao diện
            frame.setVisible(true);
        }
    }


