package com.coffee.DAL;

import com.coffee.DTO.Role_Detail_Bonus;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Role_Detail_BonusDAL extends Manager {
    public Role_Detail_BonusDAL() {
        super("role_detail_bonus",
                List.of("role_id",
                        "staff_id",
                        "entry_date",
                        "bonus_id"));
    }

    public List<Role_Detail_Bonus> convertToRole_Detail_Bonuss(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Role_Detail_Bonus(
                        Integer.parseInt(row.get(0)), // role_id
                        Integer.parseInt(row.get(1)), // staff_id
                        LocalDateTime.parse(row.get(2)), // entry_date
                        Integer.parseInt(row.get(3)) // bonus_id
                );
            } catch (Exception e) {
                System.out.println("Error occurred in Role_Detail_BonusDAL.convertToRole_Detail_Bonuss(): " + e.getMessage());
            }
            return new Role_Detail_Bonus();
        });
    }

    public int addRole_Detail_Bonus(Role_Detail_Bonus role_detail_bonus) {
        try {
            return create(role_detail_bonus.getRole_id(),
                    role_detail_bonus.getStaff_id(),
                    role_detail_bonus.getEntry_date(),
                    role_detail_bonus.getBonus_id()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_Detail_BonusDAL.addRole_Detail_Bonus(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteRole_Detail_Bonus(String... conditions) {
        try {
            return delete(conditions);
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_Detail_BonusDAL.deleteRole_Detail_Bonus(): " + e.getMessage());
        }
        return 0;
    }
    
    public List<Role_Detail_Bonus> searchRole_Detail_Bonuss(String... conditions) {
        try {
            return convertToRole_Detail_Bonuss(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Role_Detail_BonusDAL.searchRole_Detail_Bonuss(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
