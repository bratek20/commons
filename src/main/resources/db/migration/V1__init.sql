CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identity_id BIGINT NOT NULL,
    name VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL
);