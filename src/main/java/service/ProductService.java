package service;

import dao.ConnectDB_Product;
import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    private final ConnectDB_Product productDAO = new ConnectDB_Product();

    public ArrayList<Product> getProducts() {
        return productDAO.getAllProduct();
    }

    public Product getProduct(int id) {
        return productDAO.getProduct(id);
    }

    public boolean createProduct(Product product, int id_category) {
        return productDAO.createProduct(product, id_category);
    }

    public boolean deleteProduct(int id) throws SQLException {
        return productDAO.deleteProduct(id);
    }

    public boolean editProduct(Product product, int id_category) throws SQLException {
        return productDAO.updateProduct(product, id_category);
    }

    public ArrayList<Category> getCategories() {
        return productDAO.getAllCategory();
    }
}