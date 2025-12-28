create table if NOT EXISTS sequence(
    id serial4 primary key,
    name varchar(250) not null,
    format VARCHAR(250) not null,
    current_number int4 not null,
    start_no int4 not null default 0,
    reset_condition varchar(10),
    created_by varchar(250) not null,
    created_at TIMESTAMPTZ not null,
    created_by_id UUID not null,
    last_updated_by varchar(250) not null,
    last_updated_at TIMESTAMPTZ not null,
    last_updated_by_id UUID not null
)