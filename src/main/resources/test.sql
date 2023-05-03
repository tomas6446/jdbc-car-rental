SELECT * FROM customer;
SELECT * FROM reservation;
SELECT * FROM rent;
SELECT * FROM car;

SELECT * FROM car_rental_stats;

INSERT INTO Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-14', '2023-04-15', 260.00);
REFRESH MATERIALIZED VIEW car_rental_stats;

SELECT * FROM car_reservations;
SELECT * FROM customer_rentals;
SELECT * FROM car_rental_stats;






