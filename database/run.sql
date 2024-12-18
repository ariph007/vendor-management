CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE roles
(
    id         text        NOT NULL DEFAULT uuid_generate_v4(),
    name       VARCHAR(50) NOT NULL,
    code       VARCHAR(12) NOT NULL,
    limit      INT         NOT NULL,
    created_by text        NOT NULL DEFAULT 'SYSTEM'::text,
    created_at timestamp   NOT NULL DEFAULT now(),
    updated_by text        NOT NULL DEFAULT 'SYSTEM'::text,
    updated_at timestamp   NOT NULL DEFAULT now(),
    "version"  int8        NOT NULL DEFAULT 0,
    deleted_at timestamptz NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX roles_un
    ON roles (code) WHERE deleted_at IS NULL;

-- Create Users
CREATE TABLE users
(
    id         text        NOT NULL DEFAULT uuid_generate_v4(),
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50),
    role_id    TEXT        NOT NULL,
    email      text        NOT NULL,
    username   VARCHAR(50),
    "password" text        NOT NULL,
    created_by text        NOT NULL DEFAULT 'SYSTEM'::text,
    created_at timestamp   NOT NULL DEFAULT now(),
    updated_by text        NOT NULL DEFAULT 'SYSTEM'::text,
    updated_at timestamp   NOT NULL DEFAULT now(),
    "version"  int8        NOT NULL DEFAULT 0,
    deleted_at timestamptz NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_fk_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);
CREATE UNIQUE INDEX users_un
    ON users (email, username) WHERE deleted_at IS NULL;


