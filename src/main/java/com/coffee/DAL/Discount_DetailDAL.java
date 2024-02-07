package com.coffee.DAL;

import com.coffee.DTO.Discount_Detail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Discount_DetailDAL extends Manager{
    public Discount_DetailDAL() {
        super("discount_detail",
                List.of("discount_id",
                        "product_id",
                        "percent"
                ));
    }

    public List<Discount_Detail> convertToDiscountDetails(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Discount_Detail(
                        Integer.parseInt(row.get(0)), // discount_id
                        Integer.parseInt(row.get(1)), // product_id
                        Double.parseDouble(row.get(2)) // percent
                );
            } catch (Exception e) {
                System.out.println("Error occurred in Discount_DetailDAL.convertToDiscountDetails(): " + e.getMessage());
            }
            return new Discount_Detail();
        });
    }

    public int addDiscountDetail(Discount_Detail discount_detail) {
        try {
            return create(discount_detail.getDiscount_id(),
                    discount_detail.getProduct_id(),
                    discount_detail.getPercent()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Discount_DetailDAL.addDiscountDetail(): " + e.getMessage());
        }
        return 0;
    }

    public int updateDiscountDetail(Discount_Detail discount_detail) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(discount_detail.getDiscount_id());
            updateValues.add(discount_detail.getProduct_id());
            updateValues.add(discount_detail.getPercent());
            return update(updateValues,
                    "discount_id = " + discount_detail.getDiscount_id(),
                    "product_id = " +discount_detail.getProduct_id());
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Discount_DetailDAL.updateDiscountDetail(): " + e.getMessage());
        }
        return 0;
    }

    public List<Discount_Detail> searchDiscountDetails(String... conditions) {
        try {
            return convertToDiscountDetails(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Discount_detailsDAL.searchDiscountDetails(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
