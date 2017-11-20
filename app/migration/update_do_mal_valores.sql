update treatmentdetail set price = 
(select ad."Valor_Atividade" from treatmentdetail td
inner join treatment tr on tr.id = td.treatment and tr.dateevent > '2013-10-10' and tr.dateevent < '2013-10-15' and tr.obs like '%atend%'
inner join ieda.tab_atendimento_detalhes ad on ad."CodAtendimento" = to_number (tr.external_id,'999999') 
and ad."Cod_Detalhes_Atividade" = (select to_number (external_id,'999999') from product where company = 35 and productclass = 0 
and trim (to_char (ad."Cod_Detalhes_Atividade",'999999')) = external_id
and id = td.activity
and typeproduct <> 596 /* kit manicure */)
and "Valor_Atividade" <> td.price
where td.company = 35 and td.id = treatmentdetail.id) where

id in (select td.id from treatmentdetail td
inner join treatment tr on tr.id = td.treatment and tr.dateevent > '2013-10-10' and tr.dateevent < '2013-10-15' and tr.obs like '%atend%'
inner join ieda.tab_atendimento_detalhes ad on ad."CodAtendimento" = to_number (tr.external_id,'999999') 
and ad."Cod_Detalhes_Atividade" = (select to_number (external_id,'999999') from product where company = 35 and productclass = 0 
and trim (to_char (ad."Cod_Detalhes_Atividade",'999999')) = external_id
and id = td.activity
and typeproduct <> 596 /* kit manicure */)
and "Valor_Atividade" <> td.price
where td.company = 35 and td.id = treatmentdetail.id) and company = 35;


