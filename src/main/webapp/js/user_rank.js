  google.load("visualization", "1", {packages:["corechart"]});
      function drawChart() {
        $.post("/treatments/ranking",$("#form_report").serialize(),function(t){
          eval("var a = "+t);
          var lastUser = null;
          var dataForSet = []
          var tempArray = [];
          var uniqueNames = [];
          var arrayToDataTable = [];
          for (var i = a.length - 1; i >= 0; i--) {
            tempArray.push(a[i].name);
          };
          $.each(tempArray, function(i, el){
              if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
          });
          uniqueNames.push("Meses")
          arrayToDataTable.push(uniqueNames.reverse())
          for(i in a){
            if(a[i].month != lastUser){
              lastUser = a[i].month;
              if(dataForSet.length >0){
                arrayToDataTable.push(dataForSet);
                dataForSet = []
              }
              dataForSet.push(a[i].month);
            }
            dataForSet.push(parseFloat(a[i].total));
          }
          arrayToDataTable.push(dataForSet);
          var data = google.visualization.arrayToDataTable(arrayToDataTable);
          var options = {
            title: 'Performance'
          };
          var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
          chart.draw(data, options); 
        });
      };
      $(function(){
          DataManager.getUsers(function(userObj){
          user_str = "";
          for(var i in userObj){
                user_str += "<option value='"+userObj[i].id+"'>"+userObj[i].name+"</option>";
            }
            $("#user").html(user_str);
            $("#user").val("");
        });
        $("#go").click(function(){
          drawChart();
        });
      });