INSERT INTO `CSC480Data`.`PLAYER_TABLE` (`uid`, `cumulative_score`, `longest_word`, `bonuses`, `highest_word_score`) VALUES ('kim', 200, 'MAGICIAN', 6, 29);
INSERT INTO `CSC480Data`.`PLAYER_TABLE` (`uid`, `cumulative_score`, `longest_word`, `bonuses`, `highest_word_score`) VALUES ('ken', 150, 'MAVRICK', 5, 30);
INSERT INTO `CSC480Data`.`PLAYER_TABLE` (`uid`, `cumulative_score`, `longest_word`, `bonuses`, `highest_word_score`) VALUES ('yingying', 150, 'COMPUTERS', 3, 20);
INSERT INTO `CSC480Data`.`PLAYER_TABLE` (`uid`, `cumulative_score`, `longest_word`, `bonuses`, `highest_word_score`) VALUES ('christian', 92, 'LIGHT', 3, 9);
INSERT INTO `CSC480Data`.`PLAYER_TABLE` (`uid`, `cumulative_score`, `longest_word`, `bonuses`, `highest_word_score`) VALUES ('dor', 161, 'WHAT', 3, 12);
INSERT INTO `CSC480Data`.`PLAYER_TABLE` (`uid`, `cumulative_score`, `longest_word`, `bonuses`, `highest_word_score`) VALUES ('john', 31, 'HAT', 3, 7);

INSERT INTO `CSC480Data`.`PLAYER_TEAM` (`uid`, `teamid`) VALUES ('kim', 'green');
INSERT INTO `CSC480Data`.`PLAYER_TEAM` (`uid`, `teamid`) VALUES ('ken', 'gold');
INSERT INTO `CSC480Data`.`PLAYER_TEAM` (`uid`, `teamid`) VALUES ('yingying', 'green');
INSERT INTO `CSC480Data`.`PLAYER_TEAM` (`uid`, `teamid`) VALUES ('christian', 'gold');
INSERT INTO `CSC480Data`.`PLAYER_TEAM` (`uid`, `teamid`) VALUES ('dor', 'green');
INSERT INTO `CSC480Data`.`PLAYER_TEAM` (`uid`, `teamid`) VALUES ('john', 'gold');

INSERT INTO `CSC480Data`.`USER_TABLE` (`uid`, `mac_addr`) VALUES ('kim', '00-14-22-01-23-45');
INSERT INTO `CSC480Data`.`USER_TABLE` (`uid`, `mac_addr`) VALUES ('ken', '00-13-21-00-22-44');
INSERT INTO `CSC480Data`.`USER_TABLE` (`uid`, `mac_addr`) VALUES ('yingying', '00-12-20-00-21-44');
INSERT INTO `CSC480Data`.`USER_TABLE` (`uid`, `mac_addr`) VALUES ('christian', '00-11-19-00-20-43');
INSERT INTO `CSC480Data`.`USER_TABLE` (`uid`, `mac_addr`) VALUES ('dor', '00-10-18-00-19-42');
INSERT INTO `CSC480Data`.`USER_TABLE` (`uid`, `mac_addr`) VALUES ('john', '00-09-17-00-18-41');

insert into GAME_TABLE (game_id, gold_team_score, green_team_score) values (0, 90, 120);
insert into GAME_TABLE (game_id, gold_team_score, green_team_score) values (1, 91, 91);
insert into GAME_TABLE (game_id, gold_team_score, green_team_score) values (2, 50, 100);
insert into GAME_TABLE (game_id, gold_team_score, green_team_score) values (3, 42, 200);

insert into TEAM_TABLE (team_name, cumulative_game_score, highest_word_score, highest_game_session_score, win_count, lose_count, tie_count, longest_word, bonuses, dirty_word) values ("green", 511, 30, 200, 3, 0, 1, "computers", 21, 5);
insert into TEAM_TABLE (team_name, cumulative_game_score, highest_word_score, highest_game_session_score, win_count, lose_count, tie_count, longest_word, bonuses, dirty_word) values ("gold", 273, 33, 91, 0, 3, 1, "mavrick", 13, 10);

insert into DIRTY_WORD_TABLE (dirty_word_id, dirty_word, attempt_usage_count) values (0, "FUCK", 7);
insert into DIRTY_WORD_TABLE (dirty_word_id, dirty_word, attempt_usage_count) values (1, "SHIT", 3);
insert into DIRTY_WORD_TABLE (dirty_word_id, dirty_word, attempt_usage_count) values (2, "ASS", 2);
insert into DIRTY_WORD_TABLE (dirty_word_id, dirty_word, attempt_usage_count) values (3, "CUNT", 3);

insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (0, "GOBLIN", 13, 6, 0, 1);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (1, "COMPUTERS", 20, 9, 1, 1);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (2, "MAGICIAN", 29, 4, 0, 2);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (3, "MAVRICK", 30, 3, 0, 2);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (4, "LOVE", 21, 4, 0, 1);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (5, "SLAM", 20, 4, 0, 2);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (6, "JAMS", 20, 4, 0, 2);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (7, "BALL", 15, 4, 0, 1);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (8, "HAT", 7, 4, 0, 0);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (9, "WHAT", 12, 4, 0, 1);
insert into VALID_WORD_TABLE (word_id, word, value, length, is_extension, bonuses_used) values (10, "LIGHT", 9, 4, 0, 0);

