var customers = [];
var deleteStakeHolder = function(id) {
	if (confirm("Deseja excluir o detalhe de atendimento?")) {
		$.post("/project/remove_stakeholder/" + gup('id'), {
				id: id
			})
			.success(function() {
				$("#bp_stakeholder").val('').change();
				updateReportStake(0);
				updateReportStake(1);
			});
	}
};
var updateReportStake = function(classe) {
	var fields = [];
/*	fields [0] = {
		type: "format",
		decode: function(name, row) {
			return "<a href='/customer/edit?id=" + row[3] + "' target='_customer_maste'>" + name + "</a>";
		}
	};
*/
	if (classe == 1) {
		fields [1] = 'none';
	}
	fields [2] = 'real';
	fields [4] = 'real';

	fields [7] = {
		type: "format",
		decode: function(id, row) {
			customers.push(id);
			return "<span style='margin-right:4px'><a class='btn' href='/treatment/treatmentdetail?id=" + row[7] + "' target='_treatdetail_maste'>Ir</a></span>" +
				"<span><a class='btn danger' target='_blank' onclick='deleteStakeHolder(" + row[7] + ")'>Excluir</a></span>";
		}
	};

	if (classe == "0") {
		renderReport("/report/td_activities", fields, {
			treatment: gup('id'),
			productclass: classe
		}, "#table_activities");
	} else {
		renderReport("/report/td_activities", fields, {
			treatment: gup('id'),
			productclass: classe
		}, "#table_products");
	}
};

$(function() {
	if (gup('id')) {
		updateReportStake(0);
		updateReportStake(1);
	    setTimeout(function(){    
			if($("#add_td").length > 0){
			$("#add_td").attr("href", $("#add_td").attr("href").replace("##", gup("id")));
			}
			if($("#add_td1").length > 0){
			$("#add_td1").attr("href", $("#add_td1").attr("href").replace("##", gup("id")));
			}
	    },600);
	}

    $("#icd").icdSearch({
      createName: false,
      iconElement: ".add-on",
      userThuch: true,
      allUnits: true
    });
});

