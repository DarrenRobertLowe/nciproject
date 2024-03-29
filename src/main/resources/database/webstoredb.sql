
/*
CREATE DATABASE IF NOT EXISTS webstoredb;
USE webstoredb;
-- SET GLOBAL read_only=0;

CREATE TABLE Address (
	id int UNSIGNED auto_increment PRIMARY KEY,
    addressLine1 varchar(250),
    addressLine2 varchar(250),
    city varchar(100),
    district varchar(100),
    postcode varchar(20),
    country varchar(100)
);

CREATE TABLE Customer (
	id int UNSIGNED auto_increment PRIMARY KEY,
    firstName varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    userName varchar(50) NOT NULL,
    userPass varchar(512) NOT NULL,
    address int UNSIGNED,
    location int UNSIGNED,
    userRole varchar(50),
    FOREIGN KEY (address) REFERENCES Address(id),
    FOREIGN KEY (location) REFERENCES Location(id)
);

CREATE TABLE Driver (
	id int UNSIGNED auto_increment PRIMARY KEY,
    firstName varchar(50),
    surname varchar(50),
    userName varchar(50),
    userPass varchar(512),
    address int UNSIGNED,
    FOREIGN KEY (address) REFERENCES Address(id)
);

CREATE TABLE Location (
	id int UNSIGNED auto_increment PRIMARY KEY,
    locationName varchar(50),
    driver_ID int UNSIGNED,
    FOREIGN KEY (driver_ID) REFERENCES Driver(id)
);

CREATE TABLE Supplier (
	id int UNSIGNED auto_increment PRIMARY KEY,
    storeName varchar(100) NOT NULL,
    address_ID int UNSIGNED,
    location_ID int UNSIGNED,
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (location_ID) REFERENCES Location(id)
);


-- SET FOREIGN_KEY_CHECKS=0;
-- drop table Orders;
CREATE TABLE Orders (
	id int UNSIGNED auto_increment PRIMARY KEY,
    orderStatus int UNSIGNED,
    customer_ID int UNSIGNED,
    address_ID int UNSIGNED,
    driver_ID int UNSIGNED,
    location_ID int UNSIGNED,
    date varchar(10),
    time varchar(8),
    FOREIGN KEY (customer_ID) REFERENCES Customer(id),
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (driver_ID) REFERENCES Driver(id),
    FOREIGN KEY (location_ID) REFERENCES Location(id)
);

-- SET FOREIGN_KEY_CHECKS=1;
-- drop table SubOrder;
CREATE TABLE SubOrder (
	id int UNSIGNED auto_increment PRIMARY KEY,
    orderStatus int UNSIGNED,
    order_ID int UNSIGNED,
    supplier_ID int UNSIGNED,
    FOREIGN KEY (order_ID) REFERENCES Orders(id),
    FOREIGN KEY (supplier_ID) REFERENCES Supplier(id)
);


CREATE TABLE Product (
	id int UNSIGNED auto_increment PRIMARY KEY,
    productName varchar(100),
    productDescription text,
    image varchar(2048),
    price decimal(10,2),
    stock int UNSIGNED,
    category varchar(30),
    supplier_ID int UNSIGNED,
    FOREIGN KEY (supplier_ID) REFERENCES Supplier(id)
);

-- use webstoredb;
-- SET FOREIGN_KEY_CHECKS=0;
-- drop table SubOrder_Items;
CREATE TABLE SubOrder_Items (
	id int UNSIGNED auto_increment PRIMARY KEY,
    subOrder_ID int UNSIGNED,
    product_ID int UNSIGNED,
    quantity int UNSIGNED,
    FOREIGN KEY (subOrder_ID) REFERENCES SubOrder(id),
    FOREIGN KEY (product_ID) REFERENCES Product(id)
);


-- use webstoredb;
-- drop table OrderItems;
CREATE TABLE OrderItems (
	id int UNSIGNED auto_increment PRIMARY KEY,
    order_ID int UNSIGNED,
    product_ID int UNSIGNED,
    quantity int UNSIGNED,
    unitPrice decimal
);


-- use webstoredb;
-- drop table CartItem;
CREATE TABLE CartItem (
	id int UNSIGNED auto_increment PRIMARY KEY,
    customer_ID int UNSIGNED,
    product_ID int UNSIGNED,
    quantity int,
    FOREIGN KEY (customer_ID) REFERENCES Customer(id),
    FOREIGN KEY (product_ID) REFERENCES Product(id)
);


CREATE TABLE SupplierOrders (
	supplier_ID int UNSIGNED,
    order_ID int UNSIGNED,
    PRIMARY KEY (supplier_ID, order_ID)
);


CREATE TABLE User (
	id int UNSIGNED auto_increment PRIMARY KEY,
    firstName varchar(50),
    surname varchar(50),
    userName varchar(50),
    userPass varchar(512),
    role varchar(50),
    customer int UNSIGNED,
    driver int UNSIGNED,
    supplier int UNSIGNED,
    FOREIGN KEY (customer) REFERENCES Customer(id),
    FOREIGN KEY (driver) REFERENCES Driver(id),
    FOREIGN KEY (supplier) REFERENCES Supplier(id)
);


CREATE TABLE Counties (
	id int UNSIGNED auto_increment PRIMARY KEY,
    county varchar(10),
    location int UNSIGNED,
    FOREIGN KEY (location) REFERENCES Location(id)
);

/*
CREATE TABLE LocationCounties (
	county int UNSIGNED,
    location int UNSIGNED,
    PRIMARY KEY (county, location)
);
*/


/* REFRESH TABLES - don't forget to re-create them after
SET FOREIGN_KEY_CHECKS = 0;
drop table Orders;
drop table SubOrder;
drop table SubOrder_Items;
drop table OrderItems;
drop table SupplierOrders;
drop table User;
drop table locations;
*/

/* THIS IS A FIX FOR STRANGENESS DURING ORDER PLACEMENT - DON'T FORGOT TO RECREATE THE TABLES
drop table Orders;
drop table SubOrder;
drop table SubOrder_Items;
drop table OrderItems;
drop table SupplierOrders;
*/

/*

update Driver
set address = 68
where id = 3;
*/


use webstoredb;
SET FOREIGN_KEY_CHECKS = 1;
select * from User;
select * from Driver;
select * from Location;
select * from Product;
select * from Supplier;
select * from SupplierOrders;
select * from Customer;
select * from Orders;
select * from SubOrder;
select * from SubOrder_Items;
select * from CartItem;
select * from Address;
select * from orderItems;
select * from Counties;
select * from User;
