-- Use the schema
USE `pms-doctor`;

-- Create 'speciality' table if it doesn't exist
CREATE TABLE IF NOT EXISTS `speciality` (
    `id`          INT(11) NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(100) NOT NULL UNIQUE,
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create 'doctor' table if it doesn't exist
CREATE TABLE IF NOT EXISTS `doctor` (
    `id`            INT          NOT NULL AUTO_INCREMENT,
    `speciality_id` INT(11)      NOT NULL,
    `first_name`    VARCHAR(50)  NOT NULL,
    `last_name`     VARCHAR(50)  NOT NULL,
    `title`         VARCHAR(45)  NOT NULL,
    `email`         VARCHAR(150) NOT NULL,
    `phone`         VARCHAR(30)  NOT NULL,
    `department`    VARCHAR(100) NOT NULL,
    `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `idx_doctor_1` (`speciality_id`),
    INDEX `idx_doctor_2` (`last_name`),
    INDEX `idx_doctor_3` (`email`),
    INDEX `idx_doctor_4` (`department`),
    CONSTRAINT `fk_doctor_1`
    FOREIGN KEY (`speciality_id`)
    REFERENCES `speciality` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
