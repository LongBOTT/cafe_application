package com.coffee.DAL;

import com.coffee.DTO.Payroll;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAL extends Manager{
    public PayrollDAL() {
        super("payroll",
                List.of("id",
                        "staff_id",
                        "month",
                        "year",
                        "hours_amount",
                        "bonus_amount",
                        "deduction_amount",
                        "salary_amount"));
    }

    public List<Payroll> convertToPayrolls(List<List<String>> data ){
        return convert(data, row -> {
            try {
                return new Payroll(
                        Integer.parseInt(row.get(0)), // id
                        Integer.parseInt(row.get(1)), // staff_id
                        Integer.parseInt(row.get(2)), // month
                        Integer.parseInt(row.get(3)), // year
                        Double.parseDouble(row.get(4)), // hours_amount
                        Double.parseDouble(row.get(5)), // bonus_amount
                        Double.parseDouble(row.get(6)), // deduction_amount
                        Double.parseDouble(row.get(7)) // salary_amount
                );
            } catch (Exception e) {
                System.out.println("Error occurred in RoleDAL.convertToPayroll(): " + e.getMessage());
            }
            return new Payroll();
        });
    }
    public int addPayroll(Payroll payroll) {
        try {
            return create(payroll.getId(),
                    payroll.getStaff_id(),
                    payroll.getMonth(),
                    payroll.getYear(),
                    payroll.getHours_amount(),
                    payroll.getBonus_amount(),
                    payroll.getDeduction_amount(),
                    payroll.getSalary_amount()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in PayrollDAL.addPayroll(): " + e.getMessage());
        }
        return 0;
    }
    public int updatePayroll(Payroll payroll) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(payroll.getId());
            updateValues.add(payroll.getStaff_id());
            updateValues.add(payroll.getMonth());
            updateValues.add(payroll.getYear());
            updateValues.add(payroll.getHours_amount());
            updateValues.add(payroll.getBonus_amount());
            updateValues.add(payroll.getDeduction_amount());
            updateValues.add(payroll.getSalary_amount());
            return update(updateValues, "id = " + payroll.getId());
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in PayrollDAL.updatePayroll(): " + e.getMessage());
        }
        return 0;
    }

    public List<Payroll> searchPayrolls(String... conditions) {
        try {
            return convertToPayrolls(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in PayrollDAL.searchPayrolls(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
