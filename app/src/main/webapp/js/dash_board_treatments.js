$(function(){
  function drawChartTreatments(chartData) {
    var data = google.visualization.arrayToDataTable(chartData);

    var options = {
      title: 'Atendimentos',
      vAxis: {minValue: 0}
    };

    var chart = new google.visualization.AreaChart(document.getElementById('treatments_line'));
    chart.draw(data, options);
  }
  function drawNewCustomers(data) {
    var data = google.visualization.arrayToDataTable([
      ['Clientes', 'Novos'],
      ['Novos',     data.news],
      ['Velhos',     data.returns]
    ]);

    var options = {
      title: 'Novos Clientes',
      is3D: true,
    };

    var chart = new google.visualization.PieChart(document.getElementById('treatments_stats'));
    chart.draw(data, options);
  }
  var request = function(){
    var url = "/treatments/dashboar";
    $.post(url, $("#form_report").serializeObject(), function(result){
      eval("var obj = "+result);
      var chartData = [['Dia', 'Atendimentos', 'Novos Clientes']];
      var customerData = {news:0, returns:0};
      var treatmentData = {total:0, unicCustomers:0, avgDuration:"00:00", details:0, rejeision:0, services:0};
      obj.data.forEach(function(item){
                        item[0] = new Date(item[0]).getTextWhen();
                        item[1] = parseInt(item[1]);
                        item[2] = parseInt(item[2]);
                        customerData.news +=item[2];
                        if(item[1] > item[2]){
                          customerData.returns += item[1]-item[2];
                        }
                        chartData.push(item);
                      });
      treatmentData.unicCustomers = obj.customer.length;
      obj.customer.forEach(function(item){
          treatmentData.total += parseInt(item[0]);
          treatmentData.services += parseInt(item[1]);
          if(item[0]==1){
            treatmentData.rejeision++;
          }
      });
      var percentRejeicion = (treatmentData.rejeision / treatmentData.unicCustomers)*100;
      var percentNewCustomer = (customerData.news  / (customerData.news+customerData.returns))*100;
      drawChartTreatments(chartData);
      drawNewCustomers(customerData);
      //
      $("#services .value_dashboar").html(treatmentData.services.formatMoney().split(',')[0]);
      $("#total_treatments .value_dashboar").html(treatmentData.total.formatMoney().split(',')[0]);
      $("#unic_customers .value_dashboar").html(treatmentData.unicCustomers.formatMoney().split(',')[0]);
      $("#avg_duration .value_dashboar").html(obj.duration[0][0]);

      $("#rejeision .value_dashboar").html(percentRejeicion.formatMoney()+"%");
      $("#percent_new_customer .value_dashboar").html(percentNewCustomer.formatMoney()+"%");
      
    });
  };
  $("#start").val(getDateBr(new Date().getStartOfMonth()));
  $("#end").val(getDateBr(new Date()));
  $("#units").unitField(false, false);
  $("#users").userField();
  $("#user_groups").userGroupField();
  $("#activitys").activityField();
  $("#activity_types").activityTypeField();
  $("#send").click(request);
  request();
});