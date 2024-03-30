package com.coffee.BLL;

import com.coffee.DAL.MaterialDAL;
import com.coffee.DTO.*;
import com.coffee.DTO.Material;
import com.coffee.DTO.Module;
import com.coffee.utils.VNString;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MaterialBLL extends Manager<Material> {
    private MaterialDAL materialDAL;

    public MaterialBLL() {
        materialDAL = new MaterialDAL();
    }

    public MaterialDAL getMaterialDAL() {
        return materialDAL;
    }

    public void setMaterialDAL(MaterialDAL materialDAL) {
        this.materialDAL = materialDAL;
    }

    public Object[][] getData() {
        return getData(materialDAL.searchMaterials("deleted = 0"));
    }

    public Pair<Boolean, String> addMaterial(Material material) {
        Pair<Boolean, String> result;

        result = exists(material);
        if (result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        if (materialDAL.addMaterial(material) == 0)
            return new Pair<>(false, "Thêm nguyên liệu không thành công.");

        return new Pair<>(true, "Thêm nguyên liệu thành công.");
    }

    public Pair<Boolean, String> updateMaterial(Material material) {

        if (materialDAL.updateMaterial(material) == 0)
            return new Pair<>(false, "Cập nhật nguyên liệu không thành công.");

        return new Pair<>(true, "Cập nhật nguyên liệu thành công.");
    }

    public Pair<Boolean, String> deleteMaterial(Material material) {
        Pair<Boolean, String> result;

        result = checkMaterial(material);
        if (result.getKey()) {
            return new Pair<>(false, result.getValue());
        }

        if (materialDAL.deleteMaterial("id = " + material.getId()) == 0)
            return new Pair<>(false, "Xoá nguyên liệu không thành công.");

        return new Pair<>(true, "Xoá nguyên liệu thành công.");
    }

    public List<Material> searchMaterials(String... conditions) {
        return materialDAL.searchMaterials(conditions);
    }

    public List<Material> findMaterials(String key, String value) {
        List<Material> list = new ArrayList<>();
        List<Material> materialList = materialDAL.searchMaterials("deleted = 0");
        for (Material material : materialList) {
            if (getValueByKey(material, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(material);
            }
        }
        return list;
    }

    public List<Material> findMaterialsBy(Map<String, Object> conditions) {
        List<Material> materials = materialDAL.searchMaterials("deleted = 0");
        ;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            materials = findObjectsBy(entry.getKey(), entry.getValue(), materials);
        return materials;
    }

    private static Pair<Boolean, String> validateName(String name) {
        if (name.isBlank())
            return new Pair<>(false, "Tên nguyên liệu được để trống.");
        if (VNString.containsSpecial(name))
            return new Pair<>(false, "Tên nguyên liêu không được chứa ký tự đặc biệt.");
        if (VNString.containsNumber(name))
            return new Pair<>(false, "Tên nguyên liệu không được chứa số.");
        return new Pair<>(true, name);
    }

    public Pair<Boolean, String> exists(Material newMaterial) {
        List<Material> materials = materialDAL.searchMaterials("name = '" + newMaterial.getName() + "'");
        if (!materials.isEmpty()) {
            return new Pair<>(true, "Nguyên liệu đã tồn tại.");
        }

        return new Pair<>(false, "");
    }

    public Pair<Boolean, String> checkMaterial(Material material) {
        RecipeBLL recipeBLL = new RecipeBLL();
        List<Recipe> recipes = recipeBLL.findRecipesBy(Map.of(
                "material_id", material.getId()
        ));

        if (!recipes.isEmpty()) {
            return new Pair<>(true, "Nguyên liệu đã tồn tại trong công thức.");
        }
        return new Pair<>(false, "");
    }

    @Override
    public Object getValueByKey(Material material, String key) {
        return switch (key) {
            case "id" -> material.getId();
            case "name" -> material.getName();
            case "remain" -> material.getRemain();
            case "unit" -> material.getUnit();
            default -> null;
        };
    }

    public static void main(String[] args) {
//        MaterialBLL materialBLL = new MaterialBLL();
//        Material material = new Material(materialBLL.getAutoID(materialBLL.searchMaterials()), "xzy", 1, 1, "d", false);

//        System.out.println(materialBLL.addMaterial(material).getValue());

//        material.setId(2);

//        materialBLL.updateMaterial(material);
//        materialBLL.deleteMaterial(material);
    }
}
