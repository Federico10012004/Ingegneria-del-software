-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: calcetto_hub
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `code` char(36) NOT NULL,
  `field_id` char(36) NOT NULL,
  `player_email` varchar(45) NOT NULL,
  `booking_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `status` enum('CONFIRMED','CANCELLED','COMPLETED') NOT NULL,
  PRIMARY KEY (`code`),
  KEY `fk_field_booking_idx` (`field_id`),
  KEY `fk_player_booking_idx` (`player_email`),
  CONSTRAINT `fk_field_booking` FOREIGN KEY (`field_id`) REFERENCES `field` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_player_booking` FOREIGN KEY (`player_email`) REFERENCES `player` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES ('144d236e-57d1-47b0-854a-7943919a9cc0','e21c94d9-83c8-4aef-9edd-022553b38627','mario@email.com','2026-01-15','18:00:00','19:00:00','COMPLETED'),('18dd7841-ba56-4cb9-b83b-e6372019cedd','3d3e73da-08f3-482d-8651-4199fd87d84f','roberto@email.com','2026-01-16','21:00:00','22:00:00','CONFIRMED'),('240b0b93-bd63-4347-9509-5302d6462095','61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0','roberto@email.com','2026-01-15','20:00:00','21:00:00','CONFIRMED'),('2cc72762-a9d1-4d6b-8880-9d040ef392fe','3d3e73da-08f3-482d-8651-4199fd87d84f','mario@email.com','2026-01-16','19:00:00','20:00:00','CANCELLED'),('4071e322-716d-4b65-af8d-dc3110594182','81ccae5d-6a64-4230-bca0-91e66a5f4d03','marta@email.com','2026-01-15','20:00:00','21:00:00','COMPLETED'),('4fe1cf3e-b969-4db3-9f95-3987b723f5bc','76401f30-3c1f-4fd5-ba2d-067adc0e824a','mario@email.com','2026-01-19','21:00:00','22:00:00','CONFIRMED'),('57a9fe11-6a4a-4b40-9362-4e5b4ccfa62d','3d3e73da-08f3-482d-8651-4199fd87d84f','mario@email.com','2026-01-15','20:00:00','21:00:00','COMPLETED'),('781c0004-9d92-46cb-b01a-d82d5a0bdf19','e8002372-dcae-4cd5-b106-3af40852e350','roberto@email.com','2026-01-14','15:00:00','16:00:00','CANCELLED'),('c9f9f2cf-18c6-48e8-bdbc-439580eac66f','e8002372-dcae-4cd5-b106-3af40852e350','marta@email.com','2026-01-23','19:00:00','20:00:00','CONFIRMED'),('cd054330-b266-40a1-be57-e32fc5999ab6','e8002372-dcae-4cd5-b106-3af40852e350','roberto@email.com','2026-01-24','21:00:00','22:00:00','CONFIRMED'),('d09ce044-e721-4180-964d-1ae4eb0e3e09','76401f30-3c1f-4fd5-ba2d-067adc0e824a','roberto@email.com','2026-01-15','21:00:00','22:00:00','CONFIRMED');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field`
--

DROP TABLE IF EXISTS `field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field` (
  `id` char(36) NOT NULL,
  `field_name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `surface_type` enum('SYNTHETIC','GRASS','PARQUET') NOT NULL,
  `indoor` tinyint NOT NULL,
  `hourly_price` decimal(8,2) NOT NULL,
  `manager` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_manager_idx` (`manager`),
  CONSTRAINT `fk_manager` FOREIGN KEY (`manager`) REFERENCES `fieldmanager` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field`
--

