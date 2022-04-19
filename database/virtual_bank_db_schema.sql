-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema virtual_bank
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `virtual_bank` ;

-- -----------------------------------------------------
-- Schema virtual_bank
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `virtual_bank` DEFAULT CHARACTER SET utf8 ;
USE `virtual_bank` ;

-- -----------------------------------------------------
-- Table `virtual_bank`.`bank_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtual_bank`.`bank_account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `card_number` VARCHAR(45) NOT NULL,
  `card_type` ENUM('VISA', 'MASTERCARD', 'AMERICAN_EXPRESS') NOT NULL,
  `expiration_date` DATE NOT NULL,
  `pin` VARCHAR(256) NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  `blocked` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `card_number_UNIQUE` (`card_number` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `virtual_bank`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `virtual_bank`.`transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `bank_account_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transaction_bank_account_idx` (`bank_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_transaction_bank_account`
    FOREIGN KEY (`bank_account_id`)
    REFERENCES `virtual_bank`.`bank_account` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
