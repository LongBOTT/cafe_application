package com.coffee.utils;

import com.coffee.BLL.*;
import com.coffee.DTO.*;
import com.coffee.utils.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDImmutableRectangle;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDFont;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Pdf {
    private PDDocument document;
    private PDPage currentPage;
    private PDPageContentStream contentStream;
    private PDType0Font regularFont;
    private PDType0Font boldFont;
    private PDType0Font italicFont;
    public float pageWidth;
    public float pageHeight;
    public float TAB;
    public float LINE;

    public Pdf(float numberOfTabs, float numberOfLines, float tabSize, float lineHeight) {
        Configurator.setLevel("org.apache.fontbox.ttf.gsub.GlyphSubstitutionDataExtractor", org.apache.logging.log4j.Level.OFF);
        Configurator.setLevel("org.apache.pdfbox.pdmodel.PDDocument", org.apache.logging.log4j.Level.OFF);
        Configurator.setAllLevels(LogManager.getRootLogger().getName(), org.apache.logging.log4j.Level.OFF);
        Logger.getLogger("org.apache.fontbox.ttf.gsub.GlyphSubstitutionDataExtractor").setLevel(java.util.logging.Level.OFF);
        Logger.getLogger("org.apache.pdfbox.pdmodel.PDDocument").setLevel(java.util.logging.Level.OFF);
        TAB = tabSize;
        LINE = lineHeight;
        document = new PDDocument();
        currentPage = new PDPage(new PDImmutableRectangle(numberOfTabs * TAB, numberOfLines * LINE));
        document.addPage(currentPage);
        pageWidth = currentPage.getMediaBox().getWidth();
        pageHeight = currentPage.getMediaBox().getHeight();
        regularFont = newFont("Roboto/Roboto-Regular.ttf");
        boldFont = newFont("Roboto/Roboto-Bold.ttf");
        italicFont = newFont("Roboto/Roboto-Italic.ttf");
        try {
            contentStream = new PDPageContentStream(document, currentPage);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void closeDocument(File file) {
        try {
            contentStream.close();
            document.save(file);
            document.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void addText(String text, float x, float y, PDFont font, float fontSize) {
        try {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x, pageHeight - y);
            contentStream.showText(text);
            contentStream.endText();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTextAt(String text, float tabs, float lines, PDFont font, float fontSize) {
        addText(text, tabs * TAB, lines * LINE, font, fontSize);
    }

    public void addLine(float x1, float y1, float x2, float y2, float lineWidth) {
        try {
            contentStream.setLineWidth(lineWidth);
            contentStream.moveTo(x1, pageHeight - y1);
            contentStream.lineTo(x2, pageHeight - y2);
            contentStream.stroke();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addLineAt(float tabs1, float lines1, float tabs2, float lines2, float lineWidth) {
        addLine(tabs1 * TAB, lines1 * LINE, tabs2 * TAB, lines2 * LINE, lineWidth);
    }

    public void addTable(List<String[]> data, float fontSize, float startLine, float firstRow, float[] columns) {
        int rows = data.size();

        for (int i = 0; i <= rows; ++i)
            addLineAt(columns[0], startLine + i, columns[columns.length - 1], startLine + i, 0.5F);
        for (float column : columns)
            addLineAt(column, startLine, column, startLine + rows, 0.5F);
        for (int j = 0; j < columns.length - 1; ++j)
            addTextAt(data.get(0)[j], columns[j] + 0.1F, firstRow, boldFont, fontSize);
        for (int i = 1; i < rows; ++i)
            for (int j = 0; j < columns.length - 1; ++j)
                addTextAt(data.get(i)[j], columns[j] + 0.1F, firstRow + i, regularFont, fontSize - 1);
    }

    public static boolean exportPDF(Import_Note import_note, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        List<Import_Note> import_noteList = new Import_NoteBLL().searchImport("id = '" + import_note.getId() + "'");
        int receiptSize = import_noteList.size();
        File file = new File(path + "/" + import_note.getId() + ".pdf");
        Pdf pdf = new Pdf(15, 11 + receiptSize, 20F, 15F);

        pdf.addTextAt("PHIẾU NHẬP HÀNG", 4.5F, 2, pdf.boldFont, 14);

        pdf.addTextAt("Mã phiếu:", 1, 3.5F, pdf.boldFont, 7);
        pdf.addTextAt(String.valueOf(import_note.getId()), 4, 3.5F, pdf.regularFont, 6);

        pdf.addTextAt("Ngày:", 9, 3.5F, pdf.boldFont, 7);
        pdf.addTextAt(import_note.getReceived_date().toString(), 12, 3.5F, pdf.regularFont, 6);

        pdf.addTextAt("Tên nhân viên:", 9, 4.5F, pdf.boldFont, 7);
        Staff staff = new StaffBLL().searchStaffs("STAFF_ID = '" + import_note.getStaff_id() + "'").get(0);
        String[] name = staff.getName().split(" ");
        pdf.addTextAt(name[name.length - 2] + " " + name[name.length - 1], 12, 4.5F, pdf.regularFont, 6);

        List<String[]> tableData = new ArrayList<>();

        tableData.add(new String[]{ "Mã Lô", "Nguyên liệu", "Tên Nhà Cung Cấp", "Số Lượng Nhập",
                "Số Lượng Tồn", "Ngày Sản Xuất", "Ngày Hết Hạn" });
        MaterialBLL materialBLL = new MaterialBLL();
        ShipmentBLL shipmentBLL = new ShipmentBLL();
        SupplierBLL supplierBLL = new SupplierBLL();
        for (int i = 0; i < receiptSize; ++i) {
            Import_Note importNote = import_noteList.get(i);
            Material material = materialBLL.searchMaterials("id = '" + importNote.getId() + "'").get(0);

            Shipment shipment = shipmentBLL.searchShipments("id = '" + importNote.getId() + "'").get(0);
            Supplier supplier = supplierBLL.searchSuppliers("id = '" + importNote.getId() + "'").get(0);

            String[] data = new String[6];
            data[0] = String.valueOf(shipment.getId());
            data[1] = material.getName();
            data[2] = supplier.getName();
            data[3] = String.valueOf(shipment.getQuantity());
            data[4] = String.valueOf(shipment.getRemain());
            data[5] = String.valueOf(shipment.getMfg());
            data[6] = String.valueOf(shipment.getExp());

            tableData.add(data);
        }
        pdf.addTable(tableData, 7F, 5.3F, 6F, new float[]{1, 2, 8, 9, 11, 14});

        pdf.addTextAt("Tổng Tiền:", 1, 3.5F, pdf.boldFont, 7);
        pdf.addTextAt(String.valueOf(import_note.getTotal()), 4, 3.5F, pdf.regularFont, 7);


        pdf.closeDocument(file);
        return true;
    }


    PDType0Font newFont(String path) {
        try (InputStream fontFile = Resource.loadInputStream("font/" + path)) {
            return PDType0Font.load(document, fontFile);
        } catch (Exception e) {
            return null;
        }
    }

}
