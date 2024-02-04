DROP DATABASE IF EXISTS `cinema`;
CREATE DATABASE `cinema`;

DROP USER IF EXISTS 'cinema_user'@'localhost';
CREATE USER 'cinema_user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'cinema';
GRANT ALL PRIVILEGES ON `cinema`.* TO 'cinema_user'@'localhost';

USE `cinema`;

DROP TABLE IF EXISTS `cinema`.movies;

CREATE TABLE `cinema`.`movies` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `duration` INT NOT NULL,
  `rating` INT NOT NULL,
  `genre` VARCHAR(100) NOT NULL,
  `release_date` DATE NOT NULL,
  PRIMARY KEY (`id`));

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('Space Odyssey', 'A breathtaking journey through space and time, exploring the uncharted territories of the universe.', 120, 5, 'Sci-Fi', '2024-01-01');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('The Great Adventure', 'An exhilarating tale of a group of friends embarking on a thrilling quest to find a hidden treasure.', 90, 4, 'Adventure', '2023-10-15');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('Heartfelt Melodies', 'A musical journey filled with emotions, songs, and dances that capture the essence of love and life.', 110, 5, 'Musical', '2023-11-20');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('Mystery at the Manor', 'A gripping detective story set in an old manor, where secrets and intrigue lurk in every corner.', 105, 4, 'Mystery', '2023-11-22');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('Lost in Time', 'An epic time-travel adventure that takes the protagonist through various historical events, altering the course of history.', 130, 5, 'Science Fiction', '2023-10-05');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('Whispers in the Wind', 'A poignant drama exploring the lives of a group of friends reuniting after a decade, uncovering old secrets and new truths.', 95, 4, 'Drama', '2023-12-01');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('Rise of the Champions', 'An inspirational sports story showcasing the journey of underdogs to become national champions.', 115, 5, 'Sports', '2024-02-20');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('The Ghosts of Elm Street', 'A horror thriller where a group of friends encounter paranormal activities in an abandoned house, revealing a dark past.', 100, 4, 'Horror', '2024-04-13');


DROP TABLE IF EXISTS `cinema`.`movie_session`;

CREATE TABLE `cinema`.`movie_session` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `movie_id` INT NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`movie_id`) REFERENCES `cinema`.movies(`id`));

-- Sunday
INSERT INTO `cinema`.`movie_session` (`movie_id`, `start_time`, `end_time`, `price`)
VALUES (8, '2024-02-04 15:00:00', '2024-02-04 16:45:00', 11.00),
       (1, '2024-02-04 17:30:00', '2024-02-04 19:40:00', 12.50),
       (2, '2024-02-04 20:00:00', '2024-02-04 21:35:00', 10.00);

-- Monday
INSERT INTO `cinema`.`movie_session` (`movie_id`, `start_time`, `end_time`, `price`)
VALUES (3, '2024-02-05 16:00:00', '2024-02-05 17:55:00', 13.00),
       (4, '2024-02-05 18:30:00', '2024-02-05 20:10:00', 11.00),
       (5, '2024-02-05 21:00:00', '2024-02-05 23:00:00', 15.00);

-- Tuesday
INSERT INTO `cinema`.`movie_session` (`movie_id`, `start_time`, `end_time`, `price`)
VALUES (6, '2024-02-06 14:00:00', '2024-02-06 15:30:00', 10.00),
       (7, '2024-02-06 16:30:00', '2024-02-06 18:20:00', 13.00),
       (8, '2024-02-06 19:00:00', '2024-02-06 20:45:00', 11.00);

-- Wednesday
INSERT INTO `cinema`.`movie_session` (`movie_id`, `start_time`, `end_time`, `price`)
VALUES (1, '2024-02-07 15:00:00', '2024-02-07 17:10:00', 12.50),
       (2, '2024-02-07 18:00:00', '2024-02-07 19:35:00', 10.00),
       (3, '2024-02-07 20:30:00', '2024-02-07 22:25:00', 13.00);

-- Thursday
INSERT INTO `cinema`.`movie_session` (`movie_id`, `start_time`, `end_time`, `price`)
VALUES (4, '2024-02-08 15:00:00', '2024-02-08 16:45:00', 11.00),
       (5, '2024-02-08 17:30:00', '2024-02-08 19:40:00', 12.50),
       (6, '2024-02-08 20:00:00', '2024-02-08 21:35:00', 10.00);

-- Friday
INSERT INTO `cinema`.`movie_session` (`movie_id`, `start_time`, `end_time`, `price`)
VALUES (7, '2024-02-09 16:00:00', '2024-02-09 17:55:00', 13.00),
       (8, '2024-02-09 18:30:00', '2024-02-09 20:10:00', 11.00),
       (1, '2024-02-09 21:00:00', '2024-02-09 23:00:00', 15.00);

-- Saturday
INSERT INTO `cinema`.`movie_session` (`movie_id`, `start_time`, `end_time`, `price`)
VALUES (2, '2024-02-10 14:00:00', '2024-02-10 15:30:00', 10.00),
       (3, '2024-02-10 16:30:00', '2024-02-10 18:20:00', 13.00),
       (4, '2024-02-10 19:00:00', '2024-02-10 20:45:00', 11.00);


DROP TABLE IF EXISTS `cinema`.`ticket`;

CREATE TABLE `cinema`.`ticket` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `movie_session_id` INT NOT NULL,
    `row` INT NOT NULL,
    `seat` INT NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`movie_session_id`) REFERENCES `cinema`.`movie_session`(`id`));

DROP TABLE IF EXISTS `cinema`.`users`;

CREATE TABLE `cinema`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `first_name` VARCHAR(100) NOT NULL,
    `last_name` VARCHAR(100) NOT NULL,
    `role` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`));

INSERT INTO `cinema`.`users` (`email`, `password`, `first_name`, `last_name`, `role`)
VALUES ('admin@admin.com', '$2a$12$aq.uW1aDXg9kcgTHalb9reQb2vrhus5buwTgYPgYCcNAnfEKDQRQa', 'Admin', 'New', 'ADMIN');

INSERT INTO `cinema`.`users` (`email`, `password`, `first_name`, `last_name`, `role`)
VALUES ('michaela@gmail.com', '$2a$12$FAZQKbJBsUbXKwUiAcIXZOsPBqWsGE35SAoj24xBSDflkJ2LPmO1e', 'Michaela', 'Majorosova', 'USER');

DROP TABLE IF EXISTS `cinema`.`order`;

CREATE TABLE `cinema`.`order` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `movie_session_id` INT NOT NULL,
    `order_time` DATETIME NOT NULL,
    `total_price` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `cinema`.users(`id`),
    FOREIGN KEY (`movie_session_id`) REFERENCES `cinema`.`movie_session`(`id`));

DROP TABLE IF EXISTS `cinema`.`order_ticket`;

CREATE TABLE `cinema`.`order_ticket` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `ticket_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`order_id`) REFERENCES `cinema`.`order`(`id`),
    FOREIGN KEY (`ticket_id`) REFERENCES `cinema`.`ticket`(`id`));