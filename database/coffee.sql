-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 07, 2024 at 09:05 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

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

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
                           `id` bigint(20) NOT NULL,
                           `username` varchar(255) DEFAULT NULL,
                           `password` varchar(255) DEFAULT NULL,
                           `staff_id` bigint(20) DEFAULT NULL,
                           `role_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `staff_id`, `role_id`) VALUES
    (1, 'longbott', '$2a$12$mQ/t9HUWSBZ2/I7Ff3qFAOajpfYqFyZxLZ9VDiFGYSbITcvoGxzre', 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `decentralization`
--

CREATE TABLE `decentralization` (
                                    `role_id` bigint(20) NOT NULL,
                                    `module_id` bigint(20) NOT NULL,
                                    `function_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `decentralization`
--

INSERT INTO `decentralization` (`role_id`, `module_id`, `function_id`) VALUES
                                                                           (0, 1, 1),
                                                                           (0, 2, 1),
                                                                           (0, 2, 5),
                                                                           (0, 3, 1),
                                                                           (0, 4, 1),
                                                                           (0, 5, 1),
                                                                           (0, 6, 1),
                                                                           (0, 6, 5),
                                                                           (0, 7, 1),
                                                                           (0, 7, 2),
                                                                           (0, 7, 5),
                                                                           (0, 8, 1),
                                                                           (0, 8, 2),
                                                                           (0, 8, 5),
                                                                           (0, 9, 1),
                                                                           (0, 9, 2),
                                                                           (0, 9, 3),
                                                                           (0, 9, 4),
                                                                           (0, 9, 5),
                                                                           (0, 10, 1),
                                                                           (0, 10, 2),
                                                                           (0, 10, 3),
                                                                           (0, 10, 4),
                                                                           (0, 10, 5),
                                                                           (0, 10, 6),
                                                                           (0, 10, 7),
                                                                           (0, 11, 1),
                                                                           (0, 11, 2),
                                                                           (0, 11, 3),
                                                                           (0, 11, 4),
                                                                           (0, 11, 5),
                                                                           (0, 11, 6),
                                                                           (0, 11, 7),
                                                                           (0, 12, 1),
                                                                           (0, 12, 2),
                                                                           (0, 12, 3),
                                                                           (0, 12, 4),
                                                                           (0, 12, 5),
                                                                           (0, 13, 1),
                                                                           (0, 13, 2),
                                                                           (0, 13, 3),
                                                                           (0, 13, 4),
                                                                           (1, 12, 1),
                                                                           (1, 12, 2),
                                                                           (1, 12, 3),
                                                                           (1, 12, 4),
                                                                           (1, 12, 5),
                                                                           (1, 13, 1),
                                                                           (1, 13, 2),
                                                                           (1, 13, 3),
                                                                           (1, 13, 4),
                                                                           (2, 1, 1),
                                                                           (2, 2, 1),
                                                                           (2, 2, 5),
                                                                           (2, 3, 1),
                                                                           (2, 4, 1),
                                                                           (2, 5, 1),
                                                                           (2, 6, 1),
                                                                           (2, 6, 5),
                                                                           (2, 7, 1),
                                                                           (2, 7, 2),
                                                                           (2, 7, 5),
                                                                           (2, 8, 1),
                                                                           (2, 8, 2),
                                                                           (2, 8, 5),
                                                                           (2, 9, 1),
                                                                           (2, 9, 2),
                                                                           (2, 9, 3),
                                                                           (2, 9, 4),
                                                                           (2, 9, 5),
                                                                           (2, 10, 1),
                                                                           (2, 10, 2),
                                                                           (2, 10, 3),
                                                                           (2, 10, 4),
                                                                           (2, 10, 5),
                                                                           (2, 10, 6),
                                                                           (2, 10, 7),
                                                                           (2, 11, 1),
                                                                           (2, 11, 2),
                                                                           (2, 11, 3),
                                                                           (2, 11, 4),
                                                                           (2, 11, 5),
                                                                           (2, 11, 6),
                                                                           (2, 11, 7),
                                                                           (3, 1, 1),
                                                                           (3, 4, 1),
                                                                           (3, 5, 1),
                                                                           (3, 6, 1),
                                                                           (3, 6, 5),
                                                                           (3, 9, 1),
                                                                           (3, 9, 5);

-- --------------------------------------------------------

--
-- Table structure for table `discount`
--

