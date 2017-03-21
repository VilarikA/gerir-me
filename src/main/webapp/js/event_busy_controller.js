var EventBussy = function(start, end, user, obs){
	this.start = start;
	this.end = end;
  if(user)
	  this.user = [user];
	this.obs = obs;
};
var EventBusyController = function() {};
EventBusyController.save = function (model, callback) {
		EventBusyController.validate(model);
		$.post("/calendar/freebusy",model, function(r) {
			if(r==1){
				callback(r);	
			}else{
				callback(false, r);
			}
			
		});
};
EventBusyController.validate = function(model){
		if(!model.start){
			throw "Selecione horário de início";
		}
		if(!model.end){
			throw "Selecione horário final";
		}
		if(!model.user || model.user.length === 0){
			throw "Selecione um profissional!";
		}
		return true;
};


