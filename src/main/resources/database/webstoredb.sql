-- DROP DATABASE IF EXISTS webstoredb;
-- DROP TABLE IF EXISTS Customer;
-- DROP TABLE IF EXISTS Address;
-- DROP TABLE IF EXISTS Driver;
-- DROP TABLE IF EXISTS Location;
-- DROP TABLE IF EXISTS Supplier;
-- DROP TABLE IF EXISTS Orders;
-- DROP TABLE IF EXISTS SubOrder;
-- DROP TABLE IF EXISTS Product;
-- DROP TABLE IF EXISTS Supplier_SubOrder_Items;
-- DROP TABLE IF EXISTS OrderItems;

CREATE DATABASE IF NOT EXISTS webstoredb;
USE webstoredb;


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
    userPass varchar(512) NOT NULL
);

CREATE TABLE Driver (
	id smallint UNSIGNED auto_increment PRIMARY KEY,
    firstName varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    username varchar(50) NOT NULL,
    userpass varchar(512) NOT NULL
);

CREATE TABLE Location (
	id smallint UNSIGNED auto_increment PRIMARY KEY,
    driver_ID smallint UNSIGNED,
    FOREIGN KEY (driver_ID) REFERENCES Driver(id)
);

CREATE TABLE Supplier (
	id smallint UNSIGNED auto_increment PRIMARY KEY,
    storeName varchar(100) NOT NULL,
    address_ID int UNSIGNED,
    location_ID smallint UNSIGNED,
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (location_ID) REFERENCES Location(id)
);

CREATE TABLE Orders (
	id int UNSIGNED auto_increment PRIMARY KEY,
    orderDateTime DATETIME NOT NULL,
    orderStatus TINYINT UNSIGNED NOT NULL, -- this is a TINYINT to effectively be an enum, but maybe a varchar could be better?
    customer_ID int UNSIGNED NOT NULL,
    address_ID int UNSIGNED NOT NULL,
    driver_ID smallint UNSIGNED,
    FOREIGN KEY (customer_ID) REFERENCES Customer(id),
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (driver_ID) REFERENCES Driver(id)
);


CREATE TABLE SubOrder (
	id int UNSIGNED auto_increment PRIMARY KEY,
    status TINYINT UNSIGNED NOT NULL,
    order_ID int UNSIGNED NOT NULL,
    supplier_ID smallint UNSIGNED NOT NULL,
    FOREIGN KEY (order_ID) REFERENCES Orders(id),
    FOREIGN KEY (supplier_ID) REFERENCES Supplier(id)
);

CREATE TABLE Product (
	id int UNSIGNED auto_increment PRIMARY KEY,
    name varchar(50) NOT NULL,
    description varchar(250) NOT NULL,
    image varchar(250),
    price decimal(10,2) NOT NULL, -- UNSIGNED to avoid having negative values
    stock int UNSIGNED,
    category varchar(30),
    supplier_ID smallint UNSIGNED,
    FOREIGN KEY (supplier_ID) REFERENCES Supplier(id)
);

CREATE TABLE Supplier_SubOrder_Items (
	id int UNSIGNED auto_increment PRIMARY KEY,
    subOrder_ID int UNSIGNED,
    product_ID int UNSIGNED,
    quantity tinyint UNSIGNED	-- 255 is probably enough for any 1 item in an order
);

CREATE TABLE OrderItems (
	id int UNSIGNED auto_increment PRIMARY KEY,
    order_ID int UNSIGNED NOT NULL,
    product_ID int UNSIGNED NOT NULL,
    quantity tinyint UNSIGNED NOT NULL,	-- 255 is probably enough for any 1 item in an order
    unitPrice decimal NOT NULL
);

select * from Customer;