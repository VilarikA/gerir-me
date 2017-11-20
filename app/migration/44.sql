update useractivity 
set
use_product_price = false
where 
price <>0;

update useractivity 
set
use_product_commission = false
where 
commission <>0;
--
update useractivity 
set
use_product_price = true
where 
price = 0;

update useractivity 
set
use_product_commission = true
where 
commission = 0;