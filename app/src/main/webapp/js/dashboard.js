var tratmentUsers = []
var treatmentsFrequency = []
$(function() {
	$(".column" ).sortable({connectWith: ".column",stop:function(){drawChart();drawFrequencyChart();}	});
	$(".portlet" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
				   .find( ".portlet-header" )
				   .addClass( "ui-widget-header ui-corner-all" )
				   .prepend( "<span class='ui-icon ui-icon-minusthick'></span>")
				   .end().find( ".portlet-content" );
	$( ".portlet-header .ui-icon" ).click(function() {
		$( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
		$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
	});
	$(".column").disableSelection();
	$("#treatment_detail_div").hide();

	$.get("/payment/getCheques",function(resp){
		$("#cheque_dash table tbody").html("");
		eval("resp = "+ resp)
		var max = resp.length <= 15 ? (resp.length - 1) : 15;
		for (var i = max; i >= 0; i--) {
			cheque = resp[i];
			$("#cheque_dash table tbody").append("<tr><td>"+cheque.id+"</td><td>"+cheque.number+"</td><td>R$ "+cheque.value+"</td><td>"+getDateBr(new Date(cheque.date))+"</td></tr>");
		};
	})

	$.get("/calendar/treatments/today/total",function(resp){
		eval("tratmentUsers = "+ resp)
		drawChart();
    })

	$.get("/calendar/treatments/frequency/total",function(resp){
		eval("treatmentsFrequency = "+ resp)
		drawFrequencyChart();
    })

	var fields = [];
    fields[1] = "real";
    var url = "/report/dre";
    var request = function(){
      renderReport(url,fields,{"start":new Date().getStartOfMonth().getDateBr(), "end" : new Date().getDateBr() }, "#account_dash");
    }
    request()


});

drawChart = function(){
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'nomes');
    data.addColumn('number', 'total');
    for(i in tratmentUsers){
    	data.addRow([tratmentUsers[i].name,tratmentUsers[i].total]);
    }
    var options = {'title':'Atendimentos de Hoje','width':275,'height':200};
	// Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
	chart.draw(data, options);	
}
drawFrequencyChart = function(){
	var data = new google.visualization.DataTable();
    data.addColumn('string', 'Data');
    data.addColumn('number', 'Atendimentos');
    for(i in treatmentsFrequency){
    	dateBr = getDateBr(new Date(treatmentsFrequency[i].date))
    	data.addRow([dateBr,treatmentsFrequency[i].total]);
    }
    var options = {'title':'Atendimentos da semana','width':275,'height':200};
	// Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.LineChart(document.getElementById('frequency_chart_div'));
	chart.draw(data, options);	
}