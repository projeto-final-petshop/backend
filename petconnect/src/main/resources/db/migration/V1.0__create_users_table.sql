CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled TINYINT DEFAULT 0 NOT NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL
)