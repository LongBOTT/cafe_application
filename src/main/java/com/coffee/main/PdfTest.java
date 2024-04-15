//package com.coffee.main;
//
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//
//
//
//    public class PdfTest {
//        public static void main(String[] args) {
//            JFrame frame = new JFrame("PDF Export Test");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(600, 400);
//
//            JTable table = new JTable();
//            DefaultTableModel model = new DefaultTableModel();
//
//            model.addColumn("ID");
//            model.addColumn("Name");
//            model.addColumn("Price");
//
//            model.addRow(new Object[]{1, "Coffee", 3.5});
//            model.addRow(new Object[]{2, "Tea", 2.0});
//            model.addRow(new Object[]{3, "Cake", 5.0});
//
//            table.setModel(model);
//
//
//            JButton exportButton = new JButton("Export to PDF");
//            exportButton.addActionListener(e -> {
//                String filePath = Pdf.chooseFilePath("pdf");
//
//                if (filePath != null) {
//                    Pdf.exportToPdfRecipe(
//                            "2024-03-07", // Ngày tạo
//                            "John Doe",   // Thu ngân
//                            table,        // Bảng dữ liệu
//                            "20.00",      // Tổng tiền
//                            "50.00",      // Tiền khách đưa
//                            "30.00",      // Tiền thối lại
//                            filePath      // Đường dẫn lưu file
//                    );
//                }
//            });
//
//            frame.setLayout(new BorderLayout());
//            frame.add(new JScrollPane(table), BorderLayout.CENTER);
//            frame.add(exportButton, BorderLayout.SOUTH);
//
//            // Hiển thị giao diện
//            frame.setVisible(true);
//        }
//    }
//
//
