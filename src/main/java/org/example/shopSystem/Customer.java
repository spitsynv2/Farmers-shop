package org.example.shopSystem;

import org.example.products.Product;

import java.sql.*;
import java.util.*;

public class Customer {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private List<Order> orderList;
    private List<Product> cart = new ArrayList<>();
    private Map<String, Address> addresses = new LinkedHashMap<>();

    public List<Product> getCart(){
        return this.cart;
    }

    public void addAddress(String type, Address address){
        if (!addresses.containsKey(type)){
            addresses.put(type,address);
        }else {
            System.out.println("You already have such address type");
        }

    }
    public void register(String name,String phoneNumber,String email){
        this.id = UUID.randomUUID().toString();
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.email=email;
        System.out.println(this.id +", its your unique login, remember it");
    }
    public void register(String name,String phoneNumber){
        this.id = UUID.randomUUID().toString();
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.email=null;
        System.out.println(this.id +", its your unique login, remember it");
    }


    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    // showOrdersHistory() = getOrderList()
    public List<Order> getOrderList() {
        orderList.sort(Comparator.comparing(Order::getOrderDate).reversed());
        return orderList;
    }
    public Map<String, Address> getAddresses() {
        return addresses;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //addProductToCart(Product) = setCart(List<Product>)
    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //register(Shop) = saveCustomer(Shop)
    public void saveCustomer(Shop shop) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {
            if (this.getEmail()!=null){
                String customerSql = "INSERT INTO Customer (Id,name, phone_number, email) VALUES (?, ?, ?, ?)";
                PreparedStatement customerStatement = conn.prepareStatement(customerSql);
                customerStatement.setString(1, this.getId());
                customerStatement.setString(2, this.getName());
                customerStatement.setString(3, this.getPhoneNumber());
                customerStatement.setString(4, this.getEmail());
                customerStatement.executeUpdate();
            }else {
                String customerSql = "INSERT INTO Customer (Id,name, phone_number) VALUES (?, ?, ?)";
                PreparedStatement customerStatement = conn.prepareStatement(customerSql);
                customerStatement.setString(1, this.getId());
                customerStatement.setString(2, this.getName());
                customerStatement.setString(3, this.getPhoneNumber());
                customerStatement.executeUpdate();
            }


            String addressSql = "INSERT INTO Customer_Address (Customer_Id, Address_Id,address_type) VALUES (?, ?, ?)";

            PreparedStatement addressStatement = conn.prepareStatement(addressSql);

            String customerId = this.getId();

            for (Map.Entry<String, Address> entry : this.getAddresses().entrySet()) {
                String key = entry.getKey();
                Address address = entry.getValue();

                saveAddress(conn, address);
                int addressId = getLastInsertedRowId(conn, "Address");

                addressStatement.setString(1, customerId);
                addressStatement.setInt(2, addressId);
                addressStatement.setString(3, key);
                addressStatement.executeUpdate();
            }

            shop.registerCustomer(this);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void saveAddress(Connection conn, Address address) throws SQLException {
        String sql = "INSERT INTO Address (location,postalCode) " +
                "VALUES (?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, address.getLocation());
            statement.setString(2, address.getPostalCode());

            statement.executeUpdate();
        }
    }

    private static int getLastInsertedRowId(Connection conn, String tableName) throws SQLException {
        int lastInsertedRowId = -1;
        String sql = "SELECT * FROM " + tableName + " ORDER BY Id DESC LIMIT 1";

        try (PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                lastInsertedRowId = resultSet.getInt("Id");
            }
        }

        return lastInsertedRowId;
    }

    //Login(Id) = retrieveCustomerFromDatabase(String)
    public static Customer retrieveCustomerFromDatabase(String customerId) {
        Customer customer = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {
            String customerSql = "SELECT * FROM Customer WHERE Id = ?";
            PreparedStatement customerStatement = conn.prepareStatement(customerSql);
            customerStatement.setString(1, customerId);

            try (ResultSet customerResult = customerStatement.executeQuery()) {
                if (customerResult.next()) {
                    String name = customerResult.getString("name");
                    String phoneNumber = customerResult.getString("phone_number");
                    String email = customerResult.getString("email");

                    customer = new Customer();
                    customer.setId(customerId);
                    customer.setName(name);
                    customer.setPhoneNumber(phoneNumber);
                    customer.setEmail(email);
                }
            }

            String addressSql = "SELECT * FROM Customer_Address " +
                    "JOIN Address ON Customer_Address.Address_Id = Address.Id " +
                    "WHERE Customer_Id = ?";
            PreparedStatement addressStatement = conn.prepareStatement(addressSql);
            addressStatement.setString(1, customerId);

            try (ResultSet addressResult = addressStatement.executeQuery()) {
                while (addressResult.next()) {
                    String addressType = addressResult.getString("address_type");
                    String location = addressResult.getString("location");
                    String postalCode = addressResult.getString("postalCode");

                    Address address = new Address(location, postalCode);
                    customer.addAddress(addressType, address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", orderList=" + orderList +
                ", cart=" + cart +
                ", addresses=" + addresses +
                '}';
    }
}
