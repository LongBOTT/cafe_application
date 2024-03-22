package com.coffee.DAL;

import com.coffee.DTO.Role_Detail_Deduction;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Role_Detail_DeductionDAL extends Manager {
    public Role_Detail_DeductionDAL() {
        super("role_detail_deduction",
                List.of("role_id",
                        "staff_id",
                        "entry_date",
                        "deduction_id"));
    }

    public List<Role_Detail_Deduction> convertToRole_Detail_Deductions(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Role_Detail_Deduction(
                        Integer.parseInt(row.get(0)), // role_id
                        Integer.parseInt(row.get(1)), // staff_id
                        LocalDateTime.parse(row.get(2)), // entry_date
                        Integer.parseInt(row.get(3)) // deduction_id
                );
            } catch (Exception e) {
                System.out.println("Error occurred in Role_Detail_DeductionDAL.convertToRole_Detail_Deductions(): " + e.getMessage());
            }
            return new Role_Detail_Deduction();
        });
    }

    public int addRole_Detail_Deduction(Role_Detail_Deduction role_detail_deduction) {
        try {
            return create(role_detail_deduction.getRole_id(),
                    role_detail_deduction.getStaff_id(),
                    role_detail_deduction.getEntry_date(),
                    role_detail_deduction.getDeduction_id()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_Detail_DeductionDAL.addRole_Detail_Deduction(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteRole_Detail_Deduction(String... conditions) {
        try {
            return delete(conditions);
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_Detail_DeductionDAL.deleteRole_Detail_Deduction(): " + e.getMessage());
        }
        return 0;
    }

    public List<Role_Detail_Deduction> searchRole_Detail_Deductions(String... conditions) {
        try {
            return convertToRole_Detail_Deductions(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_Detail_DeductionDAL.searchRole_Detail_Deductions(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
