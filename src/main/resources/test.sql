-- Tables
SELECT *
FROM toko7940.customer;
SELECT *
FROM toko7940.reservation;
SELECT *
FROM toko7940.rent;
SELECT *
FROM toko7940.car;

-- Test unique index by trying to insert an existing email
INSERT INTO toko7940.Customer (name, email, phone)
VALUES ('Tomas Kozakas', 'tomas.kozakas@gmail.com', '13333222');
-- Test index by sorting rents by rent_date in desc order
SELECT *
FROM toko7940.Rent
ORDER BY rent_date DESC;

-- Test view
SELECT *
FROM toko7940.customer_rentals;
SELECT *
FROM toko7940.car_reservations;
SELECT *
FROM toko7940.car_rentals;

-- Test materialized view
SELECT *
FROM toko7940.car_rental_stats;

INSERT INTO toko7940.Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-10', '2023-04-14', 260.00);

REFRESH MATERIALIZED VIEW toko7940.car_rental_stats;

SELECT *
FROM toko7940.car_rental_stats;

-- Test triggers
-- Bandom nuomoti masina kuri jau yra rezervuota
INSERT INTO toko7940.Reservation (car_id, customer_id, reservation_date, expiration_date)
VALUES (1, 4, '2023-05-04', '2023-05-30');
SELECT *
FROM toko7940.reservation;
INSERT INTO toko7940.Rent (car_id, customer_id, rent_date, return_date)
VALUES (1, 5, '2023-05-04', '2023-05-30');

-- Bandom insertint masina kuri yra 1799 metu
INSERT INTO toko7940.Car (manufacturer, model, year, daily_rate)
VALUES ('Car', 'car', 1799, 0);

-- Bandom inserting Rent ar Reservation kuriu datos nesutampa
INSERT INTO toko7940.Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-14', '2023-04-10', 260.00);
INSERT INTO toko7940.Reservation (car_id, customer_id, reservation_date, expiration_date)
VALUES (1, 4, '2023-04-14', '2023-04-10');

-- Test kiek uzmuokejo
INSERT INTO toko7940.Rent (car_id, customer_id, rent_date, return_date)
VALUES (1, 5, '2023-03-10', '2023-04-25');
SELECT *
FROM toko7940.Rent;

-- Test non valid phone number
INSERT INTO toko7940.Customer (name, email, phone)
VALUES ('Tomas Kozakas', 'tomaskozakas@gmail.com', '3767217198');

SELECT toko7940.reservation.reservation_id,
       toko7940.car.model,
       toko7940.customer.email,

       toko7940.reservation.reservation_date,
       toko7940.reservation.expiration_date
FROM toko7940.reservation
         JOIN toko7940.car ON reservation.car_id = car.car_id
         JOIN toko7940.customer
              ON reservation.customer_id = customer.customer_id
WHERE customer.customer_id = 1