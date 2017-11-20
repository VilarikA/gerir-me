var prepareCashierParams = Cachier.prepareCashierParams;
var renderOuts = function(params, id_grid) {
    params = prepareCashierParams(params);
    if (!id_grid) {
        id_grid = "#grid_outs";
    }
    var fields = [];
    fields[0] = "date";
    fields[3] = "real";
    fields[4] = "inOut";
    var totalOuts = 0.00;
    var totalIns = 0.00;
    if (params.cashier instanceof Array) {
        params.cashier = params.cashier.join(",");
    }
    if (params.cashier != "") {
        renderReport("/account/report/out_of_cacashier", fields, params, id_grid, function(data) {
            data.forEach(function(item) {
                if (item[4] == '1') { //Saidas
                    totalOuts += parseFloat(item[3]);
                } else {
                    totalIns += parseFloat(item[3]);
                }
            });
            $("#total_out").val(totalOuts.formatMoney());
            $("#total_in").val(totalIns.formatMoney());
        });
    }
}

