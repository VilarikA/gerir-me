	var openAnimalSelected = function (id) {
		var animal_id = id || $("#animal").val();
		if (animal_id) {
			if (animal_id != "" && animal_id != "0") {
				window.open("/animal/edit_animal?id=" + animal_id,"_animal_maste")
			}
		} else {
			alert ("Nenhum animal selecionado!");
		}
	};
	var openAnimalRapidAdd = function () {
		// acrescentei este parent aqui - as vezes inseria cliente na bola
		// verde e não mostrava o nome
		// parent_pet_search = $(this).parent().parent();
		$("#pet_rapid_add").modal({
			"backdrop": true,
			'keyboard': true,
			'show': true
		});
		$("#rapid_pet_bp_manager").val($("#cutomer_id_treatment").val());
		$("#rapid_pet_bp_manager").change();
	}	

/*
	$("#pet_add_button").click(function() {
		alert ("vaiiiii ")
		// acrescentei este parent aqui - as vezes inseria cliente na bola
		// verde e não mostrava o nome
		parent_pet_search = $(this).parent().parent();
		$("#pet_rapid_add").modal({
			"backdrop": true,
			'keyboard': true,
			'show': true
		});
	});
*/
	var AnimalRapidAdd = function () {
		if ($("#rapid_pet_name").val() == "") {
			alert ("Nome do pet precisa ser informado")
			return
		}
		if ($("#rapid_pet_bp_manager").val() != $("#cutomer_id_treatment").val() && 
			$("#rapid_pet_bp_indicatedby").val() != $("#cutomer_id_treatment").val()) {
			alert ("Tutor do pet ou quem Indicou precisa ser igual ao cliente na agenda")
			return
		}
		Pet.rapidCreate($("#rapid_pet_name").val(), 
			$("#rapid_pet_obs").val(), 
			$("#rapid_pet_bp_manager").val(),
			$("#rapid_pet_bp_indicatedby").val());
	}

/*	$("#b_pet_rapid_add").click(function() {
		alert ("vaiiiii ")
		Pet.rapidCreate($("#rapid_pet_name").val(), 
			$("#rapid_pet_obs").val(), 
			$("#rapid_pet_bp_indicatedby").val());
	})
*/
	$("#rapid_pet_name").keypress(function(e) {
		if (e.keyCode == 13) {
			$("#b_pet_rapid_add").click();
		}
	});

	var openAnimalRecordSelected = function (id) {
		var animal_id = id || $("#animal").val();
		if (animal_id) {
			if (animal_id != "" && animal_id != "0") {
				window.open("/records/edit_patient?id=" + animal_id,"_patient_maste")
			}
		} else {
			alert ("Nenhum animal selecionado!");
		}
	};
	var getAnimals = function() {
		// caixa e agenda
		var animal_id = $("#customer").val() || $("#cutomer_id_treatment").val()
		if (!animal_id) {
			$('#animal option').remove();
		} else {
			DataManager.getAnimals(animal_id, function(animalsObj) {
				global_animalsObj = animalsObj;
				$('#animal option').remove();
				$('#animal_target option').remove();
				var ret = "<option value='' selected='true'> Selecione um pet</option>";
				for (var i in animalsObj) {
					var relation = ""
					if (animalsObj[i].bp_manager != animal_id) {
						relation = " indicado"
					}
					var death = "";
					if (animalsObj[i].death != "") {
						// o negrito nao funcionou aqui
						//death = "<b> falecido em </b> " + animalsObj[i].death
						death = " falecido em " + animalsObj[i].death
					} else {
						death = ""
					}
					ret += "<option value='" + animalsObj[i].id + "'>" + animalsObj[i].name + " " + death + " " + relation + "</option>";
				}
				$('#animal').append(ret);
				$('#animal_target').append(ret);
				if (animalsObj.length == 1) {
					// se só tem um pet seta ele de uma vez
					$('#animal').val(animalsObj[0].id).change();
					$('#animal_target').val(animalsObj[0].id).change();
				} else if (animalsObj.length == 0) {
					$('#animal').val("").change();
					$('#animal_target').val("").change();
				}
			});
		}
	};

$(function() {
});

