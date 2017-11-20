update workhouer set company=(select company from business_pattern  where id=workhouer.user_c);
update workhouer set end_c='19:20'  where end_c='19:00' and company=35;