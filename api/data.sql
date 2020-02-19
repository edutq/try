-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: translock
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
  `alias` varchar(30) DEFAULT NULL,
  `dueno` int(11) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  KEY `dueno` (`dueno`),
  CONSTRAINT `candado_ibfk_1` FOREIGN KEY (`dueno`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candado`
--

LOCK TABLES `candado` WRITE;
/*!40000 ALTER TABLE `candado` DISABLE KEYS */;
INSERT INTO `candado` VALUES (1,'candadito 1',NULL,NULL,NULL),(2,'candadito 2',NULL,NULL,0),(3,'candadito 3',NULL,NULL,0);
/*!40000 ALTER TABLE `candado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candado_usuario`
--

DROP TABLE IF EXISTS `candado_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candado_usuario` (
  `id_usuario` int(11) NOT NULL,
  `id_candado` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `administrador` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `candado_usuario_ibfk_1` (`id_usuario`),
  KEY `candado_usuario_ibfk_2` (`id_candado`),
  CONSTRAINT `candado_usuario_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `candado_usuario_ibfk_2` FOREIGN KEY (`id_candado`) REFERENCES `candado` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candado_usuario`
--

LOCK TABLES `candado_usuario` WRITE;
/*!40000 ALTER TABLE `candado_usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `candado_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_sessions_table_name`
--

DROP TABLE IF EXISTS `custom_sessions_table_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `custom_sessions_table_name` (
  `custom_session_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `custom_expires_column_name` int(11) unsigned NOT NULL,
  `custom_data_column_name` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  PRIMARY KEY (`custom_session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_sessions_table_name`
--

LOCK TABLES `custom_sessions_table_name` WRITE;
/*!40000 ALTER TABLE `custom_sessions_table_name` DISABLE KEYS */;
/*!40000 ALTER TABLE `custom_sessions_table_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos_de_apertura`
--

DROP TABLE IF EXISTS `permisos_de_apertura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos_de_apertura` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_inicio` datetime DEFAULT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos_de_apertura`
--

LOCK TABLES `permisos_de_apertura` WRITE;
/*!40000 ALTER TABLE `permisos_de_apertura` DISABLE KEYS */;
/*!40000 ALTER TABLE `permisos_de_apertura` ENABLE KEYS */;
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
  `id_permiso` int(11) DEFAULT NULL,
  `id_usuario_candado` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `id_permiso` (`id_permiso`),
  KEY `id_usuario_candado` (`id_usuario_candado`),
  CONSTRAINT `permisos_usuario_candado_ibfk_1` FOREIGN KEY (`id_permiso`) REFERENCES `permisos_de_apertura` (`id`),
  CONSTRAINT `permisos_usuario_candado_ibfk_2` FOREIGN KEY (`id_usuario_candado`) REFERENCES `candado_usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos_usuario_candado`
--

LOCK TABLES `permisos_usuario_candado` WRITE;
/*!40000 ALTER TABLE `permisos_usuario_candado` DISABLE KEYS */;
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
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `session_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `expires` int(11) unsigned NOT NULL,
  `data` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
  PRIMARY KEY (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
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
INSERT INTO `sessions` VALUES ('duqBzZLoY8p7seo4zxnU-Rt53vm77shd',1579630635,'{\"cookie\":{\"originalMaxAge\":null,\"expires\":null,\"httpOnly\":true,\"path\":\"/\"},\"passport\":{\"user\":{\"id\":1,\"nombre\":\"Eduardo\",\"apellido\":\"Tavarez\",\"telefono\":\"4424588186\",\"rol\":2,\"activo\":null}}}'),('o0qaVXRVM1sq7q3hgdJG_M8rE2zbnmuz',1579629300,'{\"cookie\":{\"originalMaxAge\":null,\"expires\":null,\"httpOnly\":true,\"path\":\"/\"},\"passport\":{\"user\":{\"id\":1,\"nombre\":\"Eduardo\",\"apellido\":\"Tavarez\",\"telefono\":\"4424588186\",\"rol\":2,\"activo\":null}}}');
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (2,'4424588186',2,'Eduardo','Tavarez','$2b$10$/oG.5KBf1DbZizywiTy8QO/aUc/sIKJBYHA2AZLhgz0s.0.XtGh2m',0),(4,'4434588186',2,'Eduardo','Quezada','$2b$10$acEtq3nzjg2jtCONHnTB9uZ94BS4DE6.M.x3iVCL0xJ1Oda3GXKIC',0),(5,'4444588186',2,'Eduardo','Tavarez','$2b$10$xfR9iSMT4ATKCUOOr8IfdeQEA4xRrwUg4.CbiOkoq/jWkj/YgTQ3.',0),(7,'4454588186',2,'Eduardo','Tavarez','$2b$10$cJvNWaGt1Z54s/L/WJwX8O4qE/BkeQMR96xCjSX7huSY/8kHEvcyu',0),(8,'4464588186',2,'Eduardo','Tavarez','$2b$10$cj6c3COvi5bmpE.TfieDge03grStW04qg.LKPWoNJEnbzJz8WiIt2',0),(9,'4474588186',2,'Eduardo','Tavarez','$2b$10$ULcnxNzYdtg8ud5vIV6be.3FmAuKgSsV1o6rS.0G4E3POX.6iRZTa',0),(11,'4484588186',2,'Eduardo','Tavarez','$2b$10$R3j8a1LoKw2XEDF6gv.aaerywZ5o5/DQpOXsnC378PwsbQpJ3qLeC',0),(13,'4494588186',2,'Eduardo','Tavarez','$2b$10$3l9BfzQp7F0vhDgDxW3al.LRqyfeQgAjavaXqQqEMSZ5IAhpYwBDi',0),(15,'4524588186',2,'Eduardo','Tavarez','$2b$10$Fck6DIkXYwjsd8yK22llmuMoIwcytvpWptZIpvC2vB9YvFhZuBCVm',0),(16,'4534588186',2,'Eduardo','Tavarez','$2b$10$JqahBM6GxZWF0TTWg4Vpne/cLOjdMUbN2jz8.kZp2cV4N.zUHcrMy',0),(17,'4544588186',2,'Eduardo','Tavarez','$2b$10$Qkcg8JkwN0AOVj6.oRVG7OxDX9Z8cCvC2b73jACAwMIo6nxMddmlm',0),(19,'4554588186',2,'Eduardo','Tavarez','$2b$10$i2/zJ9sMG/6Ul0Q5a4i49eoKIYdE9pkkKumuuM4uLud3VSPTS4nm6',0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-21  9:50:06
