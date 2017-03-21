var i18n_days_obj = {Mon:"Segunda",Tue:"Terça",Wed:"Quarta",Thu:"Quinta",Fri:"Sexta",Sat:"Sábado",Sun:"Domingo",All:"Todos",Wordays:"Dias de Semana"};;var CrudFactory = function(params){
    var OVERRIDE = {'edit' : true, 'save' : true, '$scope' : true};
    var optionsActions = {
      save : {
        method:'POST',
        transformResponse : function(a){
         return Util.jsonParse(a);
        }
      },
      get : {
        transformResponse : function(a){
         return Util.jsonParse(a);
        }        
      },
      query : { 
        method : "GET",
        isArray:true, 
        transformResponse : function(a){
         return Util.jsonParse(a);
       }
     },
     'delete': {
        method:'GET',
      url : params.collectionUrl+"/delete"
      }
    };
  var options = {
    useRouteParameter : false,
    name : "",
    columns : [],
    defaultValues : {},
    url : "",
    collectionUrl : "",
    collectionParams : { query : "@query" },
    actions : {},
    onSelectObj : function(obj){},
    extend : function(){},
    userGridAction: false
  };
  angular.extend(options, params);
  angular.extend(optionsActions, options.actions);
      var controller = EbelleApp.controller(options.name,["$resource","$route", "$routeParams", "$location", "$scope","$q", "$http",function($resource, $route, $routeParams, $location,$scope, $q, $http){
          $scope.$route = $route;
          $scope.controllerName = options.name;
          $scope.$location = $location;
          $scope.$routeParams = $routeParams;
          $scope.columns = options.columns;
          $scope.userGridAction = options.userGridAction;
          if(options.extend){
            options.extend.$scope = $scope;
          }
          this.$scope = $scope;
          var runtimeCollectionParams = {};
          (function(){
            for(var prop in options.collectionParams){
              var val = options.collectionParams[prop];
              if(angular.isFunction(val)){
                val = val();
              }
              if(val){
                runtimeCollectionParams[prop] = val;
              }
            }
          }());
          var hasUrlCollection = options.collectionUrl !== "";
          var updateList = function(options){
              if(hasUrlCollection){
                $scope.list = crudCollections.query(options, function(){});
              }
          };
          var newRow = function(){
            var newObj = options.defaultValues;
            for(var prop in runtimeCollectionParams){
              if(!angular.isString(runtimeCollectionParams[prop]) || runtimeCollectionParams[prop].indexOf('@') !== 0){
                newObj[prop] = runtimeCollectionParams[prop];
              }
            }
            $scope.current = new crudCollections(newObj);
          };
          var crudCollections =  $resource(options.collectionUrl, runtimeCollectionParams, optionsActions);
          this.crudCollections = crudCollections;
          var crudResource = $resource(options.url+"/:id/",{id:'@id'},{update: { method: 'POST' },  get : {
                                          method:'GET',
                                          transformResponse : function(a){
                                           return Util.jsonParse(a);
                                          }        
                                      }});
          this.crudResource = crudResource;
          $scope.$watch('current', function(obj){
            options.onSelectObj(obj);
          });
          if(options.useRouteParameter && $routeParams.id){
            $scope.current = crudResource.get({"id" :  $routeParams.id});
          }else{
            updateList();
            newRow();
          }
          $scope.save = function(obj){
            var saveClojure = function(obj, callback, listUpdate){
              //obj = angular.copy(obj);
              if(obj){
                obj.$save().then(function(resp){
                  if(callback){
                    callback(resp);
                  }
                  if(listUpdate){
                    updateList();
                  }
                })['catch'](function(message){
                    alert(message.data);
                });
              }else{
                updateList();
              }
            };
            if(options.extend.save){
              options.extend.save(obj, saveClojure, $q, $http);
            }else{
              saveClojure(obj);
            }
          };

          $scope.edit = function(obj){
            var copyObj = angular.copy(obj);
            var editClojure = function(copyObj){
              $scope.current = copyObj;
            };
            if(options.extend.edit){
              options.extend.edit(copyObj, $scope.list, editClojure);
            }else{
              editClojure(copyObj);
            }
          };

          $scope.remove = function(obj){
           obj.$delete().then(function(){
            updateList();
          });
         };
        var extend = function(src){
            for(var method in src){
              if(!OVERRIDE[method]){
                $scope[method] = src[method]($scope, crudResource, crudCollections);
            }
          }
        };
        extend(options.extend);
    }]);
    return controller;
 };;var DataManagerClass = function(){
	var datas = [];
	this.getSelectDataSource = function(url_source, callback){
		if(datas[url_source]){
			callback(datas[url_source]);
		}else{
			$.get(url_source, function(t){
				datas[url_source] = Util.jsonParse(t);
				callback(datas[url_source]);
			});
		}
	};
};
var DataManager = new DataManagerClass();;var Util = {
	jsonParse : function(strJson){
		if(strJson.lastIndexOf(';') === (strJson.length-1)){
			strJson = strJson.slice(0, -1);	
		}
		return JSON.parse(strJson);	
	},
	getLocalDataSource : function(url, $http, callback){
		var data = localStorage.getItem(url); 
		if(data){
			data = JSON.parse(data);
			callback(data);
		}else{
			$http.get(url).success(function(data){
				localStorage.setItem(url, JSON.stringify(data));
				callback(data);
			});
		}
	},
	weekDayFilter : function(day){
		return i18n_days_obj[day];
	},
	booleanFilter : function(value){
		return value ? 'Sim' : 'Não';
	},
	getUrlParameter : function(name){
		var searchString = window.location.href.split('?')[1];
		if(searchString){
			var variableArray = searchString.split('&');
			var results = [];
			var result = "";
			for(var i = 0; i < variableArray.length; i++){
				var keyValuePair = variableArray[i].split('=');
				if(keyValuePair[0] == name){
					results.push(decodeURIComponent(keyValuePair[1]).replace("+", " "));
				}
			}
			if(results.length ==1){
				return results[0];
			}else if(results.length > 1 ){
				result= results;
			}
			return result;			
		}else {
			return false;
		}
	}
};;
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
}]);;var WorkhourControllerExtended = {
	save : function(obj, parrent, Q, http){
		var promises = [];
		obj.days.forEach(function(day){
			var toSave = angular.copy(obj);
			toSave.day = day;
			promises.push(toSave.$save());
		});
		Q.all(promises).then(function(){
			parrent(null);
			var date = new Date();
			http.post("/calendar/clearBusyEventByUser?user="+UserControllerExtended.$scope.current.id).then(function(){
				var dateStartUrl = encodeURIComponent(date.getDateBr());
				var dateEndUrl = encodeURIComponent(date.getNextMonth().getDateBr());
				var unit = UserControllerExtended.$scope.current.unit;
				http.get("/calendar/freebusys/"+dateStartUrl+"/"+dateEndUrl+"?unit="+unit);
			});
		});
	},
	edit : function(obj, list, parrent){
		obj.days = [obj.day];
		parrent(obj);
	}
};
var UserActivityControllerExtended = { 
	doAllApplay : function($scope){ 
		return function(doAll){
			$scope.list.forEach(function(item){ 
				item.enabled = doAll; 
				item._changed = true;
			});
		};
	},
	applayPercernt : function($scope, crudResource, crudCollections){
		return function(percentCategory){
			$scope.list.forEach(function(item){
				item.commission = percentCategory;
				item.enabled = true;
				item._changed = true;
			});
		};
	},
	findByCategory : function($scope, crudResource, crudCollections){
		return function(category){
			$scope.list = crudCollections.findByCategory({category : category});
		};
	},
	saveChanged : function($scope, crudResource, crudCollections){
		return function(){
			var lastPromese = null;
			$scope.list.filter(function(obj){
				return obj._changed;
			}).forEach(function(toSave){
				toSave = angular.copy(toSave);
				if(lastPromese === null){
					if(toSave.enabled === false){
						lastPromese = toSave.$delete();
					}else{
						lastPromese = toSave.$save();
					}
				}else{
					lastPromese = lastPromese.then(function(){
						if(toSave.enabled === false){
							toSave.$delete();
						}else{
							toSave.$save();
						}						
					});
				}				
			});
			if(lastPromese){
				lastPromese.then(function(){
					alert('Salvo com sucesso!');
					lastPromese = null;
				});
			}
		};
	}
};
var WorkhourController = CrudFactory({
	name : "WorkhourController",
	userGridAction : true,
	columns : [
	{name:"unit", label : "Unidade", width:"10px"}, 
	{name:"day", label : "Dia", filter : 'weekday'}, 
	{name:"start", label : "Início", icon : 'icon-time'}, 
	{name:'startLanch', label : "Início Almoço", icon : 'icon-time'},
	{name:"endLanch", label : "Fim Almoço", icon : 'icon-time'},
	{name:"end", label : "Fim", icon : 'icon-time'}
	], 
	url : "/api/workhour", 
	collectionUrl : "/api/user/workhour/:user/:id",
	collectionParams : {user: function(){ return parseInt(Util.getUrlParameter('id'),10);}, id:"@id"},
	extend : WorkhourControllerExtended
});
 
