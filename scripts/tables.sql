CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `wei_xin_open_id` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
  `role` int(8) DEFAULT NULL,
  `phone` varchar(16) CHARACTER SET utf8mb4 DEFAULT NULL,
  `name` varchar(16) CHARACTER SET utf8mb4 DEFAULT NULL,
  `address` varchar(128) CHARACTER SET utf8mb4 DEFAULT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_wx` (`wei_xin_open_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 DEFAULT COLLATE=utf8mb4_general_ci;

CREATE TABLE `order` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `status` int(8) DEFAULT NULL,
  `bottle_count` int(8) DEFAULT NULL,
  `carton_count` int(8) DEFAULT NULL,
  `scrap_iron_count` int(8) DEFAULT NULL,
  `other` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL,
  `remark` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL,
  `created_time` bigint(16) NOT NULL,
  `modified_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_uc` (`user_id`,`created_time`) USING BTREE,
  KEY `idx_c` (`created_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 DEFAULT COLLATE=utf8mb4_general_ci;

CREATE TABLE `token` (
  `id` bigint(16) NOT NULL,
  `user_id` int(10) NOT NULL,
  `token` varchar(32) CHARACTER SET utf8mb4 NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_uc` (`user_id`,`created_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 DEFAULT COLLATE=utf8mb4_general_ci;