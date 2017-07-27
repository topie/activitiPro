DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `dict_id` int(32) NOT NULL AUTO_INCREMENT,
  `dict_desc` varchar(100) DEFAULT NULL,
  `dict_name` varchar(50) DEFAULT NULL,
  `dict_seq` bigint(20) DEFAULT NULL,
  `dict_status` bigint(20) DEFAULT NULL,
  `dict_type` varchar(10) DEFAULT NULL,
  `dict_code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_dict_item`;
CREATE TABLE `t_dict_item` (
  `item_id` int(32) NOT NULL AUTO_INCREMENT,
  `item_code` varchar(50) DEFAULT NULL,
  `item_name` varchar(50) DEFAULT NULL,
  `item_seq` bigint(20) DEFAULT NULL,
  `dict_id` int(32) DEFAULT NULL,
  `item_pid` int(32) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `fke56003f12e061065` (`item_pid`),
  KEY `fke56003f1e8962a97` (`dict_id`),
  CONSTRAINT `t_dict_item_ibfk_1` FOREIGN KEY (`dict_id`) REFERENCES `t_dict` (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1010 DEFAULT CHARSET=utf8;

