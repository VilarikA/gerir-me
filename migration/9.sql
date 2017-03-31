INSERT INTO inventorycause(
            name, id, company, updatedby, createdby, updatedat, createdat, 
            obs)
SELECT ic.name, nextval('inventorycause_id_seq'), c.id as company, ic.updatedby, ic.createdby, ic.updatedat, ic.createdat, 
       ic.obs
FROM inventorycause ic
inner join company c on(1=1)
where c.id != 8;




update company  
set inventorycausetrasfer=(SELECT ic.id from inventorycause ic where ic.company=company.id and ic.name like 'Trasferencia' limit 1),
inventorycausesale=(SELECT ic.id from inventorycause ic where ic.company=company.id and ic.name like 'Venda' limit 1),
inventorycausepurchase=(SELECT ic.id from inventorycause ic where ic.company=company.id and ic.name like 'Compra' limit 1);