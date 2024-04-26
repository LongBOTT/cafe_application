package com.coffee.DAL;

import com.coffee.utils.Database;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
}
