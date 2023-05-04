CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_email ON Customer (email); -- unikalus email
CREATE INDEX IF NOT EXISTS idx_rent_date ON Rent (rent_date);
