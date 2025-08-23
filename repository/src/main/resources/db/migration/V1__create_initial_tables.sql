CREATE TABLE addresses
(
    id            BIGSERIAL PRIMARY KEY,
    is_active     BOOLEAN NOT NULL DEFAULT true,
    is_enabled    BOOLEAN NOT NULL DEFAULT true,
    creation_date TIMESTAMP,
    updated_date  TIMESTAMP,
    created_by    BIGINT,
    updated_by    BIGINT,
    city          VARCHAR(255),
    country       VARCHAR(255),
    country_code  VARCHAR(255),
    house_number  VARCHAR(255),
    street        VARCHAR(255),
    postal_code   VARCHAR(255),
    state         VARCHAR(255)
);

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    is_active     BOOLEAN      NOT NULL DEFAULT true,
    is_enabled    BOOLEAN      NOT NULL DEFAULT true,
    creation_date TIMESTAMP,
    updated_date  TIMESTAMP,
    created_by    BIGINT,
    updated_by    BIGINT,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    phone_number  VARCHAR(15)  NOT NULL UNIQUE,
    password      VARCHAR(510) NOT NULL,
    address_id    BIGINT,
    CONSTRAINT fk_address_id FOREIGN KEY (address_id) REFERENCES addresses (id)
);


INSERT INTO addresses (is_active, is_enabled, creation_date, updated_date, created_by, updated_by, city, country,
                       country_code, house_number, street, postal_code, state)
VALUES (true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, -1, -1, 'Riyadh', 'Saudi Arabia', 'SA', '000', 'Street',
        '12345', 'Saudi Arabia');

INSERT INTO users (is_active, is_enabled, creation_date, updated_date, created_by, updated_by, first_name,
                       last_name, email, phone_number, password, address_id)
VALUES (true, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, -1, -1, 'waraq', 'Flow', 'merchant@waraq.io',
        '0000000000', '$2a$12$nVh19eK47q3bvZPykXh2xOOt1tvOrQ2xdNxJOLgh2ccBXB5Jald6K', 1);

