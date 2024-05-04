CREATE TABLE fine (
    id SERIAL PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    violation VARCHAR(255),
    date VARCHAR(255),
    location VARCHAR(255),
    owner_id BIGINT,
    vehicle_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES owner(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);
