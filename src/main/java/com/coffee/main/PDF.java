package com.coffee.main;

import com.coffee.BLL.*;
import com.coffee.DAL.StaffDAL;
import com.coffee.DTO.*;
import com.coffee.utils.Resource;
import com.coffee.utils.VNString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDImmutableRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

import static com.coffee.GUI.components.WorkSchedulePanel.getDaysBetween;

public class PDF {
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

        public PDF(float numberOfTabs, float numberOfLines, float tabSize, float lineHeight) {
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



    public static String getFileNameDatetodate(String path, String name, Date from, Date to) {
            int max = 0;
            File[] files = new File(path).listFiles();
            for (File file : Objects.requireNonNull(files)) {
                if (file.getName().startsWith(name)) {
                    String numStr = file.getName().substring(name.length(), file.getName().indexOf("_"));
                    int num = Integer.parseInt(numStr);
                    if (num > max)
                        max = num;
                }
            }
            return String.format("%s/%s%03d_%s_%s.pdf", path, name, max + 1, from.toString(), to.toString() );
        }

    public static String getFileName(String path, String name, Date date) {
        int max = 0;
        File[] files = new File(path).listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.getName().startsWith(name)) {
                String numStr = file.getName().substring(name.length(), file.getName().indexOf("_"));
                int num = Integer.parseInt(numStr);
                if (num > max)
                    max = num;
            }
        }
        return String.format("%s/%s%03d_%s.pdf", path, name, max + 1, date.toString());
    }



    public static boolean exportBillDetailsPDF(Export_Note exportNote, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        List<Export_Detail> exports = new Export_DetailBLL().searchExport("export_id = "+ exportNote.getId());
        File file = new File(getFileName(path, "exBill_Details", java.sql.Date.valueOf(LocalDate.now())));
        PDF pdf = new PDF(8, 12 + exports.size(), 125F, exports.size()+40F);

        pdf.addTextAt("BẢNG HÓA ĐƠN", 3.3F,2, pdf.boldFont, 25);
        pdf.addTextAt(" Mã Phiếu Xuất : " + exportNote.getId(), 0.5F, 3, pdf.italicFont, 20);

        int staffId = exportNote.getStaff_id();
        List<Staff> staff = new StaffBLL().searchStaffs("id = '" + staffId + "'" );

        pdf.addTextAt(" Tên Nhân Viên : " + staff.get(0).getName(), 0.5F, 4, pdf.italicFont, 20);
        pdf.addTextAt(" Ngày Tạo : " + exportNote.getInvoice_date(), 0.5F, 5, pdf.italicFont, 20);
//            pdf.addTextAt("Từ ngày " + from + " đến ngày " + to, 2.5F,4, pdf.italicFont, 20);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(List.of(
                "Mã Lô",
                "Nguyên Liệu",
                "Số Lượng",
                "Lý Do").toArray(new String[0]));


        for (Export_Detail export_detail : exports) {
            String[] data = new String[4];
            data[0] = String.valueOf(export_detail.getShipment_id());

            int shipmentId = export_detail.getShipment_id();
            Shipment shipment = new ShipmentBLL().findShipmentsBy(Map.of("id",shipmentId)).get(0);

            int materialId = shipment.getMaterial_id();
            Material material = new MaterialBLL().findMaterialsBy(Map.of("id", materialId)).get(0);

            data[1] = String.valueOf(material.getName());
            data[2] = String.valueOf(export_detail.getQuantity());
            data[3] = String.valueOf(export_detail.getReason());
            tableData.add(data);
        }

        pdf.addTable(tableData, 16F, 6.5F, 7.1F, new float[]{0.5F, 1.5F, 4F, 5F, 6F});

        String formattedTotal = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(exportNote.getTotal());
        pdf.addTextAt(" Tổng tiền: " + formattedTotal, 0.5F, exports.size()+ 10, pdf.italicFont, 20);

        pdf.closeDocument(file);
        return true;
    }

    public static boolean importBillDetailsPDF(Import_Note importNote, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        List<Shipment> shipments = new ShipmentBLL().searchShipments("import_id = "+ importNote.getId());
        File file = new File(getFileName(path, "imBill_Details", java.sql.Date.valueOf(LocalDate.now())));
        PDF pdf = new PDF(8, 12 + shipments.size(), 180F, shipments.size()+40F);

        pdf.addTextAt("BẢNG CHI TIẾT HÓA ĐƠN", 3.3F,2, pdf.boldFont, 25);
        pdf.addTextAt(" Mã Phiếu Nhập : " + importNote.getId(), 0.5F, 3, pdf.italicFont, 20);

        int staffId = importNote.getStaff_id();
        List<Staff> staff = new StaffBLL().searchStaffs("id = '" + staffId + "'" );

        pdf.addTextAt(" Tên Nhân Viên : " + staff.get(0).getName(), 0.5F, 4, pdf.italicFont, 20);
        pdf.addTextAt(" Ngày Nhập : " + importNote.getReceived_date(), 0.5F, 5, pdf.italicFont, 20);
//            pdf.addTextAt("Từ ngày " + from + " đến ngày " + to, 2.5F,4, pdf.italicFont, 20);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(List.of(
                "Mã Lô",
                "Nguyên Liệu", "Tên Nhà Cung Cấp",
                "Số Lượng Nhập ", "Số Lượng Tồn ",
                "Ngày Sản Xuất","Ngày Hết Hạn ").toArray(new String[0]));


        for (Shipment shipment : shipments) {
            String[] data = new String[7];
            data[0] = String.valueOf(shipment.getImport_id());

            int materialId = shipment.getMaterial_id();
            Material material = new MaterialBLL().findMaterialsBy(Map.of("id", materialId)).get(0);
            data[1] = String.valueOf(material.getName());
            int supplierId = shipment.getSupplier_id();
            Supplier supplier = new SupplierBLL().findSuppliersBy(Map.of("id",supplierId)).get(0);
            data[2] = String.valueOf(supplier.getName());

            data[3] = String.valueOf(shipment.getQuantity());
            data[4] = String.valueOf(shipment.getRemain());
            data[5] = String.valueOf(shipment.getMfg());
            data[6] = String.valueOf(shipment.getExp());
            tableData.add(data);
        }

        pdf.addTable(tableData, 16F, 6.5F, 7.1F, new float[]{0.5F, 1F, 2.2F, 3.2F, 4F,4.8F, 5.6F, 6.3F});

        String formattedTotal = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(importNote.getTotal());
        pdf.addTextAt(" Tổng tiền: " + formattedTotal, 0.5F, shipments.size()+ 10, pdf.italicFont, 20);
        pdf.closeDocument(file);
        return true;
    }

    public static boolean exportMaterialPDF(Object[][] objects, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getFileName(path, "exMaterial", java.sql.Date.valueOf(LocalDate.now())));
        PDF pdf = new PDF(6, 10 + objects.length , 167F, 30F);
        pdf.addTextAt("BẢNG NGUYÊN LIỆU ", 2.2F, 2, pdf.boldFont, 25);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(List.of(
                "Id",
                "Tên Nguyên Liệu",
                " Tồn Kho "," Đơn Vị ",
                "Giá Vốn").toArray(new String[0]));

        for (int i = 0; i < objects.length; i++) {
            String[] data = new String[5];
            data[0] = objects[i][0].toString();
            data[1] = objects[i][1].toString();
            data[2] = objects[i][2].toString();
            data[3] = objects[i][3].toString();
            data[4] = VNString.currency(Double.parseDouble(objects[i][4].toString()));
            tableData.add(data);
        }

        pdf.addTable(tableData, 16F, 5F, 5.6F, new float[]{0.5F, 1F, 2.5F, 3.5F, 4F, 5F});

        pdf.closeDocument(file);
        return true;
    }

    public static boolean exportReceiptDetialsPDF(Receipt receipt, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        List<Receipt_Detail> receipts = new Receipt_DetailBLL().searchReceipt_Details("receipt_id = " + receipt.getId());
        File file = new File(getFileName(path, "exReceipt_Detials", java.sql.Date.valueOf(LocalDate.now())));
        PDF pdf = new PDF(8, 18+ receipts.size(), 125F, 30F); // note
        pdf.addTextAt("BẢNG CHI TIẾT HÓA ĐƠN", 3.3F, 2, pdf.boldFont, 25);

        pdf.addTextAt(" Mã Hóa Đơn : " + receipt.getId(), 0.5F, 3, pdf.italicFont, 20);
        int staffId = receipt.getStaff_id();
        List<Staff> staff = new StaffBLL().searchStaffs("id = '" + staffId + "'");
        pdf.addTextAt(" Tên Nhân Viên : " + staff.get(0).getName(), 0.5F, 4, pdf.italicFont, 20);
        pdf.addTextAt(" Ngày Tạo : " + receipt.getInvoice_date(), 0.5F, 5, pdf.italicFont, 20);

        List<String[]> tableData = new ArrayList<>();

        tableData.add(List.of(
                " Sản Phẩm ",
                "Loại ",
                " Số Lượng ",
                " Giá ").toArray(new String[0]));

        for (Receipt_Detail receipt_detail : receipts) {
            String[] data = new String[4];
            int productId = receipt_detail.getProduct_id();
            List<Product> products = new ProductBLL().searchProducts("id = '" + productId + "'");

            data[0] = String.valueOf(products.get(0).getName());
            data[1] = receipt_detail.getSize();
            data[2] = String.valueOf(receipt_detail.getQuantity());
            data[3] = VNString.currency(receipt_detail.getPrice());
            tableData.add(data);
        }

        pdf.addTable(tableData, 16F, 6.5F, 7.1F, new float[]{0.5F, 1.5F, 2.5F, 3.5F, 4.5F});

        pdf.addTextAt(" Tổng tiền: " + VNString.currency(receipt.getTotal()), 0.5F, receipts.size() + 9, pdf.italicFont, 20);
        pdf.addTextAt(" Dư thừa: " + VNString.currency(receipt.getExcess()), 0.5F, receipts.size() + 10, pdf.italicFont, 20);
        pdf.addTextAt(" Đã nhận: " + VNString.currency(receipt.getReceived()), 0.5F, receipts.size() +11, pdf.italicFont, 20);

        pdf.closeDocument(file);
        return true;
    }


    public static boolean exportReceiptsPDF(Object[][] objects, Date from, Date to, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getFileNameDatetodate(path, "exReceipts", from, to));
        PDF pdf = new PDF(6, 10 + objects.length , 167F, 30F);
        pdf.addTextAt("BẢNG HÓA ĐƠN ", 2.2F, 2, pdf.boldFont, 25);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(List.of(
                "Mã Hóa Đơn",
                "Nhân Viên",
                " Tổng Tiền ",
                "Ngày Lập").toArray(new String[0]));

        for (int i = 0; i < objects.length; i++) {
            String[] data = new String[4];
            data[0] = objects[i][0].toString();
            data[1] = objects[i][1].toString();
            data[2] = VNString.currency(Double.parseDouble(objects[i][2].toString()));
            data[3] = objects[i][3].toString();

            tableData.add(data);
        }

        pdf.addTable(tableData, 16F, 6F, 6.6F, new float[]{0.5F, 1.5F, 2.5F, 3.5F, 4.5F});

        pdf.closeDocument(file);
        return true;
    }
    public static boolean exportExportNotePDF(Object[][] objects, Date from, Date to, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getFileNameDatetodate(path, "exExport_Note", from, to));
        PDF pdf = new PDF(6, 10 + objects.length , 167F, 30F);
        pdf.addTextAt("BẢNG PHIẾU NHẬP HÀNG", 2.2F, 2, pdf.boldFont, 25);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(List.of(
                "Mã Phiếu Nhập ",
                "Nhân Viên",
                " Tổng Tiền ",
                "Ngày Xuất ").toArray(new String[0]));

        for (int i = 0; i < objects.length; i++) {
            String[] data = new String[4];
            data[0] = objects[i][0].toString();
            data[1] = new StaffBLL().searchStaffs("id = " + Integer.parseInt(objects[i][1].toString())).get(0).getName() ;
            data[2] = VNString.currency(Double.parseDouble(objects[i][2].toString()));
            data[3] = objects[i][3].toString();

            tableData.add(data);
        }

        pdf.addTable(tableData, 16F, 6F, 6.6F, new float[]{0.5F, 1.5F, 2.5F, 3.5F, 4.5F});

        pdf.closeDocument(file);
        return true;
    }
    public static boolean exportImportNotePDF(Object[][] objects, Date from, Date to, String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        File file = new File(getFileNameDatetodate(path, "exExport_Note", from, to));
        PDF pdf = new PDF(6, 10 + objects.length , 167F, 30F);
        pdf.addTextAt("BẢNG PHIẾU NHẬP HÀNG", 2.2F, 2, pdf.boldFont, 25);

        List<String[]> tableData = new ArrayList<>();
        tableData.add(List.of(
                "Mã Phiếu Nhập ",
                "Nhân Viên",
                " Tổng Tiền ",
                "Ngày Xuất ").toArray(new String[0]));

        for (int i = 0; i < objects.length; i++) {
            String[] data = new String[4];
            data[0] = objects[i][0].toString();
            data[1] = new StaffBLL().searchStaffs("id = " + Integer.parseInt(objects[i][1].toString())).get(0).getName() ;
            data[2] = VNString.currency(Double.parseDouble(objects[i][2].toString()));
            data[3] = objects[i][3].toString();

            tableData.add(data);
        }

        pdf.addTable(tableData, 16F, 6F, 6.6F, new float[]{0.5F, 1.5F, 2.5F, 3.5F, 4.5F});

        pdf.closeDocument(file);
        return true;
    }

        public static boolean exportWorkSchedulePDF(Object[][] managers, Object[][] sales, Object[][] warehouses, Date from, Date to, String path) {
            try {
                Files.createDirectories(Paths.get(path));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return false;
            }
            File file = new File(getFileNameDatetodate(path, "importnoteaxc", from, to));
            PDF pdf = new PDF(6, 10 + managers.length + sales.length + warehouses.length, 250F, 30F);

            pdf.addTextAt("CA LÀM NHÂN VIÊN QUẢN LÝ ", 2.2F, 2, pdf.boldFont, 25);

            List<Date> dates = getDaysBetween(from, to);
            DateTimeFormatter dtfInput = DateTimeFormatter.ofPattern("u-M-d", Locale.ENGLISH);
            DateTimeFormatter dtfOutput = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH);

            List<String[]> tableData = new ArrayList<>();

            tableData.add(List.of("",
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(0)), dtfInput).format(dtfOutput) +
                            " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(0)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")",
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(1)), dtfInput).format(dtfOutput) +
                            " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(1)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")",
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(2)), dtfInput).format(dtfOutput) +
                            " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(2)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")",
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(3)), dtfInput).format(dtfOutput) +
                            " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(3)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")",
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(4)), dtfInput).format(dtfOutput) +
                            " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(4)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")",
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(5)), dtfInput).format(dtfOutput) +
                            " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(5)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")",
                    LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(6)), dtfInput).format(dtfOutput) +
                            " (" + LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(dates.get(6)), dtfInput).format(DateTimeFormatter.ofPattern("d/M", Locale.ENGLISH)) + ")" )
                    .toArray(new String[0]));


            String[] datamanager = new String[]{"Shift 1", "", "", "", "", "", "", ""} ;

            for (int i = 0; i < managers.length; i++) {
                int shift = Integer.parseInt(managers[i][3].toString());
                if (shift == 1) {
                    String staffName = new StaffBLL().searchStaffs("id = " + Integer.parseInt(managers[i][1].toString())).get(0).getName();
                    datamanager[1] += staffName;
                    datamanager = new String[]{"", "", "", "", "", "", "", ""} ;
                }
            }


            tableData.add(datamanager);

            pdf.addTable(tableData, 16F, 4F, 4.6F, new float[]{0.5F, 1F, 1.7F, 2.4F, 3.1F, 3.8F, 4.5F, 5.2F});

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

        public static void main(String[] args) {
//            List<Receipt> receipts = new ReceiptBLL().searchReceipts();
//            PDF.exportReceiptsPDF(receipts, java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(LocalDate.now()),
//                    "C:\\Users\\MI\\OneDrive\\Documents\\GitHub\\cafe_application\\src\\main\\java\\com\\coffee\\Export" );

//            List<Export_Note> exportNotes = new Export_NoteBLL().searchExport_Note();
//            PDF.exportBillDetailsPDF(exportNotes.get(0),"C:\\Users\\MI\\OneDrive\\Documents\\GitHub\\cafe_application\\src\\main\\java\\com\\coffee\\Export" );
//
//            List<Import_Note> importNotes = new Import_NoteBLL().searchImport();
//            PDF.importBillDetailsPDF(importNotes.get(0),"C:\\Users\\MI\\OneDrive\\Documents\\GitHub\\cafe_application\\src\\main\\java\\com\\coffee\\Export" );
//

            List<Integer> staffIDList = new ArrayList<>();
            List<Role_Detail> role_detailList = new Role_DetailBLL().searchRole_detailsByRole(2, new SimpleDateFormat("yyyy-MM-dd").format(java.sql.Date.valueOf(LocalDate.now())));
            for (Role_Detail roleDetail : role_detailList)
//            if (!staffIDList.contains(roleDetail.getStaff_id()))
                staffIDList.add(roleDetail.getStaff_id());

            List<Work_Schedule> work_schedules = new ArrayList<>();
            for (Integer id : staffIDList) {
                work_schedules.addAll(new Work_ScheduleBLL().searchWork_schedules("staff_id = " + id,
                        "date >= '2024-03-11'",
                        "date <= '2024-03-18'"));
            }
            PDF.exportWorkSchedulePDF(new Work_ScheduleBLL().getData(work_schedules), new Work_ScheduleBLL().getData(work_schedules),new Work_ScheduleBLL().getData(work_schedules),
                    java.sql.Date.valueOf("2024-03-11"),
                    java.sql.Date.valueOf("2024-03-18"),
                    "C:\\Users\\MI\\OneDrive\\Documents\\GitHub\\cafe_application\\src\\main\\java\\com\\coffee\\Export" );

        }
    }


