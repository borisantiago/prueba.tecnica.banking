-- Insertar clientes
INSERT INTO customer (name, gender, age, identification, address, phone, password, status)
VALUES
('Jose Lema', 'M', 35, '110001', 'Otavalo sn y principal', '098254785', '1234', true),
('Marianela Montalvo', 'F', 29, '110002', 'Amazonas y NNUU', '097548965', '5678', true),
('Juan Osorio', 'M', 40, '110003', '13 junio y Equinoccial', '098874587', '1245', true);

-- Insertar cuentas (suponiendo que Jose = ID 1, Marianela = ID 2, Juan = ID 3)
INSERT INTO account (account_type, balance, account_status, customer_id)
VALUES
('Ahorro', 2000.0, true, 1),
('Corriente', 100.0, true, 2),
('Ahorro', 0.0, true, 3),
('Ahorro', 540.0, true, 2);

-- Insertar movimientos (basado en cuentas con IDs 2 y 4)
INSERT INTO movements (date, movement_type, amount, balance, account_number)
VALUES
('2022-02-10', 'Depósito', 600.0, 700.0, 2),
('2022-02-08', 'Retiro', -540.0, 0.0, 4);