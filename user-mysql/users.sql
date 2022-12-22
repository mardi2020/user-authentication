create database userDB;
use userDB;

create table users
(
	id bigint auto_increment
		primary key,
	email varchar(255) not null,
	name varchar(255) not null,
	password varchar(255) not null,
	roles varchar(255) null,
	constraint UK_3g1j96g94xpk3lpxl2qbl985x
		unique (name),
	constraint UK_6dotkott2kjsp8vw4d0m25fb7
		unique (email)
);

