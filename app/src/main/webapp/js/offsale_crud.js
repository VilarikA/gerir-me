$(function() {
  if (gup("id")) {
    var renderOsactivity = function() {
      var filter = {
        offsaleId: gup("id")
      };
      var fields = [];
      fields[1] = {
        type : "format",
        decode : function(value){
          var floatValue = parseFloat(value);
          if(!isNaN(floatValue)) {
            //total_serv += floatValue;
          }
          return floatValue.formatMoney();
        }
      }
      fields[4] = {
        type: "format",
        decode: function(value) {
          return '<input data-id="' + value + '" type="button" class="btn danger osactivity_remove" value="Excluir">';
        }
      }
      var bindEvent = function() {
        $(".osactivity_remove").click(function() {
          if (confirm("Deseja excluir o serviço?")) {
            var id = $(this).data("id");
            $.ajax("/api/v2/osactivity/" + id, {
              "type": "DELETE",
              "success": function() {
                alert("Serviço excluído com sucesso!");
                renderOsactivity();
              }
            })
          }
        });
      }
      renderReport("/api/v2/osactivity/list", fields, filter, "#grid_osactivity", bindEvent);
    }
    var renderOsproduct = function() {
      var filter = {
        offsaleId: gup("id")
      };
      var fields = [];
      fields[1] = {
        type : "format",
        decode : function(value){
          var floatValue = parseFloat(value);
          if(!isNaN(floatValue)) {
            //total_serv += floatValue;
          }
          return floatValue.formatMoney();
        }
      }
      fields[5] = {
        type: "format",
        decode: function(value) {
          return "<span style='margin-right:4px'><a class='btn' href='/offsale/offsaleproduct?id=" + value + "' target='_offsaleproduct_maste'>Editar</a></span>" +
          '<input data-id="' + value + '" type="button" class="btn danger osproduct_remove" value="Excluir">';
        }
      }
      var bindEvent = function() {
        $(".osproduct_remove").click(function() {
          if (confirm("Deseja excluir o produto?")) {
            var id = $(this).data("id");
            $.ajax("/api/v2/osproduct/" + id, {
              "type": "DELETE",
              "success": function() {
                alert("Produto excluído com sucesso!");
                renderOsproduct();
              }
            })
          }
        });
      }
      renderReport("/api/v2/osproduct/list", fields, filter, "#grid_osproduct", bindEvent);
    }

    renderOsactivity();
    renderOsproduct();
    
    $("#osactivity_add").click(function() {
      $.post("/api/v2/osactivity", {
        obs: $("#osactivity_obs").val(),
        product: $("#osactivity_activity").val(),
        offprice: $("#osactivity_offprice").val(),
        offsale: gup("id")
      }, function() {
        alert("Serviço adicionado com sucesso!");
        renderOsactivity();
      });
    });
    $("#osproduct_add").click(function() {
      $.post("/api/v2/osproduct", {
        obs: $("#osproduct_obs").val(),
        product: $("#osproduct_product").val(),
        offprice: $("#osproduct_offprice").val(),
        offsale: gup("id")
      }, function() {
        alert("Produto adicionado com sucesso!");
        renderOsproduct();
      });
    });
  }
})

