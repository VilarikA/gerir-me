delete from business_pattern where company =29 and is_customer=true;
insert into business_pattern (
            name, 
            state, 
            number_c, 
            complement, 
            external_id,
            id,
            email, 
            updatedat, 
            createdat, 
            company, 
            sex, 
            phone,            
            updatedby, 
            createdby,
            document,
            birthday,
            mobile_phone,
            street,
            district, 
            city,
            barcode,
            indicatedby,
            obs,
            email_alternative,
            is_customer
            ) 
select 
distinct
"Nome",
e."UF",
0 as number,
'' as complement,
c.cod_cli, 
nextval('business_pattern_id_seq'),
em."Email",
now() as updatedat,
now() as createdat,
29 as company ,
p."Sexo", 
t."Telefone",
1 as updatedby ,
1 as createdby, 
p."CPF",
p."Data_Nascimento", 
t2."Telefone" as mobile_phone, 
e."Endereco",
e."Bairro", 
e."Cidade",
p."Cod_Barra",
indicadopor,
obs,
em2."Email",
true
from 
tab_cliente c
inner join tab_pessoas p on (p."Codigo" = c.Codigo)
left join tab_endereco e on (e."Codigo" = p."Codigo" and e."Tipo_End"='1')
left join tab_emails em on (em."Codigo" = p."Codigo" and em."Email"!='' and em."tipo_email" = '1')
left join tab_emails em2 on (em2."Codigo" = p."Codigo" and em2."Email"!='' and em2."tipo_email" = '2')
left join tab_telefones t on (t."Codigo" = p."Codigo" and t."Tipo_Tel" = '1')
left join tab_telefones t2 on (t2."Codigo" = p."Codigo" and t2."Tipo_Tel" = '2');
update business_pattern set email='' where email is null and company=29;
update business_pattern set phone='' where phone is null and company=29;
update business_pattern set mobilephone ='' where mobilephone is null and company=29;
update business_pattern set email='' where email is null and company=29;
update business_pattern set phone='' where phone is null and company=29;
update business_pattern set mobilephone ='' where mobilephone is null and company=29;

----------------------------------------------------------------------------------------------------------------------

delete from product where company=29;
INSERT INTO product(name, external_id, company, updatedat, createdat, obs, updatedby, createdby, 
       commission, saleprice, typeproduct, purchaseprice, currentstock, 
       minstock)
select "Produto","Cod_Produto", 29,now(),now(),"Obs_Produto",1,1,"percentcomissao","ValorVenda","Cod_Categoria","precomedio",0,"EstoqueMinimo" from tab_produto;

-------------------------------------------------------------------------------------------------------------------------------
delete from activitytype;

INSERT INTO activitytype
select  "Descricao_Atividade","CodAtividade", 29, now(),now(),'',1,1 from tab_atividades;
delete from activity;
INSERT INTO activity(
            name, id, duration, company, updatedat, createdat, price, obs, 
            updatedby, createdby, typeactivity)
SELECT tipo, Cod_Detalhes_Atividade, duracao , 29, now(),now(), Preco_Cobrado,  obs, 1, 1, Cod_Atividade
FROM tab_atividades_detalhes;
update product set productclass=1 where company=29;

insert into product (id,name, external_id,duration,company,updatedat, createdat, saleprice, obs, 
       updatedby, createdby, typeproduct, commission, search_name,productclass)
SELECT nextval('product_id_seq'),name, id, duration, company, updatedat, createdat, price, obs, 
       updatedby, createdby, typeactivity, commission, '',0
FROM activity;
update product  set productclass =0 where productclass is null and company=29;

update product  set search_name  = lower(name) where company=29;

insert into producttype  (id,name,external_id,company,updatedat, createdat, obs, updatedby, createdby,typeclass)
SELECT nextval('producttype_id_seq'),name, id, company, updatedat, createdat, obs, updatedby, createdby,1
FROM activitytype;

-------------------------------------------------------------------------------------------------------------------------------
update business_pattern  set search_name  = lower(name)  where company=29;

update business_pattern set unit=(select max(id) from companyunit where company=29 )  where company=29;

-------------------------------------------------------------------------------------------------------------------------------
delete from treatment where company=29;
INSERT INTO treatment(
            start_c, 
            command,
            external_id, 
            end_c, 
            user_c,
            status,
            company, 
            customer, 
            updatedat, 
            createdat, 
            updatedby, 
            createdby, 
            hasdetail,
            showincalendar)
SELECT CAST((CAST("Data" as text) || ' ' || CAST("HorarioInicial" as text)) AS timestamp) as start,
       numcomanda,
       "Cod_Atendimento",
       CAST((CAST("Data" as text) || ' ' || CAST("HorarioFinal" as text))AS timestamp) as end_c,
       "Cod_Func",
      "StatusAtendimento",
       29,
      "Cod_Cli_velho",
       now(),
       now(),
       1,1,true,true
  FROM tab_atendimento;

update treatment
set customer=(select max(id) from business_pattern bp  where CAST(bp.external_id AS integer)=customer and is_customer=true and company=29 ) 
where company=29;

update treatment  
set user_c=(select max(id) from business_pattern bp  where CAST(bp.external_id AS integer)=user_c and is_employee=true and bp.external_id<>'' and company=29 )
where company=29;
update treatment set status=0 where status>4 and company=29;
update treatment  set dateevent=start_c where company=29;

-------------------------------------------------------------------------------------------------------------------------------
INSERT INTO treatmentdetail(
            id, product, activity, updatedat, createdat, price, updatedby, 
            createdby, treatment,company)
SELECT nextval('treatment_id_seq'),
       "Cod_produto",
       "Cod_Detalhes_Atividade",
       now(),
       now(),
       "Valor_Atividade",
       1,
       1,
       "CodAtendimento",
       29
FROM tab_atendimento_detalhes;
update treatmentdetail set treatment =(select id from treatment t  where cast(t.external_id as integer)=treatment) where company=29
update treatmentdetail set activity=(select id from product p where p.productclass=0 and p.external_id=cast(activity as varchar) and company=29) where company=29;   
update treatmentdetail set product=(select id from product p where p.productclass=1 and p.external_id=cast(product as varchar) and company=29) where company=29;  
-------------------------------------------------------------------------------------------------------------------------------
update treatment set status=0 where status>4 and company=29;
update treatment  set dateevent=start_c;

UPDATE business_pattern  set userstatus =1 where company=29;
UPDATE business_pattern  set is_employee  = is_user where company=29;
update business_pattern  set search_name  = lower(name)  where company=29;

update business_pattern set showincalendar=true where is_employee=true and company=29;
update business_pattern set unit=(select max(id) from companyunit where company=29 )  where company=29;