create database groupDB;
use groupDB;

create table `groups`
(
    id bigint auto_increment
        primary key,
    name varchar(15) not null
);

create table group_users
(
	id bigint auto_increment
		primary key,
	user_id bigint not null,
	group_id bigint not null,
	constraint group_users_groups_id_fk
		foreign key (group_id) references `groups` (id)
			on update cascade on delete cascade
);


