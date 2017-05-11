/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.1.21-MariaDB : Database - silly_english
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`silly_english` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_vietnamese_ci */;

USE `silly_english`;

/*Table structure for table `answer_key` */

DROP TABLE IF EXISTS `answer_key`;

CREATE TABLE `answer_key` (
  `answer_key_id` int(11) NOT NULL,
  `details` tinytext COLLATE utf8_vietnamese_ci,
  PRIMARY KEY (`answer_key_id`),
  KEY `answer_key_id` (`answer_key_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `answer_key` */

insert  into `answer_key`(`answer_key_id`,`details`) values (1,'A'),(2,'B'),(3,'C'),(4,'D'),(5,'NONE');

/*Table structure for table `battle_question` */

DROP TABLE IF EXISTS `battle_question`;

CREATE TABLE `battle_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `battle_id` int(11) NOT NULL,
  `attacker_answer` int(11) NOT NULL DEFAULT '5',
  `defender_answer` int(11) NOT NULL DEFAULT '5',
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  KEY `battle_id` (`battle_id`),
  KEY `battle_question_ibfk_3` (`attacker_answer`),
  KEY `battle_question_ibfk_4` (`defender_answer`),
  CONSTRAINT `battle_question_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`),
  CONSTRAINT `battle_question_ibfk_2` FOREIGN KEY (`battle_id`) REFERENCES `battles` (`battle_id`),
  CONSTRAINT `battle_question_ibfk_3` FOREIGN KEY (`attacker_answer`) REFERENCES `answer_key` (`answer_key_id`),
  CONSTRAINT `battle_question_ibfk_4` FOREIGN KEY (`defender_answer`) REFERENCES `answer_key` (`answer_key_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `battle_question` */

/*Table structure for table `battle_status` */

DROP TABLE IF EXISTS `battle_status`;

CREATE TABLE `battle_status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'The status''s id',
  `detail` tinytext COLLATE utf8_vietnamese_ci NOT NULL COMMENT 'The status detail',
  PRIMARY KEY (`status_id`),
  KEY `status_id` (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `battle_status` */

insert  into `battle_status`(`status_id`,`detail`) values (1,'waiting'),(2,'completed');

/*Table structure for table `battles` */

DROP TABLE IF EXISTS `battles`;

CREATE TABLE `battles` (
  `battle_id` int(11) NOT NULL AUTO_INCREMENT,
  `attacker_id` varchar(40) COLLATE utf8_vietnamese_ci NOT NULL,
  `defender_id` varchar(40) COLLATE utf8_vietnamese_ci NOT NULL,
  `battle_status` int(11) DEFAULT '1',
  `date_create` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`battle_id`),
  KEY `attacker_id` (`attacker_id`),
  KEY `defender_id` (`defender_id`),
  KEY `battle_status` (`battle_status`),
  CONSTRAINT `battles_ibfk_1` FOREIGN KEY (`battle_status`) REFERENCES `battle_status` (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `battles` */

/*Table structure for table `lesson` */

DROP TABLE IF EXISTS `lesson`;

CREATE TABLE `lesson` (
  `ls_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Listening Unit ID',
  `ls_title` varchar(200) COLLATE utf8_vietnamese_ci DEFAULT NULL COMMENT 'Listening Unit Title',
  `ls_level` int(11) DEFAULT NULL COMMENT 'Level of Listening Unit',
  `ls_chapter` int(11) DEFAULT NULL COMMENT 'Chapter Number of Listening Unit',
  `ls_source` int(11) DEFAULT NULL COMMENT 'Source of Infomation',
  `ls_price` int(11) DEFAULT NULL COMMENT 'Price of Unit',
  `ls_exp` int(11) DEFAULT NULL COMMENT 'Maximum Exp of Unit',
  `ls_max_hear_times` int(11) DEFAULT NULL COMMENT 'Max hear times',
  `ls_url` varchar(200) COLLATE utf8_vietnamese_ci DEFAULT NULL COMMENT 'Link of Listening file',
  `ls_note` varchar(500) COLLATE utf8_vietnamese_ci DEFAULT NULL COMMENT 'User Note for Unit file',
  PRIMARY KEY (`ls_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `lesson` */

insert  into `lesson`(`ls_id`,`ls_title`,`ls_level`,`ls_chapter`,`ls_source`,`ls_price`,`ls_exp`,`ls_max_hear_times`,`ls_url`,`ls_note`) values (1,'A Ghost in Love and Other Plays',1,14,0,140,140,2,'null _url','note'),(2,'Goodbye, Mr. Hollywood',1,8,0,80,80,2,'null _url','note');

/*Table structure for table `lesson_source` */

DROP TABLE IF EXISTS `lesson_source`;

CREATE TABLE `lesson_source` (
  `source_id` int(11) NOT NULL AUTO_INCREMENT,
  `source_name` varchar(100) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `source_image_url` varchar(100) COLLATE utf8_vietnamese_ci DEFAULT NULL,
  PRIMARY KEY (`source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `lesson_source` */

insert  into `lesson_source`(`source_id`,`source_name`,`source_image_url`) values (1,'Oxford BookWorm','oxford bookworm'),(2,'Podcast In English','Podcast In English'),(3,'BBC English','BBC ENGLISH');

/*Table structure for table `lesson_unit` */

DROP TABLE IF EXISTS `lesson_unit`;

CREATE TABLE `lesson_unit` (
  `lu_id` int(11) NOT NULL COMMENT 'Lesson Unit Id',
  `lu_name` varchar(100) COLLATE utf8_vietnamese_ci DEFAULT NULL COMMENT 'Lesson Unit Name',
  `lu_sequence` int(11) DEFAULT NULL COMMENT 'Lesson Unit Sequence Number',
  `ls_id` int(11) DEFAULT NULL COMMENT 'Reference to Lesson',
  `lu_max_hear` int(11) DEFAULT NULL COMMENT 'Lesson Unit Max Hear Time',
  `lu_url` varchar(50) COLLATE utf8_vietnamese_ci DEFAULT NULL COMMENT 'Lesson Unit Url',
  `lu_exp` int(11) DEFAULT NULL COMMENT 'Exp get every time',
  `lu_note` text COLLATE utf8_vietnamese_ci COMMENT 'Lesson Unit Note',
  `lu_type` int(11) DEFAULT NULL COMMENT 'Type of Unit',
  PRIMARY KEY (`lu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `lesson_unit` */

/*Table structure for table `level` */

DROP TABLE IF EXISTS `level`;

CREATE TABLE `level` (
  `lv_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Level ID',
  `lv_exp_point` int(11) DEFAULT NULL COMMENT 'Exp must have to reach this level',
  `lv_leter` char(1) COLLATE utf8_vietnamese_ci DEFAULT NULL COMMENT 'The letter of Level',
  `lv_metal_kind` int(11) DEFAULT NULL COMMENT 'Metal Kind of Level',
  PRIMARY KEY (`lv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `level` */

/*Table structure for table `question_type` */

DROP TABLE IF EXISTS `question_type`;

CREATE TABLE `question_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `details` tinytext COLLATE utf8_vietnamese_ci,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `question_type` */

insert  into `question_type`(`type_id`,`details`) values (1,'listening'),(2,'reading');

/*Table structure for table `questions` */

DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type` int(11) DEFAULT NULL,
  `question_content` text COLLATE utf8_vietnamese_ci,
  `answer_a` text COLLATE utf8_vietnamese_ci,
  `answer_b` text COLLATE utf8_vietnamese_ci,
  `audio_source` text COLLATE utf8_vietnamese_ci,
  `true_answer` int(11) DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `fk_question_type` (`question_type`),
  KEY `true_answer` (`true_answer`),
  CONSTRAINT `fk_question_type` FOREIGN KEY (`question_type`) REFERENCES `question_type` (`type_id`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`true_answer`) REFERENCES `answer_key` (`answer_key_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `questions` */

insert  into `questions`(`question_id`,`question_type`,`question_content`,`answer_a`,`answer_b`,`audio_source`,`true_answer`) values (1,1,'What are they talking about?','A movie','A film','http://zmp3-mp3-s1.zmp3-vtnhcm-1.za.zdn.vn/733db67cbd3854660d29/8356612300525677950?key=6TMyDmH3yfbpxB5M7tuq9Q&expires=1488043991',1),(2,2,'This is a reading question.','This is answer A','This is answer B',NULL,2),(3,1,'This is a listening question.','Answer A...','Answer B...','http://zmp3-mp3-s1.zmp3-vtnhcm-1.za.zdn.vn/733db67cbd3854660d29/8356612300525677950?key=6TMyDmH3yfbpxB5M7tuq9Q&expires=1488043991',2),(4,2,'reading reading reading','The answer a... kaka','bbbbbbbbb',NULL,2),(5,1,'this is 5th question','Answer for question 5 - A','Answer for question 5 - B',NULL,1),(6,2,'test test','haha','hehe','\"mp3 link!\"',1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'the auto increment id',
  `user_id` varchar(40) COLLATE utf8_vietnamese_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_vietnamese_ci NOT NULL COMMENT 'User Name',
  `facebook_id` varchar(20) COLLATE utf8_vietnamese_ci DEFAULT NULL COMMENT 'User''s Facebook Id',
  `coin` int(11) DEFAULT NULL COMMENT 'User Coin',
  `rank` int(11) DEFAULT NULL COMMENT 'Exp of User',
  `level` int(11) DEFAULT NULL COMMENT 'Level of User',
  `win_match` int(11) DEFAULT NULL COMMENT 'Win Match Number of User',
  `total_match` int(11) DEFAULT NULL COMMENT 'Total Match Number of User',
  `avatar_url` text COLLATE utf8_vietnamese_ci COMMENT 'User''s avatar url - from facebook or default',
  PRIMARY KEY (`id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`user_id`,`name`,`facebook_id`,`coin`,`rank`,`level`,`win_match`,`total_match`,`avatar_url`) values (1,'b1d7dd8f11b32c9a0f66ea3c4416ca7f0aa02c80','sillyenglish',NULL,1000,2000,2,10,20,'http://images.google.com/folder/125a1241a/gks.jpg'),(2,'7817469c39d0539775a545fd4357e671414fcbf3','Huỳnh Khoa Vin','728517987296641',1222,3333,4,54,222,'http://images.google.com/folder/125a1241a/gks.jpg'),(3,'bc76c6649b2b7ab5b6d6f19292c4de33173c5ea7','OatOal','100006561107068',13025,52,5,10,30,NULL);

/*Table structure for table `user_lesson` */

DROP TABLE IF EXISTS `user_lesson`;

CREATE TABLE `user_lesson` (
  `us_id` int(11) DEFAULT NULL COMMENT 'user_id',
  `ls_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

/*Data for the table `user_lesson` */

/* Procedure structure for procedure `answer` */

/*!50003 DROP PROCEDURE IF EXISTS  `answer` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `answer`(IN p_user_id int(11), in p_battle_id int(11), in p_question_id int(11), in p_chose_answer int(11))
BEGIN
		/*Check if current user are attacker or defender*/
		SELECT IF(battles.`attacker_id` = p_user_id, 1, 0) AS checker FROM battles WHERE (battles.`battle_id` = p_battle_id) into @checker;
		if(@checker = 1) then	
			/*for attacker*/
			/*update only available for first time(current answer = 5: 'NONE')*/
			update battle_question
			set battle_question.`attacker_answer` = p_chose_answer
			where (`battle_question`.`battle_id` = p_battle_id && `battle_question`.`question_id` = p_question_id && `battle_question`.`attacker_answer` = 5);
		else
			/*for defender*/
			/*update only available for first time (current answer = 5: 'NONE')*/
			UPDATE battle_question
			SET battle_question.`defender_answer` = p_chose_answer
			WHERE (`battle_question`.`battle_id` = p_battle_id && `battle_question`.`question_id` = p_question_id && `battle_question`.`defender_answer` = 5);
		
		end if;
		
		SELECT IF(questions.`true_answer` = p_chose_answer, 1, 0) AS checker FROM `questions` WHERE (`questions`.`question_id` = p_question_id);
		
    END */$$
DELIMITER ;

/* Procedure structure for procedure `create_battle` */

/*!50003 DROP PROCEDURE IF EXISTS  `create_battle` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `create_battle`(IN p_attacker_id varchar(40), IN p_defender_id varchar(40))
BEGIN
	
	INSERT INTO battles (attacker_id, defender_id) VALUES(p_attacker_id, p_defender_id);
	select LAST_INSERT_ID() into @lastid ;
	INSERT INTO battle_question (battle_id, question_id) (SELECT @lastid AS battle_id, question_id FROM questions ORDER BY RAND() LIMIT 5);
	select * from battle_question where battle_id = @lastid;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `get_user_info` */

/*!50003 DROP PROCEDURE IF EXISTS  `get_user_info` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_info`(IN p_user_id VARCHAR(40))
BEGIN
		select `user_id`, `name`, `coin`, `rank`, `level`, `win_match`, `total_match`, `avatar_url` from user
		where `user_id` = p_user_id;
    END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
