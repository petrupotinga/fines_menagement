CREATE TABLE vehicle (
         id SERIAL PRIMARY KEY,
         vin VARCHAR(255) NOT NULL,
         license_plate VARCHAR(255) NOT NULL,
         make VARCHAR(255) NOT NULL,
         model VARCHAR(255) NOT NULL,
         owner_id NUMERIC(20) NOT NULL,
         year NUMERIC(4) NOT NULL
);
