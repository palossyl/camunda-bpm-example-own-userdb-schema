create table user(
	id varchar(50) not null primary key,
	password varchar(50) not null,
	firstname varchar(50) not null,
	lastname varchar(50) not null,
	email varchar(50) not null
);

create table groups (
	id varchar(50) primary key,
	name varchar(80)
);

create table group_authorities (
    id int auto_increment primary key,
	group_id varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);

create table group_members (
    id int auto_increment primary key,
	group_id varchar(50) not null,
	user_id varchar(50) not null,
	constraint fk_group_members_group foreign key(group_id) references groups(id)
);
