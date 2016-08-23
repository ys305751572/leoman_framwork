USE `leoman_framework_demo`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(50) NOT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  `last_login_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统管理员表';

/*Data for the table `t_admin` */

insert  into `t_admin`(`id`,`username`,`password`,`create_date`,`modify_date`,`last_login_date`) values (1,'admin','E10ADC3949BA59ABBE56E057F20F883E',NULL,NULL,NULL);

/*Table structure for table `t_log` */
DROP TABLE IF EXISTS `t_log`;

CREATE TABLE `t_log` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `message` varchar(100) DEFAULT '' COMMENT '日志消息',
  `user_type` int(2) DEFAULT 0 COMMENT '用户类别 0:后台管理员 1:APP用户',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作用户ID',
  `url` VARCHAR(500) DEFAULT '' COMMENT '访问路径',
  `params` VARCHAR(500) DEFAULT '' COMMENT '访问参数',
  `log_type` int(2) DEFAULT 0 COMMENT '日志类型 0:信息 1:错误',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8 COMMENT='日志表';

/*Table structure for table `t_module` */

DROP TABLE IF EXISTS `t_module`;

CREATE TABLE `t_module` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '模块名称',
  `url` bigint(20) DEFAULT NULL COMMENT '模块url',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `admin_id` bigint(20) DEFAULT NULL COMMENT '操作员',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块表';

/*Data for the table `t_module` */

/*Table structure for table `t_module_relation` */

DROP TABLE IF EXISTS `t_module_relation`;

CREATE TABLE `t_module_relation` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `child_id` bigint(20) NOT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模块关联表';


DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色',
  `admin_id` bigint(20) DEFAULT NULL COMMENT '操作员',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色模块关联关系表';

/*Table structure for table `t_admin_role` */

DROP TABLE IF EXISTS `t_admin_role`;

CREATE TABLE `t_admin_role` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(32) DEFAULT NULL COMMENT '管理员ID',
  `role_id` bigint(32) DEFAULT NULL COMMENT '角色ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联关系表';

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

/*Table structure for table `t_user_info` */
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(32) NOT NULL COMMENT '关联user_login表ID',
  `mobile` varchar(32) NOT NULL COMMENT '账号',
  `password` VARCHAR(50) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) DEFAULT '' COMMENT '昵称',
  `gender` TINYINT DEFAULT 0 comment '性别 男:male 女:female',
  `avater` VARCHAR(200) DEFAULT '' COMMENT '头像',
  `status` TINYINT DEFAULT 0 comment '状态 0:正常 1:冻结',
  `type` TINYINT DEFAULT 0 COMMENT '会员类型 0:普通会员 1:主创',
  `level` INT DEFAULT 1 COMMENT '会员等级',
  `funs` BIGINT DEFAULT 0 COMMENT '粉丝数',
  `focus` BIGINT DEFAULT 0 comment '关注数',
  `posts` INT DEFAULT 0 comment '发帖数',
  `praises` BIGINT DEFAULT 0 comment '主创点赞数',
  `coin` BIGINT DEFAULT 0 COMMENT '馒头币',
  `integral` BIGINT DEFAULT 0 COMMENT '积分',
  `is_creator` TINYINT DEFAULT 0 comment '是否主播 0:不是 1:是',
  `creator_id` BIGINT DEFAULT 0 COMMENT '主创ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息表';

/*Table structure for table `t_user_address` */

DROP TABLE IF EXISTS `t_user_address`;
CREATE TABLE `t_user_address` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `name` VARCHAR(50) DEFAULT '' COMMENT '姓名',
  `mobile` VARCHAR(20) DEFAULT '' COMMENT '手机',
  `address` VARCHAR(500) DEFAULT '' COMMENT '地址',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表-地址表';

/*Table structure for table `t_user_creator` */

DROP TABLE IF EXISTS `t_user_creator`;
CREATE TABLE `t_user_creator` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `provice_id` BIGINT COMMENT '省份ID',
  `city_id` BIGINT COMMENT '城市ID',
  `desc` VARCHAR(5000) DEFAULT '' COMMENT '简介',
  `experience` VARCHAR(2000) DEFAULT '' COMMENT '履历',
  `dynamic` VARCHAR(1000) DEFAULT '' COMMENT '今日动态',
  `audio_url` VARCHAR(500) DEFAULT '' COMMENT '音频',
  `cover_url` VARCHAR(500) DEFAULT '' COMMENT '封面',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-主创信息表';

/*Table structure for table `t_user_creator_image` */

DROP TABLE IF EXISTS `t_user_creator_image`;
CREATE TABLE `t_user_creator_image` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `creator_id` BIGINT NOT NULL COMMENT '用户ID',
  `type` TINYINT DEFAULT 0 COMMENT '图片类别 0:剧照 1:生活照',
  `image` VARCHAR(500) COMMENT '剧照',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-主创-图片表';


/*Table structure for table `t_category` */

DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '名称',
  `type` TINYINT DEFAULT 0 COMMENT '类别 0：作品集 1:帖子 2:剧照 3:音乐',
  `count` BIGINT DEFAULT 0 COMMENT  '数量',
  `url` VARCHAR(200) DEFAULT '' COMMENT '地址，包括图片，音频地址',
  `singer` VARCHAR(50) DEFAULT '' COMMENT '歌手',
  `status` TINYINT DEFAULT 0 COMMENT '状态 0:显示 1:隐藏',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类管理';

/*Table structure for table `t_works` */
DROP TABLE IF EXISTS `t_works`;
CREATE TABLE `t_works` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '视频名称',
  `series_count` INT DEFAULT 0 COMMENT '集数',
  `category` BIGINT DEFAULT 0 COMMENT '分类',
  `desc` VARCHAR(2000) DEFAULT '' COMMENT '简介',
  `is_end` TINYINT DEFAULT 0 COMMENT '是否完结 0:未完结 1:已完结',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作品管理';

/*Table structure for table `t_works_creator` */
DROP TABLE IF EXISTS `t_works_creator`;
CREATE TABLE `t_works_creator` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `works_id` BIGINT NOT NULL COMMENT '作品ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作品-主创管理';

/*Table structure for table `t_works_surround` */
DROP TABLE IF EXISTS `t_works_surround`;
CREATE TABLE `t_works_surround` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `works_id` BIGINT NOT NULL COMMENT '作品ID',
  `name` VARCHAR(50) NOT NULL COMMENT '名称',
  `desc` VARCHAR(2000) NOT NULL COMMENT '描述',
  `link_url` VARCHAR(500) DEFAULT '' COMMENT '购买链接',
  `cover` VARCHAR(200) DEFAULT '' COMMENT '封面',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作品-周边管理';

/*Table structure for table `t_works_vedio` */
DROP TABLE IF EXISTS `t_works_vedio`;
CREATE TABLE `t_works_vedio` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `works_id` BIGINT NOT NULL COMMENT '作品ID',
  `series` VARCHAR(50) NOT NULL COMMENT '当前集数',
  `link_url` VARCHAR(200) NOT NULL  COMMENT '链接地址',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作品-视频管理';


/*Table structure for table `t_works_novel` */
DROP TABLE IF EXISTS `t_works_novel`;
CREATE TABLE `t_works_novel` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `works_id` BIGINT NOT NULL COMMENT '作品ID',
  `series` VARCHAR(50) NOT NULL COMMENT '当前回数',
  `detail` VARCHAR(5000) NOT NULL  COMMENT '内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作品-小说管理';

/*Table structure for table `t_works_comic` */
DROP TABLE IF EXISTS `t_works_comic`;
CREATE TABLE `t_works_comic` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `works_id` BIGINT NOT NULL COMMENT '作品ID',
  `series` VARCHAR(50) NOT NULL COMMENT '当前回数',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作品-小漫画管理';

/*Table structure for table `t_works_comic` */
DROP TABLE IF EXISTS `t_works_comic_image`;
CREATE TABLE `t_works_comic_image` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `comic_id` BIGINT NOT NULL COMMENT '漫画ID',
  `url` VARCHAR(200) DEFAULT '' COMMENT '图片地址',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='作品-小漫画管理';


/*Table structure for table `t_resource_jz` */
DROP TABLE IF EXISTS `t_resource_jz`;
CREATE TABLE `t_resource_jz` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(50) NOT NULL COMMENT '名称',
  `url`VARCHAR(200) DEFAULT '' COMMENT '图片url',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='剧照资源管理';

/*Table structure for table `t_resource_music` */
DROP TABLE IF EXISTS `t_resource_music`;
CREATE TABLE `t_resource_music` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(50) NOT NULL COMMENT '名称',
  `name` VARCHAR(50) DEFAULT '' COMMENT '音乐名称',
  `singer` VARCHAR(50) DEFAULT '' COMMENT '歌手',
  `url`VARCHAR(200) DEFAULT '' COMMENT '图片url',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='音乐资源管理';

/*Table structure for table `t_resource_game` */

DROP TABLE IF EXISTS `t_resource_game`;
CREATE TABLE `t_resource_game` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '名称',
  `url`VARCHAR(200) DEFAULT '' COMMENT '图片url',
  `link_url` VARCHAR(500) DEFAULT '' COMMENT '下载链接',
  `version` INT DEFAULT 0 COMMENT '版本',
  `size` INT DEFAULT 0 COMMENT '大小',
  `down_count` INT DEFAULT 0 COMMENT '下载次数',
  `desc` VARCHAR(5000) DEFAULT '' COMMENT '简介',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏资源管理';

/*Table structure for table `t_welfare` */

DROP TABLE IF EXISTS `t_welfare`;
CREATE TABLE `t_welfare` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL COMMENT '标题',
  `sub_title`VARCHAR(50) DEFAULT '' COMMENT '副标题',
  `type` TINYINT DEFAULT 0 COMMENT '类型 0:铃声 1:经验值 2:实物 3:表情包 4:商城购买 5:游戏兑换码',
  `cover` VARCHAR(200) DEFAULT '' COMMENT '封面',
  `coin` INT DEFAULT 0 COMMENT '需要馒头数',
  `length` DOUBLE DEFAULT 0 COMMENT '铃声时长',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='福利管理';

/*Table structure for table `t_barrage` */
DROP TABLE IF EXISTS `t_barrage`;
CREATE TABLE `t_barrage` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `type` TINYINT DEFAULT 0 COMMENT '类别 0:视频 1:小说',
  `works_id` BIGINT DEFAULT 0 COMMENT '作品ID',
  `user_id` BIGINT DEFAULT 0 COMMENT '用户ID',
  `praise` BIGINT DEFAULT 0 COMMENT '点赞数',
  `content` VARCHAR(500) DEFAULT '' COMMENT '弹幕内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='弹幕管理';

/*Table structure for table `t_comment_works` */
DROP TABLE IF EXISTS `t_comment_works`;
CREATE TABLE `t_comment_works` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `from_user_id` BIGINT(32) NOT NULL COMMENT '评论发起人ID',
  `type` TINYINT NOT NULL COMMENT '0:视频 1:小说 2:漫画',
  `works_id` BIGINT(32) NOT NULL COMMENT '作品ID',
  `to_user_id` BIGINT(32) DEFAULT 0 COMMENT '评论接收人ID 为空则直接对作品进行评论',
  `to_series` INT DEFAULT 0 COMMENT '对某一章节评论 为0 则对整个小说或者漫画评论',
  `priase` BIGINT DEFAULT 0 COMMENT '点赞数',
  `conent` VARCHAR(500) DEFAULT '' COMMENT '评论内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论管理';

/*Table structure for table `t_post_pt` */
DROP TABLE IF EXISTS `t_post_pt`;
CREATE TABLE `t_post_pt` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) DEFAULT '' COMMENT '名字',
  `category` BIGINT(32) DEFAULT 0 COMMENT '帖子分类',
  `content` VARCHAR(2000) DEFAULT '' COMMENT '内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='普通贴管理';

/*Table structure for table `t_post_zb` */
DROP TABLE IF EXISTS `t_post_zb`;
CREATE TABLE `t_post_zb` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) DEFAULT '' COMMENT '名字',
  `category` BIGINT(32) DEFAULT 0 COMMENT '帖子分类',
  `start_date` BIGINT(20) COMMENT '开始时间',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='直播贴管理';

/*Table structure for table `t_post_tp` */
DROP TABLE IF EXISTS `t_post_tp`;
CREATE TABLE `t_post_tp` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) DEFAULT '' COMMENT '名字',
  `category` BIGINT(32) DEFAULT 0 COMMENT '帖子分类',
  `desc` VARCHAR(500) DEFAULT '' COMMENT '投票说明',
  `end_dae` BIGINT COMMENT '投票截止时间',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投票贴管理';

/*Table structure for table `t_post_tp_sub` */
DROP TABLE IF EXISTS `t_post_tp_sub`;
CREATE TABLE `t_post_tp_sub` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT(32) NOT NULL COMMENT '帖子',
  `name` VARCHAR(50) DEFAULT '' COMMENT '名称',
  `cover` VARCHAR(200) DEFAULT '' COMMENT '封面',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投票贴对象管理';

/*Table structure for table `t_post_tp_sub` */
DROP TABLE IF EXISTS `t_post_image`;
CREATE TABLE `t_post_image` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT(32) NOT NULL COMMENT '帖子',
  `type` TINYINT DEFAULT 0 COMMENT '帖子类型 0:普通贴 1:直播贴 2:投票贴',
  `url` VARCHAR(200) DEFAULT '' COMMENT '图片url',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投票贴对象管理';


/*Table structure for table `t_post_comment` */
DROP TABLE IF EXISTS `t_post_comment`;
CREATE TABLE `t_post_comment` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `post_id` BIGINT(32) NOT NULL COMMENT '帖子',
  `from_user_id` BIGINT(32) NOT NULL COMMENT '评论发起人ID',
  `to_user_id` BIGINT(32) DEFAULT 0 COMMENT '评论接收人ID 为空则直接对作品进行评论',
  `type` TINYINT DEFAULT 0 COMMENT '帖子类型 0:普通贴 1:直播贴 2:投票贴',
  `content` VARCHAR(500) DEFAULT '' COMMENT '内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贴子评论管理';

/*Table structure for table `t_banner` */
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `cover` VARCHAR(200) DEFAULT '' COMMENT '封面',
  `link_url` VARCHAR(200) DEFAULT '' COMMENT '外链',
  `postion` TINYINT DEFAULT 0 COMMENT '链接位子 0:帖子 1:视频 2:小说 3:漫画',
  `works_id` BIGINT DEFAULT 0 COMMENT '作品ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告管理';

/*Table structure for table `t_ads_index` */
DROP TABLE IF EXISTS `t_ads_index`;
CREATE TABLE `t_ads_index` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL COMMENT '标题',
  `sub_title` VARCHAR(200) NOT NULL COMMENT '子标题',
  `cover` VARCHAR(200) COMMENT '封面',
  `position` TINYINT DEFAULT 0 COMMENT '链接位子 0:帖子 1:视频 2:小说 3:漫画',
  `category_id` BIGINT DEFAULT 0 COMMENT '分类ID',
  `works_id` BIGINT NOT NULL COMMENT '作品ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页推荐管理';

/*Table structure for table `t_float_win` */
DROP TABLE IF EXISTS `t_float_win`;
CREATE TABLE `t_float_win` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL COMMENT '标题',
  `sub_title` VARCHAR(200) NOT NULL COMMENT '子标题',
  `cover` VARCHAR(200) COMMENT '封面',
  `position` TINYINT DEFAULT 0 COMMENT '链接位子 0:帖子 1:视频 2:小说 3:漫画',
  `category_id` BIGINT DEFAULT 0 COMMENT '分类ID',
  `works_id` BIGINT NOT NULL COMMENT '作品ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='浮窗广告管理';

/*Table structure for table `t_creator_dynamic` */
DROP TABLE IF EXISTS `t_creator_dynamic`;
CREATE TABLE `t_creator_dynamic` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(32) NOT NULL COMMENT '主创ID',
  `content` VARCHAR(100) NOT NULL COMMENT '互动内容',
  `source` TINYINT DEFAULT 0 COMMENT '数据来源 0:系统 1:手动',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主创动态管理';

/*Table structure for table `t_system_config` */
DROP TABLE IF EXISTS `t_system_config`;
CREATE TABLE `t_system_config` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(20) NOT NULL COMMENT '分类',
  `content` VARCHAR(20) NOT NULL COMMENT '内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主创动态管理';

/*Table structure for table `t_prize` */
DROP TABLE IF EXISTS `t_prize`;
CREATE TABLE `t_prize` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '名字',
  `coin` INT DEFAULT 0 COMMENT '奖品额度',
  `pro` DOUBLE DEFAULT 0 COMMENT '概率',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='抽奖管理';

/*Table structure for table `t_task` */
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `type` TINYINT DEFAULT 0 COMMENT '任务类型 0：发表帖子 1:主创点赞 2:邀请用户 3:发布评价 4:发布弹幕 5:回复帖子',
  `sql1`VARCHAR(200) DEFAULT '' COMMENT '监听事件',
  `sql2`VARCHAR(200) DEFAULT '' COMMENT '触发事件',
  `task_count` INT DEFAULT 0 COMMENT '任务数量',
  `coin` INT DEFAULT 0 COMMENT '奖励金额',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务奖励管理';

/*Table structure for table `t_sensitive_words` */
DROP TABLE IF EXISTS `t_sensitive_words`;
CREATE TABLE `t_sensitive_words` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(2) NOT NULL COMMENT '敏感词首字母',
  `words` VARCHAR(20) NOT NULL COMMENT '敏感词',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='敏感词库管理';

/*Table structure for table `t_report` */
DROP TABLE IF EXISTS `t_report`;
CREATE TABLE `t_report` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(32) NOT NULL COMMENT '用户ID',
  `report_type` VARCHAR(50) NOT NULL COMMENT '举报类型 多选',
  `post_type` TINYINT NOT NULL COMMENT '帖子类型 0:普通贴 1:直播贴 2:投票贴 ',
  `post_id` BIGINT(32) NOT NULL COMMENT '帖子ID',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='举报管理';


/*Table structure for table `t_feedback` */
DROP TABLE IF EXISTS `t_feedback`;
CREATE TABLE `t_feedback` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(32) NOT NULL COMMENT '用户ID',
  `content` VARCHAR(200) DEFAULT '' COMMENT '反馈内容',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈管理';

/*Table structure for table `t_gift_exchange_record` */
DROP TABLE IF EXISTS `t_gift_exchange_record`;
CREATE TABLE `t_gift_exchange_record` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(32) NOT NULL COMMENT '用户ID',
  `nickname` VARCHAR(50) NOT NULL COMMENT '用户昵称',
  `username_mobile` VARCHAR(20) DEFAULT '' COMMENT '用户手机',
  `gift_name` VARCHAR(100) DEFAULT '' COMMENT '兑换物品名称',
  `exchange_coin` BIGINT DEFAULT 0 COMMENT '兑换金额',
  `receiver_name` VARCHAR(50) DEFAULT '' COMMENT '收件人姓名',
  `receiver_mobile` VARCHAR(20) DEFAULT '' COMMENT '收件人电话',
  `receiver_address` VARCHAR(200) DEFAULT '' COMMENT '收件人地址',
  `status` TINYINT DEFAULT 0 COMMENT '状态 0:待邮寄 1:待签收 2:已签收',
  `courier_name` VARCHAR(50) DEFAULT '' COMMENT '快递名称',
  `courier_sn` VARCHAR(50) DEFAULT '' COMMENT '快递单号',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈管理';


/*Table structure for table `t_message` */
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL COMMENT '标题',
  `to_object` TINYINT DEFAULT 0 COMMENT '发送对象 0:全部 1:普通会员 2:主创会员',
  `content` VARCHAR(5000) DEFAULT '' COMMENT '内容',
  `send_date` BIGINT COMMENT '发送时间',
  `create_date` bigint(20) DEFAULT NULL,
  `modify_date` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息管理';

