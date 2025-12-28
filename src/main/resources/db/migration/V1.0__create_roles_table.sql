create table if not EXISTS roles(
    id serial PRIMARY KEY,
    name VARCHAR(250) not NULL,
    description text,
     created_at TIMESTAMPtz not null,
    created_by VARCHAR(250) not null,
    created_by_id UUID not null,
    last_updated_at TIMESTAMPtz not null,
    last_updated_by VARCHAR(250) not null,
    last_updated_by_id UUID not null
);