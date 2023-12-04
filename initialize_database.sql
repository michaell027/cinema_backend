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
VALUES ('Space Odyssey', 'A breathtaking journey through space and time, exploring the uncharted territories of the universe.', 120, 5, 'Sci-Fi', '2023-01-01');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('The Great Adventure', 'An exhilarating tale of a group of friends embarking on a thrilling quest to find a hidden treasure.', 90, 4, 'Adventure', '2023-03-15');

INSERT INTO `cinema`.`movies` (`title`, `description`, `duration`, `rating`, `genre`, `release_date`)
VALUES ('Heartfelt Melodies', 'A musical journey filled with emotions, songs, and dances that capture the essence of love and life.', 110, 5, 'Musical', '2023-05-20');

DROP TABLE IF EXISTS `cinema`.`movie_session`;

CREATE TABLE `cinema`.`movie_session` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `movie_id` INT NOT NULL,
    `start_time` DATETIME NOT NULL,
    `end_time` DATETIME NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`movie_id`) REFERENCES `cinema`.movies(`id`));

DROP TABLE IF EXISTS `cinema`.`ticket`;

CREATE TABLE `cinema`.`ticket` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `movie_session_id` INT NOT NULL,
    `row` INT NOT NULL,
    `seat` INT NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`movie_session_id`) REFERENCES `cinema`.`movie_session`(`id`));

DROP TABLE IF EXISTS `cinema`.`user`;

CREATE TABLE `cinema`.`user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `first_name` VARCHAR(100) NOT NULL,
    `last_name` VARCHAR(100) NOT NULL,
    `role` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`));

DROP TABLE IF EXISTS `cinema`.`order`;

CREATE TABLE `cinema`.`order` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `movie_session_id` INT NOT NULL,
    `order_time` DATETIME NOT NULL,
    `total_price` DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `cinema`.`user`(`id`),
    FOREIGN KEY (`movie_session_id`) REFERENCES `cinema`.`movie_session`(`id`));

DROP TABLE IF EXISTS `cinema`.`order_ticket`;

CREATE TABLE `cinema`.`order_ticket` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `order_id` INT NOT NULL,
    `ticket_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`order_id`) REFERENCES `cinema`.`order`(`id`),
    FOREIGN KEY (`ticket_id`) REFERENCES `cinema`.`ticket`(`id`));