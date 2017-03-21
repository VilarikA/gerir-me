var callApiLock = false;

var openCashier = function() {
  if ($("#start_value").val() == "") {
    $("#start_value").val(0.00);
  }
  url = $('#checkoutOpen_form').attr('action');
  if (!AuthUtil.company.autoOpenCalendar_$qmark && !$("#unit").val()) {
    alert("Selecione uma unidade para abertura do caixa!");
    callApiLock = false;
    return;
  }
  var unit = $("#unit").val() || '0';
  var obs = $("#obs").val() || '';
  url = url + "/" + encodeURIComponent($("#opener_date").val()) + "/" + $("#start_value").val() + "/" + unit + "/" + obs;
  $.get(url, function(t) {
    eval("var obj = " + t)
    if (obj.status == 'success') {
      alert("Caixa[" + obj.id + "] aberto com sucesso! ")
      //url = "/financial_cashier/checkout_open";
      //window.open(url);
      callApiLock = false;
    } else {
      if (obj.message) {
        alert(obj.message)
        callApiLock = false;
      } else {
        alert("Erro ao abrir o caixa. Verifique os campos e tente novamente!")
        callApiLock = false;
      }
    }
  });
};

var callApi = function() {
  if (!callApiLock) {
    callApiLock = true
    DataManager.getCashiers(function(cashiers) {
      var unit = $("#unit").val() || '0';
      if (cashiers.length == 0) {
        openCashier();
      } else if ((unit != "0" && unit != cashiers[0].unit_id) || 
                 (unit == "0" && AuthUtil.unit.id != cashiers[0].unit_id)) {
        openCashier();
      } else if ($("#opener_date").val() != getDateBr(new Date(parseInt(cashiers[0].openerDate)))){
        openCashier();
      } else if ($("#obs").val() != "" && $("#obs").val() != cashiers[0].obs) {
        openCashier();
      } else {
        alert ("O caixa  " + cashiers[0].idForCompany + "  -  " + getDateBr(new Date(parseInt(cashiers[0].openerDate))) +
        "  -  " + cashiers[0].userName + "  -  " + cashiers[0].unit + "  já está aberto, para abrir outro na mesma data justifique no campo de observação")
        callApiLock = false;
      }
  //    if (confirm("O caixa  " + cashiers[0].idForCompany + "  de  " + getDateBr(new Date(parseInt(cashiers[0].openerDate))) +
  //      "  já esta aberto, tem certeza que deseja abrir outro?")) {
  //    }

    });
  } else {
    alert("Já existe um processo em andamento. Aguarde o fim do processamento para clicar novamente!");
  }
};

$(function() {
  $("#unit").unitField(true, false);
  $("#opener_date").val(getDateBr(new Date()));
});

