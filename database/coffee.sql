-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 02, 2024 lúc 04:10 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `cafe`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `staff_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `staff_id`) VALUES
(1, 'longbott', '$2a$12$mQ/t9HUWSBZ2/I7Ff3qFAOajpfYqFyZxLZ9VDiFGYSbITcvoGxzre', 1); 

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `decentralization`
--

CREATE TABLE `decentralization` (
  `role_id` bigint(20) NOT NULL,
  `module_id` bigint(20) NOT NULL,
  `function_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `decentralization`
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
-- Cấu trúc bảng cho bảng `discount`
--

CREATE TABLE `discount` (
  `id` bigint(20) NOT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `discount`
--

INSERT INTO `discount` (`id`, `start_date`, `end_date`, `status`) VALUES
(0, '1000-01-01', '1000-01-01', b'0'),
(1, '2024-03-10', '2024-05-10', b'0'),
(2, '2024-03-10', '2024-05-10', b'1'),
(3, '2024-03-10', '2024-05-10', b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `discount_detail`
--

CREATE TABLE `discount_detail` (
  `discount_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `percent` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `discount_detail`
--

INSERT INTO `discount_detail` (`discount_id`, `product_id`, `percent`) VALUES
(1, 1, 20);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `export_detail`
--

CREATE TABLE `export_detail` (
  `export_id` bigint(20) NOT NULL,
  `shipment_id` bigint(20) NOT NULL,
  `quantity` double DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `export_detail`
--

INSERT INTO `export_detail` (`export_id`, `shipment_id`, `quantity`, `reason`) VALUES
(1, 1, 20, 'Ban');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `export_note`
--

CREATE TABLE `export_note` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `invoice_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `export_note`
--

INSERT INTO `export_note` (`id`, `staff_id`, `total`, `invoice_date`) VALUES
(1, 1, 0, '2024-02-07');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `function`
--

CREATE TABLE `function` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `function`
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
-- Cấu trúc bảng cho bảng `import_note`
--

CREATE TABLE `import_note` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `received_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `import_note`
--

INSERT INTO `import_note` (`id`, `staff_id`, `total`, `received_date`) VALUES
(1, 1, 0, '2024-02-06'),
(2, 1, 0, '2024-02-07');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `material`
--

CREATE TABLE `material` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `supplier_id` bigint(20) DEFAULT NULL,
  `remain` double DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `material`
--

INSERT INTO `material` (`id`, `name`, `supplier_id`, `remain`, `unit`, `deleted`) VALUES
(1, 'abc', 1, 3, 'd', b'1'),
(2, 'xzy', 1, 51, 'd', b'1'),
(3, 'Cà phê bột truyền thống', 3, 10, 'kg', b'1'),
(4, 'Đường túi', 3, 50, 'Túi', b'1'),
(5, 'Bánh cookie', 3, 100, 'Cái', b'1'),
(6, 'Đá viên', 3, 20000, 'g', b'1'),
(7, 'Sữa đặc', 2, 100, 'ml', b'1'),
(8, 'Nước nóng', 2, 100, 'ml', b'1'),
(9, 'Syrup hạnh nhân', 5, 100, 'ml', b'1'),
(10, 'Sữa tươi', 3, 100, 'ml', b'1'),
(11, 'Sữa Béo (NDC)', 5, 100, 'ml', b'1'),
(12, 'Thạch Cà Phê', 5, 500, 'g', b'1'),
(13, 'Milk foam', 2, 200, 'ml', b'1'),
(14, 'Bột chocolate trang trí', 3, 50, 'g', b'1'),
(15, 'Sốt chocolate', 2, 200, 'ml', b'1'),
(16, 'Cà phê đen pha sẵn', 4, 500, 'ml', b'1'),
(18, 'Đường nước ', 3, 100, 'g', b'1'),
(19, 'Trà đào pha sẵn ', 2, 100, 'ml', b'1'),
(20, 'Đào lát', 4, 100, 'g', b'1'),
(21, 'Hạt sen', 1, 200, 'g', b'1'),
(22, 'Củ năng', 3, 500, 'g', b'1'),
(23, 'Syrup vải', 2, 200, 'ml', b'1'),
(24, 'Nước vải ngâm', 2, 500, 'ml', b'1'),
(25, 'Vải trái', 2, 200, 'trái', b'1'),
(26, 'Thạch vải ', 4, 100, 'g', b'1'),
(27, 'Bột freeze mix', 4, 200, 'g', b'1'),
(28, 'Whipping cream ', 3, 50, 'g', b'1'),
(29, 'Bột cà phê espresso', 3, 1, 'lít', b'1'),
(30, 'Bột green tea mix mới ', 3, 100, 'g', b'1'),
(31, 'Thạch trà xanh ', 3, 500, 'g', b'1'),
(32, 'Bột trà xanh trang trí', 3, 200, 'g', b'1'),
(33, 'Hỗn hợp sữa pha sẵn', 4, 500, 'ml', b'1'),
(34, 'Bột chocolate ', 4, 200, 'g', b'1'),
(35, 'Sốt chocolate trang trí', 3, 50, 'ml', b'1'),
(36, 'Cà phê sữa pha sẵn', 2, 500, 'ml', b'1'),
(37, 'Thạch cà phê', 4, 300, 'g', b'1'),
(38, 'Trà oolong pha sẵn ', 2, 200, 'ml', b'1'),
(39, 'Thạch Đào', 3, 200, 'g', b'1'),
(40, 'Syrup Đào', 4, 100, 'ml', b'1'),
(41, 'Syrup sả', 3, 100, 'ml', b'1'),
(42, 'Sốt caramel', 3, 100, 'ml', b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `module`
--

CREATE TABLE `module` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `module`
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
-- Cấu trúc bảng cho bảng `payroll`
--

CREATE TABLE `payroll` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `hours_amount` double DEFAULT NULL,
  `bonus_amount` double DEFAULT NULL,
  `deduction_amount` double DEFAULT NULL,
  `salary_amount` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `Size` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `name`, `category`, `price`, `image`, `deleted`, `Size`) VALUES
(1, 'abc', 'abc', 2, 'asc', b'1', '0'),
(1, 'Phin Đen Đá', 'CÀ PHÊ PHIN', 39, 'SP01', b'1', 'L'),
(1, 'Phin Đen Đá', 'CÀ PHÊ PHIN', 35, 'SP01', b'1', 'M'),
(1, 'Phin Đen Đá', 'CÀ PHÊ PHIN', 29, 'SP01', b'1', 'S'),
(2, 'long', 'abc', 50, 'aaa', b'1', '0'),
(2, 'Phin Sữa Đá', 'CÀ PHÊ PHIN', 45, 'SP02', b'1', 'L'),
(2, 'Phin Sữa Đá ', 'CÀ PHÊ PHIN', 39, 'SP02', b'1', 'M'),
(2, 'Phin Sữa Đá', 'CÀ PHÊ PHIN', 29, 'SP02', b'1', 'S'),
(3, 'Bạc Xỉu', 'CÀ PHÊ PHIN', 45, 'SP03', b'1', 'L'),
(3, 'Bạc Xỉu ', 'CÀ PHÊ PHIN', 39, 'SP03', b'1', 'M'),
(3, 'Bạc Xỉu', 'CÀ PHÊ PHIN', 29, 'SP03', b'1', 'S'),
(4, 'Trà Sen Vàng', 'TRÀ', 65, 'SP04', b'1', 'L'),
(4, 'Trà Sen Vàng', 'TRÀ', 55, 'SP04', b'1', 'M'),
(4, 'Trà Sen Vàng', 'TRÀ', 45, 'SP04', b'1', 'S'),
(5, 'Trà Thạch Đào', 'TRÀ', 65, 'SP05', b'1', 'L'),
(5, 'Trà Thạch Đào', 'TRÀ', 55, 'SP05', b'1', 'M'),
(5, 'Trà Thạch Đào', 'TRÀ', 45, 'SP05', b'1', 'S'),
(6, 'Trà Thanh Đào', 'TRÀ', 65, 'SP06', b'1', 'L'),
(6, 'Trà Thanh Đào', 'TRÀ', 55, 'SP06', b'1', 'M'),
(6, 'Trà Thanh Đào', 'TRÀ', 45, 'SP06', b'1', 'S'),
(7, 'Trà Thạch Vãi', 'TRÀ', 65, 'SP07', b'1', 'L'),
(7, 'Trà Thạch Vãi', 'TRÀ', 55, 'SP07', b'1', 'M'),
(7, 'Trà Thạch Vãi', 'TRÀ', 45, 'SP07', b'1', 'S'),
(8, 'Bánh Chuối', 'BÁNH', 29, 'SP08', b'1', '0'),
(9, 'Bánh Su Kem', 'BÁNH', 29, 'SP09', b'1', '0'),
(10, 'Phô Mai Chanh Dây', 'BÁNH', 29, 'SP10', b'1', '0'),
(11, 'Phô Mai Trà Xanh', 'BÁNH', 29, 'SP11', b'1', '0'),
(12, 'PhinDi Hạnh Nhân', 'PHINDI', 55, 'SP12', b'1', 'L'),
(12, 'PhinDi Hạnh Nhân', 'PHINDI', 49, 'SP12', b'1', 'M'),
(12, 'Phindi Hạnh Nhân', 'PHINDI', 45, 'SP12', b'1', 'S'),
(13, 'PhinDi Kem Sữa', 'PHINDI', 55, 'SP13', b'1', 'L'),
(13, 'PhinDi Kem Sữa', 'PHINDI', 49, 'SP13', b'1', 'M'),
(13, 'PhinDi Kem Sữa', 'PHINDI', 45, 'SP13', b'1', 'S'),
(14, 'PhinDi Choco', 'PHINDI', 55, 'SP14', b'1', 'L'),
(14, 'PhinDi Choco', 'PHINDI', 49, 'SP14', b'1', 'M'),
(14, 'PhinDi Choco', 'PHINDI', 45, 'SP14', b'1', 'S'),
(15, 'Freeze Trà Xanh', 'FREEZE ', 69, 'SP15', b'1', 'L'),
(15, 'Freeze Trà Xanh', 'FREEZE', 65, 'SP15', b'1', 'M'),
(15, 'Freeze Trà Xanh', 'FREEZE', 55, 'SP15', b'1', 'S'),
(16, 'Caramel Phin Freeze', 'FREEZE', 69, 'SP16', b'1', 'L'),
(16, 'Caramel Phin Freeze', 'FREEZE', 65, 'SP16', b'1', 'M'),
(16, 'Caramel Phin Freeze', 'FREEZE', 55, 'SP16', b'1', 'S'),
(17, 'Freeze Sô-Cô-La', 'FREEZE', 69, 'SP17', b'1', 'L'),
(17, 'Freeze Sô-Cô-La', 'FREEZE', 65, 'SP17', b'1', 'M'),
(17, 'Freeze Sô-Cô-La', 'FREEZE', 55, 'SP17', b'1', 'S'),
(18, 'Classic Phin Freeze', 'FREEZE', 69, 'SP18', b'1', 'L'),
(18, 'Classic Phin Freeze', 'FREEZE', 65, 'SP18', b'1', 'M'),
(18, 'Classic Phin Freeze', 'FREEZE', 55, 'SP18', b'1', 'S');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `receipt`
--

CREATE TABLE `receipt` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `received` double DEFAULT NULL,
  `excess` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `receipt`
--

INSERT INTO `receipt` (`id`, `staff_id`, `total`, `invoice_date`, `received`, `excess`) VALUES
(1, 1, 0, '2024-02-07', 0, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `receipt_detail`
--

CREATE TABLE `receipt_detail` (
  `receipt_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` double DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `receipt_detail`
--

INSERT INTO `receipt_detail` (`receipt_id`, `product_id`, `quantity`, `price`) VALUES
(1, 1, 0, 0),
(1, 2, 0, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `recipe`
--

CREATE TABLE `recipe` (
  `product_id` bigint(20) NOT NULL,
  `material_id` bigint(20) NOT NULL,
  `quantity` double DEFAULT NULL,
  `Size` varchar(20) NOT NULL,
  `unit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `recipe`
--

INSERT INTO `recipe` (`product_id`, `material_id`, `quantity`, `Size`, `unit`) VALUES
(1, 6, 400, 'L', 'g'),
(1, 6, 260, 'M', 'g'),
(1, 6, 200, 'S', 'g'),
(1, 16, 60, 'L', 'ml'),
(1, 16, 50, 'M', 'ml'),
(1, 16, 40, 'S', 'ml'),
(1, 18, 25, 'L', 'ml'),
(1, 18, 20, 'M', 'ml'),
(1, 18, 15, 'S', 'ml'),
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
(4, 18, 45, 'L', 'ml'),
(4, 18, 40, 'M', 'ml'),
(4, 18, 30, 'S', 'ml'),
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
(5, 18, 45, 'L', 'ml'),
(5, 18, 40, 'M', 'ml'),
(5, 18, 30, 'S', 'ml'),
(5, 19, 100, 'L', 'ml'),
(5, 19, 80, 'M', 'ml'),
(5, 19, 50, 'S', 'ml'),
(5, 20, 40, 'L', 'g'),
(5, 20, 40, 'M', 'g'),
(5, 20, 30, 'S', 'g'),
(5, 39, 75, 'L', 'g'),
(5, 39, 65, 'M', 'g'),
(5, 39, 50, 'S', 'g'),
(6, 6, 0, '280', 'g'),
(6, 6, 300, 'L', 'g'),
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
(12, 18, 15, 'L', 'ml'),
(12, 18, 10, 'M', 'nl'),
(12, 18, 10, 'S', 'ml'),
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
(13, 13, 0, '45', 'ml'),
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
(15, 18, 25, 'L', 'ml'),
(15, 18, 20, 'M', 'ml'),
(15, 18, 15, 's', 'ml'),
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
(17, 10, 0, '90', 'ml'),
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
(17, 37, 100, 'S', 'g');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(0, 'Super Admin'),
(1, 'Admin'),
(2, 'Quản lý cửa hàng'),
(3, 'Nhân viên bán hàng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role_detail`
--

CREATE TABLE `role_detail` (
  `role_id` bigint(20) NOT NULL,
  `staff_id` bigint(20) NOT NULL,
  `entry_date` date NOT NULL,
  `salary` float DEFAULT NULL,
  `hourly_wage` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `shipment`
--

CREATE TABLE `shipment` (
  `id` bigint(20) NOT NULL,
  `material_id` bigint(20) DEFAULT NULL,
  `import_id` bigint(20) DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `unit_price` double DEFAULT NULL,
  `mfg` date DEFAULT NULL,
  `exp` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `shipment`
--

INSERT INTO `shipment` (`id`, `material_id`, `import_id`, `quantity`, `unit_price`, `mfg`, `exp`) VALUES
(1, 1, 1, 22, 222, '2024-02-06', '2024-03-01'),
(2, 2, 1, 50, 0, '2024-02-07', '2024-02-10');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `staff`
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
  `deleted` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `staff`
--

INSERT INTO `staff` (`id`, `no`, `name`, `gender`, `birthdate`, `phone`, `address`, `email`, `deleted`) VALUES
(1, '079203023644', 'Admin', b'0', '2003-08-30', '0963333946', '514/26 Lê Đức Thọ P17 Gò Vấp TPHCM', 'admin2003@gmail.com', b'0');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `supplier`
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
-- Đang đổ dữ liệu cho bảng `supplier`
--

INSERT INTO `supplier` (`id`, `name`, `phone`, `address`, `email`, `deleted`) VALUES
(1, 'abc', '2', 'abc', 'c', b'0'),
(2, 'Drai Farm', '0917 762 211', 'xã Quảng Hiệp, huyện Cư M’gar, tỉnh DakLak', 'DraiFarmcoffee@gmail.com', b'0'),
(3, 'Sơn Việt Coffee', '0937442338', '148 Lý Thái Tổ, Thôn 6, DamBri, Bảo Lộc, Lâm Đồng', 'sonvietcoffe@gmail.com', b'1'),
(4, 'Cà phê Triều Nguyên', '0966770770', '120A Lý Thái Tổ, Đamb’ri, Bảo Lộc, Lâm Đồng', 'trieunguyencoffe@gmail.com', b'1'),
(5, 'Công ty TNHH SX & TM Hucafood ', '0935551919', 'Tổ 9 Hòa Bắc, Khánh Hòa', 'hucafoodcoffee@gmail.com', b'1'),
(15, 'xyz', '0963333946', '514', 'a@gmail.com', b'1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `work_schedule`
--

CREATE TABLE `work_schedule` (
  `id` bigint(20) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_account_staff` (`staff_id`);

--
-- Chỉ mục cho bảng `decentralization`
--
ALTER TABLE `decentralization`
  ADD PRIMARY KEY (`role_id`,`module_id`,`function_id`),
  ADD KEY `fk_decentralization_module` (`module_id`),
  ADD KEY `fk_decentralization_function` (`function_id`);

--
-- Chỉ mục cho bảng `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `discount_detail`
--
ALTER TABLE `discount_detail`
  ADD PRIMARY KEY (`discount_id`,`product_id`),
  ADD KEY `fk_discount_detail_product` (`product_id`);

--
-- Chỉ mục cho bảng `export_detail`
--
ALTER TABLE `export_detail`
  ADD PRIMARY KEY (`export_id`,`shipment_id`) USING BTREE,
  ADD KEY `fk_export_detail` (`shipment_id`);

--
-- Chỉ mục cho bảng `export_note`
--
ALTER TABLE `export_note`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_export_note_staff` (`staff_id`);

--
-- Chỉ mục cho bảng `function`
--
ALTER TABLE `function`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `import_note`
--
ALTER TABLE `import_note`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_import_note_staff` (`staff_id`);

--
-- Chỉ mục cho bảng `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_material_supplier` (`supplier_id`);

--
-- Chỉ mục cho bảng `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `payroll`
--
ALTER TABLE `payroll`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_payroll_staff` (`staff_id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`,`Size`);

--
-- Chỉ mục cho bảng `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_receipt_staff` (`staff_id`);

--
-- Chỉ mục cho bảng `receipt_detail`
--
ALTER TABLE `receipt_detail`
  ADD PRIMARY KEY (`receipt_id`,`product_id`),
  ADD KEY `fk_receipt_detail_product` (`product_id`);

--
-- Chỉ mục cho bảng `recipe`
--
ALTER TABLE `recipe`
  ADD PRIMARY KEY (`product_id`,`material_id`,`Size`),
  ADD KEY `fk_recipe_material` (`material_id`),
  ADD KEY `fk_recipe_product1` (`Size`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `role_detail`
--
ALTER TABLE `role_detail`
  ADD PRIMARY KEY (`role_id`,`staff_id`,`entry_date`) USING BTREE,
  ADD KEY `fk_role_detail_staff` (`staff_id`);

--
-- Chỉ mục cho bảng `shipment`
--
ALTER TABLE `shipment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_shipment_import_note` (`import_id`),
  ADD KEY `fk_shipment_material` (`material_id`);

--
-- Chỉ mục cho bảng `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Chỉ mục cho bảng `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `work_schedule`
--
ALTER TABLE `work_schedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_work_schedule_staff` (`staff_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `function`
--
ALTER TABLE `function`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `module`
--
ALTER TABLE `module`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `fk_account_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `decentralization`
--
ALTER TABLE `decentralization`
  ADD CONSTRAINT `fk_decentralization_function` FOREIGN KEY (`function_id`) REFERENCES `function` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_decentralization_module` FOREIGN KEY (`module_id`) REFERENCES `module` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_decentralization_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `discount_detail`
--
ALTER TABLE `discount_detail`
  ADD CONSTRAINT `fk_discount_detail_discount` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_discount_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `export_detail`
--
ALTER TABLE `export_detail`
  ADD CONSTRAINT `fk_export_detail` FOREIGN KEY (`shipment_id`) REFERENCES `shipment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_export_detail_export_note` FOREIGN KEY (`export_id`) REFERENCES `export_note` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `export_note`
--
ALTER TABLE `export_note`
  ADD CONSTRAINT `fk_export_note_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `import_note`
--
ALTER TABLE `import_note`
  ADD CONSTRAINT `fk_import_note_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `material`
--
ALTER TABLE `material`
  ADD CONSTRAINT `fk_material_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `payroll`
--
ALTER TABLE `payroll`
  ADD CONSTRAINT `fk_payroll_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `receipt`
--
ALTER TABLE `receipt`
  ADD CONSTRAINT `fk_receipt_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `receipt_detail`
--
ALTER TABLE `receipt_detail`
  ADD CONSTRAINT `fk_receipt_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_receipt_detail_receipt` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `recipe`
--
ALTER TABLE `recipe`
  ADD CONSTRAINT `fk_recipe_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_recipe_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `role_detail`
--
ALTER TABLE `role_detail`
  ADD CONSTRAINT `fk_role_detail_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_role_detail_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `shipment`
--
ALTER TABLE `shipment`
  ADD CONSTRAINT `fk_shipment_import_note` FOREIGN KEY (`import_id`) REFERENCES `import_note` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_shipment_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `work_schedule`
--
ALTER TABLE `work_schedule`
  ADD CONSTRAINT `fk_work_schedule_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
