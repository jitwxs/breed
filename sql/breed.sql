/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : breed

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-04-27 14:55:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `model_num` varchar(255) DEFAULT NULL COMMENT '设备型号',
  `description` varchar(255) DEFAULT NULL COMMENT '设备描述',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设备表';

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('1', '紫光增氧001号', 'ZG-ZY-001', '紫光增氧001号，功率大，覆盖广', '2018-04-25 21:18:00', null);
INSERT INTO `device` VALUES ('2', '紫光增氧002号', 'ZG-ZY-002', '紫光增氧002号，功率小，精度高', '2018-04-25 21:18:44', null);
INSERT INTO `device` VALUES ('3', '天河增氧A型', 'TIANHE-ZY-A', '天河增氧A型，延续天河一贯的高性价比', '2018-04-25 21:20:42', null);
INSERT INTO `device` VALUES ('4', '富农X10', 'FUNONG-X10', '富农X10增氧机，含氧量高', '2018-04-25 21:21:39', null);
INSERT INTO `device` VALUES ('5', '中民农药喷洒1代', 'ZHONGMIN-PS-P1', '覆盖率在同价位中较广', '2018-04-25 21:22:53', null);

-- ----------------------------
-- Table structure for pool
-- ----------------------------
DROP TABLE IF EXISTS `pool`;
CREATE TABLE `pool` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '塘口名称',
  `areas` double DEFAULT NULL COMMENT '塘口面积（单位：亩）',
  `depth` double DEFAULT NULL COMMENT '塘口深度（单位：米）',
  `aquatic` varchar(255) DEFAULT NULL COMMENT '塘口中的水产品种（多个用逗号分隔）',
  `density` double DEFAULT NULL COMMENT '投放密度（单位：尾/亩）',
  `user_id` varchar(255) DEFAULT NULL COMMENT '所属用户',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user` (`user_id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='塘口表';

