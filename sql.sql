-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping structure for table db_kas.tb_anggota
CREATE TABLE IF NOT EXISTS `tb_anggota` (
  `id_anggota` int NOT NULL AUTO_INCREMENT,
  `level_anggota` varchar(50) DEFAULT NULL,
  `nama_anggota` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_anggota`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table db_kas.tb_anggota: ~3 rows (approximately)
DELETE FROM `tb_anggota`;
INSERT INTO `tb_anggota` (`id_anggota`, `level_anggota`, `nama_anggota`) VALUES
	(1, '1', 'admin'),
	(2, '2', 'Dadang'),
	(4, '2', 'f');

-- Dumping structure for table db_kas.tb_penarikan
CREATE TABLE IF NOT EXISTS `tb_penarikan` (
  `id_penarikan` int NOT NULL AUTO_INCREMENT,
  `jumlah_penarikan` int DEFAULT NULL,
  `id_anggota` int DEFAULT NULL,
  `tanggal_penarikan` date DEFAULT NULL,
  PRIMARY KEY (`id_penarikan`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table db_kas.tb_penarikan: ~1 rows (approximately)
DELETE FROM `tb_penarikan`;
INSERT INTO `tb_penarikan` (`id_penarikan`, `jumlah_penarikan`, `id_anggota`, `tanggal_penarikan`) VALUES
	(2, 2000, 2, '2023-06-13');

-- Dumping structure for table db_kas.tb_pengeluaran
CREATE TABLE IF NOT EXISTS `tb_pengeluaran` (
  `id_pengeluaran` int NOT NULL AUTO_INCREMENT,
  `jumlah_pengeluaran` int NOT NULL DEFAULT '0',
  `keperluan` text,
  `tanggal_pengeluaran` date DEFAULT NULL,
  PRIMARY KEY (`id_pengeluaran`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table db_kas.tb_pengeluaran: ~0 rows (approximately)
DELETE FROM `tb_pengeluaran`;

-- Dumping structure for table db_kas.tb_transaksi
CREATE TABLE IF NOT EXISTS `tb_transaksi` (
  `id_transaksi` int NOT NULL AUTO_INCREMENT,
  `id_anggota` int NOT NULL DEFAULT '0',
  `jumlah_kas` int NOT NULL DEFAULT '0',
  `jumlah_tabungan` int NOT NULL DEFAULT '0',
  `jumlah_transaksi` int NOT NULL DEFAULT '0',
  `tanggal_transaksi` date DEFAULT NULL,
  PRIMARY KEY (`id_transaksi`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table db_kas.tb_transaksi: ~2 rows (approximately)
DELETE FROM `tb_transaksi`;
INSERT INTO `tb_transaksi` (`id_transaksi`, `id_anggota`, `jumlah_kas`, `jumlah_tabungan`, `jumlah_transaksi`, `tanggal_transaksi`) VALUES
	(1, 2, 25000, 30000, 55000, '2023-06-13'),
	(2, 2, 25000, 0, 25000, '2023-06-13');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
