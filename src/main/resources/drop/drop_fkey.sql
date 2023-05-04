ALTER TABLE IF EXISTS Rent
    DROP CONSTRAINT IF EXISTS rent_car_id_fkey;
ALTER TABLE IF EXISTS Rent
    DROP CONSTRAINT IF EXISTS rent_customer_id_fkey;
ALTER TABLE IF EXISTS Reservation
    DROP CONSTRAINT IF EXISTS reservation_car_id_fkey;
ALTER TABLE IF EXISTS Reservation
    DROP CONSTRAINT IF EXISTS reservation_customer_id_fkey;
