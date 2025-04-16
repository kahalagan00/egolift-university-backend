DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS user_account;

CREATE TABLE exercise
(
    id            VARCHAR(36) NOT NULL,
    exercise_name VARCHAR(50) NOT NULL,
    category      VARCHAR(50) NOT NULL,
    popularity    DECIMAL(10, 2) DEFAULT NULL,
    likability    DECIMAL(10, 2) DEFAULT NULL,
    difficulty    INTEGER        DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_account
(
    id          VARCHAR(36) NOT NULL,
    email       VARCHAR(50) NOT NULL,
    password    VARCHAR(100) NOT NULL,
    first_name  VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    phone       VARCHAR(15)    DEFAULT NULL,
    address     VARCHAR(50)    DEFAULT NULL,
    city        VARCHAR(50)    DEFAULT NULL,
    country     VARCHAR(50)    DEFAULT NULL,
    birth_date  DATE           DEFAULT NULL,
    height      DECIMAL(10, 2) DEFAULT NULL,
    weight      DECIMAL(10, 2) DEFAULT NULL,
    exercise_id VARCHAR(36)    DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_exercise FOREIGN KEY (exercise_id) REFERENCES exercise (id)
);
