CREATE Table if NOT EXISTS charge_item(
    id serial PRIMARY key,
    booking_id int4 not NULL,
    qty int2 not null,
    unit_price numeric(15,4) not NULL,
    total_price numeric(15,4) not NULL,
    created_by_id uuid not null,
    created_by varchar(250) not null,
    created_at TIMESTAMPTZ not null DEFAULT now(),
    last_updated_by_id uuid not null,
    last_updated_by varchar(250) not null,
    last_updated_at TIMESTAMPTZ not null default now(),
    Foreign Key (booking_id) REFERENCES booking(id)
)