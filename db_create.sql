create table author (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB
create table author_book (book_id bigint not null, author_id bigint not null, primary key (book_id, author_id)) engine=InnoDB
create table book (id bigint not null auto_increment, isbn varchar(255), title varchar(255), publisher_id bigint, primary key (id)) engine=InnoDB
create table category (id bigint not null auto_increment, category_name varchar(255), primary key (id)) engine=InnoDB
create table ingredient (id bigint not null auto_increment, amount decimal(19,2), description varchar(255), recipe_id bigint, unit_of_measure_id bigint, primary key (id)) engine=InnoDB
create table notes (id bigint not null auto_increment, notes longtext, recipe_id bigint, primary key (id)) engine=InnoDB
create table publisher (id bigint not null auto_increment, address_line_1 varchar(255), city varchar(255), name varchar(255) not null, state varchar(255), zip varchar(255), primary key (id)) engine=InnoDB
create table recipe (id bigint not null auto_increment, description varchar(255), difficulty varchar(255), directions varchar(255), image longblob, prep_time integer, notes_id bigint, primary key (id)) engine=InnoDB
create table recipe_category (recipe_id bigint not null, category_id bigint not null, primary key (recipe_id, category_id)) engine=InnoDB
create table unit_of_measure (id bigint not null auto_increment, unit_name varchar(255), primary key (id)) engine=InnoDB
alter table author_book add constraint FKg7j6ud9d32ll232o9mgo90s57 foreign key (author_id) references author (id)
alter table author_book add constraint FKn8665s8lv781v4eojs8jo3jao foreign key (book_id) references book (id)
alter table book add constraint FKgtvt7p649s4x80y6f4842pnfq foreign key (publisher_id) references publisher (id)
alter table ingredient add constraint FKj0s4ywmqqqw4h5iommigh5yja foreign key (recipe_id) references recipe (id)
alter table ingredient add constraint FK15ttfoaomqy1bbpo251fuidxw foreign key (unit_of_measure_id) references unit_of_measure (id)
alter table notes add constraint FKdbfsiv21ocsbt63sd6fg0t3c8 foreign key (recipe_id) references recipe (id)
alter table recipe add constraint FK37al6kcbdasgfnut9xokktie9 foreign key (notes_id) references notes (id)
alter table recipe_category add constraint FKqsi87i8d4qqdehlv2eiwvpwb foreign key (category_id) references category (id)
alter table recipe_category add constraint FKcqlqnvfyarhieewfeayk3v25v foreign key (recipe_id) references recipe (id)
