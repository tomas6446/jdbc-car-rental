CREATE OR REPLACE VIEW customer_rentals AS
SELECT Customer.name,
       Customer.email,
       Rent.rent_date,
       Rent.return_date
FROM Customer
         JOIN Rent ON Customer.customer_id = Rent.customer_id;

CREATE OR REPLACE VIEW car_reservations AS
SELECT Car.manufacturer,
       Car.model,
       Customer.name,
       Reservation.reservation_date,
       Reservation.expiration_date
FROM Car
         JOIN Reservation ON Car.car_id = Reservation.car_id
         JOIN Customer ON Customer.customer_id = Reservation.customer_id;

CREATE OR REPLACE VIEW car_rentals AS
SELECT Car.manufacturer,
       Car.model,
       Customer.name,
       Rent.rent_date,
       Rent.return_date
FROM Car
         JOIN Rent ON Car.car_id = Rent.car_id
         JOIN Customer ON Customer.customer_id = Rent.customer_id;

CREATE MATERIALIZED VIEW car_rental_stats AS
SELECT Car.manufacturer,
       Car.model,
       COUNT(Rent.car_id) AS total_rentals
FROM Car
         JOIN Rent ON Car.car_id = Rent.car_id
GROUP BY Car.manufacturer, Car.model;
REFRESH MATERIALIZED VIEW car_rental_stats;