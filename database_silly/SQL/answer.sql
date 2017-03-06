DELIMITER $$

USE `silly_english`$$

DROP PROCEDURE IF EXISTS `answer`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `answer`(IN p_user_id INT(11), IN p_battle_id INT(11), IN p_question_id INT(11), IN p_chose_answer INT(11))
BEGIN
		/*Check if current user are attacker or defender*/
		SELECT IF(battles.`attacker_id` = p_user_id, 1, 0) AS checker FROM battles WHERE (battles.`battle_id` = p_battle_id) INTO @checker;
		IF(@checker = 1) THEN	
			/*for attacker*/
			/*update only available for first time(current answer = 5: 'NONE')*/
			UPDATE battle_question
			SET battle_question.`attacker_answer` = p_chose_answer
			WHERE (`battle_question`.`battle_id` = p_battle_id && `battle_question`.`question_id` = p_question_id && `battle_question`.`attacker_answer` = 5);
		ELSE
			/*for defender*/
			/*update only available for first time (current answer = 5: 'NONE')*/
			UPDATE battle_question
			SET battle_question.`defender_answer` = p_chose_answer
			WHERE (`battle_question`.`battle_id` = p_battle_id && `battle_question`.`question_id` = p_question_id && `battle_question`.`defender_answer` = 5);
		
		END IF;
		
		SELECT IF(questions.`true_answer` = p_chose_answer, 1, 0) AS checker FROM `questions` WHERE (`questions`.`question_id` = p_question_id);
		
    END$$

DELIMITER ;