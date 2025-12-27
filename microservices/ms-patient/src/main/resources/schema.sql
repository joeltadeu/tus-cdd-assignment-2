-- Use the schema
USE `pms-patient`;

-- Create 'patient' table if it doesn't exist
CREATE TABLE IF NOT EXISTS `patient`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(50) NOT NULL,
    `last_name` varchar(50) NOT NULL,
    `email` varchar(100) NOT NULL,
    `date_of_birth` date NOT NULL,
    `address` varchar(150) DEFAULT NULL,
    `created_at` datetime NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_patient_1` (`first_name`),
    KEY `idx_patient_2` (`email`),
    KEY `idx_patient_3` (`last_name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;