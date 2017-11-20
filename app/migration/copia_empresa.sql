INSERT INTO business_pattern(
            name, group_c, status, lng, state, number_c, complement, id, 
            unit, username, password, email, updatedat, createdat, lat, company, 
            sex, is_employee, search_name, updatedby, createdby, email_alternative, 
            phone, mobile_phone, birthday, document, document_company, street, 
            district, city, barcode, indicatedby, obs, external_id, is_prospect, 
            is_suplier, is_person, is_user, showincalendar, orderincalendar, 
            is_customer, is_brand, userstatus, grouppermission, sennotifications, 
            lastbuydate, lastsaledate, hiredate, resignationdate, facebookid, 
            facebookaccesstoken, facebookusername, bp_indicatedby, short_name)
            
SELECT name, group_c, status, lng, state, number_c, complement, nextval('business_pattern_id_seq'), 
       unit, username, password, email, updatedat, createdat, lat, 9, 
       sex, is_employee, search_name, updatedby, createdby, email_alternative, 
       phone, mobile_phone, birthday, document, document_company, street, 
       district, city, barcode, indicatedby, obs, external_id, is_prospect, 
       is_suplier, is_person, is_user, showincalendar, orderincalendar, 
       is_customer, is_brand, userstatus, grouppermission, sennotifications, 
       lastbuydate, lastsaledate, hiredate, resignationdate, facebookid, 
       facebookaccesstoken, facebookusername, bp_indicatedby, short_name
  FROM business_pattern;
--Parceiros...



