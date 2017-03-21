select *  from treatmentdetail where treatment=374403;
select t.id,
(select sum(price) from treatmentdetail where treatment=t.id) as total,
(select sum(value) from paymentdetail where payment=t.payment) as total_paid
from treatment t 
where
customer=104600 order by start_c desc limit 1;
select * from paymentdetail  where id=70241;
select t.id,
(select sum(price) from treatmentdetail where treatment=t.id) as total,
(select sum(value) from paymentdetail where payment=t.payment) as total_paid
from treatment t 
where
customer=97370 order by start_c desc limit 1;
select * from treatmentdetail  where treatment =302066;

select * from treatment   
where id =302066;



select * from  paymentdetail where createdby=1 and company=35 limit 1


select * from (
select t.id, t.customer,
(select sum(price) from treatmentdetail where treatment=t.id) as total,
(select sum(value) from paymentdetail where payment=t.payment) as total_paid
from treatment t where t.company=35
order by start_c desc
) a
where  total_paid>0 and total_paid<total ;

update payment set value = (select sum (price) from treatmentdetail where company = 35 and obs like '%atend%'||payment.external_id||'%')
where company = 35 and obs like '%atend%' ;

update paymentdetail set value = (select sum (price) from treatmentdetail where company = 35 and obs like '%atend%'||paymentdetail.external_id||'%')
where company = 35 and paymentdetail.payment in (select id from payment where company = 35 and obs like '%atend%' );






select * from commisiondetails  limit 10;