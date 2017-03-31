INSERT INTO paymenttype(name, id, company, updatedat, createdat, updatedby, createdby, numdays, needchequeinfo, needcardinfo, sumincachier, generatecommision, deliverycontol, showasoptions, customerregisterdebit, customerdebitsettled, key_c, order_c)
SELECT name, nextval('payment_id_seq'), 5 as company, updatedat, createdat, updatedby, createdby, numdays, needchequeinfo, needcardinfo, sumincachier, generatecommision, deliverycontol, showasoptions, customerregisterdebit, customerdebitsettled, key_c, order_c
FROM paymenttype where company=8;


INSERT INTO product(
name, id, company, updatedat, createdat, obs, updatedby, createdby, 
commission, saleprice, typeproduct, purchaseprice, currentstock, 
minstock, duration, search_name, external_id, commisionprice, 
is_bom, productclass, brand, is_discount, is_for_sale, is_inentory_control, 
allowsimultaneos, showincommad, short_name, orderincommand)
SELECT name, nextval('product_id_seq'), 5 as company, updatedat, createdat, obs, updatedby, createdby, 
       commission, saleprice, typeproduct, purchaseprice, currentstock, 
       minstock, duration, search_name, external_id, commisionprice, 
       is_bom, productclass, brand, is_discount, is_for_sale, is_inentory_control, 
       allowsimultaneos, showincommad, short_name, orderincommand
  FROM product where productclass not in(1,0);



INSERT INTO companyunit(
        name, id, company, updatedat, createdat, updatedby, createdby, 
        showincalendar)
SELECT name, nextval('companyunit_id_seq'), 5 as company, updatedat, createdat, updatedby, createdby, 
   showincalendar
FROM companyunit WHERE company=19;




INSERT INTO permissionmodule(
            name, id, updatedat, createdat, company, updatedby, createdby)
SELECT name, nextval('permissionmodule_id_seq'), updatedat, createdat, 5 as company, updatedby, createdby
  FROM permissionmodule where company = 8

--evita erros de null de importacao
update business_pattern  set email ='' where email is null;
update product   set obs ='' where obs is null;