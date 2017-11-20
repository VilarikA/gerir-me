var WorkhourControllerExtended = {
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
