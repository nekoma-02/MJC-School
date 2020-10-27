-- -----------------------------------------------------
-- Schema GiftCertificateDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `GiftCertificateDB` DEFAULT CHARACTER SET utf8 ;
USE `GiftCertificateDB` ;

-- -----------------------------------------------------
-- Table `GiftCertificateDB`.`Tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GiftCertificateDB`.`Tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GiftCertificateDB`.`GiftCertificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GiftCertificateDB`.`GiftCertificate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  `Description` VARCHAR(255) NULL,
  `Price` DOUBLE NULL,
  `CreateDate` timestamp NULL,
  `TimeZone_CreateDate` VARCHAR(45) NULL,
  `LastUpdateDate` timestamp NULL,
  `TimeZone_LastUpdateDate` VARCHAR(45) NULL,
  `Duration` INT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GiftCertificateDB`.`Tag_has_GiftCertificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GiftCertificateDB`.`Tag_has_GiftCertificate` (
	id bigint primary key auto_increment,
  `Tag_id` bigint NOT NULL,
  `GiftCertificate_id` bigint NOT NULL,
  CONSTRAINT `fk_Tag_has_GiftCertificate_Tag`
    FOREIGN KEY (`Tag_id`)
    REFERENCES `GiftCertificateDB`.`Tag` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_Tag_has_GiftCertificate_GiftCertificate1`
    FOREIGN KEY (`GiftCertificate_id`)
    REFERENCES `GiftCertificateDB`.`GiftCertificate` (`id`)
    ON DELETE cascade
    ON UPDATE cascade)
ENGINE = InnoDB;

insert into Tag value (default, 'rock');
insert into Tag value (default, 'kek');
insert into Tag value (default, 'lol');
insert into Tag value (default, 'music');

insert into GiftCertificate value (default, 'name1', 'desc1',12.2,'2020-10-14 15:15:15', 'Europe/Moscow','2020-10-14 15:15:15', 'Europe/Moscow', 12);
insert into GiftCertificate value (default, 'name2', 'desc1',12.2,'2020-10-14 15:15:15', 'Europe/Moscow','2020-10-14 15:15:15', 'Europe/Moscow', 12);
insert into GiftCertificate value (default, 'name3', 'desc1',12.2,'2020-10-14 15:15:15', 'Europe/Moscow','2020-10-14 15:15:15', 'Europe/Moscow', 12);

insert into Tag_has_GiftCertificate value (default, 1,1);
insert into Tag_has_GiftCertificate value (default, 1,2);
insert into Tag_has_GiftCertificate value (default, 2,1);