package com.coffee.DAL;

import com.coffee.utils.Database;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MySQL {
    public MySQL() {
    }

    public List<List<String>> executeQuery(String query, Object... values) throws SQLException, IOException {
        Connection connection = Database.getConnection();
        if (connection == null)
            return new ArrayList<>();
        List<List<String>> result;
        try (Statement statement = connection.createStatement()) {
            String formattedQuery = formatQuery(query, values);
            result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(formattedQuery);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i).toString());
                }
                result.add(row);
            }
            System.out.println(formattedQuery);
            System.out.println();
        }
        Database.closeConnection(connection);
        return result;
    }

    public int executeUpdate(String query, Object... values) throws SQLException, IOException {
        Connection connection = Database.getConnection();
        if (connection == null)
            return 0;
        int numOfRows;
        try (Statement statement = connection.createStatement()) {
            String formattedQuery = formatQuery(query, values);
            numOfRows = statement.executeUpdate(formattedQuery);
            System.out.println(formattedQuery);
            System.out.println();
        }
        Database.closeConnection(connection);
        return numOfRows;
    }

    public void executeProcedureAddMaterial(int id, String size, int quantity) throws SQLException, IOException {
        Connection connection = Database.getConnection();
        if (connection == null)
            return;
        int numOfRows;
        try (Statement statement = connection.createStatement()) {
            CallableStatement cstmt = connection.prepareCall("{CALL addMaterial(?, ?, ?)}");

            // Thiết lập tham số
            cstmt.setLong(1, id);
            cstmt.setString(2, size);
            cstmt.setInt(3, quantity);

            // Thực thi thủ tục lưu trữ và kiểm tra kết quả
            boolean hasResultSet = cstmt.execute();
        }
        Database.closeConnection(connection);
    }

    public void executeProcedureSubMaterial(int id, String size, int quantity) throws SQLException, IOException {
        Connection connection = Database.getConnection();
        if (connection == null)
            return;
        int numOfRows;
        try (Statement statement = connection.createStatement()) {
            CallableStatement cstmt = connection.prepareCall("{CALL subMaterial(?, ?, ?)}");

            // Thiết lập tham số
            cstmt.setLong(1, id);
            cstmt.setString(2, size);
            cstmt.setInt(3, quantity);

            // Thực thi thủ tục lưu trữ và kiểm tra kết quả
            boolean hasResultSet = cstmt.execute();
        }
        Database.closeConnection(connection);
    }

    public String formatQuery(String query, Object... values) {
        String stringValue;
        for (Object value : values) {
            if (value instanceof Date day) {
                stringValue = "'" + day + "'";
            } else if (value instanceof String || value instanceof Character) {
                stringValue = "'" + value + "'";
            } else if (value instanceof Boolean) {
                stringValue = (boolean) value ? "1" : "0";
            } else if (value instanceof Number) {
                stringValue = value.toString();
            } else if (value instanceof LocalDateTime) {
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                stringValue = "'" + ((LocalDateTime) value).format(myFormatObj) + "'";
            } else {
                stringValue = "'" + value + "'";
            }
            query = query.replaceFirst("\\?", stringValue);
        }
        return query;
    }

    public static List<List<String>> executeQueryStatistic(String query) throws SQLException, IOException {
        Connection connection = Database.getConnection();
        if (connection == null)
            return new ArrayList<>();
        List<List<String>> result;
        try (Statement statement = connection.createStatement()) {
            result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    if (resultSet.getObject(i) == null)
                        row.add("0");
                    else
                        row.add(resultSet.getObject(i).toString());
                }
                result.add(row);
            }
            System.out.println(query);
            System.out.println();
        }
        Database.closeConnection(connection);
        return result;
    }

    public static List<List<String>> getSaleProduct(String productName, String size, String productCategory, String... dates) {
        String query = "SELECT pro.id, pro.`name`, pro.size, SUM(rd.price), SUM(rd.quantity)\n" +
                "FROM receipt rp JOIN receipt_detail rd ON rp.id = rd.receipt_id\n" +
                "\t\t\t\t\t\t\t\tJOIN product pro ON pro.id = rd.product_id AND pro.size = rd.size\n";
        if ((productName != null && size != null) || productCategory != null || dates.length != 0) {
            String[] strings = new String[0];
            if (productName != null && size != null) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.name = '" + productName + "' AND pro.size = '" + size + "'";
            }

            if (!Objects.equals(productCategory, "Tất cả")) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.category = '" + productCategory + "'";
            }

            if (dates.length != 0) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "DATE(rp.invoice_date) >= '" + dates[0] + "' AND DATE(rp.invoice_date) <= '" + dates[1] + "'";
            }
            query += "WHERE " + String.join(" AND ", strings);
        }
        query += "\nGROUP BY pro.id, pro.`name`, pro.size\n";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTop5SaleProduct(String productName, String size, String productCategory, String... dates) {
        String query = "SELECT pro.id, pro.`name`, pro.size, SUM(rd.price), SUM(rd.quantity)\n" +
                "FROM receipt rp JOIN receipt_detail rd ON rp.id = rd.receipt_id\n" +
                "\t\t\t\t\t\t\t\tJOIN product pro ON pro.id = rd.product_id AND pro.size = rd.size\n";
        if ((productName != null && size != null) || productCategory != null || dates.length != 0) {
            String[] strings = new String[0];
            if (productName != null && size != null) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.name = '" + productName + "' AND pro.size = '" + size + "'";
            }

            if (!Objects.equals(productCategory, "Tất cả")) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.category = '" + productCategory + "'";
            }

            if (dates.length != 0) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "DATE(rp.invoice_date) >= '" + dates[0] + "' AND DATE(rp.invoice_date) <= '" + dates[1] + "'";
            }
            query += "WHERE " + String.join(" AND ", strings);
        }
        query += "\nGROUP BY pro.id, pro.`name`, pro.size\n" +
                "ORDER BY SUM(rd.price) DESC LIMIT 5";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTop5BestSellers(String productName, String size, String productCategory, String... dates) {
        String query = "SELECT pro.id, pro.`name`, pro.size, SUM(rd.quantity)\n" +
                "FROM receipt rp JOIN receipt_detail rd ON rp.id = rd.receipt_id\n" +
                "\t\t\t\t\t\t\t\tJOIN product pro ON pro.id = rd.product_id AND pro.size = rd.size\n";
        if ((productName != null && size != null) || productCategory != null || dates.length != 0) {
            String[] strings = new String[0];
            if (productName != null && size != null) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.name = '" + productName + "' AND pro.size = '" + size + "'";
            }

            if (!Objects.equals(productCategory, "Tất cả")) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.category = '" + productCategory + "'";
            }

            if (dates.length != 0) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "DATE(rp.invoice_date) >= '" + dates[0] + "' AND DATE(rp.invoice_date) <= '" + dates[1] + "'";
            }
            query += "WHERE " + String.join(" AND ", strings);
        }
        query += "\nGROUP BY pro.id, pro.`name`, pro.size\n" +
                "ORDER BY SUM(rd.quantity) DESC LIMIT 5";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getProfitProduct(String productName, String size, String productCategory, String... dates) {
        String query = "SELECT pro.id, pro.`name`, pro.size, SUM(rd.price - pro.capital_price * rd.quantity), SUM(rd.quantity), SUM(rd.price), SUM(pro.capital_price * rd.quantity), SUM(rd.price - pro.capital_price * rd.quantity)/SUM(rd.price)*100\n" +
                "FROM receipt rp JOIN receipt_detail rd ON rp.id = rd.receipt_id\n" +
                "\t\t\t\t\t\t\t\tJOIN product pro ON pro.id = rd.product_id AND pro.size = rd.size\n";
        if ((productName != null && size != null) || productCategory != null || dates.length != 0) {
            String[] strings = new String[0];
            if (productName != null && size != null) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.name = '" + productName + "' AND pro.size = '" + size + "'";
            }

            if (!Objects.equals(productCategory, "Tất cả")) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.category = '" + productCategory + "'";
            }

            if (dates.length != 0) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "DATE(rp.invoice_date) >= '" + dates[0] + "' AND DATE(rp.invoice_date) <= '" + dates[1] + "'";
            }
            query += "WHERE " + String.join(" AND ", strings);
        }
        query += "\nGROUP BY pro.id, pro.`name`, pro.size\n";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTop5ProfitProduct(String productName, String size, String productCategory, String... dates) {
        String query = "SELECT pro.id, pro.`name`, pro.size, SUM(rd.price - pro.capital_price * rd.quantity), SUM(rd.quantity), SUM(rd.price), SUM(pro.capital_price * rd.quantity), SUM(rd.price - pro.capital_price * rd.quantity)/SUM(rd.price)*100\n" +
                "FROM receipt rp JOIN receipt_detail rd ON rp.id = rd.receipt_id\n" +
                "\t\t\t\t\t\t\t\tJOIN product pro ON pro.id = rd.product_id AND pro.size = rd.size\n";
        if ((productName != null && size != null) || productCategory != null || dates.length != 0) {
            String[] strings = new String[0];
            if (productName != null && size != null) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.name = '" + productName + "' AND pro.size = '" + size + "'";
            }

            if (!Objects.equals(productCategory, "Tất cả")) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.category = '" + productCategory + "'";
            }

            if (dates.length != 0) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "DATE(rp.invoice_date) >= '" + dates[0] + "' AND DATE(rp.invoice_date) <= '" + dates[1] + "'";
            }
            query += "WHERE " + String.join(" AND ", strings);
        }
        query += "\nGROUP BY pro.id, pro.`name`, pro.size\n" +
                "ORDER BY SUM(rd.price - pro.capital_price * rd.quantity) DESC LIMIT 5";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTop5CapitalizationRate(String productName, String size, String productCategory, String... dates) {
        String query = "SELECT pro.id, pro.`name`, pro.size, SUM(rd.price - pro.capital_price * rd.quantity)/SUM(rd.price)*100\n" +
                "FROM receipt rp JOIN receipt_detail rd ON rp.id = rd.receipt_id\n" +
                "\t\t\t\t\t\t\t\tJOIN product pro ON pro.id = rd.product_id AND pro.size = rd.size\n";
        if ((productName != null && size != null) || productCategory != null || dates.length != 0) {
            String[] strings = new String[0];
            if (productName != null && size != null) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.name = '" + productName + "' AND pro.size = '" + size + "'";
            }

            if (!Objects.equals(productCategory, "Tất cả")) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "pro.category = '" + productCategory + "'";
            }

            if (dates.length != 0) {
                strings = Arrays.copyOf(strings, strings.length + 1);
                strings[strings.length - 1] = "DATE(rp.invoice_date) >= '" + dates[0] + "' AND DATE(rp.invoice_date) <= '" + dates[1] + "'";
            }
            query += "WHERE " + String.join(" AND ", strings);
        }
        query += "\nGROUP BY pro.id, pro.`name`, pro.size\n" +
                "ORDER BY SUM(rd.price - pro.capital_price * rd.quantity)/SUM(rd.price)*100 DESC LIMIT 5";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTotalImport(String materialName, String start, String end) {
        String query = "SELECT ma.id, ma.`name`, SUM(sm.quantity)\n" +
                "FROM shipment sm JOIN material ma ON sm.material_id = ma.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN import_note im on im.id = sm.import_id\n";
        String[] strings = new String[0];
        if (materialName != null) {
            strings = Arrays.copyOf(strings, strings.length + 1);
            strings[strings.length - 1] = "ma.name = '" + materialName + "'";
        }
        strings = Arrays.copyOf(strings, strings.length + 1);
        strings[strings.length - 1] = "im.received_date >= '" + start + "' AND im.received_date <= '" + end + "'";
        query += "WHERE " + String.join(" AND ", strings);
        query += "\nGROUP BY ma.id, ma.`name`";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTotalExport(String materialName, String start, String end) {
        String query = "SELECT ma.id, ma.`name`, SUM(CASE WHEN ed.reason = 'Huỷ' THEN ed.quantity ELSE 0 END), SUM(CASE WHEN ed.reason = 'Bán' THEN ed.quantity ELSE 0 END), SUM(ed.quantity)\n" +
                "FROM export_detail ed JOIN shipment sm ON ed.shipment_id = sm.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN material ma ON sm.material_id = ma.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN export_note ep on ep.id = ed.export_id\n";
        String[] strings = new String[0];
        if (materialName != null) {
            strings = Arrays.copyOf(strings, strings.length + 1);
            strings[strings.length - 1] = "ma.name = '" + materialName + "'";
        }
        strings = Arrays.copyOf(strings, strings.length + 1);
        strings[strings.length - 1] = "ep.invoice_date >= '" + start + "' AND ep.invoice_date <= '" + end + "'";
        query += "WHERE " + String.join(" AND ", strings);
        query += "\nGROUP BY ma.id, ma.`name`";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTop5MostImport(String materialName, String start, String end) {
        String query = "SELECT ma.id, ma.`name`, SUM(sm.quantity)\n" +
                "FROM shipment sm JOIN material ma ON sm.material_id = ma.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN import_note im on im.id = sm.import_id\n";
        String[] strings = new String[0];
        if (materialName != null) {
            strings = Arrays.copyOf(strings, strings.length + 1);
            strings[strings.length - 1] = "ma.name = '" + materialName + "'";
        }
        strings = Arrays.copyOf(strings, strings.length + 1);
        strings[strings.length - 1] = "im.received_date >= '" + start + "' AND im.received_date <= '" + end + "'";
        query += "WHERE " + String.join(" AND ", strings);
        query += "\nGROUP BY ma.id, ma.`name`\n";
        query += "ORDER BY SUM(sm.quantity) DESC LIMIT 5\n";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getTop5MostExports(String materialName, String start, String end) {
        String query = "SELECT ma.id, ma.`name`, SUM(CASE WHEN ed.reason = 'Huỷ' THEN ed.quantity ELSE 0 END), SUM(CASE WHEN ed.reason = 'Bán' THEN ed.quantity ELSE 0 END), SUM(ed.quantity)\n" +
                "FROM export_detail ed JOIN shipment sm ON ed.shipment_id = sm.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN material ma ON sm.material_id = ma.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN export_note ep on ep.id = ed.export_id\n";
        String[] strings = new String[0];
        if (materialName != null) {
            strings = Arrays.copyOf(strings, strings.length + 1);
            strings[strings.length - 1] = "ma.name = '" + materialName + "'";
        }
        strings = Arrays.copyOf(strings, strings.length + 1);
        strings[strings.length - 1] = "ep.invoice_date >= '" + start + "' AND ep.invoice_date <= '" + end + "'";
        query += "WHERE " + String.join(" AND ", strings);
        query += "\nGROUP BY ma.id, ma.`name`\n";
        query += "ORDER BY SUM(ed.quantity) DESC LIMIT 5\n";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getDestroyExports(String materialName, String start, String end) {
        String query = "SELECT ma.id, ma.`name`,  SUM(ed.quantity), ma.unit_price * SUM(ed.quantity)\n" +
                "FROM export_detail ed JOIN shipment sm ON ed.shipment_id = sm.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN material ma ON sm.material_id = ma.id\n" +
                "\t\t\t\t\t\t\t\t\tJOIN export_note ep on ep.id = ed.export_id\n";
        String[] strings = new String[0];
        if (materialName != null) {
            strings = Arrays.copyOf(strings, strings.length + 1);
            strings[strings.length - 1] = "ma.name = '" + materialName + "'";
        }
        strings = Arrays.copyOf(strings, strings.length + 1);
        strings[strings.length - 1] = "ep.invoice_date >= '" + start + "' AND ep.invoice_date <= '" + end + "'";
        query += "WHERE " + String.join(" AND ", strings);
        query += "\nGROUP BY ma.id, ma.`name`, ma.unit_price\n";
        try {
            return executeQueryStatistic(query);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
