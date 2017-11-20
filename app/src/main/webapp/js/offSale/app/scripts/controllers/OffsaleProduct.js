'use strict';

angular.module('offSaleApp')
.controller('OffsaleProductCtrl', ['$scope','$http','OffsaleProductFactory','OffsaleProductsFactory', '$location',function ($scope, $http, OffsaleProductFactory, OffsaleProductsFactory, $location) {
        $scope.edit = function (product) {
            $scope.product = product;
            $("#activity").val(product.product_id).change();
            $("#products").val(product.product_id).change();;
            $('#line').val(product.line_id).change();
            $('#category_select').val(product.category_id).change()
            $("#minimum").val(product.minimum);
            $("#limitAmount").val(product.limitAmount);
            $("#percentOffProduct").val(product.percentOff);
            $("#offPrice").val(product.offPrice);
        };

        $scope.new = function () {
            $scope.product = { id :0 ,product_id: 0, product:"", line_id:0, line:"", category_id:0, category:"", percentOff:0.00, offPrice:0.00, minimum:0, limitAmount:0, offsale:parseInt(gup('id')),delivery:true};
            $scope.edit($scope.product);
        };        

        $scope.delete = function (id) {
            OffsaleProductFactory.delete({ id: id }).$promise.then(function(){
                updateProducts();
            });
        };
        var normaLize = function(data){
            for(var i in  data){
                if(i.indexOf("id") > 0){
                    if(data[i]){
                        data[i] = parseInt(data[i]);
                    }
                }
            }
        }
        $scope.save = function (product) {            
            product.product_id = $("#products").val() || 0;
            product.line_id = $('#line').val() || 0;
            product.offsale = parseInt(gup('id'));
            product.category_id = $('#category_select').val() || 0;
            product.minimum = parseInt($("#minimum").val());
            product.limitAmount = parseInt($("#limitAmount").val());
            product.percentOff = parseFloat($("#percentOffProduct").val());;
            product.offPrice = parseFloat($("#offPrice").val());
           if(!product.product_id || product.product_id == "0"){
                product.product_id = $("#activity").val();
            }            
            normaLize(product);
            if(!product.id){
                product.id = -1;
            }
            $http.post("/offsale/offsaleProducts",product).success(function(){
                alert('Salvo com sucesso!');
                $scope.new();
                updateProducts();
            });
        };
        var updateProducts = function(){
	        $http.get("/offsale/products/"+gup("id")).success(function(data){
	        	$scope.offsaleProducts = eval(data);//OffsaleProductsFactory.query();
                try{
                    //$scope.$apply();
                }catch(e){}
	        });        	
        };
        updateProducts();
        $scope.new();
}]);