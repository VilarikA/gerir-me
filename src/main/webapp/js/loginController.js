var loginModule  = angular.module('LoginModule',['br', 'LocalStorageModule']);
var LoginController = function($scope, $window, $http, LocalStorage){

	/*
	 * Handle success login
	 */
	var onLoginSuccess = function(response)
	{
		eval("response = " + response);

		if( ! response.success)
			return alert(response.message);

		storeMenus(response.menus);

		var companies = response.companys;
		if( isThereSingleCompany(companies) ){
			document.location.href = response.goTo;
		} else {
			$scope.companys = companies;
		}
	}

	function isThereSingleCompany(companies)
	{
		return companies.length == 1;
	}

	function storeMenus(menus)
	{
		// Temporally variable
		menus = [{
			icon: "calendar",
			label: "Profissionais",
			children: [{
				label: "Profissionais",
				url: "/user/list"
			}, 
			{
				label: "Grupos",
				url: "/group/list"
			}]
		},
		{
			icon: "list",
			label: "Serviços",
			children: [{
				label: "Serviços",
				url: "/activity/list"
			},
			{
				label: "Tipos",
				url: "/activity/list_type"
			}]
		}];

		LocalStorage.set("menus", menus);
	}

	var loginError = function(r){
		alert("Erro ao efetuar login, tente novamente!");
	};

	//
	//  Rigel - 03/03/2017
	//  ALTERACAO DE VERSAO DO APLICATIVO DEVE SER FEITA no face_prepare.js
	//
	$scope.loginWithFacebook = function(){
		var face = new EbelleFacebook();
		face.getUserByFacebook().then(function(userData){
			if(userData){//&& userData.length === 1
				var user = userData[0];
				$http.post("/security/login_face", {company : user.company, id: user.id, facebookId : user.facebookId, facebookAccessToken : user.facebookAccessToken}).success(loginSoccess).error(loginError);
			}
		}).then(null,function(){
			alert('Erro ao fazer login com facebook, tente novamente!');
		});
	};

	$scope.loginWithCompany = function(company, email, password){
		$http.post("/security/login_email", {company : company,email : email, password : password, hasCompany : true}).success(loginSoccess).error(loginError);
	};

	$scope.login = function(email, password){
		var loginData = {
			company: "",
			email: email,
			password: password,
			hasCompany: false
		};

		$http.post("/security/login_email", loginData)
			.success(onLoginSuccess)
			.error(loginError);
	};

	$scope.rememberPassword = function(email){
		var emailValidation = /^([a-z0-9._%\-+]+@(?:[a-z0-9\-]+\.)+[a-z]{2,4}$)/;
		if (!emailValidation.test(email) && email.length > 10){
			alert ('E-mail inválido!\n')
			return;
		} else if (email.indexOf("@") <= 1) {
			alert ('E-mail inválido!\n')
			return;
		} else {
			//alert ('E-mail ok!\n')
		}
		$http.post("/security/remember_password", {email : email}).success(function(results){
	        if(results === 1 || results == "1"){
				alert("Enviado com sucesso para " + email);
	        }else{
	          alert(eval(results));
	        }
		}).error(function(){
			alert("Erro ao processar requisição!");
		});	
	}
};
