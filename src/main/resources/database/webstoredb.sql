-- DROP DATABASE IF EXISTS webstoredb;
-- DROP TABLE IF EXISTS Customer;
-- DROP TABLE IF EXISTS Address;
-- DROP TABLE IF EXISTS Driver;
-- DROP TABLE IF EXISTS Location;
-- DROP TABLE IF EXISTS Supplier;
-- DROP TABLE IF EXISTS Orders;
-- DROP TABLE IF EXISTS SubOrder;
-- DROP TABLE IF EXISTS Product;
-- DROP TABLE IF EXISTS SubOrder_Items;
-- DROP TABLE IF EXISTS OrderItems;

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

-- SET FOREIGN_KEY_CHECKS=0;
-- drop table Customer;
CREATE TABLE Customer (
	id int UNSIGNED auto_increment PRIMARY KEY,
    firstName varchar(50) NOT NULL,
    surname varchar(50) NOT NULL,
    userName varchar(50) NOT NULL,
    userPass varchar(512) NOT NULL,
    address int UNSIGNED,
    location int UNSIGNED,
    FOREIGN KEY (address) REFERENCES Address(id),
    FOREIGN KEY (location) REFERENCES Location(id)
);


-- drop table Driver;
CREATE TABLE Driver (
	id int UNSIGNED auto_increment PRIMARY KEY,
    firstName varchar(50),
    surname varchar(50),
    userName varchar(50),
    userPass varchar(512)
);


-- SET FOREIGN_KEY_CHECKS=0;
-- drop table Location;
CREATE TABLE Location (
	id int UNSIGNED auto_increment PRIMARY KEY,
    locationName varchar(50),
    driver_ID int UNSIGNED,
    FOREIGN KEY (driver_ID) REFERENCES Driver(id)
    -- should there be a one to many relationship with the suppliers here?
);

CREATE TABLE Supplier (
	id int UNSIGNED auto_increment PRIMARY KEY,
    storeName varchar(100) NOT NULL,
    address_ID int UNSIGNED,
    location_ID int UNSIGNED,
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (location_ID) REFERENCES Location(id)
    -- should there be a one to many relationship with the products here?
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
    -- OrderItems int UNSIGNED,
    FOREIGN KEY (customer_ID) REFERENCES Customer(id),
    FOREIGN KEY (address_ID) REFERENCES Address(id),
    FOREIGN KEY (driver_ID) REFERENCES Driver(id),
    FOREIGN KEY (location_ID) REFERENCES Location(id)
    -- FOREIGN KEY (OrderItems_ID) REFERENCES OrderItems(id)
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

-- SET FOREIGN_KEY_CHECKS=0;
-- drop table Product;
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




CREATE TABLE CartItem (
	id int UNSIGNED auto_increment PRIMARY KEY,
    customer_ID int UNSIGNED,
    product_ID int UNSIGNED,
    quantity int,
    FOREIGN KEY (customer_ID) REFERENCES Customer(id),
    FOREIGN KEY (product_ID) REFERENCES Product(id)
);




-- INSERT INTO Customer(firstName, surname, userName, userPass, address)
-- VALUES ("Darren", "Lowe", "dlowe", "password", 1);

-- INSERT INTO Address(addressLine1, addressLine2, city, district, postcode, country)
-- VALUES ("41", "Wicker Row", "Greystones", "County Wicklow", "W2212345", "Ireland");


-- INSERT INTO Location(driver_ID)
-- VALUES(2);


-- INSERT INTO Supplier(storeName, location_ID, address_ID)
-- VALUES("Shoes R Us", 2, 1);


/*
	id int UNSIGNED auto_increment PRIMARY KEY,
    orderStatus TINYINT UNSIGNED NOT NULL, -- this is a TINYINT to effectively be an enum, but maybe a varchar could be better?
    customer_ID int UNSIGNED NOT NULL,
    address_ID int UNSIGNED NOT NULL,
    driver_ID smallint UNSIGNED,
    location_ID smallint UNSIGNED NOT NULL,
*/
-- use webstoredb;
select * from SubOrder where supplier_ID = 3;

select * from Driver;
select * from Location;
select * from Supplier;

select Location.id, Location.driver_ID, Driver.firstName, Driver.surname
from Location
inner join Driver ON Driver.id;
