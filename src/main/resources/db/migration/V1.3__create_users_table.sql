CREATE Table if not EXISTS users (
    id serial4 PRIMARY KEY,
    user_id uuid not null default gen_random_uuid () UNIQUE,
    email TEXT NOT NULL UNIQUE,
    name VARCHAR(250) not NULL,
    password_hash TEXT not NULL,
    role_id int4 not null,
    created_at TIMESTAMPtz not null,
    created_by VARCHAR(250),
    created_by_id UUID,
    last_updated_at TIMESTAMPtz,
    last_updated_by VARCHAR(250),
    last_updated_by_id UUID,
    Foreign Key (role_id) REFERENCES roles (id)
);