var UserProductsController = CrudFactory({
	name : "UserProductsController", 
	userGridAction : false,
	columns : [
		{name:"enabled", label : "Vende?", editable: true, template : 'checkbox.html', width:"10px" },
		{name:"use_product_price", label : "Usa preço produto?", editable: true, template : 'checkbox.html', width:"10px" },
		{name:"use_product_commission", label : "Usa comissão produto?", editable: true, template : 'checkbox.html', width:"10px" },
		{name:"name", label : "Produto", width:"10px"},
		{
			name:"price",
			label : "Preço",
			icon : 'icon-money',
			unEditableField : "use_product_price",
			template : 'money.html',
			width:"10px"
		},
		{
			name:"commission",
			label : "Comissão",
			unEditableField : "use_product_commission",
			template : 'percent.html',
			width:"10px" 
		}		
	], 
	url : "/api/useractivity", 
	collectionUrl : "/api/user/useractivity/:user/:id", 
	collectionParams : { user: function(){ return Util.getUrlParameter('id');}, id:"@id", activityType : "@activityType" },
	actions : {
		findByCategory : {
			method : 'GET',
			isArray: true,
			action : 'findByCategory',
			url : '/api/user/userproduct_by_category/:user/:category',
			transformResponse : function(a){
				return Util.jsonParse(a);
			}
		} 
	},
	extend : UserActivityControllerExtended
});

