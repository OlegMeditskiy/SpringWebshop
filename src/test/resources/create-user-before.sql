delete from cart;
delete from user_role;
delete from usr;

insert into usr (id, active, password, username) values
(1,true,'1','u'),
(2,true,'1','u2');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');

insert into cart (id,user_id) values
(1,1),(2,2);