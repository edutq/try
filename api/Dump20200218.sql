-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: translock
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `candado`
--

DROP TABLE IF EXISTS `candado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  `dueno` int(11) DEFAULT NULL,
  `mac_address` varchar(17) NOT NULL,
  `marca` int(11) DEFAULT NULL,
  `identificador` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `imei_UNIQUE` (`mac_address`),
  UNIQUE KEY `identificador_UNIQUE` (`identificador`),
  KEY `dueno` (`dueno`),
  KEY `marca_ibfk_1_idx` (`marca`),
  CONSTRAINT `candado_ibfk_1` FOREIGN KEY (`dueno`) REFERENCES `usuario` (`id`),
  CONSTRAINT `marca_ibfk_1` FOREIGN KEY (`marca`) REFERENCES `marca` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candado`
--

LOCK TABLES `candado` WRITE;
/*!40000 ALTER TABLE `candado` DISABLE KEYS */;
INSERT INTO `candado` VALUES (1,'Harborlock',39,'ED:C2:B0:65:8B:FD',2,'20072436047'),(2,'Translock',42,'00:15:80:91:39:0F',3,'translock'),(3,'Jointech',38,'6C:C3:74:D6:33:93',1,'740190311213'),(4,'habortry',NULL,'12:EF:50:15:FA:00',2,'12532');
/*!40000 ALTER TABLE `candado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candado_usuario`
--

DROP TABLE IF EXISTS `candado_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candado_usuario` (
  `id_usuario` int(11) DEFAULT NULL,
  `id_candado` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alias` varchar(30) NOT NULL,
  `administrador` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_usuario` (`id_usuario`,`id_candado`),
  KEY `id_candado` (`id_candado`),
  CONSTRAINT `candado_usuario_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `candado_usuario_ibfk_2` FOREIGN KEY (`id_candado`) REFERENCES `candado` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candado_usuario`
--

LOCK TABLES `candado_usuario` WRITE;
/*!40000 ALTER TABLE `candado_usuario` DISABLE KEYS */;
INSERT INTO `candado_usuario` VALUES (39,1,1,'har',1),(38,3,3,'join',1),(39,3,5,'Jointech',1),(40,3,6,'Jointech',0),(38,2,9,'translock 2',0),(38,1,23,'chiquito',1),(42,3,24,'Cargolock',1),(39,2,25,'translock 2',0),(42,2,27,'translock',1),(42,1,28,'har',0);
/*!40000 ALTER TABLE `candado_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (1,'APERTURA'),(3,'CIERRE'),(2,'COMPARTIDO');
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial`
--

DROP TABLE IF EXISTS `historial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `idUsuario` int(11) NOT NULL,
  `idCandado` int(11) NOT NULL,
  `idEvento` int(11) NOT NULL,
  `idAfectado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idCandado_idx` (`idCandado`),
  KEY `idEvento_idx` (`idEvento`),
  KEY `idUsuario_idx` (`idUsuario`),
  KEY `idAfectado_idx` (`idAfectado`),
  CONSTRAINT `idAfectado` FOREIGN KEY (`idAfectado`) REFERENCES `usuario` (`id`),
  CONSTRAINT `idCandado` FOREIGN KEY (`idCandado`) REFERENCES `candado` (`id`),
  CONSTRAINT `idEvento` FOREIGN KEY (`idEvento`) REFERENCES `evento` (`id`),
  CONSTRAINT `idUsuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial`
--

LOCK TABLES `historial` WRITE;
/*!40000 ALTER TABLE `historial` DISABLE KEYS */;
INSERT INTO `historial` VALUES (1,'2020-02-07 10:37:50',38,3,1,NULL),(2,'2020-02-07 11:37:54',38,3,1,NULL),(3,'2020-02-07 11:38:20',38,2,1,NULL),(4,'2020-02-07 11:38:24',38,2,3,NULL),(5,'2020-02-07 12:44:53',38,2,1,NULL),(6,'2020-02-07 12:55:53',39,3,1,NULL),(7,'2020-02-07 13:00:19',38,1,1,NULL),(8,'2020-02-07 13:00:19',38,1,1,NULL),(9,'2020-02-07 13:02:07',38,2,3,NULL),(10,'2020-02-07 14:56:21',38,1,1,NULL),(11,'2020-02-07 14:56:22',38,1,1,NULL),(12,'2020-02-07 14:56:53',38,3,1,NULL),(13,'2020-02-07 15:38:12',39,1,1,NULL),(14,'2020-02-07 15:38:13',39,1,1,NULL),(15,'2020-02-10 11:56:03',38,2,1,NULL),(16,'2020-02-10 17:08:21',38,2,2,39),(17,'2020-02-10 17:42:12',39,1,2,38),(18,'2020-02-10 18:03:31',38,1,1,NULL),(19,'2020-02-10 18:03:32',38,1,1,NULL),(20,'2020-02-10 18:16:51',38,3,2,42),(21,'2020-02-10 18:18:04',42,3,1,NULL),(22,'2020-02-11 13:16:33',38,2,2,39),(23,'2020-02-12 10:54:34',38,3,1,NULL),(24,'2020-02-12 10:54:44',38,1,1,NULL),(25,'2020-02-12 10:54:46',38,1,1,NULL),(26,'2020-02-12 10:54:53',38,2,1,NULL),(27,'2020-02-12 10:55:00',38,2,3,NULL),(28,'2020-02-12 10:55:07',38,2,3,NULL),(29,'2020-02-12 10:55:13',38,2,1,NULL),(30,'2020-02-12 10:55:32',38,2,3,NULL),(31,'2020-02-12 10:55:42',38,2,1,NULL),(32,'2020-02-12 10:56:25',38,2,1,NULL),(33,'2020-02-12 10:56:32',38,2,3,NULL),(34,'2020-02-13 10:26:32',38,2,1,NULL),(35,'2020-02-13 13:33:05',38,1,1,NULL),(36,'2020-02-13 13:33:06',38,1,1,NULL),(37,'2020-02-13 13:33:49',38,3,1,NULL),(38,'2020-02-13 13:34:06',38,1,1,NULL),(39,'2020-02-13 13:34:07',38,1,1,NULL),(40,'2020-02-13 13:34:12',38,1,1,NULL),(41,'2020-02-13 13:34:14',38,1,1,NULL),(42,'2020-02-13 13:34:57',38,2,3,NULL),(43,'2020-02-13 16:48:01',38,3,1,NULL),(44,'2020-02-13 16:52:12',38,1,1,NULL),(45,'2020-02-13 16:52:12',38,1,1,NULL),(46,'2020-02-13 16:52:37',38,1,1,NULL),(47,'2020-02-13 16:52:37',38,1,1,NULL),(48,'2020-02-13 16:53:16',38,1,1,NULL),(49,'2020-02-13 16:53:17',38,1,1,NULL),(50,'2020-02-13 16:53:54',38,1,1,NULL),(51,'2020-02-13 16:53:54',38,1,1,NULL),(52,'2020-02-13 16:55:38',38,1,1,NULL),(53,'2020-02-13 16:55:39',38,1,1,NULL),(54,'2020-02-13 16:55:53',38,3,1,NULL),(55,'2020-02-13 16:56:02',38,2,1,NULL),(56,'2020-02-13 16:56:04',38,2,3,NULL),(57,'2020-02-13 16:56:05',38,2,3,NULL),(58,'2020-02-13 18:27:26',38,2,3,NULL),(59,'2020-02-14 17:58:50',38,2,1,NULL),(60,'2020-02-15 22:45:38',38,1,1,NULL),(61,'2020-02-15 22:45:39',38,1,1,NULL),(62,'2020-02-15 22:45:49',38,1,1,NULL),(63,'2020-02-15 22:45:49',38,1,1,NULL),(64,'2020-02-16 01:26:08',38,1,1,NULL),(65,'2020-02-16 01:26:08',38,1,1,NULL),(66,'2020-02-16 01:29:00',38,1,1,NULL),(67,'2020-02-16 01:29:00',38,1,1,NULL),(68,'2020-02-17 08:23:22',42,3,1,NULL),(69,'2020-02-17 08:23:35',42,3,1,NULL),(70,'2020-02-17 08:28:18',42,3,1,NULL),(71,'2020-02-17 08:28:29',42,2,1,NULL),(72,'2020-02-17 08:28:33',42,2,3,NULL),(73,'2020-02-17 08:28:51',42,3,1,NULL),(74,'2020-02-17 08:28:55',42,3,1,NULL),(75,'2020-02-17 08:33:45',42,2,1,NULL),(76,'2020-02-17 08:33:54',42,2,1,NULL),(77,'2020-02-17 08:34:18',42,3,1,NULL),(78,'2020-02-17 09:32:56',42,3,1,NULL),(79,'2020-02-17 10:16:55',39,1,2,42),(80,'2020-02-17 10:17:27',39,1,1,NULL),(81,'2020-02-17 10:17:28',39,1,1,NULL),(82,'2020-02-17 12:24:45',42,3,1,NULL),(83,'2020-02-17 12:24:51',42,3,1,NULL),(84,'2020-02-17 16:42:44',38,1,1,NULL),(85,'2020-02-17 16:42:44',38,1,1,NULL),(86,'2020-02-17 16:42:45',38,3,1,NULL),(87,'2020-02-17 16:49:39',38,3,1,NULL);
/*!40000 ALTER TABLE `historial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marca`
--

DROP TABLE IF EXISTS `marca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marca`
--

LOCK TABLES `marca` WRITE;
/*!40000 ALTER TABLE `marca` DISABLE KEYS */;
INSERT INTO `marca` VALUES (2,'harborlock'),(1,'jointech'),(3,'translock');
/*!40000 ALTER TABLE `marca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos_de_rol`
--

DROP TABLE IF EXISTS `permisos_de_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos_de_rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos_de_rol`
--

LOCK TABLES `permisos_de_rol` WRITE;
/*!40000 ALTER TABLE `permisos_de_rol` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisos_de_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos_rol`
--

DROP TABLE IF EXISTS `permisos_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos_rol` (
  `id_permiso` int(11) DEFAULT NULL,
  `id_rol` int(11) DEFAULT NULL,
  KEY `id_permiso` (`id_permiso`),
  KEY `id_rol` (`id_rol`),
  CONSTRAINT `permisos_rol_ibfk_1` FOREIGN KEY (`id_permiso`) REFERENCES `permisos_de_rol` (`id`),
  CONSTRAINT `permisos_rol_ibfk_2` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos_rol`
--

LOCK TABLES `permisos_rol` WRITE;
/*!40000 ALTER TABLE `permisos_rol` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisos_rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos_usuario_candado`
--

DROP TABLE IF EXISTS `permisos_usuario_candado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos_usuario_candado` (
  `id_usuario_candado` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `fecha` (`fecha_inicio`,`fecha_fin`,`id_usuario_candado`),
  KEY `id_usuario_candado` (`id_usuario_candado`),
  CONSTRAINT `permisos_usuario_candado_ibfk_2` FOREIGN KEY (`id_usuario_candado`) REFERENCES `candado_usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos_usuario_candado`
--

LOCK TABLES `permisos_usuario_candado` WRITE;
/*!40000 ALTER TABLE `permisos_usuario_candado` DISABLE KEYS */;
INSERT INTO `permisos_usuario_candado` VALUES (6,15,'2020-02-12 07:24:00','2020-02-13 15:04:00'),(6,10,'2020-02-12 07:24:00','2020-02-13 15:24:00'),(6,14,'2020-02-12 07:24:00','2020-02-13 15:25:00'),(5,8,'2020-02-12 07:24:00','2020-02-14 15:24:00'),(25,20,'2020-02-13 14:54:00','2022-02-13 15:05:00'),(9,21,'2020-02-13 17:30:00','2020-02-14 17:28:00'),(23,25,'2020-02-15 15:00:00','2020-03-15 17:00:00'),(9,22,'2020-02-29 18:24:00','2020-03-19 18:24:00'),(9,23,'2020-03-27 18:24:00','2020-04-16 18:24:00'),(9,24,'2020-06-10 18:24:00','2020-09-10 18:24:00');
/*!40000 ALTER TABLE `permisos_usuario_candado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Super Usuario'),(2,'Usuario Base');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `session_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `expires` int(11) unsigned NOT NULL,
  `data` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES ('Bx4lEDy0ApfPaJK7Avb2MVEdT32gyF5L',1582065426,'{\"cookie\":{\"originalMaxAge\":null,\"expires\":null,\"httpOnly\":true,\"path\":\"/\"},\"passport\":{\"user\":\"4424588186\"}}'),('Mb0PiJmoQHHSSrZFTUn2J2ehHx2vz6ts',1582066003,'{\"cookie\":{\"originalMaxAge\":null,\"expires\":null,\"httpOnly\":true,\"path\":\"/\"},\"passport\":{\"user\":\"4424588186\"}}'),('jTCUFyD_5FhFtk4gRhsGSCUw2OAQXeS1',1582144956,'{\"cookie\":{\"originalMaxAge\":null,\"expires\":null,\"httpOnly\":true,\"path\":\"/\"},\"passport\":{\"user\":\"4424588186\"}}'),('rGC7kxE3S2eNgL7Rh8CO1pkG-jXsPAph',1582066103,'{\"cookie\":{\"originalMaxAge\":null,\"expires\":null,\"httpOnly\":true,\"path\":\"/\"},\"passport\":{\"user\":\"4424588186\"}}'),('zO0uW0UaOrLglBgFOFs1oMhHGcwn_h_l',1582065765,'{\"cookie\":{\"originalMaxAge\":null,\"expires\":null,\"httpOnly\":true,\"path\":\"/\"},\"passport\":{\"user\":\"4424588186\"}}');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `telefono` varchar(10) NOT NULL,
  `rol` int(11) DEFAULT '2',
  `nombre` varchar(30) DEFAULT NULL,
  `apellido` varchar(30) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `numero` (`telefono`),
  KEY `usuario_ibfk_1` (`rol`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (22,'4434588186',2,'ed','tab','$2b$10$jFdojsm6ZPDwl8tUaNfg1eIfwVVqlxXfU6ttuwEpdVL9DnRB7M7ae',1),(23,'4444588186',2,'e','t','$2b$10$UxRI6t41KHMVfL3MPn1U5.Rdl8F9p1ApxHZwkYaAlx6vvAPnwgOby',0),(38,'4424588186',2,'Eduardo','Tavarez','$2b$10$uiZ.09p9tiIXMI/Rbucx6u3j5aYLHr5o.jQfFeaDWMzl17Jic4axO',1),(39,'4421203652',2,'Francisco','Benitez','$2b$10$bEcxG2O3H6t/sO26pVLqlu2UstW95lfL0mw/K8alaKa94L1fQvkxK',1),(40,'4425996654',2,'diego','dominguez','$2b$10$8n3THFu4DhISAfxKvX1b8ul3Ge9cnA4ClqOBh/gum3YiYHfQQ4gzW',1),(41,'3331234567',1,'Victor','Valencia','1234',1),(42,'3334449284',2,'Victor','Valencia','$2b$10$Ra8JX4xHASwNxK.7LWuKVu7.ziXXiDUvKDNx4VO0/K/Ni5D9njiuG',1),(43,'4474588186',2,'e','t','$2b$10$voEnEPtkFQ/.x11bv7i7je2pGL2YgWorbyx5vpXzDjrPRT8aik9K2',0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'translock'
--
/*!50003 DROP PROCEDURE IF EXISTS `ACTIVAR_USUARIO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ACTIVAR_USUARIO`(IN telefono varchar(10))
BEGIN
	UPDATE usuario SET activo = 1 WHERE usuario.telefono = telefono;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ASIGNAR_CANDADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ASIGNAR_CANDADO`(IN idUsuario int, IN idCandado int, IN idAsignador int, IN nombre varchar(30))
BEGIN
	DECLARE rol INT;
    CALL FETCH_ROL(idAsignador, rol);
    IF rol = 1 THEN
		UPDATE candado SET candado.dueno = idUsuario where candado.id = idCandado;
        INSERT INTO 
			candado_usuario (id_usuario, id_candado, alias)
		values
			(idUsuario, idCandado, nombre);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `BUSCAR_USUARIO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `BUSCAR_USUARIO`(IN telefono varchar(10))
BEGIN
	select id, nombre, apellido, telefono, rol, activo, password from usuario where usuario.telefono = telefono;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `BUSCAR_USUARIOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `BUSCAR_USUARIOS`(IN idUsuario INT)
BEGIN
	select 
		id, nombre, apellido, telefono 
	from 
		usuario
	WHERE 
		rol = 2 AND
        id <> idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CAMBIAR_ALIAS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CAMBIAR_ALIAS`(IN idCandado INT, IN idUsuario INT, IN nuevoalias varchar(30))
BEGIN
	UPDATE
		candado_usuario
	SET
		alias = nuevoalias
	WHERE
		id_candado = idCandado AND
        id_usuario = idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CAMBIAR_PASSWORD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CAMBIAR_PASSWORD`(IN idUsuario INT, IN newpass varchar(255))
BEGIN
	UPDATE 
		usuario 
	SET 
		usuario.password = newpass 
	where 
		usuario.id = idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `COMPARTIR_CANDADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `COMPARTIR_CANDADO`(IN idUsuario int, IN idCandado int, IN idDueno int, IN administrador tinyint(1))
BEGIN
	DECLARE dueno tinyint(1);
    DECLARE alias varchar(30);
    CALL ES_DUENO(idDueno, idCandado, dueno);
    IF dueno = 1 THEN
		select 
			candado_usuario.alias 
        from 
			candado_usuario 
        where 
			id_usuario = idDueno AND 
            id_candado = idCandado
		into 
			alias;
		insert into candado_usuario 
			(id_usuario, id_candado, candado_usuario.alias, candado_usuario.administrador) 
		values 
			(idUsuario, idCandado, alias, administrador);
		CALL HISTORIAL_COMPARTIR(idDueno, idCandado, idUsuario);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CREAR_CANDADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CREAR_CANDADO`(IN nombre varchar(30), IN mac varchar(17), IN marca int, IN identificador varchar(45))
BEGIN
	INSERT INTO
		candado
			(candado.nombre, candado.mac_address, candado.marca, candado.identificador)
		VALUES
			(nombre, mac, marca, identificador);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CREAR_USUARIO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CREAR_USUARIO`(IN nombre varchar(30),
	IN apellido varchar(30), IN pass varchar (255), IN telefono varchar(10))
BEGIN
    insert into usuario (telefono, rol, nombre, apellido, password) values (telefono, 2, nombre, apellido, pass);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DEJAR_DE_COMPARTIR` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DEJAR_DE_COMPARTIR`(IN idUsuario INT, IN idCandado INT, IN idDueno INT)
BEGIN
	DECLARE dueno tinyint(1);
    CALL ES_DUENO(idDueno, idCandado, dueno);
    IF dueno = 1 THEN
		DELETE FROM
			candado_usuario
		WHERE
			id_candado = idCandado AND
            id_usuario = idUsuario;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `EDITAR_PERMISO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `EDITAR_PERMISO`(IN fecha_in varchar(15), IN hora_in varchar(15), IN fecha_f varchar(15), IN hora_f varchar(15), IN idPermiso INT, IN idCandado INT, IN idDueno INT)
BEGIN
    DECLARE dueno tinyint(1);

    CALL ES_DUENO(idDueno, idCandado, dueno);
	IF dueno = 1 THEN
		UPDATE
			permisos_usuario_candado
		SET
			fecha_inicio = CONCAT(fecha_in, " ", hora_in),
            fecha_fin = CONCAT(fecha_f, " ", hora_f)
		WHERE
			permisos_usuario_candado.id = idPermiso;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `EDITAR_USUARIO_NOMBRE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `EDITAR_USUARIO_NOMBRE`(IN nombre varchar(30), IN apellido varchar(30), IN id int)
BEGIN
	UPDATE 
		usuario 
	SET 
		usuario.nombre = nombre, 
		usuario.apellido = apellido 
	WHERE usuario.id = id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ELIMINAR_CANDADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ELIMINAR_CANDADO`(IN id_candado int, OUT mensaje varchar(50))
BEGIN
	DELETE FROM candado WHERE id = id_candado;
    SET mensaje = "Candado eliminado";
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ELIMINAR_PERMISO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ELIMINAR_PERMISO`(IN idPermiso INT, IN idDueno INT)
BEGIN
	DECLARE idCandado INT;
    DECLARE idRelacion INT;
    DECLARE dueno TINYINT(1);
    
    SELECT 
		id_usuario_candado 
	FROM 
		permisos_usuario_candado 
	WHERE 
		id = idPermiso
	INTO
		idRelacion;
    
    SELECT
		id_candado
	FROM
		candado_usuario
	WHERE
		id = idRelacion
	INTO
		idCandado;
   
    CALL ES_DUENO(idDueno, idCandado, dueno);
    IF dueno = 1 THEN
		DELETE FROM
			permisos_usuario_candado
		WHERE
			id = idPermiso;
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ES_ADMIN` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ES_ADMIN`(IN idUsuario int, IN idCandado int, OUT admin tinyint(1))
BEGIN
	SELECT 
		administrador
	FROM
		candado_usuario
	WHERE 
		id_usuario = id_usuario AND 
		id_candado = idCandado
	INTO 
		admin;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ES_DUENO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ES_DUENO`(IN id_usuario int, IN id_candado int, OUT dueno tinyint(1))
BEGIN
	SELECT EXISTS(select candado.dueno from candado where candado.dueno = id_usuario AND candado.id = id_candado) INTO dueno;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FETCH_HISTORIAL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_HISTORIAL`(IN idUsuario INT)
BEGIN
	select 
		date_format(historial.fecha, '%Y-%m-%d') as 'fecha', 
        date_format(historial.fecha, '%H:%i:%s') as 'hora', 
        usuario.nombre, 
        usuario.apellido, 
        evento.nombre as 'evento', 
        candado_usuario.alias,
        (select CONCAT(nombre, " ", apellido) from usuario where id = historial.idAfectado) as 'afectado'
    from 
		historial
	join
		usuario, evento, candado, candado_usuario
	where
		usuario.id = historial.idUsuario AND
        candado.id = historial.idCandado AND
        evento.id = historial.idEvento AND
        candado_usuario.id_usuario = idUsuario AND
        candado_usuario.id_candado = historial.idCandado AND
        historial.fecha >= now() - interval 2 month
	ORDER BY historial.fecha DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FETCH_HISTORIAL_POR_FECHA` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_HISTORIAL_POR_FECHA`(IN idUsuario INT, IN fechaInicio varchar(15), IN fechaFin varchar(15))
BEGIN
	DECLARE fecha_inicio DATE;
    DECLARE fecha_fin DATE;
    SET fecha_inicio = str_to_date(fechaInicio, '%Y-%m-%d');
    SET fecha_fin = str_to_date(fechaFin, '%Y-%m-%d');
	SELECT
		date_format(historial.fecha, '%Y-%m-%d') as 'fecha', 
        date_format(historial.fecha, '%H:%i:%s') as 'hora', 
        usuario.nombre, 
        usuario.apellido, 
        evento.nombre as 'evento', 
        candado_usuario.alias,
        (select CONCAT(nombre, " ", apellido) from usuario where id = historial.idAfectado) as 'afectado'
    from 
		historial
	join
		usuario, evento, candado, candado_usuario
	where
		usuario.id = historial.idUsuario AND
        candado.id = historial.idCandado AND
        evento.id = historial.idEvento AND
        candado_usuario.id_usuario = idUsuario AND
        candado_usuario.id_candado = historial.idCandado AND
        date_format(historial.fecha, '%Y-%m-%d') >= fecha_inicio AND
        date_format(historial.fecha, '%Y-%m-%d') <= fecha_fin
	ORDER BY historial.fecha DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FETCH_PASSWORD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_PASSWORD`(IN idUsuario INT)
BEGIN
	SELECT
		password
	FROM 
		usuario
	WHERE
		usuario.id = idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FETCH_PERMISOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_PERMISOS`(IN idUsuario INT, IN idCandado INT)
BEGIN
	DECLARE idUsuarioCandado INT;
    SELECT
		id
	FROM
		candado_usuario
	WHERE
		id_usuario = idUsuario AND
        id_candado = idCandado
	INTO
		idUsuarioCandado;
	SELECT
		id,
		date_format(fecha_inicio, '%Y-%m-%d') as 'fecha_inicio',
        date_format(fecha_inicio, '%H:%i:%s') as 'hora_inicio',
        date_format(fecha_fin, '%Y-%m-%d') as 'fecha_fin',
        date_format(fecha_fin, '%H:%i:%s') as 'hora_fin'
	FROM
		permisos_usuario_candado
	WHERE
		id_usuario_candado = idUsuarioCandado
	ORDER BY
		fecha_inicio,
        hora_inicio,
        fecha_fin,
        hora_fin;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FETCH_ROL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_ROL`(IN id_usuario int, OUT rol int)
BEGIN
	select usuario.rol INTO rol from usuario where usuario.id = id_usuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `FETCH_TODOS_MIS_PERMISOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `FETCH_TODOS_MIS_PERMISOS`(IN idUsuario INT)
BEGIN

	SELECT
		permisos_usuario_candado.id,
		date_format(fecha_inicio, '%Y-%m-%d') as 'fecha_inicio',
        date_format(fecha_inicio, '%H:%i:%s') as 'hora_inicio',
        date_format(fecha_fin, '%Y-%m-%d') as 'fecha_fin',
        date_format(fecha_fin, '%H:%i:%s') as 'hora_fin',
        candado_usuario.id_candado
	FROM
		permisos_usuario_candado
	JOIN
		candado_usuario
	WHERE
		id_usuario_candado = candado_usuario.id AND
        candado_usuario.id_usuario = idUsuario
	ORDER BY
		fecha_inicio,
        hora_inicio,
        fecha_fin,
        hora_fin;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `HISTORIAL_COMPARTIR` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `HISTORIAL_COMPARTIR`(IN idActor INT, 
										IN idCandado INT, 
										IN idAfectado INT)
BEGIN
	INSERT 
		INTO 
			historial (historial.fecha, 
						historial.idUsuario, 
						historial.idCandado,
                        historial.idEvento, 
                        historial.idAfectado)
		VALUES
			(NOW(), idActor, idCandado, 2, idAfectado);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MIS_CANDADOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `MIS_CANDADOS`(IN id_usuario int)
BEGIN
	select 
		candado.id, 
        candado_usuario.alias, 
        candado.dueno, 
        candado.mac_address as imei, 
        candado.marca as 'marca',
        candado_usuario.administrador,
        candado.identificador
	from 
		candado 
    join 
		candado_usuario 
    where 
		candado_usuario.id_usuario = id_usuario AND 
        candado.id = candado_usuario.id_candado;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MIS_CANDADOS_COMPARTIDOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `MIS_CANDADOS_COMPARTIDOS`(IN idUsuario int)
BEGIN
	select 
		candado.id, 
        candado_usuario.alias,
        ( SELECT 
			alias 
		  FROM
			candado_usuario 
		  WHERE 
			candado_usuario.id_usuario = idUsuario AND 
			candado_usuario.id_candado = candado.id 
		) as alias,
        candado.mac_address as imei, 
        candado.identificador,
        count(candado.id) as cantidad 
	from 
		candado 
	join 
		candado_usuario 
	WHERE 
		candado.dueno = idUsuario AND 
        candado_usuario.id_candado = candado.id AND 
        candado_usuario.id_usuario <> idUsuario
    group by candado.id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MIS_CANDADOS_PROPIOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `MIS_CANDADOS_PROPIOS`(IN idUsuario int)
BEGIN
	select 
		candado.id, 
        candado_usuario.alias,
        candado.identificador
	from 
		candado 
	join 
		candado_usuario 
	WHERE 
		candado.dueno = idUsuario AND 
        candado_usuario.id_candado = candado.id AND 
        candado_usuario.id_usuario = idUsuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MODIFICAR_ADMINISTRACION` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `MODIFICAR_ADMINISTRACION`(IN idUsuario int, IN idCandado int, IN idDueno int, IN administrador tinyint(1))
BEGIN

    DECLARE dueno tinyint(1);
    CALL ES_DUENO(idUsuaio, idCandado, dueno);
    IF dueno = 1 THEN

		UPDATE 
			candado_usuario 
		SET 
			candado_usuario.administrador = administrador 
		WHERE 
			candado_usuario.id_usuario = idUsuario AND 
			candado_usuario.id_candado = idCandado;

	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `OTORGAR_PERMISO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `OTORGAR_PERMISO`(IN fecha_in varchar(15), IN hora_in varchar(15), IN fecha_f varchar(15), IN hora_f varchar(15), IN idUsuario INT, IN idCandado INT, IN idDueno INT)
BEGIN
	DECLARE idcandadoUsuario INT;
    DECLARE dueno tinyint(1);

    CALL ES_DUENO(idDueno, idCandado, dueno);
	IF dueno = 1 THEN
		SELECT
			id
		FROM
			candado_usuario
		WHERE
			candado_usuario.id_usuario = idUsuario AND
			candado_usuario.id_candado = idCandado
		INTO 
			idCandadoUsuario;

		INSERT INTO
			permisos_usuario_candado (id_usuario_candado, fecha_inicio, fecha_fin)
		VALUES
			(idCandadoUsuario, CONCAT(fecha_in, " ", hora_in), CONCAT(fecha_f, " ", hora_f));
	END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RECUPERAR_PASSWORD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `RECUPERAR_PASSWORD`(IN telefono varchar(10), IN newpass varchar(255))
BEGIN
	UPDATE 
		usuario 
	SET 
		usuario.password = newpass 
	where 
		usuario.telefono = telefono;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `REGISTRAR_HISTORIAL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `REGISTRAR_HISTORIAL`(IN idUsuario INT, IN idCandado int, IN idEvento INT)
BEGIN
	INSERT 
		INTO 
			historial (historial.fecha, historial.idUsuario, historial.idCandado, historial.idEvento)
		VALUES
			(NOW(), idUsuario, idCandado, idEvento);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `REGISTRAR_HISTORIAL_OFFLINE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `REGISTRAR_HISTORIAL_OFFLINE`(IN fecha varchar(30), IN idUsuario INT, IN idCandado int, IN idEvento INT)
BEGIN
	DECLARE fecha_registro DATE;
    SET fecha_registro = str_to_date(fecha, '%Y-%m-%d %H:%i:%s');
	INSERT 
		INTO 
			historial (historial.fecha, historial.idUsuario, historial.idCandado, historial.idEvento)
		VALUES
			(fecha_registro, idUsuario, idCandado, idEvento);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `REMOVER_CANDADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `REMOVER_CANDADO`(IN idCandado int, IN idAsignador int)
BEGIN
	DECLARE rol INT;

    CALL FETCH_ROL(idAsignador, rol);
    IF rol = 1 THEN
		
		UPDATE candado SET candado.dueno = NULL where candado.id = idCandado;
        DELETE from 
			candado_usuario
		WHERE
			candado_usuario.id_candado = idCandado;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TIENE_PERMISO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `TIENE_PERMISO`(IN id_permiso int, IN id_usuario int, OUT permiso tinyint)
BEGIN
	DECLARE rol int;
	CALL FETCH_ROL(id_usuario, rol);
	select EXISTS(select * from permisos_rol where permisos_rol.id_permiso = id_permiso AND permisos_rol.id_rol = rol) INTO permiso;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TODOS_LOS_CANDADOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `TODOS_LOS_CANDADOS`()
BEGIN
	SELECT * FROM candado;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TODOS_LOS_USUARIOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `TODOS_LOS_USUARIOS`()
BEGIN
	select 
		id, nombre, apellido, telefono 
	from 
		usuario;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `USUARIOS_CANDADO_COMPARTIDO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `USUARIOS_CANDADO_COMPARTIDO`(IN idCandado int, IN idDueno int)
BEGIN
	DECLARE dueno tinyint(1);
    CALL ES_DUENO(idDueno, idCandado, dueno);
    IF dueno = 1 THEN
		select 
			usuario.id, 
            usuario.telefono, 
            usuario.nombre, 
            usuario.apellido,
            candado_usuario.administrador
		from 
        usuario 
        join 
			candado_usuario 
		where 
			candado_usuario.id_candado = idCandado AND 
            usuario.id = candado_usuario.id_usuario AND 
            candado_usuario.id_usuario <> idDueno;
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

-- Dump completed on 2020-02-18 14:44:46
