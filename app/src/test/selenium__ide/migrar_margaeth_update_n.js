describe('Vamos ver...', function() {
	//it('load page', function(){
		browser.driver.get('http://ebelle.vilarika.com.br');
		browser.driver.findElement(by.id('_userForLogin')).sendKeys('mateus');
		browser.driver.findElement(by.id('_passwrodForLogin')).sendKeys('775072');
		browser.driver.findElement(by.id('_companyForLogin')).sendKeys('66');
		browser.driver.findElement(by.id('_btnIn')).click();
		browser.driver.sleep(2000);
		browser.driver.switchTo().alert().then(
		    function (alert) { alert.dismiss(); },
		    function (err) { }
		);		
		var clientes = [
		["585","TAUANA GERKEN","(31) 8674-9082","",""],			
		];

		clientes.forEach(function(cliente){
			browser.driver.findElement(by.xpath('/html/body/div[1]/div[5]/div/div/ul[1]/li[3]/a')).click();
			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="main"]/div[2]/span/form/div/div/div/span/div')).click();
			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="main"]/div[2]/span/form/div/div/div/span/div/div/div/input')).sendKeys(cliente[1].replace("(",' ').replace(')', '').replace('*', ' funcionario').replace('.','').replace('-','').replace('/','') );
			browser.driver.sleep(1000);
			browser.driver.findElement(by.xpath('//*[@id="main"]/div[2]/span/form/div/div/div/span/div/div/ul/li[1]')).click();
			browser.driver.sleep(1000);
			browser.driver.switchTo().alert().then(
			    function (alert) { alert.dismiss(); },
			    function (err) { }
			);			
			//
			for(var i = 0;i<30; i++)
				browser.driver.findElement(by.xpath('//*[@id="phone"]')).sendKeys(protractor.Key.BACK_SPACE);
			
			browser.driver.findElement(by.xpath('//*[@id="phone"]')).sendKeys(cliente[2]);
			browser.driver.findElement(by.xpath('//*[@id="main"]/div[1]/form/div[1]/input')).click();			
			browser.driver.sleep(300);
		});
		browser.driver.sleep(300);
	//});
});