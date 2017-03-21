
select pd.value, 0.00, t.dateEvent, t.detailTreatmentAsText
from paymentdetail pd
inner join payment p on(p.id=pd.payment)
inner join treatment t on(t.payment = p.id)
where typepayment=84
and p.customer=17861 and p.deleted=false;
union
select 0.00, td.price,t.dateEvent,p.name 
from
treatmentdetail td
inner join product p on(p.id = td.product)
inner join treatment t on(t.id = td.treatment)
where product=100638 and  price is not null and t.customer=17861;



select * from payment p
inner join paymentdetail pd on(pd.payment=p.id)
 where p.company=23 
and p.id not in (select payment from treatment t where t.company=23 and payment<>null)
and p.deleted=false
and  pd.typepayment=84;





select * from (
	select p.command,pd.value, 0.00 as Pindurado, p.datePayment,  (select t.detailTreatmentAsText from treatment t where t.payment = p.id limit 1) as texto
	from paymentdetail pd
	inner join payment p on(p.id=pd.payment)
	where typepayment=84
	and p.deleted=false) a where texto is null





	delete from paymentdetail  where payment not in(select id from payment where company=23)

