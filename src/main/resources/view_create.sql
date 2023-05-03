DROP VIEW IF EXISTS car_reservations;
DROP VIEW IF EXISTS customer_rentals;
DROP VIEW IF EXISTS customer_car_rents;
DROP MATERIALIZED VIEW IF EXISTS car_rental_stats;

-- Car model and customer name along with reservation and expiration date.
CREATE VIEW car_reservations AS
SELECT Car.manufacturer,
       Car.model,
       Customer.name,
       Reservation.reservation_date,
       Reservation.expiration_date
FROM Car
         JOIN Reservation ON Car.car_id = Reservation.car_id
         JOIN Customer ON Customer.customer_id = Reservation.customer_id;

-- Customer names and car model, rental dates and return dates
CREATE VIEW customer_car_rents AS
SELECT Customer.name,
       Car.model,
       Rent.rent_date,
       Rent.return_date
FROM Customer
         JOIN Rent ON Customer.customer_id = Rent.car_id
         JOIN Car ON Rent.car_id = Car.car_id;


-- Displays the number of rentals for each car manufacturer and model.
CREATE MATERIALIZED VIEW car_rental_stats AS
SELECT Car.manufacturer,
       Car.model,
       COUNT(Rent.car_id) AS total_rentals
FROM Car
         JOIN Rent ON Car.car_id = Rent.car_id
GROUP BY Car.manufacturer, Car.model;

-- Update
REFRESH MATERIALIZED VIEW car_rental_stats;
