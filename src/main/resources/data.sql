-- Insertar clientes
INSERT INTO customer (identification, name, gender, age, address, phone, password, status)
VALUES
('0945321580', 'Jose Lema', 'M', 35, 'Otavalo sn y principal', '098254785', '1234', true),
('0854780365', 'Marianela Montalvo', 'F', 29, 'Amazonas y NNUU', '097548965', '5678', true),
('0659871023', 'Juan Osorio', 'M', 40, '13 junio y Equinoccial', '098874587', '1245', true);

-- Insertar cuentas (referencia por identificación)
INSERT INTO account (account_type, balance, account_status, identification)
VALUES
('Ahorro', 2000.0, true, '0945321580'), -- Cuenta 1
('Corriente', 100.0, true, '0854780365'), -- Cuenta 2
('Ahorro', 0.0, true, '0854780365'), -- Cuenta 3
('Ahorro', 540.0, true, '0659871023'); -- Cuenta 4

-- Insertar movimientos (referenciando account_number autogenerados)
INSERT INTO movements (date, movement_type, amount, balance, account_number)
VALUES
('2022-02-10', 'Depósito', 600.0, 700.0, 2),
('2022-02-12', 'Retiro', 100.0, 600.0, 2),
('2022-03-01', 'Depósito', 540.0, 1080.0, 4);
