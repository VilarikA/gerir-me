
var EbelleApp =angular.module('EbelleApp',["ngResource", "ngRoute",'br','ui.select2','ui.date','$strap.directives']);
EbelleApp.controller('App', ["$resource","$route", "$routeParams", "$location","$scope", "$http","$strapConfig", function ($resource,$route, $routeParams, $location, $scope, $http, $strapConfig) {
	Util.getLocalDataSource('json/app.json', $http,function(appData){
		$scope.app = appData;
	});
	$strapConfig.timepicker = {
		showSeconds : false,
		showMeridian : false 
	};
}]); 
EbelleApp.config(['$routeProvider',function($routeProvider){
	$routeProvider.when('', {
		templateUrl: '/form.html'
	}).when('/user', {
		templateUrl: 'pages/user/main.html'
	}).otherwise({
		redirectTo: '/user'
	});	
}]);
EbelleApp.filter('column_filter', function () {
	return function (item, type, params) {
		var result = null;
		if(type === 'weekday'){
			result = Util.weekDayFilter(item);
		}else if(type === 'boolean'){
			result = Util.booleanFilter(item);
		}else{
			result = item;
		}
		return result;
	}; 
});
EbelleApp.directive('ebSelect', [function () {
	return {
		scope: true,
		require: 'ngModel',
		link: function (scope, $element, iAttrs) {
			DataManager.getSelectDataSource(iAttrs.url, function(items){
				scope.items = items;
				scope.$apply();
			});
		}
	};
}]);