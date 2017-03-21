var BuildInfoClass = require("./buildInfor.js");
var green = new BuildInfoClass('green', 7171);
var blue = new BuildInfoClass('blue', 7272);
green.isRuning().then(function(greenRuing){
	console.log('greenRuing:'+greenRuing);
	if(greenRuing){
		blue.start().then(function(){
			console.log('green.stop();');
			green.stop().then(function(){
				console.log('green.stoped');
			}, function(){
				console.log('Erro stop green');
				process.exit(1);
			});
		}, function(){
			console.log('Erro on start blue');
			process.exit(1);
		});		
	}else{
		green.start().then(function(){
			console.log('blue.stop();');
			blue.stop().then(function(){
				console.log('blue.stoped');
			}, function(){
				console.log('Erro stop green');
				process.exit(1);				
			});
		}, function(){
			console.log('Erro on start green')
		});
	}
}, function(){
	console.log('Erro on check isRuning');
	process.exit(1);
});