package org.example.shopSystem;

import org.example.products.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Manager {
    private String secretLogin;
    private String information;
    private Shop shop;
    private List<Order> shopOrders = new ArrayList<>();

    private Manager(String secretLogin,String information){
        this.secretLogin = secretLogin;
        this.information = information;
    }

    public static Manager getManager(String secretLogin) {
        Manager manager = null;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user");
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Manager WHERE secret = ?")) {

            preparedStatement.setString(1, secretLogin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String secret = resultSet.getString("secret");
                String information = resultSet.getString("information");

                manager = new Manager(secret,information);

                System.out.println("Secret: " + secret);
                System.out.println("Information: " + information);
            } else {
                System.out.println("Manager not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return manager;
    }

    public void setShop(Shop shop) {
        if (this.secretLogin != null){
            this.shop = shop;
            shop.getCustomers().keySet().forEach(x -> this.shopOrders.addAll(Order.loadOrdersByCustomerId(x)));
            System.out.println("Shop name: "+ shop.getName());
            System.out.println("Shop orders: ");
            shopOrders.forEach(System.out::println);
        }else {
            System.out.println("Manager not exists");
        }

    }

    public void sendProductToShop(Product product){
        //sendProductToShop after creating new product in the gui menu
        shop.manageProduct(product);
    }

    public void sendCommandToBlockCustomer(Customer customer){
        //sendCommandToBlockCustomer after selecting customer from list in gui and clicking on the "block" button
        shop.blockCustomer(customer);
    }

    public void handleOrder(Order order){
        // HandelOrder will be called in gui menu after selecting order from list
        order.changeStatus("Completed");
        Order.updateOrderStatusInDatabase(order);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "secretLogin='" + secretLogin + '\'' +
                ", information='" + information + '\'' +
                ", shop=" + shop +
                ", shopOrders=" + shopOrders +
                '}';
    }
}