var UserActivityController = CrudFactory({
	name : "UserActivityController",
	userGridAction : false,
	columns : [
	{name:"enabled", label : "Faz?", editable: true, template : 'checkbox.html', width:"10px" },
	{name:"use_product_price", label : "Usa preço serviço?", editable: true, template : 'checkbox.html', width:"40px" },
	{name:"use_product_commission", label : "Usa comissão serviço?", editable: true, template : 'checkbox.html', width:"40px"},
	{name:"name", label : "Serviço", width:"10px" },
	{name:"duration", label : "Duração", icon : 'icon-time', editable: true, template : 'time.html', width:"10px" },
	{
		name:"price",
		label : "Preço",
		icon : 'icon-money',
		unEditableField : "use_product_price",
		template : 'money.html',
		width:"10px"
	},
	{
		name:"commission",
		label : "Comissão",
		unEditableField : "use_product_commission",
		template : 'percent.html',
		width:"10px"  
	},
	{
		name:"commissionAbs",
		label : "Com Abs",
		unEditableField : "use_product_commission",
		template : 'money.html',
		width:"10px" 
	},
	{
		name:"auxPercent",
		label : "Ass Perc",
		unEditableField : "use_product_commission",
		template : 'percent.html',
		width:"10px" 
	},
	{
		name:"auxPrice",
		label : "Ass Abs",
		unEditableField : "use_product_commission",
		template : 'money.html',
		width:"10px" 
	},
	{
		name:"auxHousePercent",
		label : "Ass C Perc",
		unEditableField : "use_product_commission",
		template : 'percent.html',
		width:"10px" 
	},
	{
		name:"auxHousePrice",
		label : "Ass C Abs",
		unEditableField : "use_product_commission",
		template : 'money.html',
		width:"10px" 
	}
	], 
	url : "/api/useractivity", 
	collectionUrl : "/api/user/useractivity/:user/:id", 
	collectionParams : { user: function(){ return Util.getUrlParameter('id');}, id:"@id", activityType : "@activityType" },
	actions : {
		findByCategory : {
			method : 'GET',
			isArray: true,
			action : 'findByCategory',
			url : '/api/user/useractivity_by_category/:user/:category',
			transformResponse : function(a){
				return Util.jsonParse(a);
			}
		} 
	},
	extend : UserActivityControllerExtended
});
var UserControllerExtended = {
	save : function(obj, parent){
		if(obj.groupPermissionText){
			obj.groupPermission = obj.groupPermissionText.join(',');
		}
		obj.userStatus = parseInt(obj.userStatus,10);
		parent(angular.copy(obj), function(t){
			alert('Salvo com sucesso!');
			if(t.id)
				window.parent.location.href="/user/edit_new?id="+t.id;
		}, false);
	}
};
var UserController = CrudFactory({
		useRouteParameter : true, 
		name : "UserController",
		defaultValues: { userStatus: 1, parent_percent : 0.00 },
		columns : [],
		url: "/api/v2/user",
		collectionUrl : "/api/v2/user",
		userGridAction : true, 
		extend :  UserControllerExtended,
		onSelectObj : function(obj){
			var process = function(){
				if(obj.groupPermission){
					obj.groupPermissionText = obj.groupPermission.split(',');
				}else{
					obj.groupPermissionText = "";
				}
				obj.parent_percent = parseFloat(obj.parent_percent);
			};
			if(obj.$promise){
				obj.$promise.then(function(){
					process();
				});				
			}else{
				process();
			}
		}
	}
);
