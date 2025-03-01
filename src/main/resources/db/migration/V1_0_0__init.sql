CREATE TABLE IF NOT EXISTS SHIPS.SPACESHIPS
(
    ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME         VARCHAR(255) NOT NULL,
    SERIES       VARCHAR(255) NOT NULL,
    MANUFACTURER VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS SHIPS."USERS"
(
    ID       BIGINT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(255) NOT NULL,
    NAME     VARCHAR(255),
    PASSWORD VARCHAR(255) NOT NULL,
    ROLE     VARCHAR(255)
);