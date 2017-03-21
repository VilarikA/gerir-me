$(function() {
	var desableKeys = function() {
		return $(event.target).is('.jqte_editor, .select2-choice');
	};
	
	Mousetrap.init();

	Mousetrap.bind(['r', 'R'], function() {
		if (!desableKeys()) {
			// aqui não dá ainda para usar   
	        //var hasEgrexSystem = $('.has-egrex-system').length > 0;
	        // precisaria estar em todos os templates
			if (document.location.href.indexOf("egrex") != -1) {
				window.location.href = "/reports/center_egrex";
			} else {
				window.location.href = "/reports/center";
			}
		}
	});

	Mousetrap.bind(['u', 'U'], function() {
		if (!desableKeys())
			window.location.href = "/unit/list";
		//alert("Use K para acessar a conferência de comandas!");
	});

	Mousetrap.bind(['k', 'K'], function() {
		if (!desableKeys())
			window.location.href = "/financial_cashier/comman_conference";
	});

	Mousetrap.bind(['h', 'H'], function() {
		if (!desableKeys())
			window.location.href = "/docs/tutorial";
	});

	// só super user ATENCAO
	Mousetrap.bind(['m', 'M'], function() {
		if (!desableKeys())
			window.location.href = "/manager/companies.html";
	});

	// só super user ATENCAO
	Mousetrap.bind(['q', 'Q'], function() {
		if (!desableKeys())
			window.location.href = "/company_log/list_sqlcommand.html";
	});

	Mousetrap.bind(['o', 'O'], function() {
		if (!desableKeys())
			//window.location.href = "/command_full/user_command_full";
	        window.open("/command_full/user_command_full","_command_maste")
	});

	Mousetrap.bind(['e', 'E'], function() {
		if (!desableKeys())
			window.location.href = "/product/control_panel";
	});

	Mousetrap.bind(['x', 'X'], function() {
		if (!desableKeys())
			window.location.href = "/financial_cashier/register_payment";
	});

	Mousetrap.bind(['c', 'C'], function() {
		if (!desableKeys())
			window.location.href = "/customer/list";
	});

	Mousetrap.bind(['#'], function() {
		if (!desableKeys())
			window.location.href = "/financial_cashier/checkout_open";
	});

	Mousetrap.bind(['$'], function() {
		alert("Use F para acessar o Financeiro!");
	});

	Mousetrap.bind(['f', 'F'], function() {
		if (!desableKeys())
			window.location.href = "/financial/account_register";
	});

	Mousetrap.bind(['p', 'P'], function() {
		if (!desableKeys())
			window.location.href = "/user/list";
	});

	Mousetrap.bind(['t', 'T'], function() {
		if (!desableKeys())
			window.open('/treatments_conferenc', 'print_window');
	});
	Mousetrap.bind(['d', 'D'], function() {
		if (!desableKeys())
			window.location.href = '/activity/dash_board_treatments';
	});
	Mousetrap.bind(['A', 'a'], function() {
		var parameters = JSON.parse(localStorage.getItem("calendar_parameters"));
		if (!parameters) {
			parameters = "";
		}
		if (!desableKeys())
			window.location.href = "/calendar?" + parameters.parameters;
	});
	Mousetrap.bind(['Y', 'y'], function() {
		if (!desableKeys())
			window.location.href = "/customer/scheduling";
	});
	Mousetrap.bind(['N', 'n'], function() {
		if (!desableKeys())
			window.location.href = "/customer/notification_list";
	});
	Mousetrap.bind(['B', 'b'], function() {
		if (!desableKeys())
			window.location.href = "/activity/busy_manager";
	});

	Mousetrap.bind(['s', 'S'], function() {
		if (!desableKeys())
			window.location.href = "/activity/list";
		//alert("Use L para acessar a Profissionais x Serviços!");
	});

	Mousetrap.bind(['l', 'L'], function() {
		if (!desableKeys())
			window.location.href = "/customer/services_executors";
	});
});