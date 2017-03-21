update payment set commission_processed=true where company=35;

update payment set commission_processed=false where  datepayment >=  date('2013-10-01') and company=35;

update paymentdetail set  processed = false, commisionnotprocessed=value where company=35;

delete from commision  where company=35;



update payment set commission_processed=true where company=35;

update payment set commission_processed=false where  datepayment >  date('2013-10-01') and company=35;

update paymentdetail set  processed = false, commisionnotprocessed=value where company=35;



select * from commision where company=35 and payment is not null;





update paymentdetail set typepayment =1057 where typepayment is null and company=35;
update payment set  commission_processed = false 
where company=35 and datepayment <>  date('2013-10-01')
and id in(select payment from treatment where user_c=109312)
;
update paymentdetail set  processed = false where company=35;
delete from commision  where company=35;


select * from commision where company=35 and payment is not null;


select
co.id,
cashier.idforcompany as cashier, 
payment.command, 
payment.datepayment,
co.payment_date,
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
where co.company = 35 and co.user_c = 109312 and date(co.payment_date) between date('2013-10-01') and date('2013-10-14')
and td.price =130.00
order by datepayment desc;


select * from commisiondetails  where commision =129468 order by id;