package dao;

import model.Category;
import model.Product;

import java.util.ArrayList;

public interface IConnectionDB_Product {
    ArrayList<Product> getAllProduct();

    ArrayList<Category> getAllCategory();

    Product getProduct(int id);

    boolean createProduct(Product product, int id_category);

    boolean updateProduct(Product product, int id_category);

    boolean deleteProduct(int id);
}
