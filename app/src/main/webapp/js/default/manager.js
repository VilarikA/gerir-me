var AuthUtil = {
	updateDataCompany: function () {
		var companyLocal = localStorage.getItem("companyInfo");
		if (companyLocal) {
			eval("AuthUtil.company = " + companyLocal);
		}
		setTimeout(function () {
			$.get("/security/companyParameters", function (company) {
				eval("AuthUtil.company = " + company);
				localStorage.setItem("companyInfo", company);
			});
		});
	},
	updateDataUnit: function () {
		var unitInfo = localStorage.getItem("unitInfo");
		if (unitInfo) {
			eval("AuthUtil.unit = " + unitInfo);
		}
		setTimeout(function () {
			$.get("/security/unitParameters", function (unitInfo) {
				eval("AuthUtil.unit = " + unitInfo);
				localStorage.setItem("unitInfo", unitInfo);
			});
		}, 500);
	},
	updateDataUser: function () {
		var userInfo = localStorage.getItem("userInfo");
		if (userInfo) {
			eval("AuthUtil.user = " + userInfo);
		}
		setTimeout(function () {
			$.get("/security/userParameters", function (userInfo) {
				eval("AuthUtil.user = " + userInfo);
				localStorage.setItem("userInfo", userInfo);
			});
		}, 500);
	},
	company: {},
	unit: {},
	user: {}
};
var Cachier = {
	prepareCashierParams: function (params) {
		if (!params.cashier) {
			params.cashier = params.number;
			params.isIdForCompany = true;
		}
		return params;
	}
};
var Customer = {
	addonsListeners: [],
	rapidCreate: function (name, phone, mobile_phone, mobile_phone_op,
		email,obs, bp_indicatedby) {
		$.post("/calendar/customer/add", {
			"name": name,
			"phone": phone,
			"mobile_phone": mobile_phone,
			"mobile_phone_op": mobile_phone_op,
			"email": email,
			"obs": obs,
			"bp_indicatedby": bp_indicatedby
		}, function (result) {
			if (isNaN(result)) {
				alert(eval(result));
			} else {
				var id = result;
				var last_id = $(".id_customer_search", parent_customer_search).val();
				if (last_id != id) {
					$(".id_customer_search", parent_customer_search).val(id);
					$(".id_customer_search", parent_customer_search).change();
				}
				$(".name_customer_search").select2('close');
				$("#customer_rapid_add").modal("hide");
			}
		});
	},
	current: false,
	hasEspecialFormat: function (item) {
		var strAux = "";
		strAux = Customer.getDeliveryIcon(item) + item.text;
		if (strAux == "" || strAux == item.text) {
			strAux = Customer.getBpMonthlyIcon(item) + item.text;
		}
		return strAux;
	},
	getDeliveryIcon: function (activity) {
		var result = "";
		if (Customer.hasDelivery(activity.id)) {
			result = "<img src='/images/delivery_ok.png' width='16px'/> ";
		}
		return result;
	},
	hasDelivery: function (productId) {
		if (Customer.current) {
			var deliverys = Customer.current.deliverys.filter(function (item) {
				return item.product == productId;
			});
			return deliverys.length > 0;
		} else {
			return false;
		}
	},
	getBpMonthlyIcon: function (activity) {
		var result = "";
		if (Customer.hasBpMonthly(activity.id)) {
			result = "<img src='/images/bpmonthly.png' width='16px'/> ";
		}
		return result;
	},
	hasBpMonthly: function (productId) {
		if (Customer.current) {
			var bpmonthlys = Customer.current.bpmonthlys.filter(function (item) {
				return item.product == productId;
			});
			return bpmonthlys.length > 0;
		} else {
			return false;
		}
	},
	useDeliveryReturnPrice: function (productId) {
		var deliverys = this.current.deliverys.filter(function (item) {
			return item.product == productId;
		});
		// mateus sugeriu passar este set true para depois do filter pq
		// ele acabava pegando o preço do próximo item
		// deliverys[0].used = true;
		this.current.deliverys = this.current.deliverys.filter(function (item) {
			return !(item.used);
		});
		deliverys[0].used = true;
		return deliverys[0].price;
	},
	customerSelectAutoCompleteName: function (customer) {
		$.get("/customer_api/addons/" + customer.id, function (addons) {
			eval("var addons=" + addons)
			Customer.current = {
				"id": customer.id,
				"name": customer.name,
				"messages": addons.messages,
				"deliverys": addons.deliverys,
				"offsale": addons.offsale,
				"bpmonthlys": addons.bpmonthlys
			}
			for (var i = addons.messages.length - 1; i >= 0; i--) {
				if (addons.messages[i].message != '' && customer.id != last_notifier_customer) {
					alert(addons.messages[i].message);
				}
			};
			last_notifier_customer = customer.id;
			localStorage.setItem("last_notifier_customer", customer.id);
			for (var i in Customer.addonsListeners) {
				Customer.addonsListeners[i]();
			}
		});
		return customer.name;
	},
	openRecordsSelected: function (id) {
		var customer_id = id || Customer.current.id;
		if (customer_id != "" && customer_id != "0") {
			window.open("/records/edit_patient?id=" + customer_id,"_patient_maste")
		}
	},
	openCustomerSelected: function (id) {
		var customer_id = id || Customer.current.id;
		if (customer_id != "" && customer_id != "0") {
			window.open("/customer/edit?id=" + customer_id,"_customer_maste")
		}
	},
	customerSelectAutoCompleteFormat: function (a) {
		if (a.isemployee) {
			return a.name + " <b>Profissional</b> " + '<small style="font-size:10px; font-style:italic"> > ' + a.obs + " " + a.phone + ' </small>';
		} else {
			if (a.obs) {
				return a.name + '<small style="font-size:10px; font-style:italic"> > ' + a.obs + " " + a.phone + ' </small>';
			} else {
				return a.name + '<small style="font-size:10px; font-style:italic"> > ' + a.phone + ' </small>';
			}
		}
	}
};
var Pet = {
	addonsListeners: [],
	rapidCreate: function (name, obs, bp_manager, bp_indicatedby) {
		$.post("/animal/animal/add", {
			"name": name,
			"obs": obs,
			"bp_manager": bp_manager,
			"bp_indicatedby": bp_indicatedby
		}, function (result) {
			if (isNaN(result)) {
				alert(eval(result));
			} else {
				var id = result;
				$("#pet_rapid_add").modal("hide");
				getAnimals();
			}
		});
	},
};

var Envitoment = {
	replaseEnviromentVars: function (value) {
		if (value) {
			for (var key in Envitoment) {
				if (!$.isFunction(Envitoment[key])) {
					value = value.replaceAll("#" + key + "#", Envitoment[key]);
				}
			}
		}
		return value;
	},
	reprocess: function () {
		var $termsElements = $(".terms_and_conditions");
		$termsElements.html(Envitoment.replaseEnviromentVars($termsElements.data("terms")));
	}
};

/*
		case  "calendar" :: "animal" :: "add" :: Nil Post _ => {
			try{
				def name = S.param("name") openOr ""
				def obs = S.param("obs") openOr ""
				def bp_indicatedby_str = S.param("bp_indicatedby") openOr "0"
				def bp_indicatedby = if(bp_indicatedby_str == ""){
					"0"
				}else{
					bp_indicatedby_str
				}
				val animal = AnimalPartner.create.name(name).obs(obs).bp_indicatedby(bp_indicatedby.toLong).company(AuthUtil.company)
				animal.save
				JInt(animal.id.is)
			}catch{
				case e:RuntimeException => {
					JString(e.getMessage)
				}
				case _ =>{
					JString("Erro desconhecido, ao cadastrar pet!")
				}
			}
		}

*/