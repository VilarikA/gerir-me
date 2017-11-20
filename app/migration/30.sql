
update accountpayable set exerciseDate = dueDate;

update accountpayable set exerciseDate = (select openerDate from cashier where id=accountpayable.cashier) where cashier is not null;