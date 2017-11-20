var DataManagerClass = function(){
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
var DataManager = new DataManagerClass();