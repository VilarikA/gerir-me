update company  set partner=111133 where id=35;

update company  set partner=111139 where id=29;--Aline Fraga;
update company  set partner=0 where id=26;--Amanda Arlete Ramos;
update company  set partner=111140 where id=28;--Claudete Rabelo;
update company  set partner=111136 where id=32;--Clinica Osaine Estetica;
update company  set partner=111144 where id=20;--Clinicas Acolher;
update company  set partner=111131 where id=5;--Corpo Limpo;
update company  set partner=111142 where id=25;--e-Beleza;
update company  set partner=111134 where id=34;--fabricio e victor vabeleireio;
update company  set partner=111143 where id=24;--Hair Baton;
update company  set partner=111135 where id=33;--Márcio Henrique;
update company  set partner=77990 where id=37;--One Day SPA;
update company  set partner=111133 where id=35;--Pierre Alexander bh;
update company  set partner=111137 where id=31;--Rose;
update company  set partner=111132 where id=23;--Salao Beleza A Mais LTDA;
update company  set partner=111141 where id=27;--VEZO;
update company  set partner=111138 where id=30;--Via da beleza;

update company  set partner=0 where id=8;--

update business_pattern SET idforcompany=(select id from company where partner=business_pattern.id)  where company=1;
update company  set dateexpiration=date(now())+60;
