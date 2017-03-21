$(function(){
	if(gup("id")){
		var id = gup("id");
		ImageManager.findById(id, function(notification){
			$("#body").val(notification.body);
			$("#name").val(notification.title);
			$("#body").jqte();
		},
		function(response){
			alert("Erro buscar mensagen!");
		});
	}else{
		$("#body").jqte();
	}
	Mousetrap.bind = function(){};
	$("#send").click(function(){
		var sucessCallBack = function(t){
			alert("Salvo com sucesso!");
			if(gup("id")){
				window.location.href ="/customer/notification_create?id="+gup("id");
			} else {
				window.location.href ="/customer/notification_list";
			}
		};
		var errorCallBack = function(response){
			alert("Erro ao adicionar!");
		};	
		ImageManager.save($("#name").val(), $("#body").val(), gup("id"), sucessCallBack, errorCallBack);
	});	
});