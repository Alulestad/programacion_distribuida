## Base de datos
´´´
export PGHOST=distribuida2025.postgres.database.azure.com
export PGUSER=distribuida
export PGPORT=5432
export PGDATABASE=postgres
export PGPASSWORD=VLAD753.
´´´

## Creacion tablas
create table authors (
id serial primary key,
first_name varchar(64) not null,
last_name varchar(64) not null
);

create table books(
id serial primary key,
isbn varchar(64) not null,
title varchar(64) not null,
price decimal(5,2) not null,
author_id integer not null,
foreign key (author_id) references authors(id)
);
## Ingreso datos
insert into authors (first_name, last_name) values ('nombre1', 'apellido1');
insert into authors (first_name, last_name) values ('nombre2', 'apellido2');

insert into books (isbn, title, price, author_id) values ('isbn1', 'titulo1', 10.0, 1);
insert into books (isbn, title, price, author_id) values ('isbn2', 'titulo2', 20.0, 1);
insert into books (isbn, title, price, author_id) values ('isbn3', 'titulo3', 30.0, 1);
insert into books (isbn, title, price, author_id) values ('isbn4', 'titulo4', 40.0, 2);
insert into books (isbn, title, price, author_id) values ('isbn5', 'titulo5', 50.0, 2);


