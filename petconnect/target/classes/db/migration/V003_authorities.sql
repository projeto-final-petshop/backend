CREATE TABLE `authorities`
(
    `id`      BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` int         NOT NULL,
    `name`    varchar(50) NOT NULL,
    KEY `user_id` (`user_id`),
    CONSTRAINT `fk_authorities_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);
