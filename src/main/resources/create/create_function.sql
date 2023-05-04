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


CREATE OR REPLACE FUNCTION prevent_renting_reserved_car()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS(
            SELECT 1
            FROM Reservation
            WHERE car_id = NEW.car_id
              AND reservation_date <= NEW.rent_date
              AND expiration_date > NEW.rent_date
        ) THEN
        RAISE EXCEPTION 'Cannot create or update rent as there is an active reservation of this car';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


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