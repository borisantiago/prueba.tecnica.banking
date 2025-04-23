CREATE TABLE customer (
customer_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
name VARCHAR(100),
gender VARCHAR(10),
age INT,
identification VARCHAR(20),
address VARCHAR(255),
phone VARCHAR(20),
password VARCHAR(100),
status BOOLEAN
);

CREATE TABLE account (
account_number BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
account_type VARCHAR(50),
balance DOUBLE PRECISION,
account_status BOOLEAN,
customer_id BIGINT,
CONSTRAINT fk_account_customer FOREIGN KEY (customer_id)
REFERENCES customer(customer_id)
ON DELETE CASCADE
);

CREATE TABLE movements (
id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
date DATE,
movement_type VARCHAR(50),
amount DOUBLE PRECISION,
balance DOUBLE PRECISION,
account_number BIGINT,
CONSTRAINT fk_movements_account FOREIGN KEY (account_number)
REFERENCES account(account_number)
ON DELETE CASCADE
);
