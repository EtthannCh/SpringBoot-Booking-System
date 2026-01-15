alter table booking
add column if not exists last_updated_by varchar(200),
add column if not EXISTS last_updated_by_id uuid,
add column if not EXISTS last_updated_at timestamptz;

