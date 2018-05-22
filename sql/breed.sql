/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : breed

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 22/05/2018 23:42:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `model` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备型号',
  `type` int(11) NULL DEFAULT NULL COMMENT '设备类型',
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备图片，多个用英文逗号分隔',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备描述',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_type`(`type`) USING BTREE,
  CONSTRAINT `fk_type` FOREIGN KEY (`type`) REFERENCES `device_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '紫光增氧001号', 'ZG-ZY-001', 4, NULL, '紫光增氧001号，功率大，覆盖广', '2018-04-25 21:18:00', NULL);
INSERT INTO `device` VALUES ('2', '紫光增氧002号', 'ZG-ZY-002', 4, NULL, '紫光增氧002号，功率小，精度高', '2018-04-25 21:18:44', NULL);
INSERT INTO `device` VALUES ('3', '天河增氧A型', 'TIANHE-ZY-A', 5, NULL, '天河增氧A型，延续天河一贯的高性价比', '2018-04-25 21:20:42', NULL);
INSERT INTO `device` VALUES ('4', '富农X10', 'FUNONG-X10', 6, NULL, '富农X10增氧机，含氧量高', '2018-04-25 21:21:39', NULL);
INSERT INTO `device` VALUES ('5', '紫光农药喷洒1代', 'ZHONGMIN-PS-P1', 7, NULL, '覆盖率在同价位中较广', '2018-04-25 21:22:53', NULL);

