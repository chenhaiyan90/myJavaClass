

 CREATE TABLE `leader_course`.`sp_course` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `name` varchar(64) DEFAULT NULL COMMENT '课程名',
                      `mark` int(11) DEFAULT NULL COMMENT '分数',
                      PRIMARY KEY (`id`),
                      KEY `idx_name` (`name`)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;
                    
 alter table `celt_charge`.`trade_refund` add column `refund_num` int(11) NULL COMMENT '退款商品数量' after `money`;
 
 INSERT into `sp_course` (`name`,`mark`) VALUES ('aaa',88);
 INSERT into `sp_course` (`name`,`mark`) VALUES ('bbb',66);
 INSERT into `sp_course` (`name`,`mark`) VALUES ('ccc',99);

                    