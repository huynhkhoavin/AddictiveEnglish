DELIMITER $$

USE `silly_english`$$

DROP PROCEDURE IF EXISTS `create_battle`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `create_battle`(IN p_attacker_id VARCHAR(40), IN p_defender_id VARCHAR(40))
BEGIN
	
	INSERT INTO battles (attacker_id, defender_id) VALUES(p_attacker_id, p_defender_id);
	SELECT LAST_INSERT_ID() INTO @lastid ;
	INSERT INTO battle_question (battle_id, question_id) (SELECT @lastid AS battle_id, question_id FROM questions ORDER BY RAND() LIMIT 5);
	SELECT * FROM battle_question WHERE battle_id = @lastid;
    END$$

DELIMITER ;