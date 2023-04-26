DROP VIEW IF EXISTS car_reservations;
DROP VIEW IF EXISTS customer_rentals;
DROP MATERIALIZED VIEW IF EXISTS car_rental_stats;

-- Customer names and email addresses along with their car rental dates and return dates
CREATE VIEW customer_rentals AS
SELECT Customer.name  AS customer_name,
       Customer.email AS customer_email,
       Rent.rent_date,
       Rent.return_date
FROM Customer
         JOIN Rent ON Customer.customer_id = Rent.customer_id;

-- Car model and customer name along with reservation and expiration date.
CREATE VIEW car_reservations AS
SELECT Car.manufacturer AS car_manufacturer,
       Car.model        AS car_model,
       Customer.name    AS customer_name,
       Reservation.reservation_date,
       Reservation.expiration_date
FROM Car
         JOIN Reservation ON Car.car_id = Reservation.car_id
         JOIN Customer ON Customer.customer_id = Reservation.customer_id;

-- Displays the number of rentals for each car manufacturer and model.
CREATE MATERIALIZED VIEW car_rental_stats AS
SELECT Car.manufacturer,
       Car.model,
       COUNT(Rent.rent_id) AS total_rentals
FROM Car
         JOIN Rent ON Car.car_id = Rent.car_id
GROUP BY Car.manufacturer, Car.model;

-- Update
REFRESH MATERIALIZED VIEW car_rental_stats;
