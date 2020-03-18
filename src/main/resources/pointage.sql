-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           10.4.10-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Listage de la structure de la table pointage. card_rfid
DROP TABLE IF EXISTS `card_rfid`;
CREATE TABLE IF NOT EXISTS `card_rfid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employe_id` int(11) NOT NULL,
  `is_valide` varchar(2) NOT NULL,
  `maricule` varchar(50) DEFAULT NULL,
  `numero_carte` varchar(50) DEFAULT NULL,
  `identifiant_carte` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nay6s06bns4ybeoc7nhecxe7r` (`numero_carte`),
  UNIQUE KEY `UK_l8n4cugefqe8v0eeu8amtf5ut` (`identifiant_carte`),
  KEY `FK_jo2diwg7f2y4ube817ybe9bx4` (`employe_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.card_rfid : 2 rows
DELETE FROM `card_rfid`;
/*!40000 ALTER TABLE `card_rfid` DISABLE KEYS */;
INSERT INTO `card_rfid` (`id`, `employe_id`, `is_valide`, `maricule`, `numero_carte`, `identifiant_carte`) VALUES
	(1, 1, '1', '0101', '0101', '0101'),
	(2, 2, '1', '2', '0102', '0102');
/*!40000 ALTER TABLE `card_rfid` ENABLE KEYS */;

-- Listage de la structure de la table pointage. employee_pointage
DROP TABLE IF EXISTS `employee_pointage`;
CREATE TABLE IF NOT EXISTS `employee_pointage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employe_id` int(11) DEFAULT NULL,
  `date_service` date DEFAULT NULL,
  `numero_carte` varchar(50) DEFAULT NULL,
  `matricule_employe` varchar(50) DEFAULT NULL,
  `mois` int(11) DEFAULT NULL,
  `annees` int(11) DEFAULT NULL,
  `heur_debut_service` time DEFAULT NULL,
  `heur_fin_service` time DEFAULT NULL,
  `heure_sup_autorize` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5ubcnr4mvue7ly94od6ob2woe` (`employe_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.employee_pointage : 0 rows
DELETE FROM `employee_pointage`;
/*!40000 ALTER TABLE `employee_pointage` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_pointage` ENABLE KEYS */;

-- Listage de la structure de la table pointage. employes
DROP TABLE IF EXISTS `employes`;
CREATE TABLE IF NOT EXISTS `employes` (
  `id` int(11) NOT NULL,
  `marticule` varchar(50) DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `lieu_naissance` varchar(50) DEFAULT NULL,
  `profession` varchar(50) DEFAULT NULL,
  `service` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `adresse` varchar(50) DEFAULT NULL,
  `photo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cl1ftamyxxi32u0i8agn4ex4i` (`marticule`),
  UNIQUE KEY `UK_3v0uyo0bds0i1s553pjkiewvv` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.employes : 2 rows
DELETE FROM `employes`;
/*!40000 ALTER TABLE `employes` DISABLE KEYS */;
INSERT INTO `employes` (`id`, `marticule`, `nom`, `prenom`, `date_naissance`, `lieu_naissance`, `profession`, `service`, `email`, `adresse`, `photo`) VALUES
	(1, '01', 'Traore', 'Abdoulaye', '2020-03-13', NULL, NULL, 'dev', NULL, NULL, NULL),
	(2, '02', 'Tine', 'Saliou', '2020-03-13', NULL, NULL, 'Assist', NULL, NULL, NULL);
/*!40000 ALTER TABLE `employes` ENABLE KEYS */;

-- Listage de la structure de la table pointage. employe_groupe
DROP TABLE IF EXISTS `employe_groupe`;
CREATE TABLE IF NOT EXISTS `employe_groupe` (
  `employe_id` int(11) NOT NULL,
  `groupe_id` int(11) NOT NULL,
  PRIMARY KEY (`groupe_id`,`employe_id`),
  KEY `FK_4up2jqwhy5u05tfvnd9mlcrtk` (`employe_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.employe_groupe : 0 rows
DELETE FROM `employe_groupe`;
/*!40000 ALTER TABLE `employe_groupe` DISABLE KEYS */;
/*!40000 ALTER TABLE `employe_groupe` ENABLE KEYS */;

-- Listage de la structure de la table pointage. employe_sortie_pointage
DROP TABLE IF EXISTS `employe_sortie_pointage`;
CREATE TABLE IF NOT EXISTS `employe_sortie_pointage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employe_id` int(11) DEFAULT NULL,
  `jour` date DEFAULT NULL,
  `heure_sortie` time DEFAULT NULL,
  `heure_retour` time DEFAULT NULL,
  `numero_carte` varchar(50) DEFAULT NULL,
  `marticule_employe` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ox2ooujfs0gdxag7bgm2xr1f3` (`employe_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.employe_sortie_pointage : 2 rows
DELETE FROM `employe_sortie_pointage`;
/*!40000 ALTER TABLE `employe_sortie_pointage` DISABLE KEYS */;
INSERT INTO `employe_sortie_pointage` (`id`, `employe_id`, `jour`, `heure_sortie`, `heure_retour`, `numero_carte`, `marticule_employe`) VALUES
	(1, 1, NULL, NULL, NULL, NULL, '1'),
	(2, 1, NULL, NULL, NULL, NULL, '2');
/*!40000 ALTER TABLE `employe_sortie_pointage` ENABLE KEYS */;

-- Listage de la structure de la table pointage. groupe
DROP TABLE IF EXISTS `groupe`;
CREATE TABLE IF NOT EXISTS `groupe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3gdppnywpdr0jqbrjddp810br` (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.groupe : 0 rows
DELETE FROM `groupe`;
/*!40000 ALTER TABLE `groupe` DISABLE KEYS */;
/*!40000 ALTER TABLE `groupe` ENABLE KEYS */;

-- Listage de la structure de la table pointage. pointage_param
DROP TABLE IF EXISTS `pointage_param`;
CREATE TABLE IF NOT EXISTS `pointage_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `heure_debut_service` time DEFAULT NULL,
  `heure_fin_service` time DEFAULT NULL,
  `nombre_heure_travaille` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.pointage_param : 0 rows
DELETE FROM `pointage_param`;
/*!40000 ALTER TABLE `pointage_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `pointage_param` ENABLE KEYS */;

-- Listage de la structure de la table pointage. role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.role : 2 rows
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'user'),
	(2, 'admin');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Listage de la structure de la table pointage. services
DROP TABLE IF EXISTS `services`;
CREATE TABLE IF NOT EXISTS `services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.services : 0 rows
DELETE FROM `services`;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
/*!40000 ALTER TABLE `services` ENABLE KEYS */;

-- Listage de la structure de la table pointage. users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `login` varchar(50) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `isvalide` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_krvotbtiqhudlkamvlpaqus0t` (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Listage des données de la table pointage.users : 2 rows
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `role_id`, `nom`, `prenom`, `email`, `login`, `password`, `isvalide`) VALUES
	(1, 2, 'Traore', 'Abdoulaye', 'at@at.com', 'at', '$2a$10$KRBmFTxlwlbAYd.Eq1aDUeE8rAgJqjr/x5eUHhTZK9QFQrxRq1fTm', b'1'),
	(2, 1, 'mali', 'mali', 'mali@mali.com', 'mali', '$2a$10$4gZs8qB4ddR6Nz4SVS/tL./Rp537jGw3muNfYBUWfqaRrpIPmBQpq', b'1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
