use `forum`;

CREATE TABLE `resets` (
                        `id` int auto_increment NOT NULL,
                        `token` varchar(128),
                        `user_id` int not null,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `fk_customer_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
                       ) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci;
