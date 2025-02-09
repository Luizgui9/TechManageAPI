CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    birth_date DATE,
    user_type VARCHAR(50)
);

ALTER TABLE users ALTER COLUMN user_id RESTART WITH 4;