CREATE TABLE customers (
  customer_id   BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(50)        NOT NULL,
  balance DECIMAL(10,2) NOT NULL
);