-- ----------------------------
-- Records of pool
-- ----------------------------
INSERT INTO `pool` VALUES ('33aac67096714cc0b4dab1fe8067f28f', '左侧塘口', '76', '12.3', '鲤鱼', '100', '610f5f8b19764a7fa7779d49a4cacc38', '2018-04-27 14:52:03', null);
INSERT INTO `pool` VALUES ('62e086afed7043979a65396958e518ab', '西1号塘', '103.6', '7.98', '螃蟹', '50', '7b3e48298a0b4725a2313fa0b85c0774', '2018-04-27 14:11:39', '2018-04-27 14:20:13');
INSERT INTO `pool` VALUES ('774eaf0b4ec04003abd67450ed37a41e', '南1号塘', '32.6', '4', '鲤鱼', '76', '7b3e48298a0b4725a2313fa0b85c0774', '2018-04-27 14:11:41', '2018-04-27 14:09:37');
INSERT INTO `pool` VALUES ('85f020cf1ca74a3785a058b28c3bdb88', '东1号塘', '200.32', '19.7', '鲫鱼,草鱼', '223', '7b3e48298a0b4725a2313fa0b85c0774', '2018-04-26 15:06:49', '2018-04-27 13:53:07');
INSERT INTO `pool` VALUES ('adfba13cf39e4a93bb7df3301d8d61f4', '右侧塘口', '15.3', '5.3', '龙虾', '30', '610f5f8b19764a7fa7779d49a4cacc38', '2018-04-27 14:52:31', null);
INSERT INTO `pool` VALUES ('b8d5971f0a7441ee87537ebcbc852449', '北1号塘', '108.6', '23.6', '鲫鱼', '165', '7b3e48298a0b4725a2313fa0b85c0774', '2018-04-27 13:53:39', null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('ROLE_AREA_ADMIN', 'ROLE_地区管理员');
INSERT INTO `sys_role` VALUES ('ROLE_SYS_ADMIN', 'ROLE_系统管理员');
INSERT INTO `sys_role` VALUES ('ROLE_USER', 'ROLE_用户');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(255) NOT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL COMMENT '头像',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('610f5f8b19764a7fa7779d49a4cacc38', null, 'wuxiangsheng', '2a6567e10b63ae7b17f4229d7fa4f5400ef3b6255f1c67362aae0dd3', '男', '', null, '2018-04-27 14:44:19', '2018-04-27 14:48:19');
INSERT INTO `sys_user` VALUES ('7b3e48298a0b4725a2313fa0b85c0774', '17626014329', 'jitwxs', '190dc0a9bb80ed06de79d4058e68519ea327b0b3a30835eb28ac1fd7', '男', 'jitwxs@foxmail.com', 'https://jitwxs-1252917613.cos.ap-shanghai.myqcloud.com/breed/pic/1.jpg', '2018-04-25 17:09:10', '2018-04-27 14:22:45');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('b8d5971f0a7441ee87532cacsacbc85', '7b3e48298a0b4725a2313fa0b85c0774', 'ROLE_AREA_ADMIN');
INSERT INTO `sys_user_role` VALUES ('eb8267b8fe794cfbb402f639fa6a9c6d', '610f5f8b19764a7fa7779d49a4cacc38', 'ROLE_USER');

-- ----------------------------
-- Table structure for user_device
-- ----------------------------
DROP TABLE IF EXISTS `user_device`;
CREATE TABLE `user_device` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户id',
  `pool_id` varchar(255) DEFAULT NULL COMMENT '塘口id',
  `device_id` varchar(255) DEFAULT NULL COMMENT '设备id',
  `imei` varchar(255) DEFAULT NULL COMMENT '设备串号',
  `params` varchar(255) DEFAULT NULL COMMENT '设备参数（JSON格式）',
  `status` tinyint(4) DEFAULT '0' COMMENT '设备状态（true：开启；false：关闭）',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_userId` (`user_id`),
  KEY `fk_deviceId` (`device_id`),
  KEY `fk_poolId` (`pool_id`),
  CONSTRAINT `fk_deviceId` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_poolId` FOREIGN KEY (`pool_id`) REFERENCES `pool` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_userId` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户设备关联表';

-- ----------------------------
-- Records of user_device
-- ----------------------------
INSERT INTO `user_device` VALUES ('002eb711b190428387c8e580fb5a2476', '7b3e48298a0b4725a2313fa0b85c0774', '62e086afed7043979a65396958e518ab', '1', 'QAJU765V0P', '{\"温度\":\"23.1°C\",\"溶氧浓度\":\"12.3mg/L\"}', '0', '2018-04-27 13:48:27', '2018-04-27 14:22:17');
INSERT INTO `user_device` VALUES ('14c9c4d79b564d5bb606ab620311fae8', '7b3e48298a0b4725a2313fa0b85c0774', null, '5', 'GYTVLGH89P', '{\"剩余时间\":\"23min\",\"剩余药量\":\"2303g\"}', '1', '2018-04-27 13:48:06', null);
INSERT INTO `user_device` VALUES ('4f6b73fbd12d43449b7bae1d10d887d6', '7b3e48298a0b4725a2313fa0b85c0774', '774eaf0b4ec04003abd67450ed37a41e', '1', '2D6JG9LM5W', '{\"温度\":\"21.3°C\",\"溶氧浓度\":\"18.5mg/L\"}', '0', '2018-04-27 13:46:35', '2018-04-27 14:07:31');
INSERT INTO `user_device` VALUES ('51305489ad7b41da9f5a2683478482fc', '610f5f8b19764a7fa7779d49a4cacc38', 'adfba13cf39e4a93bb7df3301d8d61f4', '4', '4RT8KJN0PL', '{\"溶氧浓度\":\"23mg/L\",\"水体温度\":\"13.8°C\"}', '1', '2018-04-27 14:53:16', '2018-04-27 14:53:20');
INSERT INTO `user_device` VALUES ('820428b4e4ca43c1b065da9af183e187', '7b3e48298a0b4725a2313fa0b85c0774', '85f020cf1ca74a3785a058b28c3bdb88', '1', 'QBVG54VKI8', '{\"温度\":\"18.8°C\",\"溶氧浓度\":\"16.3mg/L\"}', '1', '2018-04-27 13:48:53', '2018-04-27 13:54:12');
INSERT INTO `user_device` VALUES ('c98216b5ac1e4c79b1a68594c7f93ed6', '7b3e48298a0b4725a2313fa0b85c0774', 'b8d5971f0a7441ee87537ebcbc852449', '3', 'S35HBV9KH3', '{\"设备温度\":\"16.23°C\",\"溶氧浓度\":\"16.65mg/L\"}', '1', '2018-04-27 13:46:55', '2018-04-27 13:54:16');
INSERT INTO `user_device` VALUES ('d41a3dc9bdcc449f9f9fc652d596595e', '610f5f8b19764a7fa7779d49a4cacc38', '33aac67096714cc0b4dab1fe8067f28f', '2', '3R7YH9IKP6', '{\"溶氧浓度\":\"26.3mg/L\",\"水体温度\":\"15.8°C\"}', '1', '2018-04-27 14:52:50', '2018-04-27 14:52:56');
