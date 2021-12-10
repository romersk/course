CREATE DATABASE IF NOT EXISTS factory;
USE factory;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT NOT NULL,
    id_person INT,
    login VARCHAR(50) NOT NULL,
    pass VARCHAR(50) NOT NULL,
    role VARCHAR(30),
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS operation (
    id INT AUTO_INCREMENT NOT NULL,
    id_users INT,
    created_at DATE NOT NULL,
    hours DECIMAL(10,2),
    PRIMARY KEY(id),
    FOREIGN KEY(id_users) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS salary (
    id INT AUTO_INCREMENT NOT NULL,
    id_user INT,
    number_month INT,
    count_time DECIMAL(10,2),
    size DECIMAL(10,2),
    PRIMARY KEY(id),
    FOREIGN KEY (id_user) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS person (
    id INT AUTO_INCREMENT NOT NULL,
    id_user INT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    category INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (id_user) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE person ADD FOREIGN KEY (id_user) REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS shoes (
    id INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    expenses DECIMAL(10,2) NOT NULL,
    costs DECIMAL(10,2) NOT NULL,
    time_manufacture DECIMAL(10,2) NOT NULL,
    deleted_at DATETIME,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS content (
    id INT AUTO_INCREMENT NOT NULL,
    id_operation INT,
    id_shoes INT,
    count_of_shoes INT,
    PRIMARY KEY(id),
    FOREIGN KEY(id_operation) REFERENCES operation(id) ON DELETE CASCADE,
    FOREIGN KEY(id_shoes) REFERENCES shoes(id)
);

INSERT INTO users(login, pass, role) VALUES ('rome@gmail.com', 'romersk222', 'ADMIN');
INSERT INTO person(id_user, first_name, last_name, category) VALUES(1,'Роман','Евдокимов',10);

UPDATE users
SET users.id_person = 1
WHERE users.id = 1