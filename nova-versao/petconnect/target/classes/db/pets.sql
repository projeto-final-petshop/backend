CREATE TABLE pets
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    age        INT CHECK (age >= 0),
    color      VARCHAR(50),
    breed      VARCHAR(100),
    animalType VARCHAR(50),
    birthdate  DATE CHECK (birthdate < CURRENT_DATE),
    user_id    BIGINT,
    createdAt  DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt  DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO pets (name, age, color, breed, animalType, birthdate, user_id)
VALUES ('Rex', 3, 'Brown', 'German Shepherd', 'Dog', '2019-04-23', 1),
       ('Mia', 2, 'Black', 'Labrador', 'Dog', '2020-07-12', 2),
       ('Luna', 1, 'White', 'Persian', 'Cat', '2022-02-14', 3),
       ('Buddy', 5, 'Golden', 'Golden Retriever', 'Dog', '2018-01-01', 4),
       ('Charlie', 4, 'Gray', 'British Shorthair', 'Cat', '2019-05-10', 5),
       ('Max', 2, 'Black', 'Poodle', 'Dog', '2021-09-05', 6),
       ('Simba', 3, 'Orange', 'Tabby', 'Cat', '2020-11-22', 7),
       ('Daisy', 1, 'Brown', 'Bulldog', 'Dog', '2023-03-03', 8),
       ('Bella', 4, 'White', 'Siamese', 'Cat', '2019-12-25', 9),
       ('Oscar', 2, 'Black', 'Pug', 'Dog', '2021-06-15', 10);
