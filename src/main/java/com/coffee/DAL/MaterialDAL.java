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
                        "min_remain",
                        "max_remain",
                        "unit",
                        "unit_price",
                        "deleted"));
    }

    public List<Material> convertToMaterials(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Material(
                        Integer.parseInt(row.get(0)), // id
                        row.get(1), // name
                        Double.parseDouble(row.get(2)), // remain
                        Double.parseDouble(row.get(3)), // remain
                        Double.parseDouble(row.get(4)), // remain
                        row.get(5), // unit
                        Double.parseDouble(row.get(6)), // unit_price
                        Boolean.parseBoolean(row.get(7)) // deleted
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
                    material.getMinRemain(),
                    material.getMaxRemain(),
                    material.getUnit(),
                    material.getUnit_price(),
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
            updateValues.add(material.getMinRemain());
            updateValues.add(material.getMaxRemain());
            updateValues.add(material.getUnit());
            updateValues.add(material.getUnit_price());
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
