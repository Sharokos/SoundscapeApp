USE `soundscape_directory`;
DROP TABLE IF EXISTS `presetsound`;
DROP TABLE IF EXISTS `presetfreq`;
DROP TABLE IF EXISTS `preset`;


CREATE TABLE Preset (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `associated_soundscape_id` INT,
    `associated_username` VARCHAR(50) NOT NULL,
    `preset_name` VARCHAR(255) NOT NULL,
    CONSTRAINT `FK_PRESET_SOUNDSCAPE` 
        FOREIGN KEY (`associated_soundscape_id`) 
        REFERENCES `soundscape` (`id`),
    CONSTRAINT `FK_PRESET_USER` 
        FOREIGN KEY (`associated_username`) 
        REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE PresetSound (
    id INT PRIMARY KEY AUTO_INCREMENT,
    preset_id INT,
    sound_name VARCHAR(255) NOT NULL,
    volume INT,
	CONSTRAINT `FK_PRESET_SOUND` 
	FOREIGN KEY (`preset_id`) 
	REFERENCES `preset` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE PresetFreq (
    id INT PRIMARY KEY AUTO_INCREMENT,
    preset_id INT,
    sound_name VARCHAR(255) NOT NULL,
    frequency INT,
	CONSTRAINT `FK_PRESET_FREQ` 
	FOREIGN KEY (`preset_id`) 
	REFERENCES `preset` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO Preset VALUES
(1,1,"user","Default_test");

INSERT INTO PresetSound VALUES
(1001,1, 'Fireplace', 50),
(1002,1, 'Wind', 50),
(1003,1, 'Steps', 50),
(1004,1, 'Page', 50);

INSERT INTO PresetFreq VALUES
(1001,1, 'Fireplace', 1),
(1002,1, 'Wind', 1),
(1003,1, 'Steps', 1),
(1004,1, 'Page', 1);
