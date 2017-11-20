var RegisterController = function($scope, $http){
	$scope.showRegisterForm = true;
	$scope.registerCompany = function(company){
		$http.post("/api/company/registry", company).success(function(r){
			if(!isNaN(r)){
				alert("Registro efetuado com sucesso! Você receberá um e-mail com as instruções para acesso!");
			}else{
				alert(eval(r));
			}
			$scope.showRegisterForm = false;
		}).error(function(){
			alert("Erro ao cadastrar, verifique os dados e tente novamente!");
		});		
	};
};