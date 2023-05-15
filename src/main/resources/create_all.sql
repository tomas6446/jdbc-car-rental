-- Create tables

CREATE TABLE IF NOT EXISTS toko7940.Customer
(
    customer_id SERIAL PRIMARY KEY,
    name        VARCHAR(50) DEFAULT 'Unknown',
    email       VARCHAR(50) DEFAULT 'Unknown',
    phone       VARCHAR(50) DEFAULT 'Unknown'
        CONSTRAINT valid_phone
            CHECK (phone SIMILAR TO '(86|\+3706)\d{3}\d{4}' )
);

CREATE TABLE IF NOT EXISTS toko7940.Car
(
    car_id       SERIAL PRIMARY KEY,
    manufacturer VARCHAR(50)    DEFAULT 'Unknown',
    model        VARCHAR(50)    DEFAULT 'Unknown',
    year         INT,
    CONSTRAINT valid_year CHECK ( year >= 1899),
    daily_rate   DECIMAL(10, 2) DEFAULT 50.00
);

CREATE TABLE IF NOT EXISTS toko7940.Rent
(
    rent_id     SERIAL PRIMARY KEY,
    car_id      INT NOT NULL,
    customer_id INT NOT NULL,
    rent_date   DATE,
    return_date DATE,
    CONSTRAINT valid_date CHECK ( rent_date <= return_date ),

    amount_paid DECIMAL(10, 2),

    CONSTRAINT rent_car_id_fkey
        FOREIGN KEY (car_id)
            REFERENCES toko7940.Car (car_id) ON DELETE CASCADE,
    CONSTRAINT rent_customer_id_fkey
        FOREIGN KEY (customer_id)
            REFERENCES toko7940.Customer (customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS toko7940.Reservation
(
    reservation_id   SERIAL PRIMARY KEY,
    car_id           INT NOT NULL,
    customer_id      INT NOT NULL,
    reservation_date DATE,
    expiration_date  DATE,
    CONSTRAINT valid_date CHECK ( reservation_date <= expiration_date ),

    CONSTRAINT reservation_car_id_fkey
        FOREIGN KEY (car_id)
            REFERENCES toko7940.Car (car_id) ON DELETE CASCADE,
    CONSTRAINT reservation_customer_id_fkey
        FOREIGN KEY (customer_id)
            REFERENCES toko7940.Customer (customer_id) ON DELETE CASCADE
);

-- Create indexes
CREATE UNIQUE INDEX IF NOT EXISTS toko7940_idx_unique_email ON toko7940.Customer (email);
CREATE INDEX IF NOT EXISTS toko7940_idx_rent_date ON toko7940.Rent (rent_date);

-- Create views
CREATE OR REPLACE VIEW toko7940.customer_rentals AS
SELECT toko7940.Customer.name,
       toko7940.Customer.email,
       toko7940.Rent.rent_date,
       toko7940.Rent.return_date
FROM toko7940.Customer
         JOIN toko7940.Rent ON toko7940.Customer.customer_id = toko7940.Rent.customer_id;

CREATE OR REPLACE VIEW toko7940.car_reservations AS
SELECT toko7940.Car.manufacturer,
       toko7940.Car.model,
       toko7940.Customer.name,
       toko7940.Reservation.reservation_date,
       toko7940.Reservation.expiration_date
FROM toko7940.Car
         JOIN toko7940.Reservation ON toko7940.Car.car_id = toko7940.Reservation.car_id
         JOIN toko7940.Customer ON toko7940.Customer.customer_id = toko7940.Reservation.customer_id;

CREATE OR REPLACE VIEW toko7940.car_rentals AS
SELECT toko7940.Car.manufacturer,
       toko7940.Car.model,
       toko7940.Customer.name,
       toko7940.Rent.rent_date,
       toko7940.Rent.return_date
FROM toko7940.Car
         JOIN toko7940.Rent ON toko7940.Car.car_id = toko7940.Rent.car_id
         JOIN toko7940.Customer ON toko7940.Customer.customer_id = toko7940.Rent.customer_id;

CREATE MATERIALIZED VIEW toko7940.car_rental_stats AS
SELECT toko7940.Car.manufacturer,
       toko7940.Car.model,
       COUNT(toko7940.Rent.car_id) AS total_rentals
FROM toko7940.Car
         JOIN toko7940.Rent ON toko7940.Car.car_id = toko7940.Rent.car_id
GROUP BY toko7940.Car.manufacturer, toko7940.Car.model;
--REFRESH MATERIALIZED VIEW toko7940.car_rental_stats;

-- Create triggers
CREATE OR REPLACE FUNCTION toko7940_update_amount_paid()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.amount_paid = (SELECT daily_rate
                       FROM toko7940.Car
                       WHERE car_id = NEW.car_id) * (NEW.return_date - NEW.rent_date);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER toko7940_trg_update_amount_paid
    BEFORE INSERT OR UPDATE
    ON toko7940.Rent
    FOR EACH ROW
EXECUTE FUNCTION toko7940_update_amount_paid();

CREATE OR REPLACE FUNCTION toko7940_prevent_renting_reserved_car()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS(
            SELECT 1
            FROM toko7940.Reservation
            WHERE car_id = NEW.car_id
              AND reservation_date <= NEW.rent_date
              AND expiration_date > NEW.rent_date
        ) THEN
        RAISE EXCEPTION 'Cannot create or update rent as there is an active reservation of this car';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER toko7940_trg_prevent_renting_reserved_car
    BEFORE INSERT OR UPDATE
    ON toko7940.Rent
    FOR EACH ROW
EXECUTE FUNCTION toko7940_prevent_renting_reserved_car();

-- Insert data
INSERT INTO toko7940.Customer (name, email, phone)
VALUES ('Tomas Kozakas', 'tomaskozakas@gmail.com', '+37067217198'),
       ('Oskar Krasev', 'oskarkrasev@gmail.com', '+37067217198'),
       ('Jane Smith', 'janesmith@gmail.com', '+37067217198'),
       ('Bob Johnson', 'bobjohnson@yahoo.com', '+37067217198'),
       ('Emily Davis', 'emilydavis@hotmail.com', '+37067217198');

INSERT INTO toko7940.Car (manufacturer, model, year, daily_rate)
VALUES ('Audi', 'A4', 2022, 75.00),
       ('BMW', 'X5', 2023, 100.00),
       ('Volkswagen', 'Golf', 2021, 55.00);


INSERT INTO toko7940.Rent (car_id, customer_id, rent_date, return_date, amount_paid)
VALUES (1, 1, '2023-04-10', '2023-04-14', 260.00),
       (2, 3, '2023-04-12', '2023-04-16', 240.00),
       (3, 2, '2023-04-13', '2023-04-16', 255.00);

INSERT INTO toko7940.Reservation (car_id, customer_id, reservation_date, expiration_date)
VALUES (1, 4, '2023-04-18', '2023-04-25'),
       (2, 2, '2023-04-15', '2023-04-20'),
       (3, 3, '2023-04-17', '2023-04-20');


