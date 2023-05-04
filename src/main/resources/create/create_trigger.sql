
CREATE TRIGGER trg_update_amount_paid
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION update_amount_paid();

CREATE TRIGGER trg_prevent_renting_reserved_car
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION prevent_renting_reserved_car();

CREATE TRIGGER trg_check_car_year
    BEFORE INSERT OR UPDATE
    ON Car
    FOR EACH ROW
EXECUTE FUNCTION check_car_year();

CREATE TRIGGER trg_check_reservation_dates
    BEFORE INSERT OR UPDATE
    ON Reservation
    FOR EACH ROW
EXECUTE FUNCTION check_reservation_dates();

-- Trigger to check if rent_date is less than return_date
CREATE TRIGGER trg_check_rent_dates
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION check_rent_dates();