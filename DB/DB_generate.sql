CREATE DATABASE IF NOT EXISTS `soundscape_directory`;
USE `soundscape_directory`;
DROP TABLE IF EXISTS `sound`;
DROP TABLE IF EXISTS `soundscape`;

CREATE TABLE `soundscape` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(45) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `soundscape_image` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
	
CREATE TABLE Sound (
    `sound_id` INT PRIMARY KEY,
    `sound_name` VARCHAR(255) NOT NULL,
    `sound_path` VARCHAR(255) NOT NULL,
    `image_path` VARCHAR(255) NOT NULL,
    `id` INT,
    FOREIGN KEY (id) REFERENCES Soundscape(id)
);