-- Create Banks
CREATE TABLE banks
(
    id            text        NOT NULL DEFAULT uuid_generate_v4(),
    name          VARCHAR(50) NOT NULL,
    code          VARCHAR(12) NOT NULL,
    transfer_code VARCHAR(3)  NOT NULL,
    description   text,
    created_by    text        NOT NULL DEFAULT 'SYSTEM'::text,
    created_at    timestamp   NOT NULL DEFAULT now(),
    updated_by    text        NOT NULL DEFAULT 'SYSTEM'::text,
    updated_at    timestamp   NOT NULL DEFAULT now(),
    "version"     int8        NOT NULL DEFAULT 0,
    deleted_at    timestamptz NULL,
    CONSTRAINT banks_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX banks_code_un
    ON banks (code, transfer_code) WHERE deleted_at IS NULL;


-- Create Categories
CREATE TABLE categories
(
    id          text         NOT NULL DEFAULT uuid_generate_v4(),
    name        VARCHAR(100) NOT NULL,
    code        VARCHAR(12)  NOT NULL,
    description text,
    created_by  text         NOT NULL DEFAULT 'SYSTEM'::text,
    created_at  timestamp    NOT NULL DEFAULT now(),
    updated_by  text         NOT NULL DEFAULT 'SYSTEM'::text,
    updated_at  timestamp    NOT NULL DEFAULT now(),
    "version"   int8         NOT NULL DEFAULT 0,
    deleted_at  timestamptz NULL,
    CONSTRAINT categories_pkey PRIMARY KEY (id)
);
CREATE UNIQUE INDEX categories_un
    ON categories (code) WHERE deleted_at IS NULL;


-- Create Vendors
CREATE TABLE vendors
(
    id          text         NOT NULL DEFAULT uuid_generate_v4(),
    category_id TEXT         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    code        VARCHAR(12)  NOT NULL,
    description text,
    address     TEXT,
    email       TEXT         NOT NULL,
    created_by  text         NOT NULL DEFAULT 'SYSTEM'::text,
    created_at  timestamp    NOT NULL DEFAULT now(),
    updated_by  text         NOT NULL DEFAULT 'SYSTEM'::text,
    updated_at  timestamp    NOT NULL DEFAULT now(),
    "version"   int8         NOT NULL DEFAULT 0,
    deleted_at  timestamptz NULL,
    CONSTRAINT vendors_pkey PRIMARY KEY (id),
    CONSTRAINT vendors_fk_categories FOREIGN KEY (category_id) REFERENCES categories (id)
);
CREATE UNIQUE INDEX vendors_un
    ON vendors (code, email) WHERE deleted_at IS NULL;


-- Create Vendor Banks
CREATE TABLE vendor_banks
(
    id           text         NOT NULL DEFAULT uuid_generate_v4(),
    bank_id      TEXT         NOT NULL,
    vendor_id    TEXT         NOT NULL,
    account_no   VARCHAR(40)  NOT NULL,
    account_name VARCHAR(100) NOT NULL,
    created_by   text         NOT NULL DEFAULT 'SYSTEM'::text,
    created_at   timestamp    NOT NULL DEFAULT now(),
    updated_by   text         NOT NULL DEFAULT 'SYSTEM'::text,
    updated_at   timestamp    NOT NULL DEFAULT now(),
    "version"    int8         NOT NULL DEFAULT 0,
    deleted_at   timestamptz NULL,
    CONSTRAINT vendor_banks_pkey PRIMARY KEY (id),
    CONSTRAINT vendor_banks_fk_vendors FOREIGN KEY (vendor_id) REFERENCES vendors (id),
    CONSTRAINT vendor_banks_fk_banks FOREIGN KEY (bank_id) REFERENCES banks (id)
);
CREATE UNIQUE INDEX vendor_banks_un
    ON vendor_banks (account_no, bank_id, vendor_id) WHERE deleted_at IS NULL;



--- INSERT Default Data
-- Roles
INSERT INTO roles
(id, "name", code, "limit", created_by, created_at, updated_by, updated_at, "version", deleted_at)
VALUES ('24f28d0e-dcfc-4fe7-b731-c656cffe4d72', 'ADMIN', 'ADM', 10, 'SYSTEM',
        '2024-12-18 14:40:20.607', 'SYSTEM', '2024-12-18 14:40:20.607', 0, NULL);
INSERT INTO roles
(id, "name", code, "limit", created_by, created_at, updated_by, updated_at, "version", deleted_at)
VALUES ('302794ab-b3d8-4606-8d87-5d924c40d95a', 'USER', 'USR', 5, 'SYSTEM',
        '2024-12-18 14:40:37.859', 'SYSTEM', '2024-12-18 14:40:37.859', 0, NULL);

-- Users
--Admin
INSERT INTO users
(id, first_name, last_name, role_id, email, username, "password", created_by, created_at,
 updated_by, updated_at, "version", deleted_at)
VALUES ('28537371-2672-4280-b344-3249da32f443', 'eko', 'satrio',
        '24f28d0e-dcfc-4fe7-b731-c656cffe4d72', 'eko@google.com', 'satrio123',
        '$2a$10$6ZKsf8n1A3fme8k.UGa35eUb47EZtXfjSjukvR70EdA5SY.JRXq56', '0:0:0:0:0:0:0:1',
        '2024-12-18 07:42:14.614', '0:0:0:0:0:0:0:1', '2024-12-18 07:42:14.614', 0, NULL);

--User
INSERT INTO users
(id, first_name, last_name, role_id, email, username, "password", created_by, created_at,
 updated_by, updated_at, "version", deleted_at)
VALUES ('03ec2166-1d69-4c49-91a3-9f8f8db3df4a', 'yudhi', 'satrio',
        '302794ab-b3d8-4606-8d87-5d924c40d95a', 'yudhi@google.com', 'yudhi123',
        '$2a$10$dc9PHx60NwIRqGpkLrI59e9YgTSpmDwCl3kFePFjIIzSsaAUdY6Qi', '0:0:0:0:0:0:0:1',
        '2024-12-18 07:47:15.155', '0:0:0:0:0:0:0:1', '2024-12-18 07:47:15.155', 0, NULL);

