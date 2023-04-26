DROP TRIGGER IF EXISTS trg_update_amount_paid ON rent;

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
