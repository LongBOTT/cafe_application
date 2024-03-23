package com.coffee.BLL;

import com.coffee.DAL.Role_Detail_DeductionDAL;
import com.coffee.DTO.Role_Detail_Deduction;
import javafx.util.Pair;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Role_Detail_DeductionBLL extends Manager<Role_Detail_Deduction> {
    private Role_Detail_DeductionDAL role_Detail_DeductionDAL;

    public Role_Detail_DeductionBLL() {
        role_Detail_DeductionDAL = new Role_Detail_DeductionDAL();
    }

    public Role_Detail_DeductionDAL getRole_Detail_DeductionDAL() {
        return role_Detail_DeductionDAL;
    }

    public void setRole_Detail_DeductionDAL(Role_Detail_DeductionDAL role_Detail_DeductionDAL) {
        this.role_Detail_DeductionDAL = role_Detail_DeductionDAL;
    }

    public Object[][] getData() {
        return getData(role_Detail_DeductionDAL.searchRole_Detail_Deductions());
    }

    public Pair<Boolean, String> addRole_Detail_Deduction(Role_Detail_Deduction role_Detail_Deduction) {
        if (role_Detail_DeductionDAL.addRole_Detail_Deduction(role_Detail_Deduction) == 0)
            return new Pair<>(false, "Thêm giảm trừ không thành công.");

        return new Pair<>(true, "Thêm giảm trừ thành công.");
    }

    public Pair<Boolean, String> deleteRole_Detail_Deduction(Role_Detail_Deduction role_Detail_Deduction) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (role_Detail_DeductionDAL.deleteRole_Detail_Deduction("role_id = " + role_Detail_Deduction.getRole_id(),
                "staff_id = " + role_Detail_Deduction.getStaff_id(),
                "entry_date = '" + role_Detail_Deduction.getEntry_date().format(myFormatObj) + "'",
                "deduction_id = " + role_Detail_Deduction.getDeduction_id()) == 0)
            return new Pair<>(false, "Xoá giảm trừ không thành công.");

        return new Pair<>(true, "Xoá giảm trừ thành công.");
    }

    public List<Role_Detail_Deduction> searchRole_Detail_Deductions(String... conditions) {
        return role_Detail_DeductionDAL.searchRole_Detail_Deductions(conditions);
    }

    public List<Role_Detail_Deduction> findRole_Detail_Deductions(String key, String value) {
        List<Role_Detail_Deduction> list = new ArrayList<>();
        List<Role_Detail_Deduction> role_Detail_DeductionList = role_Detail_DeductionDAL.searchRole_Detail_Deductions();
        for (Role_Detail_Deduction role_Detail_Deduction : role_Detail_DeductionList) {
            if (getValueByKey(role_Detail_Deduction, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(role_Detail_Deduction);
            }
        }
        return list;
    }

    public List<Role_Detail_Deduction> findRole_Detail_DeductionsBy(Map<String, Object> conditions) {
        List<Role_Detail_Deduction> role_Detail_Deductions = role_Detail_DeductionDAL.searchRole_Detail_Deductions();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            role_Detail_Deductions = findObjectsBy(entry.getKey(), entry.getValue(), role_Detail_Deductions);
        return role_Detail_Deductions;
    }

    @Override
    public Object getValueByKey(Role_Detail_Deduction role_Detail_Deduction, String key) {
        return switch (key) {
            case "role_id" -> role_Detail_Deduction.getRole_id();
            case "staff_id" -> role_Detail_Deduction.getStaff_id();
            case "entry_date" -> role_Detail_Deduction.getEntry_date();
            case "deduction_id" -> role_Detail_Deduction.getDeduction_id();
            default -> null;
        };
    }

    public static void main(String[] args) {
        Role_Detail_DeductionBLL role_Detail_DeductionBLL = new Role_Detail_DeductionBLL();
//        Role_Detail_Deduction role_Detail_Deduction = new Role_Detail_Deduction(15, "abc");
//        role_Detail_DeductionBLL.addRole_Detail_Deduction(role_Detail_Deduction);
//        role_Detail_Deduction.setName("xyz");
//        role_Detail_DeductionBLL.updateRole_Detail_Deduction(role_Detail_Deduction);
//        role_Detail_DeductionBLL.deleteRole_Detail_Deduction(role_Detail_Deduction);

        System.out.println(role_Detail_DeductionBLL.searchRole_Detail_Deductions());
    }
}
