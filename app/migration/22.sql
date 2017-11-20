update account  set lastvalue=value;
update accountpayable  set lastvalue=value where typemovement=0;
update accountpayable  set lastvalue=value*-1 where typemovement=1;	
update accountpayable   set last_paid = paid;
ALTER TABLE accountpayable 
ALTER COLUMN value TYPE double precision;

