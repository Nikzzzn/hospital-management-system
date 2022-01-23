-- MySQL dump 10.13  Distrib 5.5.23, for Win64 (x86)
--
-- Host: localhost    Database: hospital
-- ------------------------------------------------------
-- Server version	5.5.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `appointment_date` date NOT NULL,
  `appointment_time` time NOT NULL,
  PRIMARY KEY (`id`),
  KEY `doctor_id_idx` (`doctor_id`),
  KEY `patient_id_idx` (`patient_id`),
  CONSTRAINT `doctor_id` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (1,16,17,'2022-01-05','13:00:00'),(2,10,44,'2021-06-21','09:30:00'),(3,7,27,'2021-10-19','16:00:00'),(4,18,44,'2021-01-21','14:30:00'),(5,14,24,'2021-03-30','12:00:00'),(6,20,8,'2021-06-27','13:30:00'),(7,11,43,'2021-05-28','09:00:00'),(8,16,9,'2022-01-14','12:30:00'),(9,14,5,'2021-02-25','15:00:00'),(10,20,35,'2021-04-22','09:30:00'),(11,20,13,'2021-03-04','16:00:00'),(12,5,5,'2021-05-27','10:30:00'),(13,15,19,'2021-01-13','10:00:00'),(14,8,47,'2021-01-06','12:30:00'),(15,4,44,'2021-10-19','13:00:00'),(16,4,50,'2021-09-03','10:30:00'),(17,16,12,'2021-06-28','13:00:00'),(18,20,46,'2021-01-16','13:30:00'),(19,15,40,'2021-05-23','13:00:00'),(20,14,12,'2021-08-07','14:30:00'),(21,20,29,'2021-02-11','10:00:00'),(22,2,10,'2021-03-20','15:30:00'),(23,7,18,'2021-06-16','09:00:00'),(24,1,14,'2021-01-13','10:30:00'),(25,19,2,'2021-09-09','17:00:00'),(26,13,26,'2021-11-29','10:30:00'),(27,5,12,'2022-01-05','09:00:00'),(28,13,14,'2021-11-12','09:30:00'),(29,11,12,'2021-02-19','14:00:00'),(30,3,45,'2021-08-09','12:30:00'),(31,1,31,'2021-12-17','09:00:00'),(32,20,39,'2021-04-08','10:30:00'),(33,1,2,'2021-04-11','14:00:00'),(34,7,15,'2021-02-01','11:30:00'),(35,19,29,'2021-04-21','11:00:00'),(36,3,17,'2021-11-19','09:30:00'),(37,7,46,'2021-06-08','16:00:00'),(38,6,14,'2021-09-23','10:30:00'),(39,7,46,'2021-01-11','09:00:00'),(40,6,4,'2021-03-02','12:30:00'),(41,17,49,'2021-11-28','15:00:00'),(42,12,1,'2021-09-22','15:30:00'),(43,18,31,'2021-09-11','15:00:00'),(44,16,9,'2021-05-02','11:30:00'),(45,9,11,'2021-10-07','10:00:00'),(46,4,7,'2021-11-12','10:30:00'),(47,20,27,'2021-11-09','17:00:00'),(48,2,12,'2021-07-15','13:30:00'),(49,18,29,'2021-07-24','09:00:00'),(50,11,47,'2021-10-24','15:30:00'),(51,15,48,'2022-01-16','10:00:00'),(52,17,41,'2021-06-09','10:30:00'),(53,1,13,'2021-10-30','14:00:00'),(54,2,42,'2021-01-11','09:30:00'),(55,5,11,'2021-11-29','14:00:00'),(56,20,33,'2021-06-22','13:30:00'),(57,12,38,'2021-05-04','12:00:00'),(58,12,48,'2021-05-17','09:30:00'),(59,17,9,'2021-11-09','15:00:00'),(60,5,3,'2021-05-24','16:30:00'),(61,18,19,'2021-11-17','17:00:00'),(62,16,35,'2021-11-29','14:30:00'),(63,4,7,'2021-06-15','12:00:00'),(64,19,14,'2021-10-11','16:30:00'),(65,2,28,'2021-01-25','11:00:00'),(66,16,18,'2021-08-09','12:30:00'),(67,8,9,'2021-02-15','10:00:00'),(68,6,41,'2021-07-25','13:30:00'),(69,18,47,'2021-04-14','13:00:00'),(70,7,26,'2021-10-25','15:30:00'),(71,19,6,'2021-06-27','12:00:00'),(72,20,18,'2021-02-27','13:30:00'),(73,7,5,'2021-06-28','10:00:00'),(74,14,26,'2021-08-02','14:30:00'),(75,17,32,'2022-01-01','09:00:00'),(76,2,49,'2021-04-21','17:30:00'),(77,19,25,'2021-06-09','16:00:00'),(78,4,16,'2021-10-24','12:30:00'),(79,15,7,'2021-07-17','10:00:00'),(80,11,38,'2022-01-15','17:30:00'),(81,4,21,'2021-05-29','13:00:00'),(82,19,38,'2021-10-02','09:30:00'),(83,6,41,'2021-06-16','09:00:00'),(84,20,42,'2022-01-09','10:30:00'),(85,5,43,'2021-02-02','12:00:00'),(86,4,50,'2021-12-12','17:30:00'),(87,1,8,'2021-01-11','10:00:00'),(88,17,45,'2021-01-23','17:30:00'),(89,10,24,'2021-03-06','17:00:00'),(90,17,21,'2021-09-05','09:30:00'),(91,16,22,'2021-07-05','10:00:00'),(92,14,24,'2021-08-05','14:30:00'),(93,4,5,'2021-09-23','13:00:00'),(94,8,4,'2021-05-10','11:30:00'),(95,6,43,'2021-03-07','15:00:00'),(96,8,5,'2021-04-13','13:30:00'),(97,2,22,'2021-11-16','11:00:00'),(98,2,23,'2021-12-11','15:30:00'),(99,8,37,'2021-10-06','10:00:00'),(100,6,5,'2021-07-07','12:30:00'),(101,16,43,'2021-10-25','14:00:00'),(102,1,1,'2021-04-15','15:30:00'),(103,1,10,'2021-09-17','14:00:00'),(104,18,37,'2021-06-03','16:30:00'),(105,3,11,'2021-04-13','09:00:00'),(106,17,49,'2021-11-05','15:30:00'),(107,13,19,'2021-03-20','14:00:00'),(108,20,16,'2021-07-03','12:30:00'),(109,14,20,'2021-08-29','12:00:00'),(110,12,36,'2021-11-16','10:30:00'),(111,6,2,'2021-08-08','11:00:00'),(112,4,6,'2021-04-17','15:30:00'),(113,20,14,'2021-07-10','12:00:00'),(114,10,45,'2021-06-09','15:30:00'),(115,15,17,'2021-03-12','16:00:00'),(116,6,34,'2021-04-03','12:30:00'),(117,1,27,'2021-04-04','14:00:00'),(118,2,35,'2021-10-29','10:30:00'),(119,16,4,'2021-12-23','16:00:00'),(120,20,16,'2021-10-07','09:30:00'),(121,10,8,'2022-01-14','13:00:00'),(122,4,16,'2021-09-10','14:30:00'),(123,19,8,'2021-12-12','09:00:00'),(124,17,30,'2021-12-07','09:30:00'),(125,15,31,'2021-07-19','09:00:00'),(126,4,30,'2021-05-14','12:30:00'),(127,15,33,'2021-06-10','16:00:00'),(128,17,14,'2022-01-21','14:30:00'),(129,9,18,'2021-10-20','10:00:00'),(130,14,12,'2021-04-28','15:30:00'),(131,7,8,'2021-11-10','10:00:00'),(132,18,33,'2021-06-15','13:30:00'),(133,9,44,'2021-07-24','16:00:00'),(134,4,47,'2021-11-12','12:30:00'),(135,16,30,'2021-12-11','11:00:00'),(136,20,42,'2021-09-21','14:30:00'),(137,15,34,'2021-03-10','09:00:00'),(138,15,7,'2021-03-11','12:30:00'),(139,11,33,'2021-09-13','09:00:00'),(140,8,42,'2021-12-14','09:30:00'),(141,18,44,'2021-09-05','14:00:00'),(142,16,25,'2021-10-12','12:30:00'),(143,14,45,'2021-08-08','13:00:00'),(144,15,22,'2021-12-27','15:30:00'),(145,20,35,'2021-03-08','15:00:00'),(146,12,11,'2021-05-20','11:30:00'),(147,16,34,'2021-10-18','09:00:00'),(148,11,21,'2022-01-14','14:30:00'),(149,12,9,'2021-09-26','10:00:00'),(150,3,3,'2021-08-21','09:30:00'),(151,9,45,'2021-12-19','10:00:00'),(152,11,23,'2021-11-22','15:30:00'),(153,14,33,'2021-12-17','14:00:00'),(154,20,5,'2021-11-25','15:30:00'),(155,1,10,'2021-11-03','12:00:00'),(156,1,6,'2022-01-16','15:30:00'),(157,6,13,'2021-11-18','14:00:00'),(158,1,47,'2021-12-07','13:30:00'),(159,13,12,'2021-11-30','11:00:00'),(160,17,9,'2021-10-13','15:30:00'),(161,12,46,'2021-12-23','16:00:00'),(162,12,41,'2021-10-23','12:30:00'),(163,13,38,'2021-09-22','12:00:00'),(164,14,30,'2021-02-20','12:30:00'),(165,4,46,'2021-03-18','16:00:00'),(166,9,26,'2021-09-11','15:30:00'),(167,14,3,'2021-11-21','09:00:00'),(168,15,26,'2021-11-11','14:30:00'),(169,13,46,'2021-05-31','17:00:00'),(170,19,2,'2021-04-06','13:30:00'),(171,8,27,'2021-03-22','11:00:00'),(172,6,39,'2022-01-09','14:30:00'),(173,18,49,'2021-08-16','13:00:00'),(174,1,10,'2021-12-18','11:30:00'),(175,17,37,'2022-01-03','14:00:00'),(176,9,13,'2021-09-12','16:30:00'),(177,5,5,'2021-09-24','10:00:00'),(178,8,27,'2021-01-23','15:30:00'),(179,5,15,'2021-06-06','09:00:00'),(180,2,37,'2021-12-09','11:30:00'),(181,19,12,'2021-08-05','16:00:00'),(182,1,8,'2021-04-15','15:30:00'),(183,18,19,'2021-03-05','17:00:00'),(184,16,47,'2021-12-09','09:30:00'),(185,8,16,'2021-09-09','13:00:00'),(186,19,4,'2022-01-19','10:30:00'),(187,4,21,'2021-09-20','10:00:00'),(188,18,48,'2022-01-11','13:30:00'),(189,1,39,'2021-07-19','13:00:00'),(190,5,39,'2021-05-23','13:30:00'),(191,4,23,'2021-12-19','10:00:00'),(192,20,15,'2021-03-31','12:30:00'),(193,14,8,'2021-10-23','17:00:00'),(194,7,23,'2021-03-13','13:30:00'),(195,2,22,'2021-02-24','14:00:00'),(196,14,34,'2022-01-17','15:30:00'),(197,3,31,'2021-04-20','09:00:00'),(198,15,29,'2021-11-16','15:30:00'),(199,17,12,'2021-11-05','09:00:00'),(200,3,1,'2021-02-23','09:30:00');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_name` varchar(45) NOT NULL,
  `specialty_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `specialty_id_idx` (`specialty_id`),
  CONSTRAINT `specialty_id` FOREIGN KEY (`specialty_id`) REFERENCES `specialties` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'Vanna Moscrop',2),(2,'Ivan Rotlauf',10),(3,'Temp Forrest',4),(4,'Pearline Bamb',2),(5,'Adams Hryniewicki',6),(6,'Alisander Kaplan',10),(7,'Nancy Burchall',9),(8,'Ilyssa Dagwell',12),(9,'Jenny McAllan',1),(10,'Arney Jaquet',9),(11,'Layla Basindale',10),(12,'Nolana de Clercq',11),(13,'Stoddard Ferrea',9),(14,'Giffard Trehearn',10),(15,'Berny Jeffress',5),(16,'Phaedra Lightewood',4),(17,'Bendick Giovanni',9),(18,'Panchito Yancey',2),(19,'Charmain Kenford',2),(20,'Leandra Winspear',9);
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_name` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  `gender` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,'Enrique Lune','1975-03-31','Genderfluid','881-780-5418','8988 Mitchell Avenue'),(2,'Selia Conley','1994-01-16','Male','520-919-7888','76353 Dryden Junction'),(3,'Geri L\'Episcopio','2007-11-11','Female','300-391-1613','20317 Victoria Parkway'),(4,'Bonnie Gemnett','2003-09-16','Genderqueer','861-189-8558','7 Fieldstone Park'),(5,'Ema Muncey','1985-09-21','Male','281-378-5523','2 Ludington Place'),(6,'Ingaberg Lightning','1979-12-03','Female','269-217-0801','9 Springs Avenue'),(7,'Avrom Delahunt','1993-02-17','Non-binary','587-107-2512','15 Shoshone Parkway'),(8,'Rubie Philo','2003-01-16','Genderfluid','500-611-9044','52 Pawling Lane'),(9,'Carrol Ginity','2004-02-10','Male','508-892-9872','66 Nobel Park'),(10,'Austen Jacqueminot','1998-08-13','Non-binary','634-701-5690','220 Quincy Drive'),(11,'Fanya Vaadeland','1996-07-13','Male','846-967-3373','8 Anthes Junction'),(12,'Eliza Lilloe','2011-12-20','Male','931-464-9169','456 Green Circle'),(13,'Terence Spancock','2007-06-16','Agender','423-155-1798','703 Milwaukee Drive'),(14,'Parnell Jachimiak','1998-03-31','Female','278-838-4353','35 Prairie Rose Junction'),(15,'Ellis Duhig','1984-12-28','Female','848-926-8391','66477 Harbort Alley'),(16,'Magdalene Fantin','2012-07-19','Female','526-740-8408','185 Katie Crossing'),(17,'Ada Stannis','2001-07-24','Female','650-330-8301','8 Ridge Oak Way'),(18,'Ruggiero Reignard','1990-07-21','Genderqueer','472-631-6055','84473 Tony Alley'),(19,'Elva Wackley','2003-04-12','Female','769-412-1494','0392 Lawn Pass'),(20,'Chrissie Leming','2006-02-26','Female','682-820-8087','2 Fairview Pass'),(21,'Israel Ellicock','1999-10-22','Male','975-252-1329','351 Vermont Place'),(22,'Janet Dobbie','1999-08-23','Female','588-884-3537','69 Jenna Crossing'),(23,'Selma Bittleson','2014-08-20','Female','222-171-3475','6 Crownhardt Street'),(24,'Adela Mathiasen','2001-11-22','Female','761-840-8066','7010 Carey Trail'),(25,'Faun Chinn','2002-03-31','Male','683-354-5205','78 Arapahoe Street'),(26,'Ronny Pugsley','1964-08-27','Female','720-944-8270','37580 Dennis Court'),(27,'Wilhelmine Cestard','2003-01-29','Genderfluid','988-339-9624','2 Grim Trail'),(28,'Sonja Bosley','1976-09-07','Female','894-774-1594','0 Merrick Pass'),(29,'Myrna Haggas','2007-04-14','Polygender','879-143-9670','2 Rutledge Circle'),(30,'Valerye Orteaux','1991-02-26','Male','136-221-2160','062 Clarendon Way'),(31,'Aviva Cackett','2006-11-05','Male','427-423-8716','4 Burning Wood Crossing'),(32,'Shermy Halso','1998-10-26','Female','330-444-7046','379 Eastwood Center'),(33,'Othella Yitzhakov','2001-11-12','Female','274-449-9017','06732 Parkside Point'),(34,'Annmaria Charer','1966-10-31','Genderqueer','136-938-4222','02436 Namekagon Plaza'),(35,'Angele Tucknutt','1984-12-24','Male','561-143-5944','502 Crest Line Road'),(36,'Kiley Mitcham','1993-04-18','Male','567-191-4674','198 Northfield Drive'),(37,'Forbes Bault','2001-10-17','Male','887-561-6287','92039 Dovetail Lane'),(38,'Ayn Gibbens','1981-04-26','Female','387-720-1987','50 Ohio Parkway'),(39,'Gusta Neenan','1960-11-25','Female','575-292-2102','45760 Steensland Drive'),(40,'Demeter Franzoli','1973-05-27','Female','101-823-7374','2726 Judy Crossing'),(41,'Elli Hamprecht','1965-05-29','Female','902-349-0901','18600 Springs Court'),(42,'Nealson Hankard','1976-04-02','Male','681-555-7661','43 Coleman Lane'),(43,'Arlyne Burgan','1979-07-08','Male','210-904-2751','41299 Sherman Way'),(44,'Maura Pershouse','1972-11-07','Female','309-721-3348','284 Northland Park'),(45,'Jose Kelston','1987-04-26','Male','170-149-1767','7914 David Circle'),(46,'Brett Gligoraci','1989-08-18','Male','955-789-6153','8 Shoshone Circle'),(47,'Viki Glisenan','1988-02-05','Agender','707-430-2912','04015 Elmside Pass'),(48,'Hercule Bootell','1960-05-02','Female','126-406-0254','68959 Saint Paul Way'),(49,'Byrom Tredget','1965-02-12','Female','606-526-5643','552 Del Mar Junction'),(50,'Noami Ilden','2001-10-12','Male','423-633-9531','841 Sloan Terrace');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialties`
--

DROP TABLE IF EXISTS `specialties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `specialties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `specialty` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialties`
--

LOCK TABLES `specialties` WRITE;
/*!40000 ALTER TABLE `specialties` DISABLE KEYS */;
INSERT INTO `specialties` VALUES (1,'Cardiology'),(2,'Dermatology'),(3,'Endocrinology'),(4,'Gastroenterology'),(5,'Surgery'),(6,'Neurology'),(7,'Ophthalmology'),(8,'Pediatrics'),(9,'Psychiatry'),(10,'Urology'),(11,'Dentistry'),(12,'Oncology');
/*!40000 ALTER TABLE `specialties` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-23 13:10:30
