CREATE TABLE users
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username varchar(45) NOT NULL,
    password varchar(45) NOT NULL,
    enabled tinyint(1) NOT NULL
);
CREATE UNIQUE INDEX users_username_uindex ON users (username);

CREATE TABLE authorities
(
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    authority varchar(45) NOT NULL,
    CONSTRAINT fk_authority_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX user_id_authority_unique ON authorities (user_id, authority);