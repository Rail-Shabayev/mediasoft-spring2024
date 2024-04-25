create table product (
        date_created date,
        price numeric(38,2) not null,
        quantity integer not null,
        date_quantity_updated timestamp(6),
        uuid uuid not null,
        description varchar(255),
        name varchar(255),
        type varchar(255) not null check (type in ('ELECTRONICS','CLOTHING','FOOTWEAR','BOOKS','ACCESSORIES')),
        primary key (uuid)
    )