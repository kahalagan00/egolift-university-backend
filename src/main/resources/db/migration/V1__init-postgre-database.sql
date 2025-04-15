DROP TABLE IF EXISTS exercise;
DROP TABLE IF EXISTS user_account;

CREATE TABLE exercise (
    id            VARCHAR(36) NOT NULL,
    exercise_name VARCHAR(50) NOT NULL,
    category      VARCHAR(50) NOT NULL,
    popularity    DECIMAL(10, 2) DEFAULT NULL,
    likability    DECIMAL(10, 2) DEFAULT NULL,
    difficulty    INTEGER        DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_account (
    id          VARCHAR(36)    NOT NULL,
    first_name  VARCHAR(50)    NOT NULL,
    last_name   VARCHAR(50)    NOT NULL,
    phone       VARCHAR(15)  DEFAULT NULL,
    address     VARCHAR(255) DEFAULT NULL,
    city        VARCHAR(255) DEFAULT NULL,
    country     VARCHAR(255) DEFAULT NULL,
    birth_date  DATE           NOT NULL,
    height      DECIMAL(10, 2) NOT NULL,
    weight      DECIMAL(10, 2) NOT NULL,
    exercise_id VARCHAR(36)    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_exercise FOREIGN KEY (exercise_id) REFERENCES exercise (id)
);
