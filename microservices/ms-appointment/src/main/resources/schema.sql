-- Create 'appointment' table if it doesn't exist
CREATE TABLE `appointment` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `patient_id` int(11) NOT NULL,
    `doctor_id` int(11) NOT NULL,
    `start_time` datetime NOT NULL,
    `end_time` datetime NOT NULL,
    `duration` int(11) NOT NULL,
    `title` varchar(100) NOT NULL,
    `description` text,
    `notes` varchar(100) DEFAULT NULL,
    `follow_up_required` tinyint(4) DEFAULT NULL,
    `cancellation_time` datetime DEFAULT NULL,
    `cancellation_reason` varchar(100) DEFAULT NULL,
    `type` varchar(50) NOT NULL,
    `status` varchar(50) NOT NULL,
    `created_at` datetime NOT NULL,
    `last_updated` datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_appointment_1` (`patient_id`),
    KEY `idx_appointment_2` (`start_time`),
    KEY `idx_appointment_3` (`status`),
    KEY `idx_appointment_4` (`doctor_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;