CREATE DATABASE if not exists webstoredb;
USE webstoredb;


CREATE TABLE if not exists users (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(500) NOT NULL
);