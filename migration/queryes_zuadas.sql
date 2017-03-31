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
------------------------------------------------------------------------------

select
co.id,
cashier.idforcompany as cashier, 
payment.command, 
payment.datepayment,
co.payment_date,
--prof.name as prof, 
customer.name as customer,
p.name product,
td.price,
co.value,
pt.name as paymenttype
from commision co
inner join payment on(payment.id = co.payment)
inner join cashier on(cashier.id = payment.cashier)
inner join paymentdetail pd on(pd.id = co.payment_detail)
inner join paymenttype pt on(pt.id=pd.typepayment)
inner join business_pattern prof on(prof.id = co.user_c)
inner join business_pattern customer on(customer.id = payment.customer)
inner join treatmentdetail  td  on(td.id = co.treatment_detail)
inner join product p on(p.id = td.product or p.id = td.activity)
where co.company = 35 and co.user_c = 109312 and date(co.payment_date) between date('2013-10-01') and date('2013-10-31') 

order by datepayment desc;

select * from  commision where company=35 and due_date < date('2013-10-01');

update payment set  commission_processed = false 
where company=35 and datepayment >  date('2013-10-01')
and id in (select payment from treatment where user_c=109312);

update paymentdetail set  processed = false 
where company=35 and payment in (select payment from treatment where user_c=109312);

delete from commision  where company=35 and payment in(select payment from treatment where user_c=109312);








select count(1)
from paymentdetail pd
inner join payment p on(p.id=pd.payment)
inner join treatment t on(t.payment = p.id)
where typepayment in (select id from paymenttype  where customerregisterdebit=true) and p.deleted=false;