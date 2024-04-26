-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2024 at 07:50 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafe`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addMaterial` (IN `id` BIGINT, IN `size` VARCHAR(255), IN `quantity` INT)   BEGIN
    DROP TABLE IF EXISTS TempTable;
    CREATE TEMPORARY TABLE TempTable (
        id INT,
        quantity DOUBLE
    );

    INSERT INTO TempTable
    SELECT ma.id, re.quantity
    FROM recipe re JOIN material ma ON re.material_id = ma.id
    WHERE re.product_id = id AND re.size = size;

    UPDATE material
    SET remain = remain + (SELECT TempTable.quantity FROM TempTable WHERE TempTable.id = material.id) * quantity * 0.001
    WHERE material.id IN (SELECT TempTable.id FROM TempTable);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `subMaterial` (IN `id` BIGINT, IN `size` VARCHAR(255), IN `quantity` INT)   BEGIN
    DROP TABLE IF EXISTS TempTable;
    CREATE TEMPORARY TABLE TempTable (
        id INT,
        quantity DOUBLE
    );

    INSERT INTO TempTable
    SELECT ma.id, re.quantity
    FROM recipe re JOIN material ma ON re.material_id = ma.id
    WHERE re.product_id = id AND re.size = size;

    UPDATE material
    SET remain = remain - (SELECT TempTable.quantity FROM TempTable WHERE TempTable.id = material.id) * quantity * 0.001
    WHERE material.id IN (SELECT TempTable.id FROM TempTable);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `staff_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `staff_id`) VALUES
(1, 'admin', 'first$2a$12$a0qkUZYDrS3jA..bHc.NQe1EL9mtrI9cbs/QN5JdHDv8QQq2BSlu6', 1),
(2, 'longbott', 'first$2a$12$/7d6bBM2mhTtEqSqGXUxz.9ZNTQENFLasGNLibfDJ/UUqIcjw5qb.', 2),
(3, 'ngminhthuan', 'first$2a$12$90R6qSbOd4mDtlFVNqMAaOBvn092WZgPlBUHJvoYEqiEelE6zGbzm', 3),
(4, 'vminhthuan', 'first$2a$12$ZvUQ7fdYIKZCFIM2Q0kA0eP1nXafaQ38QnLLTySHV2beWuR.7PXey', 4),
(5, 'ducanh', 'first$2a$12$UO7S87UDtPc3zRlon23nI.Qf1mbcx8DYrrjOu5fg1LJ5Fxbvwcude', 5);

-- --------------------------------------------------------

--
-- Table structure for table `allowance`
--

CREATE TABLE `allowance` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `allowance_amount` double DEFAULT NULL,
  `allowance_type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `allowance`
--

INSERT INTO `allowance` (`id`, `name`, `allowance_amount`, `allowance_type`) VALUES
(1, 'Tiền gửi xe', 5000, 0),
(2, 'Tip', 200000, 1),
(3, 'Thưởng', 500000, 1),
(4, 'aaaaa', 50000, 1),
(5, 'ffff', 33, 1),
(6, 'k', 8, 0),
(7, 'kk', 8, 0);

-- --------------------------------------------------------

--
-- Table structure for table `decentralization`
--

