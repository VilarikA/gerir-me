$(function() {
  if (gup("id")) {
    var renderAgerangeinterval = function() {
      var filter = {
        agerangeId: gup("id")
      };
      var fields = [];
      fields[5] = {
        type: "format",
        decode: function(value) {
          return '<input data-id="' + value + '" type="button" class="btn danger agerangeinterval_remove" value="Excluir">';
        }
      }
      var bindEvent = function() {
        $(".agerangeinterval_remove").click(function() {
          if (confirm("Deseja excluir a faixa etária?")) {
            var id = $(this).data("id");
            $.ajax("/api/v2/agerangeinterval/" + id, {
              "type": "DELETE",
              "success": function() {
                alert("Faixa etária excluída com sucesso!");
                renderAgerangeinterval();
              }
            })
          }
        });
      }
      renderReport("/api/v2/agerangeinterval/list", fields, filter, "#grid_agerangeinterval", bindEvent);
    }
    renderAgerangeinterval();
    
    $("#agerangeinterval_add").click(function() {
      $.post("/api/v2/agerangeinterval", {
        obs: $("#agerangeinterval_obs").val(),
        startmonths: $("#agerangeinterval_startmonths").val(),
        endmonths: $("#agerangeinterval_endmonths").val(),
        agerange: gup("id")
      }, function() {
        alert("Faixa etária adicionada com sucesso!");
        renderAgerangeinterval();
      });
    });
  }
})
