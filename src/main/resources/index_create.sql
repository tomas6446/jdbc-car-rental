-- Unique index for email for every customer
CREATE UNIQUE INDEX idx_unique_email ON Customer (email);

-- Car rental using rental date
CREATE INDEX idx_rent_date ON Rent (rent_date);