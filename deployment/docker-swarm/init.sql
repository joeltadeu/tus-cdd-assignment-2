-- MariaDB Initialization Script
-- Creates separate databases for each microservice

-- Create databases
CREATE DATABASE IF NOT EXISTS `pms-patient` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS `pms-doctor` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS `pms-appointment` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Grant privileges (root already has all privileges, but this is for documentation)
GRANT ALL PRIVILEGES ON `pms-patient`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `pms-doctor`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `pms-appointment`.* TO 'root'@'%';

FLUSH PRIVILEGES;

-- Show created databases
SHOW DATABASES;