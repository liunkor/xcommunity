alter table QUESTION alter column ID bigint auto_increment;
alter table QUESTION alter column CREATOR bigint not null;
alter table QUESTION alter column COMMENT_COUNT bigint default 0;
alter table QUESTION alter column LIKE_COUNT bigint default 0;
alter table QUESTION alter column COMMENT_COUNT bigint default 0;
alter table USER alter column ID bigint auto_increment;
alter table QUESTION alter column VIEW_COUNT bigint default 0;
alter table COMMENT alter column TYPE Int not null;
alter table COMMENT alter column COMMENTOR bigint not null;