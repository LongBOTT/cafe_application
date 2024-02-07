package com.coffee.DAL;

import com.coffee.DTO.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAL extends Manager{
    public ProductDAL() {
        super("product",
                List.of("id",
                        "name",
                        "category",
                        "price",
                        "unit",
                        "image",
                        "deleted"));
    }
    public List<Product> convertToProducts(List<List<String>> data){
        return convert(data, row -> {
            try {
                return new Product(
                        Integer.parseInt(row.get(0)), // id
                        row.get(1), // name
                        row.get(2), // category
                        Double.parseDouble(row.get(3)), //price
                        row.get(4), // unit
                        row.get(5),//image
                        Boolean.parseBoolean(row.get(6)) //deleted
                );
            } catch (Exception e) {
                System.out.println("Error occurred in ProductDAL.convertToProducts(): " + e.getMessage());
            }
            return new Product();
        });
    }

    public int addProduct(Product product) {
        try {
            return create(product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getUnit(),
                    product.getImage(),
                    false
            ); // product khi tạo mặc định deleted = 0
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in ProductDAL.addProduct(): " + e.getMessage());
        }
        return 0;
    }
    public int updateProduct(Product product) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(product.getId());
            updateValues.add(product.getName());
            updateValues.add(product.getCategory());
            updateValues.add(product.getPrice());
            updateValues.add(product.getUnit());
            updateValues.add(product.getImage());
            updateValues.add(product.isDeleted());
            return update(updateValues, "id = " + product.getId());
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in Product.updateProduct(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteProduct(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
            return update(updateValues, conditions);
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in ProductDAL.deleteProduct(): " + e.getMessage());
        }
        return 0;
    }

    public List<Product> searchProducts(String... conditions) {
        try {
            return convertToProducts(read(conditions));
        } catch (SQLException | IOException e) {
            System.out.println("Error occurred in ProductDAL.searchProducts(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
