-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 07 juin 2026 à 18:18
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `centre_equestre`
--
CREATE DATABASE IF NOT EXISTS `centre_equestre` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `centre_equestre`;

DELIMITER $$
--
-- Procédures
--
DROP PROCEDURE IF EXISTS `sp_maj_montant_facture`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_maj_montant_facture` (IN `idFacture` INT)   BEGIN
    DECLARE prixLogement DECIMAL(10,2);
    DECLARE totalSupp DECIMAL(10,2);

    -- récupérer le prix du logement
    SELECT t.PrixTypeLogement
    INTO prixLogement
    FROM facture f
    JOIN type_logement t ON f.IdTypeLogement = t.IdTypeLogement
    WHERE f.IdFacture = idFacture;

    -- récupérer le total des suppléments via la fonction
    SET totalSupp = fn_total_supplements(idFacture);

    -- mise à jour de la facture
    UPDATE facture
    SET MontantTotal = prixLogement + totalSupp
    WHERE IdFacture = idFacture;
END$$

--
-- Fonctions
--
DROP FUNCTION IF EXISTS `fn_total_supplements`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_total_supplements` (`idFacture` INT) RETURNS DECIMAL(10,2) DETERMINISTIC BEGIN
    DECLARE total DECIMAL(10,2);

    SELECT COALESCE(SUM(PrixSupplement), 0)
    INTO total
    FROM supplement
    WHERE IdFacture = idFacture;

    RETURN total;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `aliment`
--

