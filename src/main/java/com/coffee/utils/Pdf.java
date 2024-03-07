package com.coffee.utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Pdf {
    public static void exportToPdfRecipe( String creationDate, String cashier, JTable dataTable, String totalAmount, String customerPayment, String changeAmount, String filePath) {
        Document document = new Document();

        try {
            Font fontTitle = setFont("Arial", 18, Font.BOLD, BaseColor.BLACK);
            Font fontTitleTable = setFont("Arial", 14, Font.BOLD, BaseColor.BLACK);
            Font fontNormal = setFont("Arial", 14, Font.NORMAL, BaseColor.BLACK);
            File file = new File(filePath);
            if (file.exists()) {

                return;
            }

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

//            document.add(new Paragraph("Hóa đơn số: " + invoiceNumber, fontNormal));

            document.add(new Paragraph("Ngày tạo: " + creationDate, fontNormal));

            document.add(new Paragraph("Thu ngân: " + cashier, fontNormal));

            document.add(Chunk.NEWLINE);

            Paragraph title = new Paragraph("Hóa đơn thanh toán", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            PdfPTable table = createTable(dataTable, fontTitleTable, fontNormal);
            document.add(table);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Tổng tiền: " + totalAmount, fontNormal));

            // Kiểm tra nếu customerPayment không phải là null
            if (customerPayment != null) {
                document.add(new Paragraph("Tiền khách đưa: " + customerPayment, fontNormal));
            }

            // Kiểm tra nếu changeAmount không phải là null
            if (changeAmount != null) {
                document.add(new Paragraph("Tiền thối lại: " + changeAmount, fontNormal));
            }

            JOptionPane.showMessageDialog(null, "Xuất file thành công");

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
    private static PdfPTable createTable(JTable dataTable, Font fontTitleTable, Font fontNormal) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();

        PdfPTable table = new PdfPTable(colCount);
        table.setWidthPercentage(100);

        // Thêm tiêu đề cột
        for (int col = 0; col < colCount; col++) {
            PdfPCell cell = new PdfPCell(new Phrase(dataTable.getColumnName(col), fontTitleTable));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }

        // Thêm dữ liệu từ JTable
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(model.getValueAt(row, col)), fontNormal));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }
        }

        return table;
    }

    public static String chooseFilePath(String fileType) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter;

        switch (fileType.toLowerCase()) {
            case "pdf" ->
                    filter = new FileNameExtensionFilter("PDF files (*.pdf)", "pdf");
            case "xlsx" ->
                    filter = new FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx");
            default ->
                    throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }

        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            if (!filePath.toLowerCase().endsWith("." + fileType.toLowerCase())) {
                filePath += "." + fileType.toLowerCase();
            }

            return filePath;
        } else {
            return null;
        }
    }
    private static Font setFont(String fontPath, int size, int style, BaseColor color) {
        try {
            BaseFont unicodeFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            return new Font(unicodeFont, size, style, color);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return new Font(Font.FontFamily.TIMES_ROMAN, size, style, color);
        }
    }
}
