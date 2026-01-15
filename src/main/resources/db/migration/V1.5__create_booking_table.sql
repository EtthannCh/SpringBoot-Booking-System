create table if NOT EXISTS booking( 
    id serial4 PRIMARY KEY,
    user_id uuid not null,
    event_id int4 not null,
    status varchar(50) not null,
    booking_no VARCHAR(250) not null,
    show_time TIME not null,
    qty int4 not null DEFAULT 1,
    created_at TIMESTAMPtz not null,
    created_by VARCHAR(250) not null,
    created_by_id UUID not null,
    Foreign Key (user_id) REFERENCES users(user_id),
    Foreign Key (event_id) REFERENCES events(id)
);

CREATE table if not EXISTS booking_detail(
    id serial4 PRIMARY key,
    booking_id int4 not null,
    price numeric(13,2) not null,
    seat_id int4[] not null,
    Foreign Key (booking_id) REFERENCES booking(id),
    created_at TIMESTAMPtz not null,
    created_by VARCHAR(250) not null,
    created_by_id UUID not null
)