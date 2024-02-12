USE `soundscape_directory`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;


CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `users` 
VALUES 
('user','{bcrypt}$2a$10$rvkcEUW3FlkH2jTOEd01bOxlzCjyNwL7YlCuf66zpilIUFHUJrYuG',1),
('admin','{bcrypt}$2a$10$rvkcEUW3FlkH2jTOEd01bOxlzCjyNwL7YlCuf66zpilIUFHUJrYuG',1);


CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
('user','ROLE_USER'),
('admin','ROLE_ADMIN');
