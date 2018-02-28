-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema CSC480Data
-- -----------------------------------------------------
-- v1 of team blue's database for CSC480

-- -----------------------------------------------------
-- Schema CSC480Data
--
-- v1 of team blue's database for CSC480
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CSC480Data` DEFAULT CHARACTER SET utf8 ;
USE `CSC480Data` ;

-- -----------------------------------------------------
-- Table `CSC480Data`.`TEAM_TABLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSC480Data`.`TEAM_TABLE` (
  `team_name` VARCHAR(20) NOT NULL,
  `cumulative_game_score` INT NOT NULL,
  `highest_word_score` INT NOT NULL,
  `highest_game_session_score` INT NOT NULL,
  `win_count` INT NOT NULL,
  `lose_count` INT NOT NULL,
  `tie_count` INT NOT NULL,
  `longest_word` VARCHAR(15) NOT NULL,
  `bonuses` INT NOT NULL,
  `dirty_word` INT NOT NULL,
  PRIMARY KEY (`team_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSC480Data`.`GAME_TABLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSC480Data`.`GAME_TABLE` (
  `game_id` INT NOT NULL,
  `gold_team_score` INT NOT NULL,
  `green_team_score` INT NOT NULL,
  PRIMARY KEY (`game_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSC480Data`.`VALID_WORD_TABLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSC480Data`.`VALID_WORD_TABLE` (
  `word_id` INT NOT NULL,
  `word` VARCHAR(15) NOT NULL,
  `value` INT NOT NULL,
  `length` INT NOT NULL,
  `is_extension` INT NOT NULL,
  `bonuses_used` INT NOT NULL,
  PRIMARY KEY (`word_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSC480Data`.`DIRTY_WORD_TABLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSC480Data`.`DIRTY_WORD_TABLE` (
  `dirty_word_id` INT NOT NULL,
  `dirty_word` VARCHAR(15) NULL,
  `attempt_usage_count` INT NULL,
  PRIMARY KEY (`dirty_word_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSC480Data`.`PLAYER_TABLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSC480Data`.`PLAYER_TABLE` (
  `uid` VARCHAR(12) NOT NULL,
  `cumulative_score` INT NOT NULL,
  `longest_word` VARCHAR(15) NULL,
  `bonuses` INT NOT NULL,
  `highest_word_score` INT NULL,
  PRIMARY KEY (`uid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSC480Data`.`PLAYER_TEAM`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSC480Data`.`PLAYER_TEAM` (
  `uid` VARCHAR(12) NOT NULL,
  `teamid` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`uid`))
ENGINE = InnoDB
COMMENT = 'Used for associating a team with players';


-- -----------------------------------------------------
-- Table `CSC480Data`.`USER_TABLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSC480Data`.`USER_TABLE` (
  `uid` VARCHAR(12) NOT NULL,
  `mac_addr` VARCHAR(20) NULL,
  PRIMARY KEY (`uid`))
ENGINE = InnoDB
COMMENT = 'Used to associate players with thier mac_addr';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
