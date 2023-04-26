-- Unique index for email for every customer
CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_email ON Customer (email);

-- Car rental using rental date
CREATE INDEX IF NOT EXISTS idx_rent_date ON Rent (rent_date);