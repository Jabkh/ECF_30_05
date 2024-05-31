-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: ecf_30_05
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categorie` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `prix` double NOT NULL,
  `quantite` int NOT NULL,
  `taille` varchar(255) DEFAULT NULL,
  `id_vente` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrobowf2ms1hhc6jqify2sbdhi` (`id_vente`),
  CONSTRAINT `FKrobowf2ms1hhc6jqify2sbdhi` FOREIGN KEY (`id_vente`) REFERENCES `vente` (`id_vente`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'HOMME','description1',40,20,'taille1',NULL),(2,'FEMME','modification reussie',60,40,'nouvelle taille',NULL),(3,'ENFANT','decscription3',30,30,'taille3',NULL),(4,'HOMME','descritpion4',30,20,'taille4',NULL),(5,'FEMME','description5',35,30,'taille5',NULL),(6,'ENFANT','decription6',50,40,'taille6',NULL),(7,'HOMME','HOMME',50,60,'taille7',NULL),(8,'FEMME','description8',80,40,'taille8',NULL),(9,'ENFANT','description9',50,20,'taille9',NULL),(11,'ENFANT','description11',55,50,'taille11',NULL);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `mail` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'mail1','nom1'),(2,'mail modifie','nom modifie'),(3,'mail3','nom3'),(5,'mail5','nom5');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vente`
--

DROP TABLE IF EXISTS `vente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vente` (
  `id_vente` int NOT NULL AUTO_INCREMENT,
  `dateAchat` date DEFAULT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `client_id` int DEFAULT NULL,
  PRIMARY KEY (`id_vente`),
  KEY `FKt8c0ckr8dqeq9nia3wb7j6r7n` (`client_id`),
  CONSTRAINT `FKt8c0ckr8dqeq9nia3wb7j6r7n` FOREIGN KEY (`client_id`) REFERENCES `client` (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vente`
--

LOCK TABLES `vente` WRITE;
/*!40000 ALTER TABLE `vente` DISABLE KEYS */;
INSERT INTO `vente` VALUES (1,'2024-05-31','EN_COURS',1);
/*!40000 ALTER TABLE `vente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vente_article`
--

DROP TABLE IF EXISTS `vente_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vente_article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int DEFAULT NULL,
  `vente_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrftl4bck8hf60bsevicfv0i56` (`article_id`),
  KEY `FKpx5kc6inx0x571nyw1vt9ocqe` (`vente_id`),
  CONSTRAINT `FKpx5kc6inx0x571nyw1vt9ocqe` FOREIGN KEY (`vente_id`) REFERENCES `vente` (`id_vente`),
  CONSTRAINT `FKrftl4bck8hf60bsevicfv0i56` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vente_article`
--

LOCK TABLES `vente_article` WRITE;
/*!40000 ALTER TABLE `vente_article` DISABLE KEYS */;
INSERT INTO `vente_article` VALUES (1,2,1);
/*!40000 ALTER TABLE `vente_article` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-31 12:54:53
