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
DROP TABLE IF EXISTS `tb_anggota`;
CREATE TABLE IF NOT EXISTS `tb_anggota` (
  `id_anggota` int NOT NULL AUTO_INCREMENT,
  `level_anggota` varchar(50) DEFAULT NULL,
  `nama_anggota` varchar(50) DEFAULT NULL,
  `npm` int DEFAULT NULL,
  `jurusan` varchar(50) DEFAULT NULL,
  `semester` varchar(50) DEFAULT NULL,
  `alamat` text,
  `no_hp` varchar(50) DEFAULT NULL,
  `tempat_lahir` varchar(50) DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `foto` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_anggota`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 ;

-- Dumping data for table db_kas.tb_anggota: ~6 rows (approximately)
DELETE FROM `tb_anggota`;
INSERT INTO `tb_anggota` (`id_anggota`, `level_anggota`, `nama_anggota`, `npm`, `jurusan`, `semester`, `alamat`, `no_hp`, `tempat_lahir`, `tanggal_lahir`, `foto`, `username`, `password`) VALUES
	(1, '1', 'admin', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'admin', '21232f297a57a5a743894a0e4a801fc3'),
	(2, '2', 'Dadang', 1, 'Informatika', '1', 'jl angkreng', '62838189571414', 'Bandung', '2023-06-15', 'anggota-1687235387.jpg', NULL, NULL),
	(3, '2', 'James', 111111, 'Hukum', '1', 'JL sohot', '6283818957839', 'Solo', '2023-06-15', 'anggota-1687235341.jpg', NULL, NULL),
	(27, '1', 'd e', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'd e', 'd2f2297d6e829cd3493aa7de4416a18f'),
	(32, '2', 'Nama Anggota', 1314143455, 'Jurusan', '1', 'Alamat e', '6283818957839', 'Tempat Lahir', '2023-06-06', 'anggota-1687235687.jpg', '', ''),
	(33, '1', 'user', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'user', 'ee11cbb19052e40b07aac0ca060c23ee');

-- Dumping structure for table db_kas.tb_penarikan
DROP TABLE IF EXISTS `tb_penarikan`;
CREATE TABLE IF NOT EXISTS `tb_penarikan` (
  `id_penarikan` int NOT NULL AUTO_INCREMENT,
  `jumlah_penarikan` int DEFAULT NULL,
  `id_anggota` int DEFAULT NULL,
  `tanggal_penarikan` date DEFAULT NULL,
  PRIMARY KEY (`id_penarikan`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ;

-- Dumping data for table db_kas.tb_penarikan: ~1 rows (approximately)
DELETE FROM `tb_penarikan`;
INSERT INTO `tb_penarikan` (`id_penarikan`, `jumlah_penarikan`, `id_anggota`, `tanggal_penarikan`) VALUES
	(2, 2000, 2, '2023-06-13');

-- Dumping structure for table db_kas.tb_pengeluaran
DROP TABLE IF EXISTS `tb_pengeluaran`;
CREATE TABLE IF NOT EXISTS `tb_pengeluaran` (
  `id_pengeluaran` int NOT NULL AUTO_INCREMENT,
  `jumlah_pengeluaran` int NOT NULL DEFAULT '0',
  `keperluan` text,
  `tanggal_pengeluaran` date DEFAULT NULL,
  PRIMARY KEY (`id_pengeluaran`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 ;

-- Dumping data for table db_kas.tb_pengeluaran: ~2 rows (approximately)
DELETE FROM `tb_pengeluaran`;
INSERT INTO `tb_pengeluaran` (`id_pengeluaran`, `jumlah_pengeluaran`, `keperluan`, `tanggal_pengeluaran`) VALUES
	(2, 1000, 'beli sapu masjid e', '2023-06-20'),
	(3, 30000, 'BUAT JALAN', '2023-06-13');

-- Dumping structure for table db_kas.tb_transaksi
DROP TABLE IF EXISTS `tb_transaksi`;
CREATE TABLE IF NOT EXISTS `tb_transaksi` (
  `id_transaksi` int NOT NULL AUTO_INCREMENT,
  `id_anggota` int NOT NULL DEFAULT '0',
  `jumlah_kas` int NOT NULL DEFAULT '0',
  `jumlah_tabungan` int NOT NULL DEFAULT '0',
  `jumlah_transaksi` int NOT NULL DEFAULT '0',
  `tanggal_transaksi` date DEFAULT NULL,
  PRIMARY KEY (`id_transaksi`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 ;

-- Dumping data for table db_kas.tb_transaksi: ~2 rows (approximately)
DELETE FROM `tb_transaksi`;
INSERT INTO `tb_transaksi` (`id_transaksi`, `id_anggota`, `jumlah_kas`, `jumlah_tabungan`, `jumlah_transaksi`, `tanggal_transaksi`) VALUES
	(1, 2, 25000, 30000, 55000, '2023-06-13'),
	(2, 2, 25000, 7000, 32000, '2023-06-13');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
