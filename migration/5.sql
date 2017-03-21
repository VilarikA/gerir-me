ALTER TABLE company ADD COLUMN user_c BIGINT
ALTER TABLE treatmentdetail ADD COLUMN amount INTEGER
ALTER TABLE treatmentdetail ADD COLUMN for_delivery BOOLEAN
ALTER TABLE useractivity ADD COLUMN company BIGINT
CREATE INDEX company_user_c ON company ( user_c )
CREATE INDEX useractivity_company ON useractivity ( company )