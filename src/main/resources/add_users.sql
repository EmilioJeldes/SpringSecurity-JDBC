INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$UjkBbFTTLtrVrPWKm4AmjufiyGGGprc04nxghBeWmWyP1o25lA.ka', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$U.kxzZsFe3.1Uw3qgVicXek9X8HeyRbVGMRsG3VeuoGWRXyV2zHF2', 1);

INSERT INTO authorities (user_id, authority) VALUES ('1', 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES ('2', 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES ('2', 'ROLE_ADMIN');