-- ----------------------------
-- Table structure for device_maintenance
-- ----------------------------
DROP TABLE IF EXISTS `device_maintenance`;
CREATE TABLE `device_maintenance`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID',
  `imei` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备IMEI',
  `profit` float(11, 0) NULL DEFAULT NULL COMMENT '收益',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_device`(`device_id`) USING BTREE,
  CONSTRAINT `fk_device` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备维护表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for device_order
-- ----------------------------
DROP TABLE IF EXISTS `device_order`;
CREATE TABLE `device_order`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单ID',
  `provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商ID',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '购买者（用户）ID',
  `price` float(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_provider_ID`(`provider_id`) USING BTREE,
  CONSTRAINT `fk_providerid` FOREIGN KEY (`provider_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_order
-- ----------------------------
INSERT INTO `device_order` VALUES ('a13213af23af3a2vaf23bf61x33134', '74d8f29914414d3d83c4072bf61b2537', '7b3e48298a0b4725a2313fa0b85c0774', 267.50, '无发票', '2018-05-02 18:21:33', NULL);
INSERT INTO `device_order` VALUES ('ac8f299afafaba83cafa2bf61xx537', '74d8f29914414d3d83c4072bf61b2537', '7b3e48298a0b4725a2313fa0b85c0774', 185.40, NULL, '2018-05-17 18:19:55', NULL);
INSERT INTO `device_order` VALUES ('afqr12131dac1231sac7yfaovad13', '74d8f29914414d3d83c4072bf61b2537', '908ed25218dc4be588f285e5fed13b51', 553.52, NULL, '2018-05-17 18:23:18', NULL);

-- ----------------------------
-- Table structure for device_order_desc
-- ----------------------------
DROP TABLE IF EXISTS `device_order_desc`;
CREATE TABLE `device_order_desc`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `device_order` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备号',
  `imei` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备串号',
  `price` float(10, 2) NOT NULL DEFAULT 0.00 COMMENT '价格',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_device_order`(`device_order`) USING BTREE,
  CONSTRAINT `fk_device_order` FOREIGN KEY (`device_order`) REFERENCES `device_order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_order_desc
-- ----------------------------
INSERT INTO `device_order_desc` VALUES ('afagvasgwet1237yfaovad13', 'a13213af23af3a2vaf23bf61x33134', '1', '12GYT3', 123.50, '这是该订单的第一件设备');
INSERT INTO `device_order_desc` VALUES ('afagvasgwet1237yfaovad14', 'a13213af23af3a2vaf23bf61x33134', '2', '1XA3GY', 121.00, NULL);
INSERT INTO `device_order_desc` VALUES ('hvakafaerkqavaz2341fcas14', 'a13213af23af3a2vaf23bf61x33134', '3', 'MHF54Z', 23.00, '优惠商品');

-- ----------------------------
-- Table structure for device_type
-- ----------------------------
DROP TABLE IF EXISTS `device_type`;
CREATE TABLE `device_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `parent_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device_type
-- ----------------------------
INSERT INTO `device_type` VALUES (1, '增氧机', NULL);
INSERT INTO `device_type` VALUES (2, '喷洒机', NULL);
INSERT INTO `device_type` VALUES (3, '控制器', NULL);
INSERT INTO `device_type` VALUES (4, '紫光', 1);
INSERT INTO `device_type` VALUES (5, '天河', 1);
INSERT INTO `device_type` VALUES (6, '富农', 2);
INSERT INTO `device_type` VALUES (7, '紫光', 2);

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_used` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pool
-- ----------------------------
DROP TABLE IF EXISTS `pool`;
CREATE TABLE `pool`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '塘口名称',
  `areas` double NULL DEFAULT NULL COMMENT '塘口面积（单位：亩）',
  `depth` double NULL DEFAULT NULL COMMENT '塘口深度（单位：米）',
  `aquatic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '塘口中的水产品种（多个用逗号分隔）',
  `density` double NULL DEFAULT NULL COMMENT '投放密度（单位：尾/亩）',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属用户',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '塘口表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pool
-- ----------------------------
INSERT INTO `pool` VALUES ('62e086afed7043979a65396958e518ab', '西1号塘', 103.6, 7.98, '螃蟹', 50, '7b3e48298a0b4725a2313fa0b85c0774', '2018-04-27 14:11:39', '2018-04-27 14:20:13');
INSERT INTO `pool` VALUES ('85f020cf1ca74a3785a058b28c3bdb88', '东1号塘', 200.32, 19.7, '鲫鱼,草鱼', 223, '7b3e48298a0b4725a2313fa0b85c0774', '2018-04-26 15:06:49', '2018-04-27 13:53:07');
INSERT INTO `pool` VALUES ('b8d5971f0a7441ee87537ebcbc852449', '北1号塘', 108.6, 23.6, '鲫鱼', 165, '7b3e48298a0b4725a2313fa0b85c0774', '2018-04-27 13:53:39', NULL);

-- ----------------------------
-- Table structure for provider_device
-- ----------------------------
DROP TABLE IF EXISTS `provider_device`;
CREATE TABLE `provider_device`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `provider_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商ID',
  `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备ID',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存',
  `sale_num` int(11) NULL DEFAULT NULL COMMENT '售出数量',
  `remark` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态（1：上架；0：下架）',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_provider`(`provider_id`) USING BTREE,
  CONSTRAINT `fk_provider` FOREIGN KEY (`provider_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '供应商设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of provider_device
-- ----------------------------
INSERT INTO `provider_device` VALUES ('0ea780beaf3d4ef09f671307dc0ca3fb', '74d8f29914414d3d83c4072bf61b2537', '4', 20, NULL, '<div><h2><span style=\"color: rgb(194, 79, 74);\"><span style=\"background-color: rgb(255, 255, 255);\">富农喷洒</span></span>就是好!!<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\"></h2></div><img src=\"https://jitwxs-1252917613.cos.ap-shanghai.myqcloud.com/breed/pic/7578349632_515464872.400x400.jpg\" style=\"max-width:100%;\"><p><br></p>', 1, '2018-05-22 18:37:32', '2018-05-22 19:05:27');
INSERT INTO `provider_device` VALUES ('12x8f29914414d3s2vsd72bf61b2537', '74d8f29914414d3d83c4072bf61b2537', '1', 110, 26, '', 0, '2018-05-17 15:49:10', '2018-05-18 17:14:32');
INSERT INTO `provider_device` VALUES ('23qagf29914414d3ssqh72bf61b227', '74d8f29914414d3d83c4072bf61b2537', '2', 60, 12, '', 1, '2018-05-17 16:09:03', '2018-05-18 17:14:21');
INSERT INTO `provider_device` VALUES ('38a45986a39e4779ac5647f21a7cdff4', '74d8f29914414d3d83c4072bf61b2537', '5', 25, NULL, '哇咔咔                                    ', 1, '2018-05-17 17:35:14', '2018-05-18 15:24:45');
INSERT INTO `provider_device` VALUES ('54206997360743e6820478e617316d60', '74d8f29914414d3d83c4072bf61b2537', '5', 12, NULL, '                    1222                ', 1, '2018-05-18 17:14:51', NULL);
INSERT INTO `provider_device` VALUES ('afa2f235143sbaet23edasqh72bf54', '74d8f29914414d3d83c4072bf61b2537', '3', 23, 0, NULL, 0, '2018-05-17 16:46:46', '2018-05-17 17:13:00');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('ROLE_ADMIN', 'ROLE_管理员');
INSERT INTO `sys_role` VALUES ('ROLE_PROVIDER', 'ROLE_供应商');
INSERT INTO `sys_role` VALUES ('ROLE_USER', 'ROLE_用户');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('74d8f29914414d3d83c4072bf61b2537', NULL, 'wxs', 'e5a2fcfcf3a01276e43cc0fb0fda79e7c36f217053196fb1dfe37d81', '男', 'wxs@foxmail.com', 'https://jitwxs-1252917613.cos.ap-shanghai.myqcloud.com/breed/pic/邮箱.png', '2018-05-03 11:48:25', '2018-05-17 15:23:24');
INSERT INTO `sys_user` VALUES ('7b3e48298a0b4725a2313fa0b85c0774', '18168404329', 'jitwxs', 'c37c6d9a886d4ba49b81f8c4b2dabdbbb4c2c17413b3202577bb1850', '男', 'jitwxs@foxmail.com', 'https://jitwxs-1252917613.cos.ap-shanghai.myqcloud.com/breed/pic/1.jpg', '2018-04-25 17:09:10', '2018-05-17 01:17:50');
INSERT INTO `sys_user` VALUES ('908ed25218dc4be588f285e5fed13b51', NULL, 'test', 'ab2038077d242abeed1c0024b1ed7bbf74537d74eb164a52b9098c15', NULL, NULL, NULL, '2018-05-17 10:43:29', NULL);
INSERT INTO `sys_user` VALUES ('b2c440caee214af68fd6731f1a388e6f', '17626014329', 'demo', 'b95eaf9f742b0d442185b21c7fc93856ecd5fb60aa760334a1385834', '男', '17626014329@gmail.com', 'https://jitwxs-1252917613.cos.ap-shanghai.myqcloud.com/breed/pic/邮箱.png', '2018-05-17 10:44:59', '2018-05-17 10:52:06');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_id`(`user_id`) USING BTREE,
  INDEX `fk_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('0f4653d8f8fb4f3098730ddaf14c0325', '908ed25218dc4be588f285e5fed13b51', 'ROLE_USER');
INSERT INTO `sys_user_role` VALUES ('9155a7e2f46d4f7999cb1f062c480b47', 'b2c440caee214af68fd6731f1a388e6f', 'ROLE_USER');
INSERT INTO `sys_user_role` VALUES ('99c2b9417dfa4786b6f60b6c3f7f46fe', '74d8f29914414d3d83c4072bf61b2537', 'ROLE_PROVIDER');
INSERT INTO `sys_user_role` VALUES ('b8d5971f0a7441ee87532cacsacbc85', '7b3e48298a0b4725a2313fa0b85c0774', 'ROLE_USER');

-- ----------------------------
-- Table structure for user_device
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `pool_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '塘口id',
  `device_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备id',
  `imei` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备串号',
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备参数（JSON格式）',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '设备状态（true：开启；false：关闭）',
  `create_date` datetime(0) NOT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_userId`(`user_id`) USING BTREE,
  INDEX `fk_deviceId`(`device_id`) USING BTREE,
  INDEX `fk_poolId`(`pool_id`) USING BTREE,
  CONSTRAINT `fk_deviceId` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_poolId` FOREIGN KEY (`pool_id`) REFERENCES `pool` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_userId` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户设备关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_device
-- ----------------------------
INSERT INTO `user_device` VALUES ('002eb711b190428387c8e580fb5a2476', '908ed25218dc4be588f285e5fed13b51', NULL, '1', 'QAJU765V0P', '{\"温度\":\"23.1°C\",\"溶氧浓度\":\"12.3mg/L\"}', 0, '2018-04-27 13:48:27', '2018-05-10 09:08:10');
INSERT INTO `user_device` VALUES ('14c9c4d79b564d5bb606ab620311fae8', '7b3e48298a0b4725a2313fa0b85c0774', '85f020cf1ca74a3785a058b28c3bdb88', '5', 'GYTVLGH89P', '{\"剩余时间\":\"23min\",\"剩余药量\":\"2303g\"}', 1, '2018-04-27 13:48:06', '2018-05-18 17:11:40');
INSERT INTO `user_device` VALUES ('4f6b73fbd12d43449b7bae1d10d887d6', '7b3e48298a0b4725a2313fa0b85c0774', '85f020cf1ca74a3785a058b28c3bdb88', '1', '2D6JG9LM5W', '{\"温度\":\"21.3°C\",\"溶氧浓度\":\"18.5mg/L\"}', 0, '2018-04-27 13:46:35', '2018-05-18 17:11:05');
INSERT INTO `user_device` VALUES ('813180a2c75e4873a8dbedf8e5e8bfd0', '7b3e48298a0b4725a2313fa0b85c0774', '85f020cf1ca74a3785a058b28c3bdb88', '2', '2XSNJ9LFZ5', NULL, 0, '2018-05-04 10:52:49', '2018-05-18 17:11:05');
INSERT INTO `user_device` VALUES ('820428b4e4ca43c1b065da9af183e187', '7b3e48298a0b4725a2313fa0b85c0774', '85f020cf1ca74a3785a058b28c3bdb88', '1', 'QBVG54VKI8', '{\"温度\":\"18.8°C\",\"溶氧浓度\":\"16.3mg/L\"}', 1, '2018-04-27 13:48:53', '2018-05-18 17:11:05');
INSERT INTO `user_device` VALUES ('b51fe0729abd40b99c5ce8d1b4815b23', '7b3e48298a0b4725a2313fa0b85c0774', '85f020cf1ca74a3785a058b28c3bdb88', '1', 'SCH4871VG8', '{\"温度\":\"22.5°C\",\"溶氧浓度\":\"13.2mg/L\"}', 1, '2018-05-04 10:53:17', '2018-05-18 17:11:05');
INSERT INTO `user_device` VALUES ('c98216b5ac1e4c79b1a68594c7f93ed6', '7b3e48298a0b4725a2313fa0b85c0774', NULL, '3', 'S35HBV9KH3', '{\"设备温度\":\"16.23°C\",\"溶氧浓度\":\"16.65mg/L\"}', 1, '2018-04-27 13:46:55', '2018-05-17 17:15:37');
INSERT INTO `user_device` VALUES ('ecde56342a944300984c838196c2e239', '7b3e48298a0b4725a2313fa0b85c0774', NULL, '4', '3DF8JIP0ND', NULL, 1, '2018-05-04 10:52:30', '2018-05-17 17:15:37');

SET FOREIGN_KEY_CHECKS = 1;
