use `forum`;

CREATE TABLE `comments` (
                        `id` int auto_increment NOT NULL,
                        `text` varchar(140),
                        `user` int not null,
                        `theme` int not null,
                        `date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `fk_user_id` FOREIGN KEY (`user`) REFERENCES `users` (`id`),
                        CONSTRAINT `fk_theme_id` FOREIGN KEY (`theme`) REFERENCES `themes` (`id`)
                       ) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci;
