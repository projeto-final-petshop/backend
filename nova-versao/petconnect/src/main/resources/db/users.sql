CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    cpf         VARCHAR(14),
    phoneNumber VARCHAR(20),
    role_id     BIGINT       NOT NULL,
    createdAt   DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users (name, email, password, cpf, phoneNumber, role_id)
VALUES ('Alice', 'alice@example.com', 'password123', '123.456.789-00', '555-1234', 1),
       ('Bob', 'bob@example.com', 'password456', '987.654.321-00', '555-5678', 2),
       ('Charlie', 'charlie@example.com', 'password789', '456.789.123-00', '555-9012', 3),
       ('David', 'david@example.com', 'password012', '321.654.987-00', '555-3456', 1),
       ('Eva', 'eva@example.com', 'password345', '654.321.987-00', '555-7890', 2),
       ('Frank', 'frank@example.com', 'password678', '789.123.456-00', '555-1122', 3),
       ('Grace', 'grace@example.com', 'password901', '147.258.369-00', '555-3344', 1),
       ('Hannah', 'hannah@example.com', 'password234', '258.369.147-00', '555-5566', 2),
       ('Ian', 'ian@example.com', 'password567', '369.147.258-00', '555-7788', 3),
       ('Judy', 'judy@example.com', 'password890', '456.123.789-00', '555-9900', 1);

