-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-06-12 00:23:48.674

-- tables
-- Table: Address
CREATE TABLE Address (
    Id int  NOT NULL AUTO_INCREMENT,
    location varchar(100)  NOT NULL,
    postalCode varchar(35)  NOT NULL,
    CONSTRAINT Address_pk PRIMARY KEY (Id)
);

-- Table: Customer
CREATE TABLE Customer (
    Id varchar(100)  NOT NULL,
    name varchar(35)  NOT NULL,
    phone_number varchar(35)  NOT NULL,
    email varchar(35)  NULL,
    CONSTRAINT Customer_pk PRIMARY KEY (Id)
);

-- Table: Customer_Address
CREATE TABLE Customer_Address (
    Customer_Id varchar(100)  NOT NULL,
    Address_Id int  NOT NULL,
    address_type varchar(35)  NOT NULL,
    CONSTRAINT Customer_Address_pk PRIMARY KEY (Customer_Id,Address_Id)
);

-- Table: Customer_Order
CREATE TABLE Customer_Order (
    Customer_Id varchar(100)  NOT NULL,
    Order_Id varchar(100)  NOT NULL,
    CONSTRAINT Customer_Order_pk PRIMARY KEY (Customer_Id,Order_Id)
);

-- Table: Manager
CREATE TABLE Manager (
    secret varchar(100)  NOT NULL,
    information varchar(250)  NOT NULL,
    CONSTRAINT Manager_pk PRIMARY KEY (secret)
);

-- Table: Order
CREATE TABLE `Order` (
    Id varchar(100)  NOT NULL,
    order_date date  NOT NULL,
    status varchar(35)  NOT NULL,
    Payment_delivery_Id varchar(100)  NOT NULL,
    CONSTRAINT Order_pk PRIMARY KEY (Id)
);

-- Table: Order_Products
CREATE TABLE Order_Products (
    Order_Id varchar(100)  NOT NULL,
    Product_Id int  NOT NULL,
    CONSTRAINT Order_Products_pk PRIMARY KEY (Order_Id,Product_Id)
);

-- Table: Payment_delivery
CREATE TABLE Payment_delivery (
    Id varchar(100)  NOT NULL,
    card_number varchar(35)  NOT NULL,
    shipping_type varchar(35)  NOT NULL,
    service_fee int  NOT NULL,
    cvv int  NOT NULL,
    expiration_date date  NOT NULL,
    CONSTRAINT Payment_delivery_pk PRIMARY KEY (Id)
);

-- Table: Product
CREATE TABLE Product (
    Id int  NOT NULL AUTO_INCREMENT,
    name varchar(35)  NOT NULL,
    information varchar(100)  NOT NULL,
    price double(35,2)  NOT NULL,
    manufacturer varchar(35)  NULL,
    expiration_date datetime  NULL,
    fertilizer_type varchar(35)  NULL,
    germination varchar(35)  NULL,
    cereal_type varchar(35)  NULL,
    foodder bool  NULL,
    vegfruit_type varchar(35)  NULL,
    chem_type varchar(35)  NULL,
    disease_res bool  NULL,
    vehcicle_model varchar(35)  NULL,
    machine_data varchar(35)  NULL,
    processing_percentage int  NULL,
    CONSTRAINT Product_pk PRIMARY KEY (Id)
);

-- Table: Shop
CREATE TABLE Shop (
    Id int  NOT NULL AUTO_INCREMENT,
    name varchar(35)  NOT NULL,
    CONSTRAINT Shop_pk PRIMARY KEY (Id)
);

-- Table: Shop_Customer
CREATE TABLE Shop_Customer (
    Shop_Id int  NOT NULL,
    Customer_Id varchar(100)  NOT NULL,
    customer_status varchar(35)  NOT NULL,
    CONSTRAINT Shop_Customer_pk PRIMARY KEY (Shop_Id,Customer_Id)
);

-- Table: Shop_Manager
CREATE TABLE Shop_Manager (
    Manager_secret varchar(100)  NOT NULL,
    Shop_Id int  NOT NULL,
    CONSTRAINT Shop_Manager_pk PRIMARY KEY (Manager_secret,Shop_Id)
);

-- Table: Shop_Product
CREATE TABLE Shop_Product (
    Product_Id int  NOT NULL,
    Shop_Id int  NOT NULL,
    CONSTRAINT Shop_Product_pk PRIMARY KEY (Product_Id,Shop_Id)
);

-- foreign keys
-- Reference: Address_Customer (table: Customer_Address)
ALTER TABLE Customer_Address ADD CONSTRAINT Address_Customer FOREIGN KEY Address_Customer (Address_Id)
    REFERENCES Address (Id);

-- Reference: Customer_Address (table: Customer_Address)
ALTER TABLE Customer_Address ADD CONSTRAINT Customer_Address FOREIGN KEY Customer_Address (Customer_Id)
    REFERENCES Customer (Id);

-- Reference: Customer_Order (table: Customer_Order)
ALTER TABLE Customer_Order ADD CONSTRAINT Customer_Order FOREIGN KEY Customer_Order (Customer_Id)
    REFERENCES Customer (Id);

-- Reference: Customer_Shop (table: Shop_Customer)
ALTER TABLE Shop_Customer ADD CONSTRAINT Customer_Shop FOREIGN KEY Customer_Shop (Customer_Id)
    REFERENCES Customer (Id);

-- Reference: Order_Customer (table: Customer_Order)
ALTER TABLE Customer_Order ADD CONSTRAINT Order_Customer FOREIGN KEY Order_Customer (Order_Id)
    REFERENCES `Order` (Id);

-- Reference: Order_Payment_delivery (table: Order)
ALTER TABLE `Order` ADD CONSTRAINT Order_Payment_delivery FOREIGN KEY Order_Payment_delivery (Payment_delivery_Id)
    REFERENCES Payment_delivery (Id);

-- Reference: Order_Products_Order (table: Order_Products)
ALTER TABLE Order_Products ADD CONSTRAINT Order_Products_Order FOREIGN KEY Order_Products_Order (Order_Id)
    REFERENCES `Order` (Id);

-- Reference: Order_Products_Product (table: Order_Products)
ALTER TABLE Order_Products ADD CONSTRAINT Order_Products_Product FOREIGN KEY Order_Products_Product (Product_Id)
    REFERENCES Product (Id);

-- Reference: Product_Shop (table: Shop_Product)
ALTER TABLE Shop_Product ADD CONSTRAINT Product_Shop FOREIGN KEY Product_Shop (Product_Id)
    REFERENCES Product (Id);

-- Reference: Shop_Customer (table: Shop_Customer)
ALTER TABLE Shop_Customer ADD CONSTRAINT Shop_Customer FOREIGN KEY Shop_Customer (Shop_Id)
    REFERENCES Shop (Id);

-- Reference: Shop_Manager_Manager (table: Shop_Manager)
ALTER TABLE Shop_Manager ADD CONSTRAINT Shop_Manager_Manager FOREIGN KEY Shop_Manager_Manager (Manager_secret)
    REFERENCES Manager (secret);

-- Reference: Shop_Manager_Shop (table: Shop_Manager)
ALTER TABLE Shop_Manager ADD CONSTRAINT Shop_Manager_Shop FOREIGN KEY Shop_Manager_Shop (Shop_Id)
    REFERENCES Shop (Id);

-- Reference: Shop_Product (table: Shop_Product)
ALTER TABLE Shop_Product ADD CONSTRAINT Shop_Product FOREIGN KEY Shop_Product (Shop_Id)
    REFERENCES Shop (Id);

-- End of file.

