
var ImageManager = {
	save : function(title, body, id, sucessCallBack, errorCallBack){
		var data = {"title": title || "", "body": body, "id": id || "" };
		$.ajax("/notification/message",{"type": "PUT", "success" : sucessCallBack , "error" : errorCallBack, "data": data});
	},
	findById : function(id, sucessCallBack, errorCallBack){
		$.get("/notification/message/"+id,function(a){
			eval("var notification = "+a);
			sucessCallBack(notification);
		}).error(function(response){
			errorCallBack(notificat);
		});		
	},
	deleteImageById : function(id, sucessCallBack){
		$.get("/media/delete/"+id,function(a){sucessCallBack()});
	}
};