-- Drop triggers
DROP TRIGGER IF EXISTS trg_prevent_renting_reserved_car ON Rent;
DROP TRIGGER IF EXISTS trg_update_amount_paid ON Rent;

-- Drop functions
DROP FUNCTION IF EXISTS prevent_renting_reserved_car();
DROP FUNCTION IF EXISTS update_amount_paid();

-- Remove foreign keys
ALTER TABLE IF EXISTS Rent
    DROP CONSTRAINT IF EXISTS rent_car_id_fkey;
ALTER TABLE IF EXISTS Rent
    DROP CONSTRAINT IF EXISTS rent_customer_id_fkey;
ALTER TABLE IF EXISTS Reservation
    DROP CONSTRAINT IF EXISTS reservation_car_id_fkey;
ALTER TABLE IF EXISTS Reservation
    DROP CONSTRAINT IF EXISTS reservation_customer_id_fkey;

-- Drop views
DROP MATERIALIZED VIEW IF EXISTS car_rental_stats;
DROP VIEW IF EXISTS car_reservations;
DROP VIEW IF EXISTS car_rentals;
DROP VIEW IF EXISTS customer_rentals;

-- Drop indexes
DROP INDEX IF EXISTS idx_unique_email;
DROP INDEX IF EXISTS idx_rent_date;

-- Drop tables
DROP TABLE IF EXISTS Rent;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Car;
