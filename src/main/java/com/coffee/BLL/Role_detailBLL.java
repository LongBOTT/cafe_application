package com.coffee.BLL;

import com.coffee.DAL.Role_detailDAL;
import com.coffee.DTO.Role_detail;
import com.coffee.DTO.Role_detail;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Role_detailBLL extends Manager<Role_detail> {
    private Role_detailDAL role_detailDAL;

    public Role_detailBLL() {
        role_detailDAL = new Role_detailDAL();
    }

    public Role_detailDAL getRole_detailDAL() {
        return role_detailDAL;
    }

    public void setRole_detailDAL(Role_detailDAL role_detailDAL) {
        this.role_detailDAL = role_detailDAL;
    }

    public Object[][] getData() {
        return getData(role_detailDAL.searchRole_details());
    }

    public Pair<Boolean, String> addRole_detail(Role_detail role_detail) {
        Pair<Boolean, String> result;

        result = exists(role_detail);
        if (result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        if (role_detailDAL.addRole_detail(role_detail) == 0)
            return new Pair<>(false, "Thêm chi tiết chức vụ nhân viên không thành công.");

        return new Pair<>(true, "Thêm bảng chi tiết chức vụ nhân viên thành công.");
    }

    public Pair<Boolean, String> updateRole_detail(Role_detail role_detail) {
        if (role_detailDAL.updateRole_detail(role_detail) == 0)
            return new Pair<>(false, "Cập nhật chi tiết chức vụ nhân viên không thành công.");

        return new Pair<>(true, "Cập nhật chi tiết chức vụ nhân viên thành công.");
    }

    public List<Role_detail> searchRole_details(String... conditions) {
        return role_detailDAL.searchRole_details(conditions);
    }

    public List<Role_detail> findRole_details(String key, String value) {
        List<Role_detail> list = new ArrayList<>();
        List<Role_detail> role_detailList = role_detailDAL.searchRole_details();
        for (Role_detail role_detail : role_detailList) {
            if (getValueByKey(role_detail, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(role_detail);
            }
        }
        return list;
    }

    public List<Role_detail> findRole_detailsBy(Map<String, Object> conditions) {
        List<Role_detail> role_details = role_detailDAL.searchRole_details();
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            role_details = findObjectsBy(entry.getKey(), entry.getValue(), role_details);
        return role_details;
    }

    public Pair<Boolean, String> exists(Role_detail role_detail) {
        List<Role_detail> role_details = findRole_detailsBy(Map.of(
                "role_id", role_detail.getRole_id(),
                "staff_id", role_detail.getStaff_id(),
                "entry_date", role_detail.getEntry_date()
        ));

        if (!role_details.isEmpty()) {
            return new Pair<>(true, "Chi tiết chức vụ nhân viên nhân viên đã tồn tại.");
        }
        return new Pair<>(false, "");
    }

    @Override
    public Object getValueByKey(Role_detail role_detail, String key) {
        return switch (key) {
            case "role_id" -> role_detail.getRole_id();
            case "staff_id" -> role_detail.getStaff_id();
            case "entry_date" -> role_detail.getEntry_date();
            case "salary" -> role_detail.getSalary();
            case "type_salary" -> role_detail.getType_salary();
            default -> null;
        };
    }
}
