package com.coffee.BLL;

import com.coffee.DAL.BonusDAL;
import com.coffee.DTO.Bonus;
import com.coffee.utils.VNString;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BonusBLL extends Manager<Bonus> {
    private BonusDAL bonusDAL;

    public BonusBLL() {
        bonusDAL = new BonusDAL();
    }

    public BonusDAL getBonusDAL() {
        return bonusDAL;
    }

    public void setBonusDAL(BonusDAL bonusDAL) {
        this.bonusDAL = bonusDAL;
    }

    public Object[][] getData() {
        return getData(bonusDAL.searchBonuss());
    }

    public Pair<Boolean, String> addBonus(Bonus bonus) {
        Pair<Boolean, String> result;

        result = validateName(bonus.getName());
        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        result = exists(bonus);
        if (result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        if (bonusDAL.addBonus(bonus) == 0)
            return new Pair<>(false, "Thêm phụ cấp không thành công.");

        return new Pair<>(true, "Thêm phụ cấp thành công.");
    }

    public Pair<Boolean, String> updateBonus(Bonus bonus) {
        Pair<Boolean, String> result;

        result = validateName(bonus.getName());
        if (!result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        if (bonusDAL.updateBonus(bonus) == 0)
            return new Pair<>(false, "Cập nhật phụ cấp không thành công.");

        return new Pair<>(true, "Cập nhật phụ cấp thành công.");
    }

    public List<Bonus> searchBonuss(String... conditions) {
        return bonusDAL.searchBonuss(conditions);
    }

    public List<Bonus> findBonuss(String key, String value) {
        List<Bonus> list = new ArrayList<>();
        List<Bonus> bonusList = bonusDAL.searchBonuss();
        for (Bonus bonus : bonusList) {
            if (getValueByKey(bonus, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(bonus);
            }
        }
        return list;
    }

    public List<Bonus> findBonussBy(Map<String, Object> conditions) {
        List<Bonus> bonuss = bonusDAL.searchBonuss();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            bonuss = findObjectsBy(entry.getKey(), entry.getValue(), bonuss);
        return bonuss;
    }

    public Pair<Boolean, String> exists(Bonus bonus) {
        List<Bonus> modules = findBonuss("name", bonus.getName());

        if (!modules.isEmpty()) {
            return new Pair<>(true, "Phụ cấp đã tồn tại.");
        }
        return new Pair<>(false, "");
    }

    private Pair<Boolean, String> validateName(String name) {
        if (name.isBlank())
            return new Pair<>(false, "Tên phụ cấp không được để trống.");
        if (VNString.containsSpecial(name))
            return new Pair<>(false, "Tên phụ cấp không được chứa ký tự đặc biệt.");
        if (VNString.containsNumber(name))
            return new Pair<>(false, "Tên phụ cấp không được chứa số.");
        return new Pair<>(true, name);
    }

    @Override
    public Object getValueByKey(Bonus bonus, String key) {
        return switch (key) {
            case "id" -> bonus.getId();
            case "name" -> bonus.getName();
            case "bonus_amount" -> bonus.getBonus_amount();
            case "bonus_type" -> bonus.getBonus_type();
            default -> null;
        };
    }

    public static void main(String[] args) {
        BonusBLL bonusBLL = new BonusBLL();
//        Bonus bonus = new Bonus(15, "abc");
//        bonusBLL.addBonus(bonus);
//        bonus.setName("xyz");
//        bonusBLL.updateBonus(bonus);
//        bonusBLL.deleteBonus(bonus);

        System.out.println(bonusBLL.searchBonuss());
    }
}
