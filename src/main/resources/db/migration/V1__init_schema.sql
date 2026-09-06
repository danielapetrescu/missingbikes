CREATE TABLE brands (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE bikes (
                       id BIGSERIAL PRIMARY KEY,
                       brand_id BIGINT NOT NULL REFERENCES brands(id),
                       color VARCHAR(50) NOT NULL,
                       description VARCHAR(50),
                       date DATE NOT NULL,
                       latitude DOUBLE PRECISION NOT NULL,
                       longitude DOUBLE PRECISION NOT NULL,
                       address VARCHAR(255) NOT NULL
);

CREATE TABLE bike_photos (
                             id BIGSERIAL PRIMARY KEY,
                             bike_id BIGINT NOT NULL REFERENCES bikes(id) ON DELETE CASCADE,
                             path VARCHAR(255) NOT NULL
);