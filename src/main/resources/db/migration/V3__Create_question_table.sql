create table question
(
	id int auto_increment,
	title varchar(50),
	description TEXT,
	gmt_create BIGINT,
	gmt_modified BIGINT,
	creator int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag VARCHAR(255)
);

create unique index question_id_uindex
	on question (id);

alter table question
	add constraint question_pk
		primary key (id);

