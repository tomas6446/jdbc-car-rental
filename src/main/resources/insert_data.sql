INSERT INTO Customer (name, email, phone)
VALUES ('John Doe', 'johndoe@gmail.com', '555-1234'),
       ('Jane Smith', 'janesmith@gmail.com', '555-5678'),
       ('Bob Johnson', 'bobjohnson@yahoo.com', '555-9101'),
       ('Emily Davis', 'emilydavis@hotmail.com', '555-1212');

INSERT INTO Car (manufacturer, model, year, daily_rate)
VALUES ('Toyota', 'Camry', 2021, 65.00),
       ('Honda', 'Accord', 2020, 60.00),
       ('Ford', 'Mustang', 2019, 85.00),
       ('Chevrolet', 'Corvette', 2021, 150.00);

INSERT INTO Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-10', '2023-04-14', 260.00),
       (2, 3, '2023-04-12', '2023-04-16', 240.00),
       (3, 2, '2023-04-13', '2023-04-16', 255.00);

INSERT INTO Reservation (car_id, customer_id, reservation_date, expiration_date)
VALUES (1, 4, '2023-04-18', '2023-04-25'),
       (2, 2, '2023-04-15', '2023-04-20'),
       (3, 3, '2023-04-17', '2023-04-20');