-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-06-12 00:23:48.674

-- foreign keys
ALTER TABLE Customer_Address
    DROP FOREIGN KEY Address_Customer;

ALTER TABLE Customer_Address
    DROP FOREIGN KEY Customer_Address;

ALTER TABLE Customer_Order
    DROP FOREIGN KEY Customer_Order;

ALTER TABLE Shop_Customer
    DROP FOREIGN KEY Customer_Shop;

ALTER TABLE Customer_Order
    DROP FOREIGN KEY Order_Customer;

ALTER TABLE `Order`
    DROP FOREIGN KEY Order_Payment_delivery;

ALTER TABLE Order_Products
    DROP FOREIGN KEY Order_Products_Order;

ALTER TABLE Order_Products
    DROP FOREIGN KEY Order_Products_Product;

ALTER TABLE Shop_Product
    DROP FOREIGN KEY Product_Shop;

ALTER TABLE Shop_Customer
    DROP FOREIGN KEY Shop_Customer;

ALTER TABLE Shop_Manager
    DROP FOREIGN KEY Shop_Manager_Manager;

ALTER TABLE Shop_Manager
    DROP FOREIGN KEY Shop_Manager_Shop;

ALTER TABLE Shop_Product
    DROP FOREIGN KEY Shop_Product;

-- tables
DROP TABLE Address;

DROP TABLE Customer;

DROP TABLE Customer_Address;

DROP TABLE Customer_Order;

DROP TABLE Manager;

DROP TABLE `Order`;

DROP TABLE Order_Products;

DROP TABLE Payment_delivery;

DROP TABLE Product;

DROP TABLE Shop;

DROP TABLE Shop_Customer;

DROP TABLE Shop_Manager;

DROP TABLE Shop_Product;

-- End of file.

