alter TABLE roles
add column if not EXISTS code varchar(100) not null;

alter table roles
drop constraint if exists role_code_key,
drop constraint if EXISTS role_name_key;

alter table roles
add constraint role_code_key unique (code),
add constraint role_name_key unique (name);