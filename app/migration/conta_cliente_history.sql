select pd.value*-1 as value, pd.id as paymentdetail,pd.payment, p.customer, p.company, 'Adicionando valor a conta cliente'  as obs
from paymentdetail pd
inner join payment p on(p.id=pd.payment)
inner join treatment t on(t.payment = p.id)
where typepayment in (select id from paymenttype  where customerregisterdebit=true) and p.deleted=false;


select td.price as value, td.id as treatment_detail, t.id as treatment, p.id as payment, t.company, 'Pagando conta cliente!' as obs
from
treatmentdetail td
inner join product p on(p.id = td.product)
inner join treatment t on(t.id = td.treatment)
where product=100638 and  price is not null;

