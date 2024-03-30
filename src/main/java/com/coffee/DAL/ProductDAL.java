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
                        "size",
                        "category",
                        "price",
                        "image",
                        "deleted"));
    }
    public List<Product> convertToProducts(List<List<String>> data){
        return convert(data, row -> {
            try {
                return new Product(
                        Integer.parseInt(row.get(0)), // id
                        row.get(1), // name
                        row.get(2), // size
                        row.get(3), // category
                        Double.parseDouble(row.get(4)), //price
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
                    product.getSize(),
                    product.getCategory(),
                    product.getPrice(),
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
            updateValues.add(product.getSize());
            updateValues.add(product.getCategory());
            updateValues.add(product.getPrice());
            updateValues.add(product.getImage());
            updateValues.add(product.isDeleted());
            return update(updateValues, "id = " + product.getId(), "size = '" + product.getSize() + "'");
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

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        try {
            List<List<String>> result = executeQuery("SELECT DISTINCT `category` FROM `product` ");
            for(List<String> category : result) {
                categories.add(category.get(0));
            }
            return categories;
        } catch (SQLException | IOException e) {
            return categories;
        }
    }

    public static void main(String[] args) {
       ProductDAL s = new ProductDAL();
        System.out.println(s.searchProducts());
    }
}
