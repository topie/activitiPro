DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function` (
  `id`            INT(11)      NOT NULL AUTO_INCREMENT,
  `parent_id`     INT(11)      NOT NULL DEFAULT 0
  COMMENT '父ID',
  `function_name` VARCHAR(64)  NOT NULL DEFAULT ''
  COMMENT '功能名称',
  `display`       INT(1)       NOT NULL DEFAULT '1'
  COMMENT '是否显示到菜单栏',
  `display_type`  INT(1)                DEFAULT '1'
  COMMENT '展示类型，1=后台 2=前台',
  `icon`          VARCHAR(32)           DEFAULT ''
  COMMENT 'icon',
  `action`        VARCHAR(255) NOT NULL
  COMMENT '请求路径',
  `function_desc` INT(11)      NOT NULL DEFAULT 0
  COMMENT '排序号',
  `insert_time`   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入时间',
  `update_time`   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `action` (`action`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id`             INT(11)     NOT NULL AUTO_INCREMENT
  COMMENT 'id',
  `role_name`      VARCHAR(64) NOT NULL
  COMMENT '角色名称',
  `default_action` VARCHAR(255)         DEFAULT ''
  COMMENT '角色默认跳转function(非rest用)',
  `state`          INT(1)               DEFAULT '1'
  COMMENT '是否启用，0=不启用，1=启用',
  `insert_time`    TIMESTAMP            DEFAULT CURRENT_TIMESTAMP
  COMMENT '生成时间',
  `update_time`    TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_role_function`;
CREATE TABLE `sys_role_function` (
  `function_id` INT(11) DEFAULT 0,
  `role_id`     INT(11) DEFAULT 0,
  KEY `function_id` (`function_id`),
  KEY `role_id` (`role_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id`                      INT(11)     NOT NULL  AUTO_INCREMENT,
  `login_name`              VARCHAR(64) NOT NULL
  COMMENT '登录名',
  `password`                VARCHAR(64) NOT NULL
  COMMENT '密码',
  `display_name`            VARCHAR(64) NOT NULL  DEFAULT ''
  COMMENT '显示名称',
  `enabled`                 TINYINT(1)  NOT NULL  DEFAULT '1'
  COMMENT '状态，0=冻结，1=正常',
  `account_non_locked`      TINYINT(1)  NOT NULL  DEFAULT '1'
  COMMENT '未锁定状态，0=正常，1=锁定',
  `account_non_expired`     TINYINT(1)  NOT NULL  DEFAULT '1'
  COMMENT '账号过期状态，1=正常，0=过期',
  `credentials_non_expired` TINYINT(1)  NOT NULL  DEFAULT '1'
  COMMENT '密码失效状态：1：未失效 0：已失效',
  `last_login_ip`           VARCHAR(32)           DEFAULT ''
  COMMENT '登陆IP',
  `last_login_time`         TIMESTAMP   NULL      DEFAULT NULL
  COMMENT '最后登陆时间',
  `email`                   VARCHAR(64)           DEFAULT ''
  COMMENT '邮箱',
  `contact_phone`           VARCHAR(32)           DEFAULT ''
  COMMENT '电话',
  `insert_time`             TIMESTAMP             DEFAULT CURRENT_TIMESTAMP
  COMMENT '插入时间',
  `update_time`             TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间',
  `last_password_reset`     TIMESTAMP   NULL      DEFAULT NULL
  COMMENT '上次密码重置时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` INT(11) DEFAULT 0,
  `role_id` INT(11) DEFAULT 0,
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
INSERT INTO `sys_function` VALUES ('1', '0', '首页', '1', '1', "glyphicon glyphicon-home", '/api/index', '1', NULL, NULL);
INSERT INTO `sys_function` VALUES ('2', '0', '系统管理', '1', '1', NULL, '#', '2', NULL, NULL);
INSERT INTO `sys_function` VALUES ('3', '2', '用户管理', '1', '1', NULL, '/api/sys/user/pageList', '1', NULL, NULL);
INSERT INTO `sys_function` VALUES ('4', '2', '角色管理', '1', '1', NULL, '/api/sys/role/pageList', '1', NULL, NULL);
INSERT INTO `sys_function` VALUES ('5', '2', '菜单管理', '1', '1', NULL, '/api/sys/function/pageList', '1', NULL, NULL);


INSERT INTO `sys_role` VALUES ('1', '管理员', '/', '1', NULL, NULL);
INSERT INTO `sys_role` VALUES ('2', '普通用户', '/', '1', NULL, NULL);

INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('1', '1');
INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('1', '2');
INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('1', '3');
INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('1', '4');
INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('1', '5');
INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('2', '1');


INSERT INTO `sys_user` VALUES
  ('1', 'admin', '$2a$10$oWaepJdwE7OjANCEEuQCW.aSxzOCZTsJglNcDpi8cnGXRLRppNZKG', '系统管理员', '1', '1',
        '1', '1', NULL, NULL, '597160667@qq.com', '18600200791', '2015-10-12 00:00:00',
   '2015-10-12 00:00:00', '2015-10-12 00:00:00');
INSERT INTO `sys_user` VALUES
  ('2', 'user', '$2a$10$cQ/pNK.KzrjjQM0I/dRa1Owmuxb4P6NqMNp1Fs7VethhJq9F/W3L6', '普通用户', '1', '1',
        '1', '1', NULL, NULL, '18600200791@163.com', '18600200791', '2015-10-12 00:00:00',
   '2015-10-12 00:00:00', '2015-10-12 00:00:00');

INSERT INTO `sys_user_role` (user_id, role_id) VALUES ('1', '1');
INSERT INTO `sys_user_role` (user_id, role_id) VALUES ('1', '2');
INSERT INTO `sys_user_role` (user_id, role_id) VALUES ('2', '2');

DROP TABLE IF EXISTS `t_login_info`;
CREATE TABLE `t_login_info` (
  `id`            INT(11)   NOT NULL AUTO_INCREMENT,
  `login_user_id` INT(11)   NOT NULL DEFAULT 0
  COMMENT '登录用户id',
  `login_name`    VARCHAR(64)        DEFAULT ''
  COMMENT '登录用户登录名',
  `login_ip`      VARCHAR(32)        DEFAULT ''
  COMMENT '登录ip',
  `login_time`    TIMESTAMP NULL     DEFAULT CURRENT_TIMESTAMP
  COMMENT '登录时间',
  PRIMARY KEY (`id`),
  KEY k_user_time(`login_user_id`, `login_time`),
  KEY k_time(`login_time`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '用户登录信息';

INSERT INTO `sys_function` (`id`, `parent_id`, `function_name`, `display`, `display_type`, `icon`, `action`, `function_desc`, `insert_time`, `update_time`) VALUES ('37', '39', '登录统计', '1', '1', NULL, '/api/info/loginInfo/page', '2', '2016-11-14 20:11:58', '2016-11-14 20:37:05');
  
INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('1', '37');
INSERT INTO `sys_role_function` (role_id, function_id) VALUES ('3', '37');

