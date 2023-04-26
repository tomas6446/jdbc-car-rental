CREATE TABLE IF NOT EXISTS Customer
(
    customer_id SERIAL PRIMARY KEY,
    name        VARCHAR(50) DEFAULT 'Unknown',
    email       VARCHAR(50) DEFAULT 'Unknown',
    phone       VARCHAR(50) DEFAULT 'Unknown'
);

CREATE TABLE IF NOT EXISTS Rent
(
    rent_id     serial PRIMARY KEY,
    car_id      INT NOT NULL,
    customer_id INT NOT NULL,
    rent_date   DATE,
    return_date DATE,
    amount_paid DECIMAL(10, 2)
);

CREATE TABLE IF NOT EXISTS Reservation
(
    reservation_id   serial PRIMARY KEY,
    car_id           INT NOT NULL,
    customer_id      INT NOT NULL,
    reservation_date DATE,
    expiration_date  DATE
);

CREATE TABLE IF NOT EXISTS Car
(
    car_id       serial PRIMARY KEY,
    manufacturer VARCHAR(50)    DEFAULT 'Unknown',
    model        VARCHAR(50)    DEFAULT 'Unknown',
    year         INT,
    daily_rate   DECIMAL(10, 2) DEFAULT 50.00
);

-- Unique index for email for every customer
CREATE UNIQUE INDEX idx_unique_email ON Customer (email);

-- Car rental using rental date
CREATE INDEX idx_rent_date ON Rent (rent_date);

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

-- Trigger function to update the amount_paid
CREATE OR REPLACE FUNCTION update_amount_paid()
    RETURNS TRIGGER AS
$$
DECLARE
    car_daily_rate DECIMAL;
BEGIN
    -- Get the daily_rate of the rented car
    SELECT daily_rate
    INTO car_daily_rate
    FROM Car
    WHERE Car.car_id = NEW.car_id;

    -- Update the amount_paid based on the daily_rate and the rental duration (return_date - rent_date)
    NEW.amount_paid := car_daily_rate * (NEW.return_date - NEW.rent_date);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger
CREATE TRIGGER trg_update_amount_paid
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION update_amount_paid();


