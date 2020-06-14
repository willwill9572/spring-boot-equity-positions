/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : equity

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-06-13 10:03:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `transaction_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `trade_id`int(10) DEFAULT NULL COMMENT 'TradeID',
  `version` int(10) DEFAULT NULL COMMENT 'Version',
  `quantity` int(10) DEFAULT NULL COMMENT 'Quantity',
  `security_code` varchar(32) DEFAULT NULL COMMENT 'securityCode',
  `command` varchar(32) DEFAULT NULL COMMENT 'Command',
  `trade_mark` varchar(32) DEFAULT NULL COMMENT 'TradeMark',
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

