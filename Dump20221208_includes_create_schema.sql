CREATE DATABASE  IF NOT EXISTS `webstoredb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `webstoredb`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: webstoredb
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `addressLine1` varchar(250) DEFAULT NULL,
  `addressLine2` varchar(250) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `postcode` varchar(20) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Redbrick House','Mountview','Charlesland','Wicklow','E123456','Ireland'),(2,'42','Arthur Dr.','Cork','Cork','C123456','Ireland'),(3,'17 Redwood Lane','Templeview','Bray','Wicklow','W334567','Ireland'),(4,'Apt 15, Chesterhouse','Merrion Square','Dublin','Dublin','D023456','Ireland'),(5,'123 Fake Street','Test Park','Teston','Cork','C123456','Ireland'),(6,'245 Fake Ave','Test Way','Fakely','Wexford','WX2345','Ireland'),(18,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(19,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(20,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(21,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(22,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(23,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(24,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(25,'21','Maple View','Arling','Wicklow','E12345','Ireland'),(26,'742','Evergreen Terrace','Springfield','Dublin','D010742','Ireland'),(27,'742','Evergreen Terrace','Springfield','Dublin','D010742','Ireland'),(28,'','','','','','Ireland'),(29,'','','','','','Ireland'),(30,'','','','','','Ireland'),(31,'','','','','','Ireland'),(32,'','','','','','Ireland'),(33,'','','','','','Ireland'),(34,'742','Evergreen Terrace','Springfield','Wicklow','D010742','Ireland'),(35,'1','street name','city name','county name','E1RC0D3','Ireland'),(36,'55','Merrypark','Greystones','Wicklow','E123344','Ireland'),(37,'42','Test Road','Testville','Cork','C102342','Ireland'),(38,'23','asdf','asdf','afaf','123234','Ireland'),(39,'21','asdf','asdf','afaf','E123456','Ireland'),(40,'1234','1234','1234','1234','E123456','Ireland'),(41,'1','street name','city name','county name','E1RC0D3','Ireland'),(42,'23','Albert Road','Galway','Co. Galway','E1RC0D3','Ireland'),(43,'12','Merrion Square','Dublin 2','Co. Dublin','D021234','Ireland'),(44,'24','Ormond Quay','Dublin 1','Co. Dublin','D012341','Ireland'),(45,'60','Abbey St','Dublin 1','Co. Dublin','D012222','Ireland'),(46,'44','Main Street','Dún Laoghaire','Co. Dublin','D163023','Ireland'),(47,'14','Lower Main Street','Lucan','Co. Dublin','K78 KO77','Ireland'),(48,'21','Maple View','Portumna','Galway','E12345','Ireland'),(49,'27 Adelaide Street','Monkstown','Cork','Co. Cork','E1RC0D3','Ireland'),(50,'15 ','Upperchurch','Thurles','Co. Tipperary','E1RC0D3','Ireland'),(51,'10','Knockpogue pk','Cork','Co. Cork','E1RC0D3','Ireland'),(52,'33','Main Street','Avoca','Co. Wicklow','E1RC0D3','Ireland'),(53,'17','Pound St.','Leixlip','Co. Kildare','E1RC0D3','Ireland'),(54,'22','Corofin','Ennis','Co. Clare','E1RC0D3','Ireland'),(55,'12','Redhill','Castlebar','Mayo','E1RC0D3','Ireland'),(56,'18','Bishop St.','Tuam','County Galway','E1RC0D3','Ireland'),(57,'35','Main St.','Edgeworthstown','County Longford','E1RC0D3','Ireland'),(58,'7','Togher Rd','Nenagh','County Tipperary','E1RC0D3','Ireland');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cartitem`
--

DROP TABLE IF EXISTS `cartitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cartitem` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `customer_ID` int unsigned DEFAULT NULL,
  `product_ID` int unsigned DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_ID` (`customer_ID`),
  KEY `product_ID` (`product_ID`),
  CONSTRAINT `cartitem_ibfk_1` FOREIGN KEY (`customer_ID`) REFERENCES `customer` (`id`),
  CONSTRAINT `cartitem_ibfk_2` FOREIGN KEY (`product_ID`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cartitem`
