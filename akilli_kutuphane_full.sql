-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: akilli_kutuphane
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `cezalar`
--

DROP TABLE IF EXISTS `cezalar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cezalar` (
  `ceza_id` int NOT NULL AUTO_INCREMENT,
  `islem_id` int DEFAULT NULL,
  `ceza_miktari` decimal(10,2) NOT NULL,
  `odendi_mi` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ceza_id`),
  KEY `islem_id` (`islem_id`),
  CONSTRAINT `cezalar_ibfk_1` FOREIGN KEY (`islem_id`) REFERENCES `odunc_islemleri` (`islem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cezalar`
--

LOCK TABLES `cezalar` WRITE;
/*!40000 ALTER TABLE `cezalar` DISABLE KEYS */;
/*!40000 ALTER TABLE `cezalar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategoriler`
--

DROP TABLE IF EXISTS `kategoriler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategoriler` (
  `kategori_id` int NOT NULL AUTO_INCREMENT,
  `ad` varchar(50) NOT NULL,
  PRIMARY KEY (`kategori_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategoriler`
--

LOCK TABLES `kategoriler` WRITE;
/*!40000 ALTER TABLE `kategoriler` DISABLE KEYS */;
/*!40000 ALTER TABLE `kategoriler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kitaplar`
--

DROP TABLE IF EXISTS `kitaplar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kitaplar` (
  `kitap_id` int NOT NULL AUTO_INCREMENT,
  `baslik` varchar(100) NOT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `stok_sayisi` int DEFAULT '1',
  `yazar_id` int DEFAULT NULL,
  `kategori_id` int DEFAULT NULL,
  PRIMARY KEY (`kitap_id`),
  UNIQUE KEY `isbn` (`isbn`),
  KEY `yazar_id` (`yazar_id`),
  KEY `kategori_id` (`kategori_id`),
  CONSTRAINT `kitaplar_ibfk_1` FOREIGN KEY (`yazar_id`) REFERENCES `yazarlar` (`yazar_id`) ON DELETE SET NULL,
  CONSTRAINT `kitaplar_ibfk_2` FOREIGN KEY (`kategori_id`) REFERENCES `kategoriler` (`kategori_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kitaplar`
--

LOCK TABLES `kitaplar` WRITE;
/*!40000 ALTER TABLE `kitaplar` DISABLE KEYS */;
/*!40000 ALTER TABLE `kitaplar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kullanicilar`
--

DROP TABLE IF EXISTS `kullanicilar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kullanicilar` (
  `kullanici_id` int NOT NULL AUTO_INCREMENT,
  `ad` varchar(50) NOT NULL,
  `soyad` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `sifre` varchar(255) NOT NULL,
  `rol` enum('OGRENCI','PERSONEL','ADMIN') DEFAULT 'OGRENCI',
  `olusturulma_tarihi` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`kullanici_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kullanicilar`
--

LOCK TABLES `kullanicilar` WRITE;
/*!40000 ALTER TABLE `kullanicilar` DISABLE KEYS */;
/*!40000 ALTER TABLE `kullanicilar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `odunc_islemleri`
--

DROP TABLE IF EXISTS `odunc_islemleri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `odunc_islemleri` (
  `islem_id` int NOT NULL AUTO_INCREMENT,
  `kullanici_id` int DEFAULT NULL,
  `kitap_id` int DEFAULT NULL,
  `alis_tarihi` date NOT NULL,
  `son_teslim_tarihi` date NOT NULL,
  `iade_tarihi` date DEFAULT NULL,
  PRIMARY KEY (`islem_id`),
  KEY `kullanici_id` (`kullanici_id`),
  KEY `kitap_id` (`kitap_id`),
  CONSTRAINT `odunc_islemleri_ibfk_1` FOREIGN KEY (`kullanici_id`) REFERENCES `kullanicilar` (`kullanici_id`),
  CONSTRAINT `odunc_islemleri_ibfk_2` FOREIGN KEY (`kitap_id`) REFERENCES `kitaplar` (`kitap_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `odunc_islemleri`
--

LOCK TABLES `odunc_islemleri` WRITE;
/*!40000 ALTER TABLE `odunc_islemleri` DISABLE KEYS */;
/*!40000 ALTER TABLE `odunc_islemleri` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `stok_dusur` AFTER INSERT ON `odunc_islemleri` FOR EACH ROW BEGIN
    UPDATE kitaplar 
    SET stok_sayisi = stok_sayisi - 1 
    WHERE kitap_id = NEW.kitap_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `yazarlar`
--

DROP TABLE IF EXISTS `yazarlar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `yazarlar` (
  `yazar_id` int NOT NULL AUTO_INCREMENT,
  `ad` varchar(50) NOT NULL,
  `soyad` varchar(50) NOT NULL,
  PRIMARY KEY (`yazar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yazarlar`
--

LOCK TABLES `yazarlar` WRITE;
/*!40000 ALTER TABLE `yazarlar` DISABLE KEYS */;
/*!40000 ALTER TABLE `yazarlar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'akilli_kutuphane'
--

--
-- Dumping routines for database 'akilli_kutuphane'
--
/*!50003 DROP PROCEDURE IF EXISTS `ceza_hesapla` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ceza_hesapla`(IN p_islem_id INT, IN gunluk_ceza DECIMAL(5,2))
BEGIN
    DECLARE gecikme_gun INT;
    DECLARE teslim_tarihi DATE;
    DECLARE gercek_iade DATE;
    
    SELECT son_teslim_tarihi, iade_tarihi INTO teslim_tarihi, gercek_iade 
    FROM odunc_islemleri WHERE islem_id = p_islem_id;
    
    IF gercek_iade > teslim_tarihi THEN
        SET gecikme_gun = DATEDIFF(gercek_iade, teslim_tarihi);
        
        INSERT INTO cezalar (islem_id, ceza_miktari) 
        VALUES (p_islem_id, gecikme_gun * gunluk_ceza);
    END IF;
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

-- Dump completed on 2025-12-02 16:14:55
