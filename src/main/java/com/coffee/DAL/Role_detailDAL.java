package com.coffee.DAL;

import com.coffee.DTO.Role_detail;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Role_detailDAL extends Manager {
    public Role_detailDAL() {
        super("role_detail",
                List.of("role_id",
                        "staff_id",
                        "entry_date",
                        "salary",
                        "type_salary"));
    }

    public List<Role_detail> convertToRole_details(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Role_detail(
                        Integer.parseInt(row.get(0)), // role_id
                        Integer.parseInt(row.get(1)), // staff_id
                        Date.valueOf(row.get(2)), // entry_date
                        Double.parseDouble(row.get(3)), // salary
                        Integer.parseInt(row.get(4)) // type_salary
                );
            } catch (Exception e) {
                System.out.println("Error occurred in RoleDAL.convertToRole_detail(): " + e.getMessage());
            }
            return new Role_detail();
        });
    }

    public int addRole_detail(Role_detail role_detail) {
        try {
            return create(role_detail.getRole_id(),
                    role_detail.getStaff_id(),
                    role_detail.getEntry_date(),
                    role_detail.getSalary(),
                    role_detail.getType_salary()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_detailDAL.addRole_detail(): " + e.getMessage());
        }
        return 0;
    }

    public int updateRole_detail(Role_detail role_detail) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(role_detail.getRole_id());
            updateValues.add(role_detail.getStaff_id());
            updateValues.add(role_detail.getEntry_date());
            updateValues.add(role_detail.getSalary());
            updateValues.add(role_detail.getType_salary());
            return update(updateValues, "role_id = " + role_detail.getRole_id(), "staff_id = " + role_detail.getStaff_id(), "entry_date = '" + role_detail.getEntry_date() + "'");
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_detailDAL.updateRole_detail(): " + e.getMessage());
        }
        return 0;
    }

    public List<Role_detail> searchRole_details(String... conditions) {
        try {
            return convertToRole_details(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_detailDAL.searchRole_details(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
