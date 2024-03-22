package com.coffee.DAL;

import com.coffee.DTO.Bonus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BonusDAL extends Manager {
    public BonusDAL() {
        super("bonus",
                List.of("id",
                        "name",
                        "bonus_amount",
                        "bonus_type"));
    }

    public List<Bonus> convertToBonuss(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Bonus(
                        Integer.parseInt(row.get(0)), // id
                        row.get(1), // name
                        Double.parseDouble(row.get(2)), // bonus_amount
                        Integer.parseInt(row.get(3)) // bonus_type
                );
            } catch (Exception e) {
                System.out.println("Error occurred in BonusDAL.convertToBonuss(): " + e.getMessage());
            }
            return new Bonus();
        });
    }

    public int addBonus(Bonus bonus) {
        try {
            return create(bonus.getId(),
                    bonus.getName(),
                    bonus.getBonus_amount(),
                    bonus.getBonus_type()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in BonusDAL.addBonus(): " + e.getMessage());
        }
        return 0;
    }

    public int updateBonus(Bonus bonus) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(bonus.getId());
            updateValues.add(bonus.getName());
            updateValues.add(bonus.getBonus_amount());
            updateValues.add(bonus.getBonus_type());
            return update(updateValues, "id = " + bonus.getId());
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Bonus.updateBonus(): " + e.getMessage());
        }
        return 0;
    }

    public List<Bonus> searchBonuss(String... conditions) {
        try {
            return convertToBonuss(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in BonusDAL.searchBonuss(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
