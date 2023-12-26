CREATE TABLE country
(
    id        UUID NOT NULL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    code      VARCHAR(10) NOT NULL CONSTRAINT country_code UNIQUE
);

CREATE TABLE address
(
    id            UUID NOT NULL PRIMARY KEY,
    street_name   VARCHAR(100) NOT NULL,
    street_number VARCHAR(50) NOT NULL,
    city          VARCHAR(100) NOT NULL,
    zip_code      INTEGER NOT NULL,
    country_id    UUID NOT NULL CONSTRAINT fk_address_country_id__id REFERENCES
        country
        ON UPDATE
            RESTRICT ON DELETE RESTRICT
);

CREATE TABLE app_user
(
    id         UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    address_id UUID NOT NULL CONSTRAINT fk_app_user_address_id__id REFERENCES
        address ON UPDATE
        RESTRICT ON DELETE RESTRICT
);