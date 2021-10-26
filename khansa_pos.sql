-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 26, 2021 at 02:36 AM
-- Server version: 5.7.33
-- PHP Version: 7.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `khansa_pos`
--

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `member_id` int(11) NOT NULL,
  `member_name` varchar(64) NOT NULL,
  `member_address` varchar(128) NOT NULL,
  `member_phone` varchar(64) NOT NULL,
  `member_created` datetime NOT NULL,
  `member_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`member_id`, `member_name`, `member_address`, `member_phone`, `member_created`, `member_update`) VALUES
(1, 'Wuriyani Hidayati', 'Karangjati Nomer 276 Rt.18 Rw.041 Sinduadi Mlati Sleman Yogyakarta Indonesia', '08327723723200', '2021-10-25 11:49:41', '2021-10-25 11:31:32'),
(2, 'Retno Wahyuni Sutotok', 'Karangjati Yogyakarta Indonesia', '034343408194', '2021-10-25 11:52:24', '2021-10-25 11:30:39'),
(3, 'Heru atmaja Kartosuro', 'Kaliadem Pringgading Kadipaten Wetan Solo Jawa Tengah', '09346775884', '2021-10-25 17:50:36', '2021-10-25 10:50:36');

-- --------------------------------------------------------

--
-- Table structure for table `supliers`
--

CREATE TABLE `supliers` (
  `suplier_id` int(11) NOT NULL,
  `suplier_name` varchar(64) NOT NULL,
  `suplier_address` varchar(128) NOT NULL,
  `suplier_phone` varchar(64) NOT NULL,
  `suplier_created` datetime NOT NULL,
  `suplier_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supliers`
--

INSERT INTO `supliers` (`suplier_id`, `suplier_name`, `suplier_address`, `suplier_phone`, `suplier_created`, `suplier_update`) VALUES
(1, 'Toko Eddy', 'Sedan Sariharjo Ngaglik Sleman DIY', '08374374734', '2021-10-25 13:31:48', '2021-10-25 13:31:48'),
(2, 'New Modern 1', 'Kotagede Yogyakarta Indonesia', '028328382300', '2021-10-25 20:34:49', '2021-10-25 13:34:49');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(64) NOT NULL,
  `user_address` varchar(128) NOT NULL,
  `user_phone` varchar(64) NOT NULL,
  `user_password` varchar(256) NOT NULL,
  `user_level` varchar(16) NOT NULL,
  `date_created` datetime NOT NULL,
  `date_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `user_address`, `user_phone`, `user_password`, `user_level`, `date_created`, `date_update`) VALUES
(2, 'Rohmadi C', 'Yogyakarta Indonesia', '085868408685', 'qMCFpMR7Bslt5NA4qFLWMQ==', 'Admin', '2021-09-21 07:23:15', '2021-10-05 02:01:29'),
(3, 'Rohmadi Cahyono', 'Karangjati no.276 RT18/41 Sinduadi Mlati Sleman Yogyakarta', '081229492567', 'NxJIy3/5Ld0uF0WHCf4Q0Q==', 'kasir', '2021-09-21 03:25:22', '2021-10-04 02:22:53'),
(4, 'Ria Rahayu', 'KarangJati No.1234 yogyakarta', '0856665544', 'pkRbn4vF4pBVTOkeNgcNPQ==', 'Kasir', '2021-10-11 19:55:43', '2021-10-11 12:55:43'),
(5, 'Khansa Nadhifa Afaf', 'Sambilegi Soloraya serunen jawa timur indonesua', '054333332232', 'hsaD//vpacwEerAWULrJmA==', 'Kasir', '2021-10-11 20:08:05', '2021-10-11 13:08:05'),
(6, 'Nafisa Safa Anindya', 'Kedungpane kealasan Jawa Barat eropa', '09643332322', 'd5ahabiuEHJV2UOgWMgAkg==', 'Admin', '2021-10-11 20:09:13', '2021-10-11 13:09:13'),
(7, 'Atina Rahmatika ', 'Karangjati wetan no.125 yogyakarta', '98776555', 'qMCFpMR7Bslt5NA4qFLWMQ==', 'Admin', '2021-10-11 20:31:33', '2021-10-11 13:31:33'),
(8, 'Wuriyani Hidayati', 'Rejanglebong Jawa Barat', '0983344755', 'qMCFpMR7Bslt5NA4qFLWMQ==', 'Admin', '2021-10-12 19:09:57', '2021-10-12 12:09:56'),
(9, 'bambang', 'jatirejo ', '0823232', 'qMCFpMR7Bslt5NA4qFLWMQ==', 'Kasir', '2021-10-12 20:47:42', '2021-10-12 13:47:41'),
(10, 'Edward Sitomurang', 'Palopo sulawesi selatan Kota palu', '077475334', 'qMCFpMR7Bslt5NA4qFLWMQ==', 'Kasir', '2021-10-25 10:49:38', '2021-10-25 03:49:38');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`member_id`);

--
-- Indexes for table `supliers`
--
ALTER TABLE `supliers`
  ADD PRIMARY KEY (`suplier_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `member_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `supliers`
--
ALTER TABLE `supliers`
  MODIFY `suplier_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
