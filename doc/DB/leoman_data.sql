/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.6.11 : Database - leoman_framework_demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`leoman_framework_demo` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `leoman_framework_demo`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(50) NOT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  `last_login_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='系统管理员表';

/*Data for the table `t_admin` */

insert  into `t_admin`(`id`,`username`,`password`,`mobile`,`create_date`,`modify_date`,`last_login_date`) values (1,'admin','E10ADC3949BA59ABBE56E057F20F883E',NULL,NULL,NULL,NULL);

/*Table structure for table `t_admin_role` */

DROP TABLE IF EXISTS `t_admin_role`;

CREATE TABLE `t_admin_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(32) DEFAULT NULL COMMENT '管理员ID',
  `role_id` bigint(32) DEFAULT NULL COMMENT '角色ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='用户角色关联关系表';

/*Data for the table `t_admin_role` */

/*Table structure for table `t_feedback` */

DROP TABLE IF EXISTS `t_feedback`;

CREATE TABLE `t_feedback` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '用户ID',
  `content` varchar(200) DEFAULT '' COMMENT '反馈内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈管理';

/*Data for the table `t_feedback` */

/*Table structure for table `t_log` */

DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `message` varchar(100) DEFAULT '' COMMENT '日志消息',
  `user_type` int(2) DEFAULT '0' COMMENT '用户类别 0:后台管理员 1:APP用户',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作用户ID',
  `url` varchar(500) DEFAULT '' COMMENT '访问路径',
  `params` varchar(500) DEFAULT '' COMMENT '访问参数',
  `log_type` int(2) DEFAULT '0' COMMENT '日志类型 0:信息 1:错误',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='日志表';

/*Data for the table `t_log` */

insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (127,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031321258,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (128,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031392984,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (129,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031523976,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (130,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031552062,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (131,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031560064,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (132,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031572769,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (133,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031594936,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (134,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472031619793,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (135,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472032090737,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (136,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472032379905,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (137,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472032411766,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (138,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472032661512,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (139,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472032760327,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (140,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472032769897,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (141,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472032907836,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (142,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472033041187,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (143,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472091701435,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (144,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472092742327,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (145,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472103381554,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (146,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472104836150,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (147,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472105232902,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (148,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472105520028,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (149,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472112856810,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (150,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472113452073,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (151,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472119376019,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (152,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472176059239,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (153,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472177588436,NULL);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (154,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472181641561,1472181641561);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (155,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472181883241,1472181883241);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (156,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472182088072,1472182088072);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (157,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472182550744,1472182550744);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (158,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472182727295,1472182727295);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (159,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472189933400,1472189933400);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (160,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472190309311,1472190309311);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (161,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472195932971,1472195932971);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (162,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472197351618,1472197351618);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (163,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472198692779,1472198692779);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (164,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472198785654,1472198785654);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (165,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472199027461,1472199027461);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (166,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472199327808,1472199327808);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (167,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472200011908,1472200011908);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (168,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472200143043,1472200143043);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (169,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472200655617,1472200655617);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (170,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472201235900,1472201235900);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (171,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472201686693,1472201686693);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (172,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472440006013,1472440006013);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (173,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472454286483,1472454286483);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (174,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472454634674,1472454634675);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (175,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472455295814,1472455295814);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (176,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472457394826,1472457394826);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (177,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472458145647,1472458145647);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (178,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472460992294,1472460992294);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (179,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472461374747,1472461374747);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (180,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472461592911,1472461592911);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (181,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472462463410,1472462463410);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (182,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472462731807,1472462731807);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (183,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472462889141,1472462889141);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (184,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472463296978,1472463296978);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (185,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472463462468,1472463462468);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (186,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472463534175,1472463534175);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (187,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472463592968,1472463592968);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (188,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472463972830,1472463972830);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (189,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472465379299,1472465379299);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (190,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472465500130,1472465500130);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (191,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472465629874,1472465629874);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (192,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472520070894,1472520070894);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (193,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472520386889,1472520386889);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (194,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472520838710,1472520838710);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (195,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472521009559,1472521009559);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (196,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472521429980,1472521429980);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (197,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472523827413,1472523827413);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (198,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472524638919,1472524638919);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (199,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472524669374,1472524669374);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (200,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472524701752,1472524701752);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (201,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472525436270,1472525436270);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (202,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472525476427,1472525476427);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (203,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472525588138,1472525588138);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (204,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472525655570,1472525655570);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (205,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472525679960,1472525679960);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (206,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472525705018,1472525705018);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (207,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472527434616,1472527434616);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (208,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472527473389,1472527473389);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (209,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472528193860,1472528193860);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (210,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472528315076,1472528315076);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (211,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472528323177,1472528323177);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (212,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472528349588,1472528349588);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (213,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472528569473,1472528569473);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (214,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472529560410,1472529560410);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (215,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472529704083,1472529704083);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (216,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472529730636,1472529730636);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (217,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472534605050,1472534605050);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (218,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472537619861,1472537619861);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (219,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472540630164,1472540630164);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (220,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472540832450,1472540832450);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (221,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472540867296,1472540867296);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (222,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472540922047,1472540922047);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (223,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472542876112,1472542876112);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (224,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472544280503,1472544280503);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (225,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472545165773,1472545165773);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (226,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472545289856,1472545289856);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (227,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472547307277,1472547307277);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (228,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472547500163,1472547500164);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (229,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472548118356,1472548118356);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (230,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472548645891,1472548645891);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (231,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472549870806,1472549870806);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (232,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472550646770,1472550646770);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (233,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472551666593,1472551666593);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (234,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472552633198,1472552633198);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (235,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472609684642,1472609684642);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (236,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472618438247,1472618438247);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (237,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472622865884,1472622865884);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (238,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472626221551,1472626221551);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (239,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472630475971,1472630475971);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (240,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472630786115,1472630786115);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (241,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472633671293,1472633671293);
insert  into `t_log`(`id`,`message`,`user_type`,`user_id`,`url`,`params`,`log_type`,`create_date`,`modify_date`) values (242,'admin登录了系统',NULL,1,NULL,NULL,NULL,1472634051040,1472634051040);

/*Table structure for table `t_module` */

DROP TABLE IF EXISTS `t_module`;

CREATE TABLE `t_module` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模块名称',
  `url` varchar(200) DEFAULT NULL COMMENT '模块url',
  `module_icon` varchar(50) DEFAULT NULL COMMENT '模块图标',
  `pid` bigint(32) DEFAULT NULL COMMENT '父模块ID',
  `code` int(11) DEFAULT NULL COMMENT '模块编号',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `admin_id` bigint(20) DEFAULT NULL COMMENT '操作员',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='模块表';

/*Data for the table `t_module` */

insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (14,'权限管理','','fa fa-sitemap',NULL,1000,'权限管理',NULL,1472540847952,1472540847952);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (15,'管理员列表','/admin/admin/index','',14,1001,'管理员列表',NULL,1472540979413,1472540979413);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (16,'角色管理','/admin/role/index','',14,1002,'角色管理',NULL,1472541193352,1472541193352);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (17,'模块管理','/admin/module/index','',14,1003,'模块管理',NULL,1472631469775,1472631469775);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (18,'用户管理','','fa fa-users',NULL,2000,'用户管理',NULL,1472642706939,1472642706939);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (19,'用户列表','/admin/user/index','',18,2001,'用户列表',NULL,1472642721569,1472642721569);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (20,'系统管理','','fa fa-cog',NULL,3000,'系统管理',NULL,1472697026454,1472697026454);
insert  into `t_module`(`id`,`name`,`url`,`module_icon`,`pid`,`code`,`description`,`admin_id`,`create_date`,`modify_date`) values (21,'界面测试','/admin/demo/index','',20,3001,'界面测试',NULL,1472697051055,1472697051055);

/*Table structure for table `t_report` */

DROP TABLE IF EXISTS `t_report`;

CREATE TABLE `t_report` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '用户ID',
  `report_type` varchar(50) NOT NULL COMMENT '举报类型 多选',
  `post_type` tinyint(4) NOT NULL COMMENT '帖子类型 0:普通贴 1:直播贴 2:投票贴 ',
  `post_id` bigint(32) NOT NULL COMMENT '帖子ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报管理';

/*Data for the table `t_report` */

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色',
  `admin_id` bigint(20) DEFAULT NULL COMMENT '操作员',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `t_role` */

/*Table structure for table `t_role_module` */

DROP TABLE IF EXISTS `t_role_module`;

CREATE TABLE `t_role_module` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(32) DEFAULT NULL COMMENT '角色ID',
  `module_id` bigint(32) DEFAULT NULL COMMENT '模块ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='角色模块关联关系表';

/*Data for the table `t_role_module` */

/*Table structure for table `t_system_config` */

DROP TABLE IF EXISTS `t_system_config`;

CREATE TABLE `t_system_config` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL COMMENT '分类',
  `content` varchar(20) NOT NULL COMMENT '内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主创动态管理';

/*Data for the table `t_system_config` */

/*Table structure for table `t_user_info` */

DROP TABLE IF EXISTS `t_user_info`;

CREATE TABLE `t_user_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '关联user_login表ID',
  `mobile` varchar(32) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT '' COMMENT '昵称',
  `gender` tinyint(4) DEFAULT '0' COMMENT '性别 男:male 女:female',
  `avater` varchar(200) DEFAULT '' COMMENT '头像',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0:正常 1:冻结',
  `type` tinyint(4) DEFAULT '0' COMMENT '会员类型 0:普通会员 1:主创',
  `level` int(11) DEFAULT '1' COMMENT '会员等级',
  `funs` bigint(20) DEFAULT '0' COMMENT '粉丝数',
  `focus` bigint(20) DEFAULT '0' COMMENT '关注数',
  `posts` int(11) DEFAULT '0' COMMENT '发帖数',
  `praises` bigint(20) DEFAULT '0' COMMENT '主创点赞数',
  `coin` bigint(20) DEFAULT '0' COMMENT '馒头币',
  `integral` bigint(20) DEFAULT '0' COMMENT '积分',
  `is_creator` tinyint(4) DEFAULT '0' COMMENT '是否主播 0:不是 1:是',
  `creator_id` bigint(20) DEFAULT '0' COMMENT '主创ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';

/*Data for the table `t_user_info` */

/*Table structure for table `t_user_login` */

DROP TABLE IF EXISTS `t_user_login`;

CREATE TABLE `t_user_login` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(50) NOT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  `last_login_date` bigint(20) DEFAULT NULL,
  `ip_address` varchar(20) DEFAULT NULL COMMENT '登录IP地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录信息表';

/*Data for the table `t_user_login` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
