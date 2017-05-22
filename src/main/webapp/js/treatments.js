var customers = [];
var deleteStakeHolder = function(id) {
	alert ("Falta implementar a deleção!!!")
	return;
	if (confirm("Deseja excluir o detalhe de atendimento?")) {
		$.post("/project/remove_stakeholder/" + gup('id'), {
				id: id
			})
			.success(function() {
				$("#bp_stakeholder").val('').change();
				updateTDetailReport(0);
				updateTDetailReport(1);
			});
	}
};
var updateTDetailReport = function(classe) {
    var hasAuxiliarModule = $('.has-auxiliar-module').length > 0;
    var hasOffSaleModule = $('.has-offsale-module').length > 0;
    var hasEsmileSystem = $('.has-esmile-system').length > 0;
    var hasEdoctusSystem = $('.has-edoctus-system').length > 0;
	var fields = [];
/*	fields [0] = {
		type: "format",
		decode: function(name, row) {
			return "<a href='/customer/edit?id=" + row[3] + "' target='_customer_maste'>" + name + "</a>";
		}
	};
*/
    if (!hasEsmileSystem) {
		fields [1] = 'none'; // dente
	}
    if (!hasAuxiliarModule) {
		fields [2] = 'none'; // auxiliar
	}
	fields [3] = 'real'; // preço unitario
	fields [4] = 'real'; // qtde
	fields [5] = 'real'; // price

    if (!hasOffSaleModule) {
		fields [7] = 'none'; // convênio
	}
    if (!hasEdoctusSystem) {
		fields [8] = 'none'; // external_id era integracao no angiosemper
	}

	fields [9] = {
		type: "format",
		decode: function(id, row) {
			customers.push(id);
			return "<span style='margin-right:4px'><a class='btn' href='/treatment/treatmentdetail?id=" + row[9] + "' target='_treatdetail_maste'>Editar</a></span>" +
				"<span><a class='btn danger' target='_blank' onclick='deleteStakeHolder(" + row[9] + ")'>Excluir</a></span>";
		}
	};

	var total_activities = 0.0;
	var total_products = 0.0;
	if (classe == "0") {
		renderReport("/report/td_activities", fields, {
			treatment: gup('id'),
			productclass: classe
		}, "#table_activities", function(data){
	        data.forEach(function(row){
	          total_activities += parseFloat(row[5]);
	        });
	        $("#total_activities").val(total_activities.formatMoney());
	    });
	} else {
		renderReport("/report/td_activities", fields, {
			treatment: gup('id'),
			productclass: classe
		}, "#table_products", function(data){
	        data.forEach(function(row){
	          total_products += parseFloat(row[5]);
	        });
	        $("#total_products").val(total_products.formatMoney());
	    });
	}
};

$(function() {
	if (gup('id')) {
		updateTDetailReport(0);
		updateTDetailReport(1);
	    setTimeout(function(){    
	    	var total = 0.0;
			if($("#add_td").length > 0){
			$("#add_td").attr("href", $("#add_td").attr("href").replace("##", gup("id")));
			}
			if($("#add_td1").length > 0){
			$("#add_td1").attr("href", $("#add_td1").attr("href").replace("##", gup("id")));
			}
			total = parseFloat($("#total_activities").val())+parseFloat($("#total_products").val())
		    $("#total").val(total.formatMoney());
	    },600);
	}

    $("#icd").icdSearch({
      createName: false,
      iconElement: ".add-on",
      userThuch: true,
      allUnits: true
    });
});

