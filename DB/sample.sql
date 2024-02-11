USE `soundscape_directory`;
INSERT INTO soundscape VALUES
(1, 'Dark Library', 'A soundscape for the lonesome night readers.', 'DarkLibrary/Images/library.jpg'),
(2, 'Dark Forest', 'A soundscape for forest wanderers.', 'DarkForest/Images/forest.jpg'),
(3, 'Mountain', 'A soundscape for the mountain-lovers', 'Mountain/Images/mountain.jpg'),
(4, 'Medieval Village', 'A soundscape for the those who long for the simpler village days.', 'Village/Images/village.jpg');

INSERT INTO sound  VALUES
(1001, 'Fireplace', 'DarkLibrary/Sound/fire.mp3','DarkLibrary/Images/library.jpg', 1),
(1002, 'Wind','DarkLibrary/Sound/wind.mp3','DarkLibrary/Images/library.jpg', 1),
(1003, 'Steps','DarkLibrary/Sound/steps.mp3','DarkLibrary/Images/library.jpg', 1),
(1004, 'Page','DarkLibrary/Sound/page.mp3','DarkLibrary/Images/library.jpg', 1);