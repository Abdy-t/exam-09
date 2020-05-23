use `forum`;
insert  into   `users` (`email`,`password`,`fullname`) values ('a@gmail.com', '111', 'Alex');

insert into `themes` (`user`, `title`, `date`) values (1, 'Sport', NOW()), (1, 'Job', NOW()),
                                             (1, 'Health', NOW()), (1, 'Crisis', NOW()),
                                             (1, 'Family', NOW()), (1, 'Memory', NOW()),
                                             (1, 'Corona virus', NOW()), (1, 'Java', NOW()),
                                             (1, 'SQL', NOW());