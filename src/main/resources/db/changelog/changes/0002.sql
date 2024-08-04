CREATE TABLE owner (
         id SERIAL PRIMARY KEY,
         idnp VARCHAR(255) NOT NULL,
         first_name VARCHAR(255) NOT NULL,
         last_name VARCHAR(255) NOT NULL,
         birth_date VARCHAR(255) NOT NULL,
         address VARCHAR(255) NOT NULL,
         phone_number VARCHAR(255) NOT NULL
);
