var Util = {
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
};