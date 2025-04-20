CREATE TABLE teams (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre VARCHAR(255) UNIQUE ,
                       liga VARCHAR(255),
                       pais VARCHAR(255)
);