CREATE TABLE `discount` (
                            `id` bigint(20) NOT NULL,
                            `start_date` date DEFAULT NULL,
                            `end_date` date DEFAULT NULL,
                            `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `discount`
--

INSERT INTO `discount` (`id`, `start_date`, `end_date`, `status`) VALUES
                                                                      (0, '1000-01-01', '1000-01-01', b'0'),
                                                                      (1, '2024-03-10', '2024-05-10', b'0'),
                                                                      (2, '2024-03-10', '2024-05-10', b'1'),
                                                                      (3, '2024-03-10', '2024-05-10', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `discount_detail`
--

CREATE TABLE `discount_detail` (
                                   `discount_id` bigint(20) NOT NULL,
                                   `product_id` bigint(20) NOT NULL,
                                   `percent` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `discount_detail`
--

INSERT INTO `discount_detail` (`discount_id`, `product_id`, `percent`) VALUES
    (1, 1, 20);

-- --------------------------------------------------------

--
-- Table structure for table `export_detail`
--

CREATE TABLE `export_detail` (
                                 `export_id` bigint(20) NOT NULL,
                                 `shipment_id` bigint(20) NOT NULL,
                                 `quantity` double DEFAULT NULL,
                                 `reason` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `export_detail`
--

INSERT INTO `export_detail` (`export_id`, `shipment_id`, `quantity`, `reason`) VALUES
    (1, 1, 20, 'Ban');

-- --------------------------------------------------------

--
-- Table structure for table `export_note`
--

CREATE TABLE `export_note` (
                               `id` bigint(20) NOT NULL,
                               `staff_id` bigint(20) DEFAULT NULL,
                               `total` double DEFAULT NULL,
                               `invoice_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `export_note`
--

INSERT INTO `export_note` (`id`, `staff_id`, `total`, `invoice_date`) VALUES
    (1, 1, 0, '2024-02-07');

-- --------------------------------------------------------

--
-- Table structure for table `function`
--

CREATE TABLE `function` (
                            `id` bigint(20) NOT NULL,
                            `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `function`
--

INSERT INTO `function` (`id`, `name`) VALUES
                                          (1, 'view'),
                                          (2, 'add'),
                                          (3, 'edit'),
                                          (4, 'remove'),
                                          (5, 'detail'),
                                          (6, 'excel'),
                                          (7, 'pdf');

-- --------------------------------------------------------

--
-- Table structure for table `import_note`
--

CREATE TABLE `import_note` (
                               `id` bigint(20) NOT NULL,
                               `staff_id` bigint(20) DEFAULT NULL,
                               `total` double DEFAULT NULL,
                               `received_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `import_note`
--

INSERT INTO `import_note` (`id`, `staff_id`, `total`, `received_date`) VALUES
                                                                           (1, 1, 0, '2024-02-06'),
                                                                           (2, 1, 0, '2024-02-07');

-- --------------------------------------------------------

--
-- Table structure for table `material`
--

CREATE TABLE `material` (
                            `id` bigint(20) NOT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `supplier_id` bigint(20) DEFAULT NULL,
                            `remain` double DEFAULT NULL,
                            `unit` varchar(255) DEFAULT NULL,
                            `deleted` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `material`
--

INSERT INTO `material` (`id`, `name`, `supplier_id`, `remain`, `unit`, `deleted`) VALUES
                                                                                      (1, 'abc', 1, 3, 'd', b'1'),
                                                                                      (2, 'xzy', 1, 51, 'd', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
                          `id` bigint(20) NOT NULL,
                          `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`id`, `name`) VALUES
                                        (1, 'homepage'),
                                        (2, 'sale'),
                                        (3, 'warehouse'),
                                        (4, 'statistics'),
                                        (5, 'discounts'),
                                        (6, 'receipts'),
                                        (7, 'export_notes'),
                                        (8, 'import_notes'),
                                        (9, 'products'),
                                        (10, 'suppliers'),
                                        (11, 'recipe'),
                                        (12, 'staffs'),
                                        (13, 'accounts'),
                                        (14, 'decentralization');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
                           `id` bigint(20) NOT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `category` varchar(255) DEFAULT NULL,
                           `price` double DEFAULT NULL,
                           `unit` varchar(255) DEFAULT NULL,
                           `image` varchar(255) DEFAULT NULL,
                           `deleted` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `category`, `price`, `unit`, `image`, `deleted`) VALUES
                                                                                          (1, 'abc', 'abc', 2, 'ad', 'asc', b'1'),
                                                                                          (2, 'long', 'abc', 50, 'd', 'aaa', b'1');

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
                           `id` bigint(20) NOT NULL,
                           `staff_id` bigint(20) DEFAULT NULL,
                           `total` double DEFAULT NULL,
                           `invoice_date` date DEFAULT NULL,
                           `received` double DEFAULT NULL,
                           `excess` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`id`, `staff_id`, `total`, `invoice_date`, `received`, `excess`) VALUES
    (1, 1, 0, '2024-02-07', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `receipt_detail`
--

CREATE TABLE `receipt_detail` (
                                  `receipt_id` bigint(20) NOT NULL,
                                  `product_id` bigint(20) NOT NULL,
                                  `quantity` double DEFAULT NULL,
                                  `price` double DEFAULT NULL,
                                  `discount_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `receipt_detail`
--

INSERT INTO `receipt_detail` (`receipt_id`, `product_id`, `quantity`, `price`, `discount_id`) VALUES
                                                                                                  (1, 1, 0, 0, 0),
                                                                                                  (1, 2, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `recipe`
--

CREATE TABLE `recipe` (
                          `product_id` bigint(20) NOT NULL,
                          `material_id` bigint(20) NOT NULL,
                          `quantity` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
                        `id` bigint(20) NOT NULL,
                        `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
                                      (0, 'Super Admin'),
                                      (1, 'Admin'),
                                      (2, 'Quản lý cửa hàng'),
                                      (3, 'Nhân viên bán hàng');

-- --------------------------------------------------------

--
-- Table structure for table `shipment`
--

CREATE TABLE `shipment` (
                            `id` bigint(20) NOT NULL,
                            `material_id` bigint(20) DEFAULT NULL,
                            `import_id` bigint(20) DEFAULT NULL,
                            `quantity` double DEFAULT NULL,
                            `unit_price` double DEFAULT NULL,
                            `mfg` date DEFAULT NULL,
                            `exp` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `shipment`
--

INSERT INTO `shipment` (`id`, `material_id`, `import_id`, `quantity`, `unit_price`, `mfg`, `exp`) VALUES
                                                                                                      (1, 1, 1, 22, 222, '2024-02-06', '2024-03-01'),
                                                                                                      (2, 2, 1, 50, 0, '2024-02-07', '2024-02-10');

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
                         `hourly_wage` double DEFAULT NULL,
                         `deleted` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`id`, `no`, `name`, `gender`, `birthdate`, `phone`, `address`, `email`, `hourly_wage`, `deleted`) VALUES
    (1, '079203023644', 'Admin', b'0', '2003-08-30', '0963333946', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'admin2003@gmail.com', 50000, b'0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `name`, `phone`, `address`, `email`, `deleted`) VALUES
                                                                                  (1, 'abc', '2', 'abc', 'c', b'0'),
                                                                                  (15, 'xyz', '0963333946', '514', 'a@gmail.com', b'1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
    ADD PRIMARY KEY (`id`),
    ADD KEY `fk_account_role` (`role_id`),
    ADD KEY `fk_account_staff` (`staff_id`);

--
-- Indexes for table `decentralization`
--
ALTER TABLE `decentralization`
    ADD PRIMARY KEY (`role_id`,`module_id`,`function_id`),
    ADD KEY `fk_decentralization_module` (`module_id`),
    ADD KEY `fk_decentralization_function` (`function_id`);

--
-- Indexes for table `discount`
--
ALTER TABLE `discount`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `discount_detail`
--
ALTER TABLE `discount_detail`
    ADD PRIMARY KEY (`discount_id`,`product_id`),
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
-- Indexes for table `material`
--
ALTER TABLE `material`
    ADD PRIMARY KEY (`id`),
    ADD KEY `fk_material_supplier` (`supplier_id`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
    ADD PRIMARY KEY (`id`),
    ADD KEY `fk_receipt_staff` (`staff_id`);

--
-- Indexes for table `receipt_detail`
--
ALTER TABLE `receipt_detail`
    ADD PRIMARY KEY (`receipt_id`,`product_id`),
    ADD KEY `fk_receipt_detail_product` (`product_id`),
    ADD KEY `fk_receipt_detail_discount` (`discount_id`);

--
-- Indexes for table `recipe`
--
ALTER TABLE `recipe`
    ADD PRIMARY KEY (`product_id`,`material_id`),
    ADD KEY `fk_recipe_material` (`material_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
    ADD PRIMARY KEY (`id`);

--
-- Indexes for table `shipment`
--
ALTER TABLE `shipment`
    ADD PRIMARY KEY (`id`),
    ADD KEY `fk_shipment_import_note` (`import_id`),
    ADD KEY `fk_shipment_material` (`material_id`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
    ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
    ADD PRIMARY KEY (`id`);

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
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
    ADD CONSTRAINT `fk_account_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
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
-- Constraints for table `material`
--
ALTER TABLE `material`
    ADD CONSTRAINT `fk_material_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `receipt`
--
ALTER TABLE `receipt`
    ADD CONSTRAINT `fk_receipt_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `receipt_detail`
--
ALTER TABLE `receipt_detail`
    ADD CONSTRAINT `fk_receipt_detail_discount` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `fk_receipt_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `fk_receipt_detail_receipt` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `recipe`
--
ALTER TABLE `recipe`
    ADD CONSTRAINT `fk_recipe_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `fk_recipe_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `shipment`
--
ALTER TABLE `shipment`
    ADD CONSTRAINT `fk_shipment_import_note` FOREIGN KEY (`import_id`) REFERENCES `import_note` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `fk_shipment_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
