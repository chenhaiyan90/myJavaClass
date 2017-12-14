/*
Navicat MySQL Data Transfer

Source Server         : localhost_msyql
Source Server Version : 50613
Source Host           : localhost:3306
Source Database       : leaderspring

Target Server Type    : MYSQL
Target Server Version : 50613
File Encoding         : 65001

Date: 2016-12-21 16:54:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `authorities`
-- ----------------------------
DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `authority` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authorities
-- ----------------------------
INSERT INTO `authorities` VALUES ('1', 'leader', 'admin');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('leader', '123456', '1');
