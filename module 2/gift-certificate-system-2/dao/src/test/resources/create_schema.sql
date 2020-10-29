-- -----------------------------------------------------
-- Schema GiftCertificateDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `test_db`;
USE `test_db` ;
-- -----------------------------------------------------
-- Table `GiftCertificateDB`.`Tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test_db`.`Tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `GiftCertificateDB`.`GiftCertificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test_db`.`GiftCertificate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  `Description` VARCHAR(255) NULL,
  `Price` DOUBLE NULL,
  `CreateDate` timestamp NULL,
  `TimeZone_CreateDate` VARCHAR(45) NULL,
  `LastUpdateDate` timestamp NULL,
  `TimeZone_LastUpdateDate` VARCHAR(45) NULL,
  `Duration` INT NULL,
  PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `GiftCertificateDB`.`Tag_has_GiftCertificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `test_db`.`Tag_has_GiftCertificate` (
	id bigint primary key AUTO_INCREMENT,
  `Tag_id` bigint NOT NULL,
  `GiftCertificate_id` bigint NOT NULL,
  CONSTRAINT `fk_Tag_has_GiftCertificate_Tag`
    FOREIGN KEY (`Tag_id`)
    REFERENCES `test_db`.`Tag` (`id`)
    ON DELETE cascade
    ON UPDATE cascade,
  CONSTRAINT `fk_Tag_has_GiftCertificate_GiftCertificate1`
    FOREIGN KEY (`GiftCertificate_id`)
    REFERENCES `test_db`.`GiftCertificate` (`id`)
    ON DELETE cascade
    ON UPDATE cascade);

insert into test_db.Tag(`Name`) values ('rock');
insert into test_db.Tag(`Name`) values ('music');

insert into test_db.GiftCertificate (`Name`,`Description`,`Price`,`CreateDate`,`TimeZone_CreateDate`,`LastUpdateDate`,`TimeZone_LastUpdateDate`,`Duration` )
values ('name1', 'desc1',12.2,'2020-10-10 10:10:10', 'Europe/Moscow','2020-10-10 10:10:10', 'Europe/Moscow', 12);

insert into test_db.GiftCertificate (`Name`,`Description`,`Price`,`CreateDate`,`TimeZone_CreateDate`,`LastUpdateDate`,`TimeZone_LastUpdateDate`,`Duration` )
values ('name2', 'desc2',12.2,'2020-10-10 10:10:10', 'Europe/Moscow','2020-10-10 10:10:10', 'Europe/Moscow', 12);

insert into Tag_has_GiftCertificate (`Tag_id`,`GiftCertificate_id`) values (1,2);
insert into Tag_has_GiftCertificate (`Tag_id`,`GiftCertificate_id`) values (1,1);
insert into Tag_has_GiftCertificate (`Tag_id`,`GiftCertificate_id`) values (2,1);