-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurant_manager
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `id` varchar(20) NOT NULL,
  `restaurant_id` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `street_name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `status` tinyint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES ('2838138152','0818083738','Địa Quán Chi Nhánh Gò Dầu','Gò Dầu','95 Gò Dầu, Tân Quý, Tân Phú, Thành phố Hồ Chí Minh','2838138152',1),('2838106837','0818083738','Địa Quán Chi Nhánh Nguyễn Sơn','Nguyễn Sơn','67 Nguyễn Sơn, Phú Thạnh, Tân Phú, Thành phố Hồ Chí Minh','2838106837',1),('0901958251','2862675771','Viet Kitchen Chi Nhánh Lũy Bán Bích','Lũy Bán Bích','72A Lũy Bán Bích, Tân Thới Hoà, Tân Phú, Thành phố Hồ Chí Minh','0909945994',1);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` varchar(20) NOT NULL,
  `restaurant_id` varchar(20) NOT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('0373656053','0818083738',NULL,'Lê','Bảo','Lê Quang Quốc Bảo','Nam','2001-06-01','baole@gmail.com','0373656053','Manager','Quảng Ngãi','Mộ Đức','Thôn 2, Đức Nhuận','$2a$10$hccD2cPLIcCNsT.1C0r65eHaQnB9w23GL66KM6PLtAQPqfjnm9Xma',1,'2022-11-02 09:51:29','2022-11-05 10:13:16'),('0901958251','0818083738',NULL,'Lê','Phong','Lê Quang Phong','Nam','2008-01-08','phongle.f@gmail.com','0901958251','Cashier','Quảng Ngãi','Mộ Đức','Thôn 2, Đức Nhuận','$2a$10$HyTotawqkOC8hjamZf5avOrN5PcAD3nUEp6TXdGf9YMTtkruU8Ib2',0,'2022-11-02 09:59:21','2022-11-03 15:54:14'),('0908558172','0818083738','2838138152','Lê','Giang','Lê Thị Lệ Giang','Nữ','2008-07-18','giangle.f@gmail.com','0908558172','Staff','Quảng Ngãi','Mộ Đức','Thôn 2, Đức Nhuận','$2a$10$iRWtsFON1XdXyngh.coNMu1ycGhGF0iMERIy/dZx/kAtLp4TYZMnu',1,'2022-11-02 10:13:27','2022-11-06 15:55:25'),('0376233212','0818083738',NULL,'Lê','Đầy','Lê Quang Đầy','Nam','1965-05-27','dayle.f@gmail.com','0376233212','President','Quảng Ngãi','Mộ Đức','Thôn 2, Đức Nhuận','$2a$10$a85Hqr3b4BgtgVjmIj872uHOurtHwsfr3DlIG6aSXwgBvbABCgrz.',0,'2022-11-02 17:12:15','2022-11-03 15:54:14'),('0702766003','0818083738','2838106837','Trần','Kết','Trần Thị Ánh Kết','Nữ','1966-05-29','anhket.f@gmail.com','0702766003','President','Quảng Ngãi','Mộ Đức','Thôn 2, Đức Nhuận','$2a$10$7ADMr4rnXk7/sUpHVJXly.uwDxUqxezVaGN7s4GJDWxb59BPF.Q9i',1,'2022-11-02 19:24:03','2022-11-05 10:21:40'),('0935327573','0818083738','2838106837','Trần','Vy','Trần Đặng Thảo Vy','Nữ','2001-08-08','thaovy.f@gmail.com','0935327573','Cashier','Quảng Ngãi','Mộ Đức','Thôn 5, Đức Nhuận','$2a$10$H6/nlBLDEIOegbIXYn8JfOJy7FyV3mJ/0FzoVKAEZ.qDrfIQ1QAJy',0,'2022-11-02 19:27:13','2022-11-03 11:06:46');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `restaurant_id` varchar(20) DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (16,'0818083738',NULL,'Ga',300000,'luoc',1),(17,'0818083738',NULL,'Ga nho',350000,'luoc',1);
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `food_detail`
--

