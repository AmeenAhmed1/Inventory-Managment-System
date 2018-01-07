-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 08, 2017 at 10:26 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `ID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Gender` varchar(50) NOT NULL,
  `Phone` varchar(50) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Company` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`ID`, `Name`, `Gender`, `Phone`, `Address`, `Email`, `Company`) VALUES
(2, 'mohammed', 'Male', '010000012201', 'Alexandria, Egypt', 'mohamed@example.example', 'Example2'),
(3, 'Girl', 'Female', '011230000001', 'Alexandria, Egypt', 'Femail@example.example', 'Example3'),
(4, 'Ameen', 'Male', '0100212', 'askdjl', 'ee@eml.com', 'askdj'),
(6, 'Moutaz', 'Male', '010201020', 'asdasd', 'Moutaz@yahoo.ed', 'Koftaa'),
(16, 'new11', 'Male', '23645645', 'aaa', 'new@kl.com', 'newCompany'),
(18, 'nnn', 'Femail', '865879', 'mn', 'nn', 'nn'),
(20, 'Alo', 'Male', '032165', 'America', 'Alo@ex.kk', 'new'),
(21, 'kk', 'Femail', '5365', 'test', 'kaskdj', 'kjasd'),
(22, 'lkjasd', 'Male', 'alksjd', 'aslkd', 'saass', 'asldkj'),
(24, 'Essa', 'Male', '2222522', 'Egypt', 'Essa@Gmail.com', 'EssaCompany'),
(26, 'Kosaaaa', 'Male', '0101010', 'Alexandria, Egypt', 'mohamed@example.example', 'Example2'),
(27, 'Ahmed', 'Male', '132256', 'aaaaa', 'Ahmed@da.com', 'aaaa'),
(28, 'Almanda', 'Femail', '22333213', 'asas', 'Almanda@gmail.com', 'asas'),
(29, 'bella', 'Femail', '526556', 'aasd', 'bella@gmail.com', 'asas'),
(30, 'lll', 'Femail', '44', 'll	', 'll', 'll'),
(31, 'aaaa', 'Femail', '65665', 'aaaa', 'aaaa@yahoo.ex', 'aaa');

-- --------------------------------------------------------

--
-- Table structure for table `exports`
--

CREATE TABLE `exports` (
  `ID` int(11) NOT NULL,
  `BarCode` varchar(50) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Customer` varchar(50) NOT NULL,
  `Date` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `exports`
--

INSERT INTO `exports` (`ID`, `BarCode`, `Quantity`, `Customer`, `Date`) VALUES
(1, '500', 20, 'Ameen', '2017-09-06'),
(2, '103', 1, 'Moutaz', '2017-09-07'),
(3, '600', 105, 'nnn', '2017-09-07'),
(4, '822', 355, 'new11', '2017-09-08');

-- --------------------------------------------------------

--
-- Table structure for table `imports`
--

CREATE TABLE `imports` (
  `ID` int(11) NOT NULL,
  `BarCode` varchar(50) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Customer` varchar(50) NOT NULL,
  `Date` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `imports`
--

INSERT INTO `imports` (`ID`, `BarCode`, `Quantity`, `Customer`, `Date`) VALUES
(1, '200', 50, 'Ameen', '2017-09-06'),
(2, '103', 12, 'nnn', '2017-09-06'),
(3, '500', 102, 'ccc', '2017-09-06'),
(4, '200', 6, 'Alo', '2017-09-06'),
(6, '800', 20, 'Alo', '2017-09-06'),
(7, '600', 200, 'new11', '2017-09-07'),
(8, '302', 109, 'Girl', '2017-09-07'),
(9, '822', 600, 'Ahmed', '2017-09-08');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `ID` int(11) NOT NULL,
  `BarCode` varchar(50) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`ID`, `BarCode`, `Quantity`) VALUES
(1, '200', 56),
(2, '103', 11),
(3, '500', 82),
(4, '800', 20),
(5, '600', 95),
(6, '302', 109),
(7, '822', 245);

-- --------------------------------------------------------

--
-- Table structure for table `userlogin`
--

CREATE TABLE `userlogin` (
  `ID` int(11) NOT NULL,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userlogin`
--

INSERT INTO `userlogin` (`ID`, `Username`, `Password`, `Date`) VALUES
(1, 'Ameen', 'Ameen', '2017-08-29 04:14:01'),
(2, 'Example', 'Example', '2017-08-29 04:14:01'),
(3, 'admin', 'admin', '2017-08-29 04:14:01');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `exports`
--
ALTER TABLE `exports`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `imports`
--
ALTER TABLE `imports`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `userlogin`
--
ALTER TABLE `userlogin`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT for table `exports`
--
ALTER TABLE `exports`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `imports`
--
ALTER TABLE `imports`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `userlogin`
--
ALTER TABLE `userlogin`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
