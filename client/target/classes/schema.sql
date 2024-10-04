CREATE TABLE IF NOT EXISTS clients (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  paternal_surname VARCHAR(255) NOT NULL,
  maternal_surname VARCHAR(255) NOT NULL,
  email VARCHAR(255) UNIQUE,
  phone CHAR(10),
  birthdate DATE,
  gender ENUM('MALE', 'FEMALE', 'OTHER') DEFAULT 'OTHER',
  CONSTRAINT chk_phone_length CHECK (LENGTH(phone) = 10)
);
