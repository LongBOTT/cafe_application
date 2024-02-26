package com.coffee.DAL;

import com.coffee.DTO.Receipt_Detail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Receipt_DetailDAL extends Manager{
    public Receipt_DetailDAL() {
        super("receipt_detail",
                List.of("receipt_id",
                        "product_id",
                        "quantity",
                        "price"));
    }

    public List<Receipt_Detail> convertToReceipt_Details(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Receipt_Detail(
                        Integer.parseInt(row.get(0)), // receipt_id
                        Integer.parseInt(row.get(1)), // product_id
                        Double.parseDouble(row.get(2)), // quantity
                        Double.parseDouble(row.get(3)) // price
                );
            } catch (Exception e) {
                System.out.println("Error occurred in Receipt_DetailDAL.convertToReceipt_Details(): " + e.getMessage());
            }
            return new Receipt_Detail();
        });
    }

    public int addReceipt_Detail(Receipt_Detail receipt_detail) {
        try {
            return create(receipt_detail.getReceipt_id(),
                    receipt_detail.getProduct_id(),
                    receipt_detail.getQuantity(),
                    receipt_detail.getPrice()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Receipt_DetailDAL.addReceipt_Detail(): " + e.getMessage());
        }
        return 0;
    }

    public List<Receipt_Detail> searchReceipt_Details(String... conditions) {
        try {
            return convertToReceipt_Details(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Receipt_DetailDAL.searchReceipt_Details(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
