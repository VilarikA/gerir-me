update paymenttype set sumtoconference=false;
update paymenttype set sumtoconference=true where needchequeinfo = true;
update paymenttype set sumtoconference=true where needcardinfo = true;
update paymenttype set sumtoconference=true where sumincachier = true;
