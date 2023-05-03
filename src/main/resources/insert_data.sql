INSERT INTO Customer (name, email, phone)
VALUES ('Tomas Kozakas', 'tomas.kozakas@gmail.com', '13333222'),
       ('Oskar Krasev', 'oskar.krasev@gmail.com', '53312222'),
       ('Jane Smith', 'janesmith@gmail.com', '73337222'),
       ('Bob Johnson', 'bobjohnson@yahoo.com', '83386222'),
       ('Emily Davis', 'emilydavis@hotmail.com', '93367222');

INSERT INTO Car (manufacturer, model, year, daily_rate)
VALUES ('Audi', 'A4', 2022, 75.00),
       ('BMW', 'X5', 2023, 100.00),
       ('Volkswagen', 'Golf', 2021, 55.00);


INSERT INTO Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-10', '2023-04-14', 260.00),
       (2, 3, '2023-04-12', '2023-04-16', 240.00),
       (3, 2, '2023-04-13', '2023-04-16', 255.00);

INSERT INTO Reservation (car_id, customer_id, reservation_date, expiration_date)
VALUES (1, 4, '2023-04-18', '2023-04-25'),
       (2, 2, '2023-04-15', '2023-04-20'),
       (3, 3, '2023-04-17', '2023-04-20');