google.load("visualization", "1", {packages:["corechart"]});
google.load('visualization', '1', {packages: ['gauge']});
function Monitor(conluns,id,show_max_results,title,interval,process,type,options){
	if(type == undefined){
		type = 'line'
	}
	if(options == undefined){
		options = {}
	}

	$.extend(options,{title: title})

	var data  = [conluns];
	var chart = null;
	if(type == 'line'){
		chart = new google.visualization.LineChart(document.getElementById(id));
	}else if(type == "pie"){
		chart = new google.visualization.PieChart(document.getElementById(id));
	}else{
		chart = new google.visualization.Gauge(document.getElementById(id));
	}

	function showGraph(){
				var datai = google.visualization.arrayToDataTable(data);
				chart.draw(datai,options);
				if(data.length > show_max_results){
					data.shift();
					data.shift();
					data.unshift(conluns);
				}
		
		}
	return {
		getStat: function(){
			process(data,showGraph);
		},
		start : function(){
			this.getStat()
			setInterval(this.getStat,interval);//process(data)
		}
	}
};