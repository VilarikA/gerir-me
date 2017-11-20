NotificationController = function($scope, $http){
	$scope.notifications = [];
	$scope.updateList = function() {
		$http.get("/notification/message").success(function(ret){
			eval("ret="+ret);
			$scope.notifications = ret;
			//$scope.$apply(); 
		}).error(function(e){
			console.log(e);
		});
	}
	$scope.updateList();
	$scope.edit = function(id){
		window.location.href="/customer/notification_create?id="+id;
	};
	$scope.remove = function(notification){
		if(confirm("Tem certeza que deseja excluir a notificação")){
			$http.delete("/notification/message/"+notification).success(function(){
				alert("Excluído com sucesso!");
				$scope.updateList();
			}).error(function(){
			});
		}
	};
	$scope.sendToYou = function(message){
		$http.get("/notification/messagesend/company/"+message).success(function(){
			alert("Enviado com sucesso!");
			$scope.updateList();
		}).error(function(){
		});
	};
	$scope.sendToAll = function(message){
		if(confirm("Tem certeza que deseja enviar a notificação para todos cliente?")){
			$http.get("/notification/messagesend/all/"+message).success(function(){
				alert("Enviado com sucesso!");
				$scope.updateList();
			}).error(function(){
			});
		}
	};
	$scope.save = function(){
		$http.post()
	};
}