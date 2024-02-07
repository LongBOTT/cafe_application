package com.coffee.DAL;

import com.coffee.DTO.Receipt;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDAL extends Manager{
    public ReceiptDAL() {
        super("receipt",
                List.of("id",
                        "staff_id",
                        "total",
                        "invoice_date",
                        "received",
                        "excess"));
    }

    public List<Receipt> convertToReceipts(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Receipt(
                        Integer.parseInt(row.get(0)), // id
                        Integer.parseInt(row.get(1)), // staff_id
                        Double.parseDouble(row.get(2)), // total
                        Date.valueOf(row.get(3)), // invoice_date
                        Double.parseDouble(row.get(4)), // received
                        Double.parseDouble(row.get(5)) // excess
                );
            } catch (Exception e) {
                System.out.println("Error occurred in ReceiptDAL.convertToReceipts(): " + e.getMessage());
            }
            return new Receipt();
        });
    }

    public int addReceipt(Receipt receipt) {
        try {
            return create(receipt.getId(),
                    receipt.getStaff_id(),
                    receipt.getTotal(),
                    receipt.getInvoice_date(),
                    receipt.getReceived(),
                    receipt.getExcess()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in ReceiptDAL.addReceipt(): " + e.getMessage());
        }
        return 0;
    }

    public List<Receipt> searchReceipts(String... conditions) {
        try {
            return convertToReceipts(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in ReceiptDAL.searchReceipts(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
