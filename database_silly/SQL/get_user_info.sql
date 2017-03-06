DELIMITER $$

USE `silly_english`$$

DROP PROCEDURE IF EXISTS `get_user_info`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_info`(IN p_user_id VARCHAR(40))
BEGIN
		SELECT `user_id`, `name`, `coin`, `rank`, `level`, `win_match`, `total_match`, `avatar_url` FROM USER
		WHERE `user_id` = p_user_id;
    END$$

DELIMITER ;