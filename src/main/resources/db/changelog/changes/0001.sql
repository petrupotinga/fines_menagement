CREATE TABLE vehicle (
         id SERIAL PRIMARY KEY,
         vin VARCHAR(255) NOT NULL,
         licensePlate VARCHAR(255) NOT NULL,
         make VARCHAR(255) NOT NULL,
         model VARCHAR(255) NOT NULL,
         ownerId NUMERIC(20) NOT NULL,
         year NUMERIC(4) NOT NULL
);
