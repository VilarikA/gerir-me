var Treatment = function(id, command, customerId, customer, userId, status, date, obs, hour_start, hour_end, treatmentConflit){
    this.id = id;
    this.command = command;
    this.customerId = customerId;
    this.customer = customer;
    this.userId = this.userId;
    this.status = status;
    this.date = date;
    this.obs = obs;
    this.hour_start = hour_start;
    this.hour_end = hour_end;
    if(treatmentConflit){
      this.conflit = true;
    }
    this.treatmentConflit = treatmentConflit;
};

Treatment.factory = function(id, command, customerId, customer, userId, status, date, obs, hour_start, hour_end, treatmentConflit){
  return new Treatment( id, command, customerId, customer, userId, status, date, obs, hour_start, hour_end, treatmentConflit);
};

var TreatmentController = function($){

    this.getAllTreatmentsConflites = function(idParent, treatments){
      return treatments.filter(function(item){ return item.treatmentConflit === idParent});
    };

    this.saveTreatment = function(treatment, sucessCallback, errorCallback){
      var url = "/treatment";
      $.ajax(url,{"type": "PUT", "success" : function(treatmentReturn){
              if(sucessCallback){
                  sucessCallback(treatment);
              }
            },
            "error" : function(response){
                if(errorCallback){
                  errorCallback(response);
                }else{
                    alert("Erro ao adicionar atendimento!");
                }
             }, "data": treatment
          });
    };

    this.deleteTreatment = function(id, sucessCallback, errorCallback){
        var url = "";
        url = "/treatment/"+id;
        $.ajax(url,{"type": "DELETE", "success" : function(){
          if(sucessCallback){
            sucessCallback();
          }
        }, "error" : function(response){
          if(errorCallback){
            errorCallback(response);
          }else{
            alert("Erro ao exluir atendimento!\n Verifique se o atendimento não foi pago!");
          }
        }});
    };

    this.updateTreatmentData = function(id,user,startDate,endDate,status, sucessCallback){
      var start = getDateBr(startDate)+" "+getHourBr(startDate);
      var end = getDateBr(endDate)+" "+getHourBr(endDate);
      var url = "";
      if(status === TreatmentController.EVENT){
        url = "/userEvent/"+id;
      }else{
        url = "/treatment/"+id;
      }
      $.post(url,{"user" : user, "start": start, "end": end},function(t){
        sucessCallback(t);
      });
    };

    this.addDetail = function(treatmentId,activityId, sucessCallback, errorCallback){
        $.post("/treatment_detail",{"activity":activityId,"id":treatmentId},function(t){
          eval("t="+t)
          if(t==1){
            sucessCallback(t);
          }else{
            errorCallback(t);
          }
        });
    };

    this.deleteDetail = function( detailId, sucessCallback, errorCallback){
        var url = "/treatment/detail/"+detailId;
        $.ajax(url,{"type": "DELETE", "success" : function(){
            if(sucessCallback)
              sucessCallback();
        }, "error" : function(response){
            if(errorCallback){
               errorCallback();
            }else{
               alert("Erro ao exluir atendimento!\n Verifique se o atendimento não foi pago!");
            }
        }});
    };

};
TreatmentController.EVENT = "event";
TreatmentController.TREATMENT = "treatment";