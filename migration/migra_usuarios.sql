drop table user_t;
drop table customer;

update business_pattern set userstatus=4;
update business_pattern set userstatus=1 where showincalendar=true;