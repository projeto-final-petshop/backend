CREATE TABLE users
(
    id        BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255)                     NOT NULL CHECK (LENGTH(name) >= 3),
    username  VARCHAR(255)                     NOT NULL UNIQUE,
    cpf       VARCHAR(14)                      NOT NULL UNIQUE,
    password  VARCHAR(255)                     NOT NULL,
    role      ENUM ('ROLE_USER', 'ROLE_ADMIN') NOT NULL,
    createdAt DATETIME                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME                         NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE authorities
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    user_id BIGINT       NOT NULL,
    CONSTRAINT authorities_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE addresses
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    cep         CHAR(8)         NOT NULL,
    logradouro  VARCHAR(255)    NOT NULL CHECK (LENGTH(logradouro) >= 3),
    complemento VARCHAR(255),
    bairro      VARCHAR(255),
    localidade  VARCHAR(50)     NOT NULL CHECK (LENGTH(localidade) >= 3),
    uf          CHAR(2)         NOT NULL CHECK (LENGTH(uf) = 2),
    numero      VARCHAR(255),
    user_id     BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE pets
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255)                                           NOT NULL CHECK (LENGTH(name) >= 3),
    breed       VARCHAR(255)                                                    DEFAULT NULL,
    color       VARCHAR(255)                                                    DEFAULT NULL,
    birthdate   DATE,
    animal_type ENUM ('DOG', 'CAT', 'RABBIT', 'BIRD', 'FISH', 'OTHER') NOT NULL,
    user_id     BIGINT UNSIGNED                                        NOT NULL,
    created_at  DATETIME                                               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME                                               NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