DROP TABLE IF EXISTS `aliment`;
CREATE TABLE `aliment` (
  `IdAliment` int(10) UNSIGNED NOT NULL,
  `NomAliment` varchar(50) NOT NULL,
  `IdCentre` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `box`
--

DROP TABLE IF EXISTS `box`;
CREATE TABLE `box` (
  `IdBox` int(10) UNSIGNED NOT NULL,
  `NumBox` int(11) NOT NULL,
  `DateAjoutBox` date NOT NULL DEFAULT curdate(),
  `IdCheval` int(10) UNSIGNED DEFAULT NULL,
  `IdTypeLogement` int(10) UNSIGNED NOT NULL,
  `IdCentre` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déclencheurs `box`
--
DROP TRIGGER IF EXISTS `trg_verif_cheval_centre_insert`;
DELIMITER $$
CREATE TRIGGER `trg_verif_cheval_centre_insert` BEFORE INSERT ON `box` FOR EACH ROW BEGIN
    DECLARE CentreCheval INT UNSIGNED;

    IF NEW.IdCheval IS NOT NULL THEN
        SELECT IdCentre
        INTO CentreCheval
        FROM cheval
        WHERE IdCheval = NEW.IdCheval;

        IF CentreCheval IS NULL THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Erreur : le cheval est inexistant.';
        END IF;

        IF CentreCheval <> NEW.IdCentre THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Erreur : le cheval appartient à un autre centre.';
        END IF;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `centre`
--

DROP TABLE IF EXISTS `centre`;
CREATE TABLE `centre` (
  `IdCentre` int(10) UNSIGNED NOT NULL,
  `NomCentre` varchar(80) NOT NULL,
  `AdresseCentre` varchar(255) NOT NULL,
  `CPCentre` varchar(10) DEFAULT NULL,
  `VilleCentre` varchar(255) DEFAULT NULL,
  `TelCentre` varchar(20) DEFAULT NULL,
  `DateAjoutCentre` date NOT NULL DEFAULT curdate(),
  `IdGerant` int(10) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `centre`
--

INSERT INTO `centre` (`IdCentre`, `NomCentre`, `AdresseCentre`, `CPCentre`, `VilleCentre`, `TelCentre`, `DateAjoutCentre`, `IdGerant`) VALUES
(1, 'Centre du Soleil', '40 route de Blagnac', '31700', 'Blagnac', '0561111111', '2026-06-07', NULL),
(2, 'Centre de Paris', '5 rue de Paris ', '75001', 'Paris', '0602040607', '2026-06-07', 1);

-- --------------------------------------------------------

--
-- Structure de la table `cheval`
--

DROP TABLE IF EXISTS `cheval`;
CREATE TABLE `cheval` (
  `IdCheval` int(10) UNSIGNED NOT NULL,
  `NomCheval` varchar(50) NOT NULL,
  `SexeCheval` enum('M','F') NOT NULL,
  `RaceCheval` varchar(255) NOT NULL,
  `DateAjoutCheval` date NOT NULL DEFAULT curdate(),
  `IdCentre` int(10) UNSIGNED NOT NULL,
  `IdClient` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `cheval`
--

INSERT INTO `cheval` (`IdCheval`, `NomCheval`, `SexeCheval`, `RaceCheval`, `DateAjoutCheval`, `IdCentre`, `IdClient`) VALUES
(1, 'Tonnerre', 'M', 'Pur-Sang', '2026-06-07', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `IdClient` int(10) UNSIGNED NOT NULL,
  `NomClient` varchar(50) NOT NULL,
  `PrenomClient` varchar(50) NOT NULL,
  `AdresseClient` varchar(255) NOT NULL,
  `CodePostalClient` varchar(10) NOT NULL,
  `VilleClient` varchar(255) DEFAULT NULL,
  `TelClient` varchar(20) DEFAULT NULL,
  `DateAjoutClient` date NOT NULL DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`IdClient`, `NomClient`, `PrenomClient`, `AdresseClient`, `CodePostalClient`, `VilleClient`, `TelClient`, `DateAjoutClient`) VALUES
(1, 'Durand', 'Marie', '12 rue des Ecuries', '31000', 'Toulouse', '0561000001', '2026-06-07'),
(2, 'Martin', 'Lucas', '5 chemin du Pre', '31200', 'Toulouse', '0561000002', '2026-06-07'),
(3, 'Costelet', 'Thomas', 'Adresse', '75001', 'Paris', '0601020304', '2026-06-07');

-- --------------------------------------------------------

--
-- Structure de la table `equipement`
--

DROP TABLE IF EXISTS `equipement`;
CREATE TABLE `equipement` (
  `IdEquipement` int(10) UNSIGNED NOT NULL,
  `LibelleEquipement` varchar(50) NOT NULL,
  `DateAjoutEquipement` date NOT NULL DEFAULT curdate(),
  `IdCentre` int(10) UNSIGNED NOT NULL,
  `IdClient` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

DROP TABLE IF EXISTS `facture`;
CREATE TABLE `facture` (
  `IdFacture` int(10) UNSIGNED NOT NULL,
  `DateBail` date NOT NULL,
  `MontantTotal` decimal(10,2) NOT NULL DEFAULT 0.00,
  `DateAjoutFacture` date NOT NULL DEFAULT curdate(),
  `IdClient` int(10) UNSIGNED NOT NULL,
  `IdCheval` int(10) UNSIGNED NOT NULL,
  `IdTypeLogement` int(10) UNSIGNED NOT NULL,
  `IdCentre` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `pature`
--

DROP TABLE IF EXISTS `pature`;
CREATE TABLE `pature` (
  `IdPature` int(10) UNSIGNED NOT NULL,
  `NomPature` varchar(50) NOT NULL,
  `TaillePature` decimal(10,2) NOT NULL,
  `DateAjoutPature` date NOT NULL DEFAULT curdate(),
  `IdCentre` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `payement`
--

DROP TABLE IF EXISTS `payement`;
CREATE TABLE `payement` (
  `IdPayement` int(10) UNSIGNED NOT NULL,
  `DatePayement` date NOT NULL,
  `DateEncaissementPayement` date DEFAULT NULL,
  `MontantPayement` decimal(10,2) NOT NULL,
  `IdFacture` int(10) UNSIGNED NOT NULL,
  `IdTypePayement` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `supplement`
--

DROP TABLE IF EXISTS `supplement`;
CREATE TABLE `supplement` (
  `IdSupplement` int(10) UNSIGNED NOT NULL,
  `NomSupplement` varchar(255) NOT NULL,
  `PrixSupplement` decimal(10,2) NOT NULL,
  `IdFacture` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `type_logement`
--

DROP TABLE IF EXISTS `type_logement`;
CREATE TABLE `type_logement` (
  `IdTypeLogement` int(10) UNSIGNED NOT NULL,
  `NomTypeLogement` varchar(50) NOT NULL,
  `TailleTypeLogement` decimal(10,2) NOT NULL,
  `PrixTypeLogement` decimal(10,2) NOT NULL,
  `DateAjoutTypeLogement` date NOT NULL DEFAULT curdate(),
  `IdCentre` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `type_payement`
--

DROP TABLE IF EXISTS `type_payement`;
CREATE TABLE `type_payement` (
  `IdTypePayement` int(10) UNSIGNED NOT NULL,
  `NomTypePayement` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `type_payement`
--

INSERT INTO `type_payement` (`IdTypePayement`, `NomTypePayement`) VALUES
(1, 'Especes'),
(2, 'Carte bancaire'),
(3, 'Virement'),
(4, 'Cheque');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE `utilisateur` (
  `IdUtilisateur` int(10) UNSIGNED NOT NULL,
  `PseudonymeUtilisateur` varchar(80) NOT NULL,
  `MailUtilisateur` varchar(120) NOT NULL,
  `MotDePasseUtilisateur` varchar(120) NOT NULL,
  `ActiviteUtilisateur` tinyint(1) NOT NULL DEFAULT 1,
  `DerniereConnexionUtilisateur` datetime DEFAULT NULL,
  `BloqueUtilisateur` tinyint(1) NOT NULL DEFAULT 0,
  `RolesUtilisateur` enum('Admin','Gerant','Client') NOT NULL,
  `ExpireUtilisateur` tinyint(1) NOT NULL DEFAULT 0,
  `PrenomUtilisateur` varchar(50) NOT NULL,
  `NomUtilisateur` varchar(50) NOT NULL,
  `SexeUtilisateur` enum('M','F','Autre') DEFAULT NULL,
  `AdresseUtilisateur` varchar(255) DEFAULT NULL,
  `CodePostalUtilisateur` varchar(10) DEFAULT NULL,
  `VilleUtilisateur` varchar(255) DEFAULT NULL,
  `DateCreationUtilisateur` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`IdUtilisateur`, `PseudonymeUtilisateur`, `MailUtilisateur`, `MotDePasseUtilisateur`, `ActiviteUtilisateur`, `DerniereConnexionUtilisateur`, `BloqueUtilisateur`, `RolesUtilisateur`, `ExpireUtilisateur`, `PrenomUtilisateur`, `NomUtilisateur`, `SexeUtilisateur`, `AdresseUtilisateur`, `CodePostalUtilisateur`, `VilleUtilisateur`, `DateCreationUtilisateur`) VALUES
(1, 'admin', 'admin@caballio.fr', '$2y$10$rUjSuNngt4984196g9y.0e4itgOew39TOCakv.2qyaB.LRsyiSlXu', 1, '2026-06-07 17:50:51', 0, 'Admin', 0, 'Admin', 'Caballio', NULL, NULL, NULL, NULL, '2026-06-07 17:50:44');

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `v_box_stats_centre`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `v_box_stats_centre`;
CREATE TABLE `v_box_stats_centre` (
`IdCentre` int(10) unsigned
,`NbBoxTotal` bigint(21)
,`NbBoxVides` decimal(22,0)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `v_facture_resume`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `v_facture_resume`;
CREATE TABLE `v_facture_resume` (
`IdFacture` int(10) unsigned
,`IdClient` int(10) unsigned
,`IdCheval` int(10) unsigned
,`IdTypeLogement` int(10) unsigned
,`IdCentre` int(10) unsigned
,`DateBail` date
,`DateAjoutFacture` date
,`MontantTotal` decimal(10,2)
,`PaiementTotal` decimal(32,2)
,`PaiementRestant` decimal(33,2)
);

-- --------------------------------------------------------

--
-- Doublure de structure pour la vue `v_total_restant_global`
-- (Voir ci-dessous la vue réelle)
--
DROP VIEW IF EXISTS `v_total_restant_global`;
CREATE TABLE `v_total_restant_global` (
`RestantTotalClients` decimal(55,2)
);

-- --------------------------------------------------------

--
-- Structure de la vue `v_box_stats_centre`
--
DROP TABLE IF EXISTS `v_box_stats_centre`;

DROP VIEW IF EXISTS `v_box_stats_centre`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_box_stats_centre`  AS SELECT `b`.`IdCentre` AS `IdCentre`, count(0) AS `NbBoxTotal`, sum(case when `b`.`IdCheval` is null then 1 else 0 end) AS `NbBoxVides` FROM `box` AS `b` GROUP BY `b`.`IdCentre` ;

-- --------------------------------------------------------

--
-- Structure de la vue `v_facture_resume`
--
DROP TABLE IF EXISTS `v_facture_resume`;

DROP VIEW IF EXISTS `v_facture_resume`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_facture_resume`  AS SELECT `f`.`IdFacture` AS `IdFacture`, `f`.`IdClient` AS `IdClient`, `f`.`IdCheval` AS `IdCheval`, `f`.`IdTypeLogement` AS `IdTypeLogement`, `f`.`IdCentre` AS `IdCentre`, `f`.`DateBail` AS `DateBail`, `f`.`DateAjoutFacture` AS `DateAjoutFacture`, `f`.`MontantTotal` AS `MontantTotal`, coalesce(sum(`p`.`MontantPayement`),0) AS `PaiementTotal`, `f`.`MontantTotal`- coalesce(sum(`p`.`MontantPayement`),0) AS `PaiementRestant` FROM (`facture` `f` left join `payement` `p` on(`p`.`IdFacture` = `f`.`IdFacture`)) GROUP BY `f`.`IdFacture`, `f`.`IdClient`, `f`.`IdCheval`, `f`.`IdTypeLogement`, `f`.`IdCentre`, `f`.`DateBail`, `f`.`DateAjoutFacture`, `f`.`MontantTotal` ;

-- --------------------------------------------------------

--
-- Structure de la vue `v_total_restant_global`
--
DROP TABLE IF EXISTS `v_total_restant_global`;

DROP VIEW IF EXISTS `v_total_restant_global`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_total_restant_global`  AS SELECT coalesce(sum(`v_facture_resume`.`PaiementRestant`),0) AS `RestantTotalClients` FROM `v_facture_resume` ;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `aliment`
--
ALTER TABLE `aliment`
  ADD PRIMARY KEY (`IdAliment`),
  ADD KEY `fk_aliment_centre` (`IdCentre`);

--
-- Index pour la table `box`
--
ALTER TABLE `box`
  ADD PRIMARY KEY (`IdBox`),
  ADD UNIQUE KEY `uq_box_numero_par_centre` (`IdCentre`,`NumBox`),
  ADD UNIQUE KEY `uq_box_par_cheval` (`IdCheval`),
  ADD KEY `fk_box_type_logement` (`IdTypeLogement`);

--
-- Index pour la table `centre`
--
ALTER TABLE `centre`
  ADD PRIMARY KEY (`IdCentre`),
  ADD KEY `fk_centre_gerant` (`IdGerant`);

--
-- Index pour la table `cheval`
--
ALTER TABLE `cheval`
  ADD PRIMARY KEY (`IdCheval`),
  ADD KEY `fk_cheval_centre` (`IdCentre`),
  ADD KEY `fk_cheval_client` (`IdClient`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`IdClient`);

--
-- Index pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD PRIMARY KEY (`IdEquipement`),
  ADD KEY `fk_equipement_centre` (`IdCentre`),
  ADD KEY `fk_equipement_client` (`IdClient`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`IdFacture`),
  ADD KEY `fk_facture_client` (`IdClient`),
  ADD KEY `fk_facture_cheval` (`IdCheval`),
  ADD KEY `fk_facture_type_logement` (`IdTypeLogement`),
  ADD KEY `fk_facture_centre` (`IdCentre`);

--
-- Index pour la table `pature`
--
ALTER TABLE `pature`
  ADD PRIMARY KEY (`IdPature`),
  ADD KEY `fk_pature_centre` (`IdCentre`);

--
-- Index pour la table `payement`
--
ALTER TABLE `payement`
  ADD PRIMARY KEY (`IdPayement`),
  ADD KEY `fk_payement_facture` (`IdFacture`),
  ADD KEY `fk_payement_type` (`IdTypePayement`);

--
-- Index pour la table `supplement`
--
ALTER TABLE `supplement`
  ADD PRIMARY KEY (`IdSupplement`),
  ADD KEY `fk_supplement_facture` (`IdFacture`);

--
-- Index pour la table `type_logement`
--
ALTER TABLE `type_logement`
  ADD PRIMARY KEY (`IdTypeLogement`),
  ADD KEY `fk_type_logement_centre` (`IdCentre`);

--
-- Index pour la table `type_payement`
--
ALTER TABLE `type_payement`
  ADD PRIMARY KEY (`IdTypePayement`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`IdUtilisateur`),
  ADD UNIQUE KEY `PseudonymeUtilisateur` (`PseudonymeUtilisateur`),
  ADD UNIQUE KEY `MailUtilisateur` (`MailUtilisateur`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `aliment`
--
ALTER TABLE `aliment`
  MODIFY `IdAliment` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `box`
--
ALTER TABLE `box`
  MODIFY `IdBox` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `centre`
--
ALTER TABLE `centre`
  MODIFY `IdCentre` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `cheval`
--
ALTER TABLE `cheval`
  MODIFY `IdCheval` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `IdClient` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `equipement`
--
ALTER TABLE `equipement`
  MODIFY `IdEquipement` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
  MODIFY `IdFacture` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `pature`
--
ALTER TABLE `pature`
  MODIFY `IdPature` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `payement`
--
ALTER TABLE `payement`
  MODIFY `IdPayement` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `supplement`
--
ALTER TABLE `supplement`
  MODIFY `IdSupplement` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `type_logement`
--
ALTER TABLE `type_logement`
  MODIFY `IdTypeLogement` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `type_payement`
--
ALTER TABLE `type_payement`
  MODIFY `IdTypePayement` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `IdUtilisateur` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `aliment`
--
ALTER TABLE `aliment`
  ADD CONSTRAINT `fk_aliment_centre` FOREIGN KEY (`IdCentre`) REFERENCES `centre` (`IdCentre`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `box`
--
ALTER TABLE `box`
  ADD CONSTRAINT `fk_box_centre` FOREIGN KEY (`IdCentre`) REFERENCES `centre` (`IdCentre`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_box_cheval` FOREIGN KEY (`IdCheval`) REFERENCES `cheval` (`IdCheval`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_box_type_logement` FOREIGN KEY (`IdTypeLogement`) REFERENCES `type_logement` (`IdTypeLogement`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `centre`
--
ALTER TABLE `centre`
  ADD CONSTRAINT `fk_centre_gerant` FOREIGN KEY (`IdGerant`) REFERENCES `utilisateur` (`IdUtilisateur`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `cheval`
--
ALTER TABLE `cheval`
  ADD CONSTRAINT `fk_cheval_centre` FOREIGN KEY (`IdCentre`) REFERENCES `centre` (`IdCentre`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_cheval_client` FOREIGN KEY (`IdClient`) REFERENCES `client` (`IdClient`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `equipement`
--
ALTER TABLE `equipement`
  ADD CONSTRAINT `fk_equipement_centre` FOREIGN KEY (`IdCentre`) REFERENCES `centre` (`IdCentre`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_equipement_client` FOREIGN KEY (`IdClient`) REFERENCES `client` (`IdClient`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `fk_facture_centre` FOREIGN KEY (`IdCentre`) REFERENCES `centre` (`IdCentre`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_facture_cheval` FOREIGN KEY (`IdCheval`) REFERENCES `cheval` (`IdCheval`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_facture_client` FOREIGN KEY (`IdClient`) REFERENCES `client` (`IdClient`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_facture_type_logement` FOREIGN KEY (`IdTypeLogement`) REFERENCES `type_logement` (`IdTypeLogement`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `pature`
--
ALTER TABLE `pature`
  ADD CONSTRAINT `fk_pature_centre` FOREIGN KEY (`IdCentre`) REFERENCES `centre` (`IdCentre`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `payement`
--
ALTER TABLE `payement`
  ADD CONSTRAINT `fk_payement_facture` FOREIGN KEY (`IdFacture`) REFERENCES `facture` (`IdFacture`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_payement_type` FOREIGN KEY (`IdTypePayement`) REFERENCES `type_payement` (`IdTypePayement`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `supplement`
--
ALTER TABLE `supplement`
  ADD CONSTRAINT `fk_supplement_facture` FOREIGN KEY (`IdFacture`) REFERENCES `facture` (`IdFacture`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `type_logement`
--
ALTER TABLE `type_logement`
  ADD CONSTRAINT `fk_type_logement_centre` FOREIGN KEY (`IdCentre`) REFERENCES `centre` (`IdCentre`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
