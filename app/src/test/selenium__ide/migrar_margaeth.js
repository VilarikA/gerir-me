describe('Vamos ver...', function() {
	//it('load page', function(){
		//browser.driver.get('http://ebelle.vilarika.com.br');
		browser.driver.get('http://localhost:7171/v2/login');
		//browser.driver.findElement(by.xpath('//*[@id="login-box"]/div/div[1]/div[3]/a')).click();
		browser.driver.findElement(by.xpath('//*[@id="email"]')).sendKeys('mateus.freira@gmail.com');
		browser.driver.findElement(by.xpath('//*[@id="login-box"]/div/div[1]/form/fieldset/label[2]/span/input')).sendKeys('775072');
		browser.driver.findElement(by.xpath('//*[@id="login-box"]/div/div[1]/form/fieldset/div[2]/button')).click();
		browser.driver.sleep(1000);
//		browser.driver.findElement(by.id('_passwrodForLogin')).sendKeys('775072');
//		browser.driver.findElement(by.id('_companyForLogin')).sendKeys('66');
//		browser.driver.findElement(by.id('_btnIn')).click();
		var clientes = 
				[
["","ALAN - PERTO MAÇONARIA","9328-9051","AV SEBASTIÃO DAIREL DE LIMA"," 155 AP 203","BRASILEIA",""],
["","ALEXANDRE CONTAINER TENDA","7323-1735","CONTAINER TENDA PREDIO RABELO","INGÁ",""],
["","ALINE XAVIER ","9821-5373","AV GOVERNADOR VALADARES"," 510 - 3º ANDAR","CENTRO","CARTORIO IMOVEIS"],
["","AMANDA - DEPILADORA CRIS","8827-7765","RUA TEIXEIRINHA"," 70","NOVO HORIZONTE","depois da escola depois do salao do encontro"],
["","AMANDA - DTA","9115-0050","AV  AMAZONAS"," 5051","CACHOEIRA","DTA"],
["","AMANDA CARVALHO - DTA","9290-7192","AV  AMAZONAS"," 5051","CACHOEIRA","DTA"],
["","AMANDA CREAS","9808-2012","RUA SANTA CRUZ"," 17","BRASILEIA","CASA: AV GOV VALADARES"," 101 - CENTRO"],
["","AMANDA SALÃO ESSENCIALE","9703-3215","RUA HENRIQUE MACHADO HORTA"," 93","ANGOLA","EM FRENTE AO GIRASSOL"],
["","AMELIA JUIZADO ESPECIAL","9912-6060","RUA INCONFIDENCIA"," 290 - SALA 202","CENTRO",""],
["","AMPAV","8579-8019","AV TEOTONIO PARREIRA COELHO"," 324","JARDIM DA CIDADE","PAGAMENTO MENSAL DIA 11 VIA CONTA CAIXA. CNPJ: 09.529.484/0001-35"],
["","ANA LUIZA GUARUJÁ","9309-2311","RUA RUBENS PINHO ANGELO"," 150 - AP 302","GUARUJÁ","SUBINDO MORRO AMELIA"," 1ª ESQUERDA"],
["","ANA PAULA AVELAR","9157-2214","MONT SERRAH","CACHOEIRA",""],
["","ANA PAULA KENNEDY ACABAMENTOS","9609-4190","AV AMAZONAS - 403","CENTRO","KENNEDY ACABAMENTOS"],
["","ANDERSON MIRANDA","9304-1121","RUA HORIZONTE BELO"," 571","FILADELFIA",""],
["","ANDRÉ VEÍCULOS","3532-0353","AV BANDEIRANTES"," 220","FILADELFIA",""],
["","ANDREIA (ALEX)","9950-1227","RUA ALFA"," 103 - AP 201","BRASILEIA",""],
["","APARECIDA - MAE HELENA","3531-4426","RUA CAPELA NOVA"," 243 ","FILADELFIA",""],
["","AUGUSTA CARTORIO ROBERTO SILVA","9673-4403","AV NS CARMO"," 90","CENTRO","CARTORIO ROBERTO SILVA"],
["","BETANIA - POSTO PANAMERA","9355-9687","AV BANDEIRANTES"," 1155 - POSTO PANAMERA","CHACARA",""],
["23","BOTICARIO CENTER GONTIJO - MARILIA","9912-2926","AV AMAZONAS"," 471","CENTRO",""],
["24","BOTICARIO VENDA DIRETA","3595-4796","RUA PEDRO NEVES"," 54 ","CENTRO",""],
["25","BRUNA CONSTRUTORA ","9922-6124","EM FRENTE AO RODOVIARIO","CENTRO",""],
["26","Bruninho André Veículos","9293-8983","AV BANDEIRANTES"," 220 ","FILADELFIA","ANDRÉ VEÍCULOS"],
["28","BRUNO FORUM 5ª VARA CIVEL","9811-9811","ANTIGA PREFEITURA 3º ANDAR","CENTRO",""],
["27","BRUNO RABELO","9974-7379","RUA JOSÉ INÁCIO FILHO"," 1001 - COND. VITALLE - BLOCO 2 - AP 307","INGÁ",""],
["29","CAMILA INGÁ","8888-1330","RUA ANTONIO DA SILVA"," 151 - BLOCO1 -AP201","INGÁ","AO LADO DO PARQUE DE EXPOSIÇÕES"],
["30","CAROLINA SAJ PUC","8801-3309","SAJ PUC","ANGOLA",""],
["31","CARTORIO CIVIL JK  - CAROLINA ","7591-3716","AV JK"," 315 (AO LADO DO LUNA)","CENTRO",""],
["","CARTORIO ROBERTO SILVA","3531-1074","AV NOSSA SENHORA DO CARMO"," 90","CENTRO",""],
[33,"CINTHIA - ESPOSA RAFAEL - TÓ","8262-9325","RUA PRESIDENTE VARGAS"," 1120 - AP 102","GUARUJÁ","NA RUA DO JOAO PAULO. PREDIO AMARELO"],
[34,"CINTIA DIONIZIO CAVALCANTE - CREAS","9696-5570","RUA SANTA CRUZ"," 17","BRASILEIA","CASA: RUA BARÃO DE MONTE ALTO"," 39 - NOVO HORIZONTE. CPF:086.737.046-77. PAGAMENTO: 5º DIA UTIL"],
[35,"CLAUDIA FORUM VARA FAMILIA","9874-2827","RUA PROFESSOR OSVALDO FRANCO - 2 ANDAR ","CENTRO",""],
[36,"CLAUDIA PQ DAS AMORAS","9107-1450","PARQUE DAS AMORAS - BLOCO 13 - AP 204","","DEPOIS DO HOSPITAL REGIONAL"],
[37,"CLEMILSON ","9624-3484","AV EDMEIA MATOS LAZAROTTI"," 3061","INGÁ",""],
[38,"CRISTINA CREAS","9903-7093","RUA SANTA CRUZ"," 17","BRASILEIA",""],
[39,"DAIANA FEIRA SHOPPING","8787-3882","AV AMAZONAS"," FEIRA SHOP LOJA 18","CENTRO",""],
[40,"DAIANE DTA","9135-3342","AV  AMAZONAS"," 5051","CACHOEIRA",""],
[41,"DANIELA - POSTO","9442-4043","AV BANDEIRANTES"," 1155 - POSTO PANAMERA","FILADELFIA",""],
[42,"DANIELA (FABIANO)","8861-7863","RUA SILVA GUIMARÃES"," 195","NOSSA SENHORA DAS GRAÇAS",""],
[43,"DANIELA AMPAV","8579-8019","AV TEOTONIO PARREIRA COELHO"," 324","JARDIM DA CIDADE",""],
[44,"DANILVANIA PACHECO","9471-5385","DROGARIA PACHECO","LUNA",""],
[45,"DÉBORA - PREDIO JU LARA","8463-4163","RUA INSPETOR JAIME CALDEIRA"," 865 - SALA 5","BRASILEIA",""],
[46,"DEBORA ALBERT","8875-8017","AV MARCO TULIO"," 2000 AP 201 BLOCO C","CHACARA",""],
[47,"DEBORA ITAU","9822-3280","AV GOVERNADOR VALADARES"," 300","CENTRO",""],
[48,"DEBORA PREFEITURA","8847-1710","SEMAS PREFEITURA ","","SALA SUELI - predio a direita de tijolinho"," 1ª porta a esquerda"],
[49,"DENIS ","9790-0149","CEMITERIO NS CARMO","CENTRO",""],
[50,"DENISE CABELELEIRA","9798-3348","RUA INSPETOR JAIME CALDEIRA","BRASILEIA",""],
["","Diego André Veículos","7596-7991","AV BANDEIRANTES"," 221","FILADELFIA","ANDRÉ VEÍCULOS"],
["","DITAO","7181-8726","RUA HUMBERTO CAMPOS"," 131","CHACARA",""],
["","DITÃO","7181-8726","","",""],
["","DONA FATIMA","9990-6734","RUA ANTONIO LEMOS FILHO"," 144A AO LADO CAMPO BOI","ANGOLA",""],
["","DONA NEIDE","9765-5210","RUA AQUEBER ARISTIDES SALIBA"," 90 - AP 303","CENTRO"," RUA DO SPLENDORE"],
["","EDER - NOVO GUARUJÁ","8796-4066","RUA ANTONIETA PEREIRA ARANTES"," 182","NOVO GUARUJA ","depois da creche"," ultima rua sem saída a esquerda"],
["","EDER - RETA SISTEMAS","8786-6830","","",""],
["","EDMUR","9760-2266","RUA CAIO MARTINS"," 840","FILADELFIA","MÃE: R AVELINO JOSÉ DE OLIVEIRA. 104 - CHACARA (3531-2997)"],
["","EDNA - PARQUE DAS INDUSTRIAS","9273-2661","RUA TIRADENTES"," 113 ","PARQUE DAS INDUSTRIAS","PASSA O DIVINO BRAGA"," 2ª PONTE A DIREITA"," SOBE O MORRÃO"," ULTIMA RUA A ESQUERDA"],
["","EDNA DITAO","9249-0461","","RIVIERA",""],
["","EDUIGES (DU)","9115-1644","SEMAS PREFEITURA","BRASILEIA",""],
["","EMANUELA MELO","9663-8083","RUA ANTONIO QUIRINO DA SILVA"," 883","INGÁ BAIXO",""],
["","ERICK - TOTAL PRINT","9184-1375","","",""],
["","ESPAÇO VISAGE (JUSSARA)","3594-7164","RUA HENRIQUE MACHADO HORTA"," 151 ","ANGOLA",""],
["","ESTEFANIA POSTO BR","9498-0676","AV BANDEIRANTES"," 1155 - POSTO PANAMERA","CHACARA",""],
["","EUSTAQUIO COPASA2 ","3539-4329","RUA GABRIEL PASSOS"," 125 - COPASA","DECAMAO",""],
["","FABRICIO - FEIRA SHOP","9841-0809","AV AMAZONAS"," box 308","CENTRO","CASA: RUA VITORIA"," 145 - NITERÓI"],
["","FAUSTO (PRIMO KAKA)","8481-3369","RUA MARTINS RODRIGUES DE FREITAS"," 91 ","CHACARA",""],
["","FELIPE ALVES IRMAO PRI RABELO","7517-2950","RUA RAMIRO BOTINHA"," 191 - AP 101 ","JARDIM DA CIDADE","CONDOMINIO DO BOSQUE"," BLOCO H- AP 304"],
["","FELIPE BIGODE","9154-9919","AV BANDEIRANTES"," 287 - ATACADÃO DA CERVEJA","CHACARA",""],
["","FELIPE SALAO","9375-9772","R JOÃO DA SILVA SANTOS"," 56","ANGOLA","SALAO PERTO DA ESCOLA NS SENHORA CARMO"],
["","FERNANDA PREDIO KAKA","","RUA SILVA GUIMARÃES"," 275 - AP 501","NOSSA SENHORA DAS GRAÇAS",""],
["","FISIOTERAPIA CHECK UP","3532-2121","AV GOV VALADARES"," 296 - 1º ANDAR","CENTRO",""],
["","FLAVIA DTA","8461-3537","AV  AMAZONAS"," 5051","CACHOEIRA",""],
["","FLAVIA SAJ","9959-9948","SAJ PUC","ANGOLA",""],
["","FORUM - AUDIENCIA - JULIANA","9605-2040","ANTIGA PREFEITURA","CENTRO",""],
["","FULVIO","9245-2982","RUA SÃO JOÃO EVANGELISTA"," 713B","NITEROI","FRENTE A IGREJA DO NITEROI"],
["","GABRIEL -CLINICA DENTARIA","(37)9956-5588","","CENTRO",""],
["","GABRIEL FEIJÃO","9582-2764","RUA ALFA"," 79","BRASILEIA",""],
["","GERALDÃO","9979-0705","LIBERATOS","LIBERATOS",""],
["","GIOMARA - PREFEITURA","9236-1666","PREFEITURA - 3º ANDAR - SECRETARIA DE ESPORTE","BRASILEIA",""],
["","HELENA","9218-9359","RUA CORONEL P SILVA"," 135 - CASA 52","CHACARA","CONDOMINIO CASAS PERTO IGREJA SÃO GERALDO"],
["","HELOISA HELENA SILVA RABELO","9990-2719","RUA SANTA CRUZ"," 17","BRASILEIA","CASA: RUA SANTA CATARINA "," 145 - INGÁCPF:574.346.309-68"],
["","HUGO SALAO MESON","8596-6189","AV NOSSA SENHORA DO CARMo","CENTRO",""],
["","ISABELA MASTER FISIO","9837-5949","AV NOSSA SENHORA DO CARMO"," 704","CENTRO","RUA DOUTOR LEÃO ANTONIO DA SILVA"," 742 - GUARUJÁ"],
["","ISMAEL KENNEDY ACABAMENTOS","9686-5487","AV MARCO TULIO","JARDIM DA CIDADE",""],
["","ITAU GOV VALADARES","3071-3565","AV GOV VALADARES"," 300","CENTRO",""],
["","IVANETE","7584-9272","RUA MARMORE"," 337A SUBINDO A RUA DA SAFRAN","BRASILEIA",""],
["","Ivo André Veículos","9105-1268","AV BANDEIRANTES"," 221","FILADELFIA","ANDRÉ VEÍCULOS"],
["","JANDERSON DENTISTA","2571-0227","RUA NICOLAU ALVES DE MELO","45","CENTRO",""],
["","JESSICA PINHEIRO","8928-0854","RUA SANTA CRUZ"," 510-SALA 303","CENTRO","CASA: RUA CREMERI"," - JD PETROPOLIS RUA DA SORVETERIA E PADARIA"],
["","JOSI","9804-2031","RUA CANDIDO CARDOSO DE MIRANDA"," 71 - AP 101","JARDIM DA CIDADE",""],
["","JOSIANE CHECK UP","9761-7039","","",""],
["","JULIANA CREAS","9787-7729","RUA SANTA CRUZ"," 17","BRASILEIA",""],
["","JULIANA LARA","8845-2454","R INSPETOR JAIME CALDEIRA"," 865 - SALÃO DE BELEZA","BRASILEIA",""],
["","JUSSARA ESPAÇO VISAGE","9146-1550","","",""],
["","JUSSARA JUSTIÇA DO TRABALHO","9198-8252","AV GOV VALADARES"," 356","CENTRO","4ª VARA"],
["","KATIA PARQUE DAS INDUSTRIAS","9503-1136","CONDOMINIO BLOCO 3 AP 202","PARQUE DAS INDUSTRIAS",""],
["","KETLEY","7366-7366","RUA SANTA CRUZ"," 612 - SALA 204 ESQUINA COM RJ","CENTRO",""],
["","KICILA","8856-1358","ACADEMIA VIP FITNESS","BRASILEIA",""],
["","LAHIS PUC","9295-5906","RUA SANTOS DUMOND"," PREDIO 12 - PUC FISIOTERAPIA","ANGOLA","predio 12 - puc fisioterapia - 2º andar"],
["","LAIENNYS","8492-5223","RUA IARA"," 690 - AP 301","SALOMÉ","TRABALHO: ESPAÇO VISAGE"],
["","LAIS SOBRANCELHA","9573-9026","RUA MARIA AUGUSTA DA COSTA"," 475","CACHOEIRA","SALÃO: RUA SÃO SALVADOR"," 944"],
["","LANA - SECRETARIA ESPORTES","9839-2104","SECRETARIA DE ESPORTES - PREFEITURA - 3º ANDAR","BRASILEIA",""],
["","LARISSA OLIVEIRA","9813-1918","RUA DE ESQUINA DA ACADEMIA VIP","BRASILEIA",""],
["","LARISSE CREAS","8906-3639","RUA SANTA CRUZ"," 17","BRASILEIA",""],
["","LEIDIANE AMIGA MARI","9689-7220","RUA PARACATU"," 145 ","BRASILEIA","NA RUA DOS PREDIOS VILLE COLETE"],
["","LELENA MARCÃO","9987-1535","AV SOLIMÕES"," 147 - AP 101","BRASILEIA","OU NA LOJA PRIVILEGE EM FRENTE RODOVIARIO"],
["","LENINHA","8678-9902","AV BELO HORIZONTE"," 1000 - PORTARIA PREDIOS ROSSI","NITEROI",""],
["","LETICIA - JD PETROPOLIS","9100-7901","RUA ANTONIO BERNARDINO COSTA"," 300 - BLOCO 8 - AP201","JARDIM PETROPOLIS","AVENIDA DO CLUBE DA FIAT"],
["","LICIA","9973-6551","RUA FLAVIO SARAIVA"," 22","GUARUJÁ","rua da padaria subindo a hotline"],
["","LILIAN - ABSOLUTA VAIDADE","9787-4080","AV AMAZONAS"," ","CACHOEIRA",""],
["","LILIANA MIRANDA","9961-3640","RUA GERVASIO LARA"," 590 - AP 101","LOJA 1","BRASILEIA",""],
["","LILIANI SOBRANCELHA NAY","9452-4258","AV GOV VALADARES"," 296 - SALA 405","CENTRO","casa: rua quaresmeira"," 399 - alto das flores"],
["","LINDSEY VISAGE","7361-2499","AV AYRTON SENNA"," 596 - INTERFONE 8","PONTE ALTA",""],
["","LOJA JUSSARA MARTINS CENTRO","3531-1198","AV GOVERNADOR VALADARES"," 510","CENTRO",""],
["","LORRAYNE DIAS","9656-5598","RUA AQUEBER SALIBA"," 44 AP 101","CENTRO",""],
["","LUANA IMOBILIARIA","9775-6969","RUA MARECHAL RONDON"," 108","BRASILEIA","rua do raul saraiva"," imobiliaria ao lado da lancho"],
["","LUANA JD BRASILIA","9946-5849","RUA ANTONIO BLEME FILHO"," 368","JARDIM BRASILIA",""],
["","LUCAS - SESI (LIMPEZA)","9282-8329","CLUBE SESI","",""],
["","LUCAS POSTO PANAMERA","7580-9137","AV BANDEIRANTES"," 1155 - POSTO PANAMERA","CHACARA",""],
["","LUCIA ELI","9916-5095","RUA VINTE E SEIS"," 95 - ROTATORIA AV PORTO ALEGRE","BUENO FRANCO",""],
["","LUCIANA SESI","8443-6821","CLUBE SESI","",""],
["","LUDMILA - PENTAGONO CONTABILID","9904-1759","RUA XINGU"," 132","BRASILEIA",""],
["","LUIZ","9703-8440","RUA DO SALAO DO ENCONTRO"," 556","",""],
["","MAICON - KENNEDY ACABAMENTOS","8979-7196","AV MARCO TULIO","JD CIDADE",""],
["","MARCELA INGÁ","9360-0256","RUA VIRIATO BORGES JUNIOR"," 745","INGÁ BAIXO","RUA ABAIXO DA CASTELINHO"],
["","MARCIO BRAGA","9872-7979","R GERVASIO LARA"," 577","BRASILEIA",""],
["","MARGARETH - JUSTIÇA DO TRABALHO","2571-6002","AV GOV VALADARES"," 356","CENTRO","4ª VARA"],
["","MARIA APARECIDA","9664-1193","RUA INSPETOR JAIME CALDEIRA"," 865 - SALA 9","BRASILEIA","PREDIO JULIANA LARA"],
["","MARIA LUCIA","9295-5808","RUA MARIMBÁ"," 14 - DEPOIS DO BAR","SANTA FÉ",""],
["","MARILDA - MAE DEBORA ABRAV","9314-8255","RUA D AMELIA AFEITOS"," 620","NOVO GUARUJÁ",""],
["","MARILEIA ","3511-6292","RUA IDALINA DAMAZIO"," 99 - AP 202","JARDIM DA CIDADE",""],
["","MARILIA MAE JU AMIGA NAY","8531-9221","R ARGENTINA"," 320 -ATRAS POSTO IPIRANGA","NOSSA SENHORA DAS GRAÇAS",""],
["","MARINA - HOSPITAL REGIONAL","9294-1050","HOSPITAL REGIONAL - BLOCO OBSTETRICIA - RAMAL 8204","",""],
["","MARLENE - SALÃO RODRIGO ","8798-1395","RUA DOS ESTADOS"," 165 - SALÃO DO RODRIGO","FILADELFIA",""],
["","MATHEUS FREIRA","9953-6408","RUA CEZARIO PARREIRA"," 345 ","NOVO HORIZONTE","DEPOIS DA RUA TEIXEIRINHA DPS DO SALAO DO ENCONTRO"],
["","MELISSA","3531-3883","GOLD TURISMO"," AV NS CARMO","CENTRO",""],
["","MICHELE DTA","8643-6931","AV  AMAZONAS"," 5051","CACHOEIRA",""],
["","MIRIAM","9612-6321","RUA AQUEBER SALIBA"," 68 AP 302","BRASILEIA",""],
["","MIRLA","8798-3133","AV PERNAMBUCO"," 388","NOSSA SENHORA DAS GRAÇAS"," EM CIMA DA LOJA DAVI ESCAPAMENT"],
["","NATALIA - YOGO MIO","9890-9762","VILA SOLEI - YOGO MIO","INGÁ",""],
["","NAYARA CIDADE VERDE","9452-5712","RUA SIRIUS"," 800","CIDADE VERDE",""],
["","Negão André Veículos","9495-5787","AV BANDEIRANTES"," 221","FILADELFIA","ANDRÉ VEÍCULOS"],
["","NELMA TIA KAKA","8842-3345","RUA FELIPE DOS SANTOS"," 486","CENTRO",""],
["","NILCE","3532-2809","R MILTON VIEIRA PINTO"," 149","ANGOLA ","(RUA CIMA DO BETIM SHOPPING"],
["","NILMARA - HOTEL SESI","9965-6334","HOTEL SESI","",""],
["","ODAISA","8766-0266","GOLD TURISMO"," AV NS CARMO","CENTRO",""],
["","PALOMA","8834-1620","RUA SILVA GUIMARAES - EDIFICIO ALCOBAÇA AP 301","NOSSA SENHORA DAS GRAÇAS",""],
["","PATRICIA - PERTO APOIO","8545-3123","R DISTRITO FEDERAL"," 93 - AP 102","NOSSA SENHORA DAS GRAÇAS",""],
["","PATRICIA VISAGE","7116-9318","AV EDMEIA MATOS LAZAROTTI"," 3791","INGÁ",""],
["","PRISCILA - AMIGA MARIANA ","9254-4233","RUA PROF CLOVIS SALGADO"," 188 - CLINICA GASTROS","CENTRO",""],
["","RAFAELA CHECK ","9715-1382","","",""],
["","RAQUELZINHA","9649-8140","AV TEOTONIO PARREIRA COELHO"," 741 AP 102","JARDIM DA CIDADE",""],
["","REGINA CARTORIO REG IMOVEIS","9113-0415","AV GOV VALADARES"," 510 - 3º ANDAR","CENTRO","CASA: RUA MANOEL DA SILVA PEREIRA"," 58 - BLOCO 8 - AP 201 - CHACARA. CPF: 043.894.866-60"],
["","REJANE","8807-7441","RUA EDICIONINA ANDRE FERREIRA"," 105 AP 403","JARDIM BRASILIA",""],
["","RENATA DUARTE","9843-6248","RUA ANTONIO QUIRINO DA SILVA"," 838","INGÁ BAIXO",""],
["","RENATA SEIVA","8626-6093","RUA RAUL SARAIVA"," 671 - AP 301","GUARUJÁ",""],
["","RENATO COPASA","9929-5156","COPASA DO LADO DA QUADRA DO BOI","ANGOLA",""],
["","RETA SISTEMAS","8786-6830","AV TEOTONIO PARREIRA COELHO"," 805 - SALA 202","JARDIM DA CIDADE",""],
["","RICARDO","7571-1079","","","LIGOU INTERESSADO E NÃO TINHA O HORARIO DISPONIVEL"],
["","RITA","9555-0207","OLIMPIA BUENO FRANCO","122 AP 403","JARDIM DA CIDADE",""],
["","ROBERT","7532-3333","RUA SALVADOR"," 174","INGÁ ALTO","TRABALHO: FORUM 5ª VARA CIVEL - 3º ANDAR"],
["","RODOLFO CHARLIE BROWN","8642-0546","RUA TEREZA MACHADO LAJE (ACADEMIA VIP)","BRASILEIA",""],
["","ROSA","3531-2838","BIBLIOTECA MUNICIPAL - ANTIGO TIRADENTES","ANGOLA",""],
["","ROSALIA","8872-5079","RUA JOAO DA SILVA SANTOS","","ANTES DO SALAO DO ENCONTRO"],
["","ROSANA AGUA DE CHEIRO","3531-3265","AV AMAZONAS"," 1083","CENTRO",""],
["","SABRINHA CHECK UP","9277-8322","","",""],
["","SALÃO DAS MENINAS - PRISCILA","9667-7954","RUA MARCELINA LOPES"," 81 (RUA LATERAL BOMBURGAO)","CENTRO",""],
["","SAPATARIA DA CIDADE","3532-3100","AV GOVERNADOR VALADARES"," 63","CENTRO",""],
["","SETEC","3531-4030","RUA OLIMPIA BUENO FRANCO"," 289","JARDIM DA CIDADE",""],
["","SILVANIA LINGERIE"," 9888-6972","RUA FERNAO DIAS"," 46","BRASILEIA",""],
["","SILVIA CARTORIO IMOVEIS","9462-0537","AV GOV VALADARES"," 510","CENTRO",""],
["","SIOMARA SALAO ESSENCIALE","9436-4594","RUA HENRIQUE MACHADO HORTA"," 93","ANGOLA","EM FRENTE AO GIRASSOL"],
["","SOL - UBIRAJARA JOIAS","8659-7620","AV AMAZONAS"," 823","CENTRO",""],
["","Soraia Predio Kaka","9487-6945","RUA SILVA GUIMARÃES"," 275 ","NOSSA SENHORA DAS GRAÇAS",""],
["","SUELI PREFEITURA SEMAS","3531-4964","ENTRADA RUA PARA DE MINAS PREFEITURA","BRASILEIA","predio a direita de tijolinho"," 1ª porta a esquerda"],
["","SUZY CERIMONIAL","8626-8988","RUA CANDIDA CARDOSO DE MIRANDA"," 266 - AP 301","JARDIM DA CIDADE",""],
["","SUZY RUA LIVRAO","9114-8524","RUA ARECLIDES PINHO ANGELO"," 16"," AP 502 ","CENTRO",""],
["","TAIS - CETAP","9687-0270","RUA RITA MARIA"," 31","ANGOLA",""],
["","TAMARA ","9910-8012","LOJA RICARDO ELETRO","CENTRO",""],
["","TASSIA - FORUM VARA DA FAMILIA","9897-6664","RUA PROFESSOR OSVALDO FRANCO - 2 ANDAR ","CENTRO",""],
["","THIAGO LEGADO","8427-9504","P&B - RUA SETEC","JARDIM DA CIDADE",""],
["","Tião André Veículos","9668-0934","AV BANDEIRANTES"," 221","FILADELFIA","ANDRÉ VEÍCULOS"],
["","VAGNER SORVETERIA BIQUINHA","9384-9822","AV AMAZONAS"," 422 ","CENTRO","em frente ao boticario CENTER GONTIJO"],
["","VALERIA CARTORIO ROBERTO SILVA","","AV NS CARMO","CENTRO","CARTORIO ROBERTO SILVA"],
["","Valter André Veículos","9436-8708","AV BANDEIRANTES"," 221","FILADELFIA","ANDRÉ VEÍCULOS"],
["","VANESSA","8885-7025","RUA DO ACRE"," 195","NOSSA SENHORA DAS GRAÇAS","depois do deposito alencar"],
["","VANIA IPREMB","9172-4811","AV AMAZONAS"," 1345"," 5º ANDAR","CENTRO",""],
["","VLADIMIR","8735-3645","AV GOV VALADARES"," 300","CENTRO","banco itau - gerencia"],
["","WANESSA MIRANDA ","9775-8489","RUA GERVASIO LARA"," 590 - AP102","BRASILEIA",""],
["","WESLEY - LANO PNEUS","7109-4682","RUA PEDRO DA SILVA FORTES"," 78","ANGOLA",""],
["","JIANE","9519-7118","RUA CAIO MARTINS"," 758","FILADELFIA",""],
["","EDVALDO","9725-2120","CARTORIO ROBERTO SILVA","CENTRO",""]
];

		clientes.forEach(function(cliente){
			browser.driver.get('http://localhost:7171/customer/list');
			browser.driver.sleep(3000);
			//browser.driver.findElement(by.xpath('/html/body/div[1]/div[5]/div/div/ul[1]/li[4]/a')).click();
			browser.driver.findElement(by.xpath('//*[@id="main"]/div[1]/a')).click();

			browser.driver.findElement(by.xpath('//*[@id="user_form"]/span/span[1]/div[2]/div/div/div[1]/input[1]')).sendKeys(cliente[1].replace("(",' ').replace(')', '').replace('*', ' funcionario').replace("-", '') );

			for(var i = 0;i<30; i++)
				browser.driver.findElement(by.xpath('//*[@id="phone"]')).sendKeys(protractor.Key.BACK_SPACE);
			
			browser.driver.findElement(by.xpath('//*[@id="phone"]')).sendKeys(("31")+cliente[2]);

			browser.driver.findElement(by.xpath('//*[@id="street"]')).sendKeys(cliente[3]+cliente[4]);
			browser.driver.findElement(by.xpath('//*[@id="obs"]')).sendKeys(cliente[5]+cliente[6]);
			browser.driver.findElement(by.xpath('//*[@id="main"]/div[1]/form/div[1]/input')).click();
			browser.driver.sleep(300);
		});
		browser.driver.sleep(300);
	//});
});