CREATE TABLE `decentralization` (
  `role_id` bigint(20) NOT NULL,
  `module_id` bigint(20) NOT NULL,
  `function_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `decentralization`
--

INSERT INTO `decentralization` (`role_id`, `module_id`, `function_id`) VALUES
(0, 1, 1),
(0, 2, 1),
(0, 2, 2),
(0, 2, 3),
(0, 2, 4),
(0, 2, 6),
(0, 3, 1),
(0, 4, 1),
(0, 5, 1),
(0, 6, 1),
(0, 6, 2),
(0, 6, 3),
(0, 6, 5),
(0, 7, 1),
(0, 7, 6),
(0, 8, 1),
(0, 8, 2),
(0, 8, 5),
(0, 8, 6),
(0, 9, 1),
(0, 9, 2),
(0, 9, 5),
(0, 9, 6),
(0, 10, 1),
(0, 10, 2),
(0, 10, 3),
(0, 10, 4),
(0, 10, 5),
(0, 11, 1),
(0, 11, 2),
(0, 11, 3),
(0, 11, 4),
(0, 11, 5),
(0, 12, 1),
(0, 12, 2),
(0, 12, 3),
(0, 12, 4),
(0, 12, 5),
(0, 13, 1),
(0, 13, 3),
(0, 14, 1),
(0, 14, 2),
(0, 15, 1),
(0, 15, 2),
(0, 15, 3),
(0, 15, 4),
(0, 16, 1),
(0, 17, 1),
(0, 18, 1),
(0, 18, 2),
(0, 18, 3),
(0, 18, 4),
(0, 18, 5),
(0, 18, 6),
(0, 19, 1),
(0, 19, 2),
(0, 19, 4),
(0, 19, 6),
(1, 14, 1),
(1, 14, 2),
(1, 15, 1),
(1, 15, 2),
(1, 15, 3),
(1, 15, 4),
(2, 1, 1),
(2, 2, 1),
(2, 2, 2),
(2, 2, 3),
(2, 2, 4),
(2, 2, 6),
(2, 3, 1),
(2, 4, 1),
(2, 5, 1),
(2, 6, 1),
(2, 6, 2),
(2, 6, 3),
(2, 6, 5),
(2, 7, 1),
(2, 7, 6),
(2, 8, 1),
(2, 8, 2),
(2, 8, 5),
(2, 8, 6),
(2, 9, 1),
(2, 9, 2),
(2, 9, 5),
(2, 9, 6),
(2, 10, 1),
(2, 10, 2),
(2, 10, 3),
(2, 10, 4),
(2, 10, 5),
(2, 11, 1),
(2, 11, 2),
(2, 11, 3),
(2, 11, 4),
(2, 11, 5),
(2, 12, 1),
(2, 12, 2),
(2, 12, 3),
(2, 12, 4),
(2, 12, 5),
(2, 13, 1),
(2, 13, 2),
(2, 13, 3),
(2, 13, 4),
(2, 16, 1),
(2, 17, 1),
(2, 18, 1),
(2, 18, 2),
(2, 18, 3),
(2, 18, 4),
(2, 18, 5),
(2, 18, 6),
(2, 19, 1),
(2, 19, 2),
(2, 19, 4),
(2, 19, 6),
(3, 2, 1),
(3, 2, 2),
(3, 2, 3),
(3, 2, 4),
(3, 2, 6),
(3, 7, 1),
(3, 7, 6),
(3, 8, 1),
(3, 8, 2),
(3, 8, 5),
(3, 8, 6),
(3, 9, 1),
(3, 9, 2),
(3, 9, 5),
(3, 9, 6),
(3, 11, 1),
(3, 11, 2),
(3, 11, 3),
(3, 11, 4),
(3, 11, 5),
(3, 16, 1),
(3, 17, 1),
(4, 1, 1),
(4, 2, 1),
(4, 10, 1),
(4, 16, 1),
(4, 17, 1);

-- --------------------------------------------------------

--
-- Table structure for table `deduction`
--

CREATE TABLE `deduction` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `deduction_amount` double DEFAULT NULL,
  `deduction_type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `deduction`
--

INSERT INTO `deduction` (`id`, `name`, `deduction_amount`, `deduction_type`) VALUES
(1, 'Đi muộn', 30000, 0),
(2, 'Về sớm', 30000, 1),
(3, 'Nghỉ làm không phép', 30000, 3),
(4, 'Tiền mặc định', 20000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `discount`
--

CREATE TABLE `discount` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `type` bit(1) NOT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `discount`
--

INSERT INTO `discount` (`id`, `name`, `start_date`, `end_date`, `type`, `status`) VALUES
(0, 'default', '1000-01-01', '1000-01-01', b'0', b'1'),
(1, 'Giỗ tổ 10/03', '2024-04-22', '2024-04-30', b'1', b'1'),
(2, 'Lễ 30/4', '2024-04-28', '2024-04-30', b'0', b'1'),
(3, 'k', '2024-04-23', '2024-04-26', b'1', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `discount_detail`
--

CREATE TABLE `discount_detail` (
  `discount_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `Size` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `percent` double DEFAULT NULL,
  `discountBill` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `discount_detail`
--

INSERT INTO `discount_detail` (`discount_id`, `product_id`, `Size`, `quantity`, `percent`, `discountBill`) VALUES
(1, 0, 'Không', 0, 5, 100000),
(1, 0, 'Không', 0, 10, 200000),
(2, 1, 'L', 1, 5, 0),
(2, 1, 'M', 1, 5, 0),
(2, 2, 'L', 1, 5, 0),
(2, 2, 'M', 1, 5, 0),
(3, 0, '0', 0, 20, 500000);

-- --------------------------------------------------------

--
-- Table structure for table `export_detail`
--

CREATE TABLE `export_detail` (
  `export_id` bigint(20) NOT NULL,
  `shipment_id` bigint(20) NOT NULL,
  `quantity` double DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `export_detail`
--

INSERT INTO `export_detail` (`export_id`, `shipment_id`, `quantity`, `reason`) VALUES
(1, 1, 30, 'Bán'),
(1, 2, 30, 'Bán'),
(1, 3, 30, 'Bán'),
(1, 4, 30, 'Bán'),
(1, 5, 30, 'Huỷ'),
(2, 1, 20, 'Bán'),
(2, 10, 3, 'Huỷ'),
(2, 11, 10, 'Huỷ'),
(3, 1, 10, 'Huỷ'),
(3, 7, 20, 'Huỷ'),
(3, 8, 20, 'Huỷ'),
(3, 9, 30, 'Huỷ'),
(3, 14, 10, 'Bán');

-- --------------------------------------------------------

--
-- Table structure for table `export_note`
--

CREATE TABLE `export_note` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `invoice_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `export_note`
--

INSERT INTO `export_note` (`id`, `staff_id`, `total`, `invoice_date`) VALUES
(1, 1, 0, '2024-02-07'),
(2, 4, 660000, '2024-04-02'),
(3, 2, 1800000, '2024-04-19');

-- --------------------------------------------------------

--
-- Table structure for table `function`
--

CREATE TABLE `function` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `function`
--

INSERT INTO `function` (`id`, `name`) VALUES
(1, 'view'),
(2, 'add'),
(3, 'edit'),
(4, 'remove'),
(5, 'excel'),
(6, 'pdf');

-- --------------------------------------------------------

--
-- Table structure for table `import_note`
--

CREATE TABLE `import_note` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `received_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `import_note`
--

INSERT INTO `import_note` (`id`, `staff_id`, `total`, `received_date`) VALUES
(1, 1, 0, '2024-02-06'),
(2, 1, 0, '2024-02-07'),
(3, 4, 60000, '2024-03-31'),
(4, 4, 120000, '2024-03-31'),
(5, 4, 260000, '2024-03-31'),
(6, 4, 20000, '2024-04-11'),
(7, 4, 400000, '2024-04-14'),
(8, 4, 20000, '2024-04-14'),
(9, 4, 400000, '2024-04-14'),
(10, 2, 960000, '2024-04-19'),
(11, 2, 800000, '2024-04-19'),
(12, 2, 60000, '2024-04-22');

-- --------------------------------------------------------

--
-- Table structure for table `leave_of_absence_form`
--

CREATE TABLE `leave_of_absence_form` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `date_off` date DEFAULT NULL,
  `shifts` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `leave_of_absence_form`
--

INSERT INTO `leave_of_absence_form` (`id`, `staff_id`, `date`, `date_off`, `shifts`, `reason`, `status`) VALUES
(1, 2, '2024-03-23', '2024-03-23', '1, 2', 'Nghỉ phép năm', 1),
(2, 2, '2024-03-25', '2024-03-25', '1, 2', 'Nghỉ ốm', 1);

-- --------------------------------------------------------

--
-- Table structure for table `material`
--

CREATE TABLE `material` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remain` double DEFAULT NULL,
  `min_remain` double DEFAULT NULL,
  `max_remain` double DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `sell` bit(1) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `remain_wearhouse` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `material`
--

INSERT INTO `material` (`id`, `name`, `remain`, `min_remain`, `max_remain`, `unit`, `unit_price`, `sell`, `deleted`, `remain_wearhouse`) VALUES
(3, 'Cà phê bột truyền thống', 40, 50, 100, 'kg', 20000, b'0', b'0', 32),
(4, 'Đường túi', 40, 50, 100, 'kg', 20000, b'0', b'0', 50),
(5, 'Bánh cookie', 40, 50, 100, 'cái', 20000, b'0', b'0', 30),
(6, 'Đá viên', 40, 50, 100, 'kg', 20000, b'0', b'0', 20),
(7, 'Sữa đặc', 40, 50, 100, 'lít', 20000, b'0', b'0', 31),
(8, 'Nước nóng', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(9, 'Syrup hạnh nhân', 40, 50, 100, 'lít', 20000, b'0', b'0', 10),
(10, 'Sữa tươi', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(11, 'Sữa Béo (NDC)', 40, 50, 100, 'lít', 20000, b'0', b'0', 3),
(12, 'Thạch Cà Phê', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(13, 'Milk foam', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(14, 'Bột chocolate trang trí', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(15, 'Sốt chocolate', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(16, 'Cà phê đen pha sẵn', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(18, 'Đường nước ', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(19, 'Trà đào pha sẵn ', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(20, 'Đào lát', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(21, 'Hạt sen', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(22, 'Củ năng', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(23, 'Syrup vải', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(24, 'Nước vải ngâm', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(25, 'Vải trái', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(26, 'Thạch vải ', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(27, 'Bột freeze mix', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(28, 'Whipping cream ', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(29, 'Bột cà phê espresso', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(30, 'Bột green tea mix mới ', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(31, 'Thạch trà xanh ', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(32, 'Bột trà xanh trang trí', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(33, 'Hỗn hợp sữa pha sẵn', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(34, 'Bột chocolate ', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(35, 'Sốt chocolate trang trí', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(36, 'Cà phê sữa pha sẵn', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(37, 'Thạch cà phê', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(38, 'Trà oolong pha sẵn ', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(39, 'Thạch Đào', 40, 50, 100, 'kg', 20000, b'0', b'0', 0),
(40, 'Syrup Đào', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(41, 'Syrup sả', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(42, 'Sốt caramel', 40, 50, 100, 'lít', 20000, b'0', b'0', 0),
(43, 'Bánh Chuối', 40, 50, 100, 'cái', 20000, b'1', b'0', 0),
(44, 'Bánh Su Kem', 40, 50, 100, 'cái', 20000, b'1', b'0', 0),
(45, 'Phô Mai Chanh Dây', 40, 50, 100, 'cái', 20000, b'0', b'0', 0),
(46, 'Phô Mai Trà Xanh', 40, 50, 100, 'cái', 20000, b'0', b'0', 0);

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`id`, `name`) VALUES
(1, 'Bán hàng'),
(2, 'Kho hàng'),
(3, 'Doanh thu'),
(4, 'Thống kê lương'),
(5, 'Thống kê nhân sự'),
(6, 'Giảm giá'),
(7, 'Hoá đơn'),
(8, 'Phiếu xuất'),
(9, 'Phiếu nhập'),
(10, 'Sản phẩm'),
(11, 'Nhà cung cấp'),
(12, 'Nhân viên'),
(13, 'Đơn nghỉ phép'),
(14, 'Tài khoản'),
(15, 'Phân quyền'),
(16, 'Thông tin'),
(17, 'Lịch làm việc'),
(18, 'Xếp ca làm'),
(19, 'Tính lương');

-- --------------------------------------------------------

--
-- Table structure for table `payroll`
--

CREATE TABLE `payroll` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `entry_date` date DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `total_salary` double DEFAULT NULL,
  `paid` double DEFAULT NULL,
  `debt` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payroll`
--

INSERT INTO `payroll` (`id`, `name`, `entry_date`, `month`, `year`, `total_salary`, `paid`, `debt`) VALUES
(1, 'Bảng lương 03/2024', '2024-04-26', 3, 2024, 43156730.76923077, 43156730.76923077, 0);

-- --------------------------------------------------------

--
-- Table structure for table `payroll_detail`
--

CREATE TABLE `payroll_detail` (
  `payroll_id` bigint(20) NOT NULL,
  `staff_id` bigint(20) NOT NULL,
  `hours_amount` double DEFAULT NULL,
  `allowance_amount` double DEFAULT NULL,
  `deduction_amount` double DEFAULT NULL,
  `bonus_amount` double DEFAULT NULL,
  `fine_amount` double DEFAULT NULL,
  `salary_amount` double DEFAULT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payroll_detail`
--

INSERT INTO `payroll_detail` (`payroll_id`, `staff_id`, `hours_amount`, `allowance_amount`, `deduction_amount`, `bonus_amount`, `fine_amount`, `salary_amount`, `status`) VALUES
(1, 2, 30, 145000, 110000, 50000, 20000, 8164230.769230769, b'1'),
(1, 3, 30, 150000, 50000, 0, 0, 8200000, b'1'),
(1, 4, 370.5, 155000, 80000, 0, 0, 9337500, b'1'),
(1, 5, 372, 155000, 20000, 0, 0, 9435000, b'1'),
(1, 6, 155, 155000, 20000, 0, 0, 4010000, b'1'),
(1, 7, 155, 155000, 20000, 0, 0, 4010000, b'1');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` varchar(255) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `capital_price` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `size`, `category`, `capital_price`, `price`, `image`, `deleted`) VALUES
(0, 'default', 'Không', '0', 0, 0, '0', b'1'),
(1, 'Phin Đen Đá', 'L', 'CÀ PHÊ PHIN', 39, 39000, 'SP01', b'0'),
(1, 'Phin Đen Đá', 'M', 'CÀ PHÊ PHIN', 35, 35000, 'SP01', b'0'),
(1, 'Phin Đen Đá', 'S', 'CÀ PHÊ PHIN', 29, 29000, 'SP01', b'0'),
(2, 'Phin Sữa Đá', 'L', 'CÀ PHÊ PHIN', 45, 45000, 'SP02', b'0'),
(2, 'Phin Sữa Đá', 'M', 'CÀ PHÊ PHIN', 39, 39000, 'SP02', b'0'),
(2, 'Phin Sữa Đá', 'S', 'CÀ PHÊ PHIN', 29, 29000, 'SP02', b'0'),
(3, 'Bạc Xỉu', 'L', 'CÀ PHÊ PHIN', 45, 45000, 'SP03', b'0'),
(3, 'Bạc Xỉu', 'M', 'CÀ PHÊ PHIN', 39, 39000, 'SP03', b'0'),
(3, 'Bạc Xỉu', 'S', 'CÀ PHÊ PHIN', 29, 29000, 'SP03', b'0'),
(4, 'Trà Sen Vàng', 'L', 'TRÀ', 65, 65000, 'SP04', b'0'),
(4, 'Trà Sen Vàng', 'M', 'TRÀ', 55, 55000, 'SP04', b'0'),
(4, 'Trà Sen Vàng', 'S', 'TRÀ', 45, 45000, 'SP04', b'0'),
(5, 'Trà Thạch Đào', 'L', 'TRÀ', 65, 65000, 'SP05', b'0'),
(5, 'Trà Thạch Đào', 'M', 'TRÀ', 55, 55000, 'SP05', b'0'),
(5, 'Trà Thạch Đào', 'S', 'TRÀ', 45, 45000, 'SP05', b'0'),
(6, 'Trà Thanh Đào', 'L', 'TRÀ', 65, 65000, 'SP06', b'0'),
(6, 'Trà Thanh Đào', 'M', 'TRÀ', 55, 55000, 'SP06', b'0'),
(6, 'Trà Thanh Đào', 'S', 'TRÀ', 45, 45000, 'SP06', b'0'),
(7, 'Trà Thạch Vãi', 'L', 'TRÀ', 65, 65000, 'SP07', b'0'),
(7, 'Trà Thạch Vãi', 'M', 'TRÀ', 55, 55000, 'SP07', b'0'),
(7, 'Trà Thạch Vãi', 'S', 'TRÀ', 45, 45000, 'SP07', b'0'),
(8, 'Bánh Chuối', 'Không', 'BÁNH', 29, 29000, 'SP08', b'0'),
(9, 'Bánh Su Kem', 'Không', 'BÁNH', 29, 29000, 'SP09', b'0'),
(10, 'Phô Mai Chanh Dây', 'Không', 'BÁNH', 29, 29000, 'SP10', b'0'),
(11, 'Phô Mai Trà Xanh', 'Không', 'BÁNH', 29, 29000, 'SP11', b'0'),
(12, 'PhinDi Hạnh Nhân', 'L', 'PHINDI', 55, 55000, 'SP12', b'0'),
(12, 'PhinDi Hạnh Nhân', 'M', 'PHINDI', 49, 49000, 'SP12', b'0'),
(12, 'PhinDi Hạnh Nhân', 'S', 'PHINDI', 45, 45000, 'SP12', b'0'),
(13, 'PhinDi Kem Sữa', 'L', 'PHINDI', 55, 55000, 'SP13', b'0'),
(13, 'PhinDi Kem Sữa', 'M', 'PHINDI', 49, 49000, 'SP13', b'0'),
(13, 'PhinDi Kem Sữa', 'S', 'PHINDI', 45, 45000, 'SP13', b'0'),
(14, 'PhinDi Choco', 'L', 'PHINDI', 55, 55000, 'SP14', b'0'),
(14, 'PhinDi Choco', 'M', 'PHINDI', 49, 49000, 'SP14', b'0'),
(14, 'PhinDi Choco', 'S', 'PHINDI', 45, 45000, 'SP14', b'0'),
(15, 'Freeze Trà Xanh', 'L', 'FREEZE ', 69, 69000, 'SP15', b'0'),
(15, 'Freeze Trà Xanh', 'M', 'FREEZE', 65, 65000, 'SP15', b'0'),
(15, 'Freeze Trà Xanh', 'S', 'FREEZE', 55, 55000, 'SP15', b'0'),
(16, 'Caramel Phin Freeze', 'L', 'FREEZE', 69, 69000, 'SP16', b'0'),
(16, 'Caramel Phin Freeze', 'M', 'FREEZE', 65, 65000, 'SP16', b'0'),
(16, 'Caramel Phin Freeze', 'S', 'FREEZE', 55, 55000, 'SP16', b'0'),
(17, 'Freeze Sô-Cô-La', 'L', 'FREEZE', 69, 69000, 'SP17', b'0'),
(17, 'Freeze Sô-Cô-La', 'M', 'FREEZE', 65, 65000, 'SP17', b'0'),
(17, 'Freeze Sô-Cô-La', 'S', 'FREEZE', 55, 55000, 'SP17', b'0'),
(18, 'Classic Phin Freeze', 'L', 'FREEZE', 69, 69000, 'SP18', b'0'),
(18, 'Classic Phin Freeze', 'M', 'FREEZE', 65, 65000, 'SP18', b'0'),
(18, 'Classic Phin Freeze', 'S', 'FREEZE', 55, 55000, 'SP18', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `total_discount` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `received` double DEFAULT NULL,
  `excess` double DEFAULT NULL,
  `discount_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`id`, `staff_id`, `invoice_date`, `total_price`, `total_discount`, `total`, `received`, `excess`, `discount_id`) VALUES
(1, 2, '2024-04-22', 145000, 7250, 137750, 140000, 2250, 1),
(2, 2, '2024-04-22', 145000, 7250, 137750, 140000, 2250, 1);

-- --------------------------------------------------------

--
-- Table structure for table `receipt_detail`
--

CREATE TABLE `receipt_detail` (
  `receipt_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `size` varchar(255) NOT NULL,
  `quantity` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `notice` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt_detail`
--

INSERT INTO `receipt_detail` (`receipt_id`, `product_id`, `size`, `quantity`, `price`, `notice`) VALUES
(1, 8, 'Không', 5, 145000, ' '),
(2, 8, 'Không', 5, 145000, ' ');

-- --------------------------------------------------------

--
-- Table structure for table `recipe`
--

CREATE TABLE `recipe` (
  `product_id` bigint(20) NOT NULL,
  `material_id` bigint(20) NOT NULL,
  `quantity` double DEFAULT NULL,
  `size` varchar(255) NOT NULL,
  `unit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recipe`
--

INSERT INTO `recipe` (`product_id`, `material_id`, `quantity`, `size`, `unit`) VALUES
(1, 6, 400, 'L', 'g'),
(1, 6, 260, 'M', 'g'),
(1, 6, 200, 'S', 'g'),
(1, 16, 60, 'L', 'ml'),
(1, 16, 50, 'M', 'ml'),
(1, 16, 40, 'S', 'ml'),
(1, 18, 25, 'L', 'g'),
(1, 18, 20, 'M', 'g'),
(1, 18, 15, 'S', 'g'),
(2, 6, 400, 'L', 'g'),
(2, 6, 260, 'M', 'g'),
(2, 6, 200, 'S', 'g'),
(2, 16, 30, 'L', 'ml'),
(2, 16, 25, 'M', 'ml'),
(2, 16, 50, 'S', 'ml'),
(2, 36, 75, 'L', 'ml'),
(2, 36, 65, 'M', 'ml'),
(2, 36, 50, 'S', 'ml'),
(3, 6, 400, 'L', 'g'),
(3, 6, 25, 'M', 'g'),
(3, 6, 200, 'S', 'g'),
(3, 7, 60, 'L', 'ml'),
(3, 7, 50, 'M', 'ml'),
(3, 7, 40, 'S', 'ml'),
(3, 16, 30, 'L', 'ml'),
(3, 16, 25, 'M', 'ml'),
(3, 16, 20, 'S', 'ml'),
(4, 6, 220, 'L', 'g'),
(4, 6, 200, 'M', 'g'),
(4, 6, 150, 'S', 'g'),
(4, 13, 75, 'L', 'ml'),
(4, 13, 65, 'M', 'ml'),
(4, 13, 50, 'S', 'ml'),
(4, 18, 45, 'L', 'g'),
(4, 18, 40, 'M', 'g'),
(4, 18, 30, 'S', 'g'),
(4, 21, 30, 'L', 'g'),
(4, 21, 25, 'M', 'g'),
(4, 21, 20, 'S', 'g'),
(4, 22, 25, 'L', 'g'),
(4, 22, 20, 'M', 'g'),
(4, 22, 15, 'S', 'g'),
(4, 38, 150, 'L', 'ml'),
(4, 38, 130, 'M', 'ml'),
(4, 38, 100, 'S', 'ml'),
(5, 6, 220, 'L', 'g'),
(5, 6, 200, 'M', 'g'),
(5, 6, 150, 'S', 'g'),
(5, 18, 45, 'L', 'g'),
(5, 18, 40, 'M', 'g'),
(5, 18, 30, 'S', 'g'),
(5, 19, 100, 'L', 'ml'),
(5, 19, 80, 'M', 'ml'),
(5, 19, 50, 'S', 'ml'),
(5, 20, 40, 'L', 'g'),
(5, 20, 40, 'M', 'g'),
(5, 20, 30, 'S', 'g'),
(5, 39, 75, 'L', 'g'),
(5, 39, 65, 'M', 'g'),
(5, 39, 50, 'S', 'g'),
(6, 6, 300, 'L', 'g'),
(6, 6, 280, 'M', 'g'),
(6, 6, 220, 'S', 'g'),
(6, 20, 40, 'L', 'g'),
(6, 20, 40, 'M', 'g'),
(6, 20, 30, 'S', 'g'),
(6, 40, 25, 'L', 'ml'),
(6, 40, 20, 'M', 'ml'),
(6, 40, 15, 'S', 'ml'),
(6, 41, 20, 'L', 'ml'),
(6, 41, 15, 'M', 'ml'),
(6, 41, 15, 'S', 'ml'),
(7, 6, 320, 'L', 'g'),
(7, 6, 300, 'M', 'g'),
(7, 6, 230, 'S', 'g'),
(7, 23, 30, 'L', 'ml'),
(7, 23, 25, 'M', 'ml'),
(7, 23, 20, 'S', 'ml'),
(7, 24, 15, 'L', 'ml'),
(7, 24, 10, 'M', 'ml'),
(7, 24, 10, 'S', 'ml'),
(7, 25, 5, 'L', 'trái'),
(7, 25, 4, 'M', 'trái'),
(7, 25, 3, 'S', 'trái'),
(7, 26, 30, 'L', 'g'),
(7, 26, 30, 'M', 'g'),
(7, 26, 30, 'S', 'g'),
(12, 6, 220, 'L', 'g'),
(12, 6, 180, 'M', 'g'),
(12, 6, 150, 'S', 'g'),
(12, 9, 20, 'L', 'ml'),
(12, 9, 15, 'M', 'ml'),
(12, 9, 10, 'S', 'ml'),
(12, 10, 80, 'L', 'ml'),
(12, 10, 70, 'M', 'ml'),
(12, 10, 50, 'S', 'ml'),
(12, 16, 30, 'L', 'ml'),
(12, 16, 25, 'M', 'ml'),
(12, 16, 20, 'S', 'ml'),
(12, 18, 15, 'L', 'g'),
(12, 18, 10, 'M', 'g'),
(12, 18, 10, 'S', 'g'),
(12, 37, 50, 'L', 'g'),
(12, 37, 40, 'M', 'g'),
(12, 37, 30, 'S', 'g'),
(13, 6, 200, 'L', 'g'),
(13, 6, 150, 'M', 'g'),
(13, 6, 120, 'S', 'g'),
(13, 7, 20, 'L', 'ml'),
(13, 7, 15, 'M', 'ml'),
(13, 7, 10, 'S', 'ml'),
(13, 10, 120, 'L', 'ml'),
(13, 10, 90, 'M', 'ml'),
(13, 10, 70, 'S', 'ml'),
(13, 13, 45, 'L', 'ml'),
(13, 13, 40, 'M', 'ml'),
(13, 13, 30, 'S', 'ml'),
(13, 16, 30, 'L', 'ml'),
(13, 16, 25, 'M', 'ml'),
(13, 16, 20, 'S', 'ml'),
(13, 37, 50, 'L', 'g'),
(13, 37, 40, 'M', 'g'),
(13, 37, 30, 'S', 'g'),
(14, 6, 270, 'L', 'g'),
(14, 6, 210, 'M', 'g'),
(14, 6, 170, 'S', 'g'),
(14, 7, 15, 'L', 'ml'),
(14, 7, 10, 'M', 'ml'),
(14, 7, 10, 'S', 'ml'),
(14, 10, 60, 'L', 'ml'),
(14, 10, 50, 'M', 'ml'),
(14, 10, 30, 'S', 'ml'),
(14, 11, 20, 'L', 'ml'),
(14, 11, 15, 'M', 'ml'),
(14, 11, 10, 'S', 'ml'),
(14, 15, 20, 'L', 'ml'),
(14, 15, 15, 'M', 'ml'),
(14, 15, 10, 'S', 'ml'),
(14, 16, 30, 'L', 'ml'),
(14, 16, 25, 'M', 'ml'),
(14, 16, 20, 'S', 'ml'),
(15, 6, 250, 'L', 'g'),
(15, 6, 230, 'M', 'g'),
(15, 6, 180, 'S', 'g'),
(15, 7, 25, 'L', 'ml'),
(15, 7, 20, 'M', 'ml'),
(15, 7, 15, 'S', 'ml'),
(15, 10, 55, 'L', 'ml'),
(15, 10, 50, 'M', 'ml'),
(15, 10, 40, 'S', 'ml'),
(15, 18, 25, 'L', 'g'),
(15, 18, 20, 'M', 'g'),
(15, 18, 15, 's', 'g'),
(15, 28, 35, 'L', 'g'),
(15, 28, 35, 'M', 'g'),
(15, 28, 30, 'S', 'g'),
(15, 30, 50, 'L', 'g'),
(15, 30, 45, 'M', 'g'),
(15, 30, 35, 'S', 'g'),
(15, 31, 150, 'L', 'g'),
(15, 31, 125, 'M', 'g'),
(15, 31, 100, 'S', 'g'),
(16, 6, 250, 'L', 'g'),
(16, 6, 230, 'M', 'g'),
(16, 6, 200, 'S', 'g'),
(16, 16, 45, 'L', 'ml'),
(16, 16, 40, 'M', 'ml'),
(16, 16, 30, 'S', 'ml'),
(16, 27, 55, 'L', 'g'),
(16, 27, 50, 'M', 'g'),
(16, 27, 40, 'S', 'g'),
(16, 37, 150, 'L', 'g'),
(16, 37, 125, 'M', 'g'),
(16, 37, 100, 'S', 'g'),
(16, 42, 25, 'L', 'ml'),
(16, 42, 20, 'M', 'ml'),
(16, 42, 15, 'S', 'ml'),
(17, 6, 250, 'L', 'g'),
(17, 6, 230, 'M', 'g'),
(17, 6, 180, 'S', 'g'),
(17, 7, 25, 'L', 'ml'),
(17, 7, 20, 'M', 'ml'),
(17, 7, 15, 'S', 'ml'),
(17, 10, 0, 'L', 'ml'),
(17, 10, 80, 'M', 'ml'),
(17, 10, 50, 'S', 'ml'),
(17, 27, 55, 'L', 'g'),
(17, 27, 50, 'M', 'g'),
(17, 27, 40, 'S', 'g'),
(17, 34, 18, 'L', 'g'),
(17, 34, 16, 'M', 'g'),
(17, 34, 12, 'S', 'g'),
(17, 37, 150, 'L', 'g'),
(17, 37, 125, 'M', 'g'),
(17, 37, 100, 'S', 'g'),
(18, 6, 250, 'L', 'g'),
(18, 6, 230, 'M', 'g'),
(18, 6, 180, 'S', 'g'),
(18, 7, 25, 'L', 'ml'),
(18, 7, 20, 'M', 'ml'),
(18, 7, 15, 'S', 'ml'),
(18, 10, 0, 'L', 'ml'),
(18, 10, 80, 'M', 'ml'),
(18, 10, 50, 'S', 'ml'),
(18, 27, 55, 'L', 'g'),
(18, 27, 50, 'M', 'g'),
(18, 27, 40, 'S', 'g'),
(18, 34, 18, 'L', 'g'),
(18, 34, 16, 'M', 'g'),
(18, 34, 12, 'S', 'g'),
(18, 37, 150, 'L', 'g'),
(18, 37, 125, 'M', 'g'),
(18, 37, 100, 'S', 'g');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(0, 'Super Admin'),
(1, 'Admin'),
(2, 'Quản lý'),
(3, 'Nhân viên kho'),
(4, 'Nhân viên bán hàng'),
(5, 'ab'),
(6, 'c'),
(7, 'cc'),
(8, 'ccc'),
(9, 'kml'),
(10, 's');

-- --------------------------------------------------------

--
-- Table structure for table `role_detail`
--

CREATE TABLE `role_detail` (
  `role_id` bigint(20) NOT NULL,
  `staff_id` bigint(20) NOT NULL,
  `entry_date` datetime NOT NULL,
  `salary` float DEFAULT NULL,
  `type_salary` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role_detail`
--

INSERT INTO `role_detail` (`role_id`, `staff_id`, `entry_date`, `salary`, `type_salary`) VALUES
(1, 1, '2024-01-25 16:24:19', 0, 0),
(2, 2, '2024-01-25 16:24:19', 7000000, 1),
(2, 3, '2024-01-25 16:24:19', 7000000, 1),
(3, 4, '2024-01-25 16:24:19', 25000, 2),
(3, 7, '2024-01-25 16:24:19', 25000, 2),
(4, 5, '2024-01-25 16:24:19', 25000, 2),
(4, 6, '2024-01-25 16:24:19', 25000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `salary_format`
--

CREATE TABLE `salary_format` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salary_format`
--

INSERT INTO `salary_format` (`id`, `name`) VALUES
(0, 'default'),
(1, 'Mặc định');

-- --------------------------------------------------------

--
-- Table structure for table `salary_format_allowance`
--

CREATE TABLE `salary_format_allowance` (
  `salary_format_id` bigint(20) NOT NULL,
  `allowance_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salary_format_allowance`
--

INSERT INTO `salary_format_allowance` (`salary_format_id`, `allowance_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `salary_format_deduction`
--

CREATE TABLE `salary_format_deduction` (
  `salary_format_id` bigint(20) NOT NULL,
  `deduction_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `salary_format_deduction`
--

INSERT INTO `salary_format_deduction` (`salary_format_id`, `deduction_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `shipment`
--

CREATE TABLE `shipment` (
  `id` bigint(20) NOT NULL,
  `material_id` bigint(20) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `import_id` bigint(20) DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `remain` double DEFAULT NULL,
  `mfg` date DEFAULT NULL,
  `exp` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shipment`
--

INSERT INTO `shipment` (`id`, `material_id`, `supplier_id`, `import_id`, `quantity`, `remain`, `mfg`, `exp`) VALUES
(1, 3, 1, 1, 30, 0, '2024-03-03', '2024-04-15'),
(2, 4, 1, 1, 30, 30, '2024-03-03', '2024-04-15'),
(3, 5, 1, 1, 30, 30, '2024-03-03', '2024-04-15'),
(4, 6, 1, 1, 30, 20, '2024-03-03', '2024-04-15'),
(5, 7, 1, 1, 30, 30, '2024-03-03', '2024-04-15'),
(6, 3, 1, 1, 30, 0, '2024-03-03', '2024-04-15'),
(7, 3, 2, 1, 30, 0, '2024-03-03', '2024-04-15'),
(8, 3, 1, 1, 30, 0, '2024-03-03', '2024-04-15'),
(9, 3, 1, 1, 30, 0, '2024-03-03', '2024-04-15'),
(10, 6, 1, 5, 3, 0, '2024-03-08', '2024-03-21'),
(11, 9, 1, 5, 10, 0, '2024-03-14', '2024-03-15'),
(13, 7, 1, 8, 1, 1, '2024-04-11', '2024-04-28'),
(14, 3, 2, 10, 22, 12, '2024-04-18', '2024-04-18'),
(15, 5, 1, 10, 2, 2, '2024-04-17', '2024-04-20'),
(17, 7, 5, 10, 2, 2, '2024-04-03', '2024-04-17'),
(18, 3, 2, 11, 20, 20, '2024-04-08', '2024-08-19'),
(19, 4, 1, 11, 20, 20, '2024-04-19', '2024-09-19'),
(20, 11, 2, 12, 3, 3, '2024-04-17', '2024-04-17');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `id` bigint(20) NOT NULL,
  `no` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `salary_format_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`, `no`, `name`, `gender`, `birthdate`, `phone`, `address`, `email`, `deleted`, `salary_format_id`) VALUES
(1, '079203023641', 'Admin', b'0', '2003-08-30', '0961234942', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'admin@gmail.com', b'0', 0),
(2, '079203023644', 'Nguyễn Hoàng Long', b'0', '2003-08-30', '0963333946', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'colong30082003@gmail.com', b'0', 1),
(3, '079203023642', 'Nguyễn Minh Thuận', b'0', '2003-08-30', '0964512947', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'nguyenminhthuan@gmail.com', b'0', 1),
(4, '079203023643', 'Vũ Minh Thuận', b'0', '2003-08-30', '0964512944', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'vmiinhthuan@gmail.com', b'0', 1),
(5, '079203023645', 'Trần Huỳnh Đức Anh', b'0', '2003-08-30', '0964512940', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'ducanh@gmail.com', b'0', 1),
(6, '079203023522', 'Nguyễn Tiến Dũng', b'0', '2003-08-30', '0964512920', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'abc@gmail.com', b'0', 1),
(7, '079203023777', 'Nguyễn Tiến Quang', b'0', '2003-08-30', '0964513325', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'abcd@gmail.com', b'0', 1);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `name`, `phone`, `address`, `email`, `deleted`) VALUES
(1, 'Drai Farm', '0917762211', 'xã Quảng Hiệp, huyện Cư M’gar, tỉnh DakLak', 'DraiFarmcoffee@gmail.com', b'0'),
(2, 'Sơn Việt Coffee', '0937442338', '148 Lý Thái Tổ, Thôn 6, DamBri, Bảo Lộc, Lâm Đồng', 'sonvietcoffe@gmail.com', b'0'),
(3, 'Cà phê Triều Nguyên', '0966770770', '120A Lý Thái Tổ, Đamb’ri, Bảo Lộc, Lâm Đồng', 'trieunguyencoffe@gmail.com', b'0'),
(4, 'Công ty TNHH SX & TM Hucafood ', '0935551919', 'Tổ 9 Hòa Bắc, Khánh Hòa', 'hucafoodcoffee@gmail.com', b'0'),
(5, 'd', '0963333947', '514/26 Lê Đức Thọ P17 Gò Vấp HCM', 'colg@gmail.com', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `work_schedule`
--

CREATE TABLE `work_schedule` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `check_in` varchar(255) DEFAULT 'null',
  `check_out` varchar(255) DEFAULT 'null',
  `shift` int(11) DEFAULT NULL,
  `notice` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `work_schedule`
--

INSERT INTO `work_schedule` (`id`, `staff_id`, `date`, `check_in`, `check_out`, `shift`, `notice`) VALUES
(1, 2, '2024-03-01', '6:00', '12:00', 1, 'Không'),
(2, 2, '2024-03-01', '12:00', '18:00', 2, 'Không'),
(3, 3, '2024-03-01', '18:00', '23:00', 3, 'Không'),
(4, 2, '2024-03-02', '6:00', '12:00', 1, 'Không'),
(5, 2, '2024-03-02', '12:00', '18:00', 2, 'Không'),
(6, 3, '2024-03-02', '18:00', '23:00', 3, 'Không'),
(7, 2, '2024-03-03', '6:00', '12:00', 1, 'Không'),
(8, 2, '2024-03-03', '12:00', '18:00', 2, 'Không'),
(9, 3, '2024-03-03', '18:00', '23:00', 3, 'Không'),
(10, 2, '2024-03-04', '6:00', '12:00', 1, 'Không'),
(11, 2, '2024-03-04', '12:00', '18:00', 2, 'Không'),
(12, 3, '2024-03-04', '18:00', '23:00', 3, 'Không'),
(13, 2, '2024-03-05', '6:00', '12:00', 1, 'Không'),
(14, 2, '2024-03-05', '12:00', '18:00', 2, 'Không'),
(15, 3, '2024-03-05', '18:00', '23:00', 3, 'Không'),
(16, 2, '2024-03-06', '6:00', '12:00', 1, 'Không'),
(17, 2, '2024-03-06', '12:00', '18:00', 2, 'Không'),
(18, 3, '2024-03-06', '18:00', '23:00', 3, 'Không'),
(19, 2, '2024-03-07', '6:00', '12:00', 1, 'Không'),
(20, 2, '2024-03-07', '12:00', '18:00', 2, 'Không'),
(21, 3, '2024-03-07', '18:00', '23:00', 3, 'Không'),
(22, 4, '2024-03-01', '6:00', '12:00', 1, 'Không'),
(23, 4, '2024-03-01', '12:00', '18:00', 2, 'Không'),
(24, 7, '2024-03-01', '18:00', '23:00', 3, 'Không'),
(25, 4, '2024-03-02', '6:00', '12:00', 1, 'Không'),
(26, 4, '2024-03-02', '12:00', '18:00', 2, 'Không'),
(27, 7, '2024-03-02', '18:00', '23:00', 3, 'Không'),
(28, 4, '2024-03-03', '6:00', '12:00', 1, 'Không'),
(29, 4, '2024-03-03', '12:00', '18:00', 2, 'Không'),
(30, 7, '2024-03-03', '18:00', '23:00', 3, 'Không'),
(31, 4, '2024-03-04', '6:00', '12:00', 1, 'Không'),
(32, 4, '2024-03-04', '12:00', '18:00', 2, 'Không'),
(33, 7, '2024-03-04', '18:00', '23:00', 3, 'Không'),
(34, 4, '2024-03-05', '6:00', '12:00', 1, 'Không'),
(35, 4, '2024-03-05', '12:00', '18:00', 2, 'Không'),
(36, 7, '2024-03-05', '18:00', '23:00', 3, 'Không'),
(37, 4, '2024-03-06', '6:00', '12:00', 1, 'Không'),
(38, 4, '2024-03-06', '12:00', '18:00', 2, 'Không'),
(39, 7, '2024-03-06', '18:00', '23:00', 3, 'Không'),
(40, 4, '2024-03-07', '6:00', '12:00', 1, 'Không'),
(41, 4, '2024-03-07', '12:00', '18:00', 2, 'Không'),
(42, 7, '2024-03-07', '18:00', '23:00', 3, 'Không'),
(43, 5, '2024-03-01', '6:00', '12:00', 1, 'Không'),
(44, 5, '2024-03-01', '12:00', '18:00', 2, 'Không'),
(45, 6, '2024-03-01', '18:00', '23:00', 3, 'Không'),
(46, 5, '2024-03-02', '6:00', '12:00', 1, 'Không'),
(47, 5, '2024-03-02', '12:00', '18:00', 2, 'Không'),
(48, 6, '2024-03-02', '18:00', '23:00', 3, 'Không'),
(49, 5, '2024-03-03', '6:00', '12:00', 1, 'Không'),
(50, 5, '2024-03-03', '12:00', '18:00', 2, 'Không'),
(51, 6, '2024-03-03', '18:00', '23:00', 3, 'Không'),
(52, 5, '2024-03-04', '6:00', '12:00', 1, 'Không'),
(53, 5, '2024-03-04', '12:00', '18:00', 2, 'Không'),
(54, 6, '2024-03-04', '18:00', '23:00', 3, 'Không'),
(55, 5, '2024-03-05', '6:00', '12:00', 1, 'Không'),
(56, 5, '2024-03-05', '12:00', '18:00', 2, 'Không'),
(57, 6, '2024-03-05', '18:00', '23:00', 3, 'Không'),
(58, 5, '2024-03-06', '6:00', '12:00', 1, 'Không'),
(59, 5, '2024-03-06', '12:00', '18:00', 2, 'Không'),
(60, 6, '2024-03-06', '18:00', '23:00', 3, 'Không'),
(61, 5, '2024-03-07', '6:00', '12:00', 1, 'Không'),
(62, 5, '2024-03-07', '12:00', '18:00', 2, 'Không'),
(63, 6, '2024-03-07', '18:00', '23:00', 3, 'Không'),
(64, 2, '2024-03-08', '6:00', '12:00', 1, 'Không'),
(65, 2, '2024-03-08', '12:00', '18:00', 2, 'Không'),
(66, 3, '2024-03-08', '18:00', '23:00', 3, 'Không'),
(67, 2, '2024-03-09', '6:00', '12:00', 1, 'Không'),
(68, 2, '2024-03-09', '12:00', '18:00', 2, 'Không'),
(69, 3, '2024-03-09', '18:00', '23:00', 3, 'Không'),
(70, 4, '2024-03-08', '6:00', '12:00', 1, 'Không'),
(71, 4, '2024-03-08', '12:00', '18:00', 2, 'Không'),
(72, 7, '2024-03-08', '18:00', '23:00', 3, 'Không'),
(73, 4, '2024-03-09', '6:00', '12:00', 1, 'Không'),
(74, 4, '2024-03-09', '12:00', '18:00', 2, 'Không'),
(75, 7, '2024-03-09', '18:00', '23:00', 3, 'Không'),
(76, 5, '2024-03-08', '6:00', '12:00', 1, 'Không'),
(77, 5, '2024-03-08', '12:00', '18:00', 2, 'Không'),
(78, 6, '2024-03-08', '18:00', '23:00', 3, 'Không'),
(79, 5, '2024-03-09', '6:00', '12:00', 1, 'Không'),
(80, 5, '2024-03-09', '12:00', '18:00', 2, 'Không'),
(81, 6, '2024-03-09', '18:00', '23:00', 3, 'Không'),
(82, 2, '2024-03-10', '6:00', '12:00', 1, 'Không'),
(83, 2, '2024-03-10', '12:00', '18:00', 2, 'Không'),
(84, 3, '2024-03-10', '18:00', '23:00', 3, 'Không'),
(85, 2, '2024-03-11', '6:00', '12:00', 1, 'Không'),
(86, 2, '2024-03-11', '12:00', '18:00', 2, 'Không'),
(87, 3, '2024-03-11', '18:00', '23:00', 3, 'Không'),
(88, 4, '2024-03-10', '6:00', '12:00', 1, 'Không'),
(89, 4, '2024-03-10', '12:00', '18:00', 2, 'Không'),
(90, 7, '2024-03-10', '18:00', '23:00', 3, 'Không'),
(91, 4, '2024-03-11', '6:00', '12:00', 1, 'Không'),
(92, 4, '2024-03-11', '12:00', '18:00', 2, 'Không'),
(93, 7, '2024-03-11', '18:00', '23:00', 3, 'Không'),
(94, 5, '2024-03-10', '6:00', '12:00', 1, 'Không'),
(95, 5, '2024-03-10', '12:00', '18:00', 2, 'Không'),
(96, 6, '2024-03-10', '18:00', '23:00', 3, 'Không'),
(97, 5, '2024-03-11', '6:00', '12:00', 1, 'Không'),
(98, 5, '2024-03-11', '12:00', '18:00', 2, 'Không'),
(99, 6, '2024-03-11', '18:00', '23:00', 3, 'Không'),
(100, 2, '2024-03-12', '6:00', '12:00', 1, 'Không'),
(101, 2, '2024-03-12', '12:00', '18:00', 2, 'Không'),
(102, 3, '2024-03-12', '18:00', '23:00', 3, 'Không'),
(103, 2, '2024-03-13', '6:00', '12:00', 1, 'Không'),
(104, 2, '2024-03-13', '12:00', '18:00', 2, 'Không'),
(105, 3, '2024-03-13', '18:00', '23:00', 3, 'Không'),
(106, 2, '2024-03-14', '6:00', '12:00', 1, 'Không'),
(107, 2, '2024-03-14', '12:00', '18:00', 2, 'Không'),
(108, 3, '2024-03-14', '18:00', '23:00', 3, 'Không'),
(109, 4, '2024-03-12', '6:00', '12:00', 1, 'Không'),
(110, 4, '2024-03-12', '12:00', '18:00', 2, 'Không'),
(111, 7, '2024-03-12', '18:00', '23:00', 3, 'Không'),
(112, 4, '2024-03-13', '6:00', '12:00', 1, 'Không'),
(113, 4, '2024-03-13', '12:00', '18:00', 2, 'Không'),
(114, 7, '2024-03-13', '18:00', '23:00', 3, 'Không'),
(115, 4, '2024-03-14', '6:00', '12:00', 1, 'Không'),
(116, 4, '2024-03-14', '12:00', '18:00', 2, 'Không'),
(117, 7, '2024-03-14', '18:00', '23:00', 3, 'Không'),
(118, 5, '2024-03-12', '6:00', '12:00', 1, 'Không'),
(119, 5, '2024-03-12', '12:00', '18:00', 2, 'Không'),
(120, 6, '2024-03-12', '18:00', '23:00', 3, 'Không'),
(121, 5, '2024-03-13', '6:00', '12:00', 1, 'Không'),
(122, 5, '2024-03-13', '12:00', '18:00', 2, 'Không'),
(123, 6, '2024-03-13', '18:00', '23:00', 3, 'Không'),
(124, 5, '2024-03-14', '6:00', '12:00', 1, 'Không'),
(125, 5, '2024-03-14', '12:00', '18:00', 2, 'Không'),
(126, 6, '2024-03-14', '18:00', '23:00', 3, 'Không'),
(127, 2, '2024-03-15', '6:00', '12:00', 1, 'Không'),
(128, 2, '2024-03-15', '12:00', '18:00', 2, 'Không'),
(129, 3, '2024-03-15', '18:00', '23:00', 3, 'Không'),
(130, 2, '2024-03-16', '6:00', '12:00', 1, 'Không'),
(131, 2, '2024-03-16', '12:00', '18:00', 2, 'Không'),
(132, 3, '2024-03-16', '18:00', '23:00', 3, 'Không'),
(133, 2, '2024-03-17', '6:00', '12:00', 1, 'Không'),
(134, 2, '2024-03-17', '12:00', '18:00', 2, 'Không'),
(135, 3, '2024-03-17', '18:00', '23:00', 3, 'Không'),
(136, 4, '2024-03-15', '6:00', '12:00', 1, 'Không'),
(137, 4, '2024-03-15', '12:00', '18:00', 2, 'Không'),
(138, 7, '2024-03-15', '18:00', '23:00', 3, 'Không'),
(139, 4, '2024-03-16', '6:00', '12:00', 1, 'Không'),
(140, 4, '2024-03-16', '12:00', '18:00', 2, 'Không'),
(141, 7, '2024-03-16', '18:00', '23:00', 3, 'Không'),
(142, 4, '2024-03-17', '6:00', '12:00', 1, 'Không'),
(143, 4, '2024-03-17', '12:00', '18:00', 2, 'Không'),
(144, 7, '2024-03-17', '18:00', '23:00', 3, 'Không'),
(145, 5, '2024-03-15', '6:00', '12:00', 1, 'Không'),
(146, 5, '2024-03-15', '12:00', '18:00', 2, 'Không'),
(147, 6, '2024-03-15', '18:00', '23:00', 3, 'Không'),
(148, 5, '2024-03-16', '6:00', '12:00', 1, 'Không'),
(149, 5, '2024-03-16', '12:00', '18:00', 2, 'Không'),
(150, 6, '2024-03-16', '18:00', '23:00', 3, 'Không'),
(151, 5, '2024-03-17', '6:00', '12:00', 1, 'Không'),
(152, 5, '2024-03-17', '12:00', '18:00', 2, 'Không'),
(153, 6, '2024-03-17', '18:00', '23:00', 3, 'Không'),
(154, 2, '2024-03-18', '6:00', '12:00', 1, 'Không'),
(155, 2, '2024-03-18', '12:00', '18:00', 2, 'Không'),
(156, 3, '2024-03-18', '18:00', '23:00', 3, 'Không'),
(157, 2, '2024-03-19', '6:00', '12:00', 1, 'Không'),
(158, 2, '2024-03-19', '12:00', '18:00', 2, 'Không'),
(159, 3, '2024-03-19', '18:00', '23:00', 3, 'Không'),
(160, 2, '2024-03-20', '6:00', '12:00', 1, 'Không'),
(161, 2, '2024-03-20', '12:00', '18:00', 2, 'Không'),
(162, 3, '2024-03-20', '18:00', '23:00', 3, 'Không'),
(163, 4, '2024-03-18', '6:00', '12:00', 1, 'Không'),
(164, 4, '2024-03-18', '12:00', '18:00', 2, 'Không'),
(165, 7, '2024-03-18', '18:00', '23:00', 3, 'Không'),
(166, 4, '2024-03-19', '6:00', '12:00', 1, 'Không'),
(167, 4, '2024-03-19', '12:00', '18:00', 2, 'Không'),
(168, 7, '2024-03-19', '18:00', '23:00', 3, 'Không'),
(169, 4, '2024-03-20', '6:00', '12:00', 1, 'Không'),
(170, 4, '2024-03-20', '12:00', '18:00', 2, 'Không'),
(171, 7, '2024-03-20', '18:00', '23:00', 3, 'Không'),
(172, 5, '2024-03-18', '6:00', '12:00', 1, 'Không'),
(173, 5, '2024-03-18', '12:00', '18:00', 2, 'Không'),
(174, 6, '2024-03-18', '18:00', '23:00', 3, 'Không'),
(175, 5, '2024-03-19', '6:00', '12:00', 1, 'Không'),
(176, 5, '2024-03-19', '12:00', '18:00', 2, 'Không'),
(177, 6, '2024-03-19', '18:00', '23:00', 3, 'Không'),
(178, 5, '2024-03-20', '6:00', '12:00', 1, 'Không'),
(179, 5, '2024-03-20', '12:00', '18:00', 2, 'Không'),
(180, 6, '2024-03-20', '18:00', '23:00', 3, 'Không'),
(181, 2, '2024-03-21', '6:00', '12:00', 1, 'Không'),
(182, 2, '2024-03-21', '12:00', '18:00', 2, 'Không'),
(183, 3, '2024-03-21', '18:00', '23:00', 3, 'Không'),
(184, 2, '2024-03-22', '6:00', '12:00', 1, 'Không'),
(185, 2, '2024-03-22', '12:00', '18:00', 2, 'Không'),
(186, 3, '2024-03-22', '18:00', '23:00', 3, 'Không'),
(187, 2, '2024-03-23', 'null', 'null', 1, 'Nghỉ phép năm'),
(188, 2, '2024-03-23', 'null', 'null', 2, 'Nghỉ phép năm'),
(189, 3, '2024-03-23', '18:00', '23:00', 3, 'Không'),
(190, 4, '2024-03-21', '6:00', '12:00', 1, 'Không'),
(191, 4, '2024-03-21', '12:00', '18:00', 2, 'Không'),
(192, 7, '2024-03-21', '18:00', '23:00', 3, 'Không'),
(193, 4, '2024-03-22', '6:00', '12:00', 1, 'Không'),
(194, 4, '2024-03-22', '12:00', '18:00', 2, 'Không'),
(195, 7, '2024-03-22', '18:00', '23:00', 3, 'Không'),
(196, 4, '2024-03-23', '6:00', '12:00', 1, 'Không'),
(197, 4, '2024-03-23', '12:00', '18:00', 2, 'Không'),
(198, 7, '2024-03-23', '18:00', '23:00', 3, 'Không'),
(199, 5, '2024-03-21', '6:00', '12:00', 1, 'Không'),
(200, 5, '2024-03-21', '12:00', '18:00', 2, 'Không'),
(201, 6, '2024-03-21', '18:00', '23:00', 3, 'Không'),
(202, 5, '2024-03-22', '6:00', '12:00', 1, 'Không'),
(203, 5, '2024-03-22', '12:00', '18:00', 2, 'Không'),
(204, 6, '2024-03-22', '18:00', '23:00', 3, 'Không'),
(205, 5, '2024-03-23', '6:00', '12:00', 1, 'Không'),
(206, 5, '2024-03-23', '12:00', '18:00', 2, 'Không'),
(207, 6, '2024-03-23', '18:00', '23:00', 3, 'Không'),
(208, 2, '2024-03-24', '6:00', '12:00', 1, 'Không'),
(209, 2, '2024-03-24', '12:00', '18:00', 2, 'Không'),
(210, 3, '2024-03-24', '18:00', '23:00', 3, 'Không'),
(211, 2, '2024-03-25', 'null', 'null', 1, 'Nghỉ ốm'),
(212, 2, '2024-03-25', 'null', 'null', 2, 'Nghỉ ốm'),
(213, 3, '2024-03-25', '18:00', '23:00', 3, 'Không'),
(214, 2, '2024-03-26', 'null', 'null', 1, 'Không'),
(215, 2, '2024-03-26', '12:00', '18:00', 2, 'Không'),
(216, 3, '2024-03-26', 'null', 'null', 3, 'Không'),
(217, 4, '2024-03-24', '6:00', '12:00', 1, 'Không'),
(218, 4, '2024-03-24', '12:00', '18:00', 2, 'Không'),
(219, 7, '2024-03-24', '18:00', '23:00', 3, 'Không'),
(220, 4, '2024-03-25', '06:30', '12:00', 1, 'Không'),
(221, 4, '2024-03-25', '12:00', '17:00', 2, 'Không'),
(222, 7, '2024-03-25', '18:00', '23:00', 3, 'Không'),
(223, 4, '2024-03-26', '6:00', '12:00', 1, 'Không'),
(224, 4, '2024-03-26', '12:00', '18:00', 2, 'Không'),
(225, 7, '2024-03-26', '18:00', '23:00', 3, 'Không'),
(226, 5, '2024-03-24', '6:00', '12:00', 1, 'Không'),
(227, 5, '2024-03-24', '12:00', '18:00', 2, 'Không'),
(228, 6, '2024-03-24', '18:00', '23:00', 3, 'Không'),
(229, 5, '2024-03-25', '6:00', '12:00', 1, 'Không'),
(230, 5, '2024-03-25', '12:00', '18:00', 2, 'Không'),
(231, 6, '2024-03-25', '18:00', '23:00', 3, 'Không'),
(232, 5, '2024-03-26', '6:00', '12:00', 1, 'Không'),
(233, 5, '2024-03-26', '12:00', '18:00', 2, 'Không'),
(234, 6, '2024-03-26', '18:00', '23:00', 3, 'Không'),
(235, 2, '2024-03-27', '06:50', '12:00', 1, 'Không'),
(236, 2, '2024-03-27', '12:00', '18:00', 2, 'Không'),
(237, 3, '2024-03-27', '18:00', '23:00', 3, 'Không'),
(238, 2, '2024-03-28', '6:00', '12:00', 1, 'Không'),
(239, 2, '2024-03-28', '12:00', '18:00', 2, 'Không'),
(240, 3, '2024-03-28', '18:00', '23:00', 3, 'Không'),
(241, 4, '2024-03-27', '6:00', '12:00', 1, 'Không'),
(242, 4, '2024-03-27', '12:00', '18:00', 2, 'Không'),
(243, 7, '2024-03-27', '18:00', '23:00', 3, 'Không'),
(244, 4, '2024-03-28', '6:00', '12:00', 1, 'Không'),
(245, 4, '2024-03-28', '12:00', '18:00', 2, 'Không'),
(246, 7, '2024-03-28', '18:00', '23:00', 3, 'Không'),
(247, 5, '2024-03-27', '6:00', '12:00', 1, 'Không'),
(248, 5, '2024-03-27', '12:00', '18:00', 2, 'Không'),
(249, 6, '2024-03-27', '18:00', '23:00', 3, 'Không'),
(250, 5, '2024-03-28', '6:00', '12:00', 1, 'Không'),
(251, 5, '2024-03-28', '12:00', '18:00', 2, 'Không'),
(252, 6, '2024-03-28', '18:00', '23:00', 3, 'Không'),
(253, 2, '2024-03-29', '6:00', '12:00', 1, 'Không'),
(254, 2, '2024-03-29', '12:00', '17:00', 2, 'Không'),
(255, 3, '2024-03-29', '18:00', '23:00', 3, 'Không'),
(256, 2, '2024-03-30', '6:00', '12:00', 1, 'Không'),
(257, 2, '2024-03-30', '12:00', '18:00', 2, 'Không'),
(258, 3, '2024-03-30', '18:00', '23:00', 3, 'Không'),
(259, 2, '2024-03-31', '6:00', '12:00', 1, 'Không'),
(260, 2, '2024-03-31', '12:00', '18:00', 2, 'Không'),
(261, 3, '2024-03-31', '18:00', '23:00', 3, 'Không'),
(262, 4, '2024-03-29', '6:00', '12:00', 1, 'Không'),
(263, 4, '2024-03-29', '12:00', '18:00', 2, 'Không'),
(264, 7, '2024-03-29', '18:00', '23:00', 3, 'Không'),
(265, 4, '2024-03-30', '6:00', '12:00', 1, 'Không'),
(266, 4, '2024-03-30', '12:00', '18:00', 2, 'Không'),
(267, 7, '2024-03-30', '18:00', '23:00', 3, 'Không'),
(268, 4, '2024-03-31', '6:00', '12:00', 1, 'Không'),
(269, 4, '2024-03-31', '12:00', '18:00', 2, 'Không'),
(270, 7, '2024-03-31', '18:00', '23:00', 3, 'Không'),
(271, 5, '2024-03-29', '6:00', '12:00', 1, 'Không'),
(272, 5, '2024-03-29', '12:00', '18:00', 2, 'Không'),
(273, 6, '2024-03-29', '18:00', '23:00', 3, 'Không'),
(274, 5, '2024-03-30', '6:00', '12:00', 1, 'Không'),
(275, 5, '2024-03-30', '12:00', '18:00', 2, 'Không'),
(276, 6, '2024-03-30', '18:00', '23:00', 3, 'Không'),
(277, 5, '2024-03-31', '6:00', '12:00', 1, 'Không'),
(278, 5, '2024-03-31', '12:00', '18:00', 2, 'Không'),
(279, 6, '2024-03-31', '18:00', '23:00', 3, 'Không');

-- --------------------------------------------------------

--
-- Table structure for table `work_schedule_bonus`
--

CREATE TABLE `work_schedule_bonus` (
  `work_schedule_id` bigint(20) NOT NULL,
  `bonus_name` varchar(20) NOT NULL,
  `bonus_amount` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `bonus_total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `work_schedule_bonus`
--

INSERT INTO `work_schedule_bonus` (`work_schedule_id`, `bonus_name`, `bonus_amount`, `quantity`, `bonus_total`) VALUES
(239, 'xyz', 50000, 1, 50000);

-- --------------------------------------------------------

--
-- Table structure for table `work_schedule_fine`
--

CREATE TABLE `work_schedule_fine` (
  `work_schedule_id` bigint(20) NOT NULL,
  `fine_name` varchar(20) NOT NULL,
  `fine_amount` double DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `fine_total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `work_schedule_fine`
--

INSERT INTO `work_schedule_fine` (`work_schedule_id`, `fine_name`, `fine_amount`, `quantity`, `fine_total`) VALUES
(215, 'abc', 10000, 2, 20000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_account_staff` (`staff_id`);

--
-- Indexes for table `allowance`
--
ALTER TABLE `allowance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `decentralization`
--
ALTER TABLE `decentralization`
  ADD PRIMARY KEY (`role_id`,`module_id`,`function_id`),
  ADD KEY `fk_decentralization_module` (`module_id`),
  ADD KEY `fk_decentralization_function` (`function_id`);

--
-- Indexes for table `deduction`
--
ALTER TABLE `deduction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `discount_detail`
--
ALTER TABLE `discount_detail`
  ADD PRIMARY KEY (`discount_id`,`product_id`,`Size`,`quantity`,`discountBill`),
  ADD KEY `fk_discount_detail_product` (`product_id`);

--
-- Indexes for table `export_detail`
--
ALTER TABLE `export_detail`
  ADD PRIMARY KEY (`export_id`,`shipment_id`) USING BTREE,
  ADD KEY `fk_export_detail` (`shipment_id`);

--
-- Indexes for table `export_note`
--
ALTER TABLE `export_note`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_export_note_staff` (`staff_id`);

--
-- Indexes for table `function`
--
ALTER TABLE `function`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `import_note`
--
ALTER TABLE `import_note`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_import_note_staff` (`staff_id`);

--
-- Indexes for table `leave_of_absence_form`
--
ALTER TABLE `leave_of_absence_form`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_leave_staff` (`staff_id`);

--
-- Indexes for table `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payroll`
--
ALTER TABLE `payroll`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payroll_detail`
--
ALTER TABLE `payroll_detail`
  ADD PRIMARY KEY (`payroll_id`,`staff_id`) USING BTREE,
  ADD KEY `fk_payroll_staff` (`staff_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`,`size`) USING BTREE,
  ADD KEY `id` (`id`),
  ADD KEY `size` (`size`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_receipt_staff` (`staff_id`),
  ADD KEY `fk_discount` (`discount_id`);

--
-- Indexes for table `receipt_detail`
--
ALTER TABLE `receipt_detail`
  ADD PRIMARY KEY (`receipt_id`,`product_id`,`size`,`notice`) USING BTREE,
  ADD KEY `fk_receipt_detail_product` (`product_id`),
  ADD KEY `receipt_detail_product_size_fk` (`size`);

--
-- Indexes for table `recipe`
--
ALTER TABLE `recipe`
  ADD PRIMARY KEY (`product_id`,`material_id`,`size`) USING BTREE,
  ADD KEY `fk_recipe_material` (`material_id`),
  ADD KEY `fk_recipe_size` (`size`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role_detail`
--
ALTER TABLE `role_detail`
  ADD PRIMARY KEY (`role_id`,`staff_id`,`entry_date`) USING BTREE,
  ADD KEY `fk_role_detail_staff` (`staff_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `entry_date` (`entry_date`);

--
-- Indexes for table `salary_format`
--
ALTER TABLE `salary_format`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salary_format_allowance`
--
ALTER TABLE `salary_format_allowance`
  ADD PRIMARY KEY (`salary_format_id`,`allowance_id`) USING BTREE,
  ADD KEY `fk_allowance` (`allowance_id`);

--
-- Indexes for table `salary_format_deduction`
--
ALTER TABLE `salary_format_deduction`
  ADD PRIMARY KEY (`salary_format_id`,`deduction_id`) USING BTREE,
  ADD KEY `fk_deduction` (`deduction_id`);

--
-- Indexes for table `shipment`
--
ALTER TABLE `shipment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_shipment_import_note` (`import_id`),
  ADD KEY `fk_shipment_material` (`material_id`),
  ADD KEY `fk_shipment_supplier` (`supplier_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`) USING BTREE,
  ADD KEY `fk_salary_format` (`salary_format_id`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `work_schedule`
--
ALTER TABLE `work_schedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_work_schedule_staff` (`staff_id`);

--
-- Indexes for table `work_schedule_bonus`
--
ALTER TABLE `work_schedule_bonus`
  ADD PRIMARY KEY (`work_schedule_id`,`bonus_name`) USING BTREE;

--
-- Indexes for table `work_schedule_fine`
--
ALTER TABLE `work_schedule_fine`
  ADD PRIMARY KEY (`work_schedule_id`,`fine_name`) USING BTREE;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `function`
--
ALTER TABLE `function`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `module`
--
ALTER TABLE `module`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11116;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `fk_account_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `decentralization`
--
ALTER TABLE `decentralization`
  ADD CONSTRAINT `fk_decentralization_function` FOREIGN KEY (`function_id`) REFERENCES `function` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_decentralization_module` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_decentralization_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `discount_detail`
--
ALTER TABLE `discount_detail`
  ADD CONSTRAINT `fk_discount_detail_discount` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_discount_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `export_detail`
--
ALTER TABLE `export_detail`
  ADD CONSTRAINT `fk_export_detail` FOREIGN KEY (`shipment_id`) REFERENCES `shipment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_export_detail_export_note` FOREIGN KEY (`export_id`) REFERENCES `export_note` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `export_note`
--
ALTER TABLE `export_note`
  ADD CONSTRAINT `fk_export_note_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `import_note`
--
ALTER TABLE `import_note`
  ADD CONSTRAINT `fk_import_note_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `leave_of_absence_form`
--
ALTER TABLE `leave_of_absence_form`
  ADD CONSTRAINT `fk_leave_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payroll_detail`
--
ALTER TABLE `payroll_detail`
  ADD CONSTRAINT `fk_payroll` FOREIGN KEY (`payroll_id`) REFERENCES `payroll` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_payroll_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `receipt`
--
ALTER TABLE `receipt`
  ADD CONSTRAINT `fk_discount` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`),
  ADD CONSTRAINT `fk_receipt_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `receipt_detail`
--
ALTER TABLE `receipt_detail`
  ADD CONSTRAINT `fk_receipt_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_receipt_detail_receipt` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `receipt_detail_product_size_fk` FOREIGN KEY (`size`) REFERENCES `product` (`size`);

--
-- Constraints for table `recipe`
--
ALTER TABLE `recipe`
  ADD CONSTRAINT `fk_recipe_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_recipe_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_recipe_size` FOREIGN KEY (`size`) REFERENCES `product` (`size`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `role_detail`
--
ALTER TABLE `role_detail`
  ADD CONSTRAINT `fk_role_detail_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_role_detail_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `salary_format_allowance`
--
ALTER TABLE `salary_format_allowance`
  ADD CONSTRAINT `fk_allowance` FOREIGN KEY (`allowance_id`) REFERENCES `allowance` (`id`),
  ADD CONSTRAINT `fk_salary` FOREIGN KEY (`salary_format_id`) REFERENCES `salary_format` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `salary_format_deduction`
--
ALTER TABLE `salary_format_deduction`
  ADD CONSTRAINT `salary_format_deduction_ibfk_2` FOREIGN KEY (`deduction_id`) REFERENCES `deduction` (`id`),
  ADD CONSTRAINT `salary_format_deduction_ibfk_3` FOREIGN KEY (`salary_format_id`) REFERENCES `salary_format` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `shipment`
--
ALTER TABLE `shipment`
  ADD CONSTRAINT `fk_shipment_import_note` FOREIGN KEY (`import_id`) REFERENCES `import_note` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_shipment_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_shipment_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `staff`
--
ALTER TABLE `staff`
  ADD CONSTRAINT `fk_salary_format` FOREIGN KEY (`salary_format_id`) REFERENCES `salary_format` (`id`);

--
-- Constraints for table `work_schedule`
--
ALTER TABLE `work_schedule`
  ADD CONSTRAINT `fk_work_schedule_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `work_schedule_bonus`
--
ALTER TABLE `work_schedule_bonus`
  ADD CONSTRAINT `fk_workschedule1` FOREIGN KEY (`work_schedule_id`) REFERENCES `work_schedule` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `work_schedule_fine`
--
ALTER TABLE `work_schedule_fine`
  ADD CONSTRAINT `fk_workschedule` FOREIGN KEY (`work_schedule_id`) REFERENCES `work_schedule` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