--

LOCK TABLES `cartitem` WRITE;
/*!40000 ALTER TABLE `cartitem` DISABLE KEYS */;
INSERT INTO `cartitem` VALUES (21,19,1,2),(22,19,1,2);
/*!40000 ALTER TABLE `cartitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `address` int unsigned DEFAULT NULL,
  `location` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `address` (`address`),
  KEY `location` (`location`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`address`) REFERENCES `address` (`id`),
  CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`location`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (13,'Homer','Simpson',34,2),(19,'Darren','Lowe',48,3);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `driver` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES (1,'Sebastian','Vettel'),(2,'Max','Verstappen'),(3,'Lewis','Hamilton'),(4,'Fernando','Alonso');
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `locationName` varchar(50) DEFAULT NULL,
  `driver_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `driver_ID` (`driver_ID`),
  CONSTRAINT `location_ibfk_1` FOREIGN KEY (`driver_ID`) REFERENCES `driver` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'SouthEast Ireland',1),(2,'Dublin',2),(3,'West Ireland',3);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitems`
--

DROP TABLE IF EXISTS `orderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitems` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `order_ID` int unsigned DEFAULT NULL,
  `product_ID` int unsigned DEFAULT NULL,
  `quantity` int unsigned DEFAULT NULL,
  `unitPrice` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9ndchiurrbm2jne4b6h8nnved` (`order_ID`),
  KEY `FKafqwx2av626sdqkajpytnf3fo` (`product_ID`),
  CONSTRAINT `FK9ndchiurrbm2jne4b6h8nnved` FOREIGN KEY (`order_ID`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKafqwx2av626sdqkajpytnf3fo` FOREIGN KEY (`product_ID`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitems`
--

LOCK TABLES `orderitems` WRITE;
/*!40000 ALTER TABLE `orderitems` DISABLE KEYS */;
INSERT INTO `orderitems` VALUES (1,1,1,2,350),(2,1,2,1,110),(3,1,3,4,69),(4,2,1,2,350),(5,2,2,1,110),(6,2,3,4,69),(7,3,1,2,350),(8,3,2,1,110),(9,3,3,4,69),(10,4,1,2,350),(11,4,2,1,110),(12,4,3,4,69),(13,5,1,2,350),(14,5,2,1,110),(15,5,3,4,69),(16,6,1,2,350),(17,6,2,1,110),(18,6,3,4,69),(19,7,1,2,350),(20,7,2,1,110),(21,7,3,4,69),(22,8,1,2,350),(23,8,2,1,110),(24,8,3,4,69),(25,9,1,2,350),(26,9,2,1,110),(27,9,3,4,69),(28,10,1,2,350),(29,10,2,1,110),(30,10,3,4,69),(31,11,1,2,350),(32,11,2,1,110),(33,11,3,4,69),(34,12,1,2,350),(35,12,2,1,110),(36,12,3,4,69),(37,13,1,2,350),(38,13,2,1,110),(39,13,3,4,69),(40,14,1,2,350),(41,14,2,1,110),(42,14,3,4,69),(43,15,1,2,350),(44,15,2,1,110),(45,15,3,4,69),(46,16,1,2,350),(47,16,2,1,110),(48,16,3,4,69),(49,17,1,2,350),(50,17,2,1,110),(51,17,3,4,69),(52,18,1,2,350),(53,18,2,1,110),(54,18,3,4,69),(55,19,1,2,350),(56,19,2,1,110),(57,19,3,4,69),(58,20,1,2,350),(59,20,2,1,110),(60,20,3,4,69),(61,21,1,2,350),(62,21,2,1,110),(63,21,3,4,69),(64,22,1,2,350),(65,22,2,1,110),(66,22,3,4,69),(67,23,1,1,350),(68,23,2,1,110),(69,23,3,4,69),(70,24,1,1,350),(71,24,2,1,110),(72,24,3,4,69),(73,25,1,6,350),(74,25,2,6,110),(75,25,3,6,69),(76,26,1,9,350),(77,26,1,2,350),(78,26,1,1,350),(79,26,1,12,350),(80,26,1,2,350),(81,26,1,4,350),(82,26,1,8,350),(83,27,1,9,350),(84,27,1,2,350),(85,27,1,1,350),(86,27,1,12,350),(87,27,1,2,350),(88,27,1,4,350),(89,27,1,8,350),(90,27,1,13,350),(91,28,1,9,350),(92,28,1,2,350),(93,28,1,1,350),(94,28,1,12,350),(95,28,1,2,350),(96,28,1,4,350),(97,28,1,8,350),(98,28,1,13,350),(99,29,1,3,350),(100,30,1,4,350),(101,31,1,4,350),(102,32,1,4,350),(103,33,1,4,350),(104,34,1,4,350);
/*!40000 ALTER TABLE `orderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `orderStatus` int unsigned DEFAULT NULL,
  `customer_ID` int unsigned DEFAULT NULL,
  `address_ID` int unsigned DEFAULT NULL,
  `driver_ID` int unsigned DEFAULT NULL,
  `location_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_ID` (`customer_ID`),
  KEY `address_ID` (`address_ID`),
  KEY `driver_ID` (`driver_ID`),
  KEY `location_ID` (`location_ID`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_ID`) REFERENCES `customer` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`address_ID`) REFERENCES `address` (`id`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`driver_ID`) REFERENCES `driver` (`id`),
  CONSTRAINT `orders_ibfk_4` FOREIGN KEY (`location_ID`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,0,NULL,NULL,NULL,NULL),(2,0,NULL,NULL,NULL,NULL),(3,0,NULL,NULL,NULL,NULL),(4,0,NULL,NULL,NULL,NULL),(5,0,NULL,NULL,NULL,NULL),(6,0,NULL,NULL,NULL,NULL),(7,0,NULL,NULL,NULL,NULL),(8,0,NULL,NULL,NULL,NULL),(9,0,NULL,NULL,NULL,NULL),(10,0,NULL,NULL,NULL,NULL),(11,0,NULL,NULL,NULL,NULL),(12,0,NULL,NULL,NULL,NULL),(13,0,NULL,NULL,NULL,NULL),(14,0,NULL,NULL,NULL,NULL),(15,0,NULL,NULL,NULL,NULL),(16,0,NULL,NULL,NULL,NULL),(17,0,NULL,NULL,NULL,NULL),(18,0,NULL,NULL,NULL,NULL),(19,4,1,1,1,1),(20,4,1,1,1,1),(21,4,1,1,1,1),(22,4,1,1,1,1),(23,1,1,1,1,1),(24,1,1,1,1,1),(25,1,1,1,1,1),(26,2,13,34,2,2),(27,4,13,34,2,2),(28,1,13,34,2,2),(29,1,13,34,2,2),(30,0,NULL,NULL,NULL,NULL),(31,1,13,34,2,2),(32,1,13,34,2,2),(33,1,13,34,2,2),(34,1,13,34,2,2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `productName` varchar(100) DEFAULT NULL,
  `productDescription` text,
  `image` varchar(2048) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `stock` int unsigned DEFAULT NULL,
  `category` varchar(30) DEFAULT NULL,
  `identifier` varchar(50) DEFAULT NULL,
  `supplier_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `supplier_ID` (`supplier_ID`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`supplier_ID`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Fansier Epiphany Gold Diamond Earrings','Very gold and very fancy.','accessories/andrew-hutchings-Asngw4A5_tM-unsplash.jpg',349.98,3,'Accessories','978-9-2629-5519-8',7),(2,'Adidas Redstorms','Size 10','shoes/revolt-164_6wVEHfI-unsplash.jpg',109.90,20,'Shoes','978-5-4956-5373-3',8),(3,'Asics Gel Impact','Size 9','shoes/omar-prestwich-jLEGurepDco-unsplash.jpg',69.20,45,'Shoes','978-0-2778-9656-8',8),(4,'TKMaxx Autumn Scarf','A red scarf for autumn','clothing/karen-cantu-q-uYF7pziBO8Y-unsplash',30.00,125,'Clothing','978-0-3915-0127-0',9),(5,'Maria Red Slacks','A test','clothing/malik-skydsgaard-3we24FcjVAk-unsplash',10.00,10,'Clothing','978-4-2538-6256-1',9),(6,'Empirical Gold Bracelet','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/albina-white-qILRFVJmsBc-unsplash-min.jpg',20.00,25,'Accessories','978-3-4495-6379-4',7),(7,'Streetwear White Cargo Pants','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/marek-mucha-s4AIGME2erI-unsplash.jpg',50.00,125,'Clothing','978-6-0853-0776-0',11),(8,'Streetwear White Long Sleeve Shirt','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/marek-mucha-s4AIGME2erI-unsplash.jpg',40.00,100,'Clothing','978-6-3810-5586-0',11),(9,'Coolguy Goldsphere Sunglasses','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/charlesdeluvio-1-nx1QR5dTE-unsplash-min.jpg',60.00,50,'Accessories','978-9-4522-2325-0',10),(10,'Smithson\'s Gold Bracelet G12-S','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/nati-melnychuk-oO0JAOJhquk-unsplash-min.jpg',62.00,50,'Accessories','978-7-0493-2856-2',10),(11,'Melodie Women\'s Brown Trenchcoat','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','coats/bundo-kim-AhBR8UGvsGU-unsplash.jpg',162.00,20,'Coats','978-6-5387-6058-6',12),(12,'Tobias Unisex Jacket Brown','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','coats/tobias-tullius-Fg15LdqpWrs-unsplash.jpg',88.00,20,'Coats','978-0-3359-0777-9',12),(13,'Mavison Women\'s Trench Brown','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','coats/bundo-kim-zkHv9pvrE9U-unsplash.jpg',155.00,20,'Coats','978-5-3433-6022-6',13),(14,'Mavison Men\'s Lined Coat Black','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','coats/joel-assuied-lA_72dfCtlc-unsplash.jpg',155.00,20,'Coats','978-5-0898-8308-9',13),(15,'Maloney\'s Women\'s Blue Jeans','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/matt-moloney-5NPId7L1_p4-unsplash.jpg',79.00,66,'Clothing','978-2-0081-3196-2',14),(16,'Maverick\'s Mens Trousers Brown','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/redd-f-jC7nVH_Sw8k-unsplash.jpg',59.00,43,'Clothing','978-5-0378-6874-8',14),(17,'Addidas Red Storms','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','shoes/revolt-164_6wVEHfI-unsplash.jpg',129.00,23,'Shoes','978-7-8525-7968-7',15),(18,'Nike Jets','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','shoes/andres-jasso-PqbL_mxmaUE-unsplash.jpg',109.00,36,'Shoes','978-8-8957-0645-0',15),(19,'Coolguy Jet Blacks','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/giorgio-trovato-K62u25Jk6vo-unsplash-min.jpg',80.00,14,'Accessories','978-7-3554-3537-4',16),(20,'Sabrianna Silver Diamond Bracelet','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/sabrianna-2z7MxnXQs3k-unsplash-min.jpg',280.00,3,'Accessories','978-4-7969-2519-8',16),(21,'Pablo Diablo Rounds Sunglasses','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/pablo-soriano-Hnp-cs9QVOc-unsplash-min.jpg',120.00,5,'Accessories','978-6-5677-3186-1',17),(22,'Maloney Women\'s Blue Jeans','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/matt-moloney-YeGao3uk8kI-unsplash.jpg',80.00,15,'Clothing','978-1-1592-6518-2',17),(23,'Walton\'s Men\'s Brown Business Coat','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','coats/serhiy-hipskyy-6KPUS5tUp3Y-unsplash.jpg',189.00,5,'Coats','978-1-8956-4671-9',18),(24,'Esquire Men\'s Gray Business Coat','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','coats/serhiy-hipskyy-8Ha9eepUoLw-unsplash.jpg',245.00,6,'Coats','978-2-2621-9912-8',18),(25,'Sprint Sportswear White Top','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/napat-saeng-pcvz4AJdllI-unsplash.jpg',89.00,6,'Clothing','978-1-0718-6190-5',19),(26,'Sprint Sportswear White Sweatpants','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/napat-saeng-pcvz4AJdllI-unsplash.jpg',89.00,8,'Clothing','978-4-2864-9310-7',19),(27,'Sprint Sportswear Cloud Runners Gray','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','shoes/martin-katler-Y4fKN-RlMV4-unsplash.jpg',90.00,14,'Shoes','978-2-0294-5695-2',20),(28,'Esquire Canvas Browns','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','shoes/mojtaba-fahiminia-t4g1gctAaKk-unsplash.jpg',125.00,8,'Shoes','978-8-9716-6053-9',20),(29,'Samar Gold Diamond Ring GDR-23','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/samar-ahmad - nKCbZlOHek-unsplash-min.jpg',225.00,4,'Accessories','978-0-5206-4348-2',21),(30,'Sebastian Unisex Sunglasses Brown','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','accessories/sebastian-coman-travel-dtOTQYmTEs0-unsplash-min.jpg',125.00,5,'Accessories','978-3-1063-4845-0',21),(31,'Nike Green Envy\'s','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','shoes/usama-akram-kP6knT7tjn4-unsplash.jpg',125.00,5,'Shoes','978-2-0243-5901-2',22),(32,'Esquire Royal White Men\'s Shirt','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','clothing/mahdi-bafande-npyWFYpHQ94-unsplash.jpg',205.00,5,'Clothing','978-4-1390-9090-3',22);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suborder`
--

DROP TABLE IF EXISTS `suborder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suborder` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `orderStatus` int unsigned DEFAULT NULL,
  `order_ID` int unsigned DEFAULT NULL,
  `supplier_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_ID` (`order_ID`),
  KEY `supplier_ID` (`supplier_ID`),
  CONSTRAINT `suborder_ibfk_1` FOREIGN KEY (`order_ID`) REFERENCES `orders` (`id`),
  CONSTRAINT `suborder_ibfk_2` FOREIGN KEY (`supplier_ID`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suborder`
--

LOCK TABLES `suborder` WRITE;
/*!40000 ALTER TABLE `suborder` DISABLE KEYS */;
INSERT INTO `suborder` VALUES (1,2,19,3),(2,2,19,1),(3,2,20,3),(4,2,20,1),(5,2,21,3),(6,2,21,1),(7,2,22,3),(8,2,22,1),(9,1,23,3),(10,2,23,1),(11,1,24,3),(12,2,24,1),(13,1,25,3),(14,2,25,1),(15,2,26,3),(16,2,27,3),(17,1,28,3),(18,1,29,3),(19,1,31,7),(20,1,32,7),(21,1,33,7),(22,2,34,7);
/*!40000 ALTER TABLE `suborder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suborder_items`
--

DROP TABLE IF EXISTS `suborder_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suborder_items` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `subOrder_ID` int unsigned DEFAULT NULL,
  `product_ID` int unsigned DEFAULT NULL,
  `quantity` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `subOrder_ID` (`subOrder_ID`),
  KEY `product_ID` (`product_ID`),
  CONSTRAINT `suborder_items_ibfk_1` FOREIGN KEY (`subOrder_ID`) REFERENCES `suborder` (`id`),
  CONSTRAINT `suborder_items_ibfk_2` FOREIGN KEY (`product_ID`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suborder_items`
--

LOCK TABLES `suborder_items` WRITE;
/*!40000 ALTER TABLE `suborder_items` DISABLE KEYS */;
INSERT INTO `suborder_items` VALUES (1,1,1,2),(2,2,2,1),(3,2,3,4),(4,3,1,2),(5,4,2,1),(6,4,3,4),(7,5,1,2),(8,6,2,1),(9,6,3,4),(10,7,1,2),(11,8,2,1),(12,8,3,4),(13,9,1,1),(14,10,2,1),(15,10,3,4),(16,11,1,1),(17,12,2,1),(18,12,3,4),(19,13,1,6),(20,14,2,6),(21,14,3,6),(22,15,1,9),(23,15,1,2),(24,15,1,1),(25,15,1,12),(26,15,1,2),(27,15,1,4),(28,15,1,8),(29,16,1,9),(30,16,1,2),(31,16,1,1),(32,16,1,12),(33,16,1,2),(34,16,1,4),(35,16,1,8),(36,16,1,13),(37,17,1,9),(38,17,1,2),(39,17,1,1),(40,17,1,12),(41,17,1,2),(42,17,1,4),(43,17,1,8),(44,17,1,13),(45,18,1,3),(46,19,1,4),(47,20,1,4),(48,21,1,4),(49,22,1,4);
/*!40000 ALTER TABLE `suborder_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `storeName` varchar(100) NOT NULL,
  `address_ID` int unsigned DEFAULT NULL,
  `location_ID` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `address_ID` (`address_ID`),
  KEY `location_ID` (`location_ID`),
  CONSTRAINT `supplier_ibfk_1` FOREIGN KEY (`address_ID`) REFERENCES `address` (`id`),
  CONSTRAINT `supplier_ibfk_2` FOREIGN KEY (`location_ID`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (7,'Rings & Things',42,2),(8,'Shoes R Us',43,2),(9,'Fab Fabriques',44,2),(10,'Excessorize',45,2),(11,'Sweet Threads',46,2),(12,'Coats-a-Plenty',47,2),(13,'Mavison\'s',49,1),(14,'Tailormade',50,1),(15,'Clark\'s',51,1),(16,'Rummages',52,1),(17,'Paraphernalia',53,1),(18,'Armoire',54,3),(19,'Coal\'s',55,3),(20,'Fresh Kicks',56,3),(21,'knick-knacks',57,3),(22,'Winston\'s',58,3);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplierorders`
--

DROP TABLE IF EXISTS `supplierorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplierorders` (
  `supplier_ID` int unsigned NOT NULL,
  `order_ID` int unsigned NOT NULL,
  PRIMARY KEY (`supplier_ID`,`order_ID`),
  KEY `FKiwvroavxmmecuo5lmklm7hndg` (`order_ID`),
  CONSTRAINT `FKibakocf0wogbj669dc4wki8a7` FOREIGN KEY (`supplier_ID`) REFERENCES `supplier` (`id`),
  CONSTRAINT `FKiwvroavxmmecuo5lmklm7hndg` FOREIGN KEY (`order_ID`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplierorders`
--

LOCK TABLES `supplierorders` WRITE;
/*!40000 ALTER TABLE `supplierorders` DISABLE KEYS */;
INSERT INTO `supplierorders` VALUES (1,19),(3,19),(1,20),(3,20),(1,21),(3,21),(1,22),(3,22),(1,23),(3,23),(1,24),(3,24),(1,25),(3,25),(3,26),(3,27),(3,28),(3,29),(7,31),(7,32),(7,33),(7,34);
/*!40000 ALTER TABLE `supplierorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) DEFAULT NULL,
  `userPass` varchar(512) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `customer` int unsigned DEFAULT NULL,
  `driver` int unsigned DEFAULT NULL,
  `supplier` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer` (`customer`),
  KEY `driver` (`driver`),
  KEY `supplier` (`supplier`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`),
  CONSTRAINT `user_ibfk_2` FOREIGN KEY (`driver`) REFERENCES `driver` (`id`),
  CONSTRAINT `user_ibfk_3` FOREIGN KEY (`supplier`) REFERENCES `supplier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'bobbyb','$2a$10$MwVExS8HfojXSiA05kX38eqdMKOn3sJ1q9saBwM5Rd8d4L/x2jity','CUSTOMER',5,NULL,NULL),(2,'HSimpson','$2a$10$9XFrtnH5ozaidaS.EmIWx.XqQnpWJSVg2VOmYEbxIZV/pmY3xdnte','CUSTOMER',13,NULL,NULL),(25,'sebvettel','$2a$10$Mmvs9aB./KnFA0TK2RlCN.8zeNFQMz75pSfQUwjCVvPopWwyuJ5Dq','DRIVER',NULL,1,NULL),(26,'maxv','$2a$10$r3jAUn3IdHRgcCaZwLNjuOcP06VpeCCvGWzxV5n712X5fu3d.G7b.','DRIVER',NULL,2,NULL),(27,'elham','$2a$10$ir4nGd43o6BsdX99r6HrsuuhUtR3NlnldTDqtWuJ3rEU7fbL8Uz2i','DRIVER',NULL,3,NULL),(28,'fernando.alanso','$2a$10$HU/.S2hlWoRVJVr8qgFFq.n0YyKE6SlSC6S9eTyxdP5l99v7i1/za','DRIVER',NULL,4,NULL),(29,'RingsAndThings','$2a$10$rwM7GfOhwmcozsPMtS7vt.Rpza.su.qryhbtRMO8qeYBPGWE6NoFe','SUPPLIER',NULL,NULL,7),(30,'ShoesRus','$2a$10$r4i/QGmwRJjDanxYHk41Ue06Ehc7pUZAINYZw5OGvhzbthv90czjm','SUPPLIER',NULL,NULL,8),(31,'FabFabriques','$2a$10$dt0LfSMH80oP0LwdOsPHDuJebgkg.GPBQ1sfkKpfnIJgsgSL2O7Ne','SUPPLIER',NULL,NULL,9),(32,'Excessorize','$2a$10$ggrKwsGl86Mh0Rjcjcznc.os22RaQQCxLUydcQ1BaiB8g4JL6fP82','SUPPLIER',NULL,NULL,10),(33,'SweetThreads','$2a$10$rS56/gTOL1cuOOuCzf87peaWMs1wyONquVTVq1qk2yedKdW1g/vwK','SUPPLIER',NULL,NULL,11),(34,'CoatsAPlenty','$2a$10$t0o2EfEETcDgKB4kQTRX5u.l4ugdUz8IE68pCU30Sa7YBa5vNWM4i','SUPPLIER',NULL,NULL,12),(35,'dlowe','$2a$10$1WpzJcLeRlK/c1BcSC1PXePja23lDJTPYK0a/OYwQDjt8dlOedEg2','CUSTOMER',19,NULL,NULL),(36,'Mavisons','$2a$10$T0dnILMRtPHfZ8YVHN/HLONFsjbJdU7GP89HMggbnNm4uuc09aWey','SUPPLIER',NULL,NULL,13),(37,'Tailormade','$2a$10$zh1gjsQtmVJA42Fh3Aa2i.KaML7E9/Ei7Pwi8f6z9mgNBiadWH2bi','SUPPLIER',NULL,NULL,14),(38,'Clarks','$2a$10$9iuF3aHP.g90iPN85lTkKen6rXZ5dWUHH3PofYy3jZfTd/MZnXuJq','SUPPLIER',NULL,NULL,15),(39,'Rummages','$2a$10$Ct.A1xZ7Xyrn/pqacIhc1.aBYz0waDoQhz9APTYr2z/ObDZEShCda','SUPPLIER',NULL,NULL,16),(40,'Paraphernalia','$2a$10$0IoCl/Cr4pl6qyaZmc5kiOVgThhymvKX9idQLk5wYqUhv4M7Rp54y','SUPPLIER',NULL,NULL,17),(41,'Armoire','$2a$10$6sCA/NP5RrvRoWWw/CYrJeEUOuHx0IIc167Sj.qmANYiHfE.OwajK','SUPPLIER',NULL,NULL,18),(42,'Coals','$2a$10$CyO5dyXqK5Q1V.ohn37Tj.i5XrBTAQuLG93wFAJ..FsmPRyMJBzVS','SUPPLIER',NULL,NULL,19),(43,'FreshKicks','$2a$10$f14Kd7XzqJ7A3Ji.MQhTXOlT2UCKERm/Ggx/6WWN0uY4aBYaS9SC.','SUPPLIER',NULL,NULL,20),(44,'Knick-Knacks','$2a$10$Mimdh07E7fX9xNCRSS0loOPhauBRJbSuIFC7J.Z9d0tvUsavNCPrm','SUPPLIER',NULL,NULL,21),(45,'Winstons','$2a$10$vzLuBlCAx/6ZlS/5OR0jcuFU31P7CXZwD2d2sqNGNrM7LiqCzqQJu','SUPPLIER',NULL,NULL,22);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `userPass` varchar(512) DEFAULT NULL,
  `userRole` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-08 16:12:47
