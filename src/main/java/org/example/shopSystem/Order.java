package org.example.shopSystem;

import org.example.products.Product;
import org.example.products.ProductFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Order {
    private String id;
    private Date orderDate;
    private List<Product> productList;
    private Customer customer;
    private String status;
    private PaymentDelivery paymentDelivery;

    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    public void changeStatus(String status) {
        if (!status.equals("Creating") && !status.equals("In progress") && !status.equals("Cancelled") && !status.equals("Completed")){
            System.out.println("This status is forbidden");
            return;
        }
        this.status = status;
    }
    public void processOrder(String cardNumber, String shippingType, int cvv, Long expiration){
            generateId();
            this.orderDate = new Date();
            this.changeStatus("In progress");
            this.productList = customer.getCart();
            this.paymentDelivery = new PaymentDelivery(cardNumber,shippingType,cvv,expiration);
            insertOrderIntoDatabase(this);
    }

    public String getPaymentCardNumber(){
        return paymentDelivery.getCardNumber();
    }
    public String getPaymentShippingType(){
        return paymentDelivery.getShippingType();
    }
    public int getPaymentServiceFee(){
        return PaymentDelivery.serviceFee;
    }
    public int getPaymentCvv(){
        return paymentDelivery.getCvv();
    }
    public Long getPaymentCardExpiration(){
        return paymentDelivery.getExpiration();
    }
    static class PaymentDelivery {
        private String id;
        private String cardNumber;
        private int cvv;
        private Long expiration;
        private String shippingType;
        private static int serviceFee = 5;

        PaymentDelivery(){}

        PaymentDelivery(String cardNumber, String shippingType, int cvv, Long expiration){
            generatePaymentId();
            this.cardNumber = cardNumber;
            this.shippingType = shippingType;
            this.cvv = cvv;
            this.expiration = expiration;
        }

        public String getCardNumber() {
            return cardNumber;
        }
        public static int getServiceFee() {
            return serviceFee;
        }
        public String getShippingType() {
            return shippingType;
        }
        public String getId() {
            return id;
        }
        public void generatePaymentId() {
            this.id = UUID.randomUUID().toString();
        }
        public Long getExpiration() {
            return expiration;
        }
        public int getCvv() {
            return cvv;
        }

        @Override
        public String toString() {
            return "PaymentDelivery{" +
                    "id='" + id + '\'' +
                    ", cardNumber=" + cardNumber +
                    ", shippingType='" + shippingType + '\'' +
                    '}';
        }
    }

    public void insertPaymentDeliveryIntoDatabase(PaymentDelivery paymentDelivery) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {
            String sql = "INSERT INTO Payment_delivery (Id,card_number,shipping_type,cvv,expiration_date,service_fee) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, paymentDelivery.getId());
                statement.setString(2, paymentDelivery.getCardNumber());
                statement.setString(3, paymentDelivery.getShippingType());
                statement.setInt(4, paymentDelivery.getCvv());
                statement.setDate(5, new java.sql.Date(paymentDelivery.getExpiration()));
                statement.setInt(6, PaymentDelivery.getServiceFee());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertOrderIntoDatabase(Order order) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {

            insertPaymentDeliveryIntoDatabase(order.getPaymentDelivery());

            String orderSql = "INSERT INTO `Order` (Id, order_date, status, Payment_delivery_Id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement orderStatement = conn.prepareStatement(orderSql)) {
                orderStatement.setString(1, order.getId());
                orderStatement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
                orderStatement.setString(3, order.getStatus());
                orderStatement.setString(4, order.getPaymentDelivery().getId());
                orderStatement.executeUpdate();
            }

            String customerOrderSql = "INSERT INTO Customer_Order (Customer_Id, Order_Id) VALUES (?, ?)";
            try (PreparedStatement customerOrderStatement = conn.prepareStatement(customerOrderSql)) {
                customerOrderStatement.setString(1, order.getCustomer().getId());
                customerOrderStatement.setString(2, order.getId());
                customerOrderStatement.executeUpdate();
            }

            String orderProductsSql = "INSERT INTO Order_Products (Order_Id, Product_Id) VALUES (?, ?)";
            try (PreparedStatement orderProductsStatement = conn.prepareStatement(orderProductsSql)) {
                String orderId = order.getId();
                List<Product> productList = order.getProductList();
                for (Product product : productList) {
                    orderProductsStatement.setString(1, orderId);
                    orderProductsStatement.setInt(2, product.getId());
                    orderProductsStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> loadOrdersByCustomerId(String customerId) {
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {

            String orderIdsSql = "SELECT Order_Id FROM Customer_Order WHERE Customer_Id = ?";
            try (PreparedStatement orderIdsStatement = conn.prepareStatement(orderIdsSql)) {
                orderIdsStatement.setString(1, customerId);
                try (ResultSet orderIdsResult = orderIdsStatement.executeQuery()) {
                    while (orderIdsResult.next()) {
                        String orderId = orderIdsResult.getString("Order_Id");
                        Order order = loadOrderFromDatabase(orderId);
                        order.setCustomer(Customer.retrieveCustomerFromDatabase(customerId));
                        if (order != null) {
                            orders.add(order);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static Order loadOrderFromDatabase(String orderId) {
        Order order = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {

            String orderSql = "SELECT * FROM `Order` WHERE Id = ?";
            try (PreparedStatement orderStatement = conn.prepareStatement(orderSql)) {
                orderStatement.setString(1, orderId);
                try (ResultSet orderResult = orderStatement.executeQuery()) {
                    if (orderResult.next()) {
                        order = new Order();
                        order.id = orderResult.getString("Id");
                        order.orderDate = orderResult.getDate("order_date");
                        order.status = orderResult.getString("status");
                        String paymentDeliveryId = orderResult.getString("Payment_delivery_Id");
                        order.paymentDelivery = loadPaymentDeliveryFromDatabase(paymentDeliveryId);
                    }
                }
            }

            String orderProductsSql = "SELECT * FROM Order_Products WHERE Order_Id = ?";
            try (PreparedStatement orderProductsStatement = conn.prepareStatement(orderProductsSql)) {
                orderProductsStatement.setString(1, orderId);
                try (ResultSet orderProductsResult = orderProductsStatement.executeQuery()) {
                    order.productList = new ArrayList<>();
                    while (orderProductsResult.next()) {
                        int productId = orderProductsResult.getInt("Product_Id");
                        Product product = loadProductFromDatabase(productId);
                        order.productList.add(product);
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    public static void updateOrderStatusInDatabase(Order order) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {
            String updateSql = "UPDATE `Order` SET status = ? WHERE Id = ?";
            try (PreparedStatement updateStatement = conn.prepareStatement(updateSql)) {
                updateStatement.setString(1, order.getStatus());
                updateStatement.setString(2, order.getId());
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static PaymentDelivery loadPaymentDeliveryFromDatabase(String paymentDeliveryId) {
        PaymentDelivery paymentDelivery = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmers_shop", "user", "user")) {
            String sql = "SELECT * FROM Payment_delivery WHERE Id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, paymentDeliveryId);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        paymentDelivery = new PaymentDelivery();
                        paymentDelivery.id = result.getString("Id");
                        paymentDelivery.cardNumber = result.getString("card_number");
                        paymentDelivery.shippingType = result.getString("shipping_type");
                        paymentDelivery.cvv = result.getInt("cvv");
                        paymentDelivery.expiration = result.getDate("expiration_date").getTime();


                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentDelivery;
    }

    private static Product loadProductFromDatabase(int productId) {
        ProductFactory factory = new ProductFactory();
        return factory.getAllProducts(false,productId).get(0);
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public PaymentDelivery getPaymentDelivery() {
        return paymentDelivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", orderDate=" + orderDate +
                ", productList=" + productList +
                ", customer=" + customer +
                ", status='" + status + '\'' +
                ", paymentDelivery=" + paymentDelivery +
                '}';
    }
}
