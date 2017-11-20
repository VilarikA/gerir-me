ALTER TABLE commision
   ALTER COLUMN value TYPE double precision;

ALTER TABLE paymentdetail
   ALTER COLUMN value TYPE double precision;

ALTER TABLE paymentdetail
   ALTER COLUMN value TYPE double precision;
ALTER TABLE paymentdetail
   ALTER COLUMN commisionnotprocessed TYPE double precision;

ALTER TABLE payment
   ALTER COLUMN value TYPE double precision;

ALTER TABLE useractivity  
   ALTER COLUMN commission TYPE double precision;
ALTER TABLE useractivity  
   ALTER COLUMN price TYPE double precision;