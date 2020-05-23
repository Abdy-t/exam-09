use `forum`;

CREATE TABLE `themes` (
                        `id` int auto_increment NOT NULL,
                        `title` varchar(128),
                        `user` int not null,
                        `date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `count` int default 0,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `frk_user_id` FOREIGN KEY (`user`) REFERENCES `users` (`id`)
                       ) ENGINE=INNODB CHARACTER SET utf8 COLLATE utf8_general_ci;
