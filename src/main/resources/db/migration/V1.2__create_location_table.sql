create table if not EXISTS location (
    id serial4 PRIMARY key,
    name varchar(250) not null,
    room_type varchar(10) not null,
    part_of int4 REFERENCES location (id),
    section varchar(50),
    row varchar(10),
    col int4,
    created_by_id uuid not null,
    created_by varchar(250) not null,
    created_at TIMESTAMPTZ not null DEFAULT now(),
    last_updated_by_id uuid not null,
    last_updated_by varchar(250) not null,
    last_updated_at TIMESTAMPTZ not null default now(),
    constraint location_name_key UNIQUE (name)
);