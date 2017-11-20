ALTER TABLE business_pattern
  ALTER COLUMN password TYPE character varying(50);
update business_pattern set password=md5(password) where password is not null and password <> '';
update business_pattern set lastpassword=password where password is not null and password <> '';