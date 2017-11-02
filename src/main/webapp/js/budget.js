$(function() {
    $('#tooth').toothField(false);
    $("#offsale").offSaleField(true);
    $("#new_item").click(function() {
      var agora = getHourBr(FactoryDate.byTime(Date.toDay().getTime()));
      $("#cutomer_id_treatment").val("")
      $("#cutomer_id_treatment").change();
      if (!$("#hour_start").val()) {
         $("#hour_start").val(agora);
      } else {
        // aceita hora de atendiemto existente do cliente
      }
      newItem ();
    });

    $(".b_add_budget").click(function() {
      return saveBudget ();
    });

    $(".tomorrow_budget").click(function() {
      return tomorrowBudget ();
    });

    $(".nextweek_budget").click(function() {
      return nextweekBudget ();
    });
    $(".nextmonth_budget").click(function() {
      return nextmonthBudget ();
    });

    $("#cutomer_id_treatment").change(function(){
      var hasPetSystem = $('.has-pet-system').length > 0;
      if (hasPetSystem) {
        getAnimals();
      }
    });

    var today = new Date();
    //var tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000);
    var start = gup("start") || getDateBr(today);
    $("#start").val(start)

    getUsersCurrentUnitCommand ();
    getAuxiliarsCurrentUnitCommand ();

    return;
})

var getActivities = function() {
  DataManager.getActivities($("#user_budget").val(), function(activitysObj) {
    global_activitiesObj = activitysObj;
    $('#activity option').remove();
    var ret = "<option value=''>Selecione um serviço</option>";
    for (var i in activitysObj) {
      ret += "<option value='" + activitysObj[i].id + "'>" + activitysObj[i].name + "</option>";
    }
    $('#activity').append(ret);
    //$('#activity').change().select2('open');
  });
};

var getAuxiliarsCurrentUnitCommand = function() {
  var url;
  url = "/cash/getAuxiliarsCurrentUnitCommand";
  return $.get(url, function(t) {
    var obj, _i, _len, _results;
    eval("userObj = " + t);
    $('#auxiliar, #auxiliar').append("<option value='0'>Selecione um auxiliar</option>");
    _results = [];
    for (_i = 0, _len = userObj.length; _i < _len; _i++) {
      obj = userObj[_i];
      _results.push($('#auxiliar, #auxiliar').append("<option value='" + obj.id + "'>" + 
        obj.name + " " + obj.idForCompany + "</option>"));
    }
    return _results;
  });
};


var getUsersCurrentUnitCommand = function() {
  var url;
  url = "/cash/getUsersCurrentUnitCommand";
  return $.get(url, function(t) {
    var obj, _i, _len, _results;
    eval("userObj = " + t);
    $('#user_budget, #user_budget').append("<option value='0'>Selecione um profissional</option>");
    _results = [];
    for (_i = 0, _len = userObj.length; _i < _len; _i++) {
      obj = userObj[_i];
      _results.push($('#user_budget, #user_budget').append("<option value='" + obj.id + "'>" + 
        obj.name + " " + obj.idForCompany + "</option>"));
    }
    return _results;
  });
};

var tomorrowBudget = function() {
  var today = new Date();
  var tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000);
  var start = getDateBr(tomorrow);
  $("#start").val(start)
}

var nextweekBudget = function() {
  var today = new Date();
  var nextweek = new Date(today.getTime() + ((24 * 60 * 60 * 1000) * 7));
  var start = getDateBr(nextweek);
  $("#start").val(start)
}

var nextmonthBudget = function() {
  var today = new Date();
  var nextweek = new Date(today.getTime() + ((24 * 60 * 60 * 1000) * 30));
  var start = getDateBr(nextweek);
  $("#start").val(start)
}

var newItem = function() {
  if (!gup("id") || gup("id") == "") {
    return alert('É preciso salvar o orçamento antes de inserir itens ao mesmo');
  }
  getActivities ();    
  return $("#budget_modal").modal({
    "show": true,
    "keyboard": true,
    "backdrop": true
  });
}

var callApiLock = false;

var saveBudget = function() {
  var end, obs, start, user, password, auxiliar, animal, offsale;
  start = $("#start").val() + " " + $("#hour_start").val();
  // aqui é start mesmo pq dt fim não é informada
  end = $("#start").val() + " " + $("#hour_end").val();
  user = $("#user_budget").val();
  password = ""//$("#password").val();
  auxiliar = $("#auxiliar").val();
  animal = $("#animal").val() || 0 ;
  offsale = $("#offsale").val() || 0;
  customer = $("#cutomer_id_treatment").val();
  price = "" //$("#price").val();
  amount = "1" //$("#amount").val();
  obs = $("#obs").val();
  activity = $("#activity").val();
  product = "" //$("#product").val();
  var valid = false;

  if ((!$("#cutomer_id_treatment").val()) || (parseFloat($("#cutomer_id_treatment").val()) == 0)) {
    return alert('Um cliente precisa ser selecionado');
  }
  if (($("#activity").val()) && (parseFloat($("#activity").val()) != 0)) {
    if (!$("#start").val() || !$("#hour_start").val() || !user) {
      return alert('Verifique os dados obrigatórios: hora início!');
    } else {
      valid = true;
    } 
  } else {
    return alert('Um serviço precisa ser selecionado');
  }

  if ((!$("#user_budget").val()) || (parseFloat($("#user_budget").val()) == 0)) {
    return alert('Um profissional precisa ser selecionado!');
  }

  if (!callApiLock) {
    callApiLock = true
    if (valid) {
      return $.post("/command/add_command", {
        "start": start,
        "end": end,
        "user": user,
        "auxiliar": auxiliar,
        "animal": animal,
        "offsale": offsale,
        "password": password,
        "customer": customer,
        "obs": obs,
        "price": price,
        "amount": amount,
        "activity": activity,
        "status": "9", // budget
        "project": gup("id"), // budget
        "product": product
      }, function(results) {
        if(results === 1 || results == "1"){
          alert("Cadastrado com sucesso");
          $("#budget_modal").modal({
            "hide": true
          });
        }else{
          alert(eval(results));
        }
        callApiLock = false
        //return Manager.getListFromServer();
        $("#send").click();
      });
    }
  } else {
    alert("Já existe um processo em andamento. Aguarde o fim do processamento para clicar novamente!");
  }
};

var new_detail = function () {
    $("#cutomer_id_treatment").val($("#bp_sponsor").val())
    $("#cutomer_id_treatment").change();
//    $("#hour_start").val(start_hour);
    $("#user_budget").val($("#bp_manager").val())
    $("#user_budget").change();
    $("#new_item").click();
    newItem ();
};

var del_detail = function (tdId) {
    return $.post("/command/del_detail", {
      "tdid": tdId
    }, function(results) {
      if(results === 1 || results == "1"){
        alert("Serviço excluído com sucesso");
      }else{
        alert(eval(results));
      }
      //return Manager.getListFromServer();
      $("#send").click();
    });
};

