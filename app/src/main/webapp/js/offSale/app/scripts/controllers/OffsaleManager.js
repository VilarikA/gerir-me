var OffSaleCurrentClass = function(){
    var offSale = {};
    this.calculatePrice = function(productId, originPrice){
        if(offSale.products){
            var product = offSale.products.filter(function(product){
                return product.product_id == productId;
            });
            if(product.length > 0){
                return product[0].offPrice;
            }
        }
        return originPrice;
    };

    this.getProcuts = function(offSaleId){
        if(offSaleId != 0){
            $.get("/offsale/products/"+offSaleId, function(products){
                offSale.products = eval(products);
            });
        }else{
            offSale.products = [];
        }
    }
}
var OffSaleCurrent = new OffSaleCurrentClass();