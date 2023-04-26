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


