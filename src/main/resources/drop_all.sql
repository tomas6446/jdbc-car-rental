-- Drop triggers
DROP TRIGGER IF EXISTS toko7940_trg_prevent_renting_reserved_car ON toko7940.Rent;
DROP TRIGGER IF EXISTS toko7940_trg_update_amount_paid ON toko7940.Rent;


-- Drop functions
DROP FUNCTION IF EXISTS toko7940_prevent_renting_reserved_car();
DROP FUNCTION IF EXISTS toko7940_update_amount_paid();

-- Remove foreign keys
ALTER TABLE IF EXISTS toko7940.Rent
    DROP CONSTRAINT IF EXISTS rent_car_id_fkey;
ALTER TABLE IF EXISTS toko7940.Rent
    DROP CONSTRAINT IF EXISTS rent_customer_id_fkey;
ALTER TABLE IF EXISTS toko7940.Reservation
    DROP CONSTRAINT IF EXISTS reservation_car_id_fkey;
ALTER TABLE IF EXISTS toko7940.Reservation
    DROP CONSTRAINT IF EXISTS reservation_customer_id_fkey;

-- Drop views
DROP MATERIALIZED VIEW IF EXISTS toko7940.car_rental_stats;
DROP VIEW IF EXISTS toko7940.car_reservations;
DROP VIEW IF EXISTS toko7940.car_rentals;
DROP VIEW IF EXISTS toko7940.customer_rentals;

-- Drop indexes
DROP INDEX IF EXISTS toko7940_idx_unique_email;
DROP INDEX IF EXISTS toko7940_idx_rent_date;

-- Drop tables
DROP TABLE IF EXISTS toko7940.Rent;
DROP TABLE IF EXISTS toko7940.Reservation;
DROP TABLE IF EXISTS toko7940.Customer;
DROP TABLE IF EXISTS toko7940.Car;

DROP SCHEMA toko7940;
