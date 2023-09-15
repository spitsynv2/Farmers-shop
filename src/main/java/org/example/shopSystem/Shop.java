package org.example.shopSystem;

import org.example.products.Product;
import org.example.products.ProductFactory;
import org.example.products.seed.Seed;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Shop {
    private String name;
    private List<Product> productList;
    private Map<String,String> customers; //Customer,Status

    public Shop(){
        setName(getShopName());
        setProductList(new ProductFactory().getAllProducts(true,0));
        customers = loadAllCustomerIdsAndStatus();
        deleteExpiredProducts();
    }

    public String getName() {
        return name;
    }

    //registerCustomer
    public void registerCustomer(Customer customer){

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {

            String shopSql = "INSERT INTO Shop_Customer (Shop_Id, Customer_Id, customer_status) VALUES (?, ?, ?)";

            int shopId = 1; // Assuming I have only one shop

            PreparedStatement shopStatement = conn.prepareStatement(shopSql);
            shopStatement.setInt(1, shopId);
            shopStatement.setString(2, customer.getId());
            shopStatement.setString(3, "Registered");
            shopStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //I don't use this Use-Case
    public void blockCustomer(Customer customer){

        int shopId = 1; // Assuming I have only one shop

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {

            String updateSql = "UPDATE Shop_Customer SET customer_status = ? WHERE Shop_Id = ? AND Customer_Id = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateSql);
            updateStatement.setString(1, "BLocked");
            updateStatement.setInt(2, shopId);
            updateStatement.setString(3, customer.getId());
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Map<String, String> loadAllCustomerIdsAndStatus() {
        Map<String, String> customerIdsAndStatus = new HashMap<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {
            String sql = "SELECT Customer_Id, customer_status FROM Shop_Customer";
            try (PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String customerId = resultSet.getString("Customer_Id");
                        String status = resultSet.getString("customer_status");
                        customerIdsAndStatus.put(customerId, status);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerIdsAndStatus;
    }

    public Map<String, String> getCustomers() {
        return customers;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    public void setCustomers(Map<String, String> customers) {
        this.customers = customers;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void deleteExpiredProducts() {
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product instanceof Seed seed) {
                Date expirationData = seed.getExpirationData();
                if (expirationData.compareTo(new Date()) < 0) {
                    iterator.remove();
                }
            }
        }
    }

    public String getShopName() {
        String shopName = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user");
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT name FROM Shop")) {

            if (resultSet.next()) {
                shopName = resultSet.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shopName;
    }

    public boolean isShopContainsProduct(int id) {

        for (Product product : productList) {
            if (product.getId() == id) {
                return true;
            }
        }
        return false;
    }

    //I don't use this Use-Case
    public void manageProduct(Product product){
        //TODO database operations, sending product, updating productList
        //Sample data via inserts.sql
    }

}
