ALTER TABLE monthly RENAME paymentdate  TO _paymentdate;
ALTER TABLE monthly ADD COLUMN paymentdate date;

update monthly set paymentdate = date(_paymentdate) where _paymentdate<> null;
ALTER TABLE monthly DROP COLUMN _paymentdate;
