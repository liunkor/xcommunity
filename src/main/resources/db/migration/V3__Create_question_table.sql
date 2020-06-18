create table question
(
    id            bigint auto_increment,
    title         varchar(100),
    description   text,
    gmt_create    bigint,
    gmt_modified  bigint,
    creator       bigint not null,
    view_count    bigint default 0,
    like_count    bigint default 0,
    tag           varchar(255),
    comment_count bigint default 0,
    constraint question_pk
        primary key (id)
);

create unique index question_id_uindex
    on question (id);