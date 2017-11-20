describe('Vamos ver...', function() {
	//it('load page', function(){
		browser.driver.get('http://ebelle.vilarika.com.br');
		browser.driver.findElement(by.id('_userForLogin')).sendKeys('mateus');
		browser.driver.findElement(by.id('_passwrodForLogin')).sendKeys('775072');
		browser.driver.findElement(by.id('_companyForLogin')).sendKeys('66');
		browser.driver.findElement(by.id('_btnIn')).click();
		browser.driver.sleep(1000);
		browser.driver.switchTo().alert().then(
		    function (alert) { alert.dismiss(); },
		    function (err) { }
		);
		var contas = [
		//disse que nao tem ["ALESSANDRA CANDIDO", "05-01-2015", "A.ESCOVA (28.00) / A.MÃO (15.00) / PÉ (16.00)", "59.00", "3477", "Débito (F)", "3389"],
		//["INGRID *", "05-01-2015", "SPRAY DE UNHA (15.90)", "15.90", "3480", "Débito (F)", "3391"],
		["CLAUDIA SENA", "03-01-2015", "A.MÃO (15.00) / A.MÃO (18.00)", "33.00", "3432", "Débito (F)", "3344"],
		["ELAINE LAPORAIS", "03-01-2015", "A.ESCOVA (25.00)", "25.00", "3446", "Débito (F)", "3358"],
		["ARIANE GONÇALVES", "03-01-2015", "A.MÃO (15.00)", "15.00", "3457", "Débito (F)", "3370"],
		["LUCIMAR PEREIRA", "03-01-2015", "A.ESCOVA (18.00) / A.MÃO (15.00)", "33.00", "3463", "Débito (F)", "3376"],
		//["ISABEL (BEL)", "03-01-2015", "A.MÃO (15.00) / PÉ (16.00)", "29.00", "3464", "Abater no Crédito", "3377"],
		["ISABEL (BEL)", "03-01-2015", "A.MÃO (15.00) / PÉ (16.00)", "2.00", "3465", "Débito (F)", "3377"],
		["FERNANDA ANDRADE (ZAIRA)", "02-01-2015", "RESTANTE DE CONTA PASSADA (246.00)", "246.00", "3431", "Débito (F)", "3343"],
		["ELAINE LAPORAIS", "31-12-2014", "A.PE E MÃO (31.00) / A.DEPILAÇÃO (10.00)", "41.00", "3379", "Débito (F)", "3298"],
		["LÚ PENA", "31-12-2014", "A.ESCOVA (28.00) / A.PE E MÃO (30.00)", "58.00", "3380", "Débito (F)", "3299"],
		["ANDREIA CIRIACO", "31-12-2014", "A.ESCOVA (25.00) / A.PE E MÃO (30.00) / A.SOBRANCELHA (25.00) / A.DEPILAÇÃO (10.00)", "90.00", "3389", "Débito (F)", "3306"],
		["ANDREIA CIRIACO", "31-12-2014", "A.SOBRANCELHA (25.00) / A.DEPILAÇÃO (10.00)", "35.00", "3391", "Débito (F)", "3308"],
		["DOMITILA", "31-12-2014", "A.ESCOVA (28.00) / PÉ (16.00) / A.MÃO (15.00) / SPA DOS PÉS (40.00)", "99.00", "3412", "Débito (F)", "3326"],
		["LUCIMAR PEREIRA", "31-12-2014", "A.ESCOVA (18.00)", "18.00", "3418", "Débito (F)", "3332"],
		//["ANA CAROLINA LEAO", "30-12-2014", "A.MÃO (15.00)", "15.00", "3314", "Abater no Crédito", "3240"],
		["PRISCILA RENATA", "29-12-2014", "A.MÃO (45.00) / A.MÃO (15.00)", "30.00", "3255", "Débito (F)", "3185"],
		["REGINA VENTURA", "29-12-2014", "MECHAS (100.00)", "100.00", "3277", "Débito (F)", "3207"],
		["ANDREIA CIRIACO", "27-12-2014", "A.ESCOVA (25.00) / APLI . COLO (28.00) / A.REDUTORA PROGRESSIVA (110.00) / A.PE E MÃO (30.00)", "193.00", "3205", "Débito (F)", "3136"],
		["CLAUDIA SENA", "27-12-2014", "A.ESCOVA (20.00) / A.PE E MÃO (32.00)", "52.00", "3239", "Débito (F)", "3170"],
		["KELY RESENDE", "26-12-2014", "A.PE E MÃO (17.00) / A.PE E MÃO (16.00)", "3.00", "3165", "Débito (F)", "3096"],
		//["MARCIA RESENDE", "26-12-2014", "A.PE E MÃO (32.00)", "32.00", "3166", "Abater no Crédito", "3097"],
		["LUCIMAR PEREIRA", "24-12-2014", "A.ESCOVA (18.00) / A.PE E MÃO (30.00)", "48.00", "3175", "Débito (F)", "3106"],
		["DOMITILA", "26-12-2014", "A.ESCOVA (28.00) / A.MÃO (20.00)", "48.00", "3193", "Débito (F)", "3124"],
		["LUCIMAR PEREIRA", "24-12-2014", "A.ESCOVA (23.00)", "23.00", "3085", "Débito (F)", "3018"],
		//["ISABEL (BEL)", "24-12-2014", "A.MÃO (15.00)", "15.00", "3145", "Abater no Crédito", "3076"],
		//["MARCIA RESENDE", "24-12-2014", "A.PE E MÃO (32.00)", "32.00", "3150", "Abater no Crédito", "3081"],
		["DOMITILA", "24-12-2014", "A.ESCOVA (28.00) / PÉ (10.00) / A.MÃO (15.00)", "53.00", "3154", "Débito (F)", "3085"],
		//["INATIVO", "23-12-2014", "A.MÃO (15.00)", "15.00", "3049", "Abater no Crédito", "2985"],
		["FERNANDA SOUZA", "23-12-2014", "A.ESCOVA (28.00)", "28.00", "3061", "Débito (F)", "2995"],
		["ANA CAROLINA LEAO", "23-12-2014", "A.PE E MÃO (31.00)", "31.00", "3068", "Débito (F)", "3002"],
		["JEANE ALVES LONGUINHOS", "22-12-2014", "A.COLORAÇAO (100.00) / A.PE E MÃO (30.00)", "130.00", "3022", "Débito (F)", "2961"],
		//["SIMONE QUEIROS", "22-12-2014", "H.BOTOX (60.00)", "60.00", "3023", "Abater no Crédito", "2962"],
		["DOMITILA", "20-12-2014", "A.ESCOVA (25.00) / A.PE E MÃO (30.00)", "55.00", "2894", "Débito (F)", "2836"],
		["ZAIRA STEFANIA", "20-12-2014", "A.PE E MÃO (30.00)", "30.00", "2900", "Débito (F)", "2842"],
		["LÚ PENA", "20-12-2014", "A.ESCOVA (28.00) / A.MÃO (15.00)", "43.00", "2932", "Débito (F)", "2873"],
		["ZAIRA STEFANIA", "20-12-2014", "SHAMPOO LOREAL 500 ML (165.00)", "165.00", "2934", "Débito (F)", "2875"],
		["LUCIMAR PEREIRA", "20-12-2014", "A.ESCOVA (18.00) / A.MÃO (15.00)", "33.00", "2941", "Débito (F)", "2882"],
		["CLAUDIA SENA", "20-12-2014", "A.ESCOVA (20.00) / A.MÃO (15.00) / PÉ (16.00)", "51.00", "2958", "Débito (F)", "2896"],
		["ELAINE LAPORAIS", "19-12-2014", "A.ESCOVA (25.00) / A.PE E MÃO (31.00)", "56.00", "2867", "Débito (F)", "2809"],
		["LUCIMAR PEREIRA", "18-12-2014", "A.ESCOVA (23.00) / APLI . COLO (28.00)", "51.00", "2807", "Débito (F)", "2750"],
		["ELAINE LAPORAIS", "16-12-2014", "A.ESCOVA (25.00)", "25.00", "2783", "Débito (F)", "2727"],
		["CLAUDIA SENA ( LORAO)", "14-12-2014", "A.ESCOVA (20.00) / A.PE E MÃO (30.00)", "50.00", "2729", "Débito (F)", "2676"],
		["DOMITILA", "14-12-2014", "A.ESCOVA (28.00) / A.PE E MÃO (30.00) / CHINELO (10.00)", "68.00", "2730", "Débito (F)", "2677"],
		//["ANA CAROLINA LEAO", "13-12-2014", "A.ESCOVA (20.00) / A.ESCOVA (45.00) / OLEO WELLA (120.00) / A.PE E MÃO (30.00)", "30.00", "2683", "Abater no Crédito", "2629"],
		//["ISABEL (BEL)", "13-12-2014", "A.MÃO (15.00) / PÉ (16.00)", "31.00", "2717", "Abater no Crédito", "2663"],
		["REGINA VENTURA", "11-12-2014", "RELAXAMENTO APLIC (110.00) / A.CORTE (20.00)", "130.00", "2605", "Débito (F)", "2555"],
		["ANA CAROLINA LEAO", "06-12-2014", "A.MÃO (16.00)", "16.00", "2476", "Débito (F)", "2429"],
		["DOMITILA", "04-12-2014", "A.ESCOVA (28.00)", "28.00", "2414", "Débito (F)", "2367"],
		["ELAINE LAPORAIS", "05-12-2014", "A.ESCOVA (25.00)", "25.00", "2439", "Débito (F)", "2391"],
		["ELAINE LAPORAIS", "18-10-2014", "A.ESCOVA (25.00)", "25.00", "2320", "Débito (F)", "2275"],
		["ELAINE LAPORAIS", "30-11-2014", "A.ESCOVA (25.00) / A.DEPILAÇÃO (10.00)", "35.00", "2321", "Débito (F)", "2276"],
		["ELAINE LAPORAIS", "22-11-2014", "A.ESCOVA (25.00)", "25.00", "2322", "Débito (F)", "2277"],
		["JEANE ALVES LONGUINHOS", "27-11-2014", "A.PROGRESSIVA (170.00) / A.MÃO (15.00) / A.DEPILAÇÃO (38.00)", "223.00", "2174", "Débito (F)", "2132"],
		["ELAINE LAPORAIS", "22-11-2014", "PÉ (15.00)", "15.00", "2062", "Débito (F)", "2021"],
		["ELAINE LAPORAIS", "21-11-2014", "A.ESCOVA (25.00)", "25.00", "1995", "Débito (F)", "1957"],
		["ELAINE LAPORAIS", "14-11-2014", "A.ESCOVA (25.00)", "25.00", "1801", "Débito (F)", "1764"],
		["ELAINE LAPORAIS", "14-11-2014", "A.ESCOVA (25.00) / A.DEPILAÇÃO (10.00) / LANXE (5.50)", "40.50", "1847", "Débito (F)", "1808"],
		["ELAINE LAPORAIS", "08-11-2014", "A.ESCOVA (25.00)", "25.00", "1724", "Débito (F)", "1692"],
		//["SIMONE QUEIROS", "09-11-2014", "H.BOTOX (60.00)", "60.00", "1732", "Abater no Crédito", "1700"],
		["WALACE", "08-11-2014", "CARBOX (65.00) / UMIDIFICADOR DE CACHOS (59.00)", "124.00", "1654", "Débito (F)", "1623"],
		["PATRICIA CORREIA", "05-11-2014", "A.MÃO (15.00) / A.MÃO (15.00)", "30.00", "1537", "Débito (F)", "1507"],
		["ELAINE LAPORAIS", "01-11-2014", "A.PE E MÃO (30.00)", "30.00", "1441", "Débito (F)", "1415"],
		["ELAINE LAPORAIS", "01-11-2014", "A.ESCOVA (25.00) / PÉ (16.00)", "41.00", "1443", "Débito (F)", "1417"],
		["ELAINE LAPORAIS", "24-10-2014", "A.ESCOVA (25.00)", "25.00", "1209", "Débito (F)", "1187"],
		["SILVIA QUEIROZ", "25-10-2014", "A.ESCOVA (30.00)", "3.00", "1246", "Débito (F)", "1224"],
		["ELAINE LAPORAIS", "25-10-2014", "A.ESCOVA (25.00) / A.SOBRANCELHA (30.00) / A.DEPILAÇÃO (10.00)", "65.00", "1268", "Débito (F)", "1244"],
		["GABRIELA ALVES", "23-10-2014", "PROGRESSIVA APLIC (300.00) / A.MÃO (16.00)", "300.00", "1152", "Débito (F)", "1133"],
		["ELAINE LAPORAIS", "18-10-2014", "A.ESCOVA (25.00)", "25.00", "1055", "Débito (F)", "1040"],
		["MELISSA DRUMON", "07-10-2014", "A.ESCOVA (28.00) / KIBE \ SFIHA (6.00) / MINI REFRI (2.50)", "3.50", "642", "Débito (F)", "643"],
		["ELAINE LAPORAIS", "03-10-2014", "A.ESCOVA (25.00) / A.PE E MÃO (31.00)", "56.00", "520", "Débito (F)", "526"],
		["NATÁLIA MARTINS", "02-10-2014", "3ª E 4ª PROMOÇÃO (140.00)", "140.00", "448", "Débito (F)", "453"],
		["PRISCILA ARIANE (JISIVONE)", "02-10-2014", "A.CORTE (35.00)", "70.00", "449", "Débito (F)", "454"],
		["RAQUEL ROCHA", "17-04-2014", "A.DEPILAÇÃO (17.00)", "17.00", "451", "Débito (F)", "456"],
		["ELAINE LAPORAIS", "02-10-2014", "3ª E 4ª PROMOÇÃO (1382.50)", "1,382.50", "455", "Débito (F)", "460"],
		["KARLA GERKEN", "02-10-2014", "3ª E 4ª PROMOÇÃO (224.00)", "224.00", "456", "Débito (F)", "461"],
		];
		browser.driver.findElement(by.xpath('/html/body/div[1]/div[5]/div/div/ul[1]/li[7]/a')).click();
		browser.driver.get('http://ebelle.vilarika.com.br/financial_cashier/register_payment');
		browser.driver.sleep(10000);
		browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[1]/div[1]/div[1]/div')).click();
		browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[1]/div[1]/div[1]/div/div/div/ul/li[4]')).click();

		contas.forEach(function(conta){
			
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[1]/div[2]/div[2]/div/div/span[1]/div')).click();
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[1]/div[2]/div[2]/div/div/span[1]/div/div/div/input')).sendKeys(conta[0].replace("(",' ').replace(')', '').replace('*', ' funcionario'));

			browser.driver.sleep(1000);
			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[1]/div[2]/div[2]/div/div/span[1]/div/div/ul/li')).click();
			browser.driver.sleep(1000);
			browser.driver.switchTo().alert().then(
			    function (alert) { alert.accept(); },
			    function (err) { }
			);
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[2]/div[1]/div[1]/div/div/div')).click();
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[2]/div[1]/div[1]/div/div/div/div/ul/li[12]')).click();
			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[2]/div[1]/div[3]/div[1]/div/div/div[1]/div/a')).click();
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[2]/div[1]/div[3]/div[1]/div/div/div[1]/div/div/div/input')).sendKeys("migracao deve");
			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="cash_form"]/div[2]/div[1]/div[3]/div[1]/div/div/div[1]/div/div/ul/li')).click();
			browser.driver.sleep(300);
			browser.driver.findElement(by.xpath('//*[@id="add_prduct"]/img')).click();
			browser.driver.sleep(1000);
			//browser.driver.findElement(by.xpath('//*[@id="treatments_details"]/tbody/tr/td[4]/input')).clear();
			browser.driver.findElement(by.xpath('//*[@id="treatments_details"]/tbody/tr/td[4]/input')).sendKeys(protractor.Key.BACK_SPACE);
			browser.driver.findElement(by.xpath('//*[@id="treatments_details"]/tbody/tr/td[4]/input')).sendKeys(protractor.Key.BACK_SPACE);
			browser.driver.findElement(by.xpath('//*[@id="treatments_details"]/tbody/tr/td[4]/input')).sendKeys(protractor.Key.BACK_SPACE);
			browser.driver.findElement(by.xpath('//*[@id="treatments_details"]/tbody/tr/td[4]/input')).sendKeys(protractor.Key.BACK_SPACE);
			browser.driver.findElement(by.xpath('//*[@id="treatments_details"]/tbody/tr/td[4]/input')).sendKeys(conta[3]);

			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="b_payment"]')).click();
			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="payment_form"]/div[2]/form/fieldset/div/div/div/div')).click();
			browser.driver.findElement(by.xpath('//*[@id="payment_form"]/div[2]/form/fieldset/div/div/div/div/div/ul/li[4]')).click();
			browser.driver.executeScript(' $(".b_payment_finalize:first").click();');
			browser.driver.sleep(1000);
			browser.driver.switchTo().alert().then(
			    function (alert) { alert.accept(); },
			    function (err) { }
			);
			browser.driver.sleep(1000);
			browser.driver.switchTo().alert().then(
			    function (alert) { alert.accept(); },
			    function (err) { }
			);
			browser.driver.sleep(1000);
		});
		browser.driver.sleep(3000);
	//});
});