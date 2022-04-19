-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ip_2022
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ip_2022
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ip_2022` DEFAULT CHARACTER SET utf8 ;
USE `ip_2022` ;

-- -----------------------------------------------------
-- Table `ip_2022`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ip_2022`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `surname` VARCHAR(255) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `token` VARCHAR(256) NULL,
  `role` ENUM('USER', 'ADMIN') NOT NULL,
  `status` ENUM('REQUESTED', 'ACTIVE', 'BLOCKED') NOT NULL,
  `logged_in` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ip_2022`.`museum`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ip_2022`.`museum` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `tel` VARCHAR(20) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `contry` VARCHAR(255) NOT NULL,
  `geolat` FLOAT(10,6) NOT NULL,
  `geolng` FLOAT(10,6) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `tel_UNIQUE` (`tel` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ip_2022`.`virtual_visit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ip_2022`.`virtual_visit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `start_time` TIME NOT NULL,
  `duration` INT NOT NULL,
  `museum_id` INT NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_virtual_visit_museum_idx` (`museum_id` ASC) VISIBLE,
  CONSTRAINT `fk_virtual_visit_museum`
    FOREIGN KEY (`museum_id`)
    REFERENCES `ip_2022`.`museum` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ip_2022`.`ticket`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ip_2022`.`ticket` (
  `virtual_visit_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(255) NOT NULL,
  INDEX `fk_virtual_visit_has_user_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_virtual_visit_has_user_virtual_visit1_idx` (`virtual_visit_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,
  CONSTRAINT `fk_virtual_visit_has_user_virtual_visit1`
    FOREIGN KEY (`virtual_visit_id`)
    REFERENCES `ip_2022`.`virtual_visit` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_virtual_visit_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `ip_2022`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ip_2022`.`log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ip_2022`.`log` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` VARCHAR(45) NOT NULL,
  `type` VARCHAR(45) NOT NULL,
  `message` VARCHAR(255) NOT NULL,
  `date_time` DATETIME NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
