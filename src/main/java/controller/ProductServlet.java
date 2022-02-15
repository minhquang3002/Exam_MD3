package controller;

import model.Category;
import model.Product;
import service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        action(request, response);
    }

    private void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "createGet":
                    createGet(request, response);
                    break;
                case "createPost":
                    createPost(request, response);
                    break;
                case "editGet":
                    editGet(request, response);
                    break;
                case "editPost":
                    editPost(request, response);
                    break;
                case "delete":
                    delete(request, response);
                    break;
                case "searchByName":
                    searchByName(request, response);
                    break;
                default:
                    displayAllProduct(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        ArrayList<Product> products = productService.getProducts();
        ArrayList<Product> productsOfSearch = new ArrayList<>();
        for (Product product : products) {
            productsOfSearch.add(product);
        }
        request.setAttribute("products", productsOfSearch);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("view.jsp");
        requestDispatcher.forward(request, response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id_product = Integer.parseInt(request.getParameter("id"));
        productService.deleteProduct(id_product);
        displayAllProduct(request, response);
    }

    private void editPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id_product = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int id_category = Integer.parseInt(request.getParameter("category"));
        Product product = new Product(id_product, name, price, quantity, color, description);
        boolean check = productService.editProduct(product, id_category);
        request.setAttribute("check", check);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit_product.jsp");
        requestDispatcher.forward(request, response);
    }

    private void editGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id_product = Integer.parseInt(request.getParameter("id"));
        Product productEdit = productService.getProduct(id_product);
        ArrayList<Category> categories = productService.getCategories();
        int id_category = -1;
        for (Category category : categories) {
            if (category.getName_category().equals(productEdit.getCategory())) {
                id_category = category.getId_category();
            }
        }
        request.setAttribute("id_category", id_category);
        request.setAttribute("productEdit", productEdit);
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit_product.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        int id_category = Integer.parseInt(request.getParameter("category"));
        Product product = new Product(name, price, quantity, color, description);
        boolean check = productService.createProduct(product, id_category);
        request.setAttribute("check", check);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("create_product.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Category> categories = productService.getCategories();
        request.setAttribute("categories", categories);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("create_product.jsp");
        requestDispatcher.forward(request, response);
    }

    private void displayAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Product> products = productService.getProducts();
        request.setAttribute("products", products);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("view.jsp");
        requestDispatcher.forward(request, response);
    }
}