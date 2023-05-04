-- Tables
SELECT *
FROM customer;
SELECT *
FROM reservation;
SELECT *
FROM rent;
SELECT *
FROM car;

-- Test unique index by trying to insert an existing email
INSERT INTO Customer (name, email, phone)
VALUES ('Tomas Kozakas', 'tomas.kozakas@gmail.com', '13333222');
-- Test index by sorting rents by rent_date in desc order
SELECT *
FROM Rent
ORDER BY rent_date DESC;

-- Test view
SELECT *
FROM customer_rentals;
SELECT *
FROM car_reservations;
SELECT *
FROM car_rentals;

-- Test materialized view
SELECT *
FROM car_rental_stats;

INSERT INTO Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-10', '2023-04-14', 260.00);

REFRESH MATERIALIZED VIEW car_rental_stats;

SELECT *
FROM car_rental_stats;

-- Test triggers
-- Bandom nuomoti masina kuri jau yra rezervuota
INSERT INTO Reservation (car_id, customer_id, reservation_date, expiration_date)
VALUES (1, 4, '2023-05-04', '2023-05-30');
SELECT *
FROM reservation;
INSERT INTO Rent (car_id, customer_id, rent_date, return_date)
VALUES (1, 5, '2023-05-04', '2023-05-30');

-- Bandom insertint masina kuri yra 1799 metu
INSERT INTO Car (manufacturer, model, year, daily_rate)
VALUES ('Car', 'car', 1799, 0);

-- Bandom inserting Rent ar Reservation kuriu datos nesutampa
INSERT INTO Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-14', '2023-04-10', 260.00);
INSERT INTO Reservation (car_id, customer_id, reservation_date, expiration_date)
VALUES (1, 4, '2023-04-14', '2023-04-10');