DROP TABLE IF EXISTS `food_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `food_id` int DEFAULT NULL,
  `material_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_detail`
--

LOCK TABLES `food_detail` WRITE;
/*!40000 ALTER TABLE `food_detail` DISABLE KEYS */;
INSERT INTO `food_detail` VALUES (15,16,'YTUDS'),(16,16,'YTUDS'),(17,17,'YTUDS'),(18,17,'ALKJDFWE');
/*!40000 ALTER TABLE `food_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(20) DEFAULT NULL,
  `restaurant_id` varchar(20) DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `cost` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `where_production` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,'YTUDS','0818083738',NULL,'Gà tây',200000,'con',400,'Nước ngoài'),(2,'YTUDS','0818083738','2838106837','Gà tây',200000,'con',1000,'Nước ngoài'),(3,'ALKJDFWE','0818083738','2838138152','Strawberry',200000,'kg',100,'Nước ngoài'),(4,'ALKJDFWE','0818083738',NULL,'Strawberry',100000,'kg',100,'Nước ngoài');
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` varchar(20) DEFAULT NULL,
  `table_id` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `total_amount` float DEFAULT NULL,
  `status` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `id` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES ('0818083738','Địa Quán','quandia@gmail.com','0818083738','Không gian thoáng mát, xanh sạch đẹp. Nơi tụ họp tiệc tùng. Các món ăn phong phú.','5 Khuông Việt, Phú Trung, Tân Phú, Thành phố Hồ Chí Minh',1),('2862675771','Viet Kitchen','bao.f@gmail.com','0373656056','Bạn thuộc “team yêu biển, ghiền hải sản”? Đến Viet Kitchen ngay để thưởng thức brunch (bữa sáng muộn) hay tiệc buffet hải sản cao cấp, với đa dạng lựa chọn món ăn từ ốc hấp xả, cá xốt rau củ, sashimi đến tôm hùm xốt bơ tỏi','Khách sạn Renaissance Riverside Saigon, 8 - 15, đường Tôn Đức Thắng, phường Bến Nghé, quận 1, thành phố Hồ Chí Minh',1),('0908410449','Bonsai Dinner Cruise','bonsaidinner@gmail.com','0908410449','Cảm nhận một Sài Gòn về đêm thật khác biệt, khi tham gia trải nghiệm ăn uống trên du thuyền Bonsai Dinner Cruise cùng bạn bè, người thân hoặc “nửa kia”. Chẳng những ghi điểm với thực đơn “cực phẩm” kết hợp Đông - Tây.','Trên Thuyền',1);
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tables`
--

DROP TABLE IF EXISTS `tables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tables` (
  `id` int NOT NULL AUTO_INCREMENT,
  `restaurant_id` varchar(20) DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `total_slot` smallint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tables`
--

LOCK TABLES `tables` WRITE;
/*!40000 ALTER TABLE `tables` DISABLE KEYS */;
INSERT INTO `tables` VALUES (36,'0818083738',NULL,'A1','Bàn cao cấp',6,0),(37,'0818083738',NULL,'A2','Bàn cao cấp',6,0),(38,'0818083738',NULL,'A3','Bàn cao cấp',6,0),(39,'0818083738',NULL,'A4','Bàn Bình Dân',6,0),(40,'0818083738',NULL,'B4','Bàn Bình Dân',6,0),(41,'0818083738',NULL,'B1','Bàn Bình Dân',6,0),(42,'0818083738','2838138152','A6','Bàn cao cấp',6,0),(43,'0818083738','2838138152','B2','Bàn Bình Dân',6,0),(44,'0818083738','2838138152','B3','Bàn Bình Dân',6,0),(45,'0818083738','2838138152','A1','Bàn Bình Dân',6,1);
/*!40000 ALTER TABLE `tables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ware_house`
--

DROP TABLE IF EXISTS `ware_house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ware_house` (
  `id` int NOT NULL AUTO_INCREMENT,
  `employee_id` varchar(20) NOT NULL,
  `material_code` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ware_house`
--

LOCK TABLES `ware_house` WRITE;
/*!40000 ALTER TABLE `ware_house` DISABLE KEYS */;
INSERT INTO `ware_house` VALUES (18,'0702766003','YTUDS'),(19,'0908558172','ALKJDFWE'),(20,'0373656053','ALKJDFWE');
/*!40000 ALTER TABLE `ware_house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ware_house_detail`
--

DROP TABLE IF EXISTS `ware_house_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ware_house_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `warehouse_id` int DEFAULT NULL,
  `cost` int DEFAULT NULL,
  `vat_amount` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `total_amount` int DEFAULT NULL,
  `create_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ware_house_detail`
--

LOCK TABLES `ware_house_detail` WRITE;
/*!40000 ALTER TABLE `ware_house_detail` DISABLE KEYS */;
INSERT INTO `ware_house_detail` VALUES (6,18,200000,10,100,700,'2022-11-05 13:49:43'),(7,18,200000,10,100,800,'2022-11-05 13:50:14'),(10,18,200000,10,100,1000,'2022-11-06 20:43:54'),(11,20,100000,10,100,100,'2022-11-07 10:47:56');
/*!40000 ALTER TABLE `ware_house_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-08  9:34:09
