-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  lun. 13 jan. 2020 à 17:53
-- Version du serveur :  10.4.8-MariaDB
-- Version de PHP :  7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `agence`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `Id_Admin` int(11) NOT NULL,
  `Nom` varchar(40) NOT NULL,
  `Password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`Id_Admin`, `Nom`, `Password`) VALUES
(1, 'aymen', '000'),
(2, 'achraf', '1234');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id_client` int(11) NOT NULL,
  `id_commande` int(11) NOT NULL,
  `nom` varchar(40) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `Tel` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id_client`, `id_commande`, `nom`, `email`, `password`, `Tel`) VALUES
(1, 0, 'benjbara', 'aymenbjuni@gmail.com', '000', '0623198124'),
(2, 0, 'adam', 'bj@gmail.com', '123', '03365254'),
(4, 0, 'test', 'bachlor@gmail.com', 'aaa', '06542551152'),
(5, 0, 'bach', 's4', '111', '19176976499'),
(10, 0, 'test', 'test', 'test', 'test'),
(11, 0, 'bachelor', 'jee', '000', '0623198124'),
(12, 0, 'anas', 'anas@jjjjj.com', 'anas', '65695');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id_commande` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `id_Voyage` int(11) NOT NULL,
  `destination` varchar(40) NOT NULL,
  `type` varchar(40) NOT NULL,
  `date` varchar(40) NOT NULL,
  `duree` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `id_client`, `id_Voyage`, `destination`, `type`, `date`, `duree`) VALUES
(2, 1, 6, 'DUBAI PREMIUM 5 VERS EMIRATS', 'ASIE', '08-12-2019', 5),
(6, 6, 8, 'BALI : SEMINYAK ou KUTA & UBUD', 'ASIE', ' 30-11-2019', 10),
(8, 6, 8, 'BALI : SEMINYAK ou KUTA & UBUD', 'ASIE', ' 30-11-2019', 10),
(10, 1, 1, 'SEJOUR DE LUXE BANGKOK / PHUKET', 'ASIE', '29-11-2019', 12),
(11, 11, 1, 'SEJOUR DE LUXE BANGKOK / PHUKET', 'ASIE', '29-11-2019', 12),
(13, 1, 6, 'DUBAI PREMIUM 5 VERS EMIRATS', 'ASIE', '08-12-2019', 5),
(14, 1, 3, 'VOYAGE ISTAMBUL/ CAPPADOCIA (en groupe)', 'TURQUIE', '2019-10-30', 11);

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE `panier` (
  `id_panier` int(11) NOT NULL,
  `id_Voyage` int(11) NOT NULL,
  `id_client` int(11) NOT NULL,
  `destination` varchar(40) NOT NULL,
  `image` varchar(40) NOT NULL,
  `type` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`id_panier`, `id_Voyage`, `id_client`, `destination`, `image`, `type`) VALUES
(5, 8, 6, 'BALI : SEMINYAK ou KUTA & UBUD', './img/product07.jpg', 'ASIE'),
(16, 5, 11, 'PRAGUE PREMIUM 5 AVEC ARTISAN', './img/product10.jpg', 'EUROPE'),
(19, 1, 1, 'SEJOUR DE LUXE BANGKOK / PHUKET', './img/product01.jpg', 'ASIE'),
(21, 1, 12, 'SEJOUR DE LUXE BANGKOK / PHUKET', './img/product01.jpg', 'ASIE');

-- --------------------------------------------------------

--
-- Structure de la table `voyage`
--

CREATE TABLE `voyage` (
  `id_Voyage` int(11) NOT NULL,
  `destination` varchar(40) NOT NULL,
  `type` varchar(40) NOT NULL,
  `date` varchar(40) NOT NULL,
  `duree` int(11) NOT NULL,
  `prix` double NOT NULL,
  `image` varchar(40) NOT NULL,
  `Theme` varchar(40) NOT NULL,
  `hebergement` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `voyage`
--

INSERT INTO `voyage` (`id_Voyage`, `destination`, `type`, `date`, `duree`, `prix`, `image`, `Theme`, `hebergement`) VALUES
(1, 'SEJOUR DE LUXE BANGKOK / PHUKET', 'ASIE', '29-11-2019', 12, 12900, './img/product01.jpg', 'Accompagne', 'Hôtel 4* à Bangkok & Hôtel 4* à Phuket'),
(2, 'LALELI 3 AVEC LA TURKISH VERS ISTAMBUL', 'TURQUIE', '30-11-2019', 7, 4990, './img/product03.jpg', 'individuel', 'Hotel Avlonya 3* ou Abel 3*'),
(3, 'VOYAGE ISTAMBUL/ CAPPADOCIA (en groupe)', 'TURQUIE', '2019-10-30', 11, 9900, './img/product02.jpg', 'Accompagne', 'Hôtel 4* à istanbul'),
(4, 'TAKSIM 4* AVEC LA TURKISH VERS ISTAMBUL', 'TURQUIE', '06-12-2019', 9, 6890, './img/product04.jpg', 'Accompagne', 'HOTEL KONAK ou RIVA ou similaire'),
(5, 'PRAGUE PREMIUM 5 AVEC ARTISAN', 'EUROPE', '02-12-2019', 9, 3900, './img/product10.jpg', 'individuel', 'Hôtel 4*'),
(6, 'DUBAI PREMIUM 5 VERS EMIRATS', 'ASIE', '08-12-2019', 5, 15900, './img/product05.jpg', 'individuel', 'Hôtel 5*'),
(7, 'MALAISIE PREMIUM 6 AVEC ARTISAN', 'ASIE', '12-12-2019', 8, 9500, './img/product06.jpg', 'individuel', 'Citin Hotel Masjid Jamek '),
(8, 'BALI : SEMINYAK ou KUTA & UBUD', 'ASIE', ' 30-11-2019', 10, 11900, './img/product07.jpg', 'Accompagne', 'SENSE HOTEL SEMINYAK 4* ou SWISS'),
(9, 'ROME PREMIUM 6 AVEC ARTISAN', 'EUROPE', '28-11-2019', 6, 5200, './img/product08.jpeg', 'individuel', 'Hôtel MF 3* ou similaire'),
(14, 'fes', 'Turquie', '2020-01-17', 5, 5454, './img/product01.jpg', 'individuel', 'hotel');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`Id_Admin`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id_client`),
  ADD KEY `id_commande` (`id_commande`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_commande`);

--
-- Index pour la table `panier`
--
ALTER TABLE `panier`
  ADD PRIMARY KEY (`id_panier`);

--
-- Index pour la table `voyage`
--
ALTER TABLE `voyage`
  ADD PRIMARY KEY (`id_Voyage`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `admin`
--
ALTER TABLE `admin`
  MODIFY `Id_Admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id_client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id_commande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `panier`
--
ALTER TABLE `panier`
  MODIFY `id_panier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pour la table `voyage`
--
ALTER TABLE `voyage`
  MODIFY `id_Voyage` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
