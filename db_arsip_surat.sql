-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 12 Apr 2018 pada 09.54
-- Versi Server: 10.1.29-MariaDB
-- PHP Version: 7.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_arsip_surat`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_disposisi`
--

CREATE TABLE `t_disposisi` (
  `no_surat` varchar(10) NOT NULL,
  `tanggal_surat` date NOT NULL,
  `perihal` varchar(100) NOT NULL,
  `tujuan` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_disposisi`
--

INSERT INTO `t_disposisi` (`no_surat`, `tanggal_surat`, `perihal`, `tujuan`, `deskripsi`) VALUES
('1', '2018-04-12', 'a', '1', '1'),
('2', '2018-04-12', 'Peminjaman Mobil TNI', 'A', 'Untuk melakukan pengantaran');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_jenis_surat`
--

CREATE TABLE `t_jenis_surat` (
  `id_jenis_surat` varchar(10) NOT NULL,
  `jenis_surat` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_jenis_surat`
--

INSERT INTO `t_jenis_surat` (`id_jenis_surat`, `jenis_surat`) VALUES
('NRS', 'NONRESMI'),
('PRI', 'PRIBADI'),
('RSM', 'RESMI');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_login`
--

CREATE TABLE `t_login` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `level` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_login`
--

INSERT INTO `t_login` (`username`, `password`, `level`) VALUES
('admin', 'admin', 'Admin'),
('fauzan', 'fauzan', 'User');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_suratkeluar`
--

CREATE TABLE `t_suratkeluar` (
  `no_surat` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `perihal` varchar(100) NOT NULL,
  `id_jenis_surat` varchar(10) NOT NULL,
  `pengirim` varchar(100) NOT NULL,
  `tujuan` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_suratkeluar`
--

INSERT INTO `t_suratkeluar` (`no_surat`, `tanggal`, `perihal`, `id_jenis_surat`, `pengirim`, `tujuan`, `deskripsi`) VALUES
('1', '2018-04-12', 'Menghadiri Acara Pembukaan Stadion GBLA', 'RSM', 'Wali Kota Bandung', 'PT.PBB', 'aaa');

-- --------------------------------------------------------

--
-- Struktur dari tabel `t_suratmasuk`
--

CREATE TABLE `t_suratmasuk` (
  `no_surat` varchar(10) NOT NULL,
  `tanggal_surat` date NOT NULL,
  `tanggal_diterima` date NOT NULL,
  `perihal` varchar(100) NOT NULL,
  `id_jenis_surat` varchar(10) NOT NULL,
  `pengirim` varchar(100) NOT NULL,
  `tujuan` varchar(100) NOT NULL,
  `deskripsi` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `t_suratmasuk`
--

INSERT INTO `t_suratmasuk` (`no_surat`, `tanggal_surat`, `tanggal_diterima`, `perihal`, `id_jenis_surat`, `pengirim`, `tujuan`, `deskripsi`) VALUES
('1', '2018-04-12', '2018-04-12', '1', 'RSM', '1', '1', '1'),
('2', '2018-04-12', '2018-04-19', 'Peminjaman Mobil TNI', 'RSM', 'SDN Gentraa', 'KODIM SILIWANGI', 'Peminjaman Bus untuk Lomba Pramuka');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `t_disposisi`
--
ALTER TABLE `t_disposisi`
  ADD KEY `no_surat` (`no_surat`);

--
-- Indexes for table `t_jenis_surat`
--
ALTER TABLE `t_jenis_surat`
  ADD PRIMARY KEY (`id_jenis_surat`);

--
-- Indexes for table `t_login`
--
ALTER TABLE `t_login`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `t_suratkeluar`
--
ALTER TABLE `t_suratkeluar`
  ADD PRIMARY KEY (`no_surat`),
  ADD KEY `id_jenis_surat` (`id_jenis_surat`);

--
-- Indexes for table `t_suratmasuk`
--
ALTER TABLE `t_suratmasuk`
  ADD PRIMARY KEY (`no_surat`),
  ADD KEY `id_jenis_surat` (`id_jenis_surat`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `t_disposisi`
--
ALTER TABLE `t_disposisi`
  ADD CONSTRAINT `t_disposisi_ibfk_1` FOREIGN KEY (`no_surat`) REFERENCES `t_suratmasuk` (`no_surat`);

--
-- Ketidakleluasaan untuk tabel `t_suratkeluar`
--
ALTER TABLE `t_suratkeluar`
  ADD CONSTRAINT `t_suratkeluar_ibfk_1` FOREIGN KEY (`id_jenis_surat`) REFERENCES `t_jenis_surat` (`id_jenis_surat`);

--
-- Ketidakleluasaan untuk tabel `t_suratmasuk`
--
ALTER TABLE `t_suratmasuk`
  ADD CONSTRAINT `t_suratmasuk_ibfk_1` FOREIGN KEY (`id_jenis_surat`) REFERENCES `t_jenis_surat` (`id_jenis_surat`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
