alter table location
add COLUMN if not EXISTS active boolean default true,
add column if not EXISTS room_level varchar(10) not NULL,
add column if not exists location_type varchar(10) not null;

alter table location
drop column if exists room_type;

alter table events
add column if not EXISTS location_name varchar(250) not null,
ADD COLUMN if not EXISTS period_start timestamptz not null,
add column if not EXISTS period_end timestamptz not NULL,
add column if not EXISTS start_time varchar[] not null;