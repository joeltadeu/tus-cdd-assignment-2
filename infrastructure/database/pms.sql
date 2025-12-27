CREATE DATABASE `pms-doctor`;

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

INSERT IGNORE INTO `speciality` (`description`)
VALUES
    ('Primary Care'),
    ('Internal Medicine'),
    ('Pediatrics'),
    ('Geriatrics'),
    ('Cardiology'),
    ('Dermatology'),
    ('Endocrinology'),
    ('Gastroenterology'),
    ('Hematology'),
    ('Oncology'),
    ('Nephrology'),
    ('Pulmonology'),
    ('Rheumatology'),
    ('Neurology'),
    ('Obstetrics & Gynecology'),
    ('Ophthalmology'),
    ('Psychiatry'),
    ('Urology');

CREATE DATABASE `pms-appointment`;

USE `pms-appointment`;

CREATE TABLE `appointment` (
    `id`                    int(11) NOT NULL AUTO_INCREMENT,
    `patient_id`            int(11) NOT NULL,
    `doctor_id`             int(11) NOT NULL,
    `start_time`            datetime NOT NULL,
    `end_time`              datetime NOT NULL,
    `duration`              int(11) NOT NULL,
    `title`                 varchar(100) NOT NULL,
    `description`           text,
    `notes`                 varchar(100) DEFAULT NULL,
    `follow_up_required`    tinyint(4) DEFAULT NULL,
    `cancellation_time`     datetime DEFAULT NULL,
    `cancellation_reason`   varchar(100) DEFAULT NULL,
    `type`                  varchar(50) NOT NULL,
    `status`                varchar(50) NOT NULL,
    `created_at`            datetime NOT NULL,
    `last_updated`          datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_appointment_1` (`patient_id`),
    KEY `idx_appointment_2` (`start_time`),
    KEY `idx_appointment_3` (`status`),
    KEY `idx_appointment_4` (`doctor_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE DATABASE `pms-patient`;

USE `pms-patient`;

-- Create 'patient' table if it doesn't exist
CREATE TABLE IF NOT EXISTS `patient`  (
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `first_name`    varchar(50) NOT NULL,
    `last_name`     varchar(50) NOT NULL,
    `email`         varchar(100) NOT NULL,
    `date_of_birth` date NOT NULL,
    `address`       varchar(150) DEFAULT NULL,
    `created_at`    datetime NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_patient_1` (`first_name`),
    KEY `idx_patient_2` (`email`),
    KEY `idx_patient_3` (`last_name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;