package com.coffee.DAL;

import com.coffee.DTO.Material;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAL extends Manager {
    public MaterialDAL() {
        super("material",
                List.of("id",
                        "name",
                        "remain",
                        "unit",
                        "deleted"));
    }

    public List<Material> convertToMaterials(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Material(
                        Integer.parseInt(row.get(0)), // id
                        row.get(1), // name
                        Double.parseDouble(row.get(2)), // remain
                        row.get(3), // unit
                        Boolean.parseBoolean(row.get(4)) // deleted
                );
            } catch (Exception e) {
                System.out.println("Error occurred in MaterialDAL.convertToMaterials(): " + e.getMessage());
            }
            return new Material();
        });
    }

    public int addMaterial(Material material) {
        try {
            return create(material.getId(),
                    material.getName(),
                    material.getRemain(),
                    material.getUnit(),
                    false
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in MaterialDAL.addMaterial(): " + e.getMessage());
        }
        return 0;
    }

    public int updateMaterial(Material material) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(material.getId());
            updateValues.add(material.getName());
            updateValues.add(material.getRemain());
            updateValues.add(material.getUnit());
            updateValues.add(material.isDeleted());
            return update(updateValues, "id = " + material.getId());
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in MaterialDAL.updateMaterial(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteMaterial(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
            return update(updateValues, conditions);
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in MaterialDAL.deleteMaterial(): " + e.getMessage());
        }
        return 0;
    }

    public List<Material> searchMaterials(String... conditions) {
        try {
            return convertToMaterials(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in MaterialDAL.searchMaterials(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
