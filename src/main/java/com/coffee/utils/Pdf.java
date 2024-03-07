package com.coffee.utils;
import com.itextpdf.text.pdf.PdfPTable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class Pdf {
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
}
