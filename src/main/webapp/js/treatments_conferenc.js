  var removeTreatmentById = function(id, element){
    var hasDeleteCalendar = $('.has-delete-calendar').length > 0;
    if (hasDeleteCalendar) {
      if(confirm("Tem certeza que deseja excluir este atendimento?")){
          var url = "/treatment/"+id;
          $.ajax(url,{"type": "DELETE", "success" : function(){
              $(element).parent().parent().hide();
          }, "error" : function(response){
            alert("Erro ao excluir atendimento!\n Verifique se o atendimento não foi pago");
          }});
        }
    } else {
      alert ("Suas permissões não permitem excluir agendamento");
    }
  };
  var decodeStatus = function(status, status2){
    // duplicado no treatmentManger.js
          var statstr = '' 
          var title = ''
          if (status == 'Open') {
            statstr = 'open'
            title ='agendado'
          } else if (status == 'Missed') {
            statstr = 'Missed'
            title ='faltou'
          } else if (status == 'Arrived') {
            statstr = 'Arrived'
            title ='chegou'
          } else if (status == 'Ready') {
            statstr = 'Ready'
            title ='atendido'
          } else if (status == 'Paid') {
            if (status2 == 'Ready') {
              statstr = 'ready_paid'
              title ='atendido / pago'
            } else if (status2 == 'Missed') {
              statstr = 'missed_paid'
              title ='faltou / pago'
            } else {
              statstr = 'paid'
              title ='pago'
            }
          } else if (status == 'Deleted') {
            statstr = 'Deleted'
            title ='excluído'
          } else if (status == 'Confirmed') {
            statstr = 'Confirmed'
            title ='confirmado'
          } else if (status == 'PreOpen') {
            statstr = 'PreOpen'
            title ='pré agendado'
          } else if (status == 'ReSchedule') {
            statstr = 'ReSchedule'
            title ='desmarcou'
          }
    return "<img title='" + title + "' src='/images/treatment_"+status.toLowerCase()+".png' width='24'/>" +
        "<img title='" + title + "' src='/images/treatment_"+statstr.toLowerCase()+"1.png' width='24'/>"
  };
  $(function(){
      var hasUnitModule = $('.has-unit-module').length > 0;
      var hasFinancialAccess = $('.has-financial-access').length > 0;

      $('form').submit(function(){
        return false;
      });
      $("#product").productField(true);
      $("#go").click(function(){
        $.post("/treatments/getTreatmentsByFilter",$("#form_search").serialize(),function(t){
          var treatments = [];
          var total = 0.0;
          eval("treatments = "+t);
          var ret = "";
          for (var i = treatments.length - 1; i >= 0; i--) {
            obj = treatments[i];
            total += obj.total;              
            ret += "<tr>" +
            "<td>"+obj.command+"</td>" +
            "<td>"+getDateBr(FactoryDate.byTime(obj.date))+"</td>" +
            "<td>"+getHourBr(FactoryDate.byTime(obj.date))+"</td>" +
            "<td>"+getHourBr(FactoryDate.byTime(obj.end))+
            "</td>" + 
            "<td>"+obj.customerid+"</td>" + 
            "<td>"+"<a href='/customer/edit?id="+obj.customerid+"' target='_customer_maste'>"+obj.customername+"</a>"+"</td>" +
            "<td>"+obj.phone+"</td>" + // tem tb email conctenado
            "<td>"+obj.obs+"</td>" +
            "<td>"+obj.username+"</td>"+
            (hasUnitModule ? "<td>"+obj.unitname+"</td>" : '')+ 
            "<td>"+decodeStatus(obj.status, obj.status2)+"</td>" + 
            "<td>"+obj.details+"</td>" +
            "<td>"+obj.payments+"</td>" +
            (hasFinancialAccess ? "<td>"+obj.total.formatMoney()+"</td>" : '') +
            "<td>"+obj.cashier+
            "</td>" +
            "<td><a target='_commission' href='/financial/commission_report_filter?treatment="+obj.id+"' ><img class='hide_on_print' alt='Ver comissões deste atendimento' src='/images/commision_payment.png' width='24'></a>" +
            "<a href='#' onclick='removeTreatmentById("
              +obj.id+", this)'><img class='hide_on_print' alt='excluir este atendimento'  src='/images/delete.png'></a></td>";
          };
          $("#table_result tbody").html(ret);
          $("#count").val(treatments.length);
          $("#total").val(total.formatMoney());
          $("#table_result").tablesorter();
        })
      });
      $('#cashier').cashierField(true,'all',function(){ $('#cashier').val(gup("cashier")); });
      $("#user").userField(true);
      $("#offsale").offSaleField(true);
      $("#unit").unitField(true);
      DataManager.getUsers(function(userObj){
        user_str = "";
        user_str += "<option value=''>Todos</option>";
        for(var i in userObj){
              user_str += "<option value='"+userObj[i].id+"'>"+userObj[i].name+"</option>";
          }
          $("#user").html(user_str);
          $("#user").val("");
      });
      DataManager.getPaymentTypes(function(paymentTypes){
            var ret = "";
            ret += "<option value='SELECT_ALL'>Todos</option>";
            for(var i in paymentTypes){
                ret += "<option value='"+paymentTypes[i].id+"'>"+paymentTypes[i].name+"</option>";
            }
        $('#payment_type').append(ret);
      });
      DataManager.getActivities(false,function(activitysObj){
        $('#activity option').remove();
        var ret ="<option value=''>Selecione um serviço</option>";
        for(var i in activitysObj){
          ret +="<option value='"+activitysObj[i].id+"'>"+activitysObj[i].name+"</option>";
        }
        $('#activity').append(ret);
      });
})
    