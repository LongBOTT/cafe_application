package com.coffee.DAL;

import com.coffee.DTO.Recipe;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAL extends Manager{
    public RecipeDAL() {
        super("recipe",
                List.of("product_id",
                        "material_id",
                        "quantity"));
    }
    public List<Recipe> convertToRecipes(List<List<String>> data){
        return convert(data, row -> {
            try {
                return new Recipe(
                        Integer.parseInt(row.get(0)), // product_id
                        Integer.parseInt(row.get(1)), // material_id
                        Double.parseDouble(row.get(2)) //quantity
                );
            } catch (Exception e) {
                System.out.println("Error occurred in RecipeDAL.convertToRecipes(): " + e.getMessage());
            }
            return new Recipe();
        });
    }

    public int addRecipe(Recipe recipe) {
        try {
            return create(recipe.getProduct_id(),
                    recipe.getMaterial_id(),
                    recipe.getQuantity()
            );
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in RecipeDAL.addRecipe(): " + e.getMessage());
        }
        return 0;
    }
    public int updateRecipe(Recipe recipe) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(recipe.getProduct_id());
            updateValues.add(recipe.getMaterial_id());
            updateValues.add(recipe.getQuantity());
            return update(updateValues, "product_id = " + recipe.getProduct_id(),
                    "material_id = " + recipe.getMaterial_id());
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Recipe.updateRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteRecipe(String... conditions) {
        try {
            return delete(conditions);
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in RecipeDAL.deleteRecipe(): " + e.getMessage());
        }
        return 0;
    }

    public List<Recipe> searchRecipes(String... conditions) {
        try {
            return convertToRecipes(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in RecipeDAL.searchRecipes(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
