
$(function() {
})
var callApiLock = false;
var callApiInvoice = function() {
  if (!$('#offsale').val()){
     alert("Convênio inválido!")
  } else {
    if (!callApiLock) {
      callApiLock = true
      var startDate = function(){
        return  encodeURIComponent($("#start").val() != "" ? $("#start").val() : getDateBr(new Date().getStartOfMonth()));
      }
      var endDate = function(){
        return encodeURIComponent($("#end").val() != "" ? $("#end").val() : getDateBr(new Date()));
      }     
      var unit = $("#unit").val() || '0';
      var type = $("#hospitalizationType").val();
      //url = $('#invoicing_form').attr('action');
      url = "/invoice/invoicing/" + $('#offsale').val()+"/"+unit+"/"+type+"/"+startDate()+"/"+endDate();
      $.get(url, function(t) {
        eval("var obj = " + t)
        if (obj.status == 'success') {
          if (obj.value != "0.0") {
            alert("Fatura [" + obj.id + "] gerada com sucesso! R$ " + obj.value)
          } else {
            alert("Fatura [" + obj.id + "] seria gerada com valor zero")
          }
          callApiLock = false;
        } else {
          if (obj.message) {
            alert(obj.message)
            callApiLock = false;
          } else {
            alert("Erro ao faturar!")
            callApiLock = false;
          }
        }
      });
    } else {
      alert("Já existe um processo em andamento. Aguarde o fim do processamento para clicar novamente!");
    }
  }
}

/*
var callApiInvoiceXml = function() {
      url = $('#invoicing_form').attr('action');
      url += "/xmltiss/" + "811";
      $.get(url, function(t) {
        eval("var obj = " + t)
        if (obj.status == 'success') {
        } else {
          if (obj.message) {
            alert(obj.message)
            callApiLock = false;
          } else {
            alert("Erro ao gerar xml!")
            callApiLock = false;
          }
        }
      });

}
*/
