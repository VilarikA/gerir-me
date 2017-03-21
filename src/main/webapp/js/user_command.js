(function() {
  var Manager;

  Manager = (function() {
    function Manager() {}

    Manager.remove = function(id) {
      var url;
      url = "/calendar/remove_freebusy/" + id;
      return $.get(url, function() {
        alert("Excluído com sucesso!");
        return Manager.getListFromServer();
      });
    };

    Manager.getUsersCurrentUnitCommand = function() {
      var url;
      url = "/cash/getUsersCurrentUnitCommand";
      return $.get(url, function(t) {
        var obj, _i, _len, _results;
        eval("userObj = " + t);
        $('#user, #user').append("<option value='0'>Selecione um profissional</option>");
        _results = [];
        for (_i = 0, _len = userObj.length; _i < _len; _i++) {
          obj = userObj[_i];
          _results.push($('#user, #user').append("<option value='" + obj.id + "'>" + 
            obj.name + " " + obj.idForCompany + "</option>"));
        }
        return _results;
      });
    };

    Manager.getAuxiliarsCurrentUnitCommand = function() {
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

    Manager.getCustomers = function() {
      var url;
      url = "/command/getCustomers";
      return $.get(url, function(t) {
        var obj, _i, _len, _results;
        eval("customerObj = " + t);
        $('#customer, #customer').append("<option value=''>Selecione um cliente</option>");
        _results = [];
        for (_i = 0, _len = customerObj.length; _i < _len; _i++) {
          obj = customerObj[_i];
          _results.push($('#customer, #customer').append("<option value='" + obj.id + "'>" + obj.name + "</option>"));
        }
        return _results;
      });
    };
    Manager.getActivities = function() {
      DataManager.getActivities($("#user").val(), function(activitysObj) {
        global_activitiesObj = activitysObj;
        $('#activity option').remove();
        var ret = "<option value=''>Selecione um serviço</option>";
        for (var i in activitysObj) {
          ret += "<option value='" + activitysObj[i].id + "'>" + activitysObj[i].name + "</option>";
        }
        $('#activity').append(ret);
        $('#activity').change().select2('open');
      });
    };

    Manager.user = function() {
      var user;
      user = $("#user").val();
      if (!user) {
        user = "0"
      }
      return user
      };

    Manager.getListFromServer = function() {
      var password;
      password = $("#password").val();
//      if (document.location.href.indexOf("edoctus") != -1) {
      var hasEdoctusSystem = $('.has-edoctus-system').length > 0;
      if (hasEdoctusSystem) {
        if ($("#password").val() != undefined) {
          // trazer aqui passwd do user e setar tb user
          if (!$("#password").val()) {
            $("#password").val("edoctus")
            password = "edoctus"
            //$("#user").val("0")
          }
        } else {
          password = "edoctus"
        }
      } else {
        if ($("#password").val() != undefined) {
          if (!$("#password").val()) {
            return alert('Informe senha do profissional');
          }
        }
      }
      var hasAuxiliarModule = $('.has-auxiliar-module').length > 0;
      var hasNotMedical = $('.has-not-medical').length > 0;
      var hasPetSystem = $('.has-pet-system').length > 0;

      var fields = [];
      //fields[5] = "dateTime"; a data já tá formatada hh24:mi no sql
      if (hasNotMedical) { // chegou
        fields[1] = "none";
      }
      fields[2] = {
        type: "format",
        decode: function(name, row) {
          return "<a href='/customer/edit?id=" + row[11] + "' target='_customer_maste'>" + name + "</a>";
        }
      }
      if (!hasAuxiliarModule) {
        fields[3] = "none";
      } else {
        fields[3] = {
          type: "format",
          decode: function(name, row) {
            return "<a href='/user/edit?id=" + row[14] + "' target='_user_maste'>" + name + "</a>";
          }
        }
      }
      if (!hasPetSystem) {
        fields[4] = "none";
      } else {
        fields[4] = {
          type: "format",
          decode: function(name, row) {
            return "<a href='/animal/edit_animal?id=" + row[15] + "' target='_animal_maste'>" + name + "</a>";
          }
        }
      }
      if (!hasNotMedical) { // qtde
        fields[6] = "none";
      }
      if (!hasNotMedical) { // valor
        fields[7] = "none";
      }
      fields[8] = {
        type : "format",
        decode: function(name, row){
        // duplicado do treatments_conference.js
          var status = row[8];
          var statstr = '' 
          var title = ''
          if (status == '0') {
            statstr = 'open'
            title ='agendado'
          } else if (status == '1') {
            statstr = 'missed'
            title ='faltou'
          } else if (status == '2') {
            statstr = 'Arrived'
            title ='chegou'
          } else if (status == '3') {
            statstr = 'Ready'
            title ='atendido'
          } else if (status == '4') {
            if (row[16] == '3') {
              statstr = 'ready_paid'
              title ='atendido / pago'
            } else {
              statstr = 'paid'
              title ='pago'
            }
          } else if (status == '5') {
            statstr = 'Deleted'
            title ='excluído'
          } else if (status == '6') {
            statstr = 'Confirmed'
            title ='confirmado'
          } else if (status == '7') {
            statstr = 'PreOpen'
            title ='pré agendado'
          } else if (status == '8') {
            statstr = 'ReSchedule'
            title ='desmarcou'
          }
          // //"<img title='" + title + "' src='/images/treatment_"+statstr.toLowerCase()+".png' width='24'/>"
          return "<img title='" + title + "' src='/images/treatment_"+statstr.toLowerCase()+"1.png' width='24'/>"
        }
      };
      if (hasNotMedical) { // espera
        fields[10] = "none";
      }
      fields[11] = {
        type : "format",
        decode: function(name, row) {
          var strAux = "";
          var hasEdoctusSystem = $('.has-edoctus-system').length > 0;
          var hasEphysioSystem = $('.has-ephysio-system').length > 0;
          if (hasEdoctusSystem || hasEphysioSystem) {
//          if ((document.location.href.indexOf("edoctus") != -1) ||
//          (document.location.href.indexOf("ephysio") != -1)) {
            strAux = '<a title="Novo prontuário" href="/quiz/quizapply?business_pattern=' + row[11] + '&quiz=' + row[13] + '"> <img width="24" src="/images/add.png"/></a>' +
                '<a title="Prontuário" href="/records/edit_patient?id=' + row[11] + '"> <img width="24" src="/images/records.png"/></a>'
          }
          return strAux 
           // agora ir para cadastro é link no nome
           //+ <a title="Cadastro" href="/customer/edit?id=' + row[11] + '"> <img width="24" src="/images/customers.png"/></a>'
        }
      };
      fields[12] = {
        type : "format",
        decode: function(name, row) {
          return "<a class='btn primary' onclick='Manager.new_detail(" + row[11] +")' title='Inserir novo serviço para este cliente/paciente' target=''>Inserir novo</a> " +
                "<a class='btn danger' onclick='Manager.del_detail(" + row[12] +")'  target=''>Excluir</a>"
          //      "<a class='btn primary' onclick='Manager.new_fit(" +row[0].replace (':','.') +")' title='Inserir novo serviço neste mesmo horário' target=''>Encaixar</a> " +
        }
      };
      fields[13] = "none" // questionario/prontuário default
      fields[14] = "none" // id assistente
      fields[15] = "none" // id animal
      fields[16] = "none" // tr.status2
      renderReport("/command/usersales" + "?user=" + (Manager.user())+"&password=" + password, fields, {
        project: gup('id')
      }, "#grid");

      var fields1 = [];
      fields1[1] = {
        type: "format",
        decode: function(name, row) {
          return "<a href='/customer/edit?id=" + row[7] + "' target='_customer_maste'>" + name + "</a>";
        }
      }
      fields1[2] = {
        type: "format",
        decode: function(name, row) {
          return "<a href='/user/edit?id=" + row[8] + "' target='_user_maste'>" + name + "</a>";
        }
      }
      fields1[3] = {
        type: "format",
        decode: function(name, row) {
          return "<a href='/user/edit?id=" + row[9] + "' target='_user_maste'>" + name + "</a>";
        }
      }
      if (!hasPetSystem) {
        fields1[4] = "none";
      } else {
        fields1[4] = {
          type: "format",
          decode: function(name, row) {
            return "<a href='/animal/edit_animal?id=" + row[10] + "' target='_animal_maste'>" + name + "</a>";
          }
        }
      }


      fields1[6] = {
        type : "format",
        decode: function(name, row) {
          // porque a 1a opção nao funciona
          // por que a função no Manager não funciona
//          return "<a class='btn success' href='/command/setaux?user=" + (Manager.user())+ "&tdid="+row[5]+"'>XML</a>"
//          return "<a class='btn success' onclick='Manager.set_auxiliar(" + (Manager.user()) + "," +row[5]+")'  target='_tissxml_maste'>XML1</a>"
          return "<a class='btn primary' onclick='Manager.new_detail("+row[7]+")' title='Inserir novo serviço para este cliente/paciente' target=''>Inserir novo</a> " +
          "<a class='btn success' onclick='set_auxiliar(" + (Manager.user()) + "," +row[6]+")'  target=''>Gravar assistente</a> " +
          "<a class='btn danger' onclick='del_auxiliar(" + (Manager.user()) + "," +row[6]+")'  target=''>Excluir assistente</a>"
        }
      };
      fields1[7] = "none"
      fields1[8] = "none"
      fields1[9] = "none"
      fields1[10] = "none"

      renderReport("/command/treataux" + "?user=" + (Manager.user())+"&password=" + password, fields1, {
        project: gup('id')
      }, "#grid1");

    };

    Manager.new_fit = function (hour_start) {
      //alert (hour_start)
      $("#hour_start").val(hour_start);
      Manager.new ();
    };

    Manager.new_detail = function (customerId) {
        $("#customer").val(customerId)
        $("#new").click();
    };

    Manager.del_detail = function (tdId) {
        return $.post("/command/del_detail", {
          "tdid": tdId
        }, function(results) {
          if(results === 1 || results == "1"){
            alert("Serviço excluído com sucesso");
          }else{
            alert(eval(results));
          }
          return Manager.getListFromServer();
        });
    };

    Manager.set_auxiliar = function (userId, tdId) {
        //
        // usado tambem na Agenda na comanda e no caixa
        //
        return $.post("/command/setaux", {
          "user": userId,
          "tdid": tdId,
          "command": "1"
        }, function(results) {
          if(results === 1 || results == "1"){
            alert("Assistente cadastrado com sucesso");
          }else{
            alert(eval(results));
          }
          return Manager.getListFromServer();
        });
    };
  
    var callApiLock = false;

    Manager.save = function() {
      var end, obs, start, user, password, auxiliar, animal, offsale;
      start = $("#start").val() + " " + $("#hour_start").val();
      // aqui é start mesmo pq dt fim não é informada
      end = $("#start").val() + " " + $("#hour_end").val();
      user = $("#user").val();
      password = $("#password").val();
      auxiliar = $("#auxiliar").val();
      animal = $("#animal").val() || 0 ;
      offsale = $("#offsale").val() || 0;
      customer = $("#customer").val();
      price = $("#price").val();
      amount = $("#amount").val();
      obs = $("#obs").val();
      activity = $("#activity").val();
      product = $("#product").val();
      var valid = false;
      if ((!$("#customer").val()) || (parseFloat($("#customer").val()) == 0)) {
        return alert('Um cliente precisa ser selecionado');
      }
      if (($("#activity").val()) && (parseFloat($("#activity").val()) != 0)) {
        if (!$("#start").val() || !$("#hour_start").val() || !user) {
          return alert('Verifique os dados obrigatórios: hora início!');
        } else {
          valid = true;
        } 
      } else {
        if (($("#product").val()) && (parseFloat($("#product").val()) != 0)) {
          if (!$("#hour_start").val()) {
            start = $("#start").val() + " 05:00"
            end = $("#start").val() + " 05:15"
          }
          valid = true;
        } else {
          return alert('Um serviço ou produto precisa ser selecionado');
        }
      }

      if ($("#password").val() != undefined) {
        if (!$("#password").val()) {
           return alert('Informe senha do profissional');
        } else {
          valid = true;
        }
      } else {
        valid = true;
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
            "product": product
          }, function(results) {
            if(results === 1 || results == "1"){
              alert("Cadastrado com sucesso");
              $("#command_modal").modal({
                "hide": true
              });
            }else{
              alert(eval(results));
            }
            callApiLock = false
            return Manager.getListFromServer();
          });
        }
      } else {
        alert("Já existe um processo em andamento. Aguarde o fim do processamento para clicar novamente!");
      }
    };

    Manager.new = function() {
      if ((!$("#user").val()) || (parseFloat($("#user").val()) == 0)) {
        return alert('Um profissional precisa ser selecionado!');
      }
      if ($("#password").val() != undefined) {
        if (!$("#password").val()) {
          return alert('Informe senha do profissional!');
        } 
      }
      Manager.getActivities();

      // para exibir um possivelcliente setado ao clicar na linha
      $("#customer").change();

      return $("#command_modal").modal({
        "show": true,
        "keyboard": true,
        "backdrop": true
      });
    };

    return Manager;

  })();

  $(function() {
    $("#forget").click(function() {
      $("#password").val("1234").change(); 
      $("#user").val("0");
      $("#user").change();
      $("#grid tbody").html("");
      // dava erro agora que o reports.js dá msg no caso de exceção
      //return Manager.getListFromServer();
    });

    $("#customer").change(function(){
      var hasPetSystem = $('.has-pet-system').length > 0;
      if (hasPetSystem) {
        getAnimals();
      }
    });

    $("#new").click(function() {
      var agora = getHourBr(FactoryDate.byTime(Date.toDay().getTime()));
      //alert (" agora ==== " + getHourBr(FactoryDate.byTime(agora)))
      $("#hour_start").val(agora);
      Manager.new ();
/*
      if ((!$("#user").val()) || (parseFloat($("#user").val()) == 0)) {
        return alert('Um profissional precisa ser selecionado!');
      }
      if ($("#password").val() != undefined) {
        if (!$("#password").val()) {
          return alert('Informe senha do profissional!');
        } 
      }
      Manager.getActivities();

      // para exibir um possivelcliente setado ao clicar na linha
      $("#customer").change();

      return $("#command_modal").modal({
        "show": true,
        "keyboard": true,
        "backdrop": true
      });
*/
    });
    $(".b_add_command").click(function() {
      return Manager.save();
    });Manager
    //$("#start_date").val(new Date().getDateBr());
    //$("#end_date").val(new Date().getDateBr());
    //var startDate = function(){
    //  return  encodeURIComponent($("#start").val() != "" ? $("#start").val() : getDateBr(new Date().getStartOfMonth()));
    //}
    //var endDate = function(){
    // return encodeURIComponent($("#end").val() != "" ? $("#end").val() : getDateBr(new Date()));
    //}     
    $("#product").productSearch({
      createName: false,
      iconElement: ".add-on",
      userThuch: true,
      userFieldSelector: '#user'
    });
    var start = gup("start") || getDateBr(new Date());
    $("#start").val(start)
    Manager.getUsersCurrentUnitCommand();
    Manager.getAuxiliarsCurrentUnitCommand();
    Manager.getCustomers();
    // comentadoo para nao entrar buscando
    //Manager.getListFromServer();
    $("#send").click(function() {
      return Manager.getListFromServer();
    });
    $("#user").change(function () {
      return Manager.getActivities();
    });
    $("#start_date,#end_date").change(function() {
      return Manager.getListFromServer();
    });
    var hasEdoctusSystem = $('.has-edoctus-system').length > 0;
    if (hasEdoctusSystem) {
//    if (document.location.href.indexOf("edoctus") != -1) {
      var min = 5 // minutos para rfresh
      setTimeout(function(){    
        window.location.reload();
      },1000*60*min);
      return Manager.getListFromServer();
    }
  });

  window.Manager = Manager;

}).call(this);
