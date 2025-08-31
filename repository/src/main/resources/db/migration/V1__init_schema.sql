-- Flyway Migration: Initial schema for Waraq API
-- PostgreSQL dialect

-- 1) Addresses
CREATE TABLE IF NOT EXISTS addresses (
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(255),
    country VARCHAR(255),
    country_code VARCHAR(10),
    house_number VARCHAR(255),
    street VARCHAR(255),
    postal_code VARCHAR(255),
    state VARCHAR(255),
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT
);

-- 2) Media
CREATE TABLE IF NOT EXISTS media (
    id BIGSERIAL PRIMARY KEY,
    media_url TEXT,
    type VARCHAR(255),
    mime_type VARCHAR(255),
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT
);

-- 3) Users
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    address_id BIGINT,
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_users_address FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- 4) Admins (one-to-one with users)
CREATE TABLE IF NOT EXISTS admins (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE,
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_admins_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 5) Waraq Users (application user profile + role enum ordinal)
CREATE TABLE IF NOT EXISTS waraq_users (
    id BIGSERIAL PRIMARY KEY,
    image_id BIGINT,
    user_id BIGINT UNIQUE,
    role INTEGER,
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_waraq_users_image FOREIGN KEY (image_id) REFERENCES media(id),
    CONSTRAINT fk_waraq_users_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 6) Translate Requests
CREATE TABLE IF NOT EXISTS requests (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT,
    request_date TIMESTAMP,
    student_name VARCHAR(255),
    total_documents INTEGER,
    translator_id BIGINT,
    proofreader_id BIGINT,
    status INTEGER,
    delivery_date TIMESTAMP,
    mistakes_id BIGINT,
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_requests_client FOREIGN KEY (client_id) REFERENCES users(id),
    CONSTRAINT fk_requests_translator FOREIGN KEY (translator_id) REFERENCES users(id),
    CONSTRAINT fk_requests_proofreader FOREIGN KEY (proofreader_id) REFERENCES users(id),
    CONSTRAINT fk_requests_mistakes FOREIGN KEY (mistakes_id) REFERENCES media(id)
);

-- 7) Translate Documents
CREATE TABLE IF NOT EXISTS documents (
    id BIGSERIAL PRIMARY KEY,
    status INTEGER,
    request_id BIGINT,
    document_id BIGINT,
    template_Id BIGINT,
    translated_document_id BIGINT,
    final_translated_document_id BIGINT,
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_documents_request FOREIGN KEY (request_id) REFERENCES requests(id),
    CONSTRAINT fk_documents_document FOREIGN KEY (document_id) REFERENCES media(id),
    CONSTRAINT fk_documents_template FOREIGN KEY (template_Id) REFERENCES media(id),
    CONSTRAINT fk_documents_translated FOREIGN KEY (translated_document_id) REFERENCES media(id),
    CONSTRAINT fk_documents_final_translated FOREIGN KEY (final_translated_document_id) REFERENCES media(id)
);

-- 8) Translate Templates
CREATE TABLE IF NOT EXISTS templates (
    id BIGSERIAL PRIMARY KEY,
    document_id BIGINT,
    -- BaseEntity common fields
    creation_date TIMESTAMP,
    updated_date TIMESTAMP,
    is_active BOOLEAN,
    is_enabled BOOLEAN NOT NULL,
    created_by BIGINT,
    updated_by BIGINT,
    CONSTRAINT fk_templates_document FOREIGN KEY (document_id) REFERENCES media(id)
);

-- Seed: default admin user and records
-- Note: password is BCrypt hash for 'waraq123'

INSERT INTO users (user_name, first_name, last_name, email, phone_number, password, address_id,
                   creation_date, updated_date, is_active, is_enabled, created_by, updated_by)
VALUES ('admin', 'System', 'Administrator', 'admin@waraq.com', '+0000000000',
        '$2a$10$6vZ.ADNgiseBfgGOUGEdvOckTPA/GBTrGjGXbWfeEpIPqrZFDl9ZG',
        NULL, NOW(), NOW(), TRUE, TRUE, NULL, NULL)
ON CONFLICT (user_name) DO NOTHING;

-- Create an admin profile for that user
INSERT INTO admins (user_id, creation_date, updated_date, is_active, is_enabled, created_by, updated_by)
SELECT u.id, NOW(), NOW(), TRUE, TRUE, NULL, NULL
FROM users u
WHERE u.user_name = 'admin'
ON CONFLICT DO NOTHING;

-- Create a waraq_users entry with ADMIN role (ordinal of Role.ADMIN). Assuming enum order: CLIENT=0, TRANSLATOR=1, ADMIN=2
INSERT INTO waraq_users (image_id, user_id, role, creation_date, updated_date, is_active, is_enabled, created_by, updated_by)
SELECT NULL, u.id, 2, NOW(), NOW(), TRUE, TRUE, NULL, NULL
FROM users u
WHERE u.user_name = 'admin'
ON CONFLICT DO NOTHING;
