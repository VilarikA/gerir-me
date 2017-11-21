// Generated by CoffeeScript 1.3.1
(function() {
  var Cheque;

  Cheque = (function() {

    Cheque.name = 'Cheque';

    function Cheque() {}

    return Cheque;

  })();

  Cheque.getListFromServer = function() {
    return $.get("/payment/getCheques", function(results) {
      var cheque, total, _i, _len;
      $("#grid tbody").html("");
      eval("results = " + results);
      total = 0;
      for (_i = 0, _len = results.length; _i < _len; _i++) {
        cheque = results[_i];
        total += cheque.value;
        cheque.date = getDateBr(new Date(cheque.date));
        $("#grid tbody").append("<tr><td>" + cheque.id + "</td><td>" + cheque.bank + "</td><td>" + cheque.agency + "</td><td>" + cheque.account + "</td><td>" + cheque.number + "</td><td>" + (cheque.value.formatMoney()) + "</td><td>" + cheque.date + "</td><td>" + cheque.customer + "</td><td><a href='#' class='_popover danger marker' rel='popover' data-content='Marcar como recebido!'  data-original-title='Recebido?' data-id='" + cheque.id + "' data-confirm-message='que deseja marcar o cheque como descontado'><img src='/images/good.png'></a></td></tr>");
        $(".marker").click(function() {
          return $.get("/payment/cheque/makeAsReceived/" + $(this).attr("data-id"), function() {
            alert("Marcado com sucesso!");
            return Cheque.getListFromServer();
          });
        });
      }
      return $("#total").val(total.formatMoney());
    });
  };

  $(function() {
    return Cheque.getListFromServer();
  });

}).call(this);