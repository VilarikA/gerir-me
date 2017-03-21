var customers = [];
var deleteStakeHolder = function(id) {
	if (confirm("Deseja excluir o participante?")) {
		$.post("/project/remove_stakeholder/" + gup('id'), {
				id: id
			})
			.success(function() {
				$("#bp_stakeholder").val('').change();
				updateReportStake();
			});
	}
};
var deletePaymentCondition = function(id) {
	if (confirm("Deseja excluir a condição de pagamento?")) {
		$.post("/project/remove_paymentcondition/" + id, {
				id: id
			})
			.success(function() {
				updateReportCondition();
			});
	}
};
var updateReportStake = function() {
	var fields = ['text', {
		type: "format",
		decode: function(name, row) {
			return "<a href='/customer/edit?id=" + row[3] + "' target='_customer_maste'>" + name + "</a>";
		}
	}, 'text', {
		type: "format",
		decode: function(id, row) {
			customers.push(id);
			return "<span style='margin-right:4px'><a class='btn' href='/project/edit_stakeholder?id=" + row[4] + "' target='_blank'>Ir</a></span>" +
				"<span><a class='btn danger' target='_blank' onclick='deleteStakeHolder(" + row[4] + ")'>Excluir</a></span>";
		}
	}, 'none'];
	renderReport("/report/stakeholder_by_project", fields, {
		project: gup('id')
	}, "#table_stakeholders");
};;
var updateReportCondition = function() {
	var fields = ['text', 'date', 'text', 'text', 'text',{
		type: "format",
		decode: function(id, row) {
			customers.push(id);
			return "<span style='margin-right:4px'><a class='btn' href='/project/edit_paymentcondition?id=" + row[5] + "' target='_blank'>Ir</a></span>" +
				"<span><a class='btn danger' target='_blank' onclick='deletePaymentCondition(" + row[5] + ")'>Excluir</a></span>";
		}
	}, 'none'];
	renderReport("/report/paymentcondition_by_project", fields, {
		project: gup('id')
	}, "#table_paymentconditions");
};;
$(function() {
	if (gup('id')) {
		updateReportStake();
		updateReportCondition();
		$("#add_stakeholder").click(function() {
			$.post("/project/add_stakeholder/" + gup('id'), {
					bp_stakeholder: $("#bp_stakeholder").val(),
					stakeholdertype: $("#stakeholdertype").val()
				})
				.success(function() {
					$("#bp_stakeholder").val('').change();
					updateReportStake();
				});
		});
		$("#add_paymentcondition").click(function() {
			$.post("/project/add_paymentcondition/" + gup('id'), {
					days: $("#paymentconditions_days").val(),
					paymentdate: $("#paymentconditions_paymentdate").val(),
					obs: $("#paymentconditions_obs").val(),
					percent: $("#paymentconditions_percent").val(),
					value: $("#paymentconditions_value").val()
				})
				.success(function() {
					updateReportCondition();
				});
		});
	}
    $('#stakeholdertype').stakeholderTypeField();
});