-- Create tables
CREATE TABLE IF NOT EXISTS Customer
(
    customer_id SERIAL PRIMARY KEY,
    name        VARCHAR(50) DEFAULT 'Unknown',
    email       VARCHAR(50) DEFAULT 'Unknown',
    phone       VARCHAR(50) DEFAULT 'Unknown'
);

CREATE TABLE IF NOT EXISTS Car
(
    car_id       SERIAL PRIMARY KEY,
    manufacturer VARCHAR(50)    DEFAULT 'Unknown',
    model        VARCHAR(50)    DEFAULT 'Unknown',
    year         INT,
    daily_rate   DECIMAL(10, 2) DEFAULT 50.00
);

CREATE TABLE IF NOT EXISTS Rent
(
    rent_id     SERIAL PRIMARY KEY,
    car_id      INT NOT NULL,
    customer_id INT NOT NULL,
    rent_date   DATE,
    return_date DATE,
    amount_paid DECIMAL(10, 2),
    FOREIGN KEY (car_id) REFERENCES Car (car_id),
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
);

CREATE TABLE IF NOT EXISTS Reservation
(
    reservation_id   SERIAL PRIMARY KEY,
    car_id           INT NOT NULL,
    customer_id      INT NOT NULL,
    reservation_date DATE,
    expiration_date  DATE,
    FOREIGN KEY (car_id) REFERENCES Car (car_id),
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id)
);

-- Create indexes
CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_email ON Customer (email);
CREATE INDEX IF NOT EXISTS idx_rent_date ON Rent (rent_date);

-- Create views
CREATE OR REPLACE VIEW customer_rentals AS
SELECT Customer.name  AS customer_name,
       Customer.email AS customer_email,
       Rent.rent_date,
       Rent.return_date
FROM Customer
         JOIN Rent ON Customer.customer_id = Rent.customer_id;

CREATE OR REPLACE VIEW car_reservations AS
SELECT Car.manufacturer AS car_manufacturer,
       Car.model        AS car_model,
       Customer.name    AS customer_name,
       Reservation.reservation_date,
       Reservation.expiration_date
FROM Car
         JOIN Reservation ON Car.car_id = Reservation.car_id
         JOIN Customer ON Customer.customer_id = Reservation.customer_id;

CREATE MATERIALIZED VIEW car_rental_stats AS
SELECT Car.manufacturer,
       Car.model,
       COUNT(Rent.car_id) AS total_rentals
FROM Car
         JOIN Rent ON Car.car_id = Rent.car_id
GROUP BY Car.manufacturer, Car.model;
REFRESH MATERIALIZED VIEW car_rental_stats;

-- Create triggers
CREATE OR REPLACE FUNCTION update_amount_paid()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.amount_paid = (SELECT daily_rate
                       FROM Car
                       WHERE car_id = NEW.car_id) * (NEW.return_date - NEW.rent_date);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_update_amount_paid
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION update_amount_paid();

-- Trigger to prevent renting a car if there's an active reservation
CREATE OR REPLACE FUNCTION prevent_renting_reserved_car()
    RETURNS TRIGGER AS
$$
DECLARE
    active_reservation_count INT;
BEGIN
    SELECT COUNT(*)
    INTO active_reservation_count
    FROM Reservation
    WHERE car_id = NEW.car_id
      AND reservation_date <= NEW.rent_date
      AND expiration_date > NEW.rent_date;

    IF active_reservation_count > 0 THEN
        RAISE EXCEPTION 'Cannot create or update rent as there is an active reservation of this car';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_prevent_renting_reserved_car
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION prevent_renting_reserved_car();

-- Trigger to check if the year value is greater than the first created car
CREATE OR REPLACE FUNCTION check_car_year()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.year <= 1886 THEN
        RAISE EXCEPTION 'Car year must be greater than 1886';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_check_car_year
    BEFORE INSERT OR UPDATE
    ON Car
    FOR EACH ROW
EXECUTE FUNCTION check_car_year();

-- Trigger to check if reservation_date is less than expiration_date
CREATE OR REPLACE FUNCTION check_reservation_dates()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.reservation_date >= NEW.expiration_date THEN
        RAISE EXCEPTION 'reservation_date must be less than expiration_date';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_check_reservation_dates
    BEFORE INSERT OR UPDATE
    ON Reservation
    FOR EACH ROW
EXECUTE FUNCTION check_reservation_dates();

-- Trigger to check if rent_date is less than return_date
CREATE OR REPLACE FUNCTION check_rent_dates()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.rent_date >= NEW.return_date THEN
        RAISE EXCEPTION 'rent_date must be less than return_date';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trg_check_rent_dates
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION check_rent_dates();

-- Insert data
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


