package com.coffee.BLL;

import com.coffee.DAL.Role_Detail_BonusDAL;
import com.coffee.DTO.Role_Detail_Bonus;
import com.coffee.utils.VNString;
import javafx.util.Pair;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Role_Detail_BonusBLL extends Manager<Role_Detail_Bonus> {
    private Role_Detail_BonusDAL role_Detail_BonusDAL;

    public Role_Detail_BonusBLL() {
        role_Detail_BonusDAL = new Role_Detail_BonusDAL();
    }

    public Role_Detail_BonusDAL getRole_Detail_BonusDAL() {
        return role_Detail_BonusDAL;
    }

    public void setRole_Detail_BonusDAL(Role_Detail_BonusDAL role_Detail_BonusDAL) {
        this.role_Detail_BonusDAL = role_Detail_BonusDAL;
    }

    public Object[][] getData() {
        return getData(role_Detail_BonusDAL.searchRole_Detail_Bonuss());
    }

    public Pair<Boolean, String> addRole_Detail_Bonus(Role_Detail_Bonus role_Detail_Bonus) {
        if (role_Detail_BonusDAL.addRole_Detail_Bonus(role_Detail_Bonus) == 0)
            return new Pair<>(false, "Thêm phụ cấp không thành công.");

        return new Pair<>(true, "Thêm phụ cấp thành công.");
    }

    public Pair<Boolean, String> deleteRole_Detail_Bonus(Role_Detail_Bonus role_Detail_Bonus) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (role_Detail_BonusDAL.deleteRole_Detail_Bonus("role_id = " + role_Detail_Bonus.getRole_id() +
                "staff_id = " + role_Detail_Bonus.getStaff_id() +
                "entry_date = '" + role_Detail_Bonus.getEntry_date().format(myFormatObj) + "'" +
                "bonus_id = " + role_Detail_Bonus.getBonus_id()) == 0)
            return new Pair<>(false, "Xoá phụ cấp không thành công.");

        return new Pair<>(true, "Xoá phụ cấp thành công.");
    }

    public List<Role_Detail_Bonus> searchRole_Detail_Bonuss(String... conditions) {
        return role_Detail_BonusDAL.searchRole_Detail_Bonuss(conditions);
    }

    public List<Role_Detail_Bonus> findRole_Detail_Bonuss(String key, String value) {
        List<Role_Detail_Bonus> list = new ArrayList<>();
        List<Role_Detail_Bonus> role_Detail_BonusList = role_Detail_BonusDAL.searchRole_Detail_Bonuss();
        for (Role_Detail_Bonus role_Detail_Bonus : role_Detail_BonusList) {
            if (getValueByKey(role_Detail_Bonus, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(role_Detail_Bonus);
            }
        }
        return list;
    }

    public List<Role_Detail_Bonus> findRole_Detail_BonussBy(Map<String, Object> conditions) {
        List<Role_Detail_Bonus> role_Detail_Bonuss = role_Detail_BonusDAL.searchRole_Detail_Bonuss();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            role_Detail_Bonuss = findObjectsBy(entry.getKey(), entry.getValue(), role_Detail_Bonuss);
        return role_Detail_Bonuss;
    }

    @Override
    public Object getValueByKey(Role_Detail_Bonus role_Detail_Bonus, String key) {
        return switch (key) {
            case "role_id" -> role_Detail_Bonus.getRole_id();
            case "staff_id" -> role_Detail_Bonus.getStaff_id();
            case "entry_date" -> role_Detail_Bonus.getEntry_date();
            case "bonus_id" -> role_Detail_Bonus.getBonus_id();
            default -> null;
        };
    }

    public static void main(String[] args) {
        Role_Detail_BonusBLL role_Detail_BonusBLL = new Role_Detail_BonusBLL();
//        Role_Detail_Bonus role_Detail_Bonus = new Role_Detail_Bonus(15, "abc");
//        role_Detail_BonusBLL.addRole_Detail_Bonus(role_Detail_Bonus);
//        role_Detail_Bonus.setName("xyz");
//        role_Detail_BonusBLL.updateRole_Detail_Bonus(role_Detail_Bonus);
//        role_Detail_BonusBLL.deleteRole_Detail_Bonus(role_Detail_Bonus);

        System.out.println(role_Detail_BonusBLL.searchRole_Detail_Bonuss());
    }
}
