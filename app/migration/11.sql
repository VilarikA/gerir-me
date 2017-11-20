update accountcategory set "isparent" = "parent_$qmark";
update business_pattern where valueInAccount = 0;
update accountpayable set createdatdate = createdat;


ALTER TABLE commision
   ALTER COLUMN due_date TYPE date;
ALTER TABLE commision
   ALTER COLUMN payment_date TYPE date;

ALTER TABLE payment
   ALTER COLUMN datepayment TYPE date;