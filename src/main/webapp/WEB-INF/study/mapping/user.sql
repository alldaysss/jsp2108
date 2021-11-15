show tables;

create table user (
	idx int not null auto_increment primary key,
	name varchar(20) not null,
	age int default 20
);

insert into user values (default, '관리자', 45);
insert into user values (default, '홍길동', 25);
insert into user values (default, '김말숙', default);
insert into user values (default, '관리맨', 48);
insert into user values (default, '홍길자', 35);
insert into user values (default, '김영숙', default);

-- drop tables uesr;

select * from user;

select * from user order by idx desc