package service;

import dao.ConnectDB_Product;
import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    private final ConnectDB_Product connectDB_product = new ConnectDB_Product();

    public ArrayList<Product> getProducts() {
        return connectDB_product.getAllProduct();
    }

    public Product getProduct(int id) {
        return connectDB_product.getProduct(id);
    }

    public boolean createProduct(Product product, int id_category) {
        return connectDB_product.createProduct(product, id_category);
    }

    public boolean deleteProduct(int id) throws SQLException {
        return connectDB_product.deleteProduct(id);
    }

    public boolean editProduct(Product product, int id_category) throws SQLException {
        return connectDB_product.updateProduct(product, id_category);
    }

    public ArrayList<Category> getCategories() {
        return connectDB_product.getAllCategory();
    }
}
