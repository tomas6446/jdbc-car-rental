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

    CONSTRAINT rent_car_id_fkey
        FOREIGN KEY (car_id)
            REFERENCES Car (car_id) ON DELETE CASCADE,
    CONSTRAINT rent_customer_id_fkey
        FOREIGN KEY (customer_id)
            REFERENCES Customer (customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Reservation
(
    reservation_id   SERIAL PRIMARY KEY,
    car_id           INT NOT NULL,
    customer_id      INT NOT NULL,
    reservation_date DATE,
    expiration_date  DATE,

    CONSTRAINT reservation_car_id_fkey
        FOREIGN KEY (car_id)
            REFERENCES Car (car_id) ON DELETE CASCADE,
    CONSTRAINT reservation_customer_id_fkey
        FOREIGN KEY (customer_id)
            REFERENCES Customer (customer_id) ON DELETE CASCADE
);