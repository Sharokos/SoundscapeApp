USE `soundscape_directory`;
DROP TABLE IF EXISTS `PresetSound`;
DROP TABLE IF EXISTS `Preset`;


CREATE TABLE Preset (
    id INT PRIMARY KEY AUTO_INCREMENT,
    preset_name VARCHAR(255) NOT NULL
);
CREATE TABLE PresetSound (
    id INT PRIMARY KEY AUTO_INCREMENT,
    preset_id INT,
    sound_name VARCHAR(255) NOT NULL,
    volume INT,
	CONSTRAINT `FK_PRESET` 
	FOREIGN KEY (`preset_id`) 
	REFERENCES `preset` (`id`) 
);
INSERT INTO Preset VALUES
(1,"Default_test");
INSERT INTO PresetSound VALUES
(1001,1, 'Fireplace', 50),
(1002,1, 'Wind', 50),
(1003,1, 'Steps', 50),
(1004,1, 'Page', 50);
