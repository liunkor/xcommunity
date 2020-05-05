create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	type int,
	content varchar(1024) not null,
	commentor int not null,
	gmt_create BIGINT not null,
	gmt_modified BIGINT not null,
	like_count bigint default 0,
	constraint comment_pk
		primary key (id)
);

comment on column comment.parent_id is 'parent id';

comment on column comment.type is 'the type of parent';

comment on column comment.commentor is 'the user who post the comment';

comment on column comment.gmt_create is 'the time when the comment posted';

comment on column comment.gmt_modified is 'the time of the comment recently modified';

