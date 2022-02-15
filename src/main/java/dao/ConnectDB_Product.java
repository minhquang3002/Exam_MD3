package dao;

import model.Category;
import model.Product;
import connection.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectDB_Product implements IConnectionDB_Product{
    private static final String SELECT_ALL_PRODUCT = "select id_product, name_product, price, quantity, color, description, name_category from product \n" +
            "join category on product.id_category = category.id_category;";
    private static final String SELECT_ALL_CATEGORY = "select * from category";
    private static final String SELECT_PRODUCT_BY_ID = "select id_product, name_product, price, quantity, color, description, name_category from product\n" +
            "join category on product.id_category = category.id_category\n" +
            "where id_product = ?;";
    private static final String INSERT_PRODUCT = "insert into product (name_product, price, quantity, color, description, id_category) value (?,?,?,?,?,?);";
    private static final String UPDATE_PRODUCT = "update product set name_product = ?, price = ?, quantity = ?, color = ?, description = ?, id_category = ? where id_product = ?;";
    private static final String DELETE_PRODUCT = "delete from product where id_product = ?";

    private final MyConnection myConnection = new MyConnection();

    @Override
    public ArrayList<Product> getAllProduct() {
        ArrayList<Product> products = new ArrayList<>();
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT);){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_product");
                String name = resultSet.getString("name_product");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                String description = resultSet.getString("description");
                String category = resultSet.getString("name_category");
                products.add(new Product(name, price, quantity, color, description, category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public ArrayList<Category> getAllCategory() {
        ArrayList<Category> categories = new ArrayList<>();
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORY);){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_category = resultSet.getInt("id_category");
                String name_category = resultSet.getString("name_category");
                categories.add(new Category(id_category, name_category));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Product getProduct(int id) {
        Product product = null;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name_product");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String color = resultSet.getString("color");
                String description = resultSet.getString("description");
                String category = resultSet.getString("name_category");
                product = new Product(id, name, price, quantity, color, description, category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public boolean createProduct(Product product, int id_category) {
        boolean rowInsert = false;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT)) {
            preparedStatement.setString(1, product.getName_product());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getColor());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, id_category);

            rowInsert = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInsert;
    }

    @Override
    public boolean updateProduct(Product product, int id_category) {
        boolean rowUpdated = false;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT)) {
            statement.setString(1, product.getName_product());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setString(4, product.getColor());
            statement.setString(5, product.getDescription());
            statement.setInt(6, id_category);
            statement.setInt(7, product.getId_product());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean deleteProduct(int id) {
        boolean rowDeleted = false;
        try (Connection connection = myConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT)) {
            statement.setInt(1, id);

            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
