DROP TRIGGER trg_update_amount_paid ON rent;

-- Trigger function to update the amount_paid
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

-- Trigger
CREATE TRIGGER trg_update_amount_paid
    BEFORE INSERT OR UPDATE
    ON Rent
    FOR EACH ROW
EXECUTE FUNCTION update_amount_paid();