LOCK TABLES `field` WRITE;
/*!40000 ALTER TABLE `field` DISABLE KEYS */;
INSERT INTO `field` VALUES ('3d3e73da-08f3-482d-8651-4199fd87d84f','Campo roma','via del corso 101','Roma','SYNTHETIC',1,70.00,'mario@email.com'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0','Campo 4km','via latina, 32','Artena','SYNTHETIC',0,50.00,'laura@email.com'),('76401f30-3c1f-4fd5-ba2d-067adc0e824a','Campo Roma sud','via marsala, 32','Roma','PARQUET',1,60.00,'laura@email.com'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03','Calcetto Roma','viale guglielmo marconi, 23','Roma','SYNTHETIC',0,60.00,'luigi@email.com'),('93627a15-201b-465b-b3bf-d4766cc1fe53','Campo artena','via guglielmo marconi 23','Artena','SYNTHETIC',0,60.00,'mario@email.com'),('b08d5a74-d512-41fa-9551-c2f5b1496ca9','Calciolandia','via consolare latina, 32','Segni','GRASS',0,70.00,'mario@email.com'),('e173851c-d6dd-4880-beae-b336b05484ba','Campo via della pace','via della pace, 138','Valmontone','SYNTHETIC',0,60.00,'luigi@email.com'),('e21c94d9-83c8-4aef-9edd-022553b38627','Campo calcetto valmontone','via antonio gramsci, 23','Valmontone','GRASS',0,65.00,'mario@email.com'),('e8002372-dcae-4cd5-b106-3af40852e350','Campo Valmontone','corso garibaldi, 86','Valmontone','PARQUET',1,60.00,'luigi@email.com');
/*!40000 ALTER TABLE `field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `field_opening_hours`
--

DROP TABLE IF EXISTS `field_opening_hours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `field_opening_hours` (
  `field_id` char(36) NOT NULL,
  `day_of_week` tinyint NOT NULL,
  `opening_time` time NOT NULL,
  `closing_time` time NOT NULL,
  PRIMARY KEY (`field_id`,`day_of_week`),
  CONSTRAINT `fk_field_id` FOREIGN KEY (`field_id`) REFERENCES `field` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `field_opening_hours`
--

LOCK TABLES `field_opening_hours` WRITE;
/*!40000 ALTER TABLE `field_opening_hours` DISABLE KEYS */;
INSERT INTO `field_opening_hours` VALUES ('3d3e73da-08f3-482d-8651-4199fd87d84f',1,'10:00:00','00:00:00'),('3d3e73da-08f3-482d-8651-4199fd87d84f',2,'10:00:00','00:00:00'),('3d3e73da-08f3-482d-8651-4199fd87d84f',4,'10:00:00','00:00:00'),('3d3e73da-08f3-482d-8651-4199fd87d84f',5,'10:00:00','00:00:00'),('3d3e73da-08f3-482d-8651-4199fd87d84f',6,'10:00:00','00:00:00'),('3d3e73da-08f3-482d-8651-4199fd87d84f',7,'10:00:00','00:00:00'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0',1,'09:00:00','21:00:00'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0',2,'09:00:00','21:00:00'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0',3,'09:00:00','21:00:00'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0',4,'09:00:00','21:00:00'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0',5,'09:00:00','21:00:00'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0',6,'10:00:00','21:00:00'),('61e27ca6-e5b7-4d9f-a6b3-e82eab810ea0',7,'10:00:00','21:00:00'),('76401f30-3c1f-4fd5-ba2d-067adc0e824a',1,'09:00:00','22:00:00'),('76401f30-3c1f-4fd5-ba2d-067adc0e824a',2,'09:00:00','22:00:00'),('76401f30-3c1f-4fd5-ba2d-067adc0e824a',3,'09:00:00','22:00:00'),('76401f30-3c1f-4fd5-ba2d-067adc0e824a',4,'09:00:00','22:00:00'),('76401f30-3c1f-4fd5-ba2d-067adc0e824a',5,'09:00:00','22:00:00'),('76401f30-3c1f-4fd5-ba2d-067adc0e824a',6,'09:00:00','22:00:00'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03',1,'09:00:00','23:00:00'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03',2,'09:00:00','23:00:00'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03',3,'09:00:00','23:00:00'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03',4,'09:00:00','23:00:00'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03',5,'09:00:00','23:00:00'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03',6,'10:00:00','00:00:00'),('81ccae5d-6a64-4230-bca0-91e66a5f4d03',7,'10:00:00','00:00:00'),('93627a15-201b-465b-b3bf-d4766cc1fe53',1,'09:00:00','22:00:00'),('93627a15-201b-465b-b3bf-d4766cc1fe53',2,'09:00:00','22:00:00'),('93627a15-201b-465b-b3bf-d4766cc1fe53',3,'09:00:00','22:00:00'),('93627a15-201b-465b-b3bf-d4766cc1fe53',4,'09:00:00','22:00:00'),('93627a15-201b-465b-b3bf-d4766cc1fe53',5,'09:00:00','22:00:00'),('b08d5a74-d512-41fa-9551-c2f5b1496ca9',1,'09:00:00','23:00:00'),('b08d5a74-d512-41fa-9551-c2f5b1496ca9',2,'09:00:00','23:00:00'),('b08d5a74-d512-41fa-9551-c2f5b1496ca9',3,'09:00:00','23:00:00'),('b08d5a74-d512-41fa-9551-c2f5b1496ca9',4,'09:00:00','23:00:00'),('b08d5a74-d512-41fa-9551-c2f5b1496ca9',5,'09:00:00','23:00:00'),('b08d5a74-d512-41fa-9551-c2f5b1496ca9',6,'09:00:00','23:00:00'),('e173851c-d6dd-4880-beae-b336b05484ba',1,'09:00:00','22:00:00'),('e173851c-d6dd-4880-beae-b336b05484ba',2,'09:00:00','22:00:00'),('e173851c-d6dd-4880-beae-b336b05484ba',3,'09:00:00','22:00:00'),('e173851c-d6dd-4880-beae-b336b05484ba',4,'09:00:00','22:00:00'),('e173851c-d6dd-4880-beae-b336b05484ba',5,'09:00:00','22:00:00'),('e21c94d9-83c8-4aef-9edd-022553b38627',1,'09:00:00','22:00:00'),('e21c94d9-83c8-4aef-9edd-022553b38627',2,'09:00:00','22:00:00'),('e21c94d9-83c8-4aef-9edd-022553b38627',3,'09:00:00','22:00:00'),('e21c94d9-83c8-4aef-9edd-022553b38627',4,'09:00:00','22:00:00'),('e21c94d9-83c8-4aef-9edd-022553b38627',5,'09:00:00','22:00:00'),('e21c94d9-83c8-4aef-9edd-022553b38627',6,'10:00:00','22:00:00'),('e8002372-dcae-4cd5-b106-3af40852e350',1,'09:00:00','22:00:00'),('e8002372-dcae-4cd5-b106-3af40852e350',2,'09:00:00','22:00:00'),('e8002372-dcae-4cd5-b106-3af40852e350',3,'09:00:00','22:00:00'),('e8002372-dcae-4cd5-b106-3af40852e350',4,'09:00:00','22:00:00'),('e8002372-dcae-4cd5-b106-3af40852e350',5,'09:00:00','22:00:00'),('e8002372-dcae-4cd5-b106-3af40852e350',6,'11:00:00','00:00:00');
/*!40000 ALTER TABLE `field_opening_hours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fieldmanager`
--

DROP TABLE IF EXISTS `fieldmanager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fieldmanager` (
  `email` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `vatNumber` char(11) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `vatNumber_UNIQUE` (`vatNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fieldmanager`
--

LOCK TABLES `fieldmanager` WRITE;
/*!40000 ALTER TABLE `fieldmanager` DISABLE KEYS */;
INSERT INTO `fieldmanager` VALUES ('laura@email.com','$2a$12$ruG6hM3sy1AvQRU7iuUo3eFu6pZAt.DzIFfKX245ciL204TCft8Hm','Laura','Verdi','1990-05-08','01472583690','+39 356 89 75 451'),('luigi@email.com','$2a$12$Riy.YyKe8fEDNHW2ZSYdme20ma/cY/JD.wZ6d.lYdwsFVhoZHqJkm','Luigi','Bianchi','1980-05-01','98765432109','386 75 45 120'),('mario@email.com','$2a$12$Nc0RlitLa8LEmvdorNxiwOlsGAV2Ah03UTaz11fner7MO89.rYQ32','Mario','Rossi','2001-12-06','01234567890','+39 359 45 76 668'),('roberto@email.com','$2a$12$5721rIebIBpBgALjVhcBqugVtLBvO/bv0MK2UuCZy090Om4nmC.oW','Roberto','Bianchi','1980-08-10','03691472588','+39 386 75 89 455');
/*!40000 ALTER TABLE `fieldmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` char(36) NOT NULL,
  `user_email` varchar(45) NOT NULL,
  `user_role` enum('PLAYER','FIELDMANAGER','REFEREE') NOT NULL,
  `message` varchar(255) NOT NULL,
  `is_read` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES ('0d6e7276-199f-480f-81b4-757a1201e81d','mario@email.com','FIELDMANAGER','Disdetta: mario@email.com ha disdetto la prenotazione per il campo Campo roma in data 2026-01-16 e ora 19:00-20:00. Motivo: motivi di salute',1),('1581c441-299d-40a0-a816-d5318ae66488','mario@email.com','FIELDMANAGER','Nuova prenotazione effettuata da mario@email.com per il campo Campo calcetto valmontone in data 2026-01-15 alle ore 18:00-19:00',1),('40cae568-47e8-447a-ab21-0a8b26d07730','roberto@email.com','PLAYER','La tua prenotazione per il campo Campo Valmontone in data 2026-01-14 e ora 15:00-16:00 è stata cancellata dal gestore. Motivo: Ristrutturazione campo',1),('4354a078-3c20-4f01-b51e-38018c32460b','laura@email.com','FIELDMANAGER','Nuova prenotazione effettuata da roberto@email.com per il campo Campo 4km in data 2026-01-15 alle ore 20:00-21:00',0),('451e172f-c694-4eda-82ab-ead1aadea15e','luigi@email.com','FIELDMANAGER','Nuova prenotazione effettuata da marta@email.com per il campo Campo Valmontone in data 2026-01-23 alle ore 19:00-20:00',1),('5dfdf982-636f-4bbc-8578-dbc708c8e811','laura@email.com','FIELDMANAGER','Nuova prenotazione effettuata da roberto@email.com per il campo Campo Roma sud in data 2026-01-15 alle ore 21:00-22:00',0),('6f6f6535-5e0e-4ecf-b473-d0da15df6fbb','luigi@email.com','FIELDMANAGER','Nuova prenotazione effettuata da marta@email.com per il campo Calcetto Roma in data 2026-01-15 alle ore 20:00-21:00',1),('71e860e6-09e4-4522-a5d8-e1dea5df0740','mario@email.com','FIELDMANAGER','Nuova prenotazione effettuata da mario@email.com per il campo Campo roma in data 2026-01-15 alle ore 20:00-21:00',1),('c2959a19-e59b-4b97-a82f-d5ab332904c5','mario@email.com','FIELDMANAGER','Nuova prenotazione effettuata da roberto@email.com per il campo Campo roma in data 2026-01-16 alle ore 21:00-22:00',1),('d663228a-bc33-4703-8fbc-b7551da0a7cc','luigi@email.com','FIELDMANAGER','Nuova prenotazione effettuata da roberto@email.com per il campo Campo Valmontone in data 2026-01-14 alle ore 15:00-16:00',1),('e1d58be4-ef84-437c-a90c-6e00d9767d58','luigi@email.com','FIELDMANAGER','Nuova prenotazione effettuata da roberto@email.com per il campo Campo Valmontone in data 2026-01-24 alle ore 21:00-22:00',1),('e1fa4320-d3cc-4e5d-9a92-e07759cb1de4','mario@email.com','FIELDMANAGER','Nuova prenotazione effettuata da mario@email.com per il campo Campo roma in data 2026-01-16 alle ore 19:00-20:00',1),('f0233d3a-c574-4ea9-856c-e090b4d517ea','laura@email.com','FIELDMANAGER','Nuova prenotazione effettuata da mario@email.com per il campo Campo Roma sud in data 2026-01-19 alle ore 21:00-22:00',0);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `email` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `preferredPosition` enum('GOALKEEPER','DEFENDER','MIDFIELDER','STRIKER') NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES ('federico.guglielmini@students.eu','$2a$12$yLoPhScMDtBH9Rrg/zD/ve7hiHjp6s9TAQpHdRs2/ftpXn.DxA7v2','Federico','Guglielmini','2004-01-10','DEFENDER'),('mario@email.com','$2a$12$55AQW4qfrcovrsW.Y8KTX..gEFp2E7RWank4wf7rnUmuQZSjg2M4m','Mario','Rossi','1999-09-08','STRIKER'),('marta@email.com','$2a$12$iWX4hV8MLnMPGgRhNf0zKOctkGZ.bmJumTt193yuE4PiWfPiu24xC','Marta','Rossi','2000-02-08','DEFENDER'),('roberto@email.com','$2a$12$RHfQLpTzGeXj8mCOKgMN0.buOed0aWTLDyzyfd8FyANgTgxF28dUe','Roberto','Bianchi','1999-09-08','DEFENDER');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'calcetto_hub'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_booking` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_booking`(
	IN p_booking_code CHAR(36),
    IN p_booking_field_id CHAR(36),
    IN p_booking_player_email VARCHAR(45),
    IN p_booking_date DATE,
    IN p_booking_start_time TIME,
    IN p_booking_end_time TIME,
    IN p_booking_status ENUM('CONFIRMED', 'CANCELLED', 'COMPLETED')
)
BEGIN
	INSERT INTO booking (code, field_id, player_email, booking_date, start_time, end_time, status)
    VALUES (p_booking_code, p_booking_field_id, p_booking_player_email, p_booking_date, p_booking_start_time, p_booking_end_time, p_booking_status);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_field` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_field`(
	IN p_field_id CHAR(36),
    IN p_field_name VARCHAR(45),
    IN p_field_address VARCHAR(45),
    IN p_field_city VARCHAR(45),
    IN p_field_surface ENUM('SYNTHETIC', 'GRASS', 'PARQUET'),
    IN p_field_indoor BOOLEAN,
    IN p_field_hourlyPrice DECIMAL(8,2),
    IN p_field_manager VARCHAR(45),
    IN p_opening_hours JSON
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;

	START TRANSACTION;
    
	INSERT INTO field (id, field_name, address, city, surface_type, indoor, hourly_price, manager)
	VALUES (p_field_id, p_field_name, p_field_address, p_field_city, p_field_surface, p_field_indoor, p_field_hourlyPrice, p_field_manager);
    
    -- Inserisce SOLO i giorni presenti nel JSON
	INSERT INTO field_opening_hours(field_id, day_of_week, opening_time, closing_time)
	SELECT
		p_field_id,
		jt.dow,
		jt.open_t,
		jt.close_t
	FROM JSON_TABLE(p_opening_hours, '$[*]'
		COLUMNS(
			dow     TINYINT PATH '$.dow',
			open_t  TIME    PATH '$.open',
			close_t TIME    PATH '$.close'
		)
	) AS jt;
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_manager` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_manager`(
	IN manager_email VARCHAR(45),
    IN manager_password VARCHAR(64),
    IN manager_name VARCHAR(45),
    IN manager_surname VARCHAR(45),
    IN manager_date_of_birth DATE,
    IN manager_vat_number CHAR(11),
    IN manager_phone VARCHAR(45)
)
BEGIN
	-- Verifica che il field manager non esista già
    IF EXISTS (
		SELECT 1 FROM fieldmanager WHERE email = manager_email
    ) THEN
		SIGNAL SQLSTATE '45000';
	ELSE
		-- Registra nuovo field manager
        INSERT INTO fieldmanager (email, password, name, surname, dateOfBirth, vatNumber, phoneNumber)
        VALUES (manager_email, manager_password, manager_name, manager_surname, manager_date_of_birth, manager_vat_number, manager_phone);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_notification` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_notification`(
	IN p_id CHAR(36),
    IN p_user_email VARCHAR(45),
    IN p_user_role ENUM('PLAYER', 'FIELDMANAGER', 'REFEREE'),
    IN p_message VARCHAR(255),
    IN p_is_read TINYINT
)
BEGIN
	INSERT INTO notification (id, user_email, user_role, message, is_read)
    VALUES (p_id, p_user_email, p_user_role, p_message, p_is_read);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `add_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `add_player`(
	IN player_email VARCHAR(45),
    IN player_password VARCHAR(64),
    IN player_name VARCHAR(45),
    IN player_surname VARCHAR(45),
    IN player_date_of_birth DATE,
    IN player_position ENUM('GOALKEEPER', 'DEFENDER', 'MIDFIELDER', 'STRIKER')
)
BEGIN
	-- Verifica che il player non esista già
    IF EXISTS (
		SELECT 1 FROM player WHERE email = player_email
    ) THEN
		SIGNAL SQLSTATE '45000';
	ELSE
		-- Registra nuovo player
        INSERT INTO player (email, password, name, surname, dateOfBirth, preferredPosition)
        VALUES (player_email, player_password, player_name, player_surname, player_date_of_birth, player_position);
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cancel_booking` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cancel_booking`(
	IN booking_code VARCHAR(45)
)
BEGIN
	UPDATE booking
	SET status = 'CANCELLED'
    WHERE code = booking_code;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_field` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_field`(
	IN field_id CHAR(36)
)
BEGIN
	DELETE FROM field
    WHERE id = field_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_manager` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_manager`(
	IN manager_email VARCHAR(45)
)
BEGIN
	DELETE FROM fieldmanager
    WHERE email = manager_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_player`(
	IN player_email CHAR(36)
)
BEGIN
	DELETE FROM player
    WHERE email = player_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `field_availability` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `field_availability`(
	IN p_field_id CHAR(36),
    IN p_booking_date DATE
)
BEGIN
	SELECT start_time, end_time
    FROM booking
    WHERE field_id = p_field_id AND booking_date = p_booking_date AND status = 'CONFIRMED';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_booking` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_booking`(
	IN booking_code VARCHAR(45)
)
BEGIN
	SELECT field_id, player_email, booking_date, start_time, end_time, status
    FROM booking
    WHERE code = booking_code;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_fields_by_manager` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_fields_by_manager`(
	IN email_manager VARCHAR(45)
)
BEGIN
	SELECT id, field_name, address, city, surface_type, indoor, hourly_price
    FROM field
    WHERE manager = email_manager
    ORDER BY field_name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_field_by_id` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_field_by_id`(
	IN id CHAR(36)
)
BEGIN
	SELECT f.field_name, f.address, f.city, f.surface_type, foh.day_of_week, foh.opening_time, foh.closing_time, f.indoor, f.hourly_price, f.manager
    FROM field f
    JOIN field_opening_hours foh ON foh.field_id = f.id
    WHERE f.id = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_manager` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_manager`(
	IN manager_email VARCHAR(45)
)
BEGIN
	SELECT email, password, name, surname, dateOfBirth, vatNumber, phoneNumber
    FROM fieldmanager
    WHERE email = manager_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_manager_bookings` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_manager_bookings`(
	IN field_manager_email VARCHAR(45)
)
BEGIN
	SELECT b.code, f.field_name, b.booking_date, b.start_time, b.end_time, b.status
    FROM booking b
    JOIN field f
    ON f.id = b.field_id
    WHERE f.manager = field_manager_email
    ORDER BY f.field_name, b.booking_date, b.start_time;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_player`(
	IN player_email VARCHAR(45)
)
BEGIN
	SELECT email, password, name, surname, dateOfBirth, preferredPosition
    FROM player
    WHERE email = player_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_player_bookings` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_player_bookings`(
	IN p_player_email VARCHAR(45)
)
BEGIN
	UPDATE booking
    SET status = 'COMPLETED'
    WHERE player_email = p_player_email 
		AND status = 'CONFIRMED'
		AND (booking_date < CURDATE()
            OR (booking_date = CURDATE() AND end_time <= CURTIME())
            );
	
	SELECT b.code, f.field_name, b.booking_date, b.start_time, b.end_time, b.status
    FROM booking b
    JOIN field f
    ON f.id = b.field_id
    WHERE player_email = p_player_email
   ORDER BY f.field_name, b.booking_date, b.start_time;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_unread_notifications` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_unread_notifications`(
	IN p_user_email VARCHAR(45),
    IN p_user_role ENUM('PLAYER', 'FIELDMANAGER', 'REFEREE')
)
BEGIN
	SELECT id, message, is_read
    FROM notification
    WHERE user_email = p_user_email AND user_role = p_user_role AND is_read = 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `mark_all_read` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `mark_all_read`(
	IN p_user_email VARCHAR(45),
    IN p_user_role ENUM('PLAYER', 'FIELDMANAGER', 'REFEREE')
)
BEGIN
	UPDATE notification
    SET is_read = 1
    WHERE user_email = p_user_email AND user_role = p_user_role AND is_read = 0;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `search_fields` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `search_fields`(
	IN field_address VARCHAR(45),
    IN field_city VARCHAR(45)
)
BEGIN
	IF field_address IS NULL THEN
		SELECT id, field_name, address, city, surface_type, indoor, hourly_price
        FROM field
        WHERE city = field_city;
        
	ELSEIF field_city IS NULL THEN
		SELECT id, field_name, address, city, surface_type, indoor, hourly_price
        FROM field
        WHERE address = field_address;
	
    ELSE
		SELECT id, field_name, address, city, surface_type, indoor, hourly_price
        FROM field
        WHERE city = field_city AND address = field_address;
        
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_manager` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_manager`(
	IN manager_email VARCHAR(45),
    IN manager_new_name VARCHAR(45),
    IN manager_new_surname VARCHAR(45),
    IN manager_new_phone VARCHAR(45)
)
BEGIN
	UPDATE fieldmanager
    SET name = manager_new_name, surname = manager_new_surname, phoneNumber = manager_new_phone
    WHERE email = manager_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_password_manager` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_password_manager`(
	IN manager_email VARCHAR(45),
    IN manager_new_password VARCHAR(64)
)
BEGIN
	UPDATE fieldmanager
    SET password = manager_new_password
    WHERE email = manager_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_password_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_password_player`(
	IN player_email VARCHAR(45),
    IN player_new_password VARCHAR(64)
)
BEGIN
	UPDATE player
    SET password = player_new_password
    WHERE email = player_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `update_player` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_player`(
	IN player_email VARCHAR(45),
	IN player_new_name VARCHAR(45),
    IN player_new_surname VARCHAR(45),
    IN player_new_position ENUM('GOALKEEPER', 'DEFENDER', 'MIDFIELDER', 'STRIKER')
)
BEGIN
	UPDATE player
    SET name = player_new_name, surname = player_new_surname, preferredPosition = player_new_position
    WHERE email = player_email;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-17 17:30:58
