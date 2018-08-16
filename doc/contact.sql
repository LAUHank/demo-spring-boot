/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_3306
Source Server Version : 50540
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2018-08-16 16:22:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `salary` decimal(18,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contact
-- ----------------------------
INSERT INTO `contact` VALUES ('0', '小明', '1.00');
INSERT INTO `contact` VALUES ('1', '小明', '1.00');
INSERT INTO `contact` VALUES ('2', '小明', '1.00');
INSERT INTO `contact` VALUES ('3', '小明', '1.00');
INSERT INTO `contact` VALUES ('4', '小明', '1.00');
INSERT INTO `contact` VALUES ('5', '小明', '1.00');
INSERT INTO `contact` VALUES ('6', '小明', '1.00');
INSERT INTO `contact` VALUES ('7', '小明', '1.00');
INSERT INTO `contact` VALUES ('8', '小明', '1.00');
INSERT INTO `contact` VALUES ('9', '小明', '1.00');
INSERT INTO `contact` VALUES ('10', '小红', '9.00');
INSERT INTO `contact` VALUES ('11', '小强', '10.00');
INSERT INTO `contact` VALUES ('12', '小强2', '10.00');
INSERT INTO `contact` VALUES ('13', 'LauHank', '10